package is.stma.judgebean.beanpoll.rules;

import is.stma.judgebean.beanpoll.data.AbstractRepo;
import is.stma.judgebean.beanpoll.data.ParameterRepo;
import is.stma.judgebean.beanpoll.model.Parameter;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.validation.ValidationException;

@Model
public class ParameterRules extends AbstractRules<Parameter> {

    @Inject
    private ParameterRepo repo;

    @Override
    public AbstractRepo<Parameter> getRepo() {
        return repo;
    }

    @Override
    public void runBusinessRules(Parameter entity, Target target)
            throws ValidationException {
    }

    @Override
    void checkBeforeDelete(Parameter entity) throws ValidationException {

    }
}
