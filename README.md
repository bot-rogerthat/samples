# samples
---
#### 1. run postgres
```
docker run --name samples-postgres -e POSTGRES_PASSWORD=postgres -d -p 5432:5432 postgres
```
#### 2. run kafka
```
docker-compose up -d
```
download:
```
https://apache-mirror.rbc.ru/pub/apache/kafka/2.6.0/kafka_2.12-2.6.0.tgz
```
create topic
```
.\kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test-topic
```
#### 3. run zipkin
```
docker run -d -p 9411:9411 openzipkin/zipkin
```
#### 5. run app
```
alt+8 & crtl+shift+F10
```
#### 6. run test
```
test.http
```
#### 7. look at zipkin
```
http://localhost:9411/zipkin/
```


docker run -d --name jaeger -e COLLECTOR_ZIPKIN_HTTP_PORT=9411 -p 5775:5775/udp -p 6831:6831/udp -p 6832:6832/udp -p 5778:5778 -p 16686:16686 -p 14268:14268 -p 14250:14250 -p 9411:9411 jaegertracing/all-in-one:1.20

docker run -d -p 9000:9000 -e ZOOKEEPER_CONNECT=127.0.0.1:2181 kafdrop

docker run -d --rm -p 9000:9000 -e KAFKA_BROKERCONNECT=127.0.0.1:9092 -e JVM_OPTS="-Xms32M -Xmx64M" -e SERVER_SERVLET_CONTEXTPATH="/" obsidiandynamics/kafdrop:latest

#### run  rabbit mq:
docker run -d -p 5672:5672 -p 15672:15672 --name my-rabbit rabbitmq:3-management
