package com.github.onechesz.simplediet.controllers;

import com.github.onechesz.simplediet.entities.UserEntity;
import com.github.onechesz.simplediet.services.UserService;
import com.github.onechesz.simplediet.validators.UserFieldsValidator;
import com.github.onechesz.simplediet.validators.UserDataValidator;
import jakarta.validation.Valid;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "")
public class AuthController {
    private final UserFieldsValidator userFieldsValidator;
    private final UserDataValidator userDataValidator;
    private final UserService userService;

    @Contract(pure = true)
    public AuthController(UserFieldsValidator userFieldsValidator, UserDataValidator userDataValidator, UserService userService) {
        this.userFieldsValidator = userFieldsValidator;
        this.userDataValidator = userDataValidator;
        this.userService = userService;
    }

    @GetMapping(value = "/register")
    public String register(@NotNull Model model) {
        model.addAttribute("user", new UserEntity());

        return "auth/register";
    }

    @PostMapping(value = "/register")
    public String register(@ModelAttribute(value = "user") @Valid UserEntity userEntity, @NotNull BindingResult bindingResult) {
        userFieldsValidator.validate(userEntity, bindingResult);

        if (bindingResult.hasErrors()) return "auth/register";

        userDataValidator.validate(userEntity, bindingResult);

        if (bindingResult.hasErrors()) return "auth/register";

        userService.save(userEntity);

        return "redirect:/login";
    }

    @GetMapping(value = "/login")
    public String login() {
        return "auth/login";
    }
}
