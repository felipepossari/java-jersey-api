package com.felipepossari.jersey.application.port.in;

import com.felipepossari.jersey.application.domain.Food;
import org.jvnet.hk2.annotations.Contract;

import java.util.List;
@Contract
public interface ReadFoodUseCase {

    List<Food> readAll(String type);

    Food readById(String id);
}
