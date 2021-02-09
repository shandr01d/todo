package org.sd.todo.validator.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateFormatValidator implements ConstraintValidator<DateFormatConstraint, String> {
    String format;
    Object payload;

    @Override
    public void initialize(DateFormatConstraint constraintAnnotation) {
        this.format = constraintAnnotation.format();
        this.payload = constraintAnnotation.payload();
    }

    @Override
    public boolean isValid(String dateStr, ConstraintValidatorContext constraintValidatorContext) {
        DateFormat sdf = new SimpleDateFormat(format);
        sdf.setLenient(false);
        try {
            sdf.parse(dateStr);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
}
