package com.felipepossari.jersey.application.domain;

import lombok.Getter;

@Getter
public enum FoodType {

    VEGETABLES("vegetables"),
    FRUITS("fruits"),
    GRAINS("grains"),
    MEAT("meat"),
    POULTRY("poultry"),
    FISH("fish"),
    SEAFOOD("seafood"),
    DAIRY_FOOD("dairy food");

    private String description;

    FoodType(String description) {
        this.description = description;
    }
}
