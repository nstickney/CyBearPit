package is.stma.judgebean.beanpoll.rules;

import is.stma.judgebean.beanpoll.data.AbstractRepo;
import is.stma.judgebean.beanpoll.data.TeamRepo;
import is.stma.judgebean.beanpoll.model.Team;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.validation.ValidationException;
import java.util.Objects;

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
            for (Team team : entity.getContest().getTeams()) {
                errorOut("Found another team");
                if (Objects.equals(team.getName(), entity.getName())) {
                    errorOut("Team name must be unique in the contest");
                }
                if (Objects.equals(team.getFlag(), entity.getFlag())) {
                    errorOut("Team flag must be unique in the contest");
                }
            }
        }
    }

    @Override
    void checkBeforeDelete(Team entity) throws ValidationException {

    }

}
