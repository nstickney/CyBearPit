package is.stma.judgebean.beanpoll.asynchronous;

import is.stma.judgebean.beanpoll.model.Contest;
import is.stma.judgebean.beanpoll.service.ContestService;
import is.stma.judgebean.beanpoll.service.ParameterService;
import is.stma.judgebean.beanpoll.service.ResourceService;
import is.stma.judgebean.beanpoll.service.TeamService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

@ApplicationScoped
@Named("judge")
public class Judge {

    @Inject
    TeamService teamService;
    @Inject
    ResourceService resourceService;
    @Inject
    ParameterService parameterService;
    @Inject
    private
    ContestService contestService;

    public void run(Contest contest) {
        contest.setRunning(true);
        contestService.update(contest);
    }

    public void stop(Contest contest) {
        contest.setRunning(false);
        contestService.update(contest);
    }
}
