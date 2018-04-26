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
import is.stma.beanpoll.service.parameterizer.SMTPParameterizer;
import is.stma.beanpoll.util.DNSUtility;
import is.stma.beanpoll.util.EmailUtility;

import javax.mail.MessagingException;
import java.util.UUID;

public class SMTPPoller extends AbstractPoller {

    SMTPPoller(Resource resource) {
        this.resource = resource;
    }

    @Override
    public Poll poll() {

        // Set values based on the resource parameters
        String username = SMTPParameterizer.SMTP_DEFAULT_USERNAME;
        String password = SMTPParameterizer.SMTP_DEFAULT_PASSWORD;
        String recipient = SMTPParameterizer.SMTP_DEFAULT_RECIPIENT;
        String ssl_string = SMTPParameterizer.SMTP_DEFAULT_SSL;
        String tls_string = SMTPParameterizer.SMTP_DEFAULT_TLS;
        String test_auth = SMTPParameterizer.SMTP_DEFAULT_TEST_AUTH;
        String resolver = SMTPParameterizer.SMTP_DEFAULT_RESOLVER;

        for (Parameter p : resource.getParameters()) {
            switch (p.getTag()) {
                case SMTPParameterizer.SMTP_USERNAME:
                    username = p.getValue();
                    break;
                case SMTPParameterizer.SMTP_PASSWORD:
                    password = p.getValue();
                    break;
                case SMTPParameterizer.SMTP_RECIPIENT:
                    recipient = p.getValue();
                    break;
                case SMTPParameterizer.SMTP_SSL:
                    ssl_string = p.getValue();
                    break;
                case SMTPParameterizer.SMTP_TLS:
                    tls_string = p.getValue();
                    break;
                case SMTPParameterizer.SMTP_TEST_AUTH:
                    test_auth = p.getValue();
                    break;
                case SMTPParameterizer.SMTP_RESOLVER:
                    resolver = p.getValue();
                    break;
                default:
                    break;
            }
        }

        Poll newPoll = new Poll();
        newPoll.setResource(resource);

        // Check that exactly one team is assigned
        if (1 != resource.getTeams().size()) {
            newPoll.setResults("ERROR: Resource does not have exactly one assigned Team");
            return newPoll;
        }

        // Get the address of the server
        String address = DNSUtility.getResolvedMailserver(resolver, resource.getAddress());
        if (address.startsWith("ERROR")) {
            newPoll.setResults(address);
            return newPoll;
        }

        // Find the values of the non-string parameters
        boolean ssl = ssl_string.equals(Parameter.TRUE);
        boolean tls = tls_string.equals(Parameter.TRUE);
        boolean auth = test_auth.equals(Parameter.TRUE);

        // Write up an easily recognizable message
        String msg = UUID.randomUUID().toString();

        // Send mail!
        try {
            EmailUtility.sendSMTPMessage(username, password, address, recipient,
                    msg, msg, resource.getPort(), tls, ssl, resource.getTimeout());
        } catch (MessagingException e) {
            newPoll.setResults(e.getMessage());
            return newPoll;
        }

        // Make sure the server isn't just letting anyone send mail
        String results = "";
        if (auth) {
            try {
                EmailUtility.sendSMTPMessage(msg, msg, address, recipient, msg,
                        msg, resource.getPort(), tls, ssl, resource.getTimeout());
                newPoll.setResults("ERROR: User " + msg + " exists");
            } catch (MessagingException e) {
                results = " - and user " + msg + " cannot send mail";
            }
        }

        newPoll.setTeam(resource.getTeams().get(0));
        newPoll.setScore(resource.getPointValue());
        newPoll.setResults("Success" + results);
        return newPoll;
    }
}