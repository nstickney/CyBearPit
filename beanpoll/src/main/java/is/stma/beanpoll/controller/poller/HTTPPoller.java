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
import is.stma.beanpoll.model.Team;
import is.stma.beanpoll.service.parameterizer.HTTPParameterizer;
import is.stma.beanpoll.util.DNSUtility;
import is.stma.beanpoll.util.HTTPUtility;

import javax.validation.ValidationException;
import java.net.URI;
import java.net.URISyntaxException;
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

        // Resolve the address if necessary
        String address = resource.getAddress();
        try {
            address = getResolvedAddress(resolver, address);
        } catch (URISyntaxException e) {
            newPoll.setResults("ERROR: Malformed request " + address);
            return newPoll;
        }

        // Find the results
        newPoll.setResults(HTTPUtility.get(address, resource.getPort(), resource.getTimeout()));

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

    /**
     * Use the specified DNS resolver to find the correct IP address for the given hostname.
     * @param resolver IP hostname or hostname of the DNS resolver to use
     * @param hostname hostname to resolve
     * @return a fully formatted HTTP query using the IP address resolved
     * @throws URISyntaxException if the hostname is not a proper URI
     */
    private String getResolvedAddress(String resolver, String hostname) throws URISyntaxException {
        String resolved = hostname;
        if (null != resolver && !resolver.equals("")) {
            hostname = HTTPUtility.setDefaultHTTPProtocol(hostname);
            URI uri = new URI(hostname);
            resolved = DNSUtility.lookup(resolver, uri.getHost());
            if (resolved.startsWith("ERROR")) {
                return resolved;
            }
            resolved = resolved.substring(resolved.indexOf("./") + 2, resolved.length());
            resolved = uri.getScheme() + "://" + resolved + uri.getPath();
        }
        return resolved;
    }
}