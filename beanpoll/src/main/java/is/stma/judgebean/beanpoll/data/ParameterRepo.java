package is.stma.judgebean.beanpoll.data;

import is.stma.judgebean.beanpoll.model.Parameter;
import org.apache.deltaspike.data.api.Repository;

@SuppressWarnings("CdiManagedBeanInconsistencyInspection")
@Repository(forEntity = Parameter.class)
public interface ParameterRepo extends AbstractRepo<Parameter> {

}
