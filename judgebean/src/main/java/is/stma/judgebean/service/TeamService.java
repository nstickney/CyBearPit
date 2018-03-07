package is.stma.judgebean.service;

import is.stma.judgebean.data.TeamRepo;
import is.stma.judgebean.model.Team;
import is.stma.judgebean.validator.TeamValidator;
import org.apache.deltaspike.data.api.EntityRepository;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;

@Stateless
public class TeamService extends AbstractService<Team, EntityRepository<Team, String>,
        TeamValidator> {

    /* Injects ----------------------------------------------------------------------- */
    @Inject
    private TeamRepo repo;

    @Inject
    private Event<Team> event;

    @Inject
    private TeamValidator validator;

    @Override
    EntityRepository<Team, String> getRepo() {
        return repo;
    }

    @Override
    Event<Team> getEvent() {
        return event;
    }

    @Override
    TeamValidator getValidator() {
        return validator;
    }
}
