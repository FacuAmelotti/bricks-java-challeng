# CHANGELOG  
Historial de cambios del proyecto **Bricks Java Challenge**  
Formato basado en [Semantic Versioning](https://semver.org/) y buenas prácticas de documentación.

---

## [0.1.0] - 2025-11-27 
### Inicialización del proyecto
- Creación del repositorio inicial en GitHub.
- Estructura base del proyecto configurada manualmente.
- Integración de Gradle Wrapper (`gradlew`, `gradlew.bat`).
- Configuración inicial de Spring Boot 4 con Java 17.
- Eliminación de clases duplicadas autogeneradas.
- Corrección de configuración YAML (remoción de propiedades incompatibles).
- Habilitación de H2 como base de datos en memoria.
- Proyecto compila y arranca correctamente.

### Organización del código
- Creación de estructura profesional con carpetas:
  - `config/`
  - `controller/`
  - `dto/`
  - `entity/`
  - `exception/`
  - `integration/`
  - `mapper/`
  - `repository/`
  - `service/`
- Creación de directorio `doc/` con documentación interna.
- Agregado del archivo `estructura.md` explicando la arquitectura por capas.

### Configuración del entorno
- Configuración de `application.yml`:
  - Definición de datasource H2.
  - Configuración de JPA / Hibernate (`ddl-auto:update`).
  - Configuración inicial de Springdoc (Swagger/OpenAPI).
  - Ajuste completo para compatibilidad con Spring Boot 4.

---

## [0.2.0] - 2025-11-28  
### Entidades + DTOs + Repositorios + Mappers (COMPLETADO)
- Creación de entidades JPA:
  - `Category`
  - `Product`
- Definición de relaciones (`@ManyToOne` / `@OneToMany`).
- Implementación de DTOs organizados por dominio:
  - `CategoryResponse`
  - `ProductRequest`
  - `ProductResponse`
  - `ProductFilter`
- Creación de repositorios con Spring Data JPA:
  - `CategoryRepository`
  - `ProductRepository`
  - Método derivado `findByTitle(String title)`
- Implementación manual de mappers:
  - `CategoryMapper`
  - `ProductMapper`
- Configuración confirmada: Hibernate genera tablas correctamente en H2.
- Ejecución de `./gradlew clean test` exitosa.
- Merge del feature `base-domain` en `develop`.

---

## [0.3.0] - 2025-11-28  
### Lógica de negocio (Services) — COMPLETADO
- Creación de la capa de servicios con separación por dominio:
  - `CategoryService` + `CategoryServiceImpl`
  - `ProductService` + `ProductServiceImpl`
- Integración con repositorios (`CategoryRepository`, `ProductRepository`).
- Uso de mappers para transformar entidades ↔ DTOs:
  - `CategoryMapper`
  - `ProductMapper`
- Validaciones internas:
  - Verificación de existencia de categoría al crear/actualizar productos.
  - Manejo de productos inexistentes mediante `ResourceNotFoundException`.
- Operaciones CRUD completas en productos:
  - Crear, listar, buscar por ID, actualizar, eliminar.
- Servicios de categoría:
  - Obtener todas
  - Obtener por ID
  - Obtener entidad interna para otros services.
- Ejecución de `./gradlew clean test` exitosa.
- Merge del feature `services` en `develop`.

---

## [0.4.0] - 2025-11-28  
### API REST (Controllers) — COMPLETADO
- Creación de la capa de controladores para exponer la API REST.
- Implementación de endpoints para productos:
  - `GET /api/products`
  - `GET /api/products/{id}`
  - `POST /api/products`
  - `PUT /api/products/{id}`
  - `DELETE /api/products/{id}` (204 NO CONTENT)
- Implementación de endpoints para categorías:
  - `GET /category`
  - `GET /category/{id}`
- Uso de DTOs para request/response:
  - `ProductRequest`, `ProductResponse`, `ProductFilter`
  - `CategoryResponse`
- Validación automática con `@Valid` para inputs.
- Manejo de errores unificado mediante `GlobalExceptionHandler`:
  - Validaciones (`MethodArgumentNotValidException`)
  - Recursos no encontrados (`ResourceNotFoundException`)
  - Errores inesperados (500)
- Arquitectura consistente con buenas prácticas de Spring Boot.
- Ejecución correcta de `./gradlew clean test`.
- Merge del feature `controllers` en `develop`.

---

## [0.5.0] - 2025-11-28
### Integración externa (FakeStore API)
- Creación de DTO externo `FakeStoreProductDto` para modelar la respuesta de FakeStore.
- Configuración de `RestTemplate` como cliente HTTP para llamadas HTTP salientes.
- Implementación de `FakeStoreIntegrationService`:
  - Llamada a `https://fakestoreapi.com/products/category/electronics`.
  - Adaptación de cada producto externo al modelo interno `Product`.
  - Resolución/creación de categorías internas en base al campo `category`.
  - Persistencia de productos en la base H2.
- Extensión de `ProductController` con endpoint:
  - `GET /api/external/products/electronics` para disparar la importación.

## [0.5.1] - 2025-11-28
### Hotfix — Correcciones en integración externa
- Eliminación de ExternalProductController duplicado ubicado dentro de FakeStoreIntegrationService.java.
- Corrección de imports rotos en:
  - ProductController
  - ExternalProductController
  - FakeStoreIntegrationServiceImpl
- Reparación del bean de integración:
  - Ajuste de firma de método importElectronicsProducts().
  - Eliminación de referencias antiguas (getElectronicsProducts).
- Reubicación correcta de archivos en el paquete integration/.
- Tests del proyecto vuelven a compilar (./gradlew clean test exitoso).
- Importación externa funcionando correctamente desde:
```bash
GET /api/external/products/electronics
```
- Confirmada persistencia de productos externos en la base H2.


---

## [0.6.0] - 2025-11-28
### Cache
- Activación del sistema de caché en Spring Boot mediante @EnableCaching.
- Implementación de caché para mejorar el rendimiento del endpoint:
  GET /api/products ahora usa @Cacheable("products").
- Invalidación automática del caché cuando cambia el estado de los productos:
  - @CacheEvict(value = "products", allEntries = true) aplicado en:
    - create()
    - update()
    - delete()
- Garantiza que los datos en caché nunca queden obsoletos tras operaciones de escritura.
- Pruebas manuales exitosas verificando:
  - Primera llamada → hit a la base.
  - Llamadas posteriores → respuesta desde caché.
  - Cada creación/actualización/eliminación → vacía el caché y fuerza regeneración.
- Proyecto sigue compilando correctamente (./gradlew clean test OK).

- !!! Revisar cache con postman
---

## [0.7.0] - 2025-11-28  
### Documentación (Swagger / OpenAPI) 
- Activación completa de documentación automática mediante **springdoc-openapi**.
- Configuración del endpoint principal:
  - `GET /swagger-ui/index.html` → interfaz visual interactiva.
  - `GET /v3/api-docs` → especificación OpenAPI en formato JSON.
- Anotación de controladores con:
  - `@Tag` para agrupar endpoints dentro de Swagger.
  - `summary` y `description` detallados por cada operación.
- Documentación generada automáticamente para:
  - Operaciones CRUD de productos.
  - Consultas internas de categorías.
  - Integración externa con FakeStore.
- Generación automática de *schemas* OpenAPI para los DTO:
  - `ProductRequest`, `ProductResponse`, `ProductFilter`.
  - `CategoryResponse`.
- Ajustes en `ExternalProductController` para incluir descripciones del proceso de importación.
- Verificación manual realizada:
  - Todas las rutas visibles y funcionales desde Swagger UI.
  - Schemas consistentes con las estructuras reales del proyecto.
  - Respuestas HTTP correctamente documentadas.
- Proyecto continúa compilando sin errores (`./gradlew clean test` OK).
- Lista para merge hacia `develop`.

---

## [0.8.0] - 2025-11-28
### Tests Unitarios (Services + Controllers + Mappers)

- **Capa de servicios (unit tests con JUnit 5 + Mockito)**
  - `ProductServiceImplTest`:
    - Cobertura completa de `findAll` (con y sin filtros), `findById`, `create`, `update` y `delete`.
    - Mock de `ProductRepository`, `CategoryService` y `ProductMapper`.
    - Verificación de:
      - Uso correcto de los repositorios.
      - Resolución de categorías internas.
      - Mapeo correcto entre entidades (Product) y DTOs (ProductResponse / ProductRequest).
      - Lanzamiento de ResourceNotFoundException cuando el recurso no existe.
  - `CategoryServiceImplTest`:
    - Cobertura completa de `findAll`, `findById` y `getEntityById`.
    - Mock de `CategoryRepository` y `CategoryMapper`.
    - Verificación de:
      - Mapeos internos correctos.
      - Manejo de ResourceNotFoundException cuando corresponde.

- **Capa web (tests de controlador con MockMvc en modo standalone)**
  - `ProductControllerTest`:
    - `GET /api/products` → 200 OK, retorna listado JSON.
    - `GET /api/products/{id}` → 200 OK (éxito) / 404 Not Found (recurso inexistente).
    - `POST /api/products` → 201 Created con body válido; 400 Bad Request cuando falla la validación (@Valid).
    - `PUT /api/products/{id}` → 200 OK al actualizar; 404 Not Found si el producto no existe.
    - `DELETE /api/products/{id}` → 204 No Content al eliminar; 404 Not Found cuando el service lanza ResourceNotFoundException.
    - Uso de GlobalExceptionHandler para mapear excepciones a códigos HTTP adecuados.
  - `CategoryControllerTest`:
    - `GET /api/categories` → 200 OK, listado JSON de categorías.
    - `GET /api/categories/{id}` → 200 OK (éxito) / 404 Not Found.
    - Verificación del uso correcto del service, integración con GlobalExceptionHandler y estructura JSON esperada.

- **Tests de mappers (unit tests puros)**
  - `ProductMapperTest`:
    - Verifica `toEntity` y `toDto`.
    - Validación de name, price, stock y relación con categoría.
  - `CategoryMapperTest`:
    - Verifica `toEntity` y `toDto`.
    - Validación del mapeo correcto de id y title.

- **Estado del proyecto**
  - Suite de tests ejecutándose correctamente con `./gradlew clean test`.
  - Cobertura sobre servicios, controladores y mapeadores.
  - Base estable y completamente probada para la entrega del challenge.


---

## [1.0.0] - 2025-11-29
### Release estable – Versión lista para evaluación

- API REST de productos y categorías completamente funcional.
- Integración externa con FakeStore operativa mediante:
  - `GET /api/external/products/electronics`
- Cache configurado y verificado en:
  - `GET /api/products` con invalidación automática en operaciones de escritura.
- Suite de tests unitarios ejecutándose correctamente (`./gradlew clean test`):
  - Servicios (`ProductServiceImplTest`, `CategoryServiceImplTest`)
  - Controladores (`ProductControllerTest`, `CategoryControllerTest`)
  - Mappers (`ProductMapperTest`, `CategoryMapperTest`)
- Documentación disponible para el evaluador:
  - `README.md` con instrucciones de deploy y ejecución.
  - `doc/ejecucion.md` con flujo sugerido de pruebas.
  - `doc/endpoints.md` con listado de rutas principales.
- Build final generable mediante:
  - `./gradlew clean build` → `build/libs/bricks-java-challenge-0.0.1-SNAPSHOT.jar`
- Proyecto considerado estable y listo para revisión en el contexto del desafío técnico de Bricks.


---
