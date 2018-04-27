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

public class FTPParameterizer {

    // DNS tag strings
    public static final String FTP_RESOLVER = "FTP_RESOLVER";
    public static final String FTP_READFILE = "FTP_READFILE";
    public static final String FTP_EXPECTED = "FTP_EXPECTED";
    public static final String FTP_WRITEFILE = "FTP_WRITEFILE";
    public static final String FTP_USERNAME = "FTP_USERNAME";
    public static final String FTP_PASSWORD = "FTP_PASSWORD";

    // DNS default value strings
    public static final String FTP_DEFAULT_RESOLVER = null;
    public static final String FTP_DEFAULT_READFILE = "flag.txt";
    public static final String FTP_DEFAULT_EXPECTED = "12345";
    public static final String FTP_DEFAULT_WRITEFILE = "test.txt";
    public static final String FTP_DEFAULT_USERNAME = "anonymous";
    public static final String FTP_DEFAULT_PASSWORD = null;

    public static List<Parameter> createParameters() {
        List<Parameter> parameters = new ArrayList<>();
        parameters.add(new Parameter(FTP_RESOLVER, FTP_DEFAULT_RESOLVER));
        parameters.add(new Parameter(FTP_READFILE, FTP_DEFAULT_READFILE));
        parameters.add(new Parameter(FTP_EXPECTED, FTP_DEFAULT_EXPECTED));
        parameters.add(new Parameter(FTP_WRITEFILE, FTP_DEFAULT_WRITEFILE));
        parameters.add(new Parameter(FTP_USERNAME, FTP_DEFAULT_USERNAME));
        parameters.add(new Parameter(FTP_PASSWORD, FTP_DEFAULT_PASSWORD));
        return parameters;
    }

}
