/*
 * Copyright 2018 Nathaniel Stickney
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package is.stma.beanpoll.controller;

import is.stma.beanpoll.model.Resource;
import is.stma.beanpoll.model.Team;
import is.stma.beanpoll.rules.ResourceRules;
import is.stma.beanpoll.service.ResourceService;

import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ValidationException;

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

    @Override
    public void update(Resource entity) {
        doUpdate(entity);
    }

    @Override
    public void delete(Resource entity) {
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
