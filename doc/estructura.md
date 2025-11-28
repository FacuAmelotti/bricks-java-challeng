# Estructura del Proyecto   
Arquitectura y organización de carpetas

Este documento explica **cómo está organizada la arquitectura del proyecto** y **qué rol cumple cada carpeta y componente**.  
El objetivo es mantener un código **claro, mantenible y escalable**, siguiendo buenas prácticas de diseño en aplicaciones Java / Spring Boot.

```bash
Controller → Service → Repository → Entity
   ↓           ↓         ↓          ↓
   API     →  Lógica  →  Datos →   BD
```
---

## Vista general de la estructura

```text
bricks-java-challeng/
├── README.md
├── build.gradle
├── settings.gradle
├── doc/
│   └── estructura.md
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── bricks
    │   │           └── challenge
    │   │               ├── BricksApplication.java
    │   │               ├── config/
    │   │               ├── controller/
    │   │               ├── dto/
    │   │               ├── entity/
    │   │               ├── exception/
    │   │               ├── integration/
    │   │               ├── mapper/
    │   │               ├── repository/
    │   │               └── service/
    │   └── resources/
    └── test
        ├── java
        │   └── com
        │       └── bricks
        │           └── challenge
        │               └── controller/
        └── service/
```

---
## Raiz del proyecto
```text
1) README.md  
   Documento principal del proyecto: descripción, stack, cómo ejecutar, endpoints principales, decisiones de diseño, etc.

2) build.gradle / settings.gradle  
   Configuración de Gradle: dependencias, plugins de Spring Boot, compilación y tests.

3) doc/  
   Carpeta para documentación interna del proyecto.

4) src/  
   Código fuente principal de la aplicación.

```

---
## Código fuente principal (src/main/java)
```text
*) com.bricks.challenge  
   Paquete raíz de la aplicación. Organiza el código por responsabilidades.

1) BricksApplication.java  
   Clase principal con @SpringBootApplication.
   Es el punto de entrada (método main) y donde se inicializa el contexto de Spring.
```

---
## Capas y responsabilidades
```text
1) config/ – Configuración de la aplicación
Contiene configuraciones transversales:
- CacheConfig (configuración del cache)
- OpenApiConfig (Swagger / OpenAPI)
- Configuraciones adicionales (CORS, beans, etc.)

2) controller/ – Capa de exposición (API REST)
Responsable de manejar las peticiones HTTP y devolver las respuestas.
Responsabilidades:
- Definir endpoints (@GetMapping, @PostMapping, etc.)
- Recibir parámetros de la URL, query params y cuerpos (@RequestBody)
- Devolver DTOs y status HTTP correctos
- Delegar la lógica en la capa service

Regla: los controllers coordinan, no piensan.

3) service/ – Lógica de negocio
Acá vive la lógica de la aplicación.
Responsabilidades:
- Implementar reglas de negocio
- Validar entidades (ej: categoría existente)
- Aplicar filtros a productos
- Gestionar paginación
- Usar cache cuando corresponda
- Coordinar acceso a repositorios
- Lanzar excepciones de negocio

Regla: los services encapsulan la lógica y pueden ser testeados sin HTTP.

4) repository/ – Acceso a datos
Interfaces de Spring Data JPA para acceder a la base de datos.
Responsabilidades:
- Acceso a la base de datos (findById, save, delete, etc.)
- Métodos derivados por nombre (ej: findByTitle)
- Aislar al resto de la app de la tecnología de persistencia

Regla: los repositorios no contienen lógica de negocio, solo persistencia.

5) entity/ – Modelo de dominio / Entidades JPA
Representan las tablas de la base de datos.
Responsabilidades:
- Mapear entidades a la base de datos (@Entity, @Id, @ManyToOne, etc.)
- Representar el modelo interno de negocio

Importante: las entidades NO se exponen por la API (para eso están los DTOs).


6)dto/ – Data Transfer Objects
Objetos que se usan para comunicar datos hacia/desde la API.

Estructura posible:
- dto/product/ProductRequest.java
- dto/product/ProductResponse.java
- dto/product/ProductFilter.java
- dto/category/CategoryResponse.java

Responsabilidades:
- Definir el formato de los JSON de entrada/salida
- Evitar exponer las entidades internas
- Facilitar validaciones (@NotNull, @Size, @Positive)

Ventaja: cambios en la BD no rompen el contrato de la API.


7) mapper/ – Conversión entre entidades y DTOs
Clases encargadas de transformar:
Product ↔ ProductRequest / ProductResponse
Category ↔ CategoryResponse

Responsabilidades:
- Transformar entidades en DTOs y viceversa
- Centralizar la conversión
- Evitar duplicar código en controllers/services


8) exception/ – Manejo de errores
Espacio dedicado a manejar errores de forma consistente.
Responsabilidades:
- Manejar excepciones globales (con @RestControllerAdvice)
- Transformar errores en respuestas JSON claras:
  - status
  - mensaje
  - path
  - timestamp
- Manejar errores de validación, JSON mal formado, entidad no encontrada


9)integration/ – Integraciones externas
Acá se maneja la integración con servicios externos, como la API de Fakestore.
Responsabilidades:
- Consumir APIs externas (ej: Fakestore)
- Transformar la respuesta externa en datos internos
- Mantener aislada la integración del resto del sistema

Regla: las integraciones NO van en controllers ni repositorios.

```

