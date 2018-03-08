package is.stma.judgebean.service;

import is.stma.judgebean.data.AbstractRepo;
import is.stma.judgebean.data.ResourceRepo;
import is.stma.judgebean.model.Resource;
import is.stma.judgebean.rules.ResourceRules;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;

@Stateless
public class ResourceService extends AbstractService<Resource, AbstractRepo<Resource>,
        ResourceRules> {

    /* Injects ----------------------------------------------------------------------- */
    @Inject
    private ResourceRepo repo;

    @Inject
    private Event<Resource> event;

    @Inject
    private ResourceRules validator;

    @Override
    AbstractRepo<Resource> getRepo() {
        return repo;
    }

    @Override
    Event<Resource> getEvent() {
        return event;
    }

    @Override
    ResourceRules getValidator() {
        return validator;
    }
}
