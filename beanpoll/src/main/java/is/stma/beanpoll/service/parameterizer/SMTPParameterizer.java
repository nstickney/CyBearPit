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

public class SMTPParameterizer {

    // SMTP tag strings
    public static final String SMTP_USERNAME = "SMTP_USERNAME";
    public static final String SMTP_PASSWORD = "SMTP_PASSWORD";

    // SMTP default value strings
    public static final String SMTP_DEFAULT_USERNAME = "nobody";
    public static final String SMTP_DEFAULT_PASSWORD = "nothing";


    public static List<Parameter> createParameters() {
        List<Parameter> parameters = new ArrayList<>();
        parameters.add(new Parameter(SMTP_USERNAME, SMTP_DEFAULT_USERNAME));
        parameters.add(new Parameter(SMTP_PASSWORD, SMTP_DEFAULT_PASSWORD));
        return parameters;
    }

}
