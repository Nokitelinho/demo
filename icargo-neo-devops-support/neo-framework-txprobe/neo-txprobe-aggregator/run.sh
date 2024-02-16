#DBG='-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=7088'
java $DBG -XX:ParallelGCThreads=8 -XX:G1ConcRefinementThreads=8 -XX:CICompilerCount=4 -XX:+ExitOnOutOfMemoryError -Dout.handler.es.serverUrl=http://10.0.0.2:9200 -jar target/quarkus-app/quarkus-run.jar

