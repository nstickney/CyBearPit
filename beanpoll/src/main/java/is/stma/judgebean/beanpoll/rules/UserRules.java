package is.stma.judgebean.beanpoll.rules;

import is.stma.judgebean.beanpoll.data.UserRepo;
import is.stma.judgebean.beanpoll.model.User;
import org.apache.deltaspike.data.api.EntityRepository;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.validation.ValidationException;

@Model
public class UserRules extends AbstractRules<User> {

    @Inject
    private UserRepo repo;

    @Override
    public EntityRepository<User, String> getRepo() {
        return repo;
    }

    @Override
    public void runBusinessRules(User entity, Target target)
            throws ValidationException {
    }

    @Override
    void checkBeforeDelete(User entity) throws ValidationException {

    }
}
