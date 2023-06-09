package com.github.onechesz.simplediet.services;

import com.github.onechesz.simplediet.dto.UserParametersDTO;
import com.github.onechesz.simplediet.repositories.PersonalDietRepository;
import com.github.onechesz.simplediet.security.UserDetails;
import com.github.onechesz.simplediet.util.gpt.Message;
import com.github.onechesz.simplediet.util.gpt.Request;
import com.github.onechesz.simplediet.util.gpt.Response;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PersonalDietService {
    private final PersonalDietRepository personalDietRepository;
    private final UserParametersService userParametersService;
    private final RestTemplate restTemplate = new RestTemplate();

    @Contract(pure = true)
    public PersonalDietService(PersonalDietRepository personalDietRepository, UserParametersService userParametersService) {
        this.personalDietRepository = personalDietRepository;
        this.userParametersService = userParametersService;
    }

    public ResponseEntity<Response> generatePersonalDiet(String preferences, @NotNull String goal, String dietTitle, int dietDuration, @NotNull Authentication authentication) {
        String URL = "https://api.openai.com/v1/chat/completions";
        HttpHeaders httpHeaders = new HttpHeaders();
        Request request = new Request();
        UserParametersDTO userParametersDTO = userParametersService.findById(((UserDetails) authentication.getPrincipal()).getId());
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

        HttpEntity<Request> requestHttpEntity = new HttpEntity<>(request, httpHeaders);

        return restTemplate.exchange(
                URL,
                HttpMethod.POST,
                requestHttpEntity,
                Response.class
        );
    }
}
