# samples
---
#### 1. run postgres
```
docker run --name samples-postgres -e POSTGRES_PASSWORD=postgres -d -p 5432:5432 postgres
```
#### 2. run kafka
```
docker-compose run -d
```
download:
```
https://apache-mirror.rbc.ru/pub/apache/kafka/2.6.0/kafka_2.12-2.6.0.tgz
```
create topic
```
.\kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test-topic
```
#### 3. test
```
test.http
```
