#!/usr/bin/env bash

DIR=$(dirname ${0})
cd ${DIR}

./localdev-stop.sh

docker-compose -p nrsbs-localdev -f localdev-docker-compose.yml rm -f

docker volume rm nrsbslocaldev_pgdata
