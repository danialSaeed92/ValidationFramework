package cs525.annotations.Validations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DateTime {
  String format() default "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
}