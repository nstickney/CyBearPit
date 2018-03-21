package is.stma.judgebean.beanpoll.controller;

import is.stma.judgebean.beanpoll.controller.poller.AbstractPoller;
import is.stma.judgebean.beanpoll.controller.poller.PollerFactory;
import is.stma.judgebean.beanpoll.model.Contest;
import is.stma.judgebean.beanpoll.model.Poll;
import is.stma.judgebean.beanpoll.model.Resource;
import is.stma.judgebean.beanpoll.service.ContestService;
import is.stma.judgebean.beanpoll.service.PollService;

import java.time.LocalDateTime;
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
        while (contest.isEnabled()) {

            // Check to see if it's time to start actually polling this contest
            if (!contest.isRunning() && null != contest.getStarts()) {
                if (contest.getStarts().isBefore(LocalDateTime.now())) {
                    contest.setRunning(true);
                    contest = contestService.update(contest);
                }
            }

            // If no start time is set, we should just start polling now!
            if (!contest.isRunning() && null == contest.getStarts()) {
                contest.setRunning(true);
                contest = contestService.update(contest);
            }

            // Check to see if it's time to stop polling this contest
            if (contest.isRunning() && null != contest.getEnds()) {
                if (contest.getEnds().isBefore(LocalDateTime.now())) {
                    contest.setRunning(false);

                    // In this case we need to update the contest entirely
                    contest.setEnabled(false);
                    contest = contestService.update(contest);
                }
            }

            // Do a poll cycle
            if (contest.isRunning()) {
                doPoll();
            }

            // Wait one polling interval
            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(POLLING_INTERVAL));
            } catch (Exception e) {
                log.log(Level.WARNING, contest.getLogName() + ": Polling interrupted");
            }

            // Update the contest from the database, to see if we should keep going
            contest = contestService.readById(contest.getId());
        }

        // If the contest is no longer enabled, it shouldn't be running either
        contest.setRunning(false);
        contest = contestService.update(contest);

        return contest.getId();
    }

    private void doPoll() {
        log.log(Level.INFO, "Polling " + contest.getLogName());

        // Check each competition resource
        for (Resource r : contest.getResources()) {
            AbstractPoller poller = PollerFactory.getPoller(r);
            Poll thisPoll = poller.poll();
            log.log(Level.INFO, thisPoll.getName());
            pollService.create(thisPoll);
        }

        log.log(Level.INFO, "Poll complete for " + contest.getLogName());
    }
}
