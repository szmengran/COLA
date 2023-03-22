package com.szmengran.base.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.CompositionType;
import org.hibernate.validator.constraints.ConstraintComposition;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @Author Joe
 * @Date 2022/3/15 11:13 AM
 */
@ConstraintComposition(CompositionType.OR)
@Pattern(
		regexp = "1[3|4|5|7|8][0-9]\\d{8}"
)
@Documented
@Constraint(
		validatedBy = {}
)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@ReportAsSingleViolation
public @interface Phone {
	String message() default "手机号校验错误";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
