package is.stma.judgebean.beanpoll.rules;

import is.stma.judgebean.beanpoll.data.AbstractRepo;
import is.stma.judgebean.beanpoll.data.PollRepo;
import is.stma.judgebean.beanpoll.model.Poll;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.validation.ValidationException;

@Model
public class PollRules extends AbstractRules<Poll> {

    @Inject
    private PollRepo repo;

    @Override
    public AbstractRepo<Poll> getRepo() {
        return repo;
    }

    @Override
    public void runBusinessRules(Poll entity, Target target)
            throws ValidationException {
    }

    @Override
    void checkBeforeDelete(Poll entity) throws ValidationException {

    }
}
