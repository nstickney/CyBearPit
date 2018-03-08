package is.stma.judgebean.data;

import is.stma.judgebean.model.scoring.ScoringDNS;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@SuppressWarnings("CdiManagedBeanInconsistencyInspection")
@Repository(forEntity = ScoringDNS.class)
public interface ScoringDNSRepo extends AbstractRepo<ScoringDNS> {

    ScoringDNS findById(String id);

    List<ScoringDNS> findAllOrderByNameAsc();

}
