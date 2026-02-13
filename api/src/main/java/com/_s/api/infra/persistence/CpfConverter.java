package com._s.api.infra.persistence;

import com._s.api.domain.valueobject.Cpf;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CpfConverter implements AttributeConverter<Cpf, String> {
    @Override
    public String convertToDatabaseColumn(Cpf cpf) {
        return cpf == null ? null : cpf.getValue();
    }

    @Override
    public Cpf convertToEntityAttribute(String value) {
        return value == null ? null : new Cpf(value);
    }
}
