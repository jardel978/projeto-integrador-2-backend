package com.dmh.msusers.api;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestApiUpdate {

    private Object token;

    @BeforeEach
    public void setUp() {
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
    @DisplayName("Patch User by ID")
    public void givenUpdatedInformation_WhenSentWithTheUserId_thenTheDataIsUpdatedInTheRegister() {
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

}
