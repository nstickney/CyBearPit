/*
 * Copyright 2018 Nathaniel Stickney
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package is.stma.beanpoll.data;

import is.stma.beanpoll.model.AbstractEntity;

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