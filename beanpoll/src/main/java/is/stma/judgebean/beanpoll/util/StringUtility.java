/*
 * Copyright 2018 Nathaniel Stickney
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package is.stma.judgebean.beanpoll.util;

import javax.validation.ValidationException;

public class StringUtility {

    private static final String REGEX_VALID_CHARACTERS = "[\\p{Alnum}\\p{Punct}]+( [\\p{Alnum}\\p{Punct}]+)*";
    private static final String REGEX_WHITESPACE = "[\\p{Space}]*";
    private static final String INVALID_CHARACTERS = "allowed characters are alphanumerics and punctuation; single spaces are allowed between words";

    public static void checkString(String string) throws ValidationException {

        // Allow only valid characters (alphanumeric, punctuation, and single space)
        if (!string.matches(REGEX_VALID_CHARACTERS)) {
            throw new ValidationException(INVALID_CHARACTERS);
        }
    }

    public static String removeWhitespace(String string) {
        return string.replaceAll(REGEX_WHITESPACE, "");
    }
}
