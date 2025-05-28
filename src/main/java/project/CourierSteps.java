package project;

import dto.Courier;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;


public class CourierSteps extends BaseApi {
    @Step("Вызов ручки на создание курьера")
    public ValidatableResponse createCourier(String login, String password){
        Courier courier = new Courier();
        courier.setLogin(login);
        courier.setPassword(password);

        return requestSpecification
                .body(courier)
                .when()
                .post("/api/v1/courier")
                .then();
    }
    @Step("Вызов ручки на получение id курьера")
    public ValidatableResponse loginCourier(String login, String password){
        Courier courier = new Courier();
        courier.setLogin(login);
        courier.setPassword(password);

        return requestSpecification
                .body(courier)
                .when()
                .post("/api/v1/courier/login")
                .then();
    }
    @Step("Вызов ручки на удаление курьера")
    public void deleteCourier(int id){
        requestSpecification
                .pathParam("id", id)
                .when()
                .delete("/api/v1/courier/{id}")
                .then();
    }
}
