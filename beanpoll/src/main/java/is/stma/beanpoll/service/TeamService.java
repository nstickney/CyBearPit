/*
 * Copyright 2018 Nathaniel Stickney
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package is.stma.beanpoll.service;

import is.stma.beanpoll.data.AbstractRepo;
import is.stma.beanpoll.data.TeamRepo;
import is.stma.beanpoll.model.Team;
import is.stma.beanpoll.model.User;
import is.stma.beanpoll.rules.AbstractRules;
import is.stma.beanpoll.rules.TeamRules;
import is.stma.beanpoll.util.StringUtility;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.validation.ValidationException;
import java.util.List;
import java.util.logging.Level;

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

    @Inject
    private UserService userService;

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

    /**
     * This is overridden from AbstractService in order to create a user for the
     * Team and attach it after the Team is persisted.
     *
     * @param entity Team to create
     * @return the created Team
     * @throws ValidationException if there is an error in the Team fields
     */
    @Override
    public Team create(Team entity) throws ValidationException {
        getRules().validate(entity, AbstractRules.Target.CREATE);
        log.log(Level.INFO, "Creating " + entity.getName());
        entity = getRepo().save(entity);
        getEvent().fire(entity);

        // The username (and password) of team user are based on team name
        String teamString = StringUtility.removeWhitespace(entity.getName());
        User user = new User();

        // Find an unused username
        int iteration = 1;
        StringBuilder buf1 = new StringBuilder().append(teamString);
        while (userService.getAllUsernames().contains(buf1.toString())) {
            buf1 = new StringBuilder().append(teamString);
            buf1.append(iteration);
        }
        user.setName(buf1.toString());

        // Find a password
        StringBuilder buf2 = new StringBuilder();
        while (!user.setPassword(buf2.toString())) {
            buf2.append(teamString);
        }

        user.setTeam(entity);
        userService.create(user);
        return entity;
    }

    /**
     * This is overridden here from AbstractService in order to delete team
     * users before the team is removed.
     *
     * @param entity Team to delete
     * @throws ValidationException if the Team cannot be deleted
     */
    @Override
    public void delete(Team entity) throws ValidationException {
        getRules().validate(entity, AbstractRules.Target.DELETE);
        log.log(Level.INFO, "Deleting " + entity.getName());
        List<User> users = userService.getByTeam(entity);
        for (User u : users) {
            userService.delete(u);
        }
        getRepo().remove(em.contains(entity) ? entity : em.merge(entity));
        getEvent().fire(entity);
    }
}
