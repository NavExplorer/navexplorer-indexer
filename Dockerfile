FROM anapsix/alpine-java

ADD ./target/navexplorer-indexer-0.3.jar /app/navexplorerIndexer.jar
ADD ./config /app/config
RUN mkdir /app/logs

ADD ./entrypoint.sh /entrypoint.sh

ENTRYPOINT ["/entrypoint.sh"]