package is.stma.judgebean.beanpoll.rules;

import is.stma.judgebean.beanpoll.data.AbstractRepo;
import is.stma.judgebean.beanpoll.model.AbstractEntity;
import is.stma.judgebean.beanpoll.util.DateUtility;
import is.stma.judgebean.beanpoll.util.StringUtility;

import javax.inject.Inject;
import javax.validation.ValidationException;
import java.text.DateFormat;
import java.util.logging.Logger;

public abstract class AbstractRules<E extends AbstractEntity> {

    final DateFormat df = DateFormat.getDateInstance();

    @Inject
    Logger log;

    abstract AbstractRepo<E> getRepo();

    abstract void runBusinessRules(E entity, Target target)
            throws ValidationException;

    abstract void checkBeforeDelete(E entity) throws ValidationException;

    public void validate(E entity, Target target) throws ValidationException {

        boolean collision = !isUuidUnique(entity);

        if (target == Target.CREATE && collision) {
            throw new ValidationException("Cannot create " + entity.getName() + "; UUID exists");
        } else if ((target == Target.UPDATE || target == Target.DELETE) && !collision) {
            throw new ValidationException("Cannot update " + entity.getName() + "; UUID not found");
        }

        if (target == Target.DELETE) {
            checkBeforeDelete(entity);
        } else {
            StringUtility.checkString(entity.getName());
            runBusinessRules(entity, target);
        }
    }

    private boolean isUuidUnique(E entity) {
        return (getRepo().findBy(entity.getId()) == null);
    }

    public enum Target {
        CREATE, UPDATE, DELETE
    }
}
