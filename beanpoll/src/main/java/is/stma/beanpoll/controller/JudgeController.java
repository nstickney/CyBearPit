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

import is.stma.beanpoll.model.Contest;
import is.stma.beanpoll.service.ContestService;
import is.stma.beanpoll.service.PollService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
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

    @Inject
    private PollService pollService;

    private ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);

    private List<IdentifiableFuture> running = new ArrayList<>();

    public void run(Contest contest) {
        contest.setEnabled(true);
        contestController.update(contest);
        running.add(new IdentifiableFuture(contest.getId(),
                executorService.submit(new JudgeCallable(contest, contestService, pollService, log))));
    }

    public void stop(Contest contest) {
        List<IdentifiableFuture> found = new ArrayList<>();
        for (IdentifiableFuture i : running) {
            if (i.getId().equals(contest.getId())) {
                i.getFuture().cancel(true);
                found.add(i);
            }
        }
        for (IdentifiableFuture i : found) {
            running.remove(i);
        }
        contest.setEnabled(false);
        contestController.update(contest);
    }

    private static class IdentifiableFuture {

        private String id;

        private Future<String> future;

        IdentifiableFuture(String id, Future<String> future) {
            this.id = id;
            this.future = future;
        }

        String getId() {
            return id;
        }

        Future<String> getFuture() {
            return future;
        }

    }
}
