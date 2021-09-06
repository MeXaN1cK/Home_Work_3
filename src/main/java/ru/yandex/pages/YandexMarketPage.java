package ru.yandex.pages;

import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class YandexMarketPage {
    public void goToComputers(){
        $(By.xpath("//a[@href='/catalog--kompiuternaia-tekhnika/54425']")).pressEnter();
    }
}
