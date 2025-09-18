import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.* ;

public class ReqresInTests {

        /*
        1. Make request (POST) to https://reqres.in/api/login with body { "email": "eve.holt@reqres.in", "password": "cityslicka" }
        2. Get response { "token": "QpwL5tke4Pnpja7X4" }
        3. Check token is QpwL5tke4Pnpja7X4
     */
    @Test
    void successfulLoginTest() {
        String body = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\" }";

        given()
                .log().uri()
                .header("x-api-key", "reqres-free-v1")
                .contentType(JSON)
                .body("{\"email\":\"eve.holt@reqres.in\",\"password\":\"cityslicka\"}")
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void unSuccessfulLoginWithMissingEmailTest() {
        String body = "{ \"password\": \"cityslicka\" }";

        given()
                .log().uri()
                .header("x-api-key", "reqres-free-v1")
                .body(body)
                .contentType(JSON)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing email or username"));
    }

    @Test
    void unSuccessfulLoginWithMissingPasswordTest() {
        String body = "{ \"email\": \"eve.holt@reqres.in\" }";

        given()
                .log().uri()
                .header("x-api-key", "reqres-free-v1")
                .body(body)
                .contentType(JSON)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    void unSuccessfulLoginWithEmptyDataTest() {
        given()
                .log().uri()
                .header("x-api-key", "reqres-free-v1")
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(415);
    }
    @Test
    void successfulDeleteUserTest() {
        given()
                .log().uri()
                .header("x-api-key", "reqres-free-v1") // если требуется
                .contentType(JSON)
                .when()
                .delete("https://reqres.in/api/users/2")
                .then()
                .log().status()
                .statusCode(204); // Проверяем, что статус 204
    }
    @Test
    void getUnknownResourcesTest() {
        given()
                .log().uri()
                .header("x-api-key", "reqres-free-v1") // если требуется
                .when()
                .get("https://reqres.in/api/unknown")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data", hasSize(6)) // проверяем, что в data 6 элементов
                .body("data[0].id", equalTo(1)) // проверяем id первого элемента
                .body("data[0].name", equalTo("cerulean")) // проверяем имя первого элемента
                .body("support.url", notNullValue()); // проверяем, что поле support.url есть
    }

}