package is.stma.judgebean.service;

import is.stma.judgebean.data.AbstractRepo;
import is.stma.judgebean.data.TeamRepo;
import is.stma.judgebean.model.Team;
import is.stma.judgebean.rules.TeamRules;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;

@Stateless
public class TeamService extends AbstractService<Team, AbstractRepo<Team>,
        TeamRules> {

    @Inject
    private TeamRepo repo;

    @Inject
    private Event<Team> event;

    @Inject
    private TeamRules validator;

    @Override
    AbstractRepo<Team> getRepo() {
        return repo;
    }

    @Override
    Event<Team> getEvent() {
        return event;
    }

    @Override
    TeamRules getValidator() {
        return validator;
    }
}
