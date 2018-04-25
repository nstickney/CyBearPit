# http://www.hostettler.net/blog/2016/01/30/integration-tests-with-docker-and-arquillian/

FROM jboss/wildfly:latest

ADD ./config_wildfly.sh /opt/jboss/wildfly/
ADD ./mysql-connector-java-5.1.45-bin.jar /opt/jboss/wildfly/

RUN ["/opt/jboss/wildfly/config_wildfly.sh"]
RUN rm -rf  /opt/jboss/wildfly/standalone/configuration/standalone_xml_history

ADD ./ROOT.war /opt/jboss/wildfly/standalone/deployments/
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "--debug", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
