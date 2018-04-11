/*
 * Copyright 2018 Nathaniel Stickney
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package is.stma.judgebean.beanpoll.test;

import is.stma.judgebean.beanpoll.model.Capturable;
import is.stma.judgebean.beanpoll.model.Captured;
import is.stma.judgebean.beanpoll.model.Contest;
import is.stma.judgebean.beanpoll.model.Team;

import java.time.LocalDateTime;
import java.util.UUID;

class TestUtility {

    static Contest makeContest() {
        Contest contest = new Contest();
        contest.setName(UUID.randomUUID().toString());
        contest.setStarts(LocalDateTime.now().minusMinutes(10));
        contest.setEnds(LocalDateTime.now().plusMinutes(10));
        return contest;
    }

    static Capturable makeCapturable(Contest contest, String flag, int points) {
        Capturable capturable = new Capturable();
        capturable.setContest(contest);
        capturable.setName(UUID.randomUUID().toString());
        capturable.setFlag(flag);
        capturable.setPointValue(points);
        return capturable;
    }

    static Team makeTeam(Contest contest, String flag) {
        Team team = new Team();
        team.setContest(contest);
        team.setName(UUID.randomUUID().toString());
        team.setFlag(flag);
        return team;
    }

    static Captured makeCaptured(Capturable capturable, Team team) {
        Captured captured = new Captured();
        captured.setCapturable(capturable);
        captured.setTeam(team);
        captured.setFlag(capturable.getFlag());
        captured.setScore(capturable.getPointValue());
        return captured;
    }
}
