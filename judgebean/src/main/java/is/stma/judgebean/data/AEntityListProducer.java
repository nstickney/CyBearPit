package is.stma.judgebean.data;

import is.stma.judgebean.model.AEntity;

import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;

/**
 * Provides a list of all <Entity> instances persistent in the database
 *
 * @param <Entity> the class of AbstractEntity to produce a list of
 */
abstract class AEntityListProducer<Entity extends AEntity> {

    /**
     * Pull all <Entity> instances from the repository
     */
    abstract void retrieveAll();

    /**
     * Monitor the list; if it changes, update from the repository
     *
     * @param entity the entity to monitor
     */
    public void onListChanged(
            @Observes(notifyObserver = Reception.IF_EXISTS) final Entity entity) {
        retrieveAll();
    }
}