package com.felipepossari.jersey.application.service;

import com.felipepossari.jersey.application.domain.Food;
import com.felipepossari.jersey.application.port.in.ReadFoodUseCase;
import com.felipepossari.jersey.application.port.out.FoodRepositoryPort;
import jakarta.inject.Inject;
import org.jvnet.hk2.annotations.Service;

import java.util.List;

@Service
public class ReadFoodUseCaseService implements ReadFoodUseCase {

    private final FoodRepositoryPort foodRepositoryPort;

    @Inject
    public ReadFoodUseCaseService(FoodRepositoryPort foodRepositoryPort) {
        this.foodRepositoryPort = foodRepositoryPort;
    }

    @Override
    public List<Food> readAll() {
        return foodRepositoryPort.readAll();
    }

    @Override
    public Food readById(String id) {
        return foodRepositoryPort.readyById(id);
    }
}
