package com.felipepossari.jersey.base.request;

import com.felipepossari.jersey.adapter.in.web.food.v1.request.FoodRequest;
import com.felipepossari.jersey.base.DefaultConstants;

public class FoodRequestTestBuilder {

    private String name = DefaultConstants.FOOD_NAME;
    private double weight = DefaultConstants.FOOD_WEIGHT;
    private String type = DefaultConstants.FOOD_TYPE.name();

    private FoodRequestTestBuilder() {}

    public static FoodRequestTestBuilder aFoodRequest(){
        return new FoodRequestTestBuilder();
    }

    public FoodRequestTestBuilder name(String name) {
        this.name = name;
        return this;
    }

    public FoodRequestTestBuilder weight(double weight) {
        this.weight = weight;
        return this;
    }

    public FoodRequestTestBuilder type(String type) {
        this.type = type;
        return this;
    }

    public FoodRequest build(){
        return FoodRequest.builder()
                .name(name)
                .type(type)
                .weight(weight)
                .build();
    }
}
