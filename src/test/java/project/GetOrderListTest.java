package project;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.notNullValue;

public class GetOrderListTest {

    GetOrderListSteps getOrderListSteps = new GetOrderListSteps();

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Позитивная проверка на получение списка заказов")
    public void getOrderList() {
        getOrderListSteps.getOrderList()
                .statusCode(SC_OK)
                .body("orders", notNullValue());
    }
}
