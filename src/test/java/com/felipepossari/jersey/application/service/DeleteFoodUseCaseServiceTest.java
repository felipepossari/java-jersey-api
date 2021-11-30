package com.felipepossari.jersey.application.service;

import com.felipepossari.jersey.application.domain.Food;
import com.felipepossari.jersey.application.exception.ApplicationErrorReason;
import com.felipepossari.jersey.application.exception.ResourceNotFoundException;
import com.felipepossari.jersey.application.port.out.FoodRepositoryPort;
import com.felipepossari.jersey.base.domain.FoodTestBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.felipepossari.jersey.base.DefaultConstants.FOOD_ID;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteFoodUseCaseServiceTest {

    @Mock
    FoodRepositoryPort repositoryPort;

    @InjectMocks
    DeleteFoodUseCaseService service;

    @Test
    void deleteShouldDeleteFoodIfIdIsValid() {
        Food food = FoodTestBuilder.aFood().build();

        given(repositoryPort.readyById(FOOD_ID)).willReturn(Optional.of(food));
        given(repositoryPort.delete(food)).willReturn(true);

        service.delete(FOOD_ID);

        verify(repositoryPort, times(1)).delete(food);
    }

    @Test
    void deleteShouldNotDeleteFoodIfItIsNotFound() {
        given(repositoryPort.readyById(FOOD_ID)).willReturn(Optional.empty());

        ResourceNotFoundException ex = Assertions.assertThrows(ResourceNotFoundException.class,
                () -> service.delete(FOOD_ID));

        Assertions.assertEquals(ApplicationErrorReason.RESOURCE_NOT_FOUND, ex.getApplicationErrorReason());
        verify(repositoryPort, times(0)).delete(any(Food.class));
    }

}
