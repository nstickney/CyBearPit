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

        if (Target.CREATE == target || Target.UPDATE == target) {

            // Team users must not be admins
            checkAdminUserNotAssignedToTeam(entity);
        }

    }

    @Override
    void checkBeforeDelete(User entity) throws ValidationException {

    }

    private void checkAdminUserNotAssignedToTeam(User entity) throws ValidationException {
        if (entity.isAdmin() && null != entity.getTeam()) {
            throw new ValidationException("Team users are not allowed to be admins");
        }
    }
}
