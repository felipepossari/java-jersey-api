package com.felipepossari.jersey.application.port.in;

import com.felipepossari.jersey.application.domain.Food;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface CreateFoodUseCase {

    Food create(Food food);
}
