package com.betting.backend.Utils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.List;

@Converter
public class ListToStringConverter implements AttributeConverter<List<String>, String> {

    private static final String SPLIT_CHAR = "|";

    @Override
    public String convertToDatabaseColumn(List<String> list) {
        return list != null ? String.join(SPLIT_CHAR, list) : "";
    }

    @Override
    public List<String> convertToEntityAttribute(String joined) {
        return joined != null ? Arrays.asList(joined.split("\\Q" + SPLIT_CHAR + "\\E")) : List.of();
    }
}
