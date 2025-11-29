# 📅 Cronograma del Proyecto – Bricks Java Challenge

Este cronograma detalla la planificación modular del desarrollo, siguiendo una arquitectura por capas, buenas prácticas de Spring Boot y entregables iterativos.

## 🧱 Resumen de Módulos y Avance

| **Módulo**                     | **Peso estimado** | **Estado** |
|-------------------------------|------------------|------------|
| Base del proyecto             | 10%              | ✔️ Completado |
| Dominio completo              | 15%              | ✔️ Completado |
| Services                      | 15%              | ✔️ Completado |
| Controllers + Excepciones     | 10%              | ✔️ Completado |
| Integración externa FakeStore | 10%              | ✔️ Completado |
| Cache                         | 10%              | ✔️ Completado |
| Swagger / OpenAPI             | 5%               | ✔️ Completado |
| Tests unitarios               | 20%              | ✔️ Completado |
| Documentación final           | 5%               | ✔️ Completado |

## 🗂 Fases del Desarrollo

### ✔️ Fase 1 — Inicialización (0.1.0)
- Creación del proyecto y repositorio  
- Configuración de Gradle y Java 17  
- Configuración de Spring Boot 4  
- Base de datos H2  
- Estructura por capas  
- Primera ejecución y verificación

### ✔️ Fase 2 — Dominio (0.2.0)
- Entidades `Category` y `Product`  
- DTOs  
- Repositorios con Spring Data JPA  
- Mappers manuales  
- Validación de tablas en H2  
- Merge en `develop`

### ✔️ Fase 3 — Lógica de Negocio (0.3.0)
- Services de categorías y productos  
- Excepciones de negocio  
- Validaciones  
- Uso de mappers internos para respuestas

### ✔️ Fase 4 — API REST (0.4.0)
- Endpoints REST completos  
- Validación con `@Valid`  
- `GlobalExceptionHandler`  
- Respuestas limpias y consistentes  

### ✔️ Fase 5 — Integración Externa (0.5.0)
- Cliente HTTP para FakeStore  
- Adaptación de modelos externos  
- Servicio de integración desacoplado  
- Endpoint para importar productos electrónicos

### ✔️ Fase 6 — Cache (0.6.0)
- Cache de listados  
- Invalidación selectiva  
- `@Cacheable`, `@CacheEvict`, `@CachePut`  
- Revisión de impacto en performance

### ✔️ Fase 7 — Swagger / OpenAPI (0.7.0)
- Configuración de Springdoc  
- Modelos documentados  
- Ejemplos y summaries  
- Test visual con Swagger UI

### ✔️ Fase 8 — Tests Unitarios (0.8.0)
- JUnit + Mockito  
- Mock de repositorios  
- MockMvc para controllers  
- Cobertura mínima del 70%  
- Tests de servicios, mappers, excepciones

### ✔️ Fase 9 — Documentación Final (1.0.0)
- README completo  
- Changelog final  
- Este cronograma  
- Decisiones técnicas  
- Ejemplos de requests/responses  
- Imagen general de arquitectura

