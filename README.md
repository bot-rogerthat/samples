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
