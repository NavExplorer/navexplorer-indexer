#!/usr/bin/env bash
mvn clean install -U

docker-compose build
docker-compose up -d
docker-compose restart indexer