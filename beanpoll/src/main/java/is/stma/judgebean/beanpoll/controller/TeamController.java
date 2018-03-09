package is.stma.judgebean.beanpoll.controller;

import is.stma.judgebean.beanpoll.service.TeamService;
import is.stma.judgebean.beanpoll.model.Team;
import is.stma.judgebean.beanpoll.rules.TeamRules;

import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

@Model
public class TeamController extends AbstractEntityController<Team, TeamRules,
        TeamService> {

    @Inject
    private TeamService service;

    private Team newTeam;

    @Override
    @Produces
    @Named("newTeam")
    Team getNew() {
        if (newTeam == null) {
            newTeam = new Team();
        }
        return newTeam;
    }

    @Override
    void setNew(Team entity) {
        newTeam = entity;
    }

    @Override
    TeamService getService() {
        return service;
    }

    @Override
    public void update(Team entity) {
        doUpdate(entity);
    }

    @Override
    public void delete(Team entity) {
        doDelete(entity);
    }
}
