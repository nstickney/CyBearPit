package is.stma.judgebean.beanpoll.data;

import is.stma.judgebean.beanpoll.model.Task;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collections;
import java.util.List;

@RequestScoped
public class TaskListProducer extends AbstractEntityListProducer<Task> {

    @Inject
    private TaskRepo repo;

    private List<Task> entities;

    @Produces
    @Named("tasks")
    public List<Task> getEntities() {
        return entities;
    }

    @Override
    @PostConstruct
    void retrieveAll() {
        entities = repo.findAllOrderByNameAsc();
        Collections.sort(entities);
    }
}
