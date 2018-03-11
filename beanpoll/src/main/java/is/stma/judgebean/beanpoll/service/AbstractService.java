package is.stma.judgebean.beanpoll.service;

import is.stma.judgebean.beanpoll.data.AbstractRepo;
import is.stma.judgebean.beanpoll.model.AbstractEntity;
import is.stma.judgebean.beanpoll.rules.AbstractRules;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.validation.ValidationException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static is.stma.judgebean.beanpoll.util.EntityUtility.prefix;

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
        log.log(Level.INFO, "Creating " + prefix(entity));
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
        if (null != entity) {
            try {
                log.log(Level.INFO, "Updating " + prefix(getRepo().findBy(entity.getId())));
            } catch (NoResultException | NullPointerException e) {
                throw new ValidationException("Cannot update nonexistent entity");
            }
            em.detach(entity);
            getRules().validate(entity, AbstractRules.Target.UPDATE);
            entity = getRepo().save(entity);
            log.log(Level.INFO, "Updated " + prefix(entity));
            getEvent().fire(entity);
            return entity;
        }
        throw new ValidationException("Cannot update nonexistent entity");
    }

    public void delete(E entity) throws ValidationException {
        getRules().validate(entity, AbstractRules.Target.DELETE);
        log.log(Level.INFO, "Deleting " + prefix(entity));
        getRepo().remove(entity);
        getEvent().fire(entity);
    }

}