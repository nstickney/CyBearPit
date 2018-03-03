#!/bin/bash

if [[ "$(docker images -q judgebean-arqtest-wildfly 2> /dev/null)" == "" ]]; then
    docker build --no-cache --force-rm -t judgebean-arqtest-wildfly .    
fi
docker run -d  -p 8080:8080 -p 9990:9990 -p 8787:8787 judgebean-arqtest-wildfly
