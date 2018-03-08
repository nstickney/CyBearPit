package is.stma.judgebean.controller;

import is.stma.judgebean.model.Contest;
import is.stma.judgebean.rules.ContestRules;
import is.stma.judgebean.service.ContestService;

import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

@Model
public class ContestController extends AbstractEntityController<Contest, ContestRules,
        ContestService> {

    @Inject
    private ContestService service;

    private Contest newContest;

    @Override
    @Produces
    @Named("newContest")
    Contest getNew() {
        if (newContest == null) {
            newContest = new Contest();
        }
        return newContest;
    }

    @Override
    void setNew(Contest entity) {
        newContest = entity;
    }

    @Override
    ContestService getService() {
        return service;
    }

    @Override
    public void update(Contest entity) {
        doUpdate(entity);
    }

    @Override
    public void delete(Contest entity) {
        doDelete(entity);
    }
}
