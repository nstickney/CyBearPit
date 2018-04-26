# BeanPoll

[![MIT License](https://img.shields.io/badge/license-MIT-blue.svg)](https://opensource.org/licenses/MIT "MIT License")
[![Maintainability](https://api.codeclimate.com/v1/badges/c84e1942412adcf43a1e/maintainability)](https://codeclimate.com/github/nstickney/CyBearPit/maintainability)
[![standard-readme compliant](https://img.shields.io/badge/readme%20style-standard-brightgreen.svg)](https://github.com/RichardLitt/standard-readme "RichardLitt/standard-readme")

> A polling engine for network security contests, in Java EE

BeanPoll is a contest management system and polling engine developed as part of the [CyBearPit](https://github./com/nstickney/CyBearPit "nstickney/CyBearPit") project. It is a Java EE web application designed to run on the [Wild**Fly**](http://wildfly.org "WildFly") application server (currently building against version 12, "High Noon").  

## Table of Contents

- [Security](#security)
- [Background](#background)
- [Install](#install)
- [Usage](#usage)
- [API](#api)
- [Contribute](#contribute)
- [License](#license)

## Security

As with any web application, security must be considered from the perspectives of both the application itself and the  server(s) it runs on. As BeanPoll is meant for Wild**Fly** and requires a database backend, it is imperative that Wild**Fly** be properly secured, along with its database connection.
* Disable the Wild**Fly** management interface by blocking off ports 9990 and 9993
* Secure connections from clients to Wild**Fly** using TLS
* Secure the connection from Wild**Fly** to the database using TLS
* Secure the database server and host system

The Docker image [beanpoll-wildfly](../containers/wildfly/prod "beanpoll-wildfly") is set up to only expose port 8443, on the host port 443. This corresponds to `https`-only access to the application listener only. It is also set up to use TLSv1.2 or higher with a (default, dynamically-generated) self-signed certificate. The Docker image [beanpoll-mysql](../containers/mysql "beanpoll-mysql") is not yet set up to secure connections over TLS; it currently runs the default [mysql/mysql-server](https://hub.docker.com/r/mysql/mysql-server/ "Docker: mysql/mysql-server") image, using Docker environment variables to set up a database, non-root user, and non-root password.

Since the entire purpose of BeanPoll is to be used in network security competitions, it is imperative that every security precaution available be taken short of disconnection. At the very least, change the passwords! The default passwords for the BeanPoll administrative user, "beanpoll", is set from `CyBearPit/containers/mysql/dump.sql`; you can see the password in `CyBearPit/beanpoll/src/main/resources/import.sql`, though that file is only used for development. The easiest way to change the default password is build the web application archive, deploy (to a server which is not open to the internet), and start the application. Log in as `beanpoll` and change both the username and password from the account page. Changing the MySQL passwords is done by changing them in `CyBearPit/containers/mysql/runServer.sh` and `CyBearPit/containers/wildfly/prod/config_wildfly.sh`. It must be set to the same value in both places for the application to work.

## Background

BeanPoll is the largest portion of the CyBearPit project, and builds most directly on other scoring engine projects like [Judge](https://github.com/cobbr/judge "cobbr/judge"), [scoring-engine](https://github.com/pwnbus/scoring_engine "pwnbus/scoring_engine"), and [Scoring-Engine](https://github.com/reedwilkins/Scoring-Engine "reedwilkins/Scoring-Engine"). It sits at the center of a network security competition and manages multiple contests, polling resources for each and providing a platform for task/inject dissemination, response submission, and grading.

## Install

### Quick Docker Installation Guide
**Note that you must have Docker (and Maven) installed and running to use this option!**

1) Read this entire section, to make sure that you know what is required and have it ready.

1) Clone the CyBearPit repository: `git clone https://github.com/nstickney/CyBearPit`

1) Change directory into the BeanPoll directory: `cd CyBearPit/beanpoll`

1) Run the Maven build command: `mvn clean install`

1) Change directory into the MySQL container directory: `cd ../containers/mysql`

1) Run the MySQL container: `./runServer.sh load`

    Note that you should probably change the `MYSQL_PASSWORD` line in `runServer.sh` before starting it. Otherwise, you will have the password, "beanpollmysqlpassword", which is written in plain text in this repository! If you change this password here, you must also change it in `CyBearPit/containers/wildfly/prod/config_wildfly.sh` so that the application server will have database access.

    The `load` parameter causes the database to load the dump.sql file into the database after it is created. The script waits ten seconds for MySQL to start up, then connects, at which point you will need to enter that password you set (or "beanpollmysqlpassword" if you didn't set it). If you get the password wrong, just run the entire command again; it will stop the old Docker container, remove its generated image, and start again.

1) Change directory into the Wild**Fly** production container directory: `cd ../wildfly/prod/`

1) Run the production server: `./runServer.sh build`

1) Open a browser and head to [https://localhost](https://localhost "localhost") to view your BeanPoll server! You can log in as `beanpoll` with password `beanpollpassword`. I recommend changing this password immediately! To do so, click the user icon (or the word `beanpoll`) in the navigation bar menu (top of the page, far right). Enter your current and new passwords on the screen, and select the "Change Password" button.

1) To shut down the system, turn off the Wild**Fly** container (`docker stop beanpoll-wildfly`) and the MySQL container (`docker stop beanpoll-mysql`).

1) Now that you have read the instructions, there is a script called `runBeanPoll.sh` in the [CyBearPit](https://github.com/nstickney/CyBearPit "nstickney/CyBearPit") directory that will do everything but open a browser for you. Find out how it works by running `./runBeanPoll.sh help`.

### Manual Installation Guide

Running `mvn clean install` in this folder will build the web application archive, which is then saved to `CyBearPit/beanpoll/target/ROOT.war`. Deploy the application on a Wild**Fly** server with a preconfigured datasource called `java:/beanpollDS`. BeanPoll is developed using MySQL, but contains no MySQL-specific code and handles all database connections through JPA/Hibernate, so it *shouldn't* matter. Your mileage may vary.

Installing MySQL or another DBMS is out of the scope of this README; you can find the MySQL documentation [here](https://dev.mysql.com/doc/ "MySQL Documentation"), and it's generally a good idea to consult your operating system's documentation as well. Wild**Fly** installation is a similar matter; you can find the Wild**Fly** documentation [here](http://docs.wildfly.org/ "WildFly Documentation").

If you have a running MySQL server and a running Wild**Fly** instance, you will need to create a datasource on Wild**Fly** which points to your MySQL database. You can download the MySQL JDBC driver [here](https://dev.mysql.com/downloads/connector/j/ "MySQL Connector/J"). You will need to either deploy it manually through the Wild**Fly** web interface (ensure you unzip the file first), then create a Non-XA datasource through the same interface, or adapt the commands found in the `CyBearPit/containers/wildfly/prod/config_wildfly.sh`, which are listed below.

```
module add --name=com.mysql --resources=/opt/jboss/wildfly/mysql-connector-java-5.1.45-bin.jar --dependencies=javax.api,javax.transaction.api
/subsystem=datasources/jdbc-driver=mysql:add(driver-name=mysql,driver-module-name=com.mysql)
data-source add --name=$beanpoll_DB --driver-name=mysql --jndi-name=$beanpoll_DS --connection-url=jdbc:mysql://$DB_HOST:$DB_PORT/$beanpoll_DB --user-name=$DB_USER --password=$DB_PWD
```

Once you have tested your JDBC connection to beanpollDS, you can deploy `ROOT.war` by adding it to the `$JBOSS_HOME/wildfly/standalone/deployments/` directory of your Wild**Fly** installation, or deploy it through the web interface.

### Building Tests

From the `CyBearPit/beanpoll` directory, you can use `mvn spotbugs:spotbugs` to check the code for errors, and `./runTests.sh` will build and run the Arquillian/JUnit tests in a purpose-built Docker container with an H2 datasource included. You can pass the class name of the test you want to run (e.g. `./runTests.sh SMTPUtilityTest`). It is preferred to run the tests this way, since the test server will not currently allow enough deployments for all the tests to run together.

Please note that EmailUtilityTest and the email poller tests require access to an email address... not giving out the password. Sorry. You'll need to set them up with an email account you have access to — and remember not to commit the password!

## Usage

All functionality of BeanPoll is available from the web interface. Once the application is deployed, open a browser and visit the server IP address or hostname (preferably over `https`) and log in. The default administrative user is `beanpoll`, and the default password is `beanpollpassword`.

![BeanPoll Interface](BeanPollInterface.png "BeanPoll Interface Example")

The screenshot above shows the BeanPoll user interface for administrative and judge users. The top navigation bar contains a link to the scoreboard page (`index.xhtml`), marked "BeanPoll", in the upper left. In the upper right are three links: "Controls", "Judging", and "beanpoll". "beanpoll" is the currently logged in user (both an administrator and a judge). Clicking that link takes the user to their account page. The "Judging" link is only available to judge users; it takes the user to a page (`grading.xhtml`) where they can view and enter scores and comments for task responses and incident reports. The "Controls" drop-down menu is only visible to administrative users; it contains options to manage Contests, Announcements, Capturables, Resources, Tasks, Teams, and Users.

On each of the admin pages just listed, there is an option, highlighted in light blue, to create a new instance. Below the creation panel is a color key, and below the color key a list of existing instances which can be updated or deleted; in the case of Contests, this is where an administrative user can schedule, start, and stop them.

### Poller Parameters

Each ResourceType corresponds to a type of server that will be scored. The system automatically generates a set of all possible parameters for a service when a Resource is created, replaces them if the ResourceType is changed, and deletes them if the Resource is deleted. You can investigate the way Parameters are used by reading through the corresponding Poller implementations; they are also listed below.

#### DNS Resource

A DNS Resource is a Domain Name Server. The Poller does a lookup for the specified query (`DNS_QUERY`) against the specified server (`addresss`), for the specified record type (`DNS_RECORD_TYPE`) . If the response contains the expected value (`DNS_EXPECTED`), then the check passes. If exactly one Team is assigned to the Resource, the Poll is awarded to that Team. Otherwise, a second DNS request is made to the same server, for the same query, for `TXT` records, which are then matched to Team flags (either all Teams assigned to the Resource, or all Teams in Contest if none is assigned). If exactly one Team flag matches, that Team is awarded the Poll. Otherwise, the Poll scores zero points and is not awarded to any Team.

| Tag | Use | Default |
|:---:|:---:|:---:|
| DNS_QUERY | What host to ask the server to resolve | `baylor.edu` |
| DNS_RECORD_TYPE | Type of record to ask for | `A` |
| DNS_TCP | Whether to use TCP | `false` |
| DNS_RECURSIVE | Whether to request recursion | `false` |
| DNS_EXPECTED | Correct response for DNS_QUERY | `129.62.3.230` |

#### HTTP Resource

An HTTP Resource is an HTTP or HTTPS server. If a DNS server is specified (`HTTP_RESOLVER`), it is used to resolve the given server (`address`), otherwise the system's DNS resolution defaults are used. The Poller then does a request to the specified resolver on its resolved address. If the response contains the expected string (`HTTP_EXPECTED`), then the check passes. If exactly one Team is assigned to the Resource, that Team is awarded the Poll. Otherwise, the response is searched for all Team flags (either all Teams assigned to the Resource, or all Teams in Contest if none is assigned). If exactly one Team flag is found in the Response, that Team is awarded the Poll. Otherwise, the Poll scores zero points and is not awarded to any Team.

| Tag | Use | Default |
|:---:|:---:|:---:|
| HTTP_RESOLVER | (Optional) DNS server to resolve the HTTP server address | `null` |
| HTTP_EXPECTED | String expected to be found in the page | `Baylor` |

#### IMAP/POP Resource

**Note that IMAP and POP Resources MUST be assigned to exactly one Team!**

An IMAP or POP Resource is an IMAP or POP email server, respectively. If a DNS server is specified (`EMAIL_RESOLVER`), it is used to resolve the given server (`address`), otherwise the system's DNS resolution defaults are used. The Poller then attempts to connect to that server and download all email. If the connection and download are successful, the check passes, and the Poll scores the point value of the Resource. The Poll is always awarded to the assigned Team; if the Resource is not assigned exactly one Team, the check is not performed, the Poll scores zero points, and it is not awarded to any Team.

| Tag | Use | Default |
|:---:|:---:|:---:|
| EMAIL_RESOLVER | (Optional) DNS server to resolve the EMAIL server address | `null` |
| EMAIL_USERNAME | Username to log in and check mail with | `null` |
| EMAIL_PASSWORD | Password for EMAIL_USERNAME | `null` |
| EMAIL_SSL | Use SSL/TLS to make the EMAIL connection | `false` |
| EMAIL_TLS | Use StartTLS before authenticating | `false` |

#### SMTP Resource

**Note that SMTP, SMTP_IMAP, and SMTP_POP Resources MUST be assigned to exactly one Team!**

An SMTP Resource is an SMTP email server, respectively. If a DNS server is specified (`EMAIL_RESOLVER`), it is used to resolve the given server (`address`), otherwise the system's DNS resolution defaults are used. The Poller then attempts to connect to that server and send an email with a randomly-generated subject and message. If the connection and sending are successful, the check passes, and the Poll scores the point value of the Resource. The Poll is always awarded to the assigned Team; if the Resource is not assigned exactly one Team, the check is not performed, the Poll scores zero points, and it is not awarded to any Team.

If the Resource is an SMTP_IMAP or SMTP_POP Resource, the Poll is not scored until the email is retrieved at the corresponding service. You must ensure that the `EMAIL_USERNAME` and `SMTP_RECIPIENT` Parameters match, so that the mailbox which is supposed to receive the email does. The receive check works the same way as a usual IMAP or POP Resource check, except that the messages are examined to see if one of them matches (by subject) the email that was sent during the SMTP check. If so, the check passes. There is a 30-second delay between the sending check and the receiving check.

| Tag | Use | Default |
|:---:|:---:|:---:|
| SMTP_RESOLVER | (Optional) DNS server to resolve the SMTP server address | `null` |
| SMTP_USERNAME | Username to log in and send mail with | `null` |
| SMTP_PASSWORD | Password for SMTP_USERNAME | `null` |
| SMTP_RECIPIENT | Who to send the message to | `null` |
| SMTP_SSL | Use SSL/TLS to make the SMTP connection | `false` |
| SMTP_TLS | Use StartTLS before authenticating | `false` |
| SMTP_TEST_AUTH | Whether the poller should check to ensure the server isn't just accepting any incoming mail, without authentication | `true` |

Note that SMTP_IMAP and SMTP_POP Resources will simply use a combination of EMAIL and SMTP Parameters, along with the SMTPCHECK Parameters.

| Tag | Use | Default |
|:---:|:---:|:---:|
| SMTPCHECK_HOST | (Optional) Hostname of server to check mail on | `null` |

## API

BeanPoll is planned to have a RESTful API in the future, but it is not yet implemented.

## Contribute

> This project subscribes to the [Contributor Covenant](CODE_OF_CONDUCT.md "Code of Conduct").

I welcome [issues](../docs/issue_template.md "Issue template"), but I prefer [pull requests](../docs/pull_request_template.md "Pull request template")! See the [contribution guidelines](../docs/contributing.md "Contributing") for more information.

## License

[MIT ©2018 Nathaniel Stickney](LICENSE)
