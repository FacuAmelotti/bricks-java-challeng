# Desafío Técnico – Bricks Java Challenge

API REST de Productos y Categorías — Java / Spring Boot

## Descripción

Este proyecto implementa una API REST completa, robusta y documentada, para la administración de productos y categorías, como parte del desafío técnico solicitado por Bricks.

### Características Principales

- ✅ CRUD completo de productos y categorías
- ✅ Integración con API externa (FakeStore)
- ✅ Cache de resultados
- ✅ Manejo global de errores
- ✅ Tests unitarios (services + controllers)
- ✅ Swagger UI (OpenAPI 3)
- ✅ Arquitectura limpia por capas

## Tecnologías Principales

| Tecnología | Uso |
|------------|-----|
| Java 17 | Lenguaje principal |
| Spring Boot 4 | Framework principal |
| Spring Web | Exposición de API REST |
| Spring Data JPA | Persistencia |
| H2 Database | Base en memoria |
| Spring Cache | Cache de resultados |
| Spring Validation | Validación de requests |
| springdoc-openapi | Swagger UI |
| Gradle | Build tool |
| JUnit 5 + Mockito + MockMvc | Tests automatizados |

## Objetivos del Sistema

La API permite administrar productos y categorías con:

### CRUD de Productos
- Crear producto
- Obtener producto por ID
- Listar todos (con filtros)
- Actualizar producto
- Eliminar producto

### Filtros Disponibles en `/api/products`
- `minPrice`
- `maxPrice`
- `categoryId`

### Categorías
- Listar todas
- Buscar por ID
- Obtener entidad interna para lógica de negocio

### Integración Externa — FakeStore API

Importación automática de productos electrónicos:
```
GET /api/external/products/electronics
```

Los productos se adaptan al modelo interno y se guardan en H2.

### Documentación

Swagger UI disponible en:
```
http://localhost:8080/swagger-ui/index.html
```

### Caché Automático

Los listados de productos utilizan caché:
- `@Cacheable("products")` para findAll
- `@CacheEvict("products")` en:
  - create()
  - update()
  - delete()

### Tests Automatizados

- Tests unitarios de servicios (Mockito)
- Tests del controlador con MockMvc

## Cómo Ejecutar el Proyecto

### Requisitos Previos
- Java 17
- Git
- Internet para primera importación externa (opcional)

### Clonar el Repositorio
```bash
git clone https://github.com/tu-usuario/bricks-java-challenge.git
cd bricks-java-challenge
```

### Ejecutar la Aplicación
```bash
./gradlew bootRun
```

### 🔧 Ejecutar como JAR (Deploy)
```bash
./gradlew clean build
```
Esto genera:
```text
build/libs/bricks-java-challenge-0.0.1-SNAPSHOT.jar
```
Ejecutar con:
```text
java -jar build/libs/bricks-java-challenge-0.0.1-SNAPSHOT.jar
```

### Acceder a Swagger
```
http://localhost:8080/swagger-ui/index.html
```

### Base de Datos H2 (consola)
```
http://localhost:8080/h2-console
```

**Credenciales H2:**
- JDBC URL: `jdbc:h2:mem:bricksdb`
- Driver: `org.h2.Driver`
- User: `sa`
- Password: *(vacío)*

## 📡 Endpoints Principales

### Productos
| Método | Endpoint                             | Descripción                               |
| ------ | ------------------------------------ | ----------------------------------------- |
| GET    | `/api/products`                      | Listar productos (con filtros opcionales) |
| GET    | `/api/products/{id}`                 | Obtener un producto                       |
| POST   | `/api/products`                      | Crear un producto                         |
| PUT    | `/api/products/{id}`                 | Actualizar un producto                    |
| DELETE | `/api/products/{id}`                 | Eliminar un producto                      |
| GET    | `/api/external/products/electronics` | Importar productos desde FakeStore        |


### Categorías
| Método | Endpoint         | Descripción       |
| ------ | ---------------- | ----------------- |
| GET    | `/category`      | Listar categorías |
| GET    | `/category/{id}` | Obtener categoría |


## Tests Automatizados

El proyecto incluye:

- ✅ Tests de Services (JUnit + Mockito)
  - ProductServiceImplTest
  - CategoryServiceImplTest
- ✅ Tests de Controller (MockMvc standalone)
  - ProductControllerTest

**Para ejecutar:**
```bash
./gradlew clean test
```

Todos los tests pasan correctamente antes del build final.

## Arquitectura del Proyecto

El proyecto sigue una arquitectura por capas clara y mantenible:

```
src/main/java/com/bricks/challenge
│
├── config/            → Configuración (cache, swagger, etc.)
├── controller/        → Endpoints REST
├── dto/               → Requests & Responses
├── entity/            → Entidades JPA
├── exception/         → Manejo global de errores
├── integration/       → Integración externa (FakeStore API)
├── mapper/            → Conversión Entity ↔ DTO
├── repository/        → Interfaces JPA
└── service/           → Lógica de negocio
```

## Scripts Útiles

```bash
# Ejecutar la aplicación
./gradlew bootRun

# Ejecutar tests
./gradlew clean test

# Build completo
./gradlew clean build
```

## Estado del Proyecto

- ✅ API funcional
- ✅ Documentada (Swagger)
- ✅ Probada (tests unitarios)
- ✅ Integración externa funcionando
- ✅ Caché operativo
- ✅ Arquitectura limpia
- ✅ Proyecto listo para evaluación


---

**Desarrollado como parte del Desafío Técnico de Bricks** 