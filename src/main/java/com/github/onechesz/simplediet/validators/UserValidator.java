package com.github.onechesz.simplediet.validators;

import com.github.onechesz.simplediet.entities.UserEntity;
import com.github.onechesz.simplediet.repositories.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    private final UserRepository userRepository;

    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(@NotNull Class<?> clazz) {
        return UserEntity.class.equals(clazz);
    }

    @Override
    public void validate(@NotNull Object target, @NotNull Errors errors) {
        UserEntity userEntity = (UserEntity) target;

        if (userRepository.findByUsername(userEntity.getUsername()).isPresent())
            errors.rejectValue("username", "", "Пользователь с таким именем уже существует.");
    }
}
