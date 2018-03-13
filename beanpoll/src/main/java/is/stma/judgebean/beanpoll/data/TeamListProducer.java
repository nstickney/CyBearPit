package is.stma.judgebean.beanpoll.data;

import is.stma.judgebean.beanpoll.model.Team;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequestScoped
public class TeamListProducer extends AbstractEntityListProducer<Team> {

    @Inject
    private TeamRepo repo;

    private List<Team> entities;

    @Produces
    @Named("teams")
    public List<Team> getEntities() {
        return entities;
    }

    @Override
    @PostConstruct
    void retrieveAll() {
        entities = repo.findAllOrderByNameAsc();
        Collections.sort(entities);
    }
}
