
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
    private int id;

    @Before
    public void setUp(){
        courier = CourierGenerator.getDefault();
        courierClient = new CourierClient();
    }

    @After
    public void cleanUp(){
        if (id != 0 ){
            courierClient.delete(id);
        }
    }

    @Test
    public void courierCanBeCreated(){ //создание курьера и авторизация
        ValidatableResponse response = courierClient.create(courier);//создай курьера
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));//авторизация
        id = loginResponse.extract().path("id");//тащит id
        int statusCode = response.extract().statusCode();
        assertEquals(SC_OK, statusCode);
    }

    @Test
    public void createCourierDoubleError(){ //два одинаковых курьера
        ValidatableResponse response = courierClient.create(courier);
        ValidatableResponse responseSecond = courierClient.create(courier);
        int statusCodeSecond = responseSecond.extract().statusCode();
        assertEquals(SC_CONFLICT, statusCodeSecond);
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));
        id = loginResponse.extract().path("id");
    }

    @Test
    public void courierWithoutLoginError(){//нельзя без какого то поля
        ValidatableResponse response = courierClient.create(CourierGenerator.getLoginNull());
        int statusCode = response.extract().statusCode();
        assertEquals(SC_BAD_REQUEST, statusCode);
    }
}

