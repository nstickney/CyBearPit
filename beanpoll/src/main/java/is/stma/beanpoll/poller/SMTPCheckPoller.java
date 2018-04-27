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

import is.stma.beanpoll.model.Parameter;
import is.stma.beanpoll.model.Poll;
import is.stma.beanpoll.model.Resource;
import is.stma.beanpoll.service.parameterizer.EmailParameterizer;
import is.stma.beanpoll.service.parameterizer.SMTPCHECKParameterizer;
import is.stma.beanpoll.service.parameterizer.SMTPParameterizer;

import java.util.concurrent.TimeUnit;

public class SMTPCheckPoller extends AbstractPoller {

    private String delay_string = SMTPCHECKParameterizer.SMTPCHECK_DEFAULT_DELAY;

    SMTPCheckPoller(Resource resource) {
        this.resource = resource;
    }

    @Override
    public Poll doPoll() {

        Poll newPoll = new Poll();
        newPoll.setResource(resource);

        // Send an email
        Poll sendPoll = new SMTPPoller(resource).doPoll();
        if (sendPoll.getResults().startsWith("ERROR")) {
            newPoll.setResults(sendPoll.getResults());
            return newPoll;
        }

        // Get the subject of the email we sent
        String msg = sendPoll.getResults().split(" ", 1)[0];

        // Wait for it to propagate
        try {
            TimeUnit.SECONDS.sleep(Integer.valueOf(delay_string));
        } catch (InterruptedException e) {
        }

        // Retrieve the email
        Poll recvPoll = new EmailPoller(resource).doPoll();
        if (recvPoll.getResults().startsWith("ERROR")) {
            newPoll.setResults(recvPoll.getResults());
            return newPoll;
        }

        // Check that the message we sent is found
        if (recvPoll.getResults().contains(msg)) {
            newPoll.setScore(resource.getPointValue());
            newPoll.setResults(msg);
        } else {
            newPoll.setResults("ERROR (" + msg + ") not in (" + recvPoll.getResults() + ")");
        }

        return newPoll;
    }

    @Override
    void setParameters() {
        for (Parameter p : resource.getParameters()) {
            switch (p.getTag()) {
                case SMTPCHECKParameterizer.SMTPCHECK_DELAY:
                    delay_string = p.getValue();
                    break;
                default:
                    break;
            }
        }
    }
}