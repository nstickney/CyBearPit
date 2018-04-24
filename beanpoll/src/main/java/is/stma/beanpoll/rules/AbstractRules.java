/*
 * Copyright 2018 Nathaniel Stickney
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package is.stma.beanpoll.rules;

import is.stma.beanpoll.data.AbstractRepo;
import is.stma.beanpoll.model.AbstractEntity;
import is.stma.beanpoll.util.StringUtility;

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
