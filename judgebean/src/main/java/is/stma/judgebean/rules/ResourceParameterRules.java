package is.stma.judgebean.rules;

import is.stma.judgebean.data.ResourceParameterRepo;
import is.stma.judgebean.model.ResourceParameter;
import org.apache.deltaspike.data.api.EntityRepository;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.validation.ValidationException;

@Model
public class ResourceParameterRules extends AbstractRules<ResourceParameter> {

    @Inject
    private ResourceParameterRepo repo;

    @Override
    public EntityRepository<ResourceParameter, String> getRepo() {
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