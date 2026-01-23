package com._s.api.infra.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CNPJValidator implements ConstraintValidator<CNPJ, String> {

    private static final int[] FIRST_DIGIT_WEIGHTS = {
            5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2
    };

    private static final int[] SECOND_DIGIT_WEIGHTS = {
            6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2
    };

    @Override
    public boolean isValid(String cnpj, ConstraintValidatorContext context) {
        if (cnpj == null) {
            return true; // let @NotNull handle null validation
        }

        String normalized = cnpj
                .replace(".", "")
                .replace("/", "")
                .replace("-", "")
                .toUpperCase();

        if (normalized.length() != 14) {
            return false;
        }

        if (!normalized.matches("[0-9A-Z]{14}")) {
            return false;
        }

        String base = normalized.substring(0, 12);
        String providedCheckDigits = normalized.substring(12);

        String calculatedCheckDigits = calculateCheckDigits(base);

        return providedCheckDigits.equals(calculatedCheckDigits);
    }

    private String calculateCheckDigits(String base) {
        int firstDigit = calculateDigit(base, FIRST_DIGIT_WEIGHTS);
        int secondDigit = calculateDigit(base + firstDigit, SECOND_DIGIT_WEIGHTS);
        return "" + firstDigit + secondDigit;
    }

    private int calculateDigit(String value, int[] weights) {
        int sum = 0;

        for (int i = 0; i < weights.length; i++) {
            int digit = Character.digit(value.charAt(i), 36);
            sum += digit * weights[i];
        }

        int remainder = sum % 11;
        return (remainder < 2) ? 0 : 11 - remainder;
    }
}
