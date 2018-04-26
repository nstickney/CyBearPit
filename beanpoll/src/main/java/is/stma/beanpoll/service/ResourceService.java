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

import is.stma.beanpoll.data.AbstractRepo;
import is.stma.beanpoll.data.ResourceRepo;
import is.stma.beanpoll.model.Parameter;
import is.stma.beanpoll.model.Poll;
import is.stma.beanpoll.model.Resource;
import is.stma.beanpoll.rules.AbstractRules;
import is.stma.beanpoll.rules.ResourceRules;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.validation.ValidationException;
import java.util.ArrayList;
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

    @Inject
    private PollService pollService;

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

    /**
     * Update a Resource. This is overridden here in order to attach a new set
     * of Parameters when the ResourceType of a Resource is changed.
     *
     * @param entity Resource to update
     * @return the updated Resource
     * @throws ValidationException if validation of entity or Parameters fails
     */
    @Override
    public Resource update(Resource entity) throws ValidationException {
        em.detach(entity);
        Resource before = getRepo().findBy(entity.getId());
        if (before.getType() != entity.getType()) {
            List<Parameter> oldParameters = before.getParameters();
            for (Parameter p : oldParameters) {
                parameterService.delete(p);
            }
            entity.setParameters(parameterService.createParameters(entity));
        }
        log.log(Level.INFO, "Updating " + before.getName());
        getRules().validate(entity, AbstractRules.Target.UPDATE);
        entity = getRepo().save(entity);
        log.log(Level.INFO, "Updated " + entity.getName());
        getEvent().fire(entity);
        return entity;
    }

    /**
     * Delete a Resource. This is overridden here to delete the Resource's
     * Parameters and any existing Poll data before the Resource is removed.
     *
     * @param entity the Resource to delete
     */
    @Override
    public void delete(Resource entity) {
        getRules().validate(entity, AbstractRules.Target.DELETE);
        log.log(Level.INFO, "Deleting " + entity.getName());
        List<Parameter> parameters = entity.getParameters();
        for (Parameter p : parameters) {
            parameterService.delete(p);
        }
        List<Poll> polls = entity.getPolls();
        for (Poll p : polls) {
            pollService.delete(p);
        }
        getRepo().remove(em.contains(entity) ? entity : em.merge(entity));
        getEvent().fire(entity);
    }

    public Resource clone(Resource entity) {

        // Make the resource and set its fields
        Resource clone = new Resource();
        clone.setName(entity.getName() + " CLONE");
        clone.setType(entity.getType());
        clone.setAddress(entity.getAddress());
        clone.setPort(entity.getPort());
        clone.setTimeout(entity.getTimeout());
        clone.setAvailable(entity.isAvailable());
        clone.setPointValue(entity.getPointValue());
        clone.setContest(entity.getContest());
        create(clone);

        // Remove the pre-generated parameters
        List<Parameter> badParameters = clone.getParameters();
        for (Parameter p : badParameters) {
            parameterService.delete(p);
        }
        List<Parameter> parameters = new ArrayList<>();
        clone.setParameters(parameters);
        clone = update(clone);

        // Copy the desired parameters
        for (Parameter p : entity.getParameters()) {
            parameters.add(parameterService.clone(p, clone));
        }
        clone.setParameters(parameters);
        clone = update(clone);

        return clone;
    }
}
