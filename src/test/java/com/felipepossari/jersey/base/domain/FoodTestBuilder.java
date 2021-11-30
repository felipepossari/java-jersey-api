package com.felipepossari.jersey.base.domain;

import com.felipepossari.jersey.application.domain.Food;
import com.felipepossari.jersey.application.domain.FoodType;
import com.felipepossari.jersey.base.DefaultConstants;

public class FoodTestBuilder {

    private String id = DefaultConstants.FOOD_ID;
    private String name = DefaultConstants.FOOD_NAME;
    private double weight = DefaultConstants.FOOD_WEIGHT;
    private FoodType type = DefaultConstants.FOOD_TYPE;

    private FoodTestBuilder() {
    }

    public static FoodTestBuilder aFood() {
        return new FoodTestBuilder();
    }

    public FoodTestBuilder id(String id) {
        this.id = id;
        return this;
    }

    public FoodTestBuilder name(String name) {
        this.name = name;
        return this;
    }

    public FoodTestBuilder weight(double weight) {
        this.weight = weight;
        return this;
    }

    public FoodTestBuilder type(FoodType type) {
        this.type = type;
        return this;
    }

    public Food build() {
        return Food.builder()
                .id(id)
                .name(name)
                .type(type)
                .weight(weight)
                .build();
    }
}
