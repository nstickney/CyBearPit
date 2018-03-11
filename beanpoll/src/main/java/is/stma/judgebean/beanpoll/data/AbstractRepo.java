package is.stma.judgebean.beanpoll.data;

import is.stma.judgebean.beanpoll.model.AbstractEntity;
import org.apache.deltaspike.data.api.EntityRepository;

import java.util.List;

public interface AbstractRepo<E extends AbstractEntity> extends EntityRepository<E, String> {

    List<E> findAllOrderByNameAsc();

}
