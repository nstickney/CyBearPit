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

public class EmailParameterizer {

    // EMAIL tag strings
    public static final String EMAIL_USERNAME = "EMAIL_USERNAME";
    public static final String EMAIL_PASSWORD = "EMAIL_PASSWORD";
    public static final String EMAIL_RESOLVER = "EMAIL_RESOLVER";
    public static final String EMAIL_SSL = "EMAIL_SSL";
    public static final String EMAIL_TLS = "EMAIL_TLS";
    public static final String EMAIL_HOST = "EMAIL_HOST";

    // EMAIL default value strings
    public static final String EMAIL_DEFAULT_USERNAME = "beanpoll@yandex.com";
    public static final String EMAIL_DEFAULT_PASSWORD = "NOT_MY_PASSWORD";
    public static final String EMAIL_DEFAULT_RESOLVER = null;
    public static final String EMAIL_DEFAULT_SSL = Parameter.TRUE;
    public static final String EMAIL_DEFAULT_TLS = Parameter.FALSE;
    public static final String EMAIL_DEFAULT_HOST = null;

    public static List<Parameter> createParameters() {
        List<Parameter> parameters = new ArrayList<>();
        parameters.add(new Parameter(EMAIL_USERNAME, EMAIL_DEFAULT_USERNAME));
        parameters.add(new Parameter(EMAIL_PASSWORD, EMAIL_DEFAULT_PASSWORD));
        parameters.add(new Parameter(EMAIL_RESOLVER, EMAIL_DEFAULT_RESOLVER));
        parameters.add(new Parameter(EMAIL_SSL, EMAIL_DEFAULT_SSL));
        parameters.add(new Parameter(EMAIL_TLS, EMAIL_DEFAULT_TLS));
        parameters.add(new Parameter(EMAIL_HOST, EMAIL_DEFAULT_HOST));
        return parameters;
    }

}
