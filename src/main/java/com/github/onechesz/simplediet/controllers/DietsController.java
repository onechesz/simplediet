package com.github.onechesz.simplediet.controllers;

import com.github.onechesz.simplediet.dto.dtio.DietDTIO;
import com.github.onechesz.simplediet.dto.dtoo.DietDTOO;
import com.github.onechesz.simplediet.entities.UserParametersEntity;
import com.github.onechesz.simplediet.security.UserDetails;
import com.github.onechesz.simplediet.services.DietService;
import com.github.onechesz.simplediet.services.UserParametersService;
import com.github.onechesz.simplediet.util.gpt.Message;
import com.github.onechesz.simplediet.util.gpt.Request;
import jakarta.validation.Valid;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/diets")
public class DietsController {
    private final DietService dietService;
    private final UserParametersService userParametersService;

    @Contract(pure = true)
    public DietsController(DietService dietService, UserParametersService userParametersService) {
        this.dietService = dietService;
        this.userParametersService = userParametersService;
    }

    @GetMapping(value = "")
    public String diets(@RequestParam(value = "search", required = false) String search, @RequestParam(value = "from", required = false) String from, @RequestParam(value = "to", required = false) String to, @RequestParam(value = "sort", required = false) String sort, @NotNull Model model) {
        model.addAttribute("diets", dietService.findAll(search, from, to, sort));

        return "diets/diets";
    }

    @GetMapping(value = "/add")
    public String addDiet(@NotNull Model model) {
        model.addAttribute("diet", new DietDTIO());

        return "diets/add";
    }

    @PostMapping(value = "/add")
    public String addDiet(@ModelAttribute(value = "diet") @Valid DietDTIO dietDTIO, @NotNull BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) return "diets/add";

        dietService.save(dietDTIO);

        return "redirect:/diets";
    }

    @GetMapping(value = "/{dietId}")
    public String viewDiet(@PathVariable("dietId") int dietId, @NotNull Model model) {
        DietDTOO dietDTOO = dietService.findById(dietId);

        if (dietDTOO == null) return "redirect:/diets";

        model.addAttribute("diet", dietDTOO);

        return "diets/diet";
    }

    @GetMapping(value = "/{dietId}/edit")
    public String editDiet(@PathVariable("dietId") int dietId, @NotNull Model model) {
        DietDTOO dietDTOO = dietService.findById(dietId);

        if (dietDTOO == null) return "redirect:/diets";

        model.addAllAttributes(Map.of("diet_dtoo", dietDTOO, "diet_dtio", new DietDTIO()));

        return "diets/edit";
    }

    @PatchMapping(value = "/{dietId}/edit")
    public String editDiet(@PathVariable("dietId") int dietId, @ModelAttribute("diet") @Valid DietDTIO dietDTIO, @NotNull BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) return "diets/edit";

        dietService.edit(dietId, dietDTIO);

        return "redirect:/diets";
    }

    @DeleteMapping(value = "/{dietId}")
    public String deleteDiet(@PathVariable("dietId") int dietId) throws IOException {
        dietService.deleteById(dietId);

        return "redirect:/diets";
    }

    @PostMapping(value = "/start")
    public String generateDiet(@RequestParam(value = "preferences", required = false) String preferences, @RequestParam(value = "goal") String goal, @RequestParam(value = "diet_title") String dietTitle, @RequestParam(value = "diet_duration") int dietDuration, @RequestParam(value = "allergy") List<String> allergy, @RequestParam(value = "physical_activity") String physicalActivity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        System.out.println(preferences);
        System.out.println(goal);
        System.out.println(dietTitle);
        System.out.println(dietDuration);
        System.out.println(allergy);
        System.out.println(physicalActivity);

//        if (authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
//            String URL = "https://api.openai.com/v1/chat/completions";
//            HttpHeaders httpHeaders = new HttpHeaders();
//            Request request = new Request();
//            UserParametersEntity userParametersEntity = userParametersService.findById(((UserDetails) authentication.getPrincipal()).getId());
//
//            switch (goal) {
//                case "gain" -> {
//                    goal = "набрать вес";
//                }
//                case "keep" -> {
//                    goal = "сохранить вес";
//                }
//                default -> {
//                    goal = "сбросить вес";
//                }
//            }
//
//            String message = "Сгенерируй план питания, основываясь на такой диете, как " + dietTitle + " с точным учётом следующих параметров: пол: " + userParametersEntity.getSex() + ", рост: " + userParametersEntity.getAge() + "см, вес " + userParametersEntity.getWeight() + "кг, цель: " + goal + ", аллергия на: , физическая активность: высокая, продолжительность (в днях): " + dietDuration + ", предпочтения: " + preferences + ". Строго в таком формате: День 1: завтрак: ... обед: ... полдник: ... ужин: ... День 2:... И так распиши всё количество дней (" + dietDuration + "), а не только часть. Обязательно к каждому продукту подписывай количество грамм.";
//
//            request.appendMessage(new Message("user", message));
//            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//        }

        return "redirect:/diet";
    }
}
