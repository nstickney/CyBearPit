/*
 * Copyright 2018 Nathaniel Stickney
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package is.stma.judgebean.beanpoll.controller;

import is.stma.judgebean.beanpoll.model.Parameter;
import is.stma.judgebean.beanpoll.model.Resource;
import is.stma.judgebean.beanpoll.model.Team;
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

    private Team addThisTeam;

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

    public Team getAddThisTeam() {
        return addThisTeam;
    }

    public void setAddThisTeam(Team addTeam) {
        this.addThisTeam = addTeam;
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
            created.setParameters(parameters);
            created = getService().update(created);
            setNew(null);
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

    public void addTeam(Resource entity) {
        if (null != entity) {
            if (null != addThisTeam) {
                if (!entity.getTeams().contains(addThisTeam)) {
                    entity.addTeam(addThisTeam);
                    addThisTeam = null;
                    update(entity);
                } else {
                    errorOut(new ValidationException(entity.getName() + " already includes " + addThisTeam.getName()),
                            "Cannot add team: ");
                }
            } else {
                errorOut(new ValidationException("team does not exist"),
                        "Cannot add team: ");
            }
        } else {
            errorOut(new ValidationException("resource does not exist"),
                    "Cannot add team: ");
        }
    }

    public void removeTeam(Resource entity, Team removeThisTeam) {
        if (null != entity) {
            if (null != removeThisTeam) {
                if (entity.getTeams().contains(removeThisTeam)) {
                    entity.removeTeam(removeThisTeam);
                    update(entity);
                } else {
                    errorOut(new ValidationException(entity.getName() + " does not include " + removeThisTeam.getName()),
                            "Cannot remove team: ");
                }
            } else {
                errorOut(new ValidationException("team does not exist"),
                        "Cannot remove team: ");
            }
        } else {
            errorOut(new ValidationException("resource does not exist"),
                    "Cannot remove team: ");
        }
    }

}
