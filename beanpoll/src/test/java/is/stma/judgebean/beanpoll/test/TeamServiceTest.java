package is.stma.judgebean.beanpoll.test;

import is.stma.judgebean.beanpoll.data.AbstractRepo;
import is.stma.judgebean.beanpoll.data.TeamRepo;
import is.stma.judgebean.beanpoll.model.*;
import is.stma.judgebean.beanpoll.rules.AbstractRules;
import is.stma.judgebean.beanpoll.rules.TeamRules;
import is.stma.judgebean.beanpoll.service.AbstractService;
import is.stma.judgebean.beanpoll.service.TeamService;
import is.stma.judgebean.beanpoll.util.EMProducer;
import is.stma.judgebean.beanpoll.util.EntityUtility;
import is.stma.judgebean.beanpoll.util.LogProducer;
import is.stma.judgebean.beanpoll.util.StringUtility;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.io.File;
import java.util.logging.Logger;

@RunWith(Arquillian.class)
public class TeamServiceTest {

    @Inject
    private Logger log;

    @Inject
    private TeamService service;

    private Team newTeam;

    private Team checkTeam;

    @Deployment
    public static WebArchive createDeployment() {

        File[] files = Maven.resolver().loadPomFromFile("pom.xml")
                .importRuntimeDependencies().resolve().withTransitivity().asFile();

        return ShrinkWrap.create(WebArchive.class, "teamServiceTest.war")
                .addClasses(AbstractEntity.class, Team.class,
                        Contest.class, Resource.class, ResourceParameter.class, User.class, Task.class,
                        AbstractRepo.class, TeamRepo.class,
                        AbstractService.class, TeamService.class,
                        AbstractRules.class, TeamRules.class,
                        EntityUtility.class, EMProducer.class, LogProducer.class, StringUtility.class)
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsResource("META-INF/apache-deltaspike.properties")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource("test-ds.xml") // Deploy test datasource
                .addAsLibraries(files); // Add necessary stuff from pom.xml
    }

    @Before
    public void setUp() {
        newTeam = new Team();
        newTeam.setName("Bala Morgab");
        newTeam.setFlag("BMG");
        service.create(newTeam);
    }

    @Test
    public void testTeamCreation() {
        checkTeam = service.readById(newTeam.getId());
        Assert.assertTrue(newTeam.equalByUUID(checkTeam));
        Assert.assertTrue(newTeam.equals(checkTeam));
    }

    @Test(expected = Exception.class)
    public void testNonUniqueUUID() {
        checkTeam = newTeam;
        checkTeam.setFlag("We Copied You!");
        service.create(checkTeam);
    }

    //TODO: This exception is too broad - need to narrowly tailor!
    @Test(expected = Exception.class)
    public void testNonUniqueFlag() {
        checkTeam = new Team();
        checkTeam.setName("Bala Morgab 2");
        checkTeam.setFlag("BMG");
        service.create(checkTeam);
    }
}
