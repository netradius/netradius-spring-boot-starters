#!/usr/bin/env bash

docker exec -it nrsbslocaldev_db_1 su - postgres -c "psql netradius"
