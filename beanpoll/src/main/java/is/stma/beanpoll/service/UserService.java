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
import is.stma.beanpoll.data.UserRepo;
import is.stma.beanpoll.model.Team;
import is.stma.beanpoll.model.User;
import is.stma.beanpoll.rules.UserRules;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class UserService extends AbstractService<User, AbstractRepo<User>,
        UserRules> {

    // Attempt to generate an unused username this many times before failing
    private final int MAX_ATTEMPTS = 3;

    @SuppressWarnings("CdiInjectionPointsInspection")
    @Inject
    private UserRepo repo;

    @Inject
    private Event<User> event;

    @Inject
    private UserRules rules;

    @Override
    AbstractRepo<User> getRepo() {
        return repo;
    }

    @Override
    Event<User> getEvent() {
        return event;
    }

    @Override
    UserRules getRules() {
        return rules;
    }

    public User getByName(String name) {
        return repo.findByName(name);
    }

    public List<User> getByTeam(Team team) {
        return repo.findByTeamId(team.getId());
    }

    public List<String> getAllUsernames() {
        return repo.findAllNames();
    }
}
