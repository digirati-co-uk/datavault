#!/bin/sh

docker run -c 512 -m 2048MB -p 3306:3306 -e MYSQL_ROOT_PASSWORD=unbr34kable -e MYSQL_DATABASE=datavault \
  -e MYSQL_USER=datavault -e MYSQL_PASSWORD=datavault mariadb:latest


