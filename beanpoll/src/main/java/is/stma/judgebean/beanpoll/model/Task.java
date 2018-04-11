/*
 * Copyright 2018 Nathaniel Stickney
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package is.stma.judgebean.beanpoll.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Task extends AbstractComparableByContest {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private int pointValue;

    @Column
    private String description;

    @Column
    private LocalDateTime starts;

    @Column
    private LocalDateTime ends;

    @ManyToOne
    private Contest contest;

    @OneToMany(mappedBy = "task")
    private List<Response> responses = new ArrayList<>();

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(AbstractComparableByContest o) {
        return compare(this, o);
    }

    public int getPointValue() {
        return pointValue;
    }

    public void setPointValue(int pointValue) {
        this.pointValue = pointValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStarts() {
        return starts;
    }

    public void setStarts(LocalDateTime available) {
        this.starts = available;
    }

    public LocalDateTime getEnds() {
        return ends;
    }

    public void setEnds(LocalDateTime expiration) {
        this.ends = expiration;
    }

    public Contest getContest() {
        return contest;
    }

    public void setContest(Contest contest) {
        this.contest = contest;
    }

    public List<Response> getResponses() {
        return responses;
    }

    public void setResponses(List<Response> responses) {
        this.responses = responses;
    }

    public Response getResponseByTeam(Team team) {
        for (Response r : responses) {
            if (r.getTeam().equalByUUID(team)) {
                return r;
            }
        }
        return null;
    }

    public boolean isAvailable() {
        return contest.isEnabled() && contest.isRunning()
                && starts.isBefore(LocalDateTime.now())
                && ends.isAfter(LocalDateTime.now());
    }

    public String getLook() {
        if (contest == null) {
            return "danger";
        } else if (!contest.isRunning()) {
            return "default";
        } else if (ends.isBefore(LocalDateTime.now())) {
            return "warning";
        } else if (starts.isAfter(LocalDateTime.now())) {
            return "info";
        }
        return "success";
    }

    public String getLookFor(Team team) {

        long WARNING_TIME_LIMIT = 15L;
        long DANGER_TIME_LIMIT = 5L;

        if (null != getResponseByTeam(team)) {
            return "success";
        } else if (ends.isBefore(LocalDateTime.now().plusMinutes(DANGER_TIME_LIMIT))) {
            return "danger";
        } else if (ends.isBefore(LocalDateTime.now().plusMinutes(WARNING_TIME_LIMIT))) {
            return "warning";
        }
        return "default";
    }
}
