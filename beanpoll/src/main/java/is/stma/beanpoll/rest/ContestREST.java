
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

import is.stma.beanpoll.data.AbstractRepo;
import is.stma.beanpoll.data.ContestRepo;
import is.stma.beanpoll.model.Contest;
import is.stma.beanpoll.rules.ContestRules;
import is.stma.beanpoll.service.ContestService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * JAX-RS Example
 * <p/>
 * This class produces a RESTful service to read/write the contents of the contests table.
 */
@Path("/contests")
@RequestScoped
public class ContestREST extends AbstractREST<Contest, AbstractRepo<Contest>, ContestRules, ContestService> {

    @Inject
    private ContestRepo repo;

    @Inject
    private ContestRules rules;

    @Inject
    private ContestService service;

    ContestRepo getRepo() {
        return repo;
    }

    ContestRules getRules() {
        return rules;
    }

    ContestService getService() {
        return service;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Contest lookupContestById(@PathParam("id") String id) {
        return lookupById(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Contest> listAll() {
        return getRepo().findAllOrderByNameAsc();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createContest(Contest entity) {
        return createEntity(entity);
    }
}