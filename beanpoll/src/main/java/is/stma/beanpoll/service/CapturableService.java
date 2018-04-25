/*
 * Copyright 2018 Nathaniel Stickney
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package is.stma.beanpoll.service;

import is.stma.beanpoll.data.AbstractRepo;
import is.stma.beanpoll.data.CapturableRepo;
import is.stma.beanpoll.model.Capturable;
import is.stma.beanpoll.rules.CapturableRules;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;

@Stateless
public class CapturableService extends AbstractService<Capturable, AbstractRepo<Capturable>,
        CapturableRules> {

    @SuppressWarnings("CdiInjectionPointsInspection")
    @Inject
    private CapturableRepo repo;

    @Inject
    private Event<Capturable> event;

    @Inject
    private CapturableRules rules;

    @Override
    AbstractRepo<Capturable> getRepo() {
        return repo;
    }

    @Override
    Event<Capturable> getEvent() {
        return event;
    }

    @Override
    CapturableRules getRules() {
        return rules;
    }

    public Capturable getByFlag(String flag) {
        return repo.findByFlag(flag);
    }
}
