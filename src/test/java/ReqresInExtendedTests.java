import models.lombok.LoginBodyLombokModel;
import models.lombok.LoginResponseLombokModel;
import models.pojo.LoginBodyPojoModel;
import models.pojo.LoginResponsePojoModel;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.hamcrest.Matchers.is;
import static org.assertj.core.api.Assertions.assertThat;



public class ReqresInExtendedTests {
    @Test
    void successfulLoginTest() {
        //шаблон-эталон
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
    ////////////////////////////////////////////////////////////////////////
    @Test
    void successfulLoginWithPojoTest() {
        //String body = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\" }";
        LoginBodyPojoModel loginBody=new LoginBodyPojoModel();
        loginBody.setEmail("eve.holt@reqres.in");
        loginBody.setPassword("cityslicka");


        LoginResponsePojoModel response= given()
                .log().uri()
                .header("x-api-key", "reqres-free-v1")
                .contentType(JSON)
                .body(loginBody)
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(LoginResponsePojoModel.class);

        //assertEquals("QpwL5tke4Pnpja7X4",response.getToken()); //проверка J-UNIT-5
        assertThat(response.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");// проверка assertj

    }
    @Test
    void successfulLoginWithLombokTest() {

        LoginBodyLombokModel loginBody=new LoginBodyLombokModel();
        loginBody.setEmail("eve.holt@reqres.in");
        loginBody.setPassword("cityslicka");


        LoginResponseLombokModel response= given()
                .log().uri()
                .header("x-api-key", "reqres-free-v1")
                .contentType(JSON)
                .body(loginBody)
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(LoginResponseLombokModel.class);

        //assertEquals("QpwL5tke4Pnpja7X4",response.getToken()); //проверка J-UNIT-5
        assertThat(response.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");// проверка assertj

    }
}
