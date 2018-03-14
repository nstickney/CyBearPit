package is.stma.judgebean.beanpoll.rules;

import is.stma.judgebean.beanpoll.data.AbstractRepo;
import is.stma.judgebean.beanpoll.data.UserRepo;
import is.stma.judgebean.beanpoll.model.User;

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
