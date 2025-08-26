package com.shyamfurniture.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ImageNameValidator implements ConstraintValidator<ImageNameValid,String>{
    private boolean isRequired;


    @Override
    public void initialize(ImageNameValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        //this is custom logic for annotation
        if (s == null || s.isBlank()) {
            return true;
        }

        // check extension
        String lower = s.toLowerCase();
        return lower.matches("[a-zA-Z0-9-_]+\\.(png|jpg|jpeg|gif)");

    }
}
