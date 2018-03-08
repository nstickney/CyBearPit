package is.stma.judgebean.model;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.UUID;

@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

    @Id
    private final String id;

    public AbstractEntity() {
        this.id = UUID.randomUUID().toString();
    }

    public abstract String getName();

    public boolean equalByUUID(AbstractEntity other) {
        return getId().equals(other.getId());
    }

    public String getId() {
        return id;
    }

}