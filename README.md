# 📦 price-service

Microservicio **API-first** en Java 21 + Spring Boot 3.5.5, arquitectura **hexagonal**, H2 + Flyway, tests **JUnit 5** y **Cucumber**, y **JaCoCo** con objetivo de **80%** de cobertura del código propio (se excluye código generado).

---

## 🚀 Cómo ejecutar

### Con Maven
```bash
mvn clean verify
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Dspring.cloud.config.enabled=false"
```

### Con JAR
```bash
mvn clean package -DskipTests
java -jar -Dspring.cloud.config.enabled=false target/price-service-1.0.0.jar
```

Swagger UI: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

---

## 📖 Contrato OpenAPI
El contrato está en `src/main/resources/openapi/openapi.yaml` y se usa `openapi-generator-maven-plugin` para generar las interfaces de controlador.  
Implementamos `PricesApi` en `PricesController`.

---

## 🗄️ Modelo de datos
Creado por Flyway (`V1__init.sql`) con los datos del enunciado.

---

## 🌐 Endpoints
- `GET /prices?applicationDate=2020-06-14T10:00:00&productId=35455&brandId=1`

Ejemplo real:

```bash
curl -X GET "http://localhost:8080/api/prices?date=2020-06-14T10:00:00&productId=35455&brandId=1" -H "accept: application/json"
```

Respuesta esperada:

```json
{
  "productId": 35455,
  "brandId": 1,
  "priceList": 1,
  "startDate": "2020-06-14T00:00:00",
  "endDate": "2020-12-31T23:59:59",
  "price": 35.50,
  "currency": "EUR"
}
```

---

## 🧪 Tests
- **Unitarios**: `PriceQueryServiceTest`, `PriceRepositoryAdapterTest`, `PricesControllerTest`.  
- **Integración BDD con Cucumber**: `prices.feature` valida los 5 casos del documento.  
- **Cobertura**: `target/site/jacoco/index.html` (ratio 100% para el código del paquete `com.example.pricing..` excluyendo `generated` y la clase `PriceServiceApplication`).  

---

## 🏗️ Decisiones de diseño
- Arquitectura **hexagonal** con puerto `PriceRepositoryPort` y adaptador JPA.  
- Consulta eficiente con índice natural (por simplicidad H2 sin índices, añadiría índice compuesto por `(PRODUCT_ID, BRAND_ID, START_DATE, END_DATE, PRIORITY)` en producción).  
- `@ControllerAdvice` para mapeo de errores.  

---

## 🐳 Despliegue con Docker

La imagen está publicada en **DockerHub**:  
👉 [sergioncio/price-service](https://hub.docker.com/r/sergioncio/price-service)

### Descargar la imagen
```bash
docker pull sergioncio/price-service:latest
```

### Ejecutar contenedor
```bash
docker run -d   -p 8080:8080   -e SPRING_CLOUD_CONFIG_ENABLED=false   --name price-service   sergioncio/price-service:latest
```

Acceso:
- API REST: [http://localhost:8080/api/prices](http://localhost:8080/api/prices)  
- Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)  

---

## 🛠️ Despliegue con Docker Compose

Archivo `docker-compose.yml` (H2 embebida):

```yaml
version: "3.9"
services:
  price-service:
    image: sergioncio/price-service:latest
    container_name: price-service
    ports:
      - "8080:8080"
    environment:
      SPRING_CLOUD_CONFIG_ENABLED: "false"
      SPRING_DATASOURCE_URL: jdbc:h2:mem:prices;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
      SPRING_DATASOURCE_DRIVERCLASSNAME: org.h2.Driver
      SPRING_DATASOURCE_USERNAME: sa
      SPRING_DATASOURCE_PASSWORD:
      SPRING_JPA_HIBERNATE_DDL_AUTO: validate
      SPRING_FLYWAY_ENABLED: "true"
```

Levantar servicio:
```bash
docker-compose up -d
```

---

## ✅ Verificación del servicio

Comprobar estado:
```bash
curl http://localhost:8080/actuator/health
```

Respuesta esperada:
```json
{"status":"UP"}
```

---

## ⚙️ CI/CD con GitHub Actions

El workflow realiza:
1. Compilación y ejecución de tests (JUnit5 + Cucumber).  
2. Análisis de cobertura con JaCoCo.  
3. Construcción y push de la imagen a DockerHub (`sergioncio/price-service:latest`).  

Secrets necesarios en el repo:
- `DOCKERHUB_USERNAME` → usuario de DockerHub.  
- `DOCKERHUB_TOKEN` → token de acceso de DockerHub.  

Ejemplo de job para build & push:

```yaml
- name: Login en DockerHub
  uses: docker/login-action@v3
  with:
    username: ${{ secrets.DOCKERHUB_USERNAME }}
    password: ${{ secrets.DOCKERHUB_TOKEN }}

- name: Build & Push Docker image
  uses: docker/build-push-action@v6
  with:
    context: .
    push: true
    tags: sergioncio/price-service:latest
```

---

## 📌 Notas
- Para despliegues en entornos cloud, se puede usar PostgreSQL configurando `application-docker-postgres.properties`.  
- La imagen ya incluye Flyway, que ejecuta automáticamente las migraciones al arrancar.  
- Swagger UI documenta todo el contrato OpenAPI.  
