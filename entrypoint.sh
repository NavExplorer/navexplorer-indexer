#!/bin/bash

exec java -jar -Dspring.config.location=file:/app/config /app/navexplorerIndexer.jar > /app/logs/navexplorer-indexer.log