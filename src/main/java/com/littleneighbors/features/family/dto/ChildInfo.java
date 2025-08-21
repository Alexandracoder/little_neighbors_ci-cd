package com.littleneighbors.features.family.dto;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.query.sql.internal.ParameterRecognizerImpl;

import java.time.Period;

@Data
@AllArgsConstructor
public class ChildInfo {
    private  Long id;
    private String gender;
    private  Integer age;

    public static  ChildInfo fromEntity(com.littleneighbors.features.child.model.Child child) {
        int age = Period.between(child.getBirthDate(), java.time.LocalDate.now()).getYears();
        return new ChildInfo(child.getId(), child.getGender(),age );

    }
}
