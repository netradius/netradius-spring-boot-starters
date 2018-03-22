#!/usr/bin/env bash

DIR=$(dirname ${0})
cd ${DIR}

docker-compose -p nrsbs-localdev -f localdev-docker-compose.yml stop
