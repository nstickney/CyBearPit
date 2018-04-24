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

import is.stma.judgebean.beanpoll.service.parameterizer.DNSParameterizer;
import is.stma.judgebean.beanpoll.service.parameterizer.HTTPParameterizer;
import is.stma.judgebean.beanpoll.model.Parameter;
import is.stma.judgebean.beanpoll.model.Resource;
import is.stma.judgebean.beanpoll.rules.ParameterRules;
import is.stma.judgebean.beanpoll.service.ParameterService;

import javax.ejb.EJBException;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.ValidationException;
import java.util.ArrayList;
import java.util.List;

@Model
public class ParameterController extends AbstractEntityController<Parameter, ParameterRules,
        ParameterService> {

    @Inject
    private ParameterService service;

    private Parameter newParameter;

    @Override
    @Produces
    @Named("newParameter")
    Parameter getNew() {
        if (newParameter == null) {
            newParameter = new Parameter();
        }
        return newParameter;
    }

    @Override
    void setNew(Parameter entity) {
        newParameter = entity;
    }

    @Override
    ParameterService getService() {
        return service;
    }

    @Override
    public void update(Parameter entity) {
        doUpdate(entity);
    }

    @Override
    public void delete(Parameter entity) {
        doDelete(entity);
    }
}
