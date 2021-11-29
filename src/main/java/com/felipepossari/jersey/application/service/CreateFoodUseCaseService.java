package com.felipepossari.jersey.application.service;

import com.felipepossari.jersey.application.domain.Food;
import com.felipepossari.jersey.application.port.in.CreateFoodUseCase;
import com.felipepossari.jersey.application.port.out.FoodRepositoryPort;
import jakarta.inject.Inject;
import org.jvnet.hk2.annotations.Service;

@Service
public class CreateFoodUseCaseService implements CreateFoodUseCase {

    private final FoodRepositoryPort foodRepositoryPort;

    @Inject
    public CreateFoodUseCaseService(FoodRepositoryPort foodRepositoryPort) {
        this.foodRepositoryPort = foodRepositoryPort;
    }

    @Override
    public Food create(Food food) {
        return foodRepositoryPort.create(food);
    }
}
