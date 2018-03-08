package is.stma.judgebean.controller;

import is.stma.judgebean.model.ResourceParameter;
import is.stma.judgebean.rules.ResourceParameterRules;
import is.stma.judgebean.service.ResourceParameterService;

import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

@Model
public class ResourceParameterController extends AbstractEntityController<ResourceParameter, ResourceParameterRules,
        ResourceParameterService> {

    /* Injects ----------------------------------------------------------------------- */
    @Inject
    private ResourceParameterService service;

    /* Produces ---------------------------------------------------------------------- */
    private ResourceParameter newResourceParameter;

    /* Override Methods -------------------------------------------------------------- */
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
}
