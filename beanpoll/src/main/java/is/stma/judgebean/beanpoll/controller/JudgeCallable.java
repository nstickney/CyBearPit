package is.stma.judgebean.beanpoll.controller;

import is.stma.judgebean.beanpoll.model.Contest;
import is.stma.judgebean.beanpoll.service.ContestService;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JudgeCallable implements Callable<String> {

    private Contest contest;
    private ContestService service;
    private Logger log;

    JudgeCallable() {
        super();
    }

    JudgeCallable(Contest contest, ContestService service, Logger log) {
        super();
        this.contest = contest;
        this.service = service;
        this.log = log;
    }

    public Contest getContest() {
        return contest;
    }

    public void setContest(Contest contest) {
        this.contest = contest;
    }

    @Override
    public String call() {
        log.log(Level.INFO,0 + ": " + contest.getName());
        for (int i = 1; i < 60; i++) {
            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(10));
            } catch (Exception e) {
                log.log(Level.INFO,"SLEEP INTERRUPTED");
            }
            contest = service.readById(contest.getId());
            log.log(Level.INFO,i + ": " + contest.getName());
        }
        return contest.getId();
    }
}
