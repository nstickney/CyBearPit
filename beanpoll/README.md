# BeanPoll

[![MIT License](https://img.shields.io/badge/license-MIT-blue.svg)](https://opensource.org/licenses/MIT "MIT License") [![standard-readme compliant](https://img.shields.io/badge/readme%20style-standard-brightgreen.svg)](https://github.com/RichardLitt/standard-readme "RichardLitt/standard-readme")

> A polling engine for network security contests, in Java EE

BeanPoll is the actual polling engine inside the [JudgeBean](https://github.com/nstickneyjudgebean "nstickney/judgebean") network security contest system. It is a Java EE web application designed to run on the [Wild**Fly**](http://wildfly.org "WildFly") application server (currently building against version 12, "High Noon").  

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

The Docker image [judgebean-wildfly](../containers/wildfly/prod "judgebean-wildfly") is set up to only expose port 8443, on the host port 443. This corresponds to `https`-only access to the application listener only. It is also set up to use TLSv1.2 or higher with a (default, dynamically-generated) self-signed certificate. The Docker image [judgebean-mysql](../containers/mysql "judgebean-mysql") is not yet set up to secure connections over TLS; it currently runs the default [mysql/mysql-server](https://hub.docker.com/r/mysql/mysql-server/ "Docker: mysql/mysql-server") image, using Docker environment variables to set up a database, non-root user, and non-root password.

Since the entire purpose of JudgeBean is to be used in network security competitions, it is imperative that every security precaution available be taken short of disconnection. At the very least, change the passwords! The default passwords for the BeanPoll administrative users are set from [import.sql](src/main/resources/import.sql "import.sql") and are listed in plain text in that file. The easiest way to change them is to remove all but the first user line from the file, build the web application archive, deploy and start the application, log in as `beanpoll` (the default admin user), and change both the username and password from the account page.

## Background

BeanPoll is the largest portion of the JudgeBean project, and builds most directly on other scoring engine projects like [Judge](https://github.com/cobbr/judge "cobbr/judge"), [scoring-engine](https://github.com/pwnbus/scoring_engine "pwnbus/scoring_engine"), and [Scoring-Engine](https://github.com/reedwilkins/Scoring-Engine "reedwilkins/Scoring-Engine"). It sits at the center of a network security competition and manages multiple contests, polling resources for each and providing a platform for task/inject dissemination, response submission, and grading.

## Install

`mvn clean install` in this folder will build the web application archive, which is then saved to [ROOT.war](target/ROOT.war "target/ROOT.war"). Deploy the application on a Wild**Fly** server with a preconfigured datasource called `java:/JudgeBeanDS` (see [persistence.xml](src/main/resources/META-INF/persistence.xml "persistence.xml") for the required configuration).

You can also use `mvn spotbugs:spotbugs` to check the code for errors, and `./runTests.sh` will build and run the Arquillian/JUnit tests in a purpose-built Docker container with a built-in H2 datasource.

## Usage

All functionality of BeanPoll is available from the web interface. Once the application is deployed, open a browser and visit the server IP address or hostname (preferably over `https`) and log in. The default administrative user is `beanpoll`, and the default password is `beanpollpassword`.

## API

TBP

## Contribute

> This project subscribes to the [Contributor Covenant](CODE_OF_CONDUCT.md "Code of Conduct").

I welcome [issues](../docs/issue_template.md "Issue template"), but I prefer [pull requests](../docs/pull_request_template.md "Pull request template")! See the [contribution guidelines](../docs/contributing.md "Contributing") for more information.

## License

[MIT ©2018 Nathaniel Stickney](LICENSE)