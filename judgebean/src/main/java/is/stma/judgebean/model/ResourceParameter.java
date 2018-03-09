package is.stma.judgebean.model;

import is.stma.judgebean.util.DNSUtility;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Null;

@Entity
public class ResourceParameter extends AbstractEntity {

    @ManyToOne
    private Resource resource;

    @Column(nullable = false)
    private ParameterTag tag = ParameterTag.HOST;

    @Column
    private String value = null;

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

}
