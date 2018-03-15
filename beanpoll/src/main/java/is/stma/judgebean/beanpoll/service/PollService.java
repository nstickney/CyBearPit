package is.stma.judgebean.beanpoll.service;

import is.stma.judgebean.beanpoll.data.AbstractRepo;
import is.stma.judgebean.beanpoll.data.PollRepo;
import is.stma.judgebean.beanpoll.model.Poll;
import is.stma.judgebean.beanpoll.rules.PollRules;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;

@Stateless
public class PollService extends AbstractService<Poll, AbstractRepo<Poll>,
        PollRules> {

    @SuppressWarnings("CdiInjectionPointsInspection")
    @Inject
    private PollRepo repo;

    @Inject
    private Event<Poll> event;

    @Inject
    private PollRules rules;

    @Override
    AbstractRepo<Poll> getRepo() {
        return repo;
    }

    @Override
    Event<Poll> getEvent() {
        return event;
    }

    @Override
    PollRules getRules() {
        return rules;
    }
}
