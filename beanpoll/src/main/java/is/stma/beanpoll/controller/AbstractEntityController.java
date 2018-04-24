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

import is.stma.beanpoll.data.AbstractRepo;
import is.stma.beanpoll.model.AbstractEntity;
import is.stma.beanpoll.rules.AbstractRules;
import is.stma.beanpoll.service.AbstractService;

import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;

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
            getService().create(getNew());
            facesContext.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO, getNew().getLogName() + " created.",
                    "")
            );
            setNew(null);
        } catch (EJBException | ValidationException e) {
            errorOut(e, "Failed to create " + getNew().getLogName() + ": ");
        } catch (Exception e) {
            errorOut(e, getNew().getLogName() + " creation failed: ");
        }
    }

    /**
     * Do the actual update of the given <E>
     *
     * @param entity the <E> to update
     */
    void doUpdate(E entity) {
        try {
            entity = getService().update(entity);
            messageOut(entity.getLogName() + " updated.");
        } catch (EJBException | ValidationException e) {
            errorOut(e, "Failed to update " + entity.getLogName() + ": ");
        } catch (Exception e) {
            errorOut(e, entity.getLogName() + " update failed: ");
        }
    }

    /**
     * Do the actual deletion of the given <E>
     *
     * @param entity the <E> to delete
     */
    void doDelete(E entity) {
        try {
            String deleted = entity.getLogName();
            getService().delete(entity);
            messageOut(deleted + " deleted.");
        } catch (EJBException | ValidationException e) {
            errorOut(e, "Failed to delete " + entity.getLogName() + ": ");
        } catch (Exception e) {
            errorOut(e, entity.getLogName() + " deletion failed: ");
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
            if (c.getLogName().toLowerCase().contains(q.toLowerCase())) {
                filteredEntities.add(c);
            }
        }
        return filteredEntities;
    }
}