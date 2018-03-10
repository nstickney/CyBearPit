package is.stma.judgebean.beanpoll.data;

import is.stma.judgebean.beanpoll.model.Contest;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
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

    @Produces
    @Named("runningContests")
    public List<Contest> findRunning() {
        List<Contest> running = new ArrayList<>();
        for (Contest entity : entities) {
            if (entity.isRunning()) {
                running.add(entity);
            }
        }
        return running;
    }

    @Produces
    @Named("stoppedContests")
    public List<Contest> findStopped() {
        List<Contest> stopped = new ArrayList<>();
        for (Contest entity : entities) {
            if (!entity.isRunning()) {
                stopped.add(entity);
            }
        }
        return stopped;
    }
}
