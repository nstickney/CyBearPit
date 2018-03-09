package is.stma.judgebean.beanpoll.service;

import is.stma.judgebean.beanpoll.data.AbstractRepo;
import is.stma.judgebean.beanpoll.data.TaskRepo;
import is.stma.judgebean.beanpoll.model.Task;
import is.stma.judgebean.beanpoll.rules.TaskRules;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;

@Stateless
public class TaskService extends AbstractService<Task, AbstractRepo<Task>,
        TaskRules> {

    @Inject
    private TaskRepo repo;

    @Inject
    private Event<Task> event;

    @Inject
    private TaskRules rules;

    @Override
    AbstractRepo<Task> getRepo() {
        return repo;
    }

    @Override
    Event<Task> getEvent() {
        return event;
    }

    @Override
    TaskRules getRules() {
        return rules;
    }
}
