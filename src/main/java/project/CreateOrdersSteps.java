package project;

import dto.Order;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;


public class CreateOrdersSteps extends BaseApi {
    @Step("Вызов ручки на создание заказа")
    public ValidatableResponse createOrder(String firstName, String lastName, String address, String metroStation,
                                           String phone, String rentTime, String deliveryDate, String comment, String[] color) {
        Order order = new Order();
        order.setFirstName(firstName);
        order.setLastName(lastName);
        order.setAddress(address);
        order.setMetroStation(metroStation);
        order.setPhone(phone);
        order.setRentTime(rentTime);
        order.setDeliveryDate(deliveryDate);
        order.setComment(comment);
        order.setColor(color);

        return requestSpecification
                .body(order)
                .when()
                .post("/api/v1/orders")
                .then();
    }
}
