package ru.yandex;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import ru.yandex.pages.YandexMarketPage;
import ru.yandex.pages.YandexPage;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Configuration.timeout;
import static com.codeborne.selenide.Selenide.*;

public class Tests {

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
        Configuration.baseUrl="https://yandex.ru/";
    }

    @AfterEach
    public void close(){
        closeWebDriver();
    }

    @Test
    public void TestLaptops(){
        open(baseUrl,YandexPage.class)
            .goToMarket();

        switchTo().window(1);

        yandexMarketPage.goToComputers();
        yandexMarketPage.goToLaptops();
        yandexMarketPage.addFilters("Lenovo",10000,30000);
        yandexMarketPage.addFilters("HP",10000,30000);
        yandexMarketPage.waitSearchResult(12);
        yandexMarketPage.searchFirstElement();

    }

    @ParameterizedTest(name="{displayName} {arguments}")
    @CsvSource({"Apple","Google","HONOR","HUAWEI","Nokia","OnePlus","OPPO","realme","Samsung","vivo","Xiaomi","ZTE"})
    public void TestSmartphones(String manufacturer){
        open(baseUrl,YandexPage.class)
                .goToMarket();

        switchTo().window(1);

        yandexMarketPage.goToElectronic();
        yandexMarketPage.goToSmartphones();
        yandexMarketPage.addFilters(manufacturer);
        yandexMarketPage.waitSearchResult(12);
        yandexMarketPage.checkResults(manufacturer);
    }
}
