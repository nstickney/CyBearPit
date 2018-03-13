package is.stma.judgebean.beanpoll.rules;

import is.stma.judgebean.beanpoll.data.AbstractRepo;
import is.stma.judgebean.beanpoll.data.TaskResponseRepo;
import is.stma.judgebean.beanpoll.model.TaskResponse;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.validation.ValidationException;

@Model
public class TaskResponseRules extends AbstractRules<TaskResponse> {

    @Inject
    private TaskResponseRepo repo;

    @Override
    public AbstractRepo<TaskResponse> getRepo() {
        return repo;
    }

    @Override
    public void runBusinessRules(TaskResponse entity, Target target)
            throws ValidationException {
    }

    @Override
    void checkBeforeDelete(TaskResponse entity) throws ValidationException {

    }
}
