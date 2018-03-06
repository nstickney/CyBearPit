package is.stma.judgebean.controller;

import is.stma.judgebean.model.AEntity;
import is.stma.judgebean.service.AService;
import is.stma.judgebean.validator.AValidator;
import org.apache.deltaspike.data.api.EntityRepository;

import javax.faces.application.FacesMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static is.stma.judgebean.util.EntityUtility.prefix;

/**
 * Provides the default operations for modeled entities (CRUD)
 *
 * @param <Entity>    The class of AEntity to control
 * @param <Validator> The class of AValidator matched to <Entity>
 * @param <Service>   The class of AService matched to <Entity>
 */
abstract class AEntityController<Entity extends AEntity,
        Validator extends AValidator<Entity>,
        Service extends AService<Entity, EntityRepository<Entity, String>, Validator>> extends AController {

    /* Abstract Methods -------------------------------------------------------------- */

    /**
     * Get a non-persistent, new <Entity> instance
     *
     * @return instance of <Entity>
     */
    abstract Entity getNew();

    /**
     * Set the referenced new <Entity>
     *
     * @param entity instance of <Entity> to reference
     */
    abstract void setNew(Entity entity);

    /**
     * Get the controller's @Inject <Entity> Service
     *
     * @return the Service
     */
    abstract Service getService();

    /**
     * Update the given <Entity>; class implementations must call doUpdate(entity)
     *
     * @param entity the <Entity> to update
     */
    abstract void update(Entity entity);

    /**
     * Delete the given <Entity>; class implementations must call doDelete(entity)
     *
     * @param entity the <Entity> to delete
     */
    abstract void delete(Entity entity);

    /* Controlled Methods ------------------------------------------------------------ */

    /**
     * Persist the <Entity> from getNew()
     */
    public void create() {
        try {
            setNew(getService().create(getNew()));
            facesContext.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO, prefix(getNew()) + " created.",
                    "")
            );
        } catch (Exception e) {
            errorOut(e, prefix(getNew()) + " creation failed.");
        }
    }

    /**
     * Do the actual update of the given <Entity>
     *
     * @param entity the <Entity> to update
     */
    void doUpdate(Entity entity) {
        try {
            entity = getService().update(entity);
            facesContext.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO, prefix(entity) + " updated.", "")
            );
        } catch (Exception e) {
            errorOut(e, prefix(entity) + " update failed.");
        }
    }

    /**
     * Do the actual deletion of the given <Entity>
     *
     * @param entity the <Entity> to delete
     */
    void doDelete(Entity entity) {
        try {
            String deleted = prefix(entity);
            getService().delete(entity);
            facesContext.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO, deleted + " deleted.",
                    "")
            );
        } catch (Exception e) {
            errorOut(e, prefix(entity) + " deletion failed.");
        }
    }

    /**
     * Search for an <Entity> where the string representation matches some query
     *
     * @param q the query to match
     * @return a List of <Entity> objects which match the query
     */
    public List<Entity> search(String q) {
        List<Entity> allEntitys = getService().readAll();
        List<Entity> filteredEntitys = new ArrayList<>();

        for (Entity c : allEntitys) {
            if (c.getName().toLowerCase().contains(q.toLowerCase())) {
                filteredEntitys.add(c);
            }
        }
        return filteredEntitys;
    }

    /* Utility Methods --------------------------------------------------------------- */

    /**
     * Handle a Throwable by logging the error message and pushing it to the FacesContext
     *
     * @param t   the Throwable to handle
     * @param msg the detailed message
     */
    private void errorOut(Throwable t, String msg) {

        // Default to general error message that registration failed.
        String errorMsg = "Action failed. See server log for more information";

        // Find a better error message if possible
        if (t != null) {

            // Start with the exception and recurse to find the root cause
            while (t != null) {
                // Get the message from the Throwable class instance
                errorMsg = t.getLocalizedMessage();
                t = t.getCause();
            }
        }

        // Log and send the message
        log.log(Level.WARNING, "CONTROLLER: " + msg);
        log.log(Level.WARNING, "CAUSE: " + errorMsg);
        facesContext.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMsg, msg));
    }
}