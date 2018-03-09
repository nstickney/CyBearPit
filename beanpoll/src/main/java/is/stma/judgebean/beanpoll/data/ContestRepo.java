package is.stma.judgebean.beanpoll.data;

import is.stma.judgebean.beanpoll.model.Contest;
import org.apache.deltaspike.data.api.Repository;

@SuppressWarnings("CdiManagedBeanInconsistencyInspection")
@Repository(forEntity = Contest.class)
public interface ContestRepo extends AbstractRepo<Contest> {

}
