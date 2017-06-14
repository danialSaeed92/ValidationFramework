package cs525.annotations.Validations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;



/**
 * @author Daniyal Saeed
 * @since 2017
 * @category Custom Validation API
 *           <h1>Advance Software Development</h1>
 *           <h2>Email Validator</h2>
 *           <p>
 *           This is annotation to validate Email Address field in any
 *           application. No code required Just call this annotation on any text
 *           field and it will work
 *           </p>
 * @version 1.0.0 <br/>
 *          <p>
 *          All Rights Reserved
 *          </p>
 */




@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Email {
}