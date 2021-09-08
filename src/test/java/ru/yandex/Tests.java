package ru.yandex;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import ru.yandex.pages.YandexMarketPage;
import ru.yandex.pages.YandexPage;

import static com.codeborne.selenide.Configuration.timeout;
import static com.codeborne.selenide.Selenide.*;

public class Tests {

    YandexPage yandexPage = new YandexPage();
    YandexMarketPage yandexMarketPage = new YandexMarketPage();

    @BeforeEach
    public void options(){
        timeout=6000;
        Configuration.browser="chrome";
        Configuration.startMaximized=true;

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-extensions");

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        Configuration.browserCapabilities = capabilities;
    }

    @Test
    public void TestLaptops(){
        open("https://yandex.ru/");
        yandexPage.goToMarket();
        switchTo().window(1);
        yandexMarketPage.goToComputers();
        yandexMarketPage.goToLaptops();
        yandexMarketPage.addFilters("Lenovo",10000,30000);
        yandexMarketPage.addFilters("HP",10000,30000);
        yandexMarketPage.waitSearchResult(12);
        yandexMarketPage.searchFirstElement();

    }
}
