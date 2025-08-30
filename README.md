# price-service

Microservicio **API-first** en Java 21 + Spring Boot 3.5.5, arquitectura **hexagonal**, H2 + Flyway, tests **JUnit 5** y **Cucumber**, y **JaCoCo** con objetivo de **80%** de cobertura del código propio (se excluye código generado).

## Cómo ejecutar
```bash
mvn clean verify
java -jar target/price-service-1.0.0.jar
```

Swagger UI: `http://localhost:8080/swagger-ui/index.html`

## Contrato OpenAPI
El contrato está en `src/main/resources/openapi/openapi.yaml` y se usa `openapi-generator-maven-plugin` para generar las interfaces de controlador. Implementamos `PricesApi` en `PricesController`.

## Modelo de datos
Creado por Flyway (`V1__init.sql`) con los datos del enunciado.

## Endpoints
- `GET /prices?applicationDate=2020-06-14T10:00:00&productId=35455&brandId=1`

## Tests
- Unitarios: `PriceQueryServiceTest`, `PriceRepositoryAdapterTest`, `PricesControllerTest`.
- Integración BDD con Cucumber: `prices.feature` valida los 5 casos del documento.
- Cobertura: `target/site/jacoco/index.html` (ratio 100% para el código del paquete `com.example.pricing..` excluyendo `generated` y la clase `PriceServiceApplication`).

## Decisiones de diseño
- Arquitectura hexagonal con puerto `PriceRepositoryPort` y adaptador JPA.
- Consulta eficiente con índice natural (por simplicidad H2 sin índices, añadiría índice compuesto por `(PRODUCT_ID, BRAND_ID, START_DATE, END_DATE, PRIORITY)` en producción).
- `@ControllerAdvice` para mapeo de errores.
