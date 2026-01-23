package com._s.api.infra.validation;

import jakarta.validation.Constraint;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CNPJValidator;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CNPJValidator.class)
@Documented
public @interface CNPJ {
}
