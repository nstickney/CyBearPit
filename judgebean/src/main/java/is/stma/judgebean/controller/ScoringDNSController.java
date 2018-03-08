package is.stma.judgebean.controller;

import is.stma.judgebean.model.scoring.ScoringDNS;
import is.stma.judgebean.service.ScoringDNSService;
import is.stma.judgebean.rules.ScoringDNSRules;

import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

@Model
public class ScoringDNSController extends AbstractEntityController<ScoringDNS, ScoringDNSRules,
        ScoringDNSService> {

    /* Injects ----------------------------------------------------------------------- */
    @Inject
    private ScoringDNSService service;

    /* Produces ---------------------------------------------------------------------- */
    private ScoringDNS newScoringDNS;

    /* Override Methods -------------------------------------------------------------- */
    @Override
    @Produces
    @Named("newScoringDNS")
    ScoringDNS getNew() {
        if (newScoringDNS == null) {
            newScoringDNS = new ScoringDNS();
        }
        return newScoringDNS;
    }

    @Override
    void setNew(ScoringDNS entity) {
        newScoringDNS = entity;
    }

    @Override
    ScoringDNSService getService() {
        return service;
    }

    @Override
    public void update(ScoringDNS entity) {
        doUpdate(entity);
    }

    @Override
    public void delete(ScoringDNS entity) {
        doDelete(entity);
    }
}
