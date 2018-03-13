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

        // Check score is within allowable range
        checkScoreWithinTaskPointValue(entity);
    }

    @Override
    void checkBeforeDelete(TaskResponse entity) throws ValidationException {

    }

    private void checkScoreWithinTaskPointValue(TaskResponse entity) throws ValidationException {
        if (entity.getScore() < -entity.getTask().getPointValue()
                || entity.getScore() > entity.getTask().getPointValue()) {
            throw new ValidationException("score " + entity.getScore() + " is not within plus or minus"
                    + entity.getTask().getPointValue() + " of zero");
        }
    }
}
