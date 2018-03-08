package is.stma.judgebean.data;

import is.stma.judgebean.model.Contest;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@RequestScoped
public class ContestListProducer extends AbstractEntityListProducer<Contest> {

    @Inject
    private ContestRepo contestRepo;

    private List<Contest> contests;

    @Produces
    @Named
    public List<Contest> getContests() {
        return contests;
    }

    @Override
    @PostConstruct
    void retrieveAll() {
        contests = contestRepo.findAllOrderByNameAsc();
    }
}
