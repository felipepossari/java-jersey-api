package com.felipepossari.jersey.adapter.in.web.food.v1.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class FoodTypeResponse {
    private String id;
    private String type;
}
