package is.stma.judgebean.beanpoll.service;

import is.stma.judgebean.beanpoll.data.AbstractRepo;
import is.stma.judgebean.beanpoll.data.ResourceParameterRepo;
import is.stma.judgebean.beanpoll.model.ResourceParameter;
import is.stma.judgebean.beanpoll.rules.ResourceParameterRules;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;

@Stateless
public class ResourceParameterService extends AbstractService<ResourceParameter, AbstractRepo<ResourceParameter>,
        ResourceParameterRules> {

    @Inject
    private ResourceParameterRepo repo;

    @Inject
    private Event<ResourceParameter> event;

    @Inject
    private ResourceParameterRules validator;

    @Override
    AbstractRepo<ResourceParameter> getRepo() {
        return repo;
    }

    @Override
    Event<ResourceParameter> getEvent() {
        return event;
    }

    @Override
    ResourceParameterRules getValidator() {
        return validator;
    }
}
