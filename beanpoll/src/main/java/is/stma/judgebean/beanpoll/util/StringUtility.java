package is.stma.judgebean.beanpoll.util;

import javax.validation.ValidationException;

public class StringUtility {

    private static final String REGEX_VALID_CHARACTERS = "[\\p{Alnum}\\p{Punct}]+( [\\p{Alnum}\\p{Punct}]+)*";
    private static final String REGEX_WHITESPACE = "[\\p{Space}]*";
    private static final String INVALID_CHARACTERS = "allowed characters are alphanumerics and punctuation; single spaces are allowed between words";

    public static void checkString(String string) throws ValidationException {

        // Allow only valid characters (alphanumeric, punctuation, and single space)
        if (!string.matches(REGEX_VALID_CHARACTERS)) {
            throw new ValidationException(INVALID_CHARACTERS);
        }
    }

    public static String removeWhitespace(String string) {
        return string.replaceAll(REGEX_WHITESPACE, "");
    }
}
