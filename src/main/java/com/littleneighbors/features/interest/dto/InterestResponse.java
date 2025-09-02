package com.littleneighbors.features.interest.dto;


import com.littleneighbors.features.interest.model.InterestType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InterestResponse {
    private  Long id;
    private  String  name;
    private InterestType type;
}
