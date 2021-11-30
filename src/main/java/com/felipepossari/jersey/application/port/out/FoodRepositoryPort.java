package com.felipepossari.jersey.application.port.out;

import com.felipepossari.jersey.application.domain.Food;
import com.felipepossari.jersey.application.domain.FoodType;
import org.jvnet.hk2.annotations.Contract;

import java.util.List;
import java.util.Optional;

@Contract
public interface FoodRepositoryPort {

    Food create(Food food);

    List<Food> readAll();

    Optional<Food> readyById(String id);

    Food update(Food foodUpdated);

    boolean delete(Food food);

    List<Food> readByType(FoodType type);

    Optional<Food> readByName(String name);

    Optional<Food> readByNameAndIdNot(String name, String id);
}
