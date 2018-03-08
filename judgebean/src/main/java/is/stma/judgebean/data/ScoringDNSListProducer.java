package is.stma.judgebean.data;

import is.stma.judgebean.model.scoring.ScoringDNS;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@RequestScoped
public class ScoringDNSListProducer extends AbstractEntityListProducer<ScoringDNS> {

    @Inject
    private ScoringDNSRepo scoringDNSRepo;

    private List<ScoringDNS> teams;

    @Produces
    @Named
    public List<ScoringDNS> getScoringDNSs() {
        return teams;
    }

    @Override
    @PostConstruct
    void retrieveAll() {
        teams = scoringDNSRepo.findAllOrderByNameAsc();
    }
}
