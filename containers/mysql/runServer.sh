#!/bin/bash

IMAGE_NAME='mysql/mysql-server:5.7'
CONTAINER_NAME='judgebean-mysql'

if [ "$1" == "new" ] || [ "$1" == "load" ] ||\
	! docker ps -a | grep -q "$CONTAINER_NAME"; then

	printf '%s\n' "Building $CONTAINER_NAME from $IMAGE_NAME..."

	# Delete the container, if it's already made
	docker stop "$CONTAINER_NAME" > /dev/null 2>&1
	docker rm "$CONTAINER_NAME" > /dev/null 2>&1

	# Run the container, forwarding port 3306
	docker run --name="$CONTAINER_NAME" \
		-e "MYSQL_DATABASE=JudgeBeanDS" \
		-e "MYSQL_USER=judgebeanmysql" \
		-e "MYSQL_PASSWORD=judgebeanmysqlpassword" \
		-p 3306:3306 \
		-d "$IMAGE_NAME"

	if [ "$1" == "load" ]; then
		sleep 10
		mysql -h 172.17.0.1 -u judgebeanmysql JudgeBeanDS -p < dump.sql
	fi
else
	docker start "$CONTAINER_NAME"
fi
