package is.stma.judgebean.beanpoll.service;

import is.stma.judgebean.beanpoll.data.AbstractRepo;
import is.stma.judgebean.beanpoll.data.ParameterRepo;
import is.stma.judgebean.beanpoll.model.Parameter;
import is.stma.judgebean.beanpoll.rules.ParameterRules;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;

@Stateless
public class ParameterService extends AbstractService<Parameter, AbstractRepo<Parameter>,
        ParameterRules> {

    @SuppressWarnings("CdiInjectionPointsInspection")
    @Inject
    private ParameterRepo repo;

    @Inject
    private Event<Parameter> event;

    @Inject
    private ParameterRules rules;

    @Override
    AbstractRepo<Parameter> getRepo() {
        return repo;
    }

    @Override
    Event<Parameter> getEvent() {
        return event;
    }

    @Override
    ParameterRules getRules() {
        return rules;
    }
}
