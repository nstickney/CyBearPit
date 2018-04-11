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
import is.stma.judgebean.beanpoll.data.CapturedRepo;
import is.stma.judgebean.beanpoll.model.Capturable;
import is.stma.judgebean.beanpoll.model.Captured;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.validation.ValidationException;
import java.util.List;

@Model
public class CapturedRules extends AbstractRules<Captured> {

    @Inject
    private CapturedRepo repo;

    @Override
    public AbstractRepo<Captured> getRepo() {
        return repo;
    }

    @Override
    public void runBusinessRules(Captured entity, Target target)
            throws ValidationException {

        // Capturable must be available
        checkCapturableIsAvailable(entity);

        // Team must be from correct contest (same contest as capturable)
        checkTeamIsInContest(entity);

        // Captured flag must be correct
        checkFlagIsCorrect(entity);

        // Check score correct
        checkScoreIsCorrect(entity);

        // Check this team hasn't already scored for this capturable
        checkSingleScoring(entity);
    }

    @Override
    void checkBeforeDelete(Captured entity) throws ValidationException {

    }

    private void checkCapturableIsAvailable(Captured entity) {
        if (!entity.getCapturable().getContest().isEnabled() || !entity.getCapturable().getContest().isRunning()) {
            throw new ValidationException("contest " + entity.getCapturable().getContest().getName()
                    + " is not running");
        }
    }

    private void checkTeamIsInContest(Captured entity) {
        if (!entity.getTeam().getContest().equalByUUID(entity.getCapturable().getContest())) {
            throw new ValidationException("team " + entity.getTeam().getName() + " is not in contest "
                    + entity.getCapturable().getContest().getName());
        }
    }

    private void checkFlagIsCorrect(Captured entity) throws ValidationException {
        if (!entity.getCapturable().getFlag().equals(entity.getFlag())) {
            throw new ValidationException("flag " + entity.getFlag() + " is incorrect");
        }
    }

    private void checkScoreIsCorrect(Captured entity) throws ValidationException {
        if (entity.getScore() != entity.getCapturable().getPointValue()) {
            throw new ValidationException("score (" + entity.getScore() + ") should be "
                    + entity.getCapturable().getPointValue());
        }
    }

    private void checkSingleScoring(Captured entity) throws ValidationException {
        for (Captured c : entity.getCapturable().getCapturedBy()) {
            if (c.getTeam().equalByUUID(entity.getTeam()) && c.getCapturable().equalByUUID(entity.getCapturable())) {
                throw new ValidationException("team " + entity.getTeam() + " has already captured "
                        + entity.getCapturable().getName());
            }
        }

    }
}
