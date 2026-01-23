package com._s.api.infra.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
@Documented
public @interface Password {
    String message() default "Senha inválida: deve conter 8 caracteres, letras maiúsculas, minúsculas e números";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}