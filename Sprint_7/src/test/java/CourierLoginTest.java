import io.restassured.response.ValidatableResponse;
import org.example.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.junit.Assert.assertEquals;

public class CourierLoginTest {

    private Courier courier;
    private CourierClient courierClient;
    private int id;

    @Before
    public void setUp(){
        courier = CourierGenerator.getDefault();
        courierClient = new CourierClient();
    }

    @After
    public void cleanUp(){
        courierClient.delete(id);
    }

    @Test
    public void authorizationWithoutFieldError(){// отсутствует поле
        ValidatableResponse response = courierClient.create(courier);
        ValidatableResponse loginResponse = (ValidatableResponse) CourierGenerator.getLoginNull();
        id = loginResponse.extract().path("id");
        int statusCode = response.extract().statusCode();
        assertEquals(SC_BAD_REQUEST, statusCode);
    }

    @Test
    public void authorizationNonExistentUserError(){ //несуществующий пользователь (рандом)
        ValidatableResponse response = (ValidatableResponse) CourierGenerator.getLoginRandom();
        int statusCode = response.extract().statusCode();
        assertEquals(SC_NOT_FOUND, statusCode);
    }

    @Test
    public void authorizationNonExistentPasswordError(){ //существующий логин и неверный пароль (рандом)
        ValidatableResponse response = courierClient.create(courier);//создай курьера
        ValidatableResponse loginResponse = (ValidatableResponse) CourierGenerator.getPasswordRandom();
        id = loginResponse.extract().path("id");
        int statusCode = response.extract().statusCode();
        assertEquals(SC_NOT_FOUND, statusCode);
    }
}

