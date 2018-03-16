package is.stma.judgebean.beanpoll.controller;

import is.stma.judgebean.beanpoll.model.Contest;
import is.stma.judgebean.beanpoll.model.Poll;
import is.stma.judgebean.beanpoll.model.Resource;
import is.stma.judgebean.beanpoll.service.ContestService;
import is.stma.judgebean.beanpoll.service.PollService;

import java.time.LocalDate;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JudgeCallable implements Callable<String> {

    private static final int POLLING_INTERVAL = 5;

    private Contest contest;
    private ContestService contestService;
    private PollService pollService;
    private Logger log;

    JudgeCallable() {
        super();
    }

    JudgeCallable(Contest contest, ContestService contestService, PollService pollService, Logger log) {
        super();
        this.contest = contest;
        this.contestService = contestService;
        this.pollService = pollService;
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
        while (contest.isRunning()) {

            // Do a poll cycle
            doPoll();

            // Wait one polling interval
            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(POLLING_INTERVAL));
            } catch (Exception e) {
                log.log(Level.INFO,"SLEEP INTERRUPTED");
            }

            // Update the contest from the database, to see if we should keep going
            contest = contestService.readById(contest.getId());
        }
        return contest.getId();
    }

    private void doPoll() {
        log.log(Level.INFO, "Polling: " + contest.getName());

        // Check each competition resource
        for (Resource r : contest.getResources()) {
            Poll newPoll = new Poll();
            newPoll.setResource(r);
            newPoll.setInformation("Checking " + r.getName() + " for " + contest.getName());
            newPoll.setScore(r.getWeight());
            newPoll.setTimestamp(LocalDate.now());
            pollService.create(newPoll);
        }

        log.log(Level.INFO, "Polled: " + contest.getName());
    }
}
