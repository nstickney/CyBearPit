package is.stma.judgebean.model;

import javax.persistence.Id;
import java.util.UUID;

public abstract class AEntity {

    /* Abstract Methods ----------------------------------------------------- */
    public abstract String getName();

    /* Fields --------------------------------------------------------------- */
    @Id
    private final String id;

    /* Constructor ---------------------------------------------------------- */
    public AEntity() {
        this.id = UUID.randomUUID().toString();
    }

    /* Comparison ----------------------------------------------------------- */
    public boolean equalByUUID(AEntity other) {
        return getId().equals(other.getId());
    }

    /* Getters and Setters -------------------------------------------------- */
    public String getId() {
        return id;
    }

}