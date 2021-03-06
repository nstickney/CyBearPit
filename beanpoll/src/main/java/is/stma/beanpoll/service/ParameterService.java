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
import is.stma.beanpoll.data.ParameterRepo;
import is.stma.beanpoll.model.Parameter;
import is.stma.beanpoll.model.Resource;
import is.stma.beanpoll.rules.ParameterRules;
import is.stma.beanpoll.service.parameterizer.*;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class ParameterService extends AbstractService<Parameter, AbstractRepo<Parameter>,
        ParameterRules> {

    @SuppressWarnings("CdiInjectionPointsInspection")
    @Inject
    private ParameterRepo repo;

    @Inject
    private Event<Parameter> event;

    @Inject
    private ParameterRules rules;

    @Override
    AbstractRepo<Parameter> getRepo() {
        return repo;
    }

    @Override
    Event<Parameter> getEvent() {
        return event;
    }

    @Override
    ParameterRules getRules() {
        return rules;
    }

    /**
     * Create the necessary default Parameters for a Resource to function.
     * @param resource Resource for which to create default Parameters
     * @return List of Parameters attached to that Resource
     * @throws ValidationException if Parameter creation fails
     */
    // MUST BE PUBLIC to be called by other EJBs
    public List<Parameter> createParameters(Resource resource) throws ValidationException {

        // Create the correct parameters for the resource
        List<Parameter> parameters;
        switch (resource.getType()) {
            case DNS:
                parameters = DNSParameterizer.createParameters();
                break;
            case HTTP:
                parameters = HTTPParameterizer.createParameters();
                break;
            case IMAP:
                parameters = EmailParameterizer.createParameters();
                break;
            case POP:
                parameters = EmailParameterizer.createParameters();
                break;
            case SMTP:
                parameters = SMTPParameterizer.createParameters();
                break;
            case SMTP_IMAP:
                parameters = SMTPCHECKParameterizer.createParameters();
                break;
            case SMTP_POP:
                parameters = SMTPCHECKParameterizer.createParameters();
                break;
            default:
                throw new ValidationException("unknown resource type " + resource.getType());
        }

        // Attempt to persist all created parameters
        List<Parameter> createdParameters = new ArrayList<>();
        try {
            for (Parameter p : parameters) {
                p.setResource(resource);
                create(p);
                createdParameters.add(p);
            }

            // If there is a problem, undo everything done so far
        } catch (Exception e) {
            for (Parameter p : createdParameters) {
                delete(p);
            }
            throw new ValidationException("could not create resource parameters: " + e.getMessage());
        }

        // Return the created list
        return createdParameters;
    }

    public Parameter clone(Parameter entity, Resource resource) {
        Parameter clone = new Parameter();
        clone.setResource(resource);
        clone.setTag(entity.getTag());
        clone.setValue(entity.getValue());
        return create(clone);
    }

}
