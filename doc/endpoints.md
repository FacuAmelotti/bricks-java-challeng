# ENDPOINTS – Bricks Java Challenge

Base URL: `http://localhost:8080`

---

## Productos

GET     /api/products  
GET     /api/products/{id}  
| Parámetro  | Tipo     | Descripción                               | Ejemplo                     |
|-----------|----------|-------------------------------------------|-----------------------------|
| name      | String   | Filtra por nombre (contiene, case-insensitive) | `name=iphone`           |
| minPrice  | Decimal  | Precio mínimo                              | `minPrice=100`             |
| maxPrice  | Decimal  | Precio máximo                              | `maxPrice=500`             |
| categoryId| Long     | ID de categoría interna                    | `categoryId=1`             |
| minStock  | Integer  | Stock mínimo                               | `minStock=10`              |
| maxStock  | Integer  | Stock máximo                               | `maxStock=100`             |
| page      | Integer  | Número de página (0-based)                 | `page=0`                   |
| size      | Integer  | Tamaño de página                           | `size=10`                  |


POST    /api/products  
PUT     /api/products/{id}  
DELETE  /api/products/{id}  

# Importación desde FakeStore
GET     /api/external/products/electronics

---

## Categorías

GET     /category  
GET     /category/{id}
