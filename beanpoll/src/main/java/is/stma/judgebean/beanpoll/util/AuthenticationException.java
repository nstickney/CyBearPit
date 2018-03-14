package is.stma.judgebean.beanpoll.util;

public class AuthenticationException extends Exception {

    public static final String LOGIN_BLANK = "Username and password are required";
    public static final String LOGIN_INCORRECT = "Username or password is incorrect";
    private static final String LOGIN_FAILURE = "Authentication failed";

    private String message = LOGIN_FAILURE;

    public AuthenticationException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
