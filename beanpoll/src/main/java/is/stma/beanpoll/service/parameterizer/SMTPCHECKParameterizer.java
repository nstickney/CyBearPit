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

public class SMTPCHECKParameterizer {

    // SMTPCHECK tag strings
    public static final String SMTPCHECK_HOST = "SMTPCHECK_HOST";

    // SMTPCHECK default value strings
    public static final String SMTPCHECK_DEFAULT_HOST = null;

    public static List<Parameter> createParameters() {
        List<Parameter> parameters = new ArrayList<>();
        parameters.add(new Parameter(SMTPCHECK_HOST, SMTPCHECK_DEFAULT_HOST));
        parameters.addAll(EmailParameterizer.createParameters());
        parameters.addAll(SMTPParameterizer.createParameters());
        return parameters;
    }

}
