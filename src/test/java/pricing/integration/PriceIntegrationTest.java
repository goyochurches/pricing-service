package pricing.integration;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class PriceIntegrationTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.basePath = "/api/prices";
    }

    @Test
    @DisplayName("Test 1: Request at 10:00 on day 14 for product 35455 and brand 1 (ZARA)")
    void test1_requestAt10OnDay14() {
        given()
                .param("productId", 35455)
                .param("brandId", 1)
                .param("date", "2020-06-14-10.00.00")
                .contentType(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", greaterThan(0))
                .body("[0].brandId", equalTo(1))
                .body("[0].productId", equalTo(35455))
                .body("[0].priceList", equalTo(1))
                .body("[0].price", equalTo(35.50f));
    }
    @Test
    @DisplayName("Test 2: Request at 16:00 on day 14 - Multiple prices (priority 0 and 1)")
    void test2_requestAt16OnDay14() {
        given()
                .param("productId", 35455)
                .param("brandId", 1)
                .param("date", "2020-06-14-16.00.00")
                .contentType(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", equalTo(2))  // ← Espera 2 precios
                .body("[0].productId", equalTo(35455))
                .body("[1].productId", equalTo(35455));
    }

    @Test
    @DisplayName("Test 3: Request at 21:00 on day 14 for product 35455 and brand 1 (ZARA)")
    void test3_requestAt21OnDay14() {
        given()
                .param("productId", 35455)
                .param("brandId", 1)
                .param("date", "2020-06-14-21.00.00")
                .contentType(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", greaterThan(0))
                .body("[0].brandId", equalTo(1))
                .body("[0].productId", equalTo(35455))
                .body("[0].priceList", equalTo(1))
                .body("[0].price", equalTo(35.50f));
    }

    @Test
    @DisplayName("Test 4: Request at 10:00 on day 15 for product 35455 and brand 1 (ZARA)")
    void test4_requestAt10OnDay15() {
        given()
                .param("productId", 35455)
                .param("brandId", 1)
                .param("date", "2020-06-15-10.00.00")
                .contentType(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", greaterThan(0))
                .body("[0].brandId", equalTo(1))
                .body("[0].productId", equalTo(35455))
                .body("[0].priceList", equalTo(1))
                .body("[0].price", equalTo(35.50f));
    }

    @Test
    @DisplayName("Test 5: Request at 21:00 on day 16 for product 35455 and brand 1 (ZARA)")
    void test5_requestAt21OnDay16() {
        given()
                .param("productId", 35455)
                .param("brandId", 1)
                .param("date", "2020-06-16-21.00.00")
                .contentType(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", greaterThan(0))
                .body("[0].brandId", equalTo(1))
                .body("[0].productId", equalTo(35455))
                .body("[0].priceList", equalTo(1))
                .body("[0].price", equalTo(35.5f));
    }

    @Test
    @DisplayName("Test 6: Request with non-existent product should return 404")
    void test6_nonExistentProduct() {
        given()
                .param("productId", 99999)
                .param("brandId", 1)
                .param("date", "2020-06-14-10.00.00")
                .contentType(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("Test 7: Request with invalid date format should return 400")
    void test7_invalidDateFormat() {
        given()
                .param("productId", 35455)
                .param("brandId", 1)
                .param("date", "invalid-date")
                .contentType(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(400);
    }

    @Test
    @DisplayName("Test 8: Request with missing parameters should return 400")
    void test8_missingParameters() {
        given()
                .param("productId", 35455)
                .contentType(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(400);
    }
}