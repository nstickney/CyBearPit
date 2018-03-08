/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package is.stma.judgebean.rest;

import is.stma.judgebean.data.AbstractRepo;
import is.stma.judgebean.data.ScoringHTTPRepo;
import is.stma.judgebean.model.scoring.ScoringHTTP;
import is.stma.judgebean.rules.ScoringHTTPRules;
import is.stma.judgebean.service.ScoringHTTPService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * JAX-RS Example
 * <p/>
 * This class produces a RESTful service to read/write the contents of the scoringHTTPs table.
 */
@Path("/scoring/http")
@RequestScoped
public class ScoringHTTPREST extends AbstractREST<ScoringHTTP, AbstractRepo<ScoringHTTP>, ScoringHTTPRules, ScoringHTTPService> {

    @Inject
    private ScoringHTTPRepo repository;

    @Inject
    private ScoringHTTPRules rules;
    
    @Inject
    private ScoringHTTPService service;

    ScoringHTTPRepo getRepo() {
        return repository;
    }

    ScoringHTTPRules getRules() {
        return rules;
    }

    ScoringHTTPService getService() {
        return service;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public ScoringHTTP lookupScoringHTTPById(@PathParam("id") String id) {
        return lookupById(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ScoringHTTP> listAll() {
        return getRepo().findAllOrderByNameAsc();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createScoringHTTP(ScoringHTTP entity) {
        return createEntity(entity);
    }
}
