package is.stma.judgebean.beanpoll.controller;

import is.stma.judgebean.beanpoll.model.Contest;

import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Singleton;
import javax.inject.Inject;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
public class JudgeBean {

    @Inject
    private Logger log;

    @Asynchronous
    public Future<String> printContestName(Contest contest) {
        String contestId = contest.getId();
        log.log(Level.INFO, 0 + ": " + contest.getName());
        for (int i = 1; i < 20; i++) {
            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(30));
            } catch (Exception e) {
                log.log(Level.INFO, "SLEEP INTERRUPTED");
            }
            log.log(Level.INFO, i + ": " + contest.getName());
        }
        return new AsyncResult<>(contestId);
    }
}
