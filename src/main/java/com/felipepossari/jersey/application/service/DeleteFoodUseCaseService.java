package com.felipepossari.jersey.application.service;

import com.felipepossari.jersey.application.domain.Food;
import com.felipepossari.jersey.application.exception.ResourceNotFoundException;
import com.felipepossari.jersey.application.port.in.DeleteFoodUseCase;
import com.felipepossari.jersey.application.port.out.FoodRepositoryPort;
import jakarta.inject.Inject;
import org.jvnet.hk2.annotations.Service;

import static com.felipepossari.jersey.application.exception.ApplicationErrorReason.RESOURCE_NOT_FOUND;

@Service
public class DeleteFoodUseCaseService implements DeleteFoodUseCase {

    private final FoodRepositoryPort foodRepositoryPort;

    @Inject
    public DeleteFoodUseCaseService(FoodRepositoryPort foodRepositoryPort) {
        this.foodRepositoryPort = foodRepositoryPort;
    }

    @Override
    public void delete(String id) {
        Food food = retrieveFood(id);
        foodRepositoryPort.delete(food);
    }

    private Food retrieveFood(String id){
        return foodRepositoryPort.readyById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND));
    }
}
