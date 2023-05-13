import io.restassured.response.ValidatableResponse;
import org.example.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.junit.Assert.assertEquals;

public class CourierLoginTest {

    private Courier courierDefault;
    private Courier courierLoginNull;
    private Courier courierLoginRandom;
    private Courier courierPasswordRandom;
    private CourierClient courierClient;
    private int id;

    @Before
    public void setUp(){
        courierClient = new CourierClient();
        courierDefault = CourierGenerator.getDefault();
        courierLoginNull = CourierGenerator.getLoginNull();
        courierLoginRandom = CourierGenerator.getLoginRandom();
        courierPasswordRandom = CourierGenerator.getPasswordRandom();
    }

    @After
    public void cleanUp(){
        courierClient.delete(id);
    }

    @Test
    public void authorizationWithoutFieldError(){// отсутствует поле при авторизации
        ValidatableResponse response = courierClient.create(courierDefault);//создай курьера
        ValidatableResponse loginNullResponse = courierClient.login(CourierCredentials.from(courierLoginNull));
        int statusCode = loginNullResponse.extract().statusCode();
        assertEquals(SC_BAD_REQUEST, statusCode);
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courierDefault));//авторизация
        id = loginResponse.extract().path("id");
    }

    @Test
    public void authorizationNonExistentUserError(){ //несуществующий пользователь (рандом)
        ValidatableResponse response = courierClient.login(CourierCredentials.from(courierLoginRandom));
        int statusCode = response.extract().statusCode();
        assertEquals(SC_NOT_FOUND, statusCode);
    }

    @Test
    public void authorizationNonExistentPasswordError(){ //существующий логин и неверный пароль (рандом)
        ValidatableResponse response = courierClient.create(courierDefault);//создай курьера
        ValidatableResponse passwordResponse = courierClient.login(CourierCredentials.from(courierPasswordRandom));
        int statusCode = passwordResponse.extract().statusCode();
        assertEquals(SC_NOT_FOUND, statusCode);
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courierDefault));//авторизация
        id = loginResponse.extract().path("id");
    }
}

