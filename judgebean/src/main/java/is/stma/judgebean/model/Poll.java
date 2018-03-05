package is.stma.judgebean.model;

import javax.enterprise.inject.Model;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Model
public class Poll extends AEntity {

    /* Fields --------------------------------------------------------------- */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contest_id")
    private Scoreable scoreable;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "result_id")
    private PollResult result;

    /* Overrides ------------------------------------------------------------ */
    @Override
    public String getName() {
        return "Poll: " + scoreable.getName() + ": " + getId();
    }

    /* Getters and Setters -------------------------------------------------- */
    public Scoreable getScoreable() {
        return scoreable;
    }

    public PollResult getResult() {
        return result;
    }

    /* Methods -------------------------------------------------------------- */
}
