package project;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CourierLoginTest {

    CourierSteps courierSteps = new CourierSteps();

    String login;
    String password;
    String firstName;

    @Before
    public void setUp() {
        RestAssured.baseURI= "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Получение id курьера")
    @Description("Позитивная проверка на получение id курьера с вводом валидных данных, ожидаем код 200")
    public void getLoginTest(){
        login = RandomStringUtils.randomAlphabetic(10);
        password = RandomStringUtils.randomAlphabetic(10);
        firstName = RandomStringUtils.randomAlphabetic(10);

        courierSteps.createCourier(login, password, firstName);
        courierSteps.getId(login, password)
                .statusCode(200)
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Получение id без логина")
    @Description("Негативная проверка на получение id курьера без ввода логина, ожидаем код 400")
    public void withoutLoginTest(){
        login = RandomStringUtils.randomAlphabetic(10);
        password = RandomStringUtils.randomAlphabetic(10);
        firstName = RandomStringUtils.randomAlphabetic(10);

        courierSteps.createCourier(login, password, firstName);
        courierSteps.getId("", password)
                .statusCode(400)
                .body("message", is("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Получение id без пароля")
    @Description("Негативная проверка на получение id курьера без ввода пароля, ожидаем код 400")
    public void withoutPasswordTest(){
        login = RandomStringUtils.randomAlphabetic(10);
        password = RandomStringUtils.randomAlphabetic(10);
        firstName = RandomStringUtils.randomAlphabetic(10);

        courierSteps.createCourier(login, password, firstName);
        courierSteps.getId(login, "")
                .statusCode(400)
                .body("message", is("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Получение id с некорректным логином")
    @Description("Негативная проверка на получение id курьера с вводом некорректного логина, ожидаем код 404")
    public void wrongLoginTest(){
        login = RandomStringUtils.randomAlphabetic(10);
        password = RandomStringUtils.randomAlphabetic(10);
        firstName = RandomStringUtils.randomAlphabetic(10);

        courierSteps.createCourier(login, password, firstName);
        courierSteps.getId("login", password)
                .statusCode(404)
                .body("message", is("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Получение id с некорректным паролем")
    @Description("Негативная проверка на получение id курьера с вводом некорректного пароля, ожидаем код 404")
    public void wrongPasswordTest(){
        login = RandomStringUtils.randomAlphabetic(10);
        password = RandomStringUtils.randomAlphabetic(10);
        firstName = RandomStringUtils.randomAlphabetic(10);

        courierSteps.createCourier(login, password, firstName);
        courierSteps.getId(login, "password")
                .statusCode(404)
                .body("message", is("Учетная запись не найдена"));
    }

    @After
    public void tearDown(){
        Integer id = courierSteps.getId(login, password).extract().path("id");
        courierSteps.deleteCourier(id);
    }
}
