# spotguide-spring-boot
A spotguide for a Spring Boot application which talks with MySQL database.


```bash
## Build and Test locally with Docker for Mac Kubernetes
docker build -t banzaicloud/spotguide-spring-boot:latest .
helm dep update .banzaicloud/charts/spotguide-spring-boot
helm upgrade --install spotguide-spring-boot .banzaicloud/charts/spotguide-spring-boot --set ingress.enabled=true --set "ingress.hosts[0]=localhost" --set monitor.enabled=true

# Check the application (if Ingress not enabled)
kubectl port-forward deployment/spotguide-spring-boot 8080

# Access the Spring application
open http://localhost:[8080]
```

```bash
# Delete the Helm release
helm delete --purge spotguide-spring-boot
```

## Run locally

```bash
mvn clean package -DskipTests

SPRING_DATASOURCE_USERNAME=sparky SPRING_DATASOURCE_PASSWORD=sparky123 java -jar target/app.jar

curl -H "Content-Type: application/json" http://localhost:8080/users -d '{"name":"john","email":"john@doe.com"}'
```