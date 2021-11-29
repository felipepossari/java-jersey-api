package com.felipepossari.jersey.adapter.out.repository.memory;

import com.felipepossari.jersey.application.domain.Food;
import com.felipepossari.jersey.application.exception.ResourceNotFoundException;
import com.felipepossari.jersey.application.port.out.FoodRepositoryPort;
import org.jvnet.hk2.annotations.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FoodMemoryRepositoryPortImpl implements FoodRepositoryPort {

    private List<Food> foods = new ArrayList<>();

    @Override
    public Food create(Food food) {
        food.setId(UUID.randomUUID().toString());
        foods.add(food);
        return food;
    }

    @Override
    public List<Food> readAll() {
        return foods;
    }

    @Override
    public Food readyById(String id) {
        return foods.stream()
                .filter(food -> food.getId().equals(id))
                .findFirst().orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public Food update(Food foodUpdated) {
        delete(foodUpdated);
        foods.add(foodUpdated);
        return foodUpdated;
    }

    @Override
    public boolean delete(Food food) {
        return foods.removeIf(foodToDelete -> foodToDelete.getId().equals(food.getId()));
    }
}
