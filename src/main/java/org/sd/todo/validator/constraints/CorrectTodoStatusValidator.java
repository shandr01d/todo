package org.sd.todo.validator.constraints;

import org.sd.todo.entity.Todo;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CorrectTodoStatusValidator implements ConstraintValidator<CorrectTodoStatusConstraint, String> {
    @Override
    public void initialize(CorrectTodoStatusConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(s == null) {
            return true;
        }

        try {
            Todo.Status.valueOf(s);
        } catch (IllegalArgumentException exception){
            return false;
        }
        return true;
    }
}
