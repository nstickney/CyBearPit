package is.stma.judgebean.data;

import is.stma.judgebean.model.Member;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@SuppressWarnings("CdiManagedBeanInconsistencyInspection")
@Repository(forEntity = Member.class)
public interface MemberRepo extends EntityRepository<Member, String> {

    Member findById(String id);

    Member findByEmail(String email);

    List<Member> findAllOrderByNameAsc();
}
