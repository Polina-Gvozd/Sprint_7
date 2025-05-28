package project;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;


public class GetOrderListSteps extends BaseApi {
    @Step("Вызов ручки получения списка заказов")
    public ValidatableResponse getOrderList() {
        return requestSpecification
                .when()
                .get("/api/v1/orders")
                .then();
    }
}
