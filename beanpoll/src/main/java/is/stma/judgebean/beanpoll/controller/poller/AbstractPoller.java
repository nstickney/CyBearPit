package is.stma.judgebean.beanpoll.controller.poller;

import is.stma.judgebean.beanpoll.model.Poll;
import is.stma.judgebean.beanpoll.model.Resource;
import is.stma.judgebean.beanpoll.model.Team;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPoller {

    Resource resource;

    public abstract Poll poll();

    List<Team> checkTeams(List<Team> resourceTeams, String response) {
        List<Team> foundTeams = new ArrayList<>();
        for (Team t : resourceTeams) {
            if (response.contains(t.getFlag())) {
                foundTeams.add(t);
            }
        }
        return foundTeams;
    }

}
