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

public class CTestApiUpdate {

    private Object token;

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "http://localhost:8090/users/login";
        Map<String, Object> data = new HashMap<>();
        data.put("email", "teste@teste.com");
        data.put("password","123456");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(data)
                .and()
                .when()
                .post("http://localhost:8090/users/login")
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
        data.put("name", "Tester");
        data.put("lastName", "QA");
        data.put("phone", "11 70707070");
        data.put("email", "tester@tester.com");
        data.put("password", "123456");
        Response response = given()
                .header("Authorization" , "Bearer " + token)
                .contentType(ContentType.JSON)
                .and()
                .body(new Gson().toJson(data))
                .when()
                .patch("http://localhost:8090/users/350995d5-9f31-4cd8-aef2-d79b52794a0") // --->>>> CONFIRMAR O ENDPOINT DE PATCH
                .then()
                .extract().response();

        //when
        int statusCode = response.getStatusCode();
        System.out.println("status code: " + statusCode);

        //then
        assertEquals(HttpStatus.SC_OK, statusCode);

    }

}
