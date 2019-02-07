FROM anapsix/alpine-java

ADD ./target/navexplorer-indexer-BUILD.jar /app/navexplorerIndexer.jar
ADD ./config/* /app/

ADD ./entrypoint.sh /entrypoint.sh

ENTRYPOINT ["/entrypoint.sh"]