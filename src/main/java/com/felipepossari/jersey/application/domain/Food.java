package com.felipepossari.jersey.application.domain;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Food {
    private String id;
    private String name;
    private double weight;
    private FoodType type;
}
