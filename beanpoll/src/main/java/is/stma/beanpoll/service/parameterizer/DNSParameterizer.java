/*
 * Copyright 2018 Nathaniel Stickney
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package is.stma.beanpoll.service.parameterizer;

import is.stma.beanpoll.model.Parameter;

import java.util.ArrayList;
import java.util.List;

public class DNSParameterizer {

    // DNS tag strings
    public static final String DNS_QUERY = "DNS_QUERY";
    public static final String DNS_RECORD_TYPE = "DNS_RECORD_TYPE";
    public static final String DNS_TCP = "DNS_TCP";
    public static final String DNS_RECURSIVE = "DNS_RECURSIVE";
    public static final String DNS_EXPECTED = "DNS_EXPECTED";

    // DNS default value strings
    public static final String DNS_DEFAULT_QUERY = "baylor.edu";
    public static final String DNS_DEFAULT_RECORD_TYPE = "A";
    public static final String DNS_DEFAULT_TCP = Parameter.FALSE;
    public static final String DNS_DEFAULT_RECURSIVE = Parameter.TRUE;
    public static final String DNS_DEFAULT_EXPECTED = "129.62.3.230";


    public static List<Parameter> createParameters() {
        List<Parameter> parameters = new ArrayList<>();
        parameters.add(new Parameter(DNS_QUERY, DNS_DEFAULT_QUERY));
        parameters.add(new Parameter(DNS_RECORD_TYPE, DNS_DEFAULT_RECORD_TYPE));
        parameters.add(new Parameter(DNS_TCP, DNS_DEFAULT_TCP));
        parameters.add(new Parameter(DNS_RECURSIVE, DNS_DEFAULT_RECURSIVE));
        parameters.add(new Parameter(DNS_EXPECTED, DNS_DEFAULT_EXPECTED));
        return parameters;
    }

}
