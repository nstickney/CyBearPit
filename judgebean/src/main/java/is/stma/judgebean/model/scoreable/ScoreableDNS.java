package is.stma.judgebean.model.scoreable;

import is.stma.judgebean.model.poll.APoll;
import is.stma.judgebean.model.poll.PollDNS;

import javax.enterprise.inject.Model;

@Model
public class ScoreableDNS extends AScoreable {

    /* Overrides ------------------------------------------------------------ */
    @Override
    public APoll createPoll() {
        return new PollDNS(this);
    }

    /* Fields --------------------------------------------------------------- */

    /* Getters and Setters -------------------------------------------------- */

    /* Methods -------------------------------------------------------------- */
}
