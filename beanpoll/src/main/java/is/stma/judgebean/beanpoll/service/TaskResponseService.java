package is.stma.judgebean.beanpoll.service;

import is.stma.judgebean.beanpoll.data.AbstractRepo;
import is.stma.judgebean.beanpoll.data.TaskResponseRepo;
import is.stma.judgebean.beanpoll.model.TaskResponse;
import is.stma.judgebean.beanpoll.rules.TaskResponseRules;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;

@Stateless
public class TaskResponseService extends AbstractService<TaskResponse, AbstractRepo<TaskResponse>,
        TaskResponseRules> {

    @Inject
    private TaskResponseRepo repo;

    @Inject
    private Event<TaskResponse> event;

    @Inject
    private TaskResponseRules rules;

    @Override
    AbstractRepo<TaskResponse> getRepo() {
        return repo;
    }

    @Override
    Event<TaskResponse> getEvent() {
        return event;
    }

    @Override
    TaskResponseRules getRules() {
        return rules;
    }
}
