package is.stma.judgebean.beanpoll.util;

public class PasswordUtility {

    private static final int MINIMUM_LENGTH = 12;
    private static final int MAXIMUM_LENGTH = 36;
    private static final String ALLOWED_PASSWORDS = "^[\\p{Alnum}\\p{Punct}]{"
            + MINIMUM_LENGTH + "," + MAXIMUM_LENGTH + "}$";

    public static boolean meetsRequirements(String password) {
        return password.matches(ALLOWED_PASSWORDS);
    }

}
