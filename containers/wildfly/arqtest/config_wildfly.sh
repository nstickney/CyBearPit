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
export JUDGEBEAN_DB="JudgeBeanDS"
export JUDGEBEAN_DS="java:/$JUDGEBEAN_DB"
export H2_URI="jdbc:h2:mem:JUDGEBEAN_DB;DB_CLOSE_DELAY=-1"
export H2_USER="judgebeanwildfly"
export H2_PWD="judgebeanwildflypassword"

$JBOSS_CLI -c << EOF
batch

echo "Connection URL: " $CONNECTION_URL

# First step : Add the datasource
data-source add --name=$JUDGEBEAN_DB --driver-name=h2 --jndi-name=$JUDGEBEAN_DS --connection-url=$H2_URI --user-name=$H2_USER --password=$H2_PWD --use-ccm=false --max-pool-size=25 --blocking-timeout-wait-millis=5000 

# Execute the batch
run-batch
EOF

# Finally, let's add an admin that can be used by the IDE to deploy the tests
/opt/jboss/wildfly/bin/add-user.sh judgebeanwildfly judgebeanwildflypassword

echo "=> Shutting down wildfly"
if [ "$JBOSS_MODE" = "standalone" ]; then
  $JBOSS_CLI -c ":shutdown"
else
  $JBOSS_CLI -c "/host=*:shutdown"
fi

