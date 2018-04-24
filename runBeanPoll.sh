#!/bin/bash

printf '%s\n' " => Building and running BeanPoll"

# Do we have what we need?
if [ ! -x "$(command -v mvn)" ]; then
	printf '%s\n' " => ERROR: Building BeanPoll requires Maven (mvn)"
	exit
fi
if [ ! -x "$(command -v docker)" ]; then
	printf '%s\n' " => ERROR: Running BeanPoll requires Docker (docker)"
	exit
fi

# Where are we?
NOWDIR=$(pwd)

# Build the application using maven
cd "$NOWDIR"/beanpoll/ || exit
mvn clean install

# Start the MySQL server
cd "$NOWDIR"/containers/mysql/ || exit
./runServer.sh load

# Start the WildFly server
cd "$NOWDIR"/containers/wildfly/prod/ || exit
./runServer.sh build
