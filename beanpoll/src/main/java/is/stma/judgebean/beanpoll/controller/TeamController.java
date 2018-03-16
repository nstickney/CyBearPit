package is.stma.judgebean.beanpoll.controller;

import is.stma.judgebean.beanpoll.model.Resource;
import is.stma.judgebean.beanpoll.model.TaskResponse;
import is.stma.judgebean.beanpoll.model.Team;
import is.stma.judgebean.beanpoll.model.User;
import is.stma.judgebean.beanpoll.rules.TeamRules;
import is.stma.judgebean.beanpoll.service.TeamService;
import is.stma.judgebean.beanpoll.service.UserService;
import is.stma.judgebean.beanpoll.util.PasswordUtility;
import is.stma.judgebean.beanpoll.util.StringUtility;

import javax.ejb.EJBException;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;

@Model
public class TeamController extends AbstractEntityController<Team, TeamRules,
        TeamService> {

    @Inject
    private TeamService service;

    private Team newTeam;

    @Inject
    private ResourceController resourceController;

    @Inject
    private TaskResponseController taskResponseController;

    @Inject
    private UserService userService;

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

            // Team will need a user to access the system
            User teamUser = new User();

            // The new user will have the same name as the team
            teamUser.setName(getNew().getName());

            // The new password will be the team name, minus all whitespace, repeated the minimum
            // number of times to create a string at least PasswordUtility.MINIMUM_LENGTH long
            String teamString = StringUtility.removeWhitespace(getNew().getName());
            StringBuilder buf = new StringBuilder();
            while (! teamUser.setPassword(buf.toString())) {
                buf.append(teamString);
            }

            // Create the team and set the user's team to the newly created team
            teamUser.setTeam(getService().create(getNew()));

            // Create the user
            userService.create(teamUser);
            messageOut(getNew().getLogName() + " created.");
        } catch (EJBException | ValidationException e) {
            errorOut(e, "Failed to create " + getNew().getLogName() + ": ");
        } catch (Exception e) {
            errorOut(e, getNew().getLogName() + " creation failed: ");
        }
    }

    @Override
    public void update(Team entity) {
        doUpdate(entity);
    }

    @Override
    public void delete(Team entity) {

        // Remove the team from all resources' team lists
        List<Resource> resources = new ArrayList<>(entity.getResources());
        for (Resource r : resources) {
            r.removeTeam(entity);
            resourceController.update(r);
        }

        // Orphan all the team's task responses
        List<TaskResponse> responses = new ArrayList<>(entity.getTaskResponses());
        for (TaskResponse r : responses) {
            r.setTeam(null);
            taskResponseController.update(r);
        }

        // Orphan all team users
        List<User> users = new ArrayList<>(entity.getUsers());
        for (User u : users) {
            u.setTeam(null);
            userService.update(u);
        }

        // Remove the team itself
        doDelete(entity);
    }
}
