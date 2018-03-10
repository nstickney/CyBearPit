package is.stma.judgebean.beanpoll.util;

import javax.validation.ValidationException;

public class RulesUtility {

    private static final String INVALID_CHARACTERS = "contains invalid characters; only alphanumeric characters and hyphens are allowed";

    public static void sanitizeString(String string) throws ValidationException {
        if (!string.matches("^[0-9a-zA-Z]*")) {
            throw new ValidationException(INVALID_CHARACTERS);
        }
    }
}
