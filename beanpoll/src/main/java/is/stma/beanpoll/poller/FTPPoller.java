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
import is.stma.beanpoll.service.parameterizer.FTPParameterizer;
import is.stma.beanpoll.util.DNSUtility;
import is.stma.beanpoll.util.FTPUtility;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FTPPoller extends AbstractPoller {

    // Set values based on the resource parameters
    private String resolver = FTPParameterizer.FTP_DEFAULT_RESOLVER;
    private String readfile = FTPParameterizer.FTP_DEFAULT_READFILE;
    private String expected = FTPParameterizer.FTP_DEFAULT_EXPECTED;
    private String writefile = FTPParameterizer.FTP_DEFAULT_WRITEFILE;
    private String username = FTPParameterizer.FTP_DEFAULT_USERNAME;
    private String password = FTPParameterizer.FTP_DEFAULT_PASSWORD;

    FTPPoller(Resource resource) {
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

        // Read the proper file
        newPoll.setResults("READ: " + FTPUtility.readFTPTextFile(address, resource.getPort(), username, password, readfile));

        // Test for the expected value
        if (!newPoll.getResults().contains(expected)) {
            return newPoll;
        }

        // Check possible ownership
        List<Team> teams;
        if (resource.getTeams().size() > 0) {
            teams = resource.getTeams();
        } else if (resource.getTeams().size() == 0) {
            teams = resource.getContest().getTeams();
        } else {
            newPoll.setTeam(resource.getTeams().get(0));
            return newPoll;
        }

        // Check actual ownership
        List<Team> found = new ArrayList<>();
        for (Team t : teams) {
            if (newPoll.getResults().contains(t.getFlag())) {
                found.add(t);
            }
        }
        if (found.size() != 1) {
            return newPoll;
        } else {
            newPoll.setTeam(found.get(0));
        }

        // Write a file (as needed)
        if (null != writefile) {
            String write = UUID.randomUUID().toString();
            if (!FTPUtility.writeFTPTextFile(address, resource.getPort(), username, password, writefile, write)) {
                newPoll.setResults(newPoll.getResults() + " --- WRITE: FAIL " + write);
                return newPoll;
            } else {
                newPoll.setResults(newPoll.getResults() + " --- WRITE: " + write);
                newPoll.setScore(resource.getPointValue());
            }
        }

        return newPoll;
    }

    @Override
    void setParameters() {
        for (Parameter p : resource.getParameters()) {
            switch (p.getTag()) {
                case FTPParameterizer.FTP_RESOLVER:
                    resolver = p.getValue();
                    break;
                case FTPParameterizer.FTP_READFILE:
                    readfile = p.getValue();
                    break;
                case FTPParameterizer.FTP_EXPECTED:
                    expected = p.getValue();
                    break;
                case FTPParameterizer.FTP_WRITEFILE:
                    writefile = p.getValue();
                    break;
                case FTPParameterizer.FTP_USERNAME:
                    username = p.getValue();
                    break;
                case FTPParameterizer.FTP_PASSWORD:
                    password = p.getValue();
                    break;
                default:
                    break;
            }
        }
    }
}