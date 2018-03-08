package is.stma.judgebean.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class ResourceParameter extends AbstractEntity {

    @ManyToOne
    private Resource resource;

    @Override
    public String getName() {
        return getId();
    }

    public enum ParameterTag {

        // These tags can apply to any kind of resource
        TYPE, HOST, PORT, CHECK_FLAG,

        // These flags apply only to DNS resources
        QUERY, RECORD_TYPE, TCP, RECURSIVE, TIMEOUT, CHECK_DEFAULT
    }

    public enum ResourceTag {
        DNS, HTTP
    }

}
