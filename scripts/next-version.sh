#!/bin/bash

# Exit on error and unset variables
set -ue

# Change working directory
DIR="$(dirname $0)"
cd ${DIR}

# Get current version
VERSION=$(cat ../pom.xml | grep "<version>.*</version>" | head -1 | sed "s/.*<version>//" | sed "s/<\/version>.*//g")

if [[ $VERSION == *"SNAPSHOT"* ]]; then
	SNAPSHOT=true
	NEWVERSION=$(echo $VERSION | sed "s/-SNAPSHOT//g")
else
	SNAPSHOT=false
	IFS="." read -a varr <<< "$VERSION"
	NEWVERSION="${varr[0]}.${varr[1]}.${varr[2]}.$((${varr[3]}+1))-SNAPSHOT"
fi

cd ../
./mvnw versions:set -DnewVersion=${NEWVERSION} 
./mvnw versions:commit
