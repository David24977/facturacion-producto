# Facturación de Productos – API REST (Spring Boot)

## API REST desarrollada con Spring Boot para la gestión de facturas, clientes y productos, orientada a un sistema de facturación real con cálculo de IVA, búsquedas por fechas y relaciones Many-to-Many.

### El proyecto está diseñado con una arquitectura limpia, separación por capas y uso de DTOs, pensado como base para una futura interfaz frontend.

## Conceptos clave trabajados

- Arquitectura REST por capas (Controller / Service / Repository)

- Relaciones:

  - Cliente → Factura (ManyToOne)

  - Factura ↔ Producto (ManyToMany mediante entidad intermedia)

- Cálculo de totales e IVA en capa de servicio

- Gestión de fechas con LocalDateTime y búsquedas por rangos

- Uso de BigDecimal para importes monetarios

- DTOs diferenciados para:

  - resumen

  - detalle

  - exposición pública

- Manejo de errores con ResponseStatusException

- Transacciones con @Transactional

- Base de datos relacional (JPA / Hibernate)

## Modelo de datos

- Cliente

  - id

  - nombre

  - dni

  - email

- Factura

  - id

  - fecha (LocalDateTime)

  - iva

  - cliente

- Producto

  - id

  - nombre

  - precio

- FacturaProducto (entidad intermedia)

  - factura

  - producto

  - cantidad

  - precioUnitario

  - subtotal

## Diseño de facturación

- El IVA se define por factura

- El total se calcula como:

  - suma de subtotales

  - aplicación del IVA sobre el total

- No se almacena el total en base de datos:

  - se calcula dinámicamente en el service

  - evita inconsistencias

## Búsqueda por fechas

- Las facturas se almacenan con LocalDateTime

- Las búsquedas por fecha se realizan mediante rangos

- Un solo día se traduce internamente a:

  - 00:00 → 23:59

Esto permite búsquedas precisas sin perder la hora real de creación.

## Endpoints principales
### Clientes

- Crear cliente

- Listar clientes (datos públicos)

- Buscar por DNI

- Autocompletado por nombre o email

### Facturas

- Crear factura asociada a cliente

- Listar facturas (resumen)

- Obtener factura completa

- Eliminar factura

- Buscar facturas por rango de fechas

### Líneas de factura

- Añadir productos a una factura

- Modificar cantidad

- Eliminar línea

## Tecnologías utilizadas

- Java 17+

- Spring Boot

- Spring Data JPA

- Hibernate

- MySQL / PostgreSQL

- Maven

- OpenAPI / Swagger

- IntelliJ IDEA

## Estado del proyecto

- Backend funcional y probado
- API documentada con Swagger
- Preparado para integrar frontend (React u otro)
- Proyecto cerrado a nivel backend

### El frontend se implementará en una fase posterior.

## Notas finales

### Este proyecto se ha desarrollado con foco en backend, diseño de dominio y lógica de negocio, priorizando claridad, mantenibilidad y buenas prácticas sobre simplicidad extrema.

## Autor

David Ferrer Sapiña  
Técnico en Desarrollo de Aplicaciones Multiplataforma (DAM)
