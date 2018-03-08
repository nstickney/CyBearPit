package is.stma.judgebean.service;

import is.stma.judgebean.data.AbstractRepo;
import is.stma.judgebean.data.ScoringHTTPRepo;
import is.stma.judgebean.model.scoring.ScoringHTTP;
import is.stma.judgebean.rules.ScoringHTTPRules;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;

@Stateless
public class ScoringHTTPService extends AbstractService<ScoringHTTP, AbstractRepo<ScoringHTTP>,
        ScoringHTTPRules> {

    /* Injects ----------------------------------------------------------------------- */
    @Inject
    private ScoringHTTPRepo repo;

    @Inject
    private Event<ScoringHTTP> event;

    @Inject
    private ScoringHTTPRules validator;

    @Override
    AbstractRepo<ScoringHTTP> getRepo() {
        return repo;
    }

    @Override
    Event<ScoringHTTP> getEvent() {
        return event;
    }

    @Override
    ScoringHTTPRules getValidator() {
        return validator;
    }
}
