package com.littleneighbors.features.family.dto;

import com.littleneighbors.features.child.entity.Child;
import com.littleneighbors.features.child.entity.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Period;

@Data
@AllArgsConstructor
public class ChildInfo {
    private  Long id;
    private Gender gender;
    private Integer age;

    public static  ChildInfo fromEntity(Child child) {
        int age = Period.between(child.getBirthDate(), java.time.LocalDate.now()).getYears();
        return new ChildInfo(child.getId(), child.getGender(),age );

    }
}
