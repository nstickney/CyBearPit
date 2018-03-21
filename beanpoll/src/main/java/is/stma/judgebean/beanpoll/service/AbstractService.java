/*
 * Copyright 2018 Nathaniel Stickney
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package is.stma.judgebean.beanpoll.service;

import is.stma.judgebean.beanpoll.data.AbstractRepo;
import is.stma.judgebean.beanpoll.model.AbstractEntity;
import is.stma.judgebean.beanpoll.rules.AbstractRules;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.validation.ValidationException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractService<E extends AbstractEntity,
        R extends AbstractRepo<E>,
        V extends AbstractRules<E>> {

    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    abstract R getRepo();

    abstract Event<E> getEvent();

    abstract V getRules();

    public E create(E entity) throws ValidationException {
        getRules().validate(entity, AbstractRules.Target.CREATE);
        log.log(Level.INFO, "Creating " + entity.getName());
        entity = getRepo().save(entity);
        getEvent().fire(entity);
        return entity;
    }

    public List<E> readAll() {
        return getRepo().findAll();
    }

    public E readById(String id) {
        return getRepo().findBy(id);
    }

    public E update(E entity) throws ValidationException {
        em.detach(entity);
        log.log(Level.INFO, "Updating " + getRepo().findBy(entity.getId()).getName());
        getRules().validate(entity, AbstractRules.Target.UPDATE);
        entity = getRepo().save(entity);
        log.log(Level.INFO, "Updated " + entity.getName());
        getEvent().fire(entity);
        return entity;
    }

    public void delete(E entity) throws ValidationException {
        getRules().validate(entity, AbstractRules.Target.DELETE);
        log.log(Level.INFO, "Deleting " + entity.getName());
        getRepo().remove(em.contains(entity) ? entity : em.merge(entity));
        getEvent().fire(entity);
    }

}
