#!/bin/bash

if [ "dump" == "$1" ]; then
	mysqldump -h 172.17.0.1 -u beanpollmysql beanpollDS -p > dump.sql
else
	mysql -h 172.17.0.1 -u beanpollmysql beanpollDS -p
fi
