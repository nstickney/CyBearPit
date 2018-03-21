package is.stma.judgebean.beanpoll.controller;

import is.stma.judgebean.beanpoll.model.Contest;
import is.stma.judgebean.beanpoll.service.ContestService;
import is.stma.judgebean.beanpoll.service.PollService;

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
