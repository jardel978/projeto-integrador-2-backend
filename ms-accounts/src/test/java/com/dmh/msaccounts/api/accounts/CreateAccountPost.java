package com.dmh.msaccounts.api.accounts;

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


public class CreateAccountPost {

    @BeforeEach
    public void setUp() throws InterruptedException {
        RestAssured.baseURI = "http://localhost:8090/users/login";
        Map<String, Object> data = new HashMap<>();
        data.put("email", "TesteAccount@email.com");
        data.put("password","1234567");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(data)
                .and()
                .when()
                .post("http://localhost:8090/users/login")     //confirmar a URL
                .then()
                .extract().response();

        Map<String, Map<String, Object>> responseData =  response.getBody().as(Map.class);
        /* token = responseData.get("data").get("access_token");
        System.out.println("token: " + token);*/

    }

    @Test


    @DisplayName("Create new Account test")
    public void givenNewInformationToTheEndpoint_whenReceived_thenTheDataIsRegisteredInTheDatabase() {
        Map<String, Object> data = new HashMap<>();

        data.put("userId","kvbnjvnvnj");

        //Given
        Response response = given()
                .contentType(ContentType.JSON)
                .body(new Gson().toJson(data))
                .and()
                .when()
                .post("http://localhost:8090/account")
                .then()
                .extract().response();

        //when
        int statusCode = response.getStatusCode();
        System.out.println(statusCode);

        //then
        assertEquals(HttpStatus.SC_CREATED, statusCode);

    }



}
