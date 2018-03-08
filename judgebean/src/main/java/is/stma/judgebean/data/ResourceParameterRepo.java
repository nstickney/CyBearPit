package is.stma.judgebean.data;

import is.stma.judgebean.model.ResourceParameter;
import org.apache.deltaspike.data.api.Repository;

@SuppressWarnings("CdiManagedBeanInconsistencyInspection")
@Repository(forEntity = ResourceParameter.class)
public interface ResourceParameterRepo extends AbstractRepo<ResourceParameter> {

}
