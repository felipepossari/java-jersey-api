package com.felipepossari.jersey.application.service;

import com.felipepossari.jersey.application.domain.Food;
import com.felipepossari.jersey.application.exception.ApplicationErrorReason;
import com.felipepossari.jersey.application.exception.ResourceNotFoundException;
import com.felipepossari.jersey.application.exception.ResourceRegisteredException;
import com.felipepossari.jersey.application.port.out.FoodRepositoryPort;
import com.felipepossari.jersey.base.domain.FoodTestBuilder;
import org.junit.jupiter.api.Assertions;
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
class UpdateFoodUseCaseServiceTest {

    @Mock
    FoodRepositoryPort repositoryPort;

    @InjectMocks
    UpdateFoodUseCaseService service;

    @Test
    void updateShouldUpdateFoodIfNameIsValid() {
        Food foodToUpdate = FoodTestBuilder.aFoodUpdated().build();
        Food currentFood = FoodTestBuilder.aFood().build();

        given(repositoryPort.readyById(foodToUpdate.getId())).willReturn(Optional.of(currentFood));
        given(repositoryPort.readByNameAndIdNot(foodToUpdate.getName(), foodToUpdate.getId()))
                .willReturn(Optional.empty());
        given(repositoryPort.update(currentFood)).willReturn(currentFood);

        Food actualFood = service.update(foodToUpdate);

        assertEquals(currentFood, actualFood);
        verify(repositoryPort, times(1)).update(currentFood);
    }

    @Test
    void updateShouldNotUpdateFoodIfItIsNotFound() {
        Food foodToUpdate = FoodTestBuilder.aFoodUpdated().build();

        given(repositoryPort.readyById(foodToUpdate.getId())).willReturn(Optional.empty());

        ResourceNotFoundException ex = Assertions.assertThrows(ResourceNotFoundException.class,
                () -> service.update(foodToUpdate));

        Assertions.assertEquals(ApplicationErrorReason.RESOURCE_NOT_FOUND, ex.getApplicationErrorReason());
        verify(repositoryPort, times(0)).update(any(Food.class));
    }

    @Test
    void updateShouldNotUpdateFoodIfNameIsAlreadyRegistered() {
        Food foodToUpdate = FoodTestBuilder.aFoodUpdated().build();
        Food currentFood = FoodTestBuilder.aFood().build();
        Food foodSameName = FoodTestBuilder.aFood().build();

        given(repositoryPort.readyById(foodToUpdate.getId())).willReturn(Optional.of(currentFood));
        given(repositoryPort.readByNameAndIdNot(foodToUpdate.getName(), foodToUpdate.getId()))
                .willReturn(Optional.of(foodSameName));

        ResourceRegisteredException ex = assertThrows(ResourceRegisteredException.class,
                () -> service.update(foodToUpdate));

        Assertions.assertEquals(ApplicationErrorReason.RESOURCE_REGISTERED, ex.getApplicationErrorReason());
        verify(repositoryPort, times(0)).update(any(Food.class));
    }

}
