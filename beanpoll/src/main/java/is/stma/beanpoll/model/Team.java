/*
 * Copyright 2018 Nathaniel Stickney
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package is.stma.beanpoll.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team extends AbstractComparableByContest {

    @Column(nullable = false)
    private String name;

    @Column
    private String flag;

    @ManyToOne
    private Contest contest;

    @OneToMany(mappedBy = "team")
    private List<User> users;

    @ManyToMany(mappedBy = "teams")
    private List<Resource> resources = new ArrayList<>();

    @OneToMany(mappedBy = "team")
    private List<Captured> captureds = new ArrayList<>();

    @OneToMany(mappedBy = "team")
    private List<Response> responses = new ArrayList<>();

    @OneToMany(mappedBy = "team")
    private List<Report> reports = new ArrayList<>();

    @OneToMany(mappedBy = "team")
    private List<Poll> polls = new ArrayList<>();

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int compareTo(AbstractComparableByContest o) {
        return compare(this, o);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Contest getContest() {
        return contest;
    }

    public void setContest(Contest contest) {
        this.contest = contest;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public List<Captured> getCaptureds() {
        return captureds;
    }

    public void setCaptureds(List<Captured> captureds) {
        this.captureds = captureds;
    }

    public List<Response> getResponses() {
        return responses;
    }

    public void setResponses(List<Response> responses) {
        this.responses = responses;
    }

    public List<Report> getReports() {
        return reports;
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
    }

    public List<Poll> getPolls() {
        return polls;
    }

    public void setPolls(List<Poll> polls) {
        this.polls = polls;
    }

    public int getScore() {
        int score = 0;
        for (Captured c : captureds) {
            score += c.getScore();
        }
        for (Poll p : polls) {
            score += p.getScore();
        }
        for (Response r : responses) {
            score += r.getScore();
        }
        for (Report r : reports) {
            score += r.getScore();
        }
        return score;
    }

    public int getResourceScore(Resource resource) {
        int score = 0;
        for (Poll p : polls) {
            if (null != p.getResource() && p.getResource().equalByUUID(resource)) {
                score += p.getScore();
            }
        }
        return score;
    }

    public List<Resource> getVisibleResources() {
        List<Resource> visible = new ArrayList<>();
        if (contest.isEnabled() && contest.isRunning()) {
            for (Resource r : contest.getResources()) {
                if (r.isAvailable() && (r.getTeams().contains(this) || r.getTeams().isEmpty())) {
                    visible.add(r);
                }
            }
        }
        return visible;
    }

    public List<Task> getAvailableTasks() {
        List<Task> available = new ArrayList<>();
        if (contest.isEnabled() && contest.isRunning()) {
            for (Task t : contest.getTasks()) {
                if (t.getPublished().isBefore(LocalDateTime.now()) &&
                        t.getDue().isAfter(LocalDateTime.now())) {
                    available.add(t);
                }
            }
        }
        return available;
    }
}
