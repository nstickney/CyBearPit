package is.stma.judgebean.beanpoll.rules;

import is.stma.judgebean.beanpoll.data.AbstractRepo;
import is.stma.judgebean.beanpoll.data.ResourceRepo;
import is.stma.judgebean.beanpoll.model.Resource;
import is.stma.judgebean.beanpoll.model.Team;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.validation.ValidationException;

@Model
public class ResourceRules extends AbstractRules<Resource> {

    @Inject
    private ResourceRepo repo;

    @Override
    public AbstractRepo<Resource> getRepo() {
        return repo;
    }

    @Override
    public void runBusinessRules(Resource entity, Target target)
            throws ValidationException {

        // Teams assigned to this resource must be from this resource's contest
        checkTeamsMatchContest(entity);
    }

    @Override
    void checkBeforeDelete(Resource entity) throws ValidationException {

    }

    private void checkTeamsMatchContest(Resource entity) throws ValidationException {
        for (Team t : entity.getTeams()) {
            if (!t.getContest().equalByUUID(entity.getContest())) {
                throw new ValidationException("only teams from " + entity.getContest().getName()
                        + " may be assigned to " + entity.getName());
            }
        }
    }
}
