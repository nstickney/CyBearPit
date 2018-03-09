package is.stma.judgebean.beanpoll.data;

import is.stma.judgebean.beanpoll.model.Resource;
import org.apache.deltaspike.data.api.Repository;

@SuppressWarnings("CdiManagedBeanInconsistencyInspection")
@Repository(forEntity = Resource.class)
public interface ResourceRepo extends AbstractRepo<Resource> {

}