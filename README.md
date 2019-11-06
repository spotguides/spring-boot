# Spotguide for Sprint Boot with MySQL database

This repository was created by Banzai Cloud Pipeline. Spotguides provide automated configuration, logging, monitoring, security, and scaling for your application stacks.

<!-- TOC -->

- [Spotguide for Sprint Boot with MySQL database](#spotguide-for-spring-boot-with-mysql-database)
  - [Development](#development)
    - [Local development](#local-development)
      - [Deploy to a local Kubernetes cluster](#deploy-to-a-local-kubernetes-cluster)
      - [Run tests](#run-tests)
      - [Start application in development mode](#start-application-in-development-mode)
    - [Kubernetes ready Spring Boot](#kubernetes-ready-spring-boot)
  - [Environment variables](#environment-variables)
  - [Endpoints](#endpoints)
    - [`GET /actuator/health/kubernetes`](#get-actuatorhealthkubernetes)
    - [`GET /actuator/prometheus`](#get-actuatorprometheus)
    - [`GET /actuator/health`](#get-actuatorhealth)
    - [`GET /ready`](#get-ready)
    - [`GET /api/v1/users`](#get-apiv1users)
    - [`POST /api/v1/users`](#post-apiv1users)
    - [`GET /api/v1/users/:id`](#get-apiv1usersid)
    - [`PUT /api/v1/users/:id`](#put-apiv1usersid)
    - [`DELETE /api/v1/users/:id`](#delete-apiv1usersid)

<!-- /TOC -->

## Development

Every time you make changes to the source code and update the `master` branch, the CI/CD pipeline will be triggered to test, validate and update the deployment of your application. Check the [`.pipeline.yaml`](.banzaicloud/pipeline.yaml) file for CI/CD steps.

### Local development

#### Deploy to a local Kubernetes cluster

_Requirements:_

- [Docker](https://www.docker.com/)
- [Docker for Desktop](https://www.docker.com/products/docker-desktop)
- [skaffold](https://github.com/GoogleContainerTools/skaffold) and its dependencies

A local Kubernetes cluster must be running (eg. [Docker for Desktop](https://www.docker.com/products/docker-desktop)).

```sh
# verify the Kubernetes context
$ kubectl config get-contexts
# expected output
CURRENT   NAME                 CLUSTER                      AUTHINFO             NAMESPACE
*         docker-for-desktop   docker-for-desktop-cluster   docker-for-desktop
# build the Docker image and deploy via helm
$ cd .banzaicloud
$ skaffold run
```

This will build the application and install all the components to Kubernetes.

#### Run tests

##### Using installed Maven:

_Requirements:_

- [Maven](https://maven.apache.org/)

_Commands:_

```sh
mvn clean package
```

##### Using Docker:

_Requirements:_

- [Docker](https://www.docker.com/)

_Commands:_

```sh
DOCKER_REPO=spotguide-spring-boot DOCKER_TAG=latest mvn compile jib:dockerBuild
```

#### Start application in development mode

This spotguide uses [spring-boot-devtools](https://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-devtools.html), it is a utility dependency that will monitor for any changes in your classpath and automatically restart your server.

Using local Spring Boot and with the SQL compatible H2 database:

_Requirements:_

- [Maven](https://maven.apache.org/)

_Commands:_

```sh
mvn spring-boot:run
```

Using Docker and Docker Compose:

_Requirements:_

- [Docker](https://www.docker.com/)

_Commands:_

```sh
# start dependencies
docker-compose up -d
# build image
DOCKER_REPO=spotguide-spring-boot DOCKER_TAG=latest mvn compile jib:dockerBuild
# start application in development mode
mvn spring-boot:run
```

### Kubernetes ready Spring Boot

Our [`Spring Boot Actuator` library](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#production-ready) and [Spring Cloud Kubernetes](https://github.com/spring-cloud/spring-cloud-kubernetes) together provides all the essential features to prepare your Spring Boot application truly ready for production on Kubernetes, such as:

- graceful error handling & shutdown
- structured JSON logging
- various HTTP middleware
- health checks
- metrics

## Environment variables

| name                         | description                                                                                                                   | default     |
| ---------------------------- | ----------------------------------------------------------------------------------------------------------------------------- | ----------- |
| `SERVER_PORT`                | Application port                                                                                                              | 8080        |
| `SPRING_DATASOURCE_URL`      | JDBC URL, scheme: `jdbc:driver:host1[:port1][,host2[:port2],...[,hostN[:portN]]][/[database][?options]]`                      |             |
| `SPRING_DATASOURCE_USERNAME` | Database user                                                                                                                 |             |
| `SPRING_DATASOURCE_PASSWORD` | Database user password                                                                                                        |             |
| `SPRING_DATASOURCE_DATABASE` | Database database name                                                                                                        |             |

## Endpoints

These are the available REST endpoints in the sample application:

### `GET /actuator/health/kubernetes`

Kubernetes endpoint, returns basic [Pod](https://kubernetes.io/docs/concepts/workloads/pods/pod/) information, like name, namespace and image.

### `GET /actuator/prometheus`

[Prometheus](https://prometheus.io) metrics endpoint.

### `GET /actuator/health`

Health check, liveness probe endpoint. Returns `200` when the application is healthy, can reach the database.

### `GET /api/v1/users`

Fetch all users.

### `POST /api/v1/users`

Create a new user. The request body has the following schema:

```json
{
  "email": "",
  "userName": "",
  "firstName": "",
  "lastName": ""
}
```

### `GET /api/v1/users/:id`

Fetch a user.

### `PUT /api/v1/users/:id`

Update a user. The request body has the same schema as [`POST /api/v1/users`](#post-apiv1users).

### `DELETE /api/v1/users/:id`

Delete a user.

## Optional Kafka endpoints

You have to enable Kafka support via running it with `SPRING_KAFKA_ENABLED=true`.

### `GET /api/v1/kafka`

Fetch all Kafka messages.

### `POST /api/v1/kafka`

Send a new message to the `"spring-boot"` Kafka topic.

```json
{
  "message": "hello-spring-boot"
}
```

## Build Run and Test locally

```bash
## Build and Test locally with Docker for Mac Kubernetes
DOCKER_REPO=banzaicloud/spotguide-spring-boot DOCKER_TAG=latest mvn compile jib:dockerBuild
helm dep update .banzaicloud/charts/spotguide-spring-boot
helm upgrade --install spotguide-spring-boot .banzaicloud/charts/spotguide-spring-boot --set ingress.enabled=true --set "ingress.hosts[0]=localhost" --set monitor.enabled=true --set pipeline-ingress.enabled=true

# Check the application (if Ingress not enabled)
kubectl port-forward deployment/spotguide-spring-boot 8080

# Access the Spring application
open http://localhost:[8080]
```

```bash
# Delete the Helm release
helm delete --purge spotguide-spring-boot
```

```bash
# Build the fat JAR
mvn clean package -DskipTests

SPRING_DATASOURCE_USERNAME=sparky SPRING_DATASOURCE_PASSWORD=sparky123 java -jar target/app.jar

curl -H "Content-Type: application/json" http://localhost:8080/api/v1/users -d '{"userName":"john","email":"john@doe.com"}'
```
