package is.stma.judgebean.beanpoll.rules;

import is.stma.judgebean.beanpoll.data.AbstractRepo;
import is.stma.judgebean.beanpoll.data.ContestRepo;
import is.stma.judgebean.beanpoll.model.Contest;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.validation.ValidationException;

@Model
public class ContestRules extends AbstractRules<Contest> {

    @SuppressWarnings("CdiInjectionPointsInspection")
    @Inject
    private ContestRepo repo;

    @Override
    public AbstractRepo<Contest> getRepo() {
        return repo;
    }

    @Override
    public void runBusinessRules(Contest entity, Target target)
            throws ValidationException {
    }

    @Override
    void checkBeforeDelete(Contest entity) throws ValidationException {

    }
}
