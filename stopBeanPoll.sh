#!/bin/bash

printf '%s\n' " => Stopping BeanPoll"

docker stop beanpoll-wildfly
docker stop beanpoll-mysql
