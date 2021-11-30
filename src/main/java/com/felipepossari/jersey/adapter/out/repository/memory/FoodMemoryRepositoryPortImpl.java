package com.felipepossari.jersey.adapter.out.repository.memory;

import com.felipepossari.jersey.application.domain.Food;
import com.felipepossari.jersey.application.domain.FoodType;
import com.felipepossari.jersey.application.port.out.FoodRepositoryPort;
import org.jvnet.hk2.annotations.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public Optional<Food> readyById(String id) {
        return foods.stream()
                .filter(food -> food.getId().equals(id))
                .findFirst();
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

    @Override
    public List<Food> readByType(FoodType type) {
        return foods.stream().filter(food -> food.getType().name().equals(type.name()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Food> readByName(String name) {
        return foods.stream().filter(food -> food.getName().equalsIgnoreCase(name.trim()))
                .findFirst();
    }

    @Override
    public Optional<Food> readByNameAndIdNot(String name, String id) {
        return foods.stream()
                .filter(food -> food.getName().equalsIgnoreCase(name.trim()) && !food.getId().equals(id))
                .findFirst();
    }
}
