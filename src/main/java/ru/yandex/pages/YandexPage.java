package ru.yandex.pages;

import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class YandexPage {
    public void goToMarket() {
        $(By.xpath("//a[@href='https://market.yandex.ru/?clid=505&utm_source=main_stripe_big&wprid=1630926293.37539.85660.934374&src_pof=505&utm_source_service=morda']")).pressEnter();
    }
}
