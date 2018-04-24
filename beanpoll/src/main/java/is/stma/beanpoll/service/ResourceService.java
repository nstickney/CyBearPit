/*
 * Copyright 2018 Nathaniel Stickney
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package is.stma.beanpoll.service;

import is.stma.beanpoll.data.ResourceRepo;
import is.stma.beanpoll.data.AbstractRepo;
import is.stma.beanpoll.model.Parameter;
import is.stma.beanpoll.model.Resource;
import is.stma.beanpoll.rules.AbstractRules;
import is.stma.beanpoll.rules.ResourceRules;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.validation.ValidationException;
import java.util.List;
import java.util.logging.Level;

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

    @Inject
    private ParameterService parameterService;

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

    /**
     * Persist a new Resource. This is overridden here in order to attach a new
     * set of Parameters to the Resource before the resource is persisted.
     */
    @Override
    public Resource create(Resource entity) {
        getRules().validate(entity, AbstractRules.Target.CREATE);
        log.log(Level.INFO, "Creating " + entity.getName());
        entity = getRepo().save(entity);
        getEvent().fire(entity);
        try {
            entity.setParameters(parameterService.createParameters(entity));
            update(entity);
        } catch (ValidationException e) {
            delete(entity);
            throw new ValidationException(e.getMessage());
        }
        return entity;
    }

    @Override
    public void delete(Resource entity) {
        getRules().validate(entity, AbstractRules.Target.DELETE);
        log.log(Level.INFO, "Deleting " + entity.getName());
        List<Parameter> parameters = entity.getParameters();
        for (Parameter p : parameters) {
            parameterService.delete(p);
        }
        getRepo().remove(em.contains(entity) ? entity : em.merge(entity));
        getEvent().fire(entity);
    }
}