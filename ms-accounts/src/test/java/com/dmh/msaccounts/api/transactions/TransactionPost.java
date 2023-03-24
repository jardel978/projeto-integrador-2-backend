package com.dmh.msaccounts.api.transactions;

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

public class TransactionPost {


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
    @DisplayName("Create new Transaction test")
    public void givenCorrectLoginInformationAndCorrectTransactionData_whenReceivingCorrectlyFromTheServer_tehPerformsATransactionOnTheAccount() {
        Map<String, Object> data = new HashMap<>();

        data.put("value", "1000.00");
        data.put("transactionType","Deposit");
        data.put("description", "teste de transação");

        //Given
        Response response = given()
                .contentType(ContentType.JSON)
                .body(new Gson().toJson(data))
                .and()
                .when()
                .post("http://localhost:8090/accounts/{id}/transactions")
                .then()
                .extract().response();

        //when
        int statusCode = response.getStatusCode();
        System.out.println(statusCode);

        //then
        assertEquals(HttpStatus.SC_OK, statusCode);
    }


}
