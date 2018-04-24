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
import is.stma.beanpoll.data.ContestRepo;
import is.stma.beanpoll.model.Contest;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.validation.ValidationException;

@Model
public class ContestRules extends AbstractRules<Contest> {

    @SuppressWarnings("CdiInjectionPointsInspection")
    @Inject
    private ContestRepo repo;

    @Override
    public AbstractRepo<Contest> getRepo() {
        return repo;
    }

    @Override
    public void runBusinessRules(Contest entity, Target target)
            throws ValidationException {
    }

    @Override
    void checkBeforeDelete(Contest entity) throws ValidationException {

        // Cannot delete a running contest
        checkNotRunning(entity);
    }

    private void checkNotRunning(Contest entity) throws ValidationException {

        // Check both the entity we are handed, and the canonical version of said entity
        if (entity.isRunning() || repo.findBy(entity.getId()).isRunning()) {
            throw new ValidationException("cannot delete a running contest");
        }

    }
}
