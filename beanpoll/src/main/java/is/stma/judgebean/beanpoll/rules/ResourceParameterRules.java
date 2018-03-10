package is.stma.judgebean.beanpoll.rules;

import is.stma.judgebean.beanpoll.data.AbstractRepo;
import is.stma.judgebean.beanpoll.data.ResourceParameterRepo;
import is.stma.judgebean.beanpoll.model.ResourceParameter;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.validation.ValidationException;

@Model
public class ResourceParameterRules extends AbstractRules<ResourceParameter> {

    @Inject
    private ResourceParameterRepo repo;

    @Override
    public AbstractRepo<ResourceParameter> getRepo() {
        return repo;
    }

    @Override
    public void runBusinessRules(ResourceParameter entity, Target target)
            throws ValidationException {
    }

    @Override
    void checkBeforeDelete(ResourceParameter entity) throws ValidationException {

    }
}
