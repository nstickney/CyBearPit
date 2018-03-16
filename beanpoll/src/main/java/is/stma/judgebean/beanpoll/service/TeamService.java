package is.stma.judgebean.beanpoll.service;

import is.stma.judgebean.beanpoll.data.AbstractRepo;
import is.stma.judgebean.beanpoll.data.TeamRepo;
import is.stma.judgebean.beanpoll.model.Team;
import is.stma.judgebean.beanpoll.rules.TeamRules;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;

@Stateless
public class TeamService extends AbstractService<Team, AbstractRepo<Team>,
        TeamRules> {

    @SuppressWarnings("CdiInjectionPointsInspection")
    @Inject
    private TeamRepo repo;

    @Inject
    private Event<Team> event;

    @Inject
    private TeamRules rules;

    @Override
    AbstractRepo<Team> getRepo() {
        return repo;
    }

    @Override
    Event<Team> getEvent() {
        return event;
    }

    @Override
    TeamRules getRules() {
        return rules;
    }
}
