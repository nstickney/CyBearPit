package is.stma.judgebean.beanpoll.controller;

import is.stma.judgebean.beanpoll.model.Contest;

import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
@Named("judge")
public class JudgeController {

    @Inject
    private JudgeBean bean;

    @Inject
    private
    ContestController contestController;

    private Future<String> runningContest;

    public void run(Contest contest) {
        contest.setRunning(true);
        contestController.update(contest);
        runningContest = bean.printContestName(contest);
    }

    public void stop(Contest contest) {
        runningContest.cancel(true);
        contest.setRunning(false);
        contestController.update(contest);
    }
}
