package is.stma.judgebean.validator;

import is.stma.judgebean.model.AEntity;
import is.stma.judgebean.util.BusinessRuleException;
import org.apache.deltaspike.data.api.EntityRepository;

import javax.inject.Inject;
import java.text.DateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import static is.stma.judgebean.util.EntityUtility.prefix;

public abstract class AValidator<E extends AEntity> {

    /* Injects ----------------------------------------------------------------------- */
    @Inject
    Logger log;

    /* Fields ------------------------------------------------------------------------ */
    final DateFormat df = DateFormat.getDateInstance();

    /* Abstract Methods -------------------------------------------------------------- */
    public abstract EntityRepository<E, String> getRepo();

    abstract void runBusinessRules(E entity, Target target)
            throws BusinessRuleException;

    abstract void checkBeforeDelete(E entity) throws BusinessRuleException;

    /* Methods ----------------------------------------------------------------------- */
    public void validate(E entity, Target target) throws BusinessRuleException {

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

    void errorOut(String details) throws BusinessRuleException {
        log.log(Level.WARNING, details);
        throw new BusinessRuleException(details);
    }
}
