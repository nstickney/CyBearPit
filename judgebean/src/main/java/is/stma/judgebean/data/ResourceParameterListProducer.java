package is.stma.judgebean.data;

import is.stma.judgebean.model.ResourceParameter;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@RequestScoped
public class ResourceParameterListProducer extends AbstractEntityListProducer<ResourceParameter> {

    @Inject
    private ResourceParameterRepo repo;

    private List<ResourceParameter> entities;

    @Produces
    @Named("resourceParameters")
    public List<ResourceParameter> getEntities() {
        return entities;
    }

    @Override
    @PostConstruct
    void retrieveAll() {
        entities = repo.findAllOrderByNameAsc();
    }
}
