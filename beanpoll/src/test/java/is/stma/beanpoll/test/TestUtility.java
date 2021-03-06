/*
 * Copyright 2018 Nathaniel Stickney
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package is.stma.beanpoll.test;

import is.stma.beanpoll.model.*;
import is.stma.beanpoll.service.ParameterService;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

class TestUtility {

    static Announcement makeAnnouncement(Contest contest) {
        Announcement announcement = new Announcement();
        announcement.setContest(contest);
        announcement.setName(UUID.randomUUID().toString());
        announcement.setContents(UUID.randomUUID().toString());
        return announcement;
    }

    static Capturable makeCapturable(Contest contest) {
        Capturable capturable = new Capturable();
        capturable.setContest(contest);
        capturable.setName(UUID.randomUUID().toString());
        capturable.setFlag(UUID.randomUUID().toString());
        capturable.setPointValue(ThreadLocalRandom.current().nextInt(1, 101));
        return capturable;
    }

    static Captured makeCaptured(Capturable capturable, Team team) {
        Captured captured = new Captured();
        captured.setCapturable(capturable);
        captured.setTeam(team);
        return captured;
    }

    static Contest makeContest() {
        Contest contest = new Contest();
        contest.setName(UUID.randomUUID().toString());
        contest.setStarts(LocalDateTime.now().minusMinutes(10));
        contest.setEnds(LocalDateTime.now().plusMinutes(10));
        return contest;
    }

    static Parameter makeParameter(Resource testResource) {
        Parameter parameter = new Parameter();
        parameter.setResource(testResource);
        parameter.setTag(UUID.randomUUID().toString());
        parameter.setValue(UUID.randomUUID().toString());
        return parameter;
    }

    static Poll makePoll(Resource testResource) {
        Poll poll = new Poll();
        poll.setResource(testResource);
        poll.setScore(0);
        poll.setResults("ERROR: Test Poll");
        return poll;
    }

    static Report makeReport(Team team) {
        Report report = new Report();
        report.setTeam(team);
        return report;
    }

    static Resource makeResource(Contest contest, ResourceType type) {
        Resource resource = new Resource();
        resource.setContest(contest);
        resource.setType(type);
        resource.setName(UUID.randomUUID().toString());
        resource.setAddress("127.0.0.1");
        resource.setPort(ThreadLocalRandom.current().nextInt(1, 65536));
        resource.setPointValue(ThreadLocalRandom.current().nextInt(1, 101));
        return resource;
    }

    static Response makeResponse(Task task, Team team) {
        Response response = new Response();
        response.setTask(task);
        response.setTeam(team);
        return response;
    }

    static Task makeTask(Contest contest) {
        Task task = new Task();
        task.setName(UUID.randomUUID().toString());
        task.setContest(contest);
        task.setPublished(LocalDateTime.now().minusMinutes(1));
        task.setDue(LocalDateTime.now().plusMinutes(1));
        return task;
    }

    static Team makeTeam(Contest contest) {
        Team team = new Team();
        team.setContest(contest);
        team.setName(UUID.randomUUID().toString());
        team.setFlag(UUID.randomUUID().toString());
        return team;
    }

    static User makeUser() {
        User user = new User();
        user.setName(UUID.randomUUID().toString());
        user.setPassword(UUID.randomUUID().toString());
        return user;
    }

    static void setResourceParameter(ParameterService service, Resource resource, String tag, String value) {
        for (Parameter p : resource.getParameters()) {
            if (p.getTag().equals(tag)) {
                p.setValue(value);
                p = service.update(p);
            }
        }
    }
}
