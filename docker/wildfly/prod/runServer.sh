#!/bin/bash

# Get the MySQL connector if it doesn't exist already
if [ ! -e mysql-connector-java-5.1.45-bin.jar ]; then
	if [ ! -e mysql-connector-java-5.1.45.tar.gz ]; then
		echo "Downloading the MySQL Connector for Java... "
		curl -L https://dev.mysql.com/get/Downloads/Connector-J/mysql-connector-java-5.1.45.tar.gz -o mysql-connector-java-5.1.45.tar.gz
	fi
	echo "Expanding the MySQL Connector for Java... "
	tar xf mysql-connector-java-5.1.45.tar.gz
	mv mysql-connector-java-5.1.45/*.jar .
	rm -rf mysql-connector-java-5.1.45
fi

# Create the image if it doesn't exist already
if [[ "$(docker images -q judgebean-wildfly 2> /dev/null)" == "" ]]; then
    docker build --no-cache --force-rm -t judgebean-wildfly .    
fi

# Run the container, forwarding ports 80 (to 8080) and 81 (to 9990)
docker run --name=judgebean-wildfly \
	-p 80:8080 \
	-p 81:9990 \
	-d judgebean-wildfly
