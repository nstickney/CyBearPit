package is.stma.judgebean.beanpoll.asynchronous;

import is.stma.judgebean.beanpoll.service.ContestService;
import is.stma.judgebean.beanpoll.service.ResourceParameterService;
import is.stma.judgebean.beanpoll.service.ResourceService;
import is.stma.judgebean.beanpoll.service.TeamService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class Judge {

    @Inject
    ContestService contestService;

    @Inject
    TeamService teamService;

    @Inject
    ResourceService resourceService;

    @Inject
    ResourceParameterService resourceParameterService;
}
