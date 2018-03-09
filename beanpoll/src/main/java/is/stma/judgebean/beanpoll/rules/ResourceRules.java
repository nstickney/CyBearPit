package is.stma.judgebean.beanpoll.rules;

import is.stma.judgebean.beanpoll.data.ResourceRepo;
import is.stma.judgebean.beanpoll.model.Resource;
import org.apache.deltaspike.data.api.EntityRepository;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.validation.ValidationException;

@Model
public class ResourceRules extends AbstractRules<Resource> {

    @Inject
    private ResourceRepo repo;

    @Override
    public EntityRepository<Resource, String> getRepo() {
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
