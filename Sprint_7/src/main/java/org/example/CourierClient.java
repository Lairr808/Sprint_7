package org.example;

import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierClient extends Client{

    private static final String PATH = "/api/v1/courier";
    private static final String DEL_PATH = "/api/v1/courier/{id}";
    private static final String LOGIN_PATH = "/api/v1/courier/login";

    public ValidatableResponse create(Courier courier){//создать
        return given().log().all()
                .spec(getSpec())
                .body(courier)
                .when()
                .post(PATH)
                .then().log().all();
    }

    public ValidatableResponse delete(int id) {//удалить
        return given().log().all()
                .spec(getSpec())
                .pathParam("id",id)
                .body("")
                .when()
                .delete(DEL_PATH)
                .then().log().all();
    }

    public ValidatableResponse login(CourierCredentials credentials) {//войти
        return given().log().all()
                .spec(getSpec())
                .body(credentials) //тело запроса
                .when()
                .post(LOGIN_PATH)
                .then().log().all();
    }
}

