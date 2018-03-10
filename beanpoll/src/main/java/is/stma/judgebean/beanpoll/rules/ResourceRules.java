package is.stma.judgebean.beanpoll.rules;

import is.stma.judgebean.beanpoll.data.AbstractRepo;
import is.stma.judgebean.beanpoll.data.ResourceRepo;
import is.stma.judgebean.beanpoll.model.Resource;

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
    }

    @Override
    void checkBeforeDelete(Resource entity) throws ValidationException {

    }
}
