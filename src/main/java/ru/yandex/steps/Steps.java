package ru.yandex.steps;

import io.qameta.allure.Step;
import ru.yandex.pages.YandexPage;

public class Steps {
    @Step("Открыть страницу Яндекс.Маркет")
    public void openMarketPage(){
        YandexPage page = new YandexPage();
        page.goToMarket();
    }
    
}
