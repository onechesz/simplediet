package com.github.onechesz.simplediet.controllers;

import com.github.onechesz.simplediet.security.UserDetails;
import com.github.onechesz.simplediet.services.PersonalDietService;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/diet")
public class DietController {
    private final PersonalDietService personalDietService;

    @Contract(pure = true)
    public DietController(PersonalDietService personalDietService) {
        this.personalDietService = personalDietService;
    }

    @GetMapping(value = "")
    public String diet(@NotNull Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            model.addAttribute("personal_diet", personalDietService.findByUserId(((UserDetails) authentication.getPrincipal()).getId()));

            return "diet/diet";
        }

        return "redirect:/login";
    }
}
