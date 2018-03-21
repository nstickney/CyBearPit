/*
 * Copyright 2018 Nathaniel Stickney
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package is.stma.judgebean.beanpoll.test;

import is.stma.judgebean.beanpoll.controller.parameterizer.HTTPParameterizer;
import is.stma.judgebean.beanpoll.controller.poller.AbstractPoller;
import is.stma.judgebean.beanpoll.controller.poller.PollerFactory;
import is.stma.judgebean.beanpoll.data.PollRepo;
import is.stma.judgebean.beanpoll.model.*;
import is.stma.judgebean.beanpoll.rules.PollRules;
import is.stma.judgebean.beanpoll.service.*;
import is.stma.judgebean.beanpoll.util.EMProducer;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(Arquillian.class)
public class HTTPPollerTest {

    @Inject
    private ContestService contestService;

    @Inject
    private ParameterService parameterService;

    @Inject
    private ResourceService resourceService;

    @Inject
    private TeamService teamService;

    private Contest contest;
    private String contestID;

    private Poll poll;

    private Team bonusTeam;
    private Team baylorTeam;
    private String bonusTeamID;
    private String baylorTeamID;

    private Resource resource;
    private String resourceID;

    @Deployment
    public static Archive<?> createTestArchive() {

        File[] files = Maven.resolver().loadPomFromFile("pom.xml")
                .importRuntimeDependencies().resolve().withTransitivity().asFile();

        return ShrinkWrap.create(WebArchive.class, "HTTPPollerTest.war")
                .addPackages(true, Poll.class.getPackage(),
                        PollRepo.class.getPackage(),
                        PollService.class.getPackage(),
                        PollRules.class.getPackage(),
                        HTTPParameterizer.class.getPackage(),
                        AbstractPoller.class.getPackage(),
                        EMProducer.class.getPackage())
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsResource("META-INF/apache-deltaspike.properties")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource("test-ds.xml") // Deploy test datasource
                .addAsLibraries(files); // Add necessary stuff from pom.xml
    }

    @Before
    public void setUp() {
        try {
            contest = contestService.readById(contestID);
        } catch (Exception e) {
            buildContest();
        }

        try {
            bonusTeam = teamService.readById(bonusTeamID);
            baylorTeam = teamService.readById(baylorTeamID);
        } catch (Exception e) {
            buildTeams();
        }

        try {
            resource = resourceService.readById(resourceID);
        } catch (Exception e) {
            buildResource();
        }
    }

    private void buildContest() {
        contest = new Contest();
        contest.setName(UUID.randomUUID().toString());
        contest = contestService.create(contest);
        contestID = contest.getId();
    }

    private void buildTeams() {
        bonusTeam = new Team();
        bonusTeam.setContest(contest);
        bonusTeam.setName(UUID.randomUUID().toString());
        bonusTeam.setFlag("BONUSPOINTS");
        bonusTeam = teamService.create(bonusTeam);
        bonusTeamID = bonusTeam.getId();
        baylorTeam = new Team();
        baylorTeam.setContest(contest);
        baylorTeam.setName(UUID.randomUUID().toString());
        baylorTeam.setFlag("Baylor University");
        baylorTeam = teamService.create(baylorTeam);
        baylorTeamID = bonusTeam.getId();
        contest = contestService.readById(contestID);
    }

    private void buildResource() {
        resource = new Resource();
        resource.setContest(contest);
        resource.setName(UUID.randomUUID().toString());
        resource.setAddress("httpbin.org");
        resource.setPort(80);
        resource.setType(Resource.HTTP);
        resource.setScoring(true);
        Parameter testParameter = new Parameter();
        testParameter.setTag(HTTPParameterizer.HTTP_RESOLVER);
        testParameter = parameterService.create(testParameter);
        List<Parameter> parameters = new ArrayList<>();
        parameters.add(testParameter);
        resource.setParameters(parameters);
        resource = resourceService.create(resource);
        for (Parameter p : parameters) {
            p.setResource(resource);
            parameterService.update(p);
        }
        resourceID = resource.getId();
        contest = contestService.readById(contestID);
    }

    private void setAddress(String address) {
        resource.setAddress(address);
        resource = resourceService.update(resource);
    }

    private void setResovler(String resolver) {
        Parameter testParameter = new Parameter();
        testParameter.setTag(HTTPParameterizer.HTTP_RESOLVER);
        testParameter.setValue(resolver);
        testParameter = parameterService.create(testParameter);
        List<Parameter> parameters = new ArrayList<>();
        parameters.add(testParameter);
        resource.setParameters(parameters);
        resource = resourceService.update(resource);
        for (Parameter p : parameters) {
            p.setResource(resource);
            parameterService.update(p);
        }
    }

    @Test
    public void testPollWorks() {
        AbstractPoller poller = PollerFactory.getPoller(resource);
        poll = poller.poll();
        Assert.assertTrue(poll.getInformation().contains("BONUSPOINTS"));
        Assert.assertTrue(poll.getTeam().equals(bonusTeam));
    }

    @Test
    public void testFullURL() {
        resource.setAddress("http://httpbin.org");
        AbstractPoller poller = PollerFactory.getPoller(resource);
        poll = poller.poll();
        Assert.assertTrue(poll.getInformation().contains("BONUSPOINTS"));
        Assert.assertTrue(poll.getTeam().equals(bonusTeam));
    }

    @Test
    public void testPollFails() {
        setAddress("baylor.ccdc");
        AbstractPoller poller = PollerFactory.getPoller(resource);
        poll = poller.poll();
        Assert.assertEquals("ERROR: ", poll.getInformation().substring(0,7));
        Assert.assertEquals(null, poll.getTeam());
    }

    @Test
    public void testBadResolver() {
        setResovler("123.123.132.132");
        AbstractPoller poller = PollerFactory.getPoller(resource);
        poll = poller.poll();
        Assert.assertThat(poll.getInformation(), anyOf(equalTo("DNS ERROR: network error"), equalTo("DNS ERROR: timed out")));
    }

    @Test
    public void testBadResolverIP() {
        setResovler("256.0.0.1");
        AbstractPoller poller = PollerFactory.getPoller(resource);
        poll = poller.poll();
        Assert.assertEquals("DNS ERROR: resolution failed", poll.getInformation());
    }

    @Test
    public void testResolutionFails() {
        setAddress("baylor.ccdc");
        setResovler("9.9.9.9");
        AbstractPoller poller = PollerFactory.getPoller(resource);
        poll = poller.poll();
        Assert.assertThat(poll.getInformation(), anyOf(equalTo("DNS ERROR: type not found"), equalTo("DNS ERROR: host not found")));
    }

    @Test
    public void testNoTeamGetsPoints() {
        setAddress("usma.edu");
        AbstractPoller poller = PollerFactory.getPoller(resource);
        poll = poller.poll();
        Assert.assertEquals(null, poll.getTeam());
    }

    @Test
    public void testMultipleTeams() {
        setAddress("baylor.edu");
        AbstractPoller poller = PollerFactory.getPoller(resource);
        poll = poller.poll();
        Assert.assertTrue(poll.getTeam().equals(baylorTeam));
    }

    @Test
    public void testIPAddress() {
        setAddress("208.123.73.69");
        AbstractPoller poller = PollerFactory.getPoller(resource);
        poll = poller.poll();
        Assert.assertTrue(poll.getInformation().contains("Netgate"));
        Assert.assertTrue(null == poll.getTeam());
    }
}
