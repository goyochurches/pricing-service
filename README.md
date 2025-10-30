# 🛒 Pricing service
## 🚀 Main Features

* **RESTful API** for fast and accurate price queries.
* **In-memory Database (H2)** preloaded with test data.
* Implementation with **JPA/Hibernate** for robust data management.
* **Input Validation** to ensure data integrity.
* **Interactive API Documentation** with Swagger/OpenAPI.
* **Comprehensive Test Coverage** (unit and integration tests).
* Full **Docker** support for easy deployment.

---

## 🛠️ Tech Stack

| Component | Version / Technology | Purpose |
| :--- | :--- | :--- |
| **Language** | **Java 21** | Main programming language. |
| **Framework** | **Spring Boot 3.4** | Application architecture. |
| **Web / REST** | Spring Web MVC | REST endpoints. |
| **Persistence** | Spring Data JPA | Data access and management. |
| **Database** | **H2 Database** | In-memory storage for testing. |
| **Build Tool** | **Maven** | Dependency management and build. |
| **Testing** | **JUnit 5 / RestAssured** | Unit and integration testing. |
| **Documentation** | **OpenAPI 3.0** | Interactive API documentation. |
| **Containers** | **Docker** | Packaging and deployment. |

---

## 🌐 Main API Endpoint

### Get Applicable Price

**`GET /api/prices?date={date}&productId={productId}&brandId={brandId}`**

| Parameter | Type | Required | Format / Example | Description |
| :--- | :--- | :--- | :--- | :--- |
| `date` | `string` | **Yes** | `2020-06-14T10:00:00` | Application date and time (ISO-8601). |
| `productId` | `long` | **Yes** | `35455` | Product identifier. |
| `brandId` | `long` | **Yes** | `1` | Brand identifier. |

#### **Example Response (JSON)**

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

## 📋 Database Schema

**Tabla: PRICES**

| Column | Data Type | Description |
| :--- | :--- | :--- |
| **ID** | `BIGINT` | Primary key. |
| **BRAND_ID** | `BIGINT` | Brand identifier (1 = ZARA). |
| **START_DATE** | `TIMESTAMP` | Start date of validity period. |
| **END_DATE** | `TIMESTAMP` | End date of validity period. |
| **PRICE_LIST** | `INT` | Price list identifier. |
| **PRODUCT_ID** | `BIGINT` | Product identifier. |
| **PRIORITY** | `INT` | Tie-breaking criterion (higher value = higher priority). |
| **PRICE** | `DECIMAL` | Final sale price. |
| **CURR** | `VARCHAR` | Currency code (e.g., EUR). |

---

## 💻 Getting Started

### Prerequisites

* Java 21 or higher.
* Maven 3.6 or higher.
* (Optional) Docker.

### 1. 💻 Running Locally

1.  Clone the repository.
2.  Build and package:
    ```bash
    mvn clean install
    ```
3.  Run the application:
    ```bash
    mvn spring-boot:run
    ```

### 2. Verificación y Acceso

Once the application is running at **`http://localhost:8080`**:

* **API REST:** `http://localhost:8080/api/prices`
* **Swagger UI:** `http://localhost:8080/swagger-ui.html`
* **H2 Console (DB):** `http://localhost:8080/h2-console`
    * **JDBC URL:** `jdbc:h2:mem:pricingdb`
    * **Username:** `sa` (Password: empty)

---

## 🐳 Running with Docker

### 1. Build the JAR

Make sure you have the executable JAR:

```bash
mvn clean package -DskipTests
```

### 2. Build and Run

```bash
docker build -t pricing-service .
docker run -p 8080:8080 pricing-service
```

## 🧪 Run test
Run all tests (unit and integration)::

```bash
mvn test
```