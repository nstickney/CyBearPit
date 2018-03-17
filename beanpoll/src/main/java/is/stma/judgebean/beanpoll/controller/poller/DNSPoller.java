package is.stma.judgebean.beanpoll.controller.poller;

import is.stma.judgebean.beanpoll.controller.parameterizer.DNSParameterizer;
import is.stma.judgebean.beanpoll.model.Parameter;
import is.stma.judgebean.beanpoll.model.Poll;
import is.stma.judgebean.beanpoll.model.Resource;
import is.stma.judgebean.beanpoll.model.Team;
import is.stma.judgebean.beanpoll.util.DNSUtility;
import org.xbill.DNS.Type;

import java.util.ArrayList;
import java.util.List;

public class DNSPoller extends AbstractPoller {

    DNSPoller(Resource resource) {
        this.resource = resource;
    }

    @Override
    public Poll poll() {


        // Set values based on the resource parameters
        String query = DNSParameterizer.DNS_DEFAULT_QUERY;
        String recordType = DNSParameterizer.DNS_DEFAULT_RECORD_TYPE;
        String tcpString = DNSParameterizer.DNS_DEFAULT_TCP;
        String recursiveString = DNSParameterizer.DNS_DEFAULT_RECURSIVE;
        String timeoutString = DNSParameterizer.DNS_DEFAULT_TIMEOUT;
        String expected = DNSParameterizer.DNS_DEFAULT_EXPECTED;

        for (Parameter p : resource.getParameters()) {
            switch (p.getTag()) {
                case DNSParameterizer.DNS_QUERY:
                    query = p.getValue();
                    break;
                case DNSParameterizer.DNS_RECORD_TYPE:
                    recordType = p.getValue();
                    break;
                case DNSParameterizer.DNS_TCP:
                    tcpString = p.getValue();
                    break;
                case DNSParameterizer.DNS_RECURSIVE:
                    recursiveString = p.getValue();
                    break;
                case DNSParameterizer.DNS_TIMEOUT:
                    timeoutString = p.getValue();
                    break;
                case DNSParameterizer.DNS_EXPECTED:
                    expected = p.getValue();
                    break;
                default:
                    break;
            }
        }

        // Convert string parameters to typed parameters
        int type = Type.A;
        switch (recordType) {
            case "AAAA":
                type = Type.AAAA;
                break;
            case "CNAME":
                type = Type.CNAME;
                break;
            case "DNAME":
                type = Type.DNAME;
                break;
            case "MX":
                type = Type.MX;
                break;
            case "NS":
                type = Type.NS;
                break;
            case "PTR":
                type = Type.PTR;
                break;
            case "SOA":
                type = Type.SOA;
                break;
            case "TXT":
                type = Type.TXT;
                break;
            default:
                break;
        }
        boolean tcp = tcpString.equals(Parameter.TRUE);
        boolean recursive = recursiveString.equals(Parameter.TRUE);
        int timeout = Integer.parseInt(timeoutString);

        Poll newPoll = new Poll();
        newPoll.setResource(resource);

        // Do the poll
        newPoll.setInformation(DNSUtility.lookup(resource.getAddress(), resource.getPort(), query,
                type, tcp, recursive, timeout, false));

        // Check if it worked, and if it did, score the poll
        if (newPoll.getInformation().contains(expected)) {

            // No need to check flags if only one team is assigned
            if (1 == resource.getTeams().size()) {
                newPoll.setTeam(resource.getTeams().get(0));
                newPoll.setScore(resource.getWeight());
            } else {

                // Check the flags
                newPoll.setInformation(newPoll.getInformation() + ", flag: " +
                        DNSUtility.lookup(resource.getAddress(), resource.getPort(), query, Type.TXT, tcp, recursive, timeout, false));

                List<Team> possibleTeams = resource.getTeams();

                // If no teams are assigned to this resource, it's fair game
                if (possibleTeams.isEmpty()) {
                    possibleTeams = resource.getContest().getTeams();
                }

                List<Team> scoringTeams = new ArrayList<>();
                scoringTeams = checkTeams(possibleTeams, newPoll.getInformation());

                if (1 == scoringTeams.size()) {
                    newPoll.setTeam(scoringTeams.get(0));
                    newPoll.setScore(resource.getWeight());
                }
            }
        }
        return newPoll;
    }
}
