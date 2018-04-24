#!/bin/bash

# Make a new docker container each time the tests are run
printf '%s\n' " => Starting WildFly-Arquillian container from"
NOWDIR=$(pwd)
cd ../containers/wildfly/arqtest/ || exit
printf '%s\n' "        $(pwd)"
./runServer.sh new
cd "$NOWDIR" || exit

# Run the test(s)
if [ "$1" == "" ]; then
	mvn clean test -Parq-wildfly-remote
else
	mvn clean -Dtest="$1" test -Parq-wildfly-remote
fi

# Kill the container
printf '%s\n' " => Stopping WildFly-Arquillian container"
docker stop judgebean-arqtest-wildfly
docker rm judgebean-arqtest-wildfly

