package is.stma.judgebean.data;

import is.stma.judgebean.model.Team;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@SuppressWarnings("CdiManagedBeanInconsistencyInspection")
@Repository(forEntity = Team.class)
public interface TeamRepo extends AbstractRepo<Team> {

    @Query("select t from Team t where t.contest.id = ?1")
    List<Team> findByContest(String contestId);

}
