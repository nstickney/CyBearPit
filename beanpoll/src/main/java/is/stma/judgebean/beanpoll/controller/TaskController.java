package is.stma.judgebean.beanpoll.controller;

import is.stma.judgebean.beanpoll.model.Task;
import is.stma.judgebean.beanpoll.rules.TaskRules;
import is.stma.judgebean.beanpoll.service.TaskService;

import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

@Model
public class TaskController extends AbstractEntityController<Task, TaskRules,
        TaskService> {

    @Inject
    private TaskService service;

    private Task newTask;

    @Override
    @Produces
    @Named("newTask")
    Task getNew() {
        if (newTask == null) {
            newTask = new Task();
        }
        return newTask;
    }

    @Override
    void setNew(Task entity) {
        newTask = entity;
    }

    @Override
    TaskService getService() {
        return service;
    }

    @Override
    public void update(Task entity) {
        doUpdate(entity);
    }

    @Override
    public void delete(Task entity) {
        doDelete(entity);
    }
}
