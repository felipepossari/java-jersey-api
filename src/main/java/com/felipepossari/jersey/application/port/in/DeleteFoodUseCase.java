package com.felipepossari.jersey.application.port.in;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface DeleteFoodUseCase {
    void delete(String id);
}
