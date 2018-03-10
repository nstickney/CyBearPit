package is.stma.judgebean.beanpoll.util;

public class AuthenticationException extends Exception {

    public static final String LOGIN_FAILURE = "Authentication failed";
    public static final String LOGIN_BLANK = "Username and password are required";
    public static final String LOGIN_INCORRECT = "Username or password is incorrect";

    @Override
    public String getMessage() {
        return LOGIN_FAILURE;
    }
}
