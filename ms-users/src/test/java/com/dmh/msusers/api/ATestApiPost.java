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

public class ATestApiPost {

    private Object token;
    @Test
    @DisplayName("Create new user test")
    public void givenNewInformationToTheEndpoint_whenReceived_thenTheDataIsRegisteredInTheDatabase() {
        Map<String, Object> data = new HashMap<>();

        data.put("name","Teste");
        data.put("lastName","Teste");
        data.put("phone","11 998877665");
        data.put("cpf","12345678900");
        data.put("email","teste@teste.com");
        data.put("password","123456");

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
}
