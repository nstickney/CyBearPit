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
import is.stma.beanpoll.data.TeamRepo;
import is.stma.beanpoll.model.Team;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.validation.ValidationException;
import java.util.List;

@Model
public class TeamRules extends AbstractRules<Team> {

    @Inject
    private TeamRepo repo;

    @Override
    public AbstractRepo<Team> getRepo() {
        return repo;
    }

    @Override
    public void runBusinessRules(Team entity, Target target)
            throws ValidationException {

        if (Target.CREATE == target || Target.UPDATE == target) {

            // Team flags must be unique within a Contest
            checkTeamNameAndFlagAreUniqueInContest(entity);
        }
    }

    private void checkTeamNameAndFlagAreUniqueInContest(Team entity) throws ValidationException {
        if (null != entity.getContest()) {
            List<Team> others = repo.findByContest(entity.getContest().getId());
            for (Team t : others) {
                if (!t.equalByUUID(entity)) {
                    if (t.getFlag().equals(entity.getFlag())) {
                        throw new ValidationException("a team in contest " + entity.getContest().getName() + " already has flag " + entity.getFlag());
                    }
                    if (t.getName().equals(entity.getName())) {
                        throw new ValidationException("a team in contest " + entity.getContest().getName() + " already has name " + entity.getName());
                    }
                }
            }
        }
    }

    @Override
    void checkBeforeDelete(Team entity) throws ValidationException {

    }

}
