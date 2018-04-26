/*
 * Copyright 2018 Nathaniel Stickney
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package is.stma.beanpoll.util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailUtility {

    // Shared properties
    private static final String HOST = "mail.%s.host";
    private static final String PORT = "mail.%s.port";
    private static final String CONN_TIMEOUT = "mail.%s.connectiontimeout";
    private static final String READ_TIMEOUT = "mail.%s.timeout";
    private static final String WRITE_TIMEOUT = "mail.%s.writetimeout";
    private static final String SSLSFC = "mail.%s.socketFactory.class";
    private static final String SSLSSLSF = "javax.net.ssl.SSLSocketFactory";
    private static final String SSLPORT = "mail.%s.socketFactory.port";
    private static final String SSLFB = "mail.%s.socketFactory.fallback";
    private static final String START_TLS = "mail.%s.starttls.enable";

    // IMAP properties

    // POP properties

    // SMTP properties
    private static final String AUTH = "mail.smtp.auth";
    private static final String TLS = "mail.smtp.starttls.enable";

    private static Properties getServerProperties(String protocol, String host, int port, int timeout) {
        Properties properties = new Properties();
        properties.put(String.format(HOST, protocol), host);
        properties.put(String.format(PORT, protocol), Integer.toString(port));
        properties.put(String.format(CONN_TIMEOUT, protocol), Integer.toString(timeout * 1000));
        properties.put(String.format(READ_TIMEOUT, protocol), Integer.toString(timeout * 1000));
        properties.put(String.format(WRITE_TIMEOUT, protocol), Integer.toString(timeout * 1000));
        return properties;
    }

    private static Session getSession(Properties properties, String user, String password) {
        return Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });
    }

    private static Properties addSSLProperties(Properties properties, String protocol, int port) {
        properties.put(String.format(SSLSFC, protocol), SSLSSLSF);
        properties.put(String.format(SSLPORT, protocol), Integer.toString(port));
        properties.put(String.format(SSLFB, protocol), "false");
        return properties;
    }

    public static Message[] getEmail(String username, String password, String host, String protocol,
                                     int port, boolean tls, boolean ssl, int timeout)
            throws MessagingException {

        // Set the session properties from arguments
        Properties properties = getServerProperties(protocol, host, port, timeout);

        // Set up the connection
        if (tls) {
            properties.put(String.format(START_TLS, protocol), "true");
        }
        if (ssl) {
            properties = addSSLProperties(properties, protocol, port);
        }

        // Get the mailbox
        Store store = getSession(properties, username, password).getStore(protocol);
        store.connect(username, password);
        Folder folder = store.getFolder("INBOX");
        folder.open(Folder.READ_ONLY);
        return folder.getMessages();
    }

    /**
     * Send an email via SMTP. Based on https://www.mkyong.com/java/javamail-api-sending-email-via-gmail-smtp-example/.
     *
     * @param sender    the full address of the sender
     * @param password  the sender's password
     * @param host      the sender's mail server
     * @param recipient the full address of the recipient
     * @param subject   the message subject
     * @param msg       the message body
     * @param port      the sender's mail server's listening port
     * @param tls       use TLS? (cannot be used with SSLSFC)
     * @param ssl       use SSLSFC? (cannot be used with TLS)
     * @param timeout   seconds to wait before giving up
     * @throws MessagingException if the mail cannot be successfully sent
     */
    public static void sendSMTPMessage(String sender, String password, String host,
                                       String recipient, String subject, String msg,
                                       int port, boolean tls, boolean ssl, int timeout)
            throws MessagingException {

        // Protocol is smtp
        String protocol = "smtp";

        // Set the session properties from arguments
        Properties properties = getServerProperties(protocol, host, port, timeout);

        // Set the session properties from arguments
        properties.put(AUTH, "true");
        if (tls) {
            properties.put(TLS, "true");
        }
        if (ssl) {
            properties = addSSLProperties(properties, protocol, port);
        }

        // Create the session
        Session session = getSession(properties, sender, password);

        // Create the message
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(sender));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        message.setSubject(subject);
        message.setText(msg);

        // Send it!
        Transport.send(message);
    }
}
