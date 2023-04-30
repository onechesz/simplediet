package com.github.onechesz.simplediet.controllers;

import com.github.onechesz.simplediet.dto.UserDTO;
import com.github.onechesz.simplediet.entities.UserEntity;
import com.github.onechesz.simplediet.services.UserService;
import com.github.onechesz.simplediet.validators.UserDTOValidator;
import com.github.onechesz.simplediet.validators.UserValidator;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/auth")
public class AuthController {
    private final UserDTOValidator userDTOValidator;
    private final UserValidator userValidator;
    private final UserService userService;

    public AuthController(UserDTOValidator userDTOValidator, UserValidator userValidator, UserService userService) {
        this.userDTOValidator = userDTOValidator;
        this.userValidator = userValidator;
        this.userService = userService;
    }

    @GetMapping(path = "/register")
    public String register(@NotNull Model model) {
        model.addAttribute("user", new UserDTO());

        return "auth/register";
    }

    @PostMapping(path = "/register")
    public String register(@ModelAttribute(value = "user") @Valid UserDTO userDTO, @NotNull BindingResult bindingResult) {
        userDTOValidator.validate(userDTO, bindingResult);

        if (bindingResult.hasErrors()) return "auth/register";

        UserEntity userEntity = UserDTO.convertToUserEntity(userDTO);

        userValidator.validate(userEntity, bindingResult);

        if (bindingResult.hasErrors()) return "auth/register";

        userService.save(userEntity);

        return "redirect:/auth/login";
    }

    @GetMapping(path = "/login")
    public String login() {
        return "auth/login";
    }
}
