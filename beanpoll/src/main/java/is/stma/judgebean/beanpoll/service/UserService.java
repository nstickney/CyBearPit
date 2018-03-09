package is.stma.judgebean.beanpoll.service;

import is.stma.judgebean.beanpoll.data.AbstractRepo;
import is.stma.judgebean.beanpoll.data.UserRepo;
import is.stma.judgebean.beanpoll.model.User;
import is.stma.judgebean.beanpoll.rules.UserRules;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;

@Stateless
public class UserService extends AbstractService<User, AbstractRepo<User>,
        UserRules> {

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

    public User getByUsername(String username) {
        return repo.findByUsername(username);
    }
}
