package project;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CreateOrdersSteps {
    @Step("Вызов ручки на создание заказа")
    public ValidatableResponse createOrder(String firstName, String lastName, String address, String metroStation,
                                           String phone, String rentTime, String deliveryDate, String comment, String[] color) {
        return given()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"firstName\": \"" + firstName + "\",\n" +
                        "    \"lastName\": \"" + lastName + "\",\n" +
                        "    \"address\": \"" + address + "\",\n" +
                        "    \"metroStation\": \"" + metroStation + "\",\n" +
                        "    \"phone\": \"" + phone + "\",\n" +
                        "    \"rentTime\": \"" + rentTime + "\",\n" +
                        "    \"deliveryDate\": \"" + deliveryDate + "\",\n" +
                        "    \"comment\": \"" + comment + "\",\n" +
                        "    \"color\": [\"" + String.join(",", color) + "\"]\n" +
                        "}")
                .when()
                .post("/api/v1/orders")
                .then();
    }
}
