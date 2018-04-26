/*
 * Copyright 2018 Nathaniel Stickney
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package is.stma.beanpoll.controller.poller;

import is.stma.beanpoll.model.Parameter;
import is.stma.beanpoll.model.Poll;
import is.stma.beanpoll.model.Resource;
import is.stma.beanpoll.service.parameterizer.EmailParameterizer;
import is.stma.beanpoll.service.parameterizer.SMTPParameterizer;

public class SMTPCheckPoller extends AbstractPoller {

    SMTPCheckPoller(Resource resource) {
        this.resource = resource;
    }

    @Override
    public Poll poll() {

        // Set values based on the resource parameters
        String popUsername = EmailParameterizer.EMAIL_DEFAULT_USERNAME;
        String popPassword = EmailParameterizer.EMAIL_DEFAULT_PASSWORD;
        String smtpUsername = SMTPParameterizer.SMTP_DEFAULT_USERNAME;
        String smtpPassword = SMTPParameterizer.SMTP_DEFAULT_PASSWORD;

        for (Parameter p : resource.getParameters()) {
            switch (p.getTag()) {
                case EmailParameterizer.EMAIL_USERNAME:
                    popUsername = p.getValue();
                    break;
                case EmailParameterizer.EMAIL_PASSWORD:
                    popPassword = p.getValue();
                    break;
                case SMTPParameterizer.SMTP_USERNAME:
                    smtpUsername = p.getValue();
                    break;
                case SMTPParameterizer.SMTP_PASSWORD:
                    smtpPassword = p.getValue();
                    break;
                default:
                    break;
            }
        }

        Poll newPoll = new Poll();
        newPoll.setResource(resource);
        return newPoll;
    }
}