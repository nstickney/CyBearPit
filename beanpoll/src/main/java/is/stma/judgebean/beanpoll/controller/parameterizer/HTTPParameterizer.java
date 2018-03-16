package is.stma.judgebean.beanpoll.controller.parameterizer;

import is.stma.judgebean.beanpoll.model.Parameter;

import java.util.ArrayList;
import java.util.List;

public class HTTPParameterizer {

    // DNS tag strings
    public static final String HTTP_RESOLVER = "HTTP_RESOLVER";

    // DNS default value strings
    public static final String HTTP_DEFAULT_RESOLVER = null;

    public static List<Parameter> createParameters() {
        List<Parameter> parameters = new ArrayList<>();
        parameters.add(new Parameter(HTTP_RESOLVER, HTTP_DEFAULT_RESOLVER));
        return parameters;
    }

}
