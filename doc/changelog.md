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
  - `POST /product` (201 CREATED)
  - `GET /product`
  - `GET /product/{id}`
  - `PUT /product/{id}`
  - `DELETE /product/{id}` (204 NO CONTENT)
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

## [0.5.0] - *(pendiente)*
### Integración externa
- Servicio para consumir:
  - `https://fakestoreapi.com/products/category/electronics`
- Adaptación de datos externos al modelo interno.
- Carga automática o bajo demanda de categorías externas.

---

## [0.6.0] - *(pendiente)*
### Cache
- Implementación de cache para listados.
- Invalidación selectiva al crear/editar/eliminar productos.
- Configuración con `@Cacheable`, `@CacheEvict`, `@CachePut`.

---

## [0.7.0] - *(pendiente)*
### Documentación
- Swagger / OpenAPI totalmente operativo.
- Ejemplos de requests/responses.
- Documentación automática de modelos DTO.
- Descripciones y summary para cada endpoint.

---

## [0.8.0] - *(pendiente)*
### Tests Unitarios
- Tests de controller usando `MockMvc`.
- Tests de service (validaciones + excepciones + mappers).
- Mock de repositorios con Mockito.
- Cobertura mínima del 70%.

---

## [1.0.0] - *(pendiente)*
### Release estable
- API completa, probada y documentada.
- Lista para entrega final del desafío.

---
