package org.example;

import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;
import static org.example.Client.getSpec;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class OrdersClient {
    private static final String PATH = "/api/v1/orders";

    public ValidatableResponse showList(Orders orders){
        return given().log().all()
                .spec(getSpec())
                .get(PATH)
                .then().assertThat().body( "orders", notNullValue()).log().all();
    }
}
