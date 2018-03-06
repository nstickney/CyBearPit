package is.stma.judgebean.util;

public class BusinessRuleException extends Exception {

    private final String message;

    public BusinessRuleException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
