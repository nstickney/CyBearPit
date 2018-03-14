package is.stma.judgebean.beanpoll.controller;

import is.stma.judgebean.beanpoll.controller.parameterizer.DNSParameterizer;
import is.stma.judgebean.beanpoll.controller.parameterizer.HTTPParameterizer;
import is.stma.judgebean.beanpoll.model.Parameter;
import is.stma.judgebean.beanpoll.model.Resource;
import is.stma.judgebean.beanpoll.rules.ParameterRules;
import is.stma.judgebean.beanpoll.service.ParameterService;

import javax.ejb.EJBException;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.ValidationException;
import java.util.ArrayList;
import java.util.List;

@Model
public class ParameterController extends AbstractEntityController<Parameter, ParameterRules,
        ParameterService> {

    @Inject
    private ParameterService service;

    private Parameter newParameter;

    @Override
    @Produces
    @Named("newParameter")
    Parameter getNew() {
        if (newParameter == null) {
            newParameter = new Parameter();
        }
        return newParameter;
    }

    @Override
    void setNew(Parameter entity) {
        newParameter = entity;
    }

    @Override
    ParameterService getService() {
        return service;
    }

    @Override
    public void update(Parameter entity) {
        doUpdate(entity);
    }

    @Override
    public void delete(Parameter entity) {
        doDelete(entity);
    }

    public List<Parameter> createParameters(Resource resource) throws ValidationException {

        // Create the correct parameters for the resource
        List<Parameter> parameters;
        switch (resource.getType()) {
            case Resource.DNS:
                parameters = DNSParameterizer.createParameters();
                break;
            case Resource.HTTP:
                parameters = HTTPParameterizer.createParameters();
                break;
            default:
                throw new ValidationException("unknown resource type " + resource.getType());
        }

        // Attempt to persist all created parameters
        List<Parameter> createdParameters = new ArrayList<>();
        try {
            for (Parameter p : parameters) {
                setNew(p);
                getService().create(p);
                createdParameters.add(p);
            }

            // If there is a problem, undo everything done so far
        } catch (EJBException e) {
            for (Parameter p : createdParameters) {
                delete(p);
            }
            throw new ValidationException("could not create resource parameters");
        }

        // Return the created list
        return createdParameters;
    }

    public void updateSilent(Parameter parameter) {
        getService().update(parameter);
    }

    public void deleteSilent(Parameter parameter) {
        getService().delete(parameter);
    }
}
