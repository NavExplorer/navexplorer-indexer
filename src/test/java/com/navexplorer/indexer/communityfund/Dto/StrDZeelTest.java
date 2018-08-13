package com.navexplorer.indexer.communityfund.Dto;

import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import java.util.Set;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;

public class StrDZeelTest {
    @Test
    public void it_can_set_and_get_amount() {
        Double amount = 50000.00;

        StrDZeel strDZeel = new StrDZeel();
        strDZeel.setAmount(amount);

        assertThat(strDZeel.getAmount()).isEqualTo(amount);
    }

    @Test
    public void it_can_set_and_get_address() {
        String address = "ADDRESS";

        StrDZeel strDZeel = new StrDZeel();
        strDZeel.setAddress(address);

        assertThat(strDZeel.getAddress()).isEqualTo(address);
    }

    @Test
    public void it_can_set_and_get_deadline() {
        Integer deadline = 197338480;

        StrDZeel strDZeel = new StrDZeel();
        strDZeel.setDeadline(deadline);

        assertThat(strDZeel.getDeadline()).isEqualTo(deadline);
    }

    @Test
    public void it_can_set_and_get_description() {
        String description = "Comunity Fund Proposal Description";

        StrDZeel strDZeel = new StrDZeel();
        strDZeel.setDescription(description);

        assertThat(strDZeel.getDescription()).isEqualTo(description);
    }

    @Test
    public void it_requires_the_amount_property_to_be_not_null() {
        StrDZeel strDZeel = new StrDZeel();

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<StrDZeel>> constraintViolations = validator.validate(strDZeel);
        Predicate<ConstraintViolation> predicate = v -> v.getPropertyPath().toString().equals("amount") && v.getMessage().equals("may not be null");

        assertThat(constraintViolations.stream().anyMatch(predicate)).isTrue();
    }

    @Test
    public void it_requires_the_address_property_to_be_not_null() {
        StrDZeel strDZeel = new StrDZeel();

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<StrDZeel>> constraintViolations = validator.validate(strDZeel);
        Predicate<ConstraintViolation> predicate = v -> v.getPropertyPath().toString().equals("address") && v.getMessage().equals("may not be null");

        assertThat(constraintViolations.stream().anyMatch(predicate)).isTrue();
    }

    @Test
    public void it_requires_the_deadline_property_to_be_not_null() {
        StrDZeel strDZeel = new StrDZeel();

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<StrDZeel>> constraintViolations = validator.validate(strDZeel);
        Predicate<ConstraintViolation> predicate = v -> v.getPropertyPath().toString().equals("deadline") && v.getMessage().equals("may not be null");

        assertThat(constraintViolations.stream().anyMatch(predicate)).isTrue();
    }

    @Test
    public void it_requires_the_description_property_to_be_not_null() {
        StrDZeel strDZeel = new StrDZeel();

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<StrDZeel>> constraintViolations = validator.validate(strDZeel);
        Predicate<ConstraintViolation> predicate = v -> v.getPropertyPath().toString().equals("description") && v.getMessage().equals("may not be null");

        assertThat(constraintViolations.stream().anyMatch(predicate)).isTrue();
    }
}
