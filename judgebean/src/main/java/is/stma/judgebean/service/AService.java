package is.stma.judgebean.service;

import is.stma.judgebean.model.AEntity;
import is.stma.judgebean.util.BusinessRuleException;
import is.stma.judgebean.validator.AValidator;
import org.apache.deltaspike.data.api.EntityRepository;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static is.stma.judgebean.util.EntityUtility.prefix;

public abstract class AService<E extends AEntity,
        R extends EntityRepository<E, String>,
        V extends AValidator<E>> {

    /* Injects ----------------------------------------------------------------------- */
    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    /* Abstract Methods -------------------------------------------------------------- */
    abstract R getRepo();

    abstract Event<E> getEvent();

    abstract V getValidator();

    /* Service Methods --------------------------------------------------------------- */
    public E create(E entity) throws BusinessRuleException {
        getValidator().validate(entity, AValidator.Target.CREATE);
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

    public E update(E entity) throws BusinessRuleException {
        em.detach(entity);
        log.log(Level.INFO, "Updating " + prefix(getRepo().findBy(entity.getId())));
        getValidator().validate(entity, AValidator.Target.UPDATE);
        entity = getRepo().save(entity);
        log.log(Level.INFO, "Updated " + prefix(entity));
        getEvent().fire(entity);
        return entity;
    }

    public void delete(E entity) throws BusinessRuleException {
        getValidator().validate(entity, AValidator.Target.DELETE);
        log.log(Level.INFO, "Deleting " + prefix(entity));
        getRepo().remove(entity);
        getEvent().fire(entity);
    }

}
