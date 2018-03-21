/*
 * Copyright 2018 Nathaniel Stickney
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package is.stma.judgebean.beanpoll.rules;

import is.stma.judgebean.beanpoll.data.AbstractRepo;
import is.stma.judgebean.beanpoll.data.ResourceRepo;
import is.stma.judgebean.beanpoll.model.Resource;
import is.stma.judgebean.beanpoll.model.Team;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.validation.ValidationException;

@Model
public class ResourceRules extends AbstractRules<Resource> {

    @Inject
    private ResourceRepo repo;

    @Override
    public AbstractRepo<Resource> getRepo() {
        return repo;
    }

    @Override
    public void runBusinessRules(Resource entity, Target target)
            throws ValidationException {

        // Teams assigned to this resource must be from this resource's contest
        checkTeamsMatchContest(entity);
    }

    @Override
    void checkBeforeDelete(Resource entity) throws ValidationException {

    }

    private void checkTeamsMatchContest(Resource entity) throws ValidationException {
        for (Team t : entity.getTeams()) {
            if (!t.getContest().equalByUUID(entity.getContest())) {
                throw new ValidationException("only teams from " + entity.getContest().getName()
                        + " may be assigned to " + entity.getName());
            }
        }
    }
}
