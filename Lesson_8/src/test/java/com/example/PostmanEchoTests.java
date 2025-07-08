package com.example;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PostmanEchoTests {

    private final String BASE_URL = "https://postman-echo.com";

    @Test
    public void testGetMethod() {
        Response response = RestAssured
                .given()
                    .queryParam("foo", "bar")
                .when()
                    .get(BASE_URL + "/get")
                .then()
                    .statusCode(200)
                    .contentType(ContentType.JSON)
                    .body("args.foo", equalTo("bar"))
                    .extract().response();

        assertEquals("bar", response.jsonPath().getString("args.foo"));
    }

    @Test
    public void testPostMethod() {
        String body = "{\"name\": \"John\"}";

        Response response = RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .body(body)
                .when()
                    .post(BASE_URL + "/post")
                .then()
                    .statusCode(200)
                    .body("data.name", equalTo("John"))
                    .extract().response();

        assertEquals("John", response.jsonPath().getString("data.name"));
    }

    @Test
    public void testPutMethod() {
        String body = "{\"update\": \"value\"}";

        Response response = RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .body(body)
                .when()
                    .put(BASE_URL + "/put")
                .then()
                    .statusCode(200)
                    .body("data.update", equalTo("value"))
                    .extract().response();

        assertEquals("value", response.jsonPath().getString("data.update"));
    }
}