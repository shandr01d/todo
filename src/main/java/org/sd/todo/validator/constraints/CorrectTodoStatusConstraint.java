package org.sd.todo.validator.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CorrectTodoStatusValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface CorrectTodoStatusConstraint {
    String message() default "Invalid todo status";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}