package is.stma.judgebean.validator;

import is.stma.judgebean.model.AEntity;
import org.apache.deltaspike.data.api.EntityRepository;

import javax.inject.Inject;
import javax.validation.ValidationException;
import java.text.DateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import static is.stma.judgebean.util.EntityUtility.prefix;

public abstract class AbstractValidator<E extends AEntity> {

    /* Injects ----------------------------------------------------------------------- */
    @Inject
    Logger log;

    /* Fields ------------------------------------------------------------------------ */
    final DateFormat df = DateFormat.getDateInstance();

    /* Abstract Methods -------------------------------------------------------------- */
    abstract EntityRepository<E, String> getRepo();

    abstract void runBusinessRules(E entity, Target target)
            throws ValidationException;

    abstract void checkBeforeDelete(E entity) throws ValidationException;

    /* Methods ----------------------------------------------------------------------- */
    public void validate(E entity, Target target) throws ValidationException {

        boolean collision = !isUuidUnique(entity);

        if (target == Target.CREATE && collision) {
            errorOut("Cannot create " + prefix(entity) + "; UUID exists");
        } else if ((target == Target.UPDATE || target == Target.DELETE) && !collision) {
            errorOut("Cannot update " + prefix(entity) + "; UUID not found");
        }

        if (target == Target.DELETE) {
            checkBeforeDelete(entity);
        } else {
            runBusinessRules(entity, target);
        }
    }

    /* Enum Type --------------------------------------------------------------------- */
    public enum Target {
        CREATE, UPDATE, DELETE
    }

    private boolean isUuidUnique(E entity) {
        return (getRepo().findBy(entity.getId()) == null);
    }

    void errorOut(String details) throws ValidationException {
        log.log(Level.WARNING, details);
        throw new ValidationException(details);
    }
}
