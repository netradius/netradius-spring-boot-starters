#!/usr/bin/env bash

DIR=$(dirname ${0})
cd ${DIR}

./localdev-stop.sh

docker-compose -p nrsbs-localdev -f localdev-docker-compose.yml up --build --abort-on-container-exit
