package is.stma.judgebean.controller;

import is.stma.judgebean.model.Team;
import is.stma.judgebean.service.TeamService;
import is.stma.judgebean.validator.TeamValidator;

import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

@Model
public class TeamController extends AbstractEntityController<Team, TeamValidator,
        TeamService> {

    /* Injects ----------------------------------------------------------------------- */
    @Inject
    private TeamService service;

    /* Produces ---------------------------------------------------------------------- */
    private Team newTeam;

    /* Override Methods -------------------------------------------------------------- */
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
    void setNew(Team team) {
        newTeam = team;
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
