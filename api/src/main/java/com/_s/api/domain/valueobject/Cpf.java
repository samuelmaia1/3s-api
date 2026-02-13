package com._s.api.domain.valueobject;

import lombok.Getter;

import java.util.Objects;

@Getter
public class Cpf {
    private final String value;

    public Cpf(String cpf) {
        String normalized = cpf.replaceAll("\\D", "");

        if (!isValid(normalized)) {
            throw new IllegalArgumentException("CPF inválido");
        }

        this.value = normalized;
    }

    public String getFormatted() {
        return value.replaceFirst(
                "(\\d{3})(\\d{3})(\\d{3})(\\d{2})",
                "$1.$2.$3-$4"
        );
    }

    private boolean isValid(String cpf) {
        return cpf != null && cpf.matches("\\d{11}");
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Cpf cpf = (Cpf) o;
        return Objects.equals(value, cpf.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
    @Override
    public String toString() {
        return getFormatted();
    }

}
