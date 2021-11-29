package com.felipepossari.jersey.application.service;

import com.felipepossari.jersey.application.domain.Food;
import com.felipepossari.jersey.application.port.in.UpdateFoodUseCase;
import com.felipepossari.jersey.application.port.out.FoodRepositoryPort;
import jakarta.inject.Inject;
import org.jvnet.hk2.annotations.Service;

@Service
public class UpdateFoodUseCaseService implements UpdateFoodUseCase {

    private final FoodRepositoryPort foodRepositoryPort;

    @Inject
    public UpdateFoodUseCaseService(FoodRepositoryPort foodRepositoryPort) {
        this.foodRepositoryPort = foodRepositoryPort;
    }

    @Override
    public Food update(Food food) {
        Food currentFood = foodRepositoryPort.readyById(food.getId());

        currentFood.setName(food.getName());
        currentFood.setWeight(food.getWeight());
        currentFood.setType(food.getType());

        return foodRepositoryPort.update(currentFood);
    }
}
