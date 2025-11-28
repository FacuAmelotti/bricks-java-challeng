# Objetivos del Proyecto  
Bricks Java Challenge – API de Productos

Este documento describe los **objetivos principales** del sistema desarrollado durante el desafío técnico.  
El propósito es dejar clara la intención funcional del proyecto y los criterios que se tuvieron en cuenta al diseñarlo.

---

## Objetivo General

Desarrollar una **API REST completa y mantenible**, utilizando Java + Spring Boot, que permita **administrar productos y categorías** de un comercio, integrando datos externos y utilizando una base de datos en memoria.

El proyecto debe ser:
- Correcto técnicamente
- Escalable y bien estructurado
- Fácil de ejecutar
- Fácil de entender para cualquier evaluador

---

## Objetivos Específicos

### 1. Exponer una API REST clara y funcional
Permitir:
- Listado de productos con filtros
- CRUD completo de productos
- Listado de categorías
- Integración con servicio externo para importar categorías

---

### 2. Implementar una arquitectura profesional por capas
Separar responsabilidades en:
- Controller
- Service
- Repository
- Entity
- DTO
- Mapper
- Exception
- Integration
- Config

Siguiendo buenas prácticas de **clean architecture** y **Spring Boot conventions**.

---

### 3. Utilizar una base de datos en memoria (H2)
- No requiere instalación externa
- Ideal para pruebas y desafíos técnicos  
- Generación automática del esquema mediante JPA

---

### 4. Documentar la API con Swagger / OpenAPI
Asegurar:
- Autodescripción de endpoints
- Tipado claro de entradas y salidas
- Facilidad para testear la API desde navegador

---

### 5. Manejar errores de forma consistente
- Respuestas unificadas
- Manejo centralizado de excepciones
- Uso de status HTTP adecuados

---

### 6. Implementar cache para mejorar desempeño
- Evitar llamadas repetitivas para listados
- Invalidación automática cuando cambia el estado

---

### 7. Diseñar tests unitarios representativos
- Tests de services
- Tests de controllers con MockMvc
- Uso de Mockito para mocks de dependencias

---

### 8. Entregar el proyecto con documentación clara
Incluyendo:
- README con instrucciones
- CHANGELOG
- Estructura del proyecto
- Objetivos
- Alcance

---

## Resultado esperado

Un proyecto:
- Fácil de ejecutar (`./gradlew bootRun`)
- Fácil de entender (arquitectura ordenada)
- Fácil de evaluar (Swagger funcional)
- Con estándares reales de desarrollo profesional

Ideal para demostrar conocimiento en:
- Spring Boot
- Diseño de APIs
- Buenas prácticas
- Testeo
- Documentación
