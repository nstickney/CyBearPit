package is.stma.judgebean.beanpoll.data;

import is.stma.judgebean.beanpoll.model.Parameter;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@RequestScoped
public class ParameterListProducer extends AbstractEntityListProducer<Parameter> {

    @Inject
    private ParameterRepo repo;

    private List<Parameter> entities;

    @Produces
    @Named("resourceParameters")
    public List<Parameter> getEntities() {
        return entities;
    }

    @Override
    @PostConstruct
    void retrieveAll() {
        entities = repo.findAllOrderByNameAsc();
    }
}
