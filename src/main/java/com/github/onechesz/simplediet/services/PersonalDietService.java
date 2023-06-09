package com.github.onechesz.simplediet.services;

import com.github.onechesz.simplediet.dto.UserParametersDTO;
import com.github.onechesz.simplediet.entities.PersonalDietEntity;
import com.github.onechesz.simplediet.entities.UserEntity;
import com.github.onechesz.simplediet.repositories.PersonalDietRepository;
import com.github.onechesz.simplediet.repositories.UserRepository;
import com.github.onechesz.simplediet.util.gpt.Message;
import com.github.onechesz.simplediet.util.gpt.Request;
import com.github.onechesz.simplediet.util.gpt.Response;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional(readOnly = true)
public class PersonalDietService {
    private final PersonalDietRepository personalDietRepository;
    private final UserParametersService userParametersService;
    private final RestTemplate restTemplate = new RestTemplate();
    private final UserRepository userRepository;

    @Contract(pure = true)
    public PersonalDietService(PersonalDietRepository personalDietRepository, UserParametersService userParametersService, UserRepository userRepository) {
        this.personalDietRepository = personalDietRepository;
        this.userParametersService = userParametersService;
        this.userRepository = userRepository;
    }

    @Transactional
    public PersonalDietEntity createEmptyPersonalDiet(int userId, int dietDaysDuration) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(userId);

        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();
            PersonalDietEntity personalDietEntity = new PersonalDietEntity(userEntity, dietDaysDuration, "process");
            personalDietRepository.save(personalDietEntity);
            userEntity.setPersonalDietEntity(personalDietEntity);
            userRepository.save(userEntity);

            return personalDietEntity;
        }

        return null;
    }

    @Async
    @Transactional
    public CompletableFuture<Void> sendRequestAndUpdatePersonalDiet(int userId, @NotNull String goal, String preferences, String dietTitle, int dietDuration, PersonalDietEntity personalDietEntity) {
        String URL = "https://api.openai.com/v1/chat/completions";
        HttpHeaders httpHeaders = new HttpHeaders();
        Request request = new Request();
        UserParametersDTO userParametersDTO = userParametersService.findById(userId);

        switch (goal) {
            case "gain" -> {
                goal = "набрать вес";
            }
            case "keep" -> {
                goal = "сохранить вес";
            }
            default -> {
                goal = "сбросить вес";
            }
        }

        switch (userParametersDTO.getPhysicalActivity()) {
            case ("low") -> {
                userParametersDTO.setPhysicalActivity("низкая");
            }
            case ("medium") -> {
                userParametersDTO.setPhysicalActivity("средняя");
            }
            default -> {
                userParametersDTO.setPhysicalActivity("высокая");
            }
        }

        String message = "Сгенерируй план питания, основываясь на такой диете, как " + dietTitle + " с точным учётом следующих параметров: пол: " + userParametersDTO.getSex() + ", рост: " + userParametersDTO.getAge() + "см, вес " + userParametersDTO.getWeight() + "кг, цель: " + goal + ", аллергия на: " + String.join(", ", userParametersDTO.getAllergy()) + ", физическая активность: " + userParametersDTO.getPhysicalActivity() + ", продолжительность (в днях): " + dietDuration + ", предпочтения: " + preferences + ". Строго в таком формате: День 1: завтрак: ... обед: ... полдник: ... ужин: ... День 2:... И так распиши всё количество дней (" + dietDuration + "), а не только часть. Обязательно к каждому продукту подписывай количество грамм.";

        request.appendMessage(new Message("user", message));
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("Authorization", "Bearer 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");

        HttpEntity<Request> requestHttpEntity = new HttpEntity<>(request, httpHeaders);
        ResponseEntity<Response> responseEntity = restTemplate.exchange(
                URL,
                HttpMethod.POST,
                requestHttpEntity,
                Response.class
        );
        String content = Objects.requireNonNull(responseEntity.getBody()).getChoices().get(0).getMessage().getContent();
        content = content.substring(content.indexOf("День 1:"));

        personalDietEntity.setMeal(content);
        personalDietEntity.setStatus("generated");
        personalDietRepository.save(personalDietEntity);

        return CompletableFuture.completedFuture(null);
    }

    public PersonalDietEntity findByUserId(int userId) {
        UserEntity userEntity = userRepository.findById(userId).orElse(null);

        if (userEntity != null) return userEntity.getPersonalDietEntity();

        return null;
    }
}
