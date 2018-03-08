package is.stma.judgebean.rest;

import is.stma.judgebean.data.AbstractRepo;
import is.stma.judgebean.model.AbstractEntity;
import is.stma.judgebean.model.Team;
import is.stma.judgebean.rules.AbstractRules;
import is.stma.judgebean.service.AbstractService;

import javax.inject.Inject;
import javax.persistence.MappedSuperclass;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

@MappedSuperclass
abstract class AbstractREST<E extends AbstractEntity,
        R extends AbstractRepo<E>,
        V extends AbstractRules<E>,
        S extends AbstractService<E,R,V>> {

    @Inject
    Logger log;

    @Inject
    Validator validator;

    abstract R getRepo();

    abstract V getRules();

    abstract S getService();

    E lookupById(String id) {
        E entity = getRepo().findById(id);
        if (entity == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return entity;
    }

    /**
     * Creates a new entity from the values provided. Performs validation, and
     * will return a JAX-RS response with either 200 ok, or with a map of fields
     * and related errors.
     */
    Response createEntity(E entity) {

        Response.ResponseBuilder builder;

        try {
            // Validates team using bean validation
            beanValidate(entity);

            // Validates using Rules before creation
            getService().create(entity);

            // Create an "ok" response
            builder = Response.ok();
        } catch (ConstraintViolationException ce) {
            // Handle bean validation issues
            builder = createViolationResponse(ce.getConstraintViolations());
        } catch (ValidationException e) {
            // Handle the unique constraint violation
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("name", "must be unique");
            builder = Response.status(Response.Status.CONFLICT).entity(responseObj);
        } catch (Exception e) {
            // Handle generic exceptions
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }

        return builder.build();
    }

    private void beanValidate(E entity) throws ValidationException {
        // Create a bean rules and check for issues.
        Set<ConstraintViolation<E>> violations = validator.validate(entity);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
        }
    }

    /**
     * Creates a JAX-RS "Bad Request" response including a map of all violation
     * fields, and their message. This can then be used by clients to show
     * violations.
     *
     * @param violations A set of violations that needs to be reported
     * @return JAX-RS response containing all violations
     */
    private Response.ResponseBuilder createViolationResponse(Set<ConstraintViolation<?>> violations) {
        log.fine("Validation completed. violations found: " + violations.size());

        Map<String, String> responseObj = new HashMap<>();

        for (ConstraintViolation<?> violation : violations) {
            responseObj.put(violation.getPropertyPath().toString(), violation.getMessage());
        }

        return Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
    }
}
