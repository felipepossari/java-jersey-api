package com.felipepossari.jersey.application.port.out;

import com.felipepossari.jersey.application.domain.Food;
import org.jvnet.hk2.annotations.Contract;

import java.util.List;

@Contract
public interface FoodRepositoryPort {

    Food create(Food food);

    List<Food> readAll();

    Food readyById(String id);

    Food update(Food foodUpdated);

    boolean delete(Food food);
}
