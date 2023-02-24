package org.example;

import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;
import static org.example.Client.getSpec;

public class OrdersClient {
    private static final String PATH = "api/v1/orders";

    public ValidatableResponse create(Orders orders){
        return given()
                .spec(getSpec())
                .body(orders)
                .when()
                .get(PATH)
                .then();

    }
}
