package com.felipepossari.jersey.application.service;

import com.felipepossari.jersey.application.domain.Food;
import com.felipepossari.jersey.application.exception.ResourceRegisteredException;
import com.felipepossari.jersey.application.port.in.CreateFoodUseCase;
import com.felipepossari.jersey.application.port.out.FoodRepositoryPort;
import jakarta.inject.Inject;
import org.jvnet.hk2.annotations.Service;

import java.util.logging.Logger;

import static com.felipepossari.jersey.application.exception.ApplicationErrorReason.RESOURCE_REGISTERED;

@Service
public class CreateFoodUseCaseService implements CreateFoodUseCase {

    private static final Logger log = Logger.getLogger(CreateFoodUseCaseService.class.getName());
    private final FoodRepositoryPort foodRepositoryPort;

    @Inject
    public CreateFoodUseCaseService(FoodRepositoryPort foodRepositoryPort) {
        this.foodRepositoryPort = foodRepositoryPort;
    }

    @Override
    public Food create(Food food) {
        validateName(food.getName());
        return foodRepositoryPort.create(food);
    }

    private void validateName(String name) {
        if (foodRepositoryPort.readByName(name).isPresent()) {
            log.warning(RESOURCE_REGISTERED.getMessage());
            throw new ResourceRegisteredException(RESOURCE_REGISTERED);
        }
    }
}