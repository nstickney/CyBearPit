package is.stma.judgebean.rules;

import is.stma.judgebean.data.TeamRepo;
import is.stma.judgebean.model.Team;
import org.apache.deltaspike.data.api.EntityRepository;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.validation.ValidationException;

@Model
public class TeamRules extends AbstractRules<Team> {

    @Inject
    private TeamRepo repo;

    @Override
    public EntityRepository<Team, String> getRepo() {
        return repo;
    }

    @Override
    public void runBusinessRules(Team entity, Target target)
            throws ValidationException {
    }

    @Override
    void checkBeforeDelete(Team entity) throws ValidationException {

    }
}
