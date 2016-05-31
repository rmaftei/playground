#!/bin/bash

mongod --dbpath /opt/mongodb/db --logpath /var/log/mongodb/mongod.log --fork

java -jar /opt/rest-service-1.0-SNAPSHOT.jar
