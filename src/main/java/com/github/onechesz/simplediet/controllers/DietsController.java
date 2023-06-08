package com.github.onechesz.simplediet.controllers;

import com.github.onechesz.simplediet.dto.dtio.DietDTIO;
import com.github.onechesz.simplediet.dto.dtoo.DietDTOO;
import com.github.onechesz.simplediet.services.DietService;
import jakarta.validation.Valid;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping(value = "/diets")
public class DietsController {
    private final DietService dietService;

    @Contract(pure = true)
    public DietsController(DietService dietService) {
        this.dietService = dietService;
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
}
