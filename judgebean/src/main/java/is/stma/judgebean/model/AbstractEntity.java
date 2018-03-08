package is.stma.judgebean.model;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.UUID;

@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

    /* Abstract Methods ----------------------------------------------------- */
    public abstract String getName();

    /* Fields --------------------------------------------------------------- */
    @Id
    private final String id;

    /* Constructor ---------------------------------------------------------- */
    public AbstractEntity() {
        this.id = UUID.randomUUID().toString();
    }

    /* Comparison ----------------------------------------------------------- */
    public boolean equalByUUID(AbstractEntity other) {
        return getId().equals(other.getId());
    }

    /* Getters and Setters -------------------------------------------------- */
    public String getId() {
        return id;
    }

}