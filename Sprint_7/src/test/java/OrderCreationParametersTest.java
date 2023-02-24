
import io.restassured.response.ValidatableResponse;
import org.example.Order;
import org.example.OrderClient;
import org.example.OrderGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.junit.Assert.assertEquals;


@RunWith(Parameterized.class)
public class OrderCreationParametersTest {
    OrderClient orderClient;
    String[] color;

    @Before
    public void setUp(){
        OrderGenerator.getDefault(color).setColor(color);
    }

    public OrderCreationParametersTest(String[] color) {this.color = color;}

    @Parameterized.Parameters
    public static String[][][] setData(){
        return new String[][][]{
                {{"BLACK"}},
                {{"GREY"}},
                {{"BLACK", "GRAY"}},
                {{null}}
        };
    }

    @Test
    public void OrderCreationParameters(){
        Order order = OrderGenerator.getDefault(color);
        System.out.println(order);
        ValidatableResponse response = orderClient.create(order);
        int statusCode = response.extract().statusCode();
        assertEquals(SC_CREATED, statusCode);
    }
}


