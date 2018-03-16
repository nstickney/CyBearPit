package is.stma.judgebean.beanpoll.service;

import is.stma.judgebean.beanpoll.data.AbstractRepo;
import is.stma.judgebean.beanpoll.data.ContestRepo;
import is.stma.judgebean.beanpoll.model.Contest;
import is.stma.judgebean.beanpoll.rules.ContestRules;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;

@Stateless
public class ContestService extends AbstractService<Contest, AbstractRepo<Contest>,
        ContestRules> {

    @SuppressWarnings("CdiInjectionPointsInspection")
    @Inject
    private ContestRepo repo;

    @Inject
    private Event<Contest> event;

    @Inject
    private ContestRules rules;

    @Override
    AbstractRepo<Contest> getRepo() {
        return repo;
    }

    @Override
    Event<Contest> getEvent() {
        return event;
    }

    @Override
    ContestRules getRules() {
        return rules;
    }
}
