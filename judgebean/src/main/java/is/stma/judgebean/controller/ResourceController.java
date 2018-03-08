package is.stma.judgebean.controller;

import is.stma.judgebean.model.Resource;
import is.stma.judgebean.rules.ResourceRules;
import is.stma.judgebean.service.ResourceService;

import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

@Model
public class ResourceController extends AbstractEntityController<Resource, ResourceRules,
        ResourceService> {

    /* Injects ----------------------------------------------------------------------- */
    @Inject
    private ResourceService service;

    /* Produces ---------------------------------------------------------------------- */
    private Resource newResource;

    /* Override Methods -------------------------------------------------------------- */
    @Override
    @Produces
    @Named("newResource")
    Resource getNew() {
        if (newResource == null) {
            newResource = new Resource();
        }
        return newResource;
    }

    @Override
    void setNew(Resource entity) {
        newResource = entity;
    }

    @Override
    ResourceService getService() {
        return service;
    }

    @Override
    public void update(Resource entity) {
        doUpdate(entity);
    }

    @Override
    public void delete(Resource entity) {
        doDelete(entity);
    }
}
