package com.felipepossari.jersey.application.service;

import com.felipepossari.jersey.application.domain.Food;
import com.felipepossari.jersey.application.exception.ApplicationErrorReason;
import com.felipepossari.jersey.application.exception.ResourceRegisteredException;
import com.felipepossari.jersey.application.port.out.FoodRepositoryPort;
import com.felipepossari.jersey.base.domain.FoodTestBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CreateFoodUseCaseServiceTest {

    @Mock
    FoodRepositoryPort repositoryPort;

    @InjectMocks
    CreateFoodUseCaseService service;

    @Test
    void createShouldCreateFoodIfNameIsValid() {
        Food food = FoodTestBuilder.aFood().build();
        Food expectedFood = FoodTestBuilder.aFood().build();

        given(repositoryPort.readByName(food.getName())).willReturn(Optional.empty());
        given(repositoryPort.create(food)).willReturn(expectedFood);

        Food actualFood = service.create(food);
        assertEquals(expectedFood, actualFood);
    }

    @Test
    void createShouldNotCreateFoodIfNameIsRegistered() {
        Food food = FoodTestBuilder.aFood().build();
        Food foodSameName = FoodTestBuilder.aFood().build();

        given(repositoryPort.readByName(food.getName())).willReturn(Optional.of(foodSameName));

        ResourceRegisteredException ex = assertThrows(ResourceRegisteredException.class,
                () -> service.create(food));

        assertEquals(ApplicationErrorReason.RESOURCE_REGISTERED, ex.getApplicationErrorReason());
        verify(repositoryPort, times(0)).create(any(Food.class));
    }
}
