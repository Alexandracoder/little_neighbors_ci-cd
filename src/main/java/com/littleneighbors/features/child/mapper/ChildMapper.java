package com.littleneighbors.features.child.mapper;

import com.littleneighbors.features.child.dto.ChildRequest;
import com.littleneighbors.features.child.dto.ChildResponse;
import com.littleneighbors.features.child.model.Child;
import com.littleneighbors.features.child.model.Gender;
import com.littleneighbors.shared.mapper.GenericMapper;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;


@Builder
@Component
public class ChildMapper extends GenericMapper<ChildRequest, ChildResponse, Child> {

    @Override
    public Child fromRequest(@NotNull ChildRequest request) {
        return  Child.builder()
                .gender((request.getGender()))
                .birthDate(request.getBirthDate())
                .build();
    }
    @Override
    public ChildResponse toResponse(Child entity) {
        return ChildResponse.builder()
                .id(entity.getId())
                .gender((entity.getGender()))
                .birthDate(entity.getBirthDate())
                .age(entity.getBirthDate() != null ?
                        Period.between(entity.getBirthDate(), LocalDate.now()).getYears() : 0)
                .familyId(entity.getFamily() != null ? entity.getFamily().getId() : null)
                .build();
    }
}
