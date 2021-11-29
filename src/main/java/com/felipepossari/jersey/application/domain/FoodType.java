package com.felipepossari.jersey.application.domain;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FoodType {
    private String id;
    private String type;
}
