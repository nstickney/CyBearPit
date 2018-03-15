package is.stma.judgebean.beanpoll.data;

import is.stma.judgebean.beanpoll.model.TaskResponse;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@RequestScoped
public class TaskResponseListProducer extends AbstractEntityListProducer<TaskResponse> {

    @Inject
    private TaskResponseRepo repo;

    private List<TaskResponse> entities;

    @Produces
    @Named("taskResponses")
    public List<TaskResponse> getEntities() {
        return entities;
    }

    @Override
    @PostConstruct
    void retrieveAll() {
        entities = repo.findAllOrderByTimestamp();
    }
}