---
## Recursos
```text
Acá van los recursos no Java:
- application.yml  
  Configuración del servidor, JPA y H2 (base de datos en memoria).
  No se utiliza ningún motor SQL externo.

- data.sql (opcional)  
  Datos iniciales para la BD en memoria.

```

---
## Base de datos en memoria (H2)
```text
El proyecto utiliza H2 como base de datos en memoria.

Esto cumple con el requerimiento del desafío:
“usar una base de datos en memoria”.

- No requiere instalación.
- No persiste datos entre ejecuciones.
- Spring Boot crea y gestiona las tablas automáticamente mediante JPA.
- No se escribe SQL de manera manual.

```


---
## Tests (src/test/java)
```text
Estructura paralela a main/java:

controller/
- Tests de endpoints con MockMvc
- Verificar status, cuerpo, errores (400/404)

service/
- Tests de lógica pura
- Validaciones, filtros, excepciones

Objetivo: testear tanto la lógica como la API.
```

---
---
---
#  Resumen del funcionamiento general

La aplicación está construida siguiendo una arquitectura por capas.  
La idea es separar cada responsabilidad en distintos lugares para que el código sea fácil de leer, mantener y escalar.

Esta es la explicación simple de cómo funciona todo:

---

## 1. El usuario hace una petición (la API recibe algo)
Cuando alguien llama a nuestra API, por ejemplo:
### GET /product
### POST /product


la petición llega primero a la **capa Controller**.

---

## 2. El Controller interpreta la solicitud
El **Controller** es como un recepcionista:  
- Recibe la petición HTTP.  
- Lee parámetros, body, headers, etc.  
- No toma decisiones importantes.  
- Llama a la capa **Service** y espera su respuesta.

**El controller conecta la API con la lógica, nada más.**

---

## 3. El Service contiene la lógica de negocio
El **Service** es el “cerebro” del sistema.

Acá se decide:
- Qué validaciones hacer.
- Cómo buscar productos.
- Cómo aplicar filtros.
- Qué pasa si no existe un ID.
- Si hay que usar cache.
- Si se debe llamar a un repositorio o a una API externa.

Es donde vive la lógica real de la aplicación.  
**Si hay una regla, se pone acá.**

---

## 4. El Repository se encarga de hablar con la base de datos
El **Repository** es como un puente entre la aplicación y la base de datos en memoria (H2).

- No tiene lógica de negocio.
- Solo ejecuta operaciones de datos:
  - guardar
  - buscar
  - listar
  - eliminar

Gracias a Spring Data JPA, no necesitamos escribir consultas manuales.
Los repositorios manejan automáticamente las operaciones sobre la base de datos en memoria.

**El service le pide datos al repository, nunca el controller.**

---

## 5. Las Entities representan las tablas de la base de datos
Las **Entities** son clases que representan la estructura interna:

- `Product` → tabla product  
- `Category` → tabla category  

Contienen:
- Campos (name, price, stock…)  
- Relación entre productos y categorías

Son el modelo interno, pero no se exponen al cliente.

---

## 6. Los DTOs son los datos que se envían y reciben por la API
Los **DTOs** se usan para:
- Entrar a la API (ej: ProductRequest)  
- Salir de la API (ej: ProductResponse)

Esto evita exponer estructuras internas o sensibles.

Se usan para validar, ordenar y limpiar la información.

---

## 7. Los Mappers convierten Entity → DTO y DTO → Entity
Un **Mapper** transforma objetos internos en objetos públicos.

Ejemplo:
- `Product` (entity interna) → `ProductResponse` (lo que ve el cliente)

Esto mejora la organización y reduce código repetido.

---

## 8. La capa Exception maneja errores de una forma ordenada
Si algo sale mal:
- un ID no existe  
- falta un campo  
- algo está mal escrito

La aplicación lanza errores controlados.

`GlobalExceptionHandler` transforma esas excepciones en respuestas claras:

```json
{
  "status": 404,
  "error": "Product not found",
  "path": "/product/100"
}
```
Esto hace que la API sea profesional y consistente.

---

## 9. La capa Integration habla con APIs externas

Acá se maneja la comunicación con otros sistemas, como:
```bash
https://fakestoreapi.com/products/category/electronics
```

La idea es tenerlo separado para que:
- no rompa el resto del código
- sea fácil cambiar de proveedor externo


---

## 10. Config contiene configuraciones generales del proyecto

Incluye:

Configuración del cache

Configuración de Swagger/OpenAPI

Configuración de CORS o beans

Son ajustes globales de la aplicación.

---

## 11. Tests aseguran que todo funcione correctamente

Los tests prueban:

Controllers (endpoints)

Services (lógica)

Esto garantiza que no rompas nada sin darte cuenta.