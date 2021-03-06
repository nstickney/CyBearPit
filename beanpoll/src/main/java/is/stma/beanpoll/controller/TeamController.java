/*
 * Copyright 2018 Nathaniel Stickney
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package is.stma.beanpoll.controller;

import is.stma.beanpoll.model.Resource;
import is.stma.beanpoll.model.Response;
import is.stma.beanpoll.model.Team;
import is.stma.beanpoll.model.User;
import is.stma.beanpoll.rules.TeamRules;
import is.stma.beanpoll.service.TeamService;
import is.stma.beanpoll.service.UserService;

import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
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
    private ResponseController responseController;

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
        List<Response> responses = new ArrayList<>(entity.getResponses());
        for (Response r : responses) {
            r.setTeam(null);
            responseController.update(r);
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
