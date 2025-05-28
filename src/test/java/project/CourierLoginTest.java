package project;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CourierLoginTest {

    CourierSteps courierSteps = new CourierSteps();

    String login;
    String password;

    @Before
    public void setUp() {
        login = RandomStringUtils.randomAlphabetic(10);
        password = RandomStringUtils.randomAlphabetic(10);

        courierSteps.createCourier(login, password);
    }

    @Test
    @DisplayName("Получение id курьера")
    @Description("Позитивная проверка на получение id курьера с вводом валидных данных, ожидаем код 200")
    public void getLoginTest(){
        courierSteps.loginCourier(login, password)
                .statusCode(SC_OK)
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Получение id без логина")
    @Description("Негативная проверка на получение id курьера без ввода логина, ожидаем код 400")
    public void withoutLoginTest(){
        courierSteps.loginCourier(null, password)
                .statusCode(SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для входа"));
    }

    @Test //обнаружен баг
    @DisplayName("Получение id без пароля")
    @Description("Негативная проверка на получение id курьера без ввода пароля, ожидаем код 400")
    public void withoutPasswordTest(){
        courierSteps.loginCourier(login, null)
                .statusCode(SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Получение id с некорректным логином")
    @Description("Негативная проверка на получение id курьера с вводом некорректного логина, ожидаем код 404")
    public void wrongLoginTest(){
        courierSteps.loginCourier("login", password)
                .statusCode(SC_NOT_FOUND)
                .body("message", is("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Получение id с некорректным паролем")
    @Description("Негативная проверка на получение id курьера с вводом некорректного пароля, ожидаем код 404")
    public void wrongPasswordTest(){
        courierSteps.loginCourier(login, "password")
                .statusCode(SC_NOT_FOUND)
                .body("message", is("Учетная запись не найдена"));
    }

    @After
    public void tearDown(){
        Response loginResponse = courierSteps.loginCourier(login, password).extract().response();
        if (loginResponse.getStatusCode() == SC_OK) {
            Integer id = loginResponse.path("id");
            courierSteps.deleteCourier(id);
        }
    }
}
