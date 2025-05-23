package project;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class GetOrderListSteps {
    @Step("Вызов ручки получения списка заказов")
    public ValidatableResponse getOrderList() {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/v1/orders")
                .then();
    }
}
