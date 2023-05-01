package com.github.onechesz.simplediet.controllers;

import com.github.onechesz.simplediet.entities.UserParams;
import com.github.onechesz.simplediet.security.UserDetails;
import com.github.onechesz.simplediet.services.UserParamsService;
import jakarta.validation.Valid;
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
    private final UserParamsService userParamsService;

    public ProfileController(UserParamsService userParamsService) {
        this.userParamsService = userParamsService;
    }

    @GetMapping(value = "")
    private @NotNull String profile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            int userId = ((UserDetails) authentication.getPrincipal()).getId();

            model.addAttribute("user_params", userParamsService.findById(userId));

            return "profile/profile";
        }

        return "redirect:/auth/login";
    }

    @PostMapping(value = "")
    private @NotNull String profile(@ModelAttribute(value = "user_params") @Valid UserParams userParams, @NotNull BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "profile/profile";

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            int userId = ((UserDetails) authentication.getPrincipal()).getId();

            userParamsService.save(userId, userParams);

            return "profile/profile";
        }

        return "redirect:/auth/login";
    }
}
