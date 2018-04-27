/*
 * Copyright 2018 Nathaniel Stickney
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package is.stma.beanpoll.poller;

import is.stma.beanpoll.model.Resource;

import javax.validation.ValidationException;

public class PollerFactory {

    public static AbstractPoller getPoller(Resource resource) {
        switch (resource.getType()) {
            case DNS:
                return new DNSPoller(resource);
            case FTP:
                return new FTPPoller(resource);
            case HTTP:
                return new HTTPPoller(resource);
            case POP:
                return new EmailPoller(resource);
            case IMAP:
                return new EmailPoller(resource);
            case SMTP:
                return new SMTPPoller(resource);
            case SMTP_IMAP:
                return new SMTPCheckPoller(resource);
            case SMTP_POP:
                return new SMTPCheckPoller(resource);
            default:
                throw new ValidationException("unknown resource type " + resource.getType());
        }
    }

}
