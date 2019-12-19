package org.example.common.utils;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Validator {

    public static <T> boolean validate(T bean) {
        if (bean == null) {
            return false;
        }
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        javax.validation.Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> violations = validator.validate(bean);
        if (violations.size() > 0) {
            StringBuilder builder = new StringBuilder();
            for (ConstraintViolation<T> violation : violations) {
                builder.append(violation.getPropertyPath()).append(": ").append(violation.getMessage()).append("; ");
            }
            log.info(builder.toString().trim());
            return false;
        }
        return true;
    }

}
