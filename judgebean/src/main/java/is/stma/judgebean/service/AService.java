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

public abstract class AService<Entity extends AEntity,
        Repository extends EntityRepository<Entity, String>,
        Validator extends AValidator<Entity>> {

    /* Injects ----------------------------------------------------------------------- */
    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    /* Abstract Methods -------------------------------------------------------------- */
    abstract Repository getRepo();

    abstract Event<Entity> getEvent();

    abstract Validator getValidator();

    /* Service Methods --------------------------------------------------------------- */
    public Entity create(Entity entity) throws BusinessRuleException {
        getValidator().validate(entity, AValidator.Target.CREATE);
        log.log(Level.INFO, "Creating " + prefix(entity));
        entity = getRepo().save(entity);
        getEvent().fire(entity);
        return entity;
    }

    public List<Entity> readAll() {
        return getRepo().findAll();
    }

    public Entity readById(String id) {
        return getRepo().findBy(id);
    }

    public Entity update(Entity entity) throws BusinessRuleException {
        em.detach(entity);
        log.log(Level.INFO, "Updating " + prefix(getRepo().findBy(entity.getId())));
        getValidator().validate(entity, AValidator.Target.UPDATE);
        entity = getRepo().save(entity);
        log.log(Level.INFO, "Updated " + prefix(entity));
        getEvent().fire(entity);
        return entity;
    }

    public void delete(Entity entity) throws BusinessRuleException {
        getValidator().validate(entity, AValidator.Target.DELETE);
        log.log(Level.INFO, "Deleting " + prefix(entity));
        getRepo().remove(entity);
        getEvent().fire(entity);
    }

}
