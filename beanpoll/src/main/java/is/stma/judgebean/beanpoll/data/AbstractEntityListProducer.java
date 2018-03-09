package is.stma.judgebean.beanpoll.data;

import is.stma.judgebean.beanpoll.model.AbstractEntity;

import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;

/**
 * Provides a list of all <E> instances persistent in the database
 *
 * @param <E> the class of AbstractEntity to produce a list of
 */
abstract class AbstractEntityListProducer<E extends AbstractEntity> {

    /**
     * Pull all <E> instances from the repository
     */
    abstract void retrieveAll();

    /**
     * Monitor the list; if it changes, update from the repository
     *
     * @param entity the entity to monitor
     */
    public void onListChanged(
            @Observes(notifyObserver = Reception.IF_EXISTS) final E entity) {
        retrieveAll();
    }
}