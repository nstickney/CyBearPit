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

@Entity
public class Poll extends AbstractEntity implements Comparable<Poll> {

    @ManyToOne
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "resource_id", referencedColumnName = "id")
    private Resource resource;

    @Column
    private LocalDateTime timestamp = LocalDateTime.now();

    @Column
    private int score = 0;

    @Lob
    @Column
    private String results;

    @Override
    public String getName() {
        return resource.getName() + ": " + timestamp.toString();
    }

    @Override
    public int compareTo(Poll poll) {
        if (timestamp.isBefore(poll.timestamp)) {
            return -1;
        }
        if (timestamp.isAfter(poll.timestamp)) {
            return 1;
        }
        return getId().compareTo(poll.getId());
    }

    @Override
    public boolean equals(Object o) {
        return null != o &&
                o.getClass().equals(getClass()) &&
                0 == compareTo((Poll) o);
    }

    @Override
    public int hashCode() {
        String hashCodeString = getId();
        return hashCodeString.hashCode();
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int total) {
        this.score = total;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getResults() {
        return results;
    }

    public void setResults(String information) {
        this.results = information;
    }
}
