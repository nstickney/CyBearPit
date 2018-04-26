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
import is.stma.beanpoll.model.ResourceType;
import is.stma.beanpoll.service.parameterizer.EmailParameterizer;
import is.stma.beanpoll.util.DNSUtility;
import is.stma.beanpoll.util.EmailUtility;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.validation.ValidationException;

public class EmailPoller extends AbstractPoller {

    // Set values based on the resource parameters
    private String username = EmailParameterizer.EMAIL_DEFAULT_USERNAME;
    private String password = EmailParameterizer.EMAIL_DEFAULT_PASSWORD;
    private String resolver = EmailParameterizer.EMAIL_DEFAULT_RESOLVER;
    private String ssl_string = EmailParameterizer.EMAIL_DEFAULT_SSL;
    private String tls_string = EmailParameterizer.EMAIL_DEFAULT_TLS;

    EmailPoller(Resource resource) {
        this.resource = resource;
    }

    @Override
    public Poll doPoll() {

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

        // Decide whether to use IMAP or POP; default to POP
        String protocol = (resource.getType().equals(ResourceType.IMAP)) ? "imap" : "pop3";

        // Find the values of the non-string parameters
        boolean ssl = ssl_string.equals(Parameter.TRUE);
        boolean tls = tls_string.equals(Parameter.TRUE);

        // Get mail!
        try {
            Message[] messages = EmailUtility.getEmail(username, password, address,
                    protocol, resource.getPort(), tls, ssl, resource.getTimeout());
        } catch (MessagingException e) {
            newPoll.setResults(e.getMessage());
            return newPoll;
        }

        newPoll.setTeam(resource.getTeams().get(0));
        newPoll.setScore(resource.getPointValue());
        newPoll.setResults("Success");
        return newPoll;
    }

    @Override
    void setParameters() {

        for (Parameter p : resource.getParameters()) {
            switch (p.getTag()) {
                case EmailParameterizer.EMAIL_USERNAME:
                    username = p.getValue();
                    break;
                case EmailParameterizer.EMAIL_PASSWORD:
                    password = p.getValue();
                    break;
                case EmailParameterizer.EMAIL_RESOLVER:
                    resolver = p.getValue();
                    break;
                case EmailParameterizer.EMAIL_SSL:
                    ssl_string = p.getValue();
                    break;
                case EmailParameterizer.EMAIL_TLS:
                    tls_string = p.getValue();
                    break;
                default:
                    break;
            }
        }
    }
}