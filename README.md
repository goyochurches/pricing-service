# 🛒 Pricing API - Prueba Técnica

## ✨ Visión General

Esta es una aplicación **Spring Boot** que implementa una **API REST** diseñada para la consulta de precios de productos en una plataforma de e-commerce (simulando el contexto Inditex/ZARA).

El objetivo principal es devolver el precio **aplicable** para un producto y una marca dados en una **fecha y hora específicas**, aplicando la lógica de **prioridad** necesaria para las diferentes tarifas o listas de precios.

---

## 🚀 Características Principales

* **API RESTful** para consultas de precios rápidas y precisas.
* **Base de Datos en Memoria (H2)** precargada con datos de prueba.
* Implementación con **JPA/Hibernate** para una gestión de datos robusta.
* **Validación de Entradas** de la API para garantizar la calidad de los datos.
* **Documentación de API interactiva** con Swagger/OpenAPI.
* **Cobertura de Pruebas exhaustiva** (unitarias y de integración).
* Soporte completo para **Docker** para un despliegue ágil.

---

## 🛠️ Stack Tecnológico

| Componente | Versión / Tecnología | Propósito |
| :--- | :--- | :--- |
| **Lenguaje** | **Java 21** | Programación principal. |
| **Framework** | **Spring Boot 3.4** | Arquitectura de la aplicación. |
| **Web / REST** | Spring Web MVC | REST endpoints |
| **Persistencia** | Spring Data JPA | Acceso y gestión de datos. |
| **Base de Datos** | **H2 Database** | Almacenamiento en memoria para pruebas. |
| **Build** | **Maven** | Gestión de dependencias y build. |
| **Testing** | **JUnit 5 / RestAssured** | Pruebas unitarias y de integración. |
| **Documentación** | **OpenAPI 3.0** | Documentación interactiva. |
| **Contenedores** | **Docker** | Empaquetado y despliegue. |

---

## 🌐 Endpoint Principal de la API

### Obtener Precio Aplicable

**`GET /api/prices?date={date}&productId={productId}&brandId={brandId}`**

| Parámetro | Tipo | Requerido | Formato / Ejemplo | Descripción |
| :--- | :--- | :--- | :--- | :--- |
| `date` | `string` | **Sí** | `2020-06-14T10:00:00` | Fecha y hora de la aplicación (ISO-8601). |
| `productId` | `long` | **Sí** | `35455` | Identificador del producto. |
| `brandId` | `long` | **Sí** | `1` | Identificador de la marca. |

#### **Ejemplo de Respuesta (JSON)**

```json
{
  "productId": 35455,
  "brandId": 1,
  "priceList": 1,
  "startDate": "2020-06-14T00:00:00",
  "endDate": "2020-12-31T23:59:59",
  "pricing": 35.50,
  "currency": "EUR"
}
```

## 📋 Esquema de la Base de Datos

**Tabla: PRICES**

| Columna | Tipo de Dato | Descripción |
| :--- | :--- | :--- |
| **ID** | `BIGINT` | Clave primaria. |
| **BRAND_ID** | `BIGINT` | Identificador de Marca (1 = ZARA). |
| **START_DATE** | `TIMESTAMP` | Inicio de validez. |
| **END_DATE** | `TIMESTAMP` | Fin de validez. |
| **PRICE_LIST** | `INT` | Identificador de la Tarifa. |
| **PRODUCT_ID** | `BIGINT` | Identificador del Producto. |
| **PRIORITY** | `INT` | **Criterio de desempate** (mayor número = mayor prioridad). |
| **PRICE** | `DECIMAL` | Precio de venta final. |
| **CURR** | `VARCHAR` | Código de la divisa (EUR). |

---

## 💻 Primeros Pasos

### Requisitos Previos

* Java 21 o superior.
* Maven 3.6 o superior.
* (Opcional) Docker.

### 1. Ejecución Local

1.  Clona el repositorio.
2.  Compila y empaqueta:
    ```bash
    mvn clean install
    ```
3.  Ejecuta la aplicación:
    ```bash
    mvn spring-boot:run
    ```

### 2. Verificación y Acceso

Una vez que la aplicación esté corriendo en **`http://localhost:8080`**:

* **API REST:** `http://localhost:8080/api/prices`
* **Swagger UI:** `http://localhost:8080/swagger-ui.html`
* **H2 Console (DB):** `http://localhost:8080/h2-console`
    * **JDBC URL:** `jdbc:h2:mem:pricingdb`
    * **Usuario:** `sa` (Contraseña: vacía)

---

## 🐳 Ejecución con Docker

### 1. Generar JAR

Asegúrate de tener el JAR ejecutable:

```bash
mvn clean package -DskipTests
```

### 3. Construir y Ejecutar

```bash
docker build -t pricing-service .
docker run -p 8080:8080 pricing-service
```

## 🧪 Ejecución test
Ejecuta todas las pruebas (unitarias y de integración):

```bash
mvn test
```