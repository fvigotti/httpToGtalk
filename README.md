# http to gtalk

receive http requests from seyren-http-target and send gtalk message
  
docker run --rm -ti -v "$(pwd):/jar"  --entrypoint "java" fvigotti/docker-fatjdk8 -jar /jar/build/libs/gs-spring-boot-0.1.0.jar 