/*
 * Copyright 2018 Nathaniel Stickney
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package is.stma.beanpoll.controller;

import is.stma.beanpoll.model.Task;
import is.stma.beanpoll.rules.TaskRules;
import is.stma.beanpoll.service.TaskService;

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
