package cs525.annotations.Validations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;



/**
 * @author Daniyal Saeed
 * @since 2017
 * @category Custom Validation API
 *           <h1>Advance Software Development</h1>
 *           <h2>Null Validator</h2>
 *           <p>
 *           This is annotation to validate  fields in any
 *           application. No code required Just call this annotation on any text
 *           field and it will work
 *           </p>
 * @version 1.0.0 <br/>
 *          <p>
 *          All Rights Reserved
 *          </p>
 */

@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
	ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NotNull {

	String message() default "{This field is required}";
	
	
}
