#!/usr/bin/bash

mvn clean package

docker build -t playsoft .

