package is.stma.judgebean.beanpoll.controller;

import is.stma.judgebean.beanpoll.model.TaskResponse;
import is.stma.judgebean.beanpoll.rules.TaskResponseRules;
import is.stma.judgebean.beanpoll.service.TaskResponseService;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Produces;

@Model
public class TaskResponseController extends AbstractEntityController<TaskResponse,
        TaskResponseRules, TaskResponseService> {

    @Inject
    private TaskResponseService service;

    private TaskResponse newTaskResponse;

    @Override
    @Produces
    @Named("newTaskResponse")
    TaskResponse getNew() {
        if (newTaskResponse == null) {
            newTaskResponse = new TaskResponse();
        }
        return newTaskResponse;
    }

    @Override
    void setNew(TaskResponse entity) {
        newTaskResponse = entity;
    }

    @Override
    TaskResponseService getService() {
        return service;
    }

    @Override
    public void update(TaskResponse entity) {
        doUpdate(entity);
    }

    @Override
    public void delete(TaskResponse entity) {
        doDelete(entity);
    }
}
