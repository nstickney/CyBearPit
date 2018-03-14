package is.stma.judgebean.beanpoll.controller.parameterizer;

import is.stma.judgebean.beanpoll.model.Parameter;

import java.util.ArrayList;
import java.util.List;

public class HTTPParameterizer {

    // DNS tag strings
    public static final String HTTP_QUERY = "HTTP_QUERY";
    public static final String HTTP_RESOLVER = "HTTP_RESOLVER";
    public static final String HTTP_HTTPS = "HTTP_HTTPS";

    // DNS default value strings
    private static final String HTTP_DEFAULT_QUERY = "www.baylor.edu";
    private static final String HTTP_DEFAULT_RESOLVER = null;
    private static final String HTTP_DEFAULT_HTTPS = Parameter.FALSE;

    public static List<Parameter> createParameters() {
        List<Parameter> parameters = new ArrayList<>();
        parameters.add(new Parameter(HTTP_QUERY, HTTP_DEFAULT_QUERY));
        parameters.add(new Parameter(HTTP_RESOLVER, HTTP_DEFAULT_RESOLVER));
        parameters.add(new Parameter(HTTP_HTTPS, HTTP_DEFAULT_HTTPS));
        return parameters;
    }

}
