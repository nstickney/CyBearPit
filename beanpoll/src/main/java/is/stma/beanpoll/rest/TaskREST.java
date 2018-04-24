
/*
 * Copyright 2018 Nathaniel Stickney
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package is.stma.beanpoll.rest;

import is.stma.beanpoll.data.TaskRepo;
import is.stma.beanpoll.model.Task;
import is.stma.beanpoll.rules.TaskRules;
import is.stma.beanpoll.service.TaskService;
import is.stma.beanpoll.data.AbstractRepo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * JAX-RS Example
 * <p/>
 * This class produces a RESTful service to read/write the contents of the tasks table.
 */
@Path("/tasks")
@RequestScoped
public class TaskREST extends AbstractREST<Task, AbstractRepo<Task>, TaskRules, TaskService> {

    @Inject
    private TaskRepo repo;

    @Inject
    private TaskRules rules;

    @Inject
    private TaskService service;

    TaskRepo getRepo() {
        return repo;
    }

    TaskRules getRules() {
        return rules;
    }

    TaskService getService() {
        return service;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Task lookupTaskById(@PathParam("id") String id) {
        return lookupById(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Task> listAll() {
        return getRepo().findAllOrderByNameAsc();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTask(Task entity) {
        return createEntity(entity);
    }
}
