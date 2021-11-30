package com.felipepossari.jersey.application.service;

import com.felipepossari.jersey.application.domain.Food;
import com.felipepossari.jersey.application.domain.FoodType;
import com.felipepossari.jersey.application.exception.ApplicationErrorReason;
import com.felipepossari.jersey.application.exception.ResourceNotFoundException;
import com.felipepossari.jersey.application.port.out.FoodRepositoryPort;
import com.felipepossari.jersey.base.domain.FoodTestBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.felipepossari.jersey.base.DefaultConstants.FOOD_ID;
import static com.felipepossari.jersey.base.DefaultConstants.INVALID_FOOD_TYPE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ReadFoodUseCaseServiceTest {

    @Mock
    FoodRepositoryPort repositoryPort;

    @InjectMocks
    ReadFoodUseCaseService service;

    @ParameterizedTest
    @ValueSource(strings = {"", INVALID_FOOD_TYPE})
    void readAllShouldReadAllFoodsIfTypeIsInvalid(String type) {
        List<Food> foods = Collections.singletonList(FoodTestBuilder.aFood().build());

        given(repositoryPort.readAll()).willReturn(foods);

        List<Food> actualFoods = service.readAll(type);

        verify(repositoryPort, times(0)).readByType(any(FoodType.class));
    }

    @Test
    void readAllShouldReadFoodsByTypeIfTypeIsValid() {
        FoodType foodType = FoodType.MEAT;
        List<Food> foods = Collections.singletonList(FoodTestBuilder.aFood().build());

        given(repositoryPort.readByType(foodType)).willReturn(foods);

        List<Food> actualFoods = service.readAll(foodType.name());

        verify(repositoryPort, times(0)).readAll();
    }

    @Test
    void readByIdShouldReturnFoodIfItWasFound() {
        Food food = FoodTestBuilder.aFood().build();

        given(repositoryPort.readyById(FOOD_ID)).willReturn(Optional.of(food));

        Food actualFood = service.readById(FOOD_ID);

        Assertions.assertEquals(food, actualFood);
    }

    @Test
    void readByIdShouldReturnThrowAResourceNotFoundExceptionIfItWasNotFound() {
        given(repositoryPort.readyById(FOOD_ID)).willReturn(Optional.empty());

        ResourceNotFoundException ex = Assertions.assertThrows(ResourceNotFoundException.class,
                () -> service.readById(FOOD_ID));

        Assertions.assertEquals(ApplicationErrorReason.RESOURCE_NOT_FOUND, ex.getApplicationErrorReason());
    }
}
