package project;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;

public class GetOrderListTest {

    GetOrderListSteps getOrderListSteps = new GetOrderListSteps();

    @Before
    public void setUp() {
        RestAssured.baseURI= "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Позитивная проверка на получение списка заказов")
    public void getOrderList() {
        getOrderListSteps.getOrderList()
                .statusCode(200)
                .body("orders", notNullValue());
    }
}
