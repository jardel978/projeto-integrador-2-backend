package com.dmh.msaccounts.api.accounts;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class CardDelete {

    private Object token;

    @BeforeEach
    public void setUp() throws InterruptedException {
        RestAssured.baseURI = "http://localhost:8090/users/login";
        Map<String, Object> data = new HashMap<>();
        data.put("email", "marcushissss@shipanu.com");
        data.put("password", "sinixitrumeiximu");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(data)
                .and()
                .when()
                .post("http://localhost:8090/users/login")     //confirmar a URL
                .then()
                .extract().response();

        Map<String, Map<String, Object>> responseData = response.getBody().as(Map.class);
        token = responseData.get("data").get("access_token");
        System.out.println("token: " + token);

    }

    @Test
    @Tag("DELETE Card by ID Test")
    @DisplayName("DELETE Card by ID")
    public void givenCardIdInformation_whenReceived_thenDeleteCardFromDatabase() {

        //given
        Response response = RestAssured.delete("http://localhost:8080/accounts/1/cards/1");

        //when
        String body = response.getBody().asString();
        System.out.println(body);

        //then
        Assertions.assertEquals("ok", body);
    }
}
