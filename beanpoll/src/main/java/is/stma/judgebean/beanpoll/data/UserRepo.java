package is.stma.judgebean.beanpoll.data;

import is.stma.judgebean.beanpoll.model.User;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@SuppressWarnings("CdiManagedBeanInconsistencyInspection")
@Repository(forEntity = User.class)
public interface UserRepo extends AbstractRepo<User> {

    User findByName(String name);

    List<User> findByAdmin(boolean admin);
}
