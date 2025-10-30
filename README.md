Pricing API - Technical Test
============================

Overview
--------

This is a Spring Boot application that implements a REST API for querying product prices in the Inditex e-commerce platform. The API allows retrieving the applicable pricing for a given product at a specific date and time, taking into account different pricing lists and priorities.

Features
--------

*   RESTful API for pricing queries

*   H2 in-memory database with test data

*   JPA/Hibernate for data access

*   Input validation

*   API documentation with Swagger/OpenAPI

*   Comprehensive test coverage (unit and integration)

*   Docker support for easy deployment


Tech Stack
----------

*   **Java 21** - Programming language

*   **Spring Boot 3.4** - Application framework

    *   Spring Web MVC - REST endpoints

    *   Spring Data JPA - Data access

    *   Spring Validation - Input validation

*   **H2 Database** - In-memory database

*   **Maven** - Build tool

*   **JUnit 5** - Testing framework

*   **Lombok** - Boilerplate reduction

*   **OpenAPI 3.0** - API documentation

*   **Docker** - Containerization


API Endpoints
-------------

### Get Price

Plain textANTLR4BashCC#CSSCoffeeScriptCMakeDartDjangoDockerEJSErlangGitGoGraphQLGroovyHTMLJavaJavaScriptJSONJSXKotlinLaTeXLessLuaMakefileMarkdownMATLABMarkupObjective-CPerlPHPPowerShell.propertiesProtocol BuffersPythonRRubySass (Sass)Sass (Scss)SchemeSQLShellSwiftSVGTSXTypeScriptWebAssemblyYAMLXML`   GET /api/prices?date={date}&productId={productId}&brandId={brandId}   `

**Parameters:**

ParameterTypeRequiredDescriptiondatestringYesISO-8601 date-time (e.g., 2020-06-14T10:00:00)productIdlongYesProduct identifierbrandIdlongYesBrand identifier

**Example Response:**

Plain textANTLR4BashCC#CSSCoffeeScriptCMakeDartDjangoDockerEJSErlangGitGoGraphQLGroovyHTMLJavaJavaScriptJSONJSXKotlinLaTeXLessLuaMakefileMarkdownMATLABMarkupObjective-CPerlPHPPowerShell.propertiesProtocol BuffersPythonRRubySass (Sass)Sass (Scss)SchemeSQLShellSwiftSVGTSXTypeScriptWebAssemblyYAMLXML`   {    "productId": 35455,    "brandId": 1,    "priceList": 1,    "startDate": "2020-06-14T00:00:00",    "endDate": "2020-12-31T23:59:59",    "pricing": 35.50,    "currency": "EUR"  }   `

Database Schema
---------------

**Table: PRICES**

ColumnTypeDescriptionIDBIGINTPrimary keyBRAND\_IDBIGINTBrand identifier (1 = ZARA)START\_DATETIMESTAMPStart date of pricing validity periodEND\_DATETIMESTAMPEnd date of pricing validity periodPRICE\_LISTINTPrice list identifierPRODUCT\_IDBIGINTProduct identifierPRIORITYINTPriority for overlapping pricing rulesPRICEDECIMALFinal sale pricingCURRVARCHARCurrency code (e.g., EUR)

Getting Started
---------------

### Prerequisites

*   Java 21 or later

*   Maven 3.6 or later

*   (Optional) Docker if you want to run the containerized version


### Running the Application

1.  Clone the repository

2.  mvn clean install

3.  mvn spring-boot:run

4.  Access the API documentation at:[**http://localhost:8080/swagger-ui.html**](http://localhost:8080/swagger-ui.html)


Running with Docker
-------------------

### Build Docker Image

To build the Docker image for the application, use:

Plain textANTLR4BashCC#CSSCoffeeScriptCMakeDartDjangoDockerEJSErlangGitGoGraphQLGroovyHTMLJavaJavaScriptJSONJSXKotlinLaTeXLessLuaMakefileMarkdownMATLABMarkupObjective-CPerlPHPPowerShell.propertiesProtocol BuffersPythonRRubySass (Sass)Sass (Scss)SchemeSQLShellSwiftSVGTSXTypeScriptWebAssemblyYAMLXML`   docker build -t pricing-service .   `

### Run Container

To run the container:

Plain textANTLR4BashCC#CSSCoffeeScriptCMakeDartDjangoDockerEJSErlangGitGoGraphQLGroovyHTMLJavaJavaScriptJSONJSXKotlinLaTeXLessLuaMakefileMarkdownMATLABMarkupObjective-CPerlPHPPowerShell.propertiesProtocol BuffersPythonRRubySass (Sass)Sass (Scss)SchemeSQLShellSwiftSVGTSXTypeScriptWebAssemblyYAMLXML`   docker run -p 8080:8080 pricing-service   `

### Verify

Once running, you can access:

*   API: **http://localhost:8080/api/prices**

*   Swagger UI: [**http://localhost:8080/swagger-ui.html**](http://localhost:8080/swagger-ui.html)


### Example Dockerfile

Below is the Dockerfile used for this project:

Plain textANTLR4BashCC#CSSCoffeeScriptCMakeDartDjangoDockerEJSErlangGitGoGraphQLGroovyHTMLJavaJavaScriptJSONJSXKotlinLaTeXLessLuaMakefileMarkdownMATLABMarkupObjective-CPerlPHPPowerShell.propertiesProtocol BuffersPythonRRubySass (Sass)Sass (Scss)SchemeSQLShellSwiftSVGTSXTypeScriptWebAssemblyYAMLXML`   # Use an official OpenJDK image  FROM openjdk:21-jdk-slim  # Set working directory  WORKDIR /app  # Copy the built JAR file into the container  COPY target/pricing-service.jar app.jar  # Expose port 8080  EXPOSE 8080  # Run the application  ENTRYPOINT ["java", "-jar", "app.jar"]   `

To generate the pricing-service.jar file before building the image, make sure to run:

Plain textANTLR4BashCC#CSSCoffeeScriptCMakeDartDjangoDockerEJSErlangGitGoGraphQLGroovyHTMLJavaJavaScriptJSONJSXKotlinLaTeXLessLuaMakefileMarkdownMATLABMarkupObjective-CPerlPHPPowerShell.propertiesProtocol BuffersPythonRRubySass (Sass)Sass (Scss)SchemeSQLShellSwiftSVGTSXTypeScriptWebAssemblyYAMLXML`   mvn clean package -DskipTests   `

Testing
-------

Run all tests (unit and integration) with:

Plain textANTLR4BashCC#CSSCoffeeScriptCMakeDartDjangoDockerEJSErlangGitGoGraphQLGroovyHTMLJavaJavaScriptJSONJSXKotlinLaTeXLessLuaMakefileMarkdownMATLABMarkupObjective-CPerlPHPPowerShell.propertiesProtocol BuffersPythonRRubySass (Sass)Sass (Scss)SchemeSQLShellSwiftSVGTSXTypeScriptWebAssemblyYAMLXML`   mvn test   `

Database
--------

The application uses an in-memory **H2 database** with test data loaded at startup.The H2 console is available at:

*   **URL:** [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

*   **JDBC URL:** jdbc:h2:mem:pricingdb

*   **Username:** sa

*   **Password:** _(empty)_


Testing Details
---------------

*   **Unit tests** are located under src/test/java/...

*   **Integration tests** (e2e style using RestAssured) are under src/test/java/pricing/integration
