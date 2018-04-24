/*
 * Copyright 2018 Nathaniel Stickney
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package is.stma.beanpoll.rules;

import is.stma.beanpoll.data.UserRepo;
import is.stma.beanpoll.data.AbstractRepo;
import is.stma.beanpoll.model.User;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.validation.ValidationException;

@Model
public class UserRules extends AbstractRules<User> {

    @SuppressWarnings("CdiInjectionPointsInspection")
    @Inject
    private UserRepo repo;

    @Override
    public AbstractRepo<User> getRepo() {
        return repo;
    }

    @Override
    public void runBusinessRules(User entity, Target target)
            throws ValidationException {

        // Username must be unique on creation
        if (Target.CREATE == target) {
            checkUniqueUsername(entity);
        }

        // Team users must not be admins
        checkAdminUserNotAssignedToTeam(entity);

        // Team users must not be judges
        checkJudgeUserNotAssignedToTeam(entity);
    }

    @Override
    void checkBeforeDelete(User entity) throws ValidationException {

        // Don't delete the last admin user
        checkNotLastAdmin(entity);

        // Don't delete the last user in a team
        checkNotLastUserOfTeam(entity);
    }

    private void checkUniqueUsername(User entity) throws ValidationException {
        for (User u : repo.findAll()) {
            if (u.getName().equals(entity.getName())) {
                throw new ValidationException("username must be unique");
            }
            if (u.getDisplayName().equals(entity.getDisplayName())) {
                throw new ValidationException("username is not allowed");
            }
        }
    }

    private void checkAdminUserNotAssignedToTeam(User entity) throws ValidationException {
        if (entity.isAdmin() && null != entity.getTeam()) {
            throw new ValidationException("team users are not allowed to be admins");
        }
    }

    private void checkJudgeUserNotAssignedToTeam(User entity) throws ValidationException {
        if (entity.isJudge() && null != entity.getTeam()) {
            throw new ValidationException("team users are not allowed to be judges");
        }
    }

    private void checkNotLastAdmin(User entity) {
        if (entity.isAdmin() && 1 >= repo.findByAdmin(true).size()) {
            throw new ValidationException(entity.getName() + " is the last administrator");
        }
    }

    private void checkNotLastUserOfTeam(User entity) {
        if (null != entity.getTeam() && 1 >= entity.getTeam().getUsers().size()) {
            throw new ValidationException("team " + entity.getTeam().getName() + " has no other users");
        }
    }
}
