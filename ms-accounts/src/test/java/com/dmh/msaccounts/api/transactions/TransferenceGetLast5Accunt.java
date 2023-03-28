package com.dmh.msaccounts.api.transactions;

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

public class TransferenceGetLast5Accunt {

    @BeforeEach
    public void setUp() throws InterruptedException {
        RestAssured.baseURI = "http://localhost:8090/users/login";
        Map<String, Object> data = new HashMap<>();
        data.put("email", "teste@teste.com");
        data.put("password", "123456");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(data)
                .and()
                .when()
                .post("http://localhost:8090/users/login")
                .then()
                .extract().response();

        Map<String, Map<String, Object>> responseData = response.getBody().as(Map.class);
        /* token = responseData.get("data").get("access_token");
        System.out.println("token: " + token);*/
    }

    @Test
    @DisplayName("Get last 10 Transferences")
    public void GivenInsertingTheDataCorrectly_whenIfEverythingIsCorrect_thenLookForTheTransfers() {

        //GIVEN
        Response response = RestAssured.get("http://localhost:8090/accounts/{id}/transferences/accounts");

        //WHEN
        int statusCode = response.getStatusCode();
        System.out.println("Status code: " + statusCode);

        //THEN
        assertEquals(HttpStatus.SC_OK, statusCode);
    }
}
