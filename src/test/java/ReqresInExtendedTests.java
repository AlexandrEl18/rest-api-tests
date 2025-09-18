import io.qameta.allure.restassured.AllureRestAssured;
import models.lombok.LoginBodyLombokModel;
import models.lombok.LoginResponseLombokModel;
import models.pojo.LoginBodyPojoModel;
import models.pojo.LoginResponsePojoModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.hamcrest.Matchers.is;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.LoginSpec.loginRequestSpec;
import static specs.LoginSpec.loginResponseSpec;

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
//подключили allure
    @Test
    void successfulLoginWithLombokAllureTest() {
        LoginBodyLombokModel loginBody=new LoginBodyLombokModel();
        loginBody.setEmail("eve.holt@reqres.in");
        loginBody.setPassword("cityslicka");

        LoginResponseLombokModel response= given()
                .filter(new AllureRestAssured())
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
    // сделали отчет более красивым
    @Test
    void successfulLoginWithCustomLombokAllureTest() {
        LoginBodyLombokModel loginBody=new LoginBodyLombokModel();
        loginBody.setEmail("eve.holt@reqres.in");
        loginBody.setPassword("cityslicka");

        LoginResponseLombokModel response= given()
                .filter(withCustomTemplates())
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
    //добавили step-ики
    @Test
    void successfulLoginWithCustomLombokAllureStepsTest() {
        step("Prepare test");
        LoginBodyLombokModel loginBody=new LoginBodyLombokModel();
        loginBody.setEmail("eve.holt@reqres.in");
        loginBody.setPassword("cityslicka");

        LoginResponseLombokModel response= step("Make request",()->
        given()
                .filter(withCustomTemplates())
                .log().uri()
                .header("x-api-key", "reqres-free-v1")
                .contentType(JSON)
                .body(loginBody)
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(LoginResponseLombokModel.class));
        step("Verify response",()->
        //assertEquals("QpwL5tke4Pnpja7X4",response.getToken()); //проверка J-UNIT-5
                assertThat(response.getToken()).isEqualTo("QpwL5tke4Pnpja7X4"));// проверка assertj
//   ./gradlew test allureServe  для запуска через терминал
    }
    @Test
    void successfulLoginWithCustomLombokAllureStepsSpecTest() {
        step("Prepare test");
        LoginBodyLombokModel loginBody=new LoginBodyLombokModel();
        loginBody.setEmail("eve.holt@reqres.in");
        loginBody.setPassword("cityslicka");

        LoginResponseLombokModel response= step("Make request",()->
                given(loginRequestSpec)
                        //.spec(loginRequestSpec) так тоже можно
                        .body(loginBody)
                        .post()
                        .then()
                        .spec(loginResponseSpec)
                        .extract().as(LoginResponseLombokModel.class));
        step("Verify response",()->
                //assertEquals("QpwL5tke4Pnpja7X4",response.getToken()); //проверка J-UNIT-5
                assertThat(response.getToken()).isEqualTo("QpwL5tke4Pnpja7X4"));// проверка assertj
//   ./gradlew test allureServe  для запуска через терминал
    }

    @ParameterizedTest(name = "Login with email={0}, password={1}")
    @CsvSource(value = {"eve.holt@reqres.in:cityslicka",   // валидные данные
                "tEst:test",                       // невалидные данные
                "Java:java"                        // ещё один вариант
        }, delimiter = ':')
        void successfulLoginParametrizedTest(String login, String password) {
            step("Prepare login body", () -> {
                // создаём тело запроса через Lombok-модель
            });
            LoginBodyLombokModel loginBody = new LoginBodyLombokModel();
            loginBody.setEmail(login);
            loginBody.setPassword(password);

            LoginResponseLombokModel response = step("Make request", () ->
                    given(loginRequestSpec)
                            .body(loginBody)
                            .when()
                            .post()
                            .then()
                            .spec(loginResponseSpec)   // проверка кода 200 + наличие токена
                            .extract().as(LoginResponseLombokModel.class)
            );

            step("Verify response", () -> {
                assertThat(response.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");
            });
        }
        //вариант параметризованного теста с возможностью прокидывать данные в терминал
    @ParameterizedTest
    @ValueSource(strings = {"default"})
    void loginWithParamsFromTerminal(String ignored) {
        String email = System.getProperty("login", "eve.holt@reqres.in"); // дефолтное значение
        String password = System.getProperty("password", "cityslicka");

        LoginBodyLombokModel loginBody = new LoginBodyLombokModel();
        loginBody.setEmail(email);
        loginBody.setPassword(password);

        given(loginRequestSpec)
                .body(loginBody)
                .post()
                .then()
                .statusCode(200)
                .body("token", notNullValue());

//        ./gradlew test \
//        -Dlogin="eve.holt@reqres.in" \
//        -Dpassword="cityslicka" \
//        --tests "ReqresInExtendedTests.loginWithParamsFromTerminal"
    }
    }



