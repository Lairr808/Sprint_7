import io.restassured.response.ValidatableResponse;
import org.example.Courier;
import org.example.CourierClient;
import org.example.CourierCredentials;
import org.example.CourierGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;


public class OrdersReceivingTest {
    private Courier courier;
    private CourierClient courierClient;
    int id;
    int courierId;

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
    public void orderList(){ //получение списка заказов
        ValidatableResponse response = courierClient.create(courier);
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));
        courierId = loginResponse.extract().path("courierId");
        int statusCode = response.extract().statusCode();
        assertEquals(SC_OK, statusCode);
    }
}
