import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SelenoidTests {

    /*
        1. Make request to https://selenoid.autotests.cloud/status
        2. Get response { total: 20, used: 0, queued: 0, pending: 0, browsers: {
                android: { 8.1: { } }, chrome: { 100.0: { }, 99.0: { } },
                chrome-mobile: { 86.0: { } }, firefox: { 97.0: { }, 98.0: { } }, opera: { 84.0: { }, 85.0: { } } } }
        3. Check total is 20
     */

    @Test
    void checkTotal() {
        get("https://selenoid.autotests.cloud/status")
                .then()
                .body("total", is(5));
    }

    @Test
    void checkWithGivenTotal() {
        given()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .body("total", is(5));
    }

    @Test
    void checkWithStatusTotal() {
        given()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .statusCode(200)
                .body("total", is(5));
    }

    @Test
    void checkWithAllLogsTotal() {
        given()
                .log().all()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .log().all()
                .statusCode(200)
                .body("total", is(5));
    }

    @Test
    void checkWithSomeLogsTotal() {
        given()
                .log().uri()
                .log().body()  // for post requests
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("total", is(5));
    }

    @Test
    void checkChromeVersion() {
        given()
                .log().uri()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("total", is(5))
                .body("browsers.chrome", hasKey("100.0"));
    }

    @Test
    void checkResponseBadPractice() { // BAD PRACTICE
        String expectedResponse = "{\"total\":5,\"used\":0,\"queued\":0,\"pending\":0,\"browsers\":{\"chrome\":{\"127.0\":{},\"128.0\":{}},\"firefox\":{\"124.0\":{},\"125.0\":{}},\"opera\":{\"108.0\":{},\"109.0\":{}}}}";

        String actualResponse = given()
                .log().uri()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().response().asString();

        // Убираем все пробелы, табы и переносы строк
        actualResponse = actualResponse.replaceAll("\\s+", "");

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void checkResponseGoodPractice() {
        Integer expectedTotal = 5;

        Integer actualTotal = given()
                .log().uri()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().path("total");

        assertEquals(expectedTotal, actualTotal);
    }

    @Test
    void checkJsonScheme() {
        given()
                .log().uri()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("total", is(5))
                .body("browsers.chrome", hasKey("127.0"))
                .body(matchesJsonSchemaInClasspath("schemes/status-scheme-responce.json"));
    }

}