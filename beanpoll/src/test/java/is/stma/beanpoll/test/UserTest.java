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

import is.stma.beanpoll.data.UserRepo;
import is.stma.beanpoll.model.User;
import is.stma.beanpoll.rules.UserRules;
import is.stma.beanpoll.service.UserService;
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
public class UserTest {
    @Inject
    private UserService Userservice;

    private User testUser;
    private User checkUser;

    /**
     * Create a web archive (WAR) for deployment via Arquillian
     *
     * @return the web archive
     */
    @Deployment
    public static WebArchive createDeployment() {
        File[] files = Maven.resolver().loadPomFromFile("pom.xml")
                .importRuntimeDependencies().resolve().withTransitivity().asFile();

        return ShrinkWrap.create(WebArchive.class, "UserTest.war")
                .addPackages(true, User.class.getPackage(),
                        UserRepo.class.getPackage(),
                        UserService.class.getPackage(),
                        UserRules.class.getPackage(),
                        EMProducer.class.getPackage())
                .addClass(TestUtility.class)
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsResource("META-INF/apache-deltaspike.properties")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource("test-ds.xml") // Deploy test datasource
                .addAsLibraries(files); // Add necessary stuff from pom.xml
    }

    /**
     * Create a test contest and a test User in that contest, and persist both
     */
    @Before
    public void setUp() {
        if (null == testUser) {
            testUser = TestUtility.makeUser();
            Userservice.create(testUser);
        }
    }

    @Test
    public void testUserCreation() {
        checkUser = Userservice.readById(testUser.getId());
        Assert.assertTrue(testUser.equalByUUID(checkUser));
        Assert.assertEquals(testUser, checkUser);
    }

    @Test
    public void testUserUpdate() {
        testUser.setName("UPDATED");
        Userservice.update(testUser);
        String UUID = testUser.getId();
        testUser = null;
        testUser = Userservice.readById(UUID);
        Assert.assertEquals("UPDATED", testUser.getName());
    }

    @Test
    public void testUserDeletion() {
        Userservice.delete(testUser);
        String UUID = testUser.getId();
        testUser = null;
        testUser = Userservice.readById(UUID);
        Assert.assertNull(testUser);
    }

    @Test(expected = EJBException.class)
    //@Test(expected = ValidationException.class)
    public void testUserUpdateNonexistent() {
        checkUser = TestUtility.makeUser();
        checkUser.setName("Check User");
        Userservice.update(checkUser);
    }

    @Test(expected = EJBException.class)
    //@Test(expected = ValidationException.class)
    public void testUserNonUniqueUUID() {
        checkUser = testUser;
        checkUser.setName("We Copied You!");
        Userservice.create(checkUser);
    }
}