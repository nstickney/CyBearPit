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
import is.stma.beanpoll.model.Team;
import is.stma.beanpoll.service.parameterizer.HTTPParameterizer;
import is.stma.beanpoll.util.DNSUtility;
import is.stma.beanpoll.util.HTTPUtility;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class HTTPPoller extends AbstractPoller {

    // Set values based on the resource parameters
    private String resolver = HTTPParameterizer.HTTP_DEFAULT_RESOLVER;
    private String expected = HTTPParameterizer.HTTP_DEFAULT_EXPECTED;

    HTTPPoller(Resource resource) {
        this.resource = resource;
    }

    @Override
    public Poll doPoll() {

        Poll newPoll = new Poll();
        newPoll.setResource(resource);

        // Resolve the address if necessary
        String address = resource.getAddress();
        try {
            address = DNSUtility.getResolvedWebServer(resolver, address);
        } catch (URISyntaxException e) {
            newPoll.setResults("ERROR: Malformed request " + address);
            return newPoll;
        }

        // Find the results
        newPoll.setResults(HTTPUtility.get(address, resource.getPort(), resource.getTimeout()));

        if (!newPoll.getResults().contains(expected)) {
            return newPoll;
        }

        // Credit a team
        List<Team> teams = resource.getTeams().isEmpty() ? resource.getContest().getTeams() : resource.getTeams();
        if (1 == teams.size()) {
            newPoll.setTeam(teams.get(0));
            newPoll.setScore(resource.getPointValue());
        } else if (1 < teams.size()) {
            List<Team> matching = new ArrayList<>();
            for (Team t : teams) {
                if (newPoll.getResults().contains(t.getFlag())) {
                    matching.add(t);
                }
            }
            if (1 == matching.size()) {
                newPoll.setTeam(matching.get(0));
                newPoll.setScore(resource.getPointValue());
            }
        }

        return newPoll;
    }

    @Override
    void setParameters() {
        for (Parameter p : resource.getParameters()) {
            if (HTTPParameterizer.HTTP_RESOLVER.equals(p.getTag())) {
                resolver = p.getValue();
            } else if (HTTPParameterizer.HTTP_EXPECTED.equals(p.getTag())) {
                expected = p.getValue();
            }
        }
    }
}