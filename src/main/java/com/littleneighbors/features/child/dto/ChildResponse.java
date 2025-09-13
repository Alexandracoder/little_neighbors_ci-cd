package com.littleneighbors.features.child.dto;

import com.littleneighbors.features.child.entity.Gender;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
import java.time.Period;


@Data
@Builder
public class ChildResponse {
    private Long id;
    private Gender gender;
    private LocalDate birthDate;
    private Integer age;
    private Long familyId;

    public int getAge() {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}
