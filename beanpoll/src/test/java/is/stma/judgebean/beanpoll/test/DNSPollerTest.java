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

import is.stma.judgebean.beanpoll.controller.parameterizer.DNSParameterizer;
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
public class DNSPollerTest {

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
                        DNSParameterizer.class.getPackage(),
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
        bonusTeam.setFlag("httpbin.org.herokudns.com");
        bonusTeam = teamService.create(bonusTeam);
        bonusTeamID = bonusTeam.getId();
        baylorTeam = new Team();
        baylorTeam.setContest(contest);
        baylorTeam.setName(UUID.randomUUID().toString());
        baylorTeam.setFlag("adobe-idp-site-verification=e7f6f050-70cd-41a6-8ace-2ca8dcabbd26");
        baylorTeam = teamService.create(baylorTeam);
        baylorTeamID = bonusTeam.getId();
        contest = contestService.readById(contestID);
    }

    private void buildResource() {
        resource = new Resource();
        resource.setContest(contest);
        resource.setName(UUID.randomUUID().toString());
        resource.setAddress("9.9.9.9");
        resource.setPort(53);
        resource.setType(Resource.DNS);
        resource.setScoring(true);
        Parameter testQueryParameter = new Parameter();
        testQueryParameter.setTag(DNSParameterizer.DNS_QUERY);
        testQueryParameter.setValue("baylor.edu");
        testQueryParameter = parameterService.create(testQueryParameter);
        List<Parameter> parameters = new ArrayList<>();
        parameters.add(testQueryParameter);
        Parameter testExpectedParameter = new Parameter();
        testExpectedParameter.setTag(DNSParameterizer.DNS_EXPECTED);
        testExpectedParameter.setValue("129.62.3.230");
        testExpectedParameter = parameterService.create(testExpectedParameter);
        parameters.add(testExpectedParameter);
        resource.setParameters(parameters);
        resource = resourceService.create(resource);
        for (Parameter p : parameters) {
            p.setResource(resource);
            parameterService.update(p);
        }
        resourceID = resource.getId();
        contest = contestService.readById(contestID);
    }

    @Test
    public void testPollWorks() {
        AbstractPoller poller = PollerFactory.getPoller(resource);
        poll = poller.poll();
        Assert.assertTrue(poll.getInformation().contains("129.62.3.230"));
        Assert.assertTrue(poll.getTeam().equals(baylorTeam));
    }
}
