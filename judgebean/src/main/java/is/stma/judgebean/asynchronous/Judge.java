package is.stma.judgebean.asynchronous;

import is.stma.judgebean.service.ContestService;
import is.stma.judgebean.service.ResourceParameterService;
import is.stma.judgebean.service.ResourceService;
import is.stma.judgebean.service.TeamService;

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
