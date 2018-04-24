#!/bin/bash

# No command specified, so print a usage message
if [ "$1" == "help" ]; then
	printf '%s\n' "  usage: ./runBeanPoll.sh [<command>]"\
		"    Optional <command> is one of:"\
		"      build: rebuilds and re-deploys the BeanPoll applicaton"\
		"      load: destroys and reloads the database"\
		"      allnew: both build and load"\
		"      help: display this message"\
		"    WARNING: \"load\" and \"allnew\" will destroy your database!"
	exit
fi

# Ensure the required tools are available early
if [ ! -x "$(command -v docker)" ]; then
	printf '%s\n' " => ERROR: Running BeanPoll requires Docker (docker)"
	exit
fi

# Where are we?
NOWDIR=$(pwd)

# A new build is requested
if [ "$1" == "build" ] || [ "$1" == "allnew" ]; then

	# Do we have what we need?
	if [ ! -x "$(command -v mvn)" ]; then
		printf '%s\n' " => ERROR: Building BeanPoll requires Maven (mvn)"
		exit
	fi
	printf '%s\n' " => Building the BeanPoll application"
	
	# Build the application using maven
	cd "$NOWDIR"/beanpoll/ || exit
	mvn clean install
fi

# Start the MySQL server
cd "$NOWDIR"/containers/mysql/ || exit
if [ "$1" == "load" ] || [ "$1" == "allnew" ]; then

	# A database reload was requested
	./runServer.sh load
else
	./runServer.sh
fi

# Start the WildFly server
cd "$NOWDIR"/containers/wildfly/prod/ || exit
if [ "$1" == "build" ] || [ "$1" == "allnew" ]; then

	# We need to rebuild the WildFly deployment
	./runServer.sh build
else
	./runServer.sh
fi
