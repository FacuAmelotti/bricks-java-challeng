# Desafío Técnico – Bricks 🧱  
API REST de Productos y Categorías (Java / Spring Boot)

Este proyecto implementa una **API REST** para la administración de productos de un comercio, como parte del **desafío técnico de Bricks**.  
Permite gestionar productos y categorías, aplicar filtros y paginación, documentar la API con OpenAPI/Swagger, utilizar una base de datos en memoria y realizar tests automatizados.

---

## 🧰 Stack Tecnológico

- **Java 17**
- **Spring Boot** (Web, Validation)
- **Spring Data JPA**
- **Base de datos H2 en memoria**
- **Spring Cache**
- **OpenAPI / Swagger UI** (springdoc-openapi)
- **Gradle** como build tool
- **JUnit 5 + Spring Boot Test + MockMvc** para tests

---

## 🎯 Objetivo del Sistema

La API permite:

- Gestionar **productos**:
  - Crear, obtener, actualizar y eliminar productos.
  - Listar productos con **filtros** por:
    - nombre
    - precio
    - stock
    - categoría
  - Soporte de **paginación**.

- Gestionar **categorías**:
  - Listar categorías disponibles.
  - Obtener los datos de la categoría `electronics` desde el endpoint externo:  
    `https://fakestoreapi.com/products/category/electronics`

- Exponer la documentación de la API con **Swagger / OpenAPI**.
- Utilizar **cache** en los endpoints de lectura más utilizados.
- Contar con **tests automatizados** para las capas principales.

---

## 🚀 Cómo ejecutar el proyecto

### Requisitos

- **Java 17** instalado
- **Git**
- Conexión a internet (para consumir la API externa de Fakestore en el arranque)

### Clonar el repositorio

```bash
git clone https://github.com/tu-usuario/bricks-java-challenge.git
cd bricks-java-challenge
```