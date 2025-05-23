package project;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest {

    CreateOrdersSteps createOrdersSteps = new CreateOrdersSteps();

    String firstName;
    String lastName;
    String address;
    String metroStation;
    String phone;
    String rentTime;
    String deliveryDate;
    String comment;
    private final String[] color;

    public CreateOrderTest(String[] color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{"BLACK", "GREY"}},
                {new String[]{"null"}},
        });
    }

    @Before
    public void setUp() {
        RestAssured.baseURI= "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Позитивная проверка на создание заказа с выбором цвета")
    public void responseShouldContentTrackNumber() {
        firstName = RandomStringUtils.randomAlphabetic(10);
        lastName = RandomStringUtils.randomAlphabetic(10);
        address = RandomStringUtils.randomAlphabetic(10);
        metroStation = RandomStringUtils.randomAlphabetic(10);
        phone = RandomStringUtils.randomNumeric(11);
        rentTime = RandomStringUtils.randomNumeric(1);
        deliveryDate = "2025-04-06";
        comment = RandomStringUtils.randomAlphabetic(10);

        createOrdersSteps.createOrder(firstName, lastName, address, metroStation,
                        phone, rentTime, deliveryDate, comment, color)
                .statusCode(201)
                .body("track", notNullValue());
    }
}
