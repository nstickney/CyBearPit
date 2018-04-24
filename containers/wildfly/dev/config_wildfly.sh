#!/bin/bash

# http://www.hostettler.net/blog/2016/01/30/integration-tests-with-docker-and-arquillian/

# Usage: execute.sh [wildfly mode] [configuration file]
#
# The default mode is 'standalone' and default configuration is based on the
# mode. It can be 'standalone.xml' or 'domain.xml'.

JBOSS_HOME=/opt/jboss/wildfly
JBOSS_CLI=$JBOSS_HOME/bin/jboss-cli.sh
JBOSS_MODE=${1:-"standalone"}
JBOSS_CONFIG=${2:-"$JBOSS_MODE.xml"}

function wait_for_server() {
  until $JBOSS_CLI -c ":read-attribute(name=server-state)" 2> /dev/null | grep -q running; do
    sleep 1
  done
}

echo "=> Starting wildfly server"
"$JBOSS_HOME"/bin/"$JBOSS_MODE".sh -b 0.0.0.0 -c "$JBOSS_CONFIG" &

echo "=> Waiting for the server to boot"
wait_for_server

echo "=> Executing the commands"
export beanpoll_DB="beanpollDS"
export beanpoll_DS="java:/$beanpoll_DB"
export DB_HOST="172.17.0.1"
export DB_PORT="3306"
export DB_USER="beanpollmysql"
export DB_PWD="beanpollmysqlpassword"

$JBOSS_CLI -c << EOF
batch

echo "Connection URL: " $CONNECTION_URL

echo "  => Adding MySQL module"
module add --name=com.mysql --resources=/opt/jboss/wildfly/mysql-connector-java-5.1.45-bin.jar --dependencies=javax.api,javax.transaction.api

echo "  => Configuring MySQL driver"
/subsystem=datasources/jdbc-driver=mysql:add(driver-name=mysql,driver-module-name=com.mysql)

echo "  => Creating MySQL datasource " $beanpoll_DS
data-source add --name=$beanpoll_DB --driver-name=mysql --jndi-name=$beanpoll_DS --connection-url=jdbc:mysql://$DB_HOST:$DB_PORT/$beanpoll_DB --user-name=$DB_USER --password=$DB_PWD

run-batch
EOF

echo "=> Adding a user"
/opt/jboss/wildfly/bin/add-user.sh beanpollwildfly beanpollwildflypassword

echo "=> Shutting down wildfly"
if [ "$JBOSS_MODE" = "standalone" ]; then
  $JBOSS_CLI -c ":shutdown"
else
  $JBOSS_CLI -c "/host=*:shutdown"
fi

