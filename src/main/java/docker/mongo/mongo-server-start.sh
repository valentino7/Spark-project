#!/bin/bash
docker run -it -d --name mongo-server --network net -p 27017:27017 mongo:xenial /usr/bin/mongod --smallfiles --bind_ip_all
