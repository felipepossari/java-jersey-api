package com.felipepossari.jersey.application.service;

import com.felipepossari.jersey.application.domain.Food;
import com.felipepossari.jersey.application.domain.FoodType;
import com.felipepossari.jersey.application.exception.ResourceNotFoundException;
import com.felipepossari.jersey.application.port.in.ReadFoodUseCase;
import com.felipepossari.jersey.application.port.out.FoodRepositoryPort;
import jakarta.inject.Inject;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.jvnet.hk2.annotations.Service;

import java.util.List;

import static com.felipepossari.jersey.application.exception.ApplicationErrorReason.RESOURCE_NOT_FOUND;

@Service
public class ReadFoodUseCaseService implements ReadFoodUseCase {

    private final FoodRepositoryPort foodRepositoryPort;

    @Inject
    public ReadFoodUseCaseService(FoodRepositoryPort foodRepositoryPort) {
        this.foodRepositoryPort = foodRepositoryPort;
    }

    @Override
    public List<Food> readAll(String type) {
        if(!StringUtils.isEmpty(type) && EnumUtils.isValidEnum(FoodType.class, type)){
            return foodRepositoryPort.readByType(FoodType.valueOf(type));
        }
        return foodRepositoryPort.readAll();
    }

    @Override
    public Food readById(String id) {
        return retrieveFood(id);
    }

    private Food retrieveFood(String id){
        return foodRepositoryPort.readyById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND));
    }
}
