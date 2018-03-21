/*
 * Copyright 2018 Nathaniel Stickney
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package is.stma.judgebean.beanpoll.controller.poller;

import is.stma.judgebean.beanpoll.controller.parameterizer.HTTPParameterizer;
import is.stma.judgebean.beanpoll.model.Parameter;
import is.stma.judgebean.beanpoll.model.Poll;
import is.stma.judgebean.beanpoll.model.Resource;
import is.stma.judgebean.beanpoll.model.Team;
import is.stma.judgebean.beanpoll.util.DNSUtility;
import is.stma.judgebean.beanpoll.util.HTTPUtility;

import java.util.ArrayList;
import java.util.List;

public class HTTPPoller extends AbstractPoller {

    HTTPPoller(Resource resource) {
        this.resource = resource;
    }

    @Override
    public Poll poll() {

        // Set values based on the resource parameters
        String resolver = HTTPParameterizer.HTTP_DEFAULT_RESOLVER;
        for (Parameter p : resource.getParameters()) {
            if (HTTPParameterizer.HTTP_RESOLVER.equals(p.getTag())) {
                resolver = p.getValue();
            }
        }

        Poll newPoll = new Poll();
        newPoll.setResource(resource);

        // Find the resource address - don't want the protocol portion
        String address = resource.getAddress();
        if (address.contains("://")) {
            address = address.substring(address.indexOf("://") + 3);
        }

        // If the HTTP_RESOLVER is set, use it to look up the resource address
        if (null != resolver && !resolver.equals("")) {
            address = DNSUtility.lookup(resolver, address);
        }

        // If the DNS resolution failed, fail
        if (address.startsWith("ERROR: ")) {
            newPoll.setInformation("DNS " + address);
            return newPoll;
        }

        if (!address.startsWith("http://") || !address.startsWith("https://")) {
            address = "http://" + address;
        }

        // Do the check
        newPoll.setInformation(HTTPUtility.get(address));

        // Score the new poll - but only if there wasn't an error
        List<Team> scoringTeams = new ArrayList<>();
        if (!newPoll.getInformation().startsWith("ERROR: ")) {

            List<Team> possibleTeams = resource.getTeams();

            // If there is only a single team assigned to this resource, don't check flags
            if (1 == possibleTeams.size()) {
                scoringTeams = possibleTeams;
            } else {
                // If the resource doesn't have any teams set, then any team in the contest is allowed
                // to score on the resource
                if (possibleTeams.isEmpty()) {
                    possibleTeams = resource.getContest().getTeams();
                }
                scoringTeams = checkTeams(possibleTeams, newPoll.getInformation());
            }
        }

        // If exactly one team's flag was found...
        if (null != scoringTeams && 1 == scoringTeams.size()) {
            try {
                newPoll.setTeam(scoringTeams.get(0));
                newPoll.setScore(resource.getWeight());
            } catch (NullPointerException e) {
                newPoll.setTeam(null);
                newPoll.setScore(0);
            }
        }

        return newPoll;
    }
}