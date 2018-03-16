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

            // If the resource doesn't have any teams set, then any team in the contest is allowed
            // to score on the resource
            List<Team> possibleTeams = resource.getTeams();
            if (possibleTeams.isEmpty()) {
                possibleTeams = resource.getContest().getTeams();
            }
            scoringTeams = checkTeams(possibleTeams, newPoll.getInformation());
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