package is.stma.judgebean.data;

import is.stma.judgebean.model.Contest;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@SuppressWarnings("CdiManagedBeanInconsistencyInspection")
@Repository(forEntity = Contest.class)
public interface ContestRepo extends AbstractRepo<Contest> {

    List<Contest> findAllOrderByNameAsc();

}
