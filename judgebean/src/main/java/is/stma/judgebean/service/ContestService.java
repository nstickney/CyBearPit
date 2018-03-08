package is.stma.judgebean.service;

import is.stma.judgebean.data.AbstractRepo;
import is.stma.judgebean.data.ContestRepo;
import is.stma.judgebean.model.Contest;
import is.stma.judgebean.rules.ContestRules;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;

@Stateless
public class ContestService extends AbstractService<Contest, AbstractRepo<Contest>,
        ContestRules> {

    /* Injects ----------------------------------------------------------------------- */
    @Inject
    private ContestRepo repo;

    @Inject
    private Event<Contest> event;

    @Inject
    private ContestRules validator;

    @Override
    AbstractRepo<Contest> getRepo() {
        return repo;
    }

    @Override
    Event<Contest> getEvent() {
        return event;
    }

    @Override
    ContestRules getValidator() {
        return validator;
    }
}
