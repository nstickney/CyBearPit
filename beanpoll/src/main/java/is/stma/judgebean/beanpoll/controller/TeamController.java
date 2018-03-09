package is.stma.judgebean.beanpoll.controller;

import is.stma.judgebean.beanpoll.model.User;
import is.stma.judgebean.beanpoll.service.TeamService;
import is.stma.judgebean.beanpoll.model.Team;
import is.stma.judgebean.beanpoll.rules.TeamRules;
import is.stma.judgebean.beanpoll.util.HashUtility;

import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import java.util.UUID;

import static is.stma.judgebean.beanpoll.util.EntityUtility.prefix;

@Model
public class TeamController extends AbstractEntityController<Team, TeamRules,
        TeamService> {

    @Inject
    private TeamService service;

    private Team newTeam;

    @Inject
    private UserController userController;

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

    /**
     * Persist the Team from getNew(). This is overridden here in order to
     * attach a new User to the Team before the team is persisted.
     */
    @Override
    public void create() {
        try {
            String teamString = getNew().getName().replaceAll("[^A-Za-z0-9]+", "");
            userController.getNew().setUsername(teamString);
            userController.getNew().setSalt(UUID.randomUUID().toString());
            userController.getNew().setHashedPassword(teamString);
            userController.create();
            setNew(getService().create(getNew()));
            facesContext.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO, prefix(getNew()) + " created.",
                    "")
            );
        } catch (Exception e) {
            errorOut(e, prefix(getNew()) + " creation failed.");
        }
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
