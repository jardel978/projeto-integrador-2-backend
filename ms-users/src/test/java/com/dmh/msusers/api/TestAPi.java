package com.dmh.msusers.api;

import com.google.gson.Gson;
import io.restassured.RestAssured;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class TestAPi {

    private Object token;

    @BeforeEach
    public void setUp() throws InterruptedException {
        RestAssured.baseURI = "http://localhost:8090/users/login";
        Map<String, Object> data = new HashMap<>();
        data.put("email", "marcushissss@shipanu.com");
        data.put("password","sinixitrumeiximu");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(data)
                .and()
                .when()
                .post("http://localhost:8090/users/login")     //confirmar a URL
                .then()
                .extract().response();

        Map<String, Map<String, Object>> responseData =  response.getBody().as(Map.class);
        token = responseData.get("data").get("access_token");
        System.out.println("token: " + token);

    }

    @Test

    @DisplayName("Create new user test")
    public void validateStatusCodeRegistrate() {
        Map<String, Object> data = new HashMap<>();

        data.put("name","Marcone");
        data.put("lastName","Shipano");
        data.put("phone","21 998877665");
        data.put("cpf","12345678900");
        data.put("email","marcansjfo@shipano.com");
        data.put("password","sinishitru");

        //Given
        Response response = given()
                .contentType(ContentType.JSON)
                .body(new Gson().toJson(data))
                .and()
                .when()
                .post("http://localhost:8090/users/registration")
                .then()
                .extract().response();

        //when
        int statusCode = response.getStatusCode();
        System.out.println(statusCode);

        //then
        assertEquals(HttpStatus.SC_CREATED, statusCode);

    }

//    @Test
//    @Tag("GET ALL Test")
//    @DisplayName("GetAll Users Test")
//    public void validateStatusCodeGetAll() {
//
//        //given
//        Response response = RestAssured.get("http://localhost:8090/users/ "); // --->>>> INSERIR O ENDPOINT DE GET
//
//        //when
//        int statusCode = response.getStatusCode();
//        System.out.println("Status code : " + statusCode);
//
//        assertEquals(200, statusCode);
//
//    }

    @Test

    @DisplayName("Get User by ID")
    public void validateStatusCodeGet() {
        //given
        Response response = RestAssured.get("http://localhost:8090/users/9df2efb8-9b31-4fde-8f98-d25a0d6a661c"); // --->>>> CONFIRMAR O ENDPOINT DE GET

        //when
        int statusCode = response.getStatusCode();
        System.out.println("Status code : " + statusCode);

        assertEquals(HttpStatus.SC_OK, statusCode);
    }

    @Test

    @DisplayName("Patch User by ID")
    public void validateStatusCodePut() {
        //given
        Map<String, Object> data = new HashMap<>();
        data.put("name", "Marquituxi");
        data.put("lastName", "Shipano");
        data.put("phone", "21 707070707070");
        data.put("email", "marcushissss@shipanu.com");
        data.put("password", "sinixitrumeiximu");
        Response response = given()
                .header("Authorization" , "Bearer " + token)
                .contentType(ContentType.JSON)
                .and()
                .body(new Gson().toJson(data))
                .when()
                .patch("http://localhost:8090/users/9df2efb8-9b31-4fde-8f98-d25a0d6a661c") // --->>>> CONFIRMAR O ENDPOINT DE PATCH
                .then()
                .extract().response();

        //when
        int statusCode = response.getStatusCode();
        System.out.println("status code: " + statusCode);

        //then
        assertEquals(HttpStatus.SC_OK, statusCode);

    }

//    @Test
//    @Tag("DELETE User by ID Test")
//    @DisplayName("DELETE User by ID")
//    public void validateStatusCodeDelete() {
//
//        //given
//        Response response = RestAssured.delete("http://localhost:8080/ms_users/users/users/1");
//
//        //when
//        String body = response.getBody().asString();
//        System.out.println(body);
//
//        //then
//        assertEquals("ok", body);
//    }

}
