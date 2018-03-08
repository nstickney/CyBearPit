package is.stma.judgebean.data;

import is.stma.judgebean.model.scoring.ScoringHTTP;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@RequestScoped
public class ScoringHTTPListProducer extends AbstractEntityListProducer<ScoringHTTP> {

    @Inject
    private ScoringHTTPRepo scoringHTTPRepo;

    private List<ScoringHTTP> teams;

    @Produces
    @Named
    public List<ScoringHTTP> getScoringHTTPs() {
        return teams;
    }

    @Override
    @PostConstruct
    void retrieveAll() {
        teams = scoringHTTPRepo.findAllOrderByNameAsc();
    }
}
