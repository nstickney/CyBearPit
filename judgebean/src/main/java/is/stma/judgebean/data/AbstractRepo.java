package is.stma.judgebean.data;

import is.stma.judgebean.model.AbstractEntity;
import org.apache.deltaspike.data.api.EntityRepository;

import java.util.List;

public interface AbstractRepo<E extends AbstractEntity> extends EntityRepository<E, String> {

    E findById(String id);

    List<E> findAllOrderByNameAsc();

}
