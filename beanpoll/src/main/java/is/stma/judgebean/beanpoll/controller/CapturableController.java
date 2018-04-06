/*
 * Copyright 2018 Nathaniel Stickney
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package is.stma.judgebean.beanpoll.controller;

import is.stma.judgebean.beanpoll.model.Capturable;
import is.stma.judgebean.beanpoll.rules.CapturableRules;
import is.stma.judgebean.beanpoll.service.CapturableService;

import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

@Model
public class CapturableController extends AbstractEntityController<Capturable, CapturableRules,
        CapturableService> {

    @Inject
    private CapturableService service;

    private Capturable newCapturable;

    @Override
    @Produces
    @Named("newCapturable")
    Capturable getNew() {
        if (newCapturable == null) {
            newCapturable = new Capturable();
        }
        return newCapturable;
    }

    @Override
    void setNew(Capturable entity) {
        newCapturable = entity;
    }

    @Override
    CapturableService getService() {
        return service;
    }

    @Override
    public void update(Capturable entity) {
        doUpdate(entity);
    }

    @Override
    public void delete(Capturable entity) {
        doDelete(entity);
    }
}
