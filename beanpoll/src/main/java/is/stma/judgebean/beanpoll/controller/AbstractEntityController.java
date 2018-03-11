package is.stma.judgebean.beanpoll.controller;

import is.stma.judgebean.beanpoll.data.AbstractRepo;
import is.stma.judgebean.beanpoll.model.AbstractEntity;
import is.stma.judgebean.beanpoll.rules.AbstractRules;
import is.stma.judgebean.beanpoll.service.AbstractService;

import javax.faces.application.FacesMessage;
import javax.xml.bind.ValidationException;
import java.util.ArrayList;
import java.util.List;

import static is.stma.judgebean.beanpoll.util.EntityUtility.prefix;

/**
 * Provides the default operations for modeled entities (CRUD)
 *
 * @param <E> The class of AbstractEntity to control
 * @param <V> The class of AbstractRules matched to <E>
 * @param <S> The class of AbstractService matched to <E>
 */
abstract class AbstractEntityController<E extends AbstractEntity,
        V extends AbstractRules<E>,
        S extends AbstractService<E, AbstractRepo<E>, V>> extends AbstractFacesController {

    /**
     * Get a non-persistent, new <E> instance
     *
     * @return instance of <E>
     */
    abstract E getNew();

    /**
     * Set the referenced new <E>
     *
     * @param entity instance of <E> to reference
     */
    abstract void setNew(E entity);

    /**
     * Get the controller's @Inject <E> Service
     *
     * @return the Service
     */
    abstract S getService();

    /**
     * Update the given <E>; class implementations must call doUpdate(entity)
     *
     * @param entity the <E> to update
     */
    abstract void update(E entity);

    /**
     * Delete the given <E>; class implementations must call doDelete(entity)
     *
     * @param entity the <E> to delete
     */
    abstract void delete(E entity);

    /**
     * Persist the <E> from getNew()
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
     * Do the actual update of the given <E>
     *
     * @param entity the <E> to update
     */
    void doUpdate(E entity) {
        if (null != entity) {
            try {
                entity = getService().update(entity);
                facesContext.addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_INFO, prefix(entity) + " updated.", "")
                );
            } catch (Exception e) {
//                errorOut(e, prefix(entity) + " update failed.");
                errorOut(e, entity.getId());
            }
        }
        errorOut(new ValidationException("Cannot update null entity"), "FAILED");
    }

    /**
     * Do the actual deletion of the given <E>
     *
     * @param entity the <E> to delete
     */
    void doDelete(E entity) {
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
     * Search for an <E> where the string representation matches some query
     *
     * @param q the query to match
     * @return a List of <E> objects which match the query
     */
    public List<E> search(String q) {
        List<E> filteredEntities = new ArrayList<>();
        for (E c : getService().readAll()) {
            if (c.getName().toLowerCase().contains(q.toLowerCase())) {
                filteredEntities.add(c);
            }
        }
        return filteredEntities;
    }
}