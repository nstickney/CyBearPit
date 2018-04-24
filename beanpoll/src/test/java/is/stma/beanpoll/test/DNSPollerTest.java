/*
 * Copyright 2018 Nathaniel Stickney
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package is.stma.beanpoll.test;

import is.stma.beanpoll.model.*;
import is.stma.beanpoll.service.*;
import is.stma.beanpoll.service.parameterizer.DNSParameterizer;
import is.stma.beanpoll.controller.poller.AbstractPoller;
import is.stma.beanpoll.controller.poller.PollerFactory;
import is.stma.beanpoll.data.PollRepo;
import is.stma.beanpoll.rules.PollRules;
import is.stma.beanpoll.util.EMProducer;
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

@RunWith(Arquillian.class)
public class DNSPollerTest {

    @Inject
    private ContestService contestService;

    @Inject
    private ParameterService parameterService;

    @Inject
    private ResourceService resourceService;

    @Inject
    private TeamService teamService;

    private Contest testContest;
    private String testContestId;

    private Poll testPoll;

    private Team testTeam;
    private Team checkTeam;
    private String testTeamId;
    private String checkTeamId;

    private Resource testResource;
    private String testResourceId;

    @Deployment
    public static Archive<?> createTestArchive() {

        File[] files = Maven.resolver().loadPomFromFile("pom.xml")
                .importRuntimeDependencies().resolve().withTransitivity().asFile();

        return ShrinkWrap.create(WebArchive.class, "HTTPPollerTest.war")
                .addPackages(true, Poll.class.getPackage(),
                        PollRepo.class.getPackage(),
                        PollService.class.getPackage(),
                        PollRules.class.getPackage(),
                        DNSParameterizer.class.getPackage(),
                        AbstractPoller.class.getPackage(),
                        EMProducer.class.getPackage())
                .addClass(TestUtility.class)
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsResource("META-INF/apache-deltaspike.properties")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource("test-ds.xml") // Deploy test datasource
                .addAsLibraries(files); // Add necessary stuff from pom.xml
    }

    @Before
    public void setUp() {
        try {
            testContest = contestService.readById(testContestId);
        } catch (Exception e) {
            buildContest();
        }

        try {
            testTeam = teamService.readById(testTeamId);
            checkTeam = teamService.readById(checkTeamId);
        } catch (Exception e) {
            buildTeams();
        }

        try {
            testResource = resourceService.readById(testResourceId);
        } catch (Exception e) {
            buildResource("9.9.9.9", "baylor.edu", "129.62.3.230");
        }
    }

    private void buildContest() {
        testContest = TestUtility.makeContest();
        testContest = contestService.create(testContest);
        testContestId = testContest.getId();
    }

    private void buildTeams() {
        testTeam = new Team();
        testTeam.setContest(testContest);
        testTeam.setName(UUID.randomUUID().toString());
        testTeam.setFlag("httpbin.org.herokudns.com");
        testTeam = teamService.create(testTeam);
        testTeamId = testTeam.getId();
        checkTeam = new Team();
        checkTeam.setContest(testContest);
        checkTeam.setName(UUID.randomUUID().toString());
        checkTeam.setFlag("adobe-idp-site-verification=e7f6f050-70cd-41a6-8ace-2ca8dcabbd26");
        checkTeam = teamService.create(checkTeam);
        checkTeamId = testTeam.getId();
        testContest = contestService.readById(testContestId);
    }

    private void buildResource(String address, String query, String expected) {
        testResource = TestUtility.makeResource(testContest, Resource.DNS);
        testResource.setAddress(address);
        testResource.setPort(53);
        testResource.setAvailable(true);
        Parameter testQueryParameter = new Parameter();
        testQueryParameter.setTag(DNSParameterizer.DNS_QUERY);
        testQueryParameter.setValue(query);
        testQueryParameter = parameterService.create(testQueryParameter);
        List<Parameter> parameters = new ArrayList<>();
        parameters.add(testQueryParameter);
        Parameter testExpectedParameter = new Parameter();
        testExpectedParameter.setTag(DNSParameterizer.DNS_EXPECTED);
        testExpectedParameter.setValue(expected);
        testExpectedParameter = parameterService.create(testExpectedParameter);
        parameters.add(testExpectedParameter);
        testResource.setParameters(parameters);
        testResource = resourceService.create(testResource);
        for (Parameter p : parameters) {
            p.setResource(testResource);
            parameterService.update(p);
        }
        testResourceId = testResource.getId();
        testContest = contestService.readById(testContestId);
    }

    @Test
    public void testPollWorks() {
        AbstractPoller poller = PollerFactory.getPoller(testResource);
        testPoll = poller.poll();
        Assert.assertTrue(testPoll.getResults().contains("129.62.3.230"));
        Assert.assertEquals(testPoll.getTeam(), checkTeam);
    }

    @Test
    public void testNoTeamGetsPoints() {
        Resource checkResource = TestUtility.makeResource(testContest, Resource.HTTP);
        checkResource = resourceService.create(checkResource);
        AbstractPoller poller = PollerFactory.getPoller(checkResource);
        testPoll = poller.poll();
        Assert.assertTrue(testPoll.getResults().contains(""));
        Assert.assertNull(testPoll.getTeam());
    }
}
