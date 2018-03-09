package is.stma.judgebean.beanpoll.data;

import is.stma.judgebean.beanpoll.model.Task;
import org.apache.deltaspike.data.api.Repository;

@SuppressWarnings("CdiManagedBeanInconsistencyInspection")
@Repository(forEntity = Task.class)
public interface TaskRepo extends AbstractRepo<Task> {

}
