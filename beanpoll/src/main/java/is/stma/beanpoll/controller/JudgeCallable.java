/*
 * Copyright 2018 Nathaniel Stickney
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package is.stma.beanpoll.controller;

import is.stma.beanpoll.controller.poller.AbstractPoller;
import is.stma.beanpoll.controller.poller.PollerFactory;
import is.stma.beanpoll.model.Contest;
import is.stma.beanpoll.model.Poll;
import is.stma.beanpoll.model.Resource;
import is.stma.beanpoll.service.ContestService;
import is.stma.beanpoll.service.PollService;

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
