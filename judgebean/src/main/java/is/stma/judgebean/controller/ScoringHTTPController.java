package is.stma.judgebean.controller;

import is.stma.judgebean.model.scoring.ScoringHTTP;
import is.stma.judgebean.service.ScoringHTTPService;
import is.stma.judgebean.rules.ScoringHTTPRules;

import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

@Model
public class ScoringHTTPController extends AbstractEntityController<ScoringHTTP, ScoringHTTPRules,
        ScoringHTTPService> {

    /* Injects ----------------------------------------------------------------------- */
    @Inject
    private ScoringHTTPService service;

    /* Produces ---------------------------------------------------------------------- */
    private ScoringHTTP newScoringHTTP;

    /* Override Methods -------------------------------------------------------------- */
    @Override
    @Produces
    @Named("newScoringHTTP")
    ScoringHTTP getNew() {
        if (newScoringHTTP == null) {
            newScoringHTTP = new ScoringHTTP();
        }
        return newScoringHTTP;
    }

    @Override
    void setNew(ScoringHTTP entity) {
        newScoringHTTP = entity;
    }

    @Override
    ScoringHTTPService getService() {
        return service;
    }

    @Override
    public void update(ScoringHTTP entity) {
        doUpdate(entity);
    }

    @Override
    public void delete(ScoringHTTP entity) {
        doDelete(entity);
    }
}
