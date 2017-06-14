package cs525.annotations.Validator;

import cs525.annotations.Validations.DateTime;
import cs525.annotations.Validations.Email;
import cs525.annotations.Validations.Length;
import cs525.annotations.Validations.NotNull;
import cs525.annotations.Validations.Password;
import cs525.annotations.Validations.Url;
import cs525.exceptions.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

/**
 * 
 * @author Daniyal Saeed
 *
 * @param <T>
 * @version 1.0.0
 * @since 2017
 *        <h1>Advance Software Development</h1>
 *        <h2>Field Validator</h2>
 *        <p>
 *        Custom field validator handler to validate fields like email
 *        address,url,date & time , length of a field
 *        </p>
 */

public class FieldValidator<T> implements Validator<T> {

	private static final String CAUSED_MESSAGE = "Caused by invalid field value";

	public Map<Boolean, List<ErrorMessage>> validate(T t) {
		Map map = new HashMap();
		List<ErrorMessage> errorMessages = new ArrayList<ErrorMessage>();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		boolean result = true;
		Class clazz = t.getClass();
		for (Field field : clazz.getDeclaredFields()) {
			for (Annotation annotation : field.getAnnotations()) {
				field.setAccessible(true);
				Object obj = null;
				Pattern pattern;
				Matcher matcher;
				try {
					obj = field.get(t);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}

				if (annotation instanceof NotNull) {
					if (obj == null) {
						result = false;
						errorMessages.add(new ErrorMessage(ErrorSource.FIELD_ERROR, field.getName(),
								String.format("%s should not be null", field.getName()), CAUSED_MESSAGE));
					}
				}

				if (annotation instanceof NotNull) {
					if (obj != null && obj.toString().isEmpty()) {
						result = false;
						errorMessages.add(new ErrorMessage(ErrorSource.FIELD_ERROR, field.getName(),
								String.format("%s should not be empty", field.getName()), CAUSED_MESSAGE));
					}
				}

				// // Validating Length
				if (annotation instanceof Length) {
					if (obj != null) {
						int len = obj.toString().length();
						Length length = (Length) annotation;
						if (len < length.min() || len > length.max()) {
							errorMessages.add(new ErrorMessage(ErrorSource.FIELD_ERROR, field.getName(),
									String.format("%s should be %d to %d characters", field.getName(), length.min(),
											length.max()),
									CAUSED_MESSAGE));
							result = false;
						}
					}
				}

				// Email Validation
				if (annotation instanceof Email) {
					if (obj != null) {
						if (!obj.toString().isEmpty()) {
							String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
									+ "[A-Za-z0-9-]+(\\.[a-z]{2,3}+)*(\\.[a-z]{2,3})$";
							pattern = Pattern.compile(EMAIL_REGEX);
							matcher = pattern.matcher(obj.toString());
							if (!matcher.matches()) {
								errorMessages.add(new ErrorMessage(ErrorSource.FIELD_ERROR, field.getName(),
										String.format("%s is invalid", field.getName()), CAUSED_MESSAGE));
								result = false;
							}
						}
					}
				}

				// Url Validation
				if (annotation instanceof Url) {
					if (obj != null) {
						if (!obj.toString().isEmpty()) {
							String WEBSITE_REGEX = "^(http(s{0,1}):\\/\\/)?(www.)?([a-zA-Z0-9]+).[a-zA-Z0-9]*.[a-z]{3}.?([a-z]+)?$";
							pattern = Pattern.compile(WEBSITE_REGEX);
							matcher = pattern.matcher(obj.toString());
							if (!matcher.matches()) {
								errorMessages.add(new ErrorMessage(ErrorSource.FIELD_ERROR, field.getName(),
										String.format("%s url is invalid", field.getName()), CAUSED_MESSAGE));
								result = false;
							}
						}
					}
				}

				// Date Format Validation
				if (annotation instanceof DateTime) {
					if (obj != null) {
						if (!obj.toString().isEmpty()) {
							DateTime dateTime = (DateTime) annotation;
							SimpleDateFormat sdf = new SimpleDateFormat(dateTime.format());
							sdf.setLenient(false);
							try {
								sdf.parse(obj.toString());
							} catch (ParseException e) {
								errorMessages.add(new ErrorMessage(ErrorSource.FIELD_ERROR, field.getName(),
										String.format("%s should be in %s format", field.getName(), dateTime.format()),
										CAUSED_MESSAGE));
								result = false;
							}
						}
					}
				}

				// Number Validation
				if (annotation instanceof Number) {
					if (obj != null) {
						if (!obj.toString().isEmpty()) {
							String NUMBER_REGEX = "[-+]?([0-9]*\\.[0-9]+|[0-9]+)";
							pattern = Pattern.compile(NUMBER_REGEX);
							matcher = pattern.matcher(obj.toString());
							if (!matcher.matches()) {
								errorMessages.add(new ErrorMessage(ErrorSource.FIELD_ERROR, field.getName(),
										String.format("%s is not a number", field.getName()), CAUSED_MESSAGE));
								result = false;
							}
						}
					}
				}

				// PASSWORD validation
				if (annotation instanceof Password) {
					if (obj != null) {
						if (!obj.toString().isEmpty()) {
							String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).*$";
							pattern = Pattern.compile(PASSWORD_REGEX);
							matcher = pattern.matcher(obj.toString());
							System.out.println("matcher:" + matcher);
							System.out.println("matcher match:" + matcher.matches());
							System.out.println("Object:" + obj.toString());
							if (!matcher.matches()) {
								errorMessages.add(new ErrorMessage(ErrorSource.FIELD_ERROR, field.getName(),
										String.format("%s is not a valid password", field.getName()), CAUSED_MESSAGE));
								result = false;
							}
						}
					}
				}

				String json = null;
				if (!result) {

					try {
						json = ow.writeValueAsString(errorMessages);
					} catch (JsonProcessingException e) {
						e.printStackTrace();
					}
					System.out.println("Validation Errors:" + json);

				}
				map.put(result, errorMessages);
				System.out.println("Validation Result:" + result);

			}

		}

		return map;
	}
}