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

        // Cannot delete a running contest
        checkNotRunning(entity);
    }

    private void checkNotRunning(Contest entity) throws ValidationException {

        // Check both the entity we are handed, and the canonical version of said entity
        if (entity.isRunning() || repo.findBy(entity.getId()).isRunning()) {
            throw new ValidationException("cannot delete a running contest");
        }

    }
}
