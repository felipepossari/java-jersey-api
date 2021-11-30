package com.felipepossari.jersey.application.service;

import com.felipepossari.jersey.application.domain.Food;
import com.felipepossari.jersey.application.exception.ResourceNotFoundException;
import com.felipepossari.jersey.application.exception.ResourceRegisteredException;
import com.felipepossari.jersey.application.port.in.UpdateFoodUseCase;
import com.felipepossari.jersey.application.port.out.FoodRepositoryPort;
import jakarta.inject.Inject;
import org.jvnet.hk2.annotations.Service;

import java.util.logging.Logger;

import static com.felipepossari.jersey.application.exception.ApplicationErrorReason.RESOURCE_NOT_FOUND;
import static com.felipepossari.jersey.application.exception.ApplicationErrorReason.RESOURCE_REGISTERED;

@Service
public class UpdateFoodUseCaseService implements UpdateFoodUseCase {

    private static final Logger log = Logger.getLogger(UpdateFoodUseCaseService.class.getName());
    private final FoodRepositoryPort foodRepositoryPort;

    @Inject
    public UpdateFoodUseCaseService(FoodRepositoryPort foodRepositoryPort) {
        this.foodRepositoryPort = foodRepositoryPort;
    }

    @Override
    public Food update(Food food) {
        Food currentFood = retrieveFood(food.getId());
        validateName(food.getName(), food.getId());
        currentFood.setName(food.getName());
        currentFood.setWeight(food.getWeight());
        currentFood.setType(food.getType());

        return foodRepositoryPort.update(currentFood);
    }

    private void validateName(String name, String id) {
        if (foodRepositoryPort.readByNameAndIdNot(name, id).isPresent()) {
            log.warning(RESOURCE_REGISTERED.getMessage());
            throw new ResourceRegisteredException(RESOURCE_REGISTERED);
        }
    }

    private Food retrieveFood(String id) {
        return foodRepositoryPort.readyById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND));
    }
}
