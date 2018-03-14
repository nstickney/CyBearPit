package is.stma.judgebean.beanpoll.controller.parameterizer;

import is.stma.judgebean.beanpoll.model.Parameter;

import java.util.ArrayList;
import java.util.List;

public class DNSParameterizer {

    // DNS tag strings
    public static final String DNS_QUERY = "DNS_QUERY";
    public static final String DNS_RECORD_TYPE = "DNS_RECORD_TYPE";
    public static final String DNS_TCP = "DNS_TCP";
    public static final String DNS_RECURSIVE = "DNS_RECURSIVE";
    public static final String DNS_TIMEOUT = "DNS_TIMEOUT";
    public static final String DNS_EXPECTED = "DNS_EXPECTED";

    // DNS default value strings
    private static final String DNS_DEFAULT_QUERY = "baylor.edu";
    private static final String DNS_DEFAULT_RECORD_TYPE = "A";
    private static final String DNS_DEFAULT_TCP = Parameter.TRUE;
    private static final String DNS_DEFAULT_RECURSIVE = Parameter.FALSE;
    private static final String DNS_DEFAULT_TIMEOUT = "3";
    private static final String DNS_DEFAULT_EXPECTED = "129.62.3.230";


    public static List<Parameter> createParameters() {
        List<Parameter> parameters = new ArrayList<>();
        parameters.add(new Parameter(DNS_QUERY, DNS_DEFAULT_QUERY));
        parameters.add(new Parameter(DNS_RECORD_TYPE, DNS_DEFAULT_RECORD_TYPE));
        parameters.add(new Parameter(DNS_TCP, DNS_DEFAULT_TCP));
        parameters.add(new Parameter(DNS_RECURSIVE, DNS_DEFAULT_RECURSIVE));
        parameters.add(new Parameter(DNS_TIMEOUT, DNS_DEFAULT_TIMEOUT));
        parameters.add(new Parameter(DNS_EXPECTED, DNS_DEFAULT_EXPECTED));
        return parameters;
    }

}
