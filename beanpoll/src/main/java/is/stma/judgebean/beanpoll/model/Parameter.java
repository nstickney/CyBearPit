package is.stma.judgebean.beanpoll.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Parameter extends AbstractEntity {

    // Boolean value strings
    public static final String TRUE = "TRUE";
    public static final String FALSE = "FALSE";

    // Utility tag strings
    private static final String UNUSED = "UNUSED";

    @ManyToOne
    private Resource resource;

    @Column(nullable = false)
    private String tag = UNUSED;

    @Column
    private String value;

    public Parameter() {
        super();
    }

    public Parameter(String tag, String value) {
        super();
        this.tag = tag;
        this.value = value;
    }

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
