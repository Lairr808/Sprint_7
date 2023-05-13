import io.restassured.response.ValidatableResponse;
import org.example.*;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;

public class OrdersReceivingTest {
    OrdersClient ordersClient = new OrdersClient();
    Orders orders;

    @Test
    public void ordersList(){ //список заказов
        ValidatableResponse response = ordersClient.showList(orders);
        int statusCode = response.extract().statusCode();
        assertEquals(SC_OK, statusCode);
    }
}
