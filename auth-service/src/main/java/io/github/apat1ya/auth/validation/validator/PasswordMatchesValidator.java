package io.github.apat1ya.auth.validation.validator;

import io.github.apat1ya.auth.dto.UserRegistrationRequestDto;
import io.github.apat1ya.auth.validation.annotation.PasswordMatches;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, UserRegistrationRequestDto> {

    @Override
    public boolean isValid(UserRegistrationRequestDto value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        if (value.password() == null || value.repeatPassword() == null) {
            return false;
        }

        return value.password().equals(value.repeatPassword());
    }
}
