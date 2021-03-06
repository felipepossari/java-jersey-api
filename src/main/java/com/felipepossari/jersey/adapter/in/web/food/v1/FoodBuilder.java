package com.felipepossari.jersey.adapter.in.web.food.v1;

import com.felipepossari.jersey.adapter.in.web.food.v1.request.FoodRequest;
import com.felipepossari.jersey.adapter.in.web.food.v1.response.FoodResponse;
import com.felipepossari.jersey.application.domain.Food;
import com.felipepossari.jersey.application.domain.FoodType;
import org.jvnet.hk2.annotations.Service;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static com.felipepossari.jersey.adapter.AdapterConstans.ENDPOINT_FOOD;

@Service
public class FoodBuilder {

    public Food buildFood(FoodRequest request) {
        return Food.builder()
                .name(request.getName())
                .weight(request.getWeight())
                .type(FoodType.valueOf(request.getType()))
                .build();
    }

    public URI buildCreatedUri(Food food) {
        return URI.create(ENDPOINT_FOOD + "/" + food.getId());
    }

    public List<FoodResponse> buildResponse(List<Food> foods) {
        return foods.stream()
                .map(this::buildResponse)
                .collect(Collectors.toList());
    }

    public FoodResponse buildResponse(Food food) {
        return FoodResponse.builder()
                .id(food.getId())
                .name(food.getName())
                .type(food.getType().name())
                .weight(food.getWeight())
                .build();
    }

    public Food buildFood(FoodRequest request, String id) {
        return Food.builder()
                .id(id)
                .type(FoodType.valueOf(request.getType()))
                .weight(request.getWeight())
                .name(request.getName())
                .build();
    }
}
