package is.stma.judgebean.beanpoll.service;

import is.stma.judgebean.beanpoll.data.AbstractRepo;
import is.stma.judgebean.beanpoll.data.ResourceRepo;
import is.stma.judgebean.beanpoll.model.Resource;
import is.stma.judgebean.beanpoll.rules.ResourceRules;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;

@Stateless
public class ResourceService extends AbstractService<Resource, AbstractRepo<Resource>,
        ResourceRules> {

    @SuppressWarnings("CdiInjectionPointsInspection")
    @Inject
    private ResourceRepo repo;

    @Inject
    private Event<Resource> event;

    @Inject
    private ResourceRules rules;

    @Override
    AbstractRepo<Resource> getRepo() {
        return repo;
    }

    @Override
    Event<Resource> getEvent() {
        return event;
    }

    @Override
    ResourceRules getRules() {
        return rules;
    }
}
