package is.stma.judgebean.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class ResourceParameter extends AbstractEntity {

    /* Fields --------------------------------------------------------------- */

    /* Overrides ------------------------------------------------------------ */
    @Override
    public String getName() {
        return getId();
    }

    /* Getters and Setters -------------------------------------------------- */

    /* Methods -------------------------------------------------------------- */
}
