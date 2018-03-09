package is.stma.judgebean.beanpoll.data;

import is.stma.judgebean.beanpoll.model.ResourceParameter;
import org.apache.deltaspike.data.api.Repository;

@SuppressWarnings("CdiManagedBeanInconsistencyInspection")
@Repository(forEntity = ResourceParameter.class)
public interface ResourceParameterRepo extends AbstractRepo<ResourceParameter> {

}
