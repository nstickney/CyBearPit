package is.stma.judgebean.beanpoll.data;

import is.stma.judgebean.beanpoll.model.TaskResponse;
import org.apache.deltaspike.data.api.Repository;

@SuppressWarnings("CdiManagedBeanInconsistencyInspection")
@Repository(forEntity = TaskResponse.class)
public interface TaskResponseRepo extends AbstractRepo<TaskResponse> {

}
