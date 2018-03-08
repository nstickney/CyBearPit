package is.stma.judgebean.rules;

import is.stma.judgebean.data.ScoringDNSRepo;
import is.stma.judgebean.model.scoring.ScoringDNS;
import org.apache.deltaspike.data.api.EntityRepository;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.validation.ValidationException;

@Model
public class ScoringDNSRules extends AbstractRules<ScoringDNS> {

    @Inject
    private ScoringDNSRepo repo;

    @Override
    public EntityRepository<ScoringDNS, String> getRepo() {
        return repo;
    }

    @Override
    public void runBusinessRules(ScoringDNS entity, Target target)
            throws ValidationException {
    }

    @Override
    void checkBeforeDelete(ScoringDNS entity) throws ValidationException {

    }
}
