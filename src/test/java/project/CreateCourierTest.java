package project;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;


public class CreateCourierTest {

    CourierSteps courierSteps = new CourierSteps();

    String login;
    String password;
    String firstName;

    @Before
    public void setUp() {
        RestAssured.baseURI= "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Создание курьера")
    @Description("Позитивная проверка на создание курьера с вводом валидных данных, ожидаем код 201")
    public void createOkTest(){
        login = RandomStringUtils.randomAlphabetic(10);
        password = RandomStringUtils.randomAlphabetic(10);
        firstName = RandomStringUtils.randomAlphabetic(10);

        courierSteps.createCourier(login, password, firstName)
                .statusCode(201)
                .body("ok", is(true));
    }

    @Test
    @DisplayName("Создание одинаковых курьеров")
    @Description("Негативная проверка на создание двух курьеров с одинаковым логином, ожидаем код 409")
    public void sameLoginTest(){
        login = RandomStringUtils.randomAlphabetic(10);
        password = RandomStringUtils.randomAlphabetic(10);
        firstName = RandomStringUtils.randomAlphabetic(10);

        courierSteps.createCourier(login, password, firstName);
        courierSteps.createCourier(login, password, firstName)
                .statusCode(409)
                .body("message", is("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Создание курьера без логина")
    @Description("Негативная проверка на создание курьера без ввода логина, ожидаем код 400")
    public void createWithoutLoginTest(){
        password = RandomStringUtils.randomAlphabetic(10);
        firstName = RandomStringUtils.randomAlphabetic(10);

        courierSteps.createCourier("", password, firstName)
                .statusCode(400)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    @Description("Негативная проверка на создание курьера без ввода пароля, ожидаем код 400")
    public void createWithoutPasswordTest(){
        login = RandomStringUtils.randomAlphabetic(10);
        firstName = RandomStringUtils.randomAlphabetic(10);

        courierSteps.createCourier(login, "", firstName)
                .statusCode(400)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }


    @After
    public void tearDown(){
        Integer id = courierSteps.getId(login, password).extract().path("id");
        if (id != null) {
            courierSteps.deleteCourier(id);
        }
    }
}
