package com.github.onechesz.simplediet.validators;

import com.github.onechesz.simplediet.entities.UserEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserFieldsValidator implements Validator {
    @Override
    public boolean supports(@NotNull Class<?> clazz) {
        return UserEntity.class.equals(clazz);
    }

    @Override
    public void validate(@NotNull Object target, @NotNull Errors errors) {
        UserEntity userEntity = (UserEntity) target;

        if (!userEntity.getUserPassword().equals(userEntity.getConfirmedPassword()))
            errors.rejectValue("confirmedPassword", "", "Пароли должны совпадать.");
    }
}
