package ru.yandex.pages;


import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$x;

public class YandexPage {
    @Step("Переходим на страницу Яндекс.Маркет")
    public void goToMarket() {
        $x("//a[@data-id='market']").shouldHave(text("Маркет")).click();
    }
}
