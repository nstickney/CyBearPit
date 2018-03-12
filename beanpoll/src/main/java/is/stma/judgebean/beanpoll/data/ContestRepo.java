package is.stma.judgebean.beanpoll.data;

import is.stma.judgebean.beanpoll.model.Contest;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@SuppressWarnings("CdiManagedBeanInconsistencyInspection")
@Repository(forEntity = Contest.class)
public interface ContestRepo extends AbstractRepo<Contest> {

    @Query("SELECT c FROM Contest c ORDER BY running DESC, name")
    List<Contest> findAllOrderByRunningDescNameAsc();
}
