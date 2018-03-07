#!/bin/bash

# If the WildFly-Arquillian docker container isn't running, start it
if ! docker ps | grep -q arqtest; then
	printf '%s\n' " => Starting WildFly-Arquillian container from"
	NOWDIR=$(pwd)
	cd ../docker/wildfly/arqtest/ || exit
	printf '%s\n' "        $(pwd)"
	./runServer.sh
	cd "$NOWDIR" || exit
fi

mvn clean test -Parq-wildfly-remote
