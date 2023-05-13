
import io.restassured.response.ValidatableResponse;
import org.example.Courier;
import org.example.CourierClient;
import org.example.CourierCredentials;
import org.example.CourierGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;

public class CourierTest {
    private Courier courier;
    private CourierClient courierClient;
    private Courier courierPasswordRandom;
    private int id;

    @Before
    public void setUp(){
        courierClient = new CourierClient();
        courier = CourierGenerator.getDefault();
        courierPasswordRandom = CourierGenerator.getPasswordRandom();
    }

    @After
    public void cleanUp(){
        courierClient.delete(id);
    }

    @Test
    public void courierCanBeCreated(){ //создание курьера
        ValidatableResponse response = courierClient.create(courier);//создай курьера
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));//авторизация
        id = loginResponse.extract().path("id");//тащит айди
        int statusCode = response.extract().statusCode();
        assertEquals(SC_OK, statusCode);
    }

    @Test
    public void createCourierDoubleError(){ //два одинаковых курьера
        ValidatableResponse response = courierClient.create(courier);
        ValidatableResponse responseSecond = courierClient.create(courier);
        int statusCode = responseSecond.extract().statusCode();
        assertEquals(SC_CONFLICT, statusCode);
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));//авторизация
        id = loginResponse.extract().path("id");//тащит айди
    }

    @Test
    public void createCourierSameLoginError(){ //создать два курьера с одинаковым логином
        ValidatableResponse response = courierClient.create(courierPasswordRandom);
        ValidatableResponse responseSecond = courierClient.create(courierPasswordRandom);
        int statusCode = responseSecond.extract().statusCode();
        assertEquals(SC_CONFLICT, statusCode);
    }

    @Test
    public void courierWithoutLoginError(){//нельзя создать без какого то поля
        ValidatableResponse response = courierClient.create(CourierGenerator.getLoginNull());
        int statusCode = response.extract().statusCode();
        assertEquals(SC_BAD_REQUEST, statusCode);
    }
}

