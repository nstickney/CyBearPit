package is.stma.judgebean.beanpoll.rules;

import is.stma.judgebean.beanpoll.data.AbstractRepo;
import is.stma.judgebean.beanpoll.data.TaskRepo;
import is.stma.judgebean.beanpoll.model.Task;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.validation.ValidationException;

@Model
public class TaskRules extends AbstractRules<Task> {

    @Inject
    private TaskRepo repo;

    @Override
    public AbstractRepo<Task> getRepo() {
        return repo;
    }

    @Override
    public void runBusinessRules(Task entity, Target target)
            throws ValidationException {
    }

    @Override
    void checkBeforeDelete(Task entity) throws ValidationException {

    }
}
