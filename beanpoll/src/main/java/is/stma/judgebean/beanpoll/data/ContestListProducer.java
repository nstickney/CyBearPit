package is.stma.judgebean.beanpoll.data;

import is.stma.judgebean.beanpoll.model.Contest;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@RequestScoped
public class ContestListProducer extends AbstractEntityListProducer<Contest> {

    @Inject
    private ContestRepo repo;

    private List<Contest> entities;

    @Produces
    @Named("contests")
    public List<Contest> getEntities() {
        return entities;
    }

    @Override
    @PostConstruct
    void retrieveAll() {
        entities = repo.findAllOrderByNameAsc();
    }
}
