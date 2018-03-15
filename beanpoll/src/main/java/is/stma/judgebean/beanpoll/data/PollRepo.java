package is.stma.judgebean.beanpoll.data;

import is.stma.judgebean.beanpoll.model.Poll;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@SuppressWarnings("CdiManagedBeanInconsistencyInspection")
@Repository(forEntity = Poll.class)
public interface PollRepo extends AbstractRepo<Poll> {

    List<Poll> findAllOrderByTimestamp();

}
