package com.felipepossari.jersey.adapter.in.web.food.v1.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FoodTypeRequest {
    private String id;
    private String type;
}
