package com.github.onechesz.simplediet.validators;

import com.github.onechesz.simplediet.dto.UserDTO;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserDTOValidator implements Validator {
    @Override
    public boolean supports(@NotNull Class<?> clazz) {
        return UserDTO.class.equals(clazz);
    }

    @Override
    public void validate(@NotNull Object target, @NotNull Errors errors) {
        UserDTO userDTO = (UserDTO) target;

        if (!userDTO.getPassword().equals(userDTO.getConfirmedPassword()))
            errors.rejectValue("confirmedPassword", "", "Пароли должны совпадать.");
    }
}
