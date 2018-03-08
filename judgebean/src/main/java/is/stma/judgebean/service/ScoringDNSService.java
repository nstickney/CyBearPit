package is.stma.judgebean.service;

import is.stma.judgebean.data.ScoringDNSRepo;
import is.stma.judgebean.data.AbstractRepo;
import is.stma.judgebean.model.scoring.ScoringDNS;
import is.stma.judgebean.rules.ScoringDNSRules;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;

@Stateless
public class ScoringDNSService extends AbstractService<ScoringDNS, AbstractRepo<ScoringDNS>,
        ScoringDNSRules> {

    /* Injects ----------------------------------------------------------------------- */
    @Inject
    private ScoringDNSRepo repo;

    @Inject
    private Event<ScoringDNS> event;

    @Inject
    private ScoringDNSRules validator;

    @Override
    AbstractRepo<ScoringDNS> getRepo() {
        return repo;
    }

    @Override
    Event<ScoringDNS> getEvent() {
        return event;
    }

    @Override
    ScoringDNSRules getValidator() {
        return validator;
    }
}
