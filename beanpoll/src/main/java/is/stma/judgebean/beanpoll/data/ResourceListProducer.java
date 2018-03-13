package is.stma.judgebean.beanpoll.data;

import is.stma.judgebean.beanpoll.model.Resource;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequestScoped
public class ResourceListProducer extends AbstractEntityListProducer<Resource> {

    @Inject
    private ResourceRepo repo;

    private List<Resource> entities;

    @Produces
    @Named("resources")
    public List<Resource> getEntities() {
        return entities;
    }

    @Override
    @PostConstruct
    void retrieveAll() {
        entities =  repo.findAllOrderByNameAsc();
        Collections.sort(entities);
    }
}
