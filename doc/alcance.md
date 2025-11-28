# Alcance del Proyecto  
Bricks Java Challenge – API de Productos

Este documento detalla el **alcance** del desarrollo:  
qué funcionalidades **incluye** y cuáles **no forman parte** del desafío, para establecer expectativas claras.

---

# ✔ Alcance Incluido (In-Scope)

### 1. API REST completa para productos
El sistema incluye los siguientes endpoints:

- `GET /product`  
  Listado de productos con filtros, orden y paginación.

- `GET /product/{id}`  
  Obtención de un producto por ID.

- `POST /product`  
  Creación de un producto nuevo.

- `PUT /product/{id}`  
  Actualización total del producto.

- `DELETE /product/{id}`  
  Eliminación de producto por ID.

---

### 2. API de categorías
- `GET /category`  
  Listado de categorías disponibles.

---

### 3. Integración externa
Importación y adaptación del endpoint:
```bash
https://fakestoreapi.com/products/category/electronics
```


Esto permite:
- Poblar categorías automáticamente
- Asegurar datos reales durante la ejecución

---

### 4. Persistencia en memoria (H2)
- Base 100% en memoria
- No requiere instalación externa
- Generación automática de tablas con JPA

---

### 5. Documentación con Swagger / OpenAPI
- Esquema completo
- Ejemplo de respuestas
- Visualización de modelos

---

### 6. Manejo centralizado de errores
- Excepciones personalizadas
- Validaciones
- Respuestas JSON normalizadas

---

### 7. Cache selectiva
- Cache para listados de productos
- Invalidation al modificar datos

---

### 8. Tests unitarios
Incluye:
- Tests de services
- Tests de controllers
- Mocks de repositorios

---

### 9. Documentación del proyecto
- README principal
- CHANGELOG
- Estructura del proyecto
- Objetivos
- Alcance

---

# ❌ Fuera de Alcance (Out of Scope)

### ❌ No se desarrolla interfaz gráfica
Ni web, ni desktop, ni móvil.  
El sistema es **solo API**.

### ❌ No se usa ninguna base de datos externa
Nada de PostgreSQL, MySQL, MongoDB, etc.

### ❌ No se implementa autenticación
Ni roles, ni JWT, ni OAuth2.

### ❌ No se implementa seguridad avanzada
No se requiere:
- CORS específico  
- Rate limiting  
- Seguridad por token  
- HTTPS

### ❌ No se crea infraestructura cloud
Nada de:
- AWS
- Docker
- Kubernetes
- CI/CD

### ❌ No se crean tests de integración completos
Solo unitarios.

### ❌ No se hace importación dinámica continua de datos externos
Solo se consume el endpoint de categorías.

---

# Conclusión

El alcance cubre **todo lo que pide el desafío**, sin agregar complejidad innecesaria.  
El proyecto se mantiene:

- Claro
- Mantenible
- Fácil de evaluar  

Y totalmente alineado con los requerimientos oficiales.
