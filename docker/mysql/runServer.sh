#!/bin/bash

# Run the container, forwarding port 3306
docker run --name=judgebean-mysql \
	-e "MYSQL_DATABASE=JudgeBeanDS" \
	-e "MYSQL_USER=judgebeanmysql" \
	-e "MYSQL_PASSWORD=judgebeanmysqlpassword" \
	-p 3306:3306 \
	-d mysql/mysql-server:5.7
