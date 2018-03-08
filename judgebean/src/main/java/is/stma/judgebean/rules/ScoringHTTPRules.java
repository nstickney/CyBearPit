package is.stma.judgebean.rules;

import is.stma.judgebean.data.ScoringHTTPRepo;
import is.stma.judgebean.model.scoring.ScoringHTTP;
import org.apache.deltaspike.data.api.EntityRepository;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.validation.ValidationException;

@Model
public class ScoringHTTPRules extends AbstractRules<ScoringHTTP> {

    @Inject
    private ScoringHTTPRepo repo;

    @Override
    public EntityRepository<ScoringHTTP, String> getRepo() {
        return repo;
    }

    @Override
    public void runBusinessRules(ScoringHTTP entity, Target target)
            throws ValidationException {
    }

    @Override
    void checkBeforeDelete(ScoringHTTP entity) throws ValidationException {

    }
}
