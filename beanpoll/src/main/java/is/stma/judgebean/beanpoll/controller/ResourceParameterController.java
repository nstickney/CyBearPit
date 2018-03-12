package is.stma.judgebean.beanpoll.controller;

import is.stma.judgebean.beanpoll.model.Resource;
import is.stma.judgebean.beanpoll.model.ResourceParameter;
import is.stma.judgebean.beanpoll.rules.ResourceParameterRules;
import is.stma.judgebean.beanpoll.service.ResourceParameterService;

import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

@Model
public class ResourceParameterController extends AbstractEntityController<ResourceParameter, ResourceParameterRules,
        ResourceParameterService> {

    @Inject
    private ResourceParameterService service;

    private ResourceParameter newResourceParameter;

    @Override
    @Produces
    @Named("newResourceParameter")
    ResourceParameter getNew() {
        if (newResourceParameter == null) {
            newResourceParameter = new ResourceParameter();
        }
        return newResourceParameter;
    }

    @Override
    void setNew(ResourceParameter entity) {
        newResourceParameter = entity;
    }

    @Override
    ResourceParameterService getService() {
        return service;
    }

    @Override
    public void update(ResourceParameter entity) {
        doUpdate(entity);
    }

    @Override
    public void delete(ResourceParameter entity) {
        doDelete(entity);
    }

    public void parameterize(Resource resource) {
        //TODO: Create and set parameters for the new resource
    }
}
