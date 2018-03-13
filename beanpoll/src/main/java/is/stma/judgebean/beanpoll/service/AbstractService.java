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
        getRepo().remove(entity);
        getEvent().fire(entity);
    }

}
