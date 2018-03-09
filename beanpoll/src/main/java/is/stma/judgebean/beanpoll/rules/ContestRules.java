package is.stma.judgebean.beanpoll.rules;

import is.stma.judgebean.beanpoll.data.ContestRepo;
import is.stma.judgebean.beanpoll.model.Contest;
import org.apache.deltaspike.data.api.EntityRepository;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.validation.ValidationException;

@Model
public class ContestRules extends AbstractRules<Contest> {

    @Inject
    private ContestRepo repo;

    @Override
    public EntityRepository<Contest, String> getRepo() {
        return repo;
    }

    @Override
    public void runBusinessRules(Contest entity, Target target)
            throws ValidationException {
    }

    @Override
    void checkBeforeDelete(Contest entity) throws ValidationException {

    }
}
