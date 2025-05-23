package project;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierSteps {
    @Step("Вызов ручки на создание курьера")
    public ValidatableResponse createCourier(String login, String password, String firstName){
        return given()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"login\": \""+ login +"\",\n" +
                        "    \"password\": \""+ password +"\",\n" +
                        "    \"firstName\": \""+ firstName +"\"\n" +
                        "}")
                .when()
                .post("/api/v1/courier")
                .then();
    }
    @Step("Вызов ручки на получение id курьера")
    public ValidatableResponse getId(String login, String password){
        return given()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"login\": \""+ login +"\",\n" +
                        "    \"password\": \""+ password +"\"\n" +
                        "}")
                .when()
                .post("/api/v1/courier/login")
                .then();
    }
    @Step("Вызов ручки на удаление курьера")
    public void deleteCourier(int id){
        given()
                .contentType(ContentType.JSON)
                .pathParam("id", id)
                .when()
                .delete("/api/v1/courier/{id}")
                .then();
    }
}
