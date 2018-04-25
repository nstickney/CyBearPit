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

import is.stma.beanpoll.util.EMProducer;
import is.stma.beanpoll.util.EmailUtility;
import is.stma.beanpoll.util.LogProducer;
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

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.validation.ValidationException;
import javax.validation.constraints.AssertTrue;
import java.io.File;
import java.util.UUID;

@RunWith(Arquillian.class)
public class EmailUtilityTest {

    private static final String DEFAULT_USERNAME = "beanpoll@yandex.com";
    private static final String DEFAULT_PASSWORD = "";
    private static final int DEFAULT_TIMEOUT = 3;

    // IMAP3
    private static final String IMAP_HOST = "imap.yandex.com";
    private static final int IMAP_PORT = 993;
    private static final boolean IMAP_SSL = true;
    private static final boolean IMAP_TLS = false;
    private static final String IMAP_LOGIN_ERROR = "AUTHENTICATE Invalid credentials or IMAP is disabled";

    // POP3
    private static final String POP_HOST = "pop.yandex.com";
    private static final int POP_PORT = 995;
    private static final boolean POP_SSL = true;
    private static final boolean POP_TLS = false;
    private static final String POP_LOGIN_ERROR = "[AUTH] login failure or POP3 disabled";

    // SMTP
    private static final String SMTP_HOST = "smtp.yandex.com";
    private static final String SMTP_RECIPIENT = "beanpoll@yandex.com";
    private static final int SMTP_PORT = 465;
    private static final boolean SMTP_SSL = true;
    private static final boolean SMTP_TLS = false;
    private static final String SMTP_LOGIN_ERROR = "Invalid user or password";

    private String username;
    private String password;
    private String host;
    private String recipient;
    private String subject;
    private String msg;
    private int port;
    private boolean tls;
    private boolean ssl;
    private int timeout;

    @Deployment
    public static Archive<?> createTestArchive() {

        File[] files = Maven.resolver().loadPomFromFile("pom.xml")
                .importRuntimeDependencies().resolve().withTransitivity().asFile();

        return ShrinkWrap.create(WebArchive.class, "testDNSUtility.war")
                .addClasses(EmailUtility.class, EMProducer.class, LogProducer.class)
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource("test-ds.xml") // Deploy test datasource
                .addAsLibraries(files); // Add necessary stuff from pom.xml
    }

    @Before
    public void setUp() {
        username = DEFAULT_USERNAME;
        password = DEFAULT_PASSWORD;
        timeout = DEFAULT_TIMEOUT;
    }

    private void setUpIMAP() {
        host = IMAP_HOST;
        port = IMAP_PORT;
        tls = IMAP_TLS;
        ssl = IMAP_SSL;
    }

    private void setUpPOP() {
        host = POP_HOST;
        port = POP_PORT;
        tls = POP_TLS;
        ssl = POP_SSL;
    }

    private void setUpSMTP() {
        host = SMTP_HOST;
        port = SMTP_PORT;
        tls = SMTP_TLS;
        ssl = SMTP_SSL;
        recipient = SMTP_RECIPIENT;
        subject = UUID.randomUUID().toString();
        msg = subject;
    }

    @Test
    public void testIMAPRetrieval() {
        setUpIMAP();
        try {
            Message[] messages = EmailUtility.getEmail(username, password, host, "imap", port, tls, ssl, timeout);
            Assert.assertTrue(messages.length > 0);
        } catch (MessagingException e) {
            throw new ValidationException(e.getMessage());
        }
    }

    @Test
    public void testIMAPBadUsername() {
        setUpIMAP();
        try {
            Message[] messages = EmailUtility.getEmail(password, password, host, "imap", port, tls, ssl, timeout);
            Assert.assertTrue(messages.length > 0);
        } catch (MessagingException e) {
            Assert.assertTrue(e.getMessage().contains(IMAP_LOGIN_ERROR));
        }
    }

    @Test
    public void testIMAPBadPassword() {
        setUpIMAP();
        try {
            Message[] messages = EmailUtility.getEmail(username, username, host, "imap", port, tls, ssl, timeout);
            Assert.assertTrue(messages.length > 0);
        } catch (MessagingException e) {
            Assert.assertTrue(e.getMessage().contains(IMAP_LOGIN_ERROR));
        }
    }

    @Test
    public void testPOPRetrieval() {
        setUpPOP();
        try {
            Message[] messages = EmailUtility.getEmail(username, password, host, "pop3", port, tls, ssl, timeout);
            Assert.assertTrue(messages.length > 0);
        } catch (MessagingException e) {
            throw new ValidationException(e.getMessage());
        }
    }

    @Test
    public void testPOPBadUsername() {
        setUpPOP();
        try {
            Message[] messages = EmailUtility.getEmail(password, password, host, "pop3", port, tls, ssl, timeout);
        } catch (MessagingException e) {
            Assert.assertTrue(e.getMessage().contains(POP_LOGIN_ERROR));
        }
    }

    @Test
    public void testPOPBadPassword() {
        setUpPOP();
        try {
            Message[] messages = EmailUtility.getEmail(username, username, host, "pop3", port, tls, ssl, timeout);
        } catch (MessagingException e) {
            Assert.assertTrue(e.getMessage().contains(POP_LOGIN_ERROR));
        }
    }

    @Test
    public void testSMTPRequest() {
        setUpSMTP();
        try {
            EmailUtility.sendSMTPMessage(username, password, host, recipient, subject, msg, port,
                    tls, ssl, timeout);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSMTPBadUsername() {
        setUpSMTP();
        try {
            EmailUtility.sendSMTPMessage(UUID.randomUUID().toString(), password, host, recipient,
                    subject, msg, port, tls, ssl, timeout);
            Assert.fail();
        } catch (MessagingException e) {
            Assert.assertTrue(e.getMessage().contains(SMTP_LOGIN_ERROR));
        }
    }

    @Test
    public void testSMTPBadPassword() {
        setUpSMTP();
        try {
            EmailUtility.sendSMTPMessage(username, username, host, recipient, subject, msg, port,
                    tls, ssl, timeout);
            Assert.fail();
        } catch (MessagingException e) {
            Assert.assertTrue(e.getMessage().contains(SMTP_LOGIN_ERROR));
        }
    }
}
