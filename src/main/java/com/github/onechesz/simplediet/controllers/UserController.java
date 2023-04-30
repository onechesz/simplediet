package com.github.onechesz.simplediet.controllers;

import com.github.onechesz.simplediet.entities.UserEntity;
import com.github.onechesz.simplediet.services.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(value = "/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/{username}")
    public String viewUser(@PathVariable(value = "username") String username, @NotNull Model model) {
        Optional<UserEntity> userEntity = userService.findByUsername(username);

        if (userEntity.isPresent()) {
            model.addAttribute("user", userEntity.get());

            return "users/user";
        }

        return "redirect:/";
    }

    @PatchMapping(value = "/{id}")
    public String changeRole(@PathVariable(value = "id") int id, @ModelAttribute(value = "role") String role) {
        userService.changeRole(id, role);

        return "redirect:/";
    }
}
