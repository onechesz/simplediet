package com.github.onechesz.simplediet.services;

import com.github.onechesz.simplediet.dto.UserParametersDTO;
import com.github.onechesz.simplediet.entities.PersonalDietEntity;
import com.github.onechesz.simplediet.entities.UserEntity;
import com.github.onechesz.simplediet.repositories.PersonalDietRepository;
import com.github.onechesz.simplediet.repositories.UserRepository;
import com.github.onechesz.simplediet.security.UserDetails;
import com.github.onechesz.simplediet.util.gpt.Message;
import com.github.onechesz.simplediet.util.gpt.Request;
import com.github.onechesz.simplediet.util.gpt.Response;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Service
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

    @Async
    public CompletableFuture<Void> generatePersonalDiet(String preferences, @NotNull String goal, String dietTitle, int dietDuration, @NotNull Authentication authentication) {
        String URL = "https://api.openai.com/v1/chat/completions";
        HttpHeaders httpHeaders = new HttpHeaders();
        Request request = new Request();
        int userId = ((UserDetails) authentication.getPrincipal()).getId();
        UserParametersDTO userParametersDTO = userParametersService.findById((userId));
        String physicalActivity;

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
                physicalActivity = "низкая";
            }
            case ("medium") -> {
                physicalActivity = "средняя";
            }
            default -> {
                physicalActivity = "высокая";
            }
        }

        String message = "Сгенерируй план питания, основываясь на такой диете, как " + dietTitle + " с точным учётом следующих параметров: пол: " + userParametersDTO.getSex() + ", рост: " + userParametersDTO.getAge() + "см, вес " + userParametersDTO.getWeight() + "кг, цель: " + goal + ", аллергия на: " + String.join(", ", userParametersDTO.getAllergy()) + ", физическая активность: " + physicalActivity + ", продолжительность (в днях): " + dietDuration + ", предпочтения: " + preferences + ". Строго в таком формате: День 1: завтрак: ... обед: ... полдник: ... ужин: ... День 2:... И так распиши всё количество дней (" + dietDuration + "), а не только часть. Обязательно к каждому продукту подписывай количество грамм.";

        request.appendMessage(new Message("user", message));
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("Authorization", "Bearer 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");

        UserEntity userEntity = userRepository.findById(userId).orElse(null);

        if (userEntity != null) {
            PersonalDietEntity personalDietEntity = new PersonalDietEntity(userEntity, LocalDateTime.now(), dietDuration, "process");

            personalDietRepository.save(personalDietEntity);
            userEntity.setPersonalDietEntity(personalDietEntity);
            userRepository.save(userEntity);

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
        }

        return CompletableFuture.completedFuture(null);
    }
}
