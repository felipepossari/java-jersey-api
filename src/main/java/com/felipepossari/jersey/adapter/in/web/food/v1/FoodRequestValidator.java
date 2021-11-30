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
        if(ObjectUtils.isEmpty(request.getType())){
            log.warning("Food type null or empty");
            errors.add(FoodApiErrorReason.FOOD_TYPE_INVALID);
        }else{
            try{
                FoodType.valueOf(request.getType());
            }catch (IllegalArgumentException ex){
                log.warning("Food type invalid");
                errors.add(FoodApiErrorReason.FOOD_TYPE_INVALID);
            }
        }
    }

    private void validateWeight(FoodRequest request, List<FoodApiErrorReason> errors) {
        if (request.getWeight() <= 0) {
            log.warning("Food weight invalid");
            errors.add(FoodApiErrorReason.FOOD_WEIGHT_INVALID);
        }
    }

    private void validateName(FoodRequest request, List<FoodApiErrorReason> errors) {
        if (StringUtils.isEmpty(request.getName())) {
            log.warning("Food name empty or null");
            errors.add(FoodApiErrorReason.FOOD_NAME_EMPTY);
        } else if (request.getName().length() > FOOD_NAME_MAX_LENGTH) {
            log.warning("Food name length invalid");
            errors.add(FoodApiErrorReason.FOOD_NAME_INVALID_LENGTH);
        }
    }
}
