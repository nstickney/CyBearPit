package is.stma.judgebean.beanpoll.data;

import is.stma.judgebean.beanpoll.model.User;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@RequestScoped
public class UserListProducer extends AbstractEntityListProducer<User> {

    @Inject
    private UserRepo repo;

    private List<User> entities;

    @Produces
    @Named("users")
    public List<User> getEntities() {
        return entities;
    }

    @Override
    @PostConstruct
    void retrieveAll() {
        entities = repo.findAllOrderByNameAsc();
    }

    @Produces
    @Named("unassignedUsers")
    public List<User> findUnassigned() {
        List<User> unassigned = new ArrayList<>();
        for (User entity : entities) {
            if (null == entity.getTeam() && !entity.isAdmin()) {
                unassigned.add(entity);
            }
        }
        return unassigned;
    }

    @Produces
    @Named("adminUsers")
    public List<User> findAdmins() {
        List<User> unassigned = new ArrayList<>();
        for (User entity : entities) {
            if (entity.isAdmin()) {
                unassigned.add(entity);
            }
        }
        return unassigned;
    }


}
