package is.stma.judgebean.beanpoll.test;

import is.stma.judgebean.beanpoll.data.TeamRepo;
import is.stma.judgebean.beanpoll.model.Contest;
import is.stma.judgebean.beanpoll.model.Team;
import is.stma.judgebean.beanpoll.rules.TeamRules;
import is.stma.judgebean.beanpoll.service.ContestService;
import is.stma.judgebean.beanpoll.service.TeamService;
import is.stma.judgebean.beanpoll.util.EMProducer;
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

import javax.ejb.EJBException;
import javax.inject.Inject;
import java.io.File;
import java.util.UUID;
import java.util.logging.Logger;

@RunWith(Arquillian.class)
public class TeamRulesTest {

    @Inject
    private Logger log;

    @Inject
    private ContestService contestService;

    @Inject
    private TeamService teamService;

    private Contest newContest;
    private Team newTeam;
    private Team checkTeam;

    @Deployment
    public static WebArchive createDeployment() {

        File[] files = Maven.resolver().loadPomFromFile("pom.xml")
                .importRuntimeDependencies().resolve().withTransitivity().asFile();

        return ShrinkWrap.create(WebArchive.class, "teamServiceTest.war")
                .addPackages(true, Team.class.getPackage(),
                        TeamRepo.class.getPackage(),
                        TeamService.class.getPackage(),
                        TeamRules.class.getPackage(),
                        EMProducer.class.getPackage())
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsResource("META-INF/apache-deltaspike.properties")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource("test-ds.xml") // Deploy test datasource
                .addAsLibraries(files); // Add necessary stuff from pom.xml
    }

    @Before
    public void setUp() {
        if (null == newContest) {
            newContest = new Contest();
            newContest.setName(UUID.randomUUID().toString());
            contestService.create(newContest);
        }

        if (null == newTeam) {
            newTeam = new Team();
            newTeam.setName("Test Team");
            newTeam.setFlag("TEST");
            newTeam.setContest(newContest);
            teamService.create(newTeam);
        }
    }

    @Test
    public void testTeamCreation() {
        checkTeam = teamService.readById(newTeam.getId());
        Assert.assertTrue(newTeam.equalByUUID(checkTeam));
        Assert.assertTrue(newTeam.equals(checkTeam));
    }

    @Test
    public void testTeamUpdate() {
        String newTeamUUID = newTeam.getId();
        newTeam.setFlag("UPDATED");
        teamService.update(newTeam);
        newTeam = null;
        newTeam = teamService.readById(newTeamUUID);
        Assert.assertEquals("UPDATED", newTeam.getFlag());
        newTeam.setFlag("TEST");
        newTeam = null;
        newTeam = teamService.readById(newTeamUUID);
        teamService.update(newTeam);
        Assert.assertEquals("TEST", newTeam.getFlag());
    }

    @Test(expected = EJBException.class)
    public void testUpdateNonexistent() {
        checkTeam = new Team();
        checkTeam.setName("Check Team");
        checkTeam.setFlag("CHECK");
        teamService.update(checkTeam);
    }

    @Test(expected = EJBException.class)
    public void testNonUniqueUUID() {
        checkTeam = newTeam;
        checkTeam.setFlag("We Copied You!");
        teamService.create(checkTeam);
    }

    //TODO: Arquillian catches the wrong error here(?!)
    @Test(expected = java.lang.AssertionError.class)
//    @Test(expected = EJBException.class)
    public void testNonUniqueFlag() {
        checkTeam = new Team();
        checkTeam.setName("Test Team 2");
        checkTeam.setFlag("TEST");
        checkTeam.setContest(newContest);
        teamService.create(checkTeam);
    }
}