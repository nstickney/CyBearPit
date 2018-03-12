package is.stma.judgebean.beanpoll.controller;

import is.stma.judgebean.beanpoll.model.Resource;
import is.stma.judgebean.beanpoll.rules.ResourceRules;
import is.stma.judgebean.beanpoll.service.ResourceService;

import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

@Model
public class ResourceController extends AbstractEntityController<Resource, ResourceRules,
        ResourceService> {

    @Inject
    private ResourceService service;

    @Inject
    private ResourceParameterController parameterController;

    private Resource newResource;

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

    @Produces
    @Named("newDNS")
    Resource getNewDNS() {
        Resource newDNS = getNew();
        newDNS.setType(Resource.DNS);
        parameterController.parameterize(newDNS);
        return newDNS;
    }

    @Produces
    @Named("newHTTP")
    Resource getNewHTTP() {
        Resource newHTTP = getNew();
        newHTTP.setType(Resource.HTTP);
        parameterController.parameterize(newHTTP);
        return newHTTP;
    }
}
