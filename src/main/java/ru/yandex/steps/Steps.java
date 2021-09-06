package ru.yandex.steps;

import io.qameta.allure.Step;
import ru.yandex.pages.YandexMarketPage;
import ru.yandex.pages.YandexPage;

public class Steps {
    private YandexPage yandexPage = new YandexPage();
    private YandexMarketPage yandexMarketPage = new YandexMarketPage();

    @Step("Открыть страницу Яндекс.Маркет")
    public void openMarketPage(){
        yandexPage.goToMarket();
    }

    @Step("Открыть категорию Компьютеры")
    public void openCatagoryComputers(){
        yandexMarketPage.goToComputers();
    }

    @Step("Открыть категорию Ноутбуки")
    public void openCatagoryLaptops(){
        yandexMarketPage.goToLaptops();
    }


}
