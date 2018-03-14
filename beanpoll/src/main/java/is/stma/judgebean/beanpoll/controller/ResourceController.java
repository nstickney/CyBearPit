package is.stma.judgebean.beanpoll.controller;

import is.stma.judgebean.beanpoll.model.Parameter;
import is.stma.judgebean.beanpoll.model.Resource;
import is.stma.judgebean.beanpoll.rules.ResourceRules;
import is.stma.judgebean.beanpoll.service.ResourceService;

import javax.ejb.EJBException;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ValidationException;
import java.util.List;

@Model
public class ResourceController extends AbstractEntityController<Resource, ResourceRules,
        ResourceService> {

    @Inject
    private ResourceService service;

    @Inject
    private ParameterController parameterController;

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

    /**
     * Persist the Resource from getNew(). This is overridden here in order to
     * attach a new set of Parameters to the Resource before the resource is persisted.
     */
    @Override
    public void create() {
        List<Parameter> parameters = null;
        Resource created = null;
        try {
            parameters = parameterController.createParameters(getNew());
            created = getService().create(getNew());
            for (Parameter p : parameters) {
                p.setResource(created);
                parameterController.updateSilent(p);
            }
            messageOut(getNew().getLogName() + " created.");
        } catch (Exception e) {

            // Clean up anything we've created so far
            if (null != parameters) {
                for (Parameter p : parameters) {
                    parameterController.deleteSilent(p);
                }
            }
            if (null != created) {
                getService().delete(created);
            }

            // Give a useful error message
            if (EJBException.class == e.getClass() || ValidationException.class == e.getClass()) {
                errorOut(e, "Failed to create " + getNew().getLogName() + ": ");
            } else {
                errorOut(e, getNew().getLogName() + " creation failed: ");
            }
        }
    }

    @Override
    public void update(Resource entity) {
        doUpdate(entity);
    }

    @Override
    public void delete(Resource entity) {
        List<Parameter> parameters = entity.getParameters();
        for (Parameter p : parameters) {
            parameterController.deleteSilent(p);
        }
        doDelete(entity);
    }

}
