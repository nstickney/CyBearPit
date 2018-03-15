package is.stma.judgebean.beanpoll.controller;

import is.stma.judgebean.beanpoll.model.Contest;
import is.stma.judgebean.beanpoll.service.ContestService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.logging.Logger;

@ApplicationScoped
@Named("judge")
public class JudgeController {

    private static final int NUM_THREADS = 8;

    @Inject
    private Logger log;

    @Inject
    private ContestController contestController;

    @Inject
    private ContestService contestService;

    private ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);

    private List<Future> running = new ArrayList<>();

    public void run(Contest contest) {
        contest.setRunning(true);
        JudgeCallable contestJudge = new JudgeCallable(contest, contestService, log);
        Future<String> runningContest = executorService.submit(contestJudge);
        running.add(runningContest);
        contestController.update(contest);
    }

    public void stop(Contest contest) {
//        runningContest.cancel(true);
        contest.setRunning(false);
        contestController.update(contest);
    }
}
