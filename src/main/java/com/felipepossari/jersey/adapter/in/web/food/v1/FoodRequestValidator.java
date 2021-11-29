package com.felipepossari.jersey.adapter.in.web.food.v1;

import com.felipepossari.jersey.adapter.in.web.food.v1.exception.FoodApiErrorReason;
import com.felipepossari.jersey.adapter.in.web.food.v1.request.FoodRequest;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.jvnet.hk2.annotations.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FoodRequestValidator {

    private static final int FOOD_NAME_MAX_LENGTH = 50;

    public List<FoodApiErrorReason> validate(FoodRequest request) {
        List<FoodApiErrorReason> errors = new ArrayList<>();

        if (StringUtils.isEmpty(request.getName())) {
            errors.add(FoodApiErrorReason.FOOD_NAME_EMPTY);
        } else if (request.getName().length() > FOOD_NAME_MAX_LENGTH) {
            errors.add(FoodApiErrorReason.FOOD_NAME_INVALID_LENGTH);
        }

        if (request.getWeight() <= 0) {
            errors.add(FoodApiErrorReason.FOOD_WEIGHT_INVALID);
        }

        if(ObjectUtils.isEmpty(request.getType())){
            errors.add(FoodApiErrorReason.FOOD_TYPE_INVALID);
        }

        return errors;
    }
}
