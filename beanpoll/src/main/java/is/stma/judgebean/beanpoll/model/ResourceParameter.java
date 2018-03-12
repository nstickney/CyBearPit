package is.stma.judgebean.beanpoll.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class ResourceParameter extends AbstractEntity {

    // DNS tag strings
    private static final String DNS_QUERY = "DNS_QUERY";
    private static final String DNS_RECORD_TYPE = "DNS_RECORD_TYPE";
    private static final String DNS_TCP = "DNS_TCP";
    private static final String DNS_RECURSIVE = "DNS_RECURSIVE";
    private static final String DNS_TIMEOUT = "DNS_TIMEOUT";

    // Boolean value strings
    private static final String TRUE = "TRUE";
    private static final String FALSE = "FALSE";

    @ManyToOne
    private Resource resource;

    @Column(nullable = false)
    private String tag = DNS_QUERY;

    @Column
    private String value = null;

    @Override
    public String getName() {
        return getId();
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
