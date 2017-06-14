package cs525.annotations.Validator;
import java.util.List;
import java.util.Map;

import cs525.exceptions.ErrorMessage;

public interface Validator<T> {
    public Map<Boolean, List<ErrorMessage>> validate(T t);
}