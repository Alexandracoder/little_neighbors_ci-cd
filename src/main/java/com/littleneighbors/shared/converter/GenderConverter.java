package com.littleneighbors.shared.converter;

import com.littleneighbors.features.child.model.Gender;
import jakarta.persistence.AttributeConverter;

import static com.littleneighbors.features.child.model.Gender.FEMALE;
import static com.littleneighbors.features.child.model.Gender.MALE;

public class GenderConverter implements AttributeConverter<Gender, String> {

    @Override
    public String convertToDatabaseColumn(Gender gender) {
        if (gender == null) return null;
        switch(gender) {
            case MALE:
                return "M";
            case FEMALE:
                return "F";
            default:
                throw new IllegalArgumentException("Unknown " + gender);
        }

    }

    @Override
    public Gender convertToEntityAttribute(String dbData) {
            if (dbData == null) return null;
            switch (dbData) {
                case "M":
                    return MALE;
                case "F":
                    return FEMALE;
                default:
                    throw new IllegalArgumentException("Unknown " + dbData);
            }
        }
    }
