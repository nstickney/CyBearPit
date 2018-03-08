package is.stma.judgebean.data;

import is.stma.judgebean.model.scoring.ScoringHTTP;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@SuppressWarnings("CdiManagedBeanInconsistencyInspection")
@Repository(forEntity = ScoringHTTP.class)
public interface ScoringHTTPRepo extends AbstractRepo<ScoringHTTP> {

    ScoringHTTP findById(String id);

    List<ScoringHTTP> findAllOrderByNameAsc();

}
