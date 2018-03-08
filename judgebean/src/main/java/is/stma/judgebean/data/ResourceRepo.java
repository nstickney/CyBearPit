package is.stma.judgebean.data;

import is.stma.judgebean.model.Resource;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@SuppressWarnings("CdiManagedBeanInconsistencyInspection")
@Repository(forEntity = Resource.class)
public interface ResourceRepo extends AbstractRepo<Resource> {

}
