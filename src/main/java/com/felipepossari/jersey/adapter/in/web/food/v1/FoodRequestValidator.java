package com.felipepossari.jersey.adapter.in.web.food.v1;

import com.felipepossari.jersey.adapter.in.web.food.v1.exception.FoodApiErrorReason;
import com.felipepossari.jersey.adapter.in.web.food.v1.request.FoodRequest;
import com.felipepossari.jersey.application.domain.FoodType;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.jvnet.hk2.annotations.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static com.felipepossari.jersey.adapter.in.web.food.v1.exception.FoodApiErrorReason.*;

@Service
public class FoodRequestValidator {

    private static final Logger log = Logger.getLogger(FoodRequestValidator.class.getName());
    private static final int FOOD_NAME_MAX_LENGTH = 50;

    public List<FoodApiErrorReason> validate(FoodRequest request) {
        log.info("Validating request");
        List<FoodApiErrorReason> errors = new ArrayList<>();
        validateName(request, errors);
        validateWeight(request, errors);
        validateFoodType(request, errors);

        return errors;
    }

    private void validateFoodType(FoodRequest request, List<FoodApiErrorReason> errors) {
        if (ObjectUtils.isEmpty(request.getType())) {
            log.warning(FOOD_TYPE_INVALID.getMessage());
            errors.add(FOOD_TYPE_INVALID);
        } else {
            try {
                FoodType.valueOf(request.getType());
            } catch (IllegalArgumentException ex) {
                log.warning(FOOD_TYPE_INVALID.getMessage());
                errors.add(FOOD_TYPE_INVALID);
            }
        }
    }

    private void validateWeight(FoodRequest request, List<FoodApiErrorReason> errors) {
        if (request.getWeight() <= 0) {
            log.warning(FOOD_WEIGHT_INVALID.getMessage());
            errors.add(FOOD_WEIGHT_INVALID);
        }
    }

    private void validateName(FoodRequest request, List<FoodApiErrorReason> errors) {
        if (StringUtils.isEmpty(request.getName())) {
            log.warning(FOOD_NAME_EMPTY.getMessage());
            errors.add(FOOD_NAME_EMPTY);
        } else if (request.getName().length() > FOOD_NAME_MAX_LENGTH) {
            log.warning(FOOD_NAME_INVALID_LENGTH.getMessage());
            errors.add(FOOD_NAME_INVALID_LENGTH);
        }
    }
}
