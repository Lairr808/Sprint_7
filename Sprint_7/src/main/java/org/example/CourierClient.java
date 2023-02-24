package org.example;

import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierClient extends Client{

    private static final String PATH = "api/v1/courier";

    public ValidatableResponse create(Courier courier){//создать
        return given()
                .spec(getSpec())
                .body(courier)
                .when()
                .post(PATH)
                .then();
    }

    public ValidatableResponse delete(int id) {//удалить
        return given()
                .spec(getSpec()) //сайт
                .body(id) //тело запроса
                .when()
                .post(PATH + "/:id")
                .then();
    }

    public ValidatableResponse login(CourierCredentials credentials) {//войти
        return given()
                .spec(getSpec()) //сайт
                .body(credentials) //тело запроса
                .when()
                .post(PATH + "/login")
                .then();
    }
}

