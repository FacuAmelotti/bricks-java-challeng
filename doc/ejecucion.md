# Guía Completa de Ejecución

### Cómo iniciar el proyecto, cargar datos, testear y validar el funcionamiento del caché

Este documento explica paso a paso cómo ejecutar la API, importar datos
desde FakeStore, verificar las entidades internas, usar el caché de
Spring y testear todos los endpoints correctamente.

------------------------------------------------------------------------

# **1. Requisitos previos**

-   Java 17 instalado\
-   Gradle Wrapper incluido (`./gradlew`)\
-   Postman o REST Client\
-   Internet activo (para la importación externa)

------------------------------------------------------------------------

# **2. Levantar la API**

Desde la raíz del proyecto, ejecutar:

``` bash
./gradlew clean bootRun
```

Esperar el inicio completo.\
Deberías ver:

    Started BricksJavaChallengeApplication
    Tomcat started on port(s): 8080

------------------------------------------------------------------------

# **3. Importar productos externos (OBLIGATORIO)**

    GET http://localhost:8080/api/external/products/electronics

✔ Devuelve una lista de \~6 productos\
✔ Inserta categorías y productos en la DB\
✔ Invalida automáticamente el caché (`@CacheEvict`)

------------------------------------------------------------------------

# **4. Comprobar DB cargada**

    GET http://localhost:8080/api/products/1

✔ Debe devolver un producto real.

------------------------------------------------------------------------

# **5. Test del caché**

    GET http://localhost:8080/api/products

✔ Devuelve lista completa\
✔ Primera llamada muestra log `[CACHE TEST]`\
✔ Siguientes llamadas NO muestran log

------------------------------------------------------------------------

# **6. CRUD y cache invalidation**

### Crear

    POST /api/products

    {
    "name": "WD 1TB Portable SSD UltraFast Edition",
    "price": 149.99,
    "stock": 120,
    "categoryId": 1
    }


### Actualizar

    PUT /api/products/{id}

    {
    "name": "!!! PRODUCTO EDITADO POR FACU (SUPER TEST) !!!",
    "price": 777.77,
    "stock": 777,
    "categoryId": 1
    }

### Eliminar

    DELETE /api/products/{id}

✔ Cada operación invalida la caché.

------------------------------------------------------------------------

# **7. Categorías**

    GET /category
    GET /category/{id}

------------------------------------------------------------------------

# **8. Flujo correcto**

1.  Levantar API\
2.  Importar FakeStore\
3.  Listar productos\
4.  Validar cache\
5.  CRUD\
6.  Categorías


