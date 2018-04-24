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

import is.stma.beanpoll.data.ParameterRepo;
import is.stma.beanpoll.model.Contest;
import is.stma.beanpoll.model.Parameter;
import is.stma.beanpoll.model.Resource;
import is.stma.beanpoll.model.ResourceType;
import is.stma.beanpoll.rules.ParameterRules;
import is.stma.beanpoll.service.ContestService;
import is.stma.beanpoll.service.ParameterService;
import is.stma.beanpoll.service.ResourceService;
import is.stma.beanpoll.util.EMProducer;
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

@RunWith(Arquillian.class)
public class ParameterTest {

    @Inject
    private ContestService contestService;

    @Inject
    private ParameterService parameterService;

    @Inject
    private ResourceService resourceService;

    private Contest testContest;

    private Parameter testParameter;
    private Parameter checkParameter;

    private Resource testResource;

    /**
     * Create a web archive (WAR) for deployment via Arquillian
     *
     * @return the web archive
     */
    @Deployment
    public static WebArchive createDeployment() {
        File[] files = Maven.resolver().loadPomFromFile("pom.xml")
                .importRuntimeDependencies().resolve().withTransitivity().asFile();

        return ShrinkWrap.create(WebArchive.class, "ParameterRulesTest.war")
                .addPackages(true, Parameter.class.getPackage(),
                        ParameterRepo.class.getPackage(),
                        ParameterService.class.getPackage(),
                        ParameterRules.class.getPackage(),
                        EMProducer.class.getPackage())
                .addClass(TestUtility.class)
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsResource("META-INF/apache-deltaspike.properties")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource("test-ds.xml") // Deploy test datasource
                .addAsLibraries(files); // Add necessary stuff from pom.xml
    }

    /**
     * Create a test contest and a test Parameter in that contest, and persist both
     */
    @Before
    public void setUp() {
        if (null == testContest) {
            testContest = TestUtility.makeContest();
            contestService.create(testContest);
        }
        if (null == testResource) {
            testResource = TestUtility.makeResource(testContest, ResourceType.DNS);
            resourceService.create(testResource);
        }
        if (null == testParameter) {
            testParameter = TestUtility.makeParameter(testResource);
            parameterService.create(testParameter);
        }
    }

    @Test
    public void testParameterCreation() {
        checkParameter = parameterService.readById(testParameter.getId());
        Assert.assertTrue(testParameter.equalByUUID(checkParameter));
        Assert.assertEquals(testParameter, checkParameter);
    }

    @Test
    public void testParameterUpdate() {
        testParameter.setValue("UPDATED");
        parameterService.update(testParameter);
        String UUID = testParameter.getId();
        testParameter = null;
        testParameter = parameterService.readById(UUID);
        Assert.assertEquals("UPDATED", testParameter.getValue());
    }

    @Test
    public void testParameterDeletion() {
        parameterService.delete(testParameter);
        String UUID = testParameter.getId();
        testParameter = null;
        testParameter = parameterService.readById(UUID);
        Assert.assertNull(testParameter);
    }

    @Test(expected = EJBException.class)
    //@Test(expected = ValidationException.class)
    public void testParameterUpdateNonexistent() {
        checkParameter = TestUtility.makeParameter(testResource);
        checkParameter.setValue("Check Parameter");
        parameterService.update(checkParameter);
    }

    @Test(expected = EJBException.class)
    //@Test(expected = ValidationException.class)
    public void testParameterNonUniqueUUID() {
        checkParameter = testParameter;
        checkParameter.setValue("We Copied You!");
        parameterService.create(checkParameter);
    }
}