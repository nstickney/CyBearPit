# http://www.hostettler.net/blog/2016/01/30/integration-tests-with-docker-and-arquillian/

FROM jboss/wildfly:latest

ADD ./config_wildfly.sh /opt/jboss/wildfly/
ADD ./mysql-connector-java-5.1.45-bin.jar /opt/jboss/wildfly/

RUN ["/opt/jboss/wildfly/config_wildfly.sh"]
RUN rm -rf  /opt/jboss/wildfly/standalone/configuration/standalone_xml_history
RUN sed -i '53s/.*/   JAVA_OPTS="-Xms128m -Xmx1024m -XX:MetaspaceSize=192M -XX:MaxMetaspaceSize=512m -Djava.net.preferIPv4Stack=true"/' /opt/jboss/wildfly/bin/standalone.conf

ADD ./ROOT.war /opt/jboss/wildfly/standalone/deployments/
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "--debug", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
