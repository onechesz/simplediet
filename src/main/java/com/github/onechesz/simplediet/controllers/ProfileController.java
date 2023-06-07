package com.github.onechesz.simplediet.controllers;

import com.github.onechesz.simplediet.entities.UserParametersEntity;
import com.github.onechesz.simplediet.security.UserDetails;
import com.github.onechesz.simplediet.services.UserParametersService;
import jakarta.validation.Valid;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/profile")
public class ProfileController {
    private final UserParametersService userParametersService;

    @Contract(pure = true)
    public ProfileController(UserParametersService userParametersService) {
        this.userParametersService = userParametersService;
    }

    @GetMapping(value = "")
    private @NotNull String profile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            int userId = ((UserDetails) authentication.getPrincipal()).getId();

            model.addAttribute("user_parameters", userParametersService.findById(userId));

            return "profile/profile";
        }

        return "redirect:/login";
    }

    @PostMapping(value = "")
    private @NotNull String profile(@ModelAttribute(value = "user_parameters") @Valid UserParametersEntity userParametersEntity, @NotNull BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "profile/profile";

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            int userId = ((UserDetails) authentication.getPrincipal()).getId();

            userParametersService.save(userId, userParametersEntity);

            return "profile/profile";
        }

        return "redirect:/login";
    }
}
