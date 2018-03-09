package is.stma.judgebean.beanpoll.rules;

import is.stma.judgebean.beanpoll.model.AbstractEntity;
import is.stma.judgebean.beanpoll.util.EntityUtility;
import org.apache.deltaspike.data.api.EntityRepository;

import javax.inject.Inject;
import javax.validation.ValidationException;
import java.text.DateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractRules<E extends AbstractEntity> {

    final DateFormat df = DateFormat.getDateInstance();

    @Inject
    Logger log;

    abstract EntityRepository<E, String> getRepo();

    abstract void runBusinessRules(E entity, Target target)
            throws ValidationException;

    abstract void checkBeforeDelete(E entity) throws ValidationException;

    public void validate(E entity, Target target) throws ValidationException {

        boolean collision = !isUuidUnique(entity);

        if (target == Target.CREATE && collision) {
            errorOut("Cannot create " + EntityUtility.prefix(entity) + "; UUID exists");
        } else if ((target == Target.UPDATE || target == Target.DELETE) && !collision) {
            errorOut("Cannot update " + EntityUtility.prefix(entity) + "; UUID not found");
        }

        if (target == Target.DELETE) {
            checkBeforeDelete(entity);
        } else {
            runBusinessRules(entity, target);
        }
    }

    private boolean isUuidUnique(E entity) {
        return (getRepo().findBy(entity.getId()) == null);
    }

    void errorOut(String details) throws ValidationException {
        log.log(Level.WARNING, details);
        throw new ValidationException(details);
    }

    public enum Target {
        CREATE, UPDATE, DELETE
    }
}
