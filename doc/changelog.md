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

## [0.2.0] - *(pendiente)*
### Entidades + Repositorios (próximo paso)
- Creación de `Category` y `Product` como entidades JPA.
- Declaración de relaciones (`@ManyToOne` / `@OneToMany`).
- Generación de repositorios con Spring Data:
  - `CategoryRepository`
  - `ProductRepository`
- Validación del correcto bootstrap de Hibernate en H2.

---

## [0.3.0] - *(pendiente)*
### Lógica de negocio (Services)
- Implementación de `CategoryService`.
- Implementación de `ProductService`.
- Manejo de excepciones de dominio.
- Filtros y paginación interna en productos.

---

## [0.4.0] - *(pendiente)*
### API REST (Controllers)
- Implementación de endpoints:
  - `GET /product`
  - `GET /product/{id}`
  - `POST /product`
  - `PUT /product/{id}`
  - `DELETE /product/{id}`
  - `GET /category`
- Respuestas normalizadas mediante DTOs.
- Códigos de estado correctos.

---

## [0.5.0] - *(pendiente)*
### Integración externa
- Servicio para consumir:
  - `https://fakestoreapi.com/products/category/electronics`
- Adaptación de datos externos al modelo interno.
- Carga automática de categorías externas.

---

## [0.6.0] - *(pendiente)*
###  Cache
- Implementación de cache para listados de productos.
- Cache selective invalidation al crear/editar/eliminar productos.

---

## [0.7.0] - *(pendiente)*
### Documentación
- Swagger / OpenAPI completamente operativo.
- Ejemplos de requests/responses.
- Documentación automática de modelos y endpoints.

---

## [0.8.0] - *(pendiente)*
### Tests Unitarios
- Tests de controller usando `MockMvc`.
- Tests de service (lógica pura + excepciones).
- Mock de repositorios con Mockito.
- Cobertura mínima recomendada del 70%.

---

## [1.0.0] - *(pendiente)*
### Release estable
- API completa, probada y documentada.
- Listo para entrega final del desafío.

---

