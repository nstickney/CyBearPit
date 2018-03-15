package is.stma.judgebean.beanpoll.data;

import is.stma.judgebean.beanpoll.model.Poll;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@RequestScoped
public class PollListProducer extends AbstractEntityListProducer<Poll> {

    @Inject
    private PollRepo repo;

    private List<Poll> entities;

    @Produces
    @Named("polls")
    public List<Poll> getEntities() {
        return entities;
    }

    @Override
    @PostConstruct
    void retrieveAll() {
        entities = repo.findAllOrderByTimestamp();
    }
}
