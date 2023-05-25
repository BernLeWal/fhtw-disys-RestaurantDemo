# Distributed Systems Demo Project
An example for a Distributed Systems project. It contains 
- OrderUI: a JavaFX frontend
- OrderAPI: a REST-Service implemented with Sprint Boot
- two worker services: CookService and WasherService
- a messaging queue (RabbitMQ) 
- and a database (PostgreSQL) prepared in a docker-compose.yml file

## Scenario
![](ComponentDiagram.png)

## Docker-Services
- queue
  - URL: localhost:5672
  - Web: localhost:15672
- orders_db
  - JDBC: localhost:5432 

## Requirements
- [Docker](https://docs.docker.com/get-docker/)

## Start
```shell
docker-compose up
```

## Documentations

### RabbitMQ Tutorial
- see [RabbitMQ](https://www.rabbitmq.com/tutorials/tutorial-one-java.html)

### RabbitMQ Management
- see [Hands-On RabbitMQ Management-Plugin](https://www.rabbitmq.com/management-cli.html)
```shell
docker exec -it <containerid> bash
```

- Show existing queues:
```shell
rabbitmqctl list_queues
```

### RabbitMQ Install Management-Plugin:
```shell
apt install wget, python3
rabbitmq-plugins enable rabbitmq_management
wget http://localhost:15672/cli/rabbitmqadmin
mv rabbitmqadmin /usr/local/bin
chmod a+x /usr/local/bin/rabbitmqadmin
```
	
- Get contents of a Queue:
```shell
rabbitmqadmin get queue=example
```

- Create Queues:
```shell
rabbitmqadmin declare queue name=re_spaces
```
See also [Stackoverflow](https://stackoverflow.com/questions/4545660/rabbitmq-creating-queues-and-bindings-from-command-line)

