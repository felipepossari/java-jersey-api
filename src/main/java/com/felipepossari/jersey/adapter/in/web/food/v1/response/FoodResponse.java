package com.felipepossari.jersey.adapter.in.web.food.v1.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FoodResponse {
    private String id;
    private String name;
    private double weight;
    private String type;
}
