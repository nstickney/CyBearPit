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
import is.stma.judgebean.beanpoll.data.ResponseRepo;
import is.stma.judgebean.beanpoll.model.Response;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.validation.ValidationException;

@Model
public class ResponseRules extends AbstractRules<Response> {

    @Inject
    private ResponseRepo repo;

    @Override
    public AbstractRepo<Response> getRepo() {
        return repo;
    }

    @Override
    public void runBusinessRules(Response entity, Target target)
            throws ValidationException {

        // Task must be available
        checkTaskIsAvailable(entity);

        // Team must be from correct contest
        checkTeamIsInContest(entity);

        // Check score is within allowable range
        checkScoreWithinTaskPointValue(entity);
    }

    @Override
    void checkBeforeDelete(Response entity) throws ValidationException {

    }

    private void checkTaskIsAvailable(Response entity) {
        if (!entity.getTask().getContest().isEnabled() || !entity.getTask().getContest().isRunning()) {
            throw new ValidationException("contest " + entity.getTask().getContest().getName() + " is not running");
        }
        if (entity.getTask().getPublished().isAfter(entity.getTimestamp())) {
            throw new ValidationException("task " + entity.getTask().getName() + " is not yet available");
        }
        if (entity.getTask().getDue().isBefore(entity.getTimestamp())) {
            throw new ValidationException("task " + entity.getTask().getName() + " has expired");
        }
    }

    private void checkTeamIsInContest(Response entity) {
        if (!entity.getTeam().getContest().equalByUUID(entity.getTask().getContest())) {
            throw new ValidationException("team " + entity.getTeam().getName() + " is not in contest " + entity.getTask().getContest().getName());
        }
    }

    private void checkScoreWithinTaskPointValue(Response entity) throws ValidationException {
        if (entity.getScore() < -entity.getTask().getPointValue()
                || entity.getScore() > entity.getTask().getPointValue()) {
            throw new ValidationException("score " + entity.getScore() + " is not within plus or minus"
                    + entity.getTask().getPointValue() + " of zero");
        }
    }
}
