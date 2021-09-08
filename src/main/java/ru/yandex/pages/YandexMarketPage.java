package ru.yandex.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.util.Objects;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Configuration.timeout;
import static com.codeborne.selenide.Selenide.*;

public class YandexMarketPage {
    @Step("Переходим в категорию Компьютеры")
    public void goToComputers(){
        $x("//a[@href='/catalog--kompiuternaia-tekhnika/54425']").pressEnter();
    }

    @Step("Переходим в категорию Ноутбуки")
    public void goToLaptops(){
        $x("//a[@href='/catalog--noutbuki/54544/list?hid=91013']").pressEnter();
    }

    @Step("Выбираем фильтры для поиска: Производитель {manufacturer}, Цена от {from}, Цена до {to}")
    public void addFilters(String manufacturer, Integer from, Integer to){
        if ($x("//legend[@class and contains(text(),'Производитель')]").is(visible)) {
            $x("//legend[@class and contains(text(),'Производитель')]/parent::*/child::*/li/child::*//*[contains(text(),'"+manufacturer+"')]/parent::*/parent::*/parent::*/child::*").shouldBe(visible).click();
            if (!Objects.equals($x("//p[@data-range-input-type='from']/input").attr("value"), from.toString()) && !Objects.equals($x("//p[@data-range-input-type='to']/input").attr("value"), to.toString())) {
                $x("//p[@data-range-input-type='from']/input").shouldBe(visible).setValue(from.toString());
                $x("//p[@data-range-input-type='to']/input").shouldBe(visible).setValue(to.toString());
            }
        }
    }

    @Step("Ожидаем результатов поиска и проверяем на количество результатов {count}")
    public void waitSearchResult(Integer count){
        sleep(timeout);
        $x("//div[@data-apiary-widget-id='/content/results']").shouldBe(visible);
        $x("//button[@type='button' and @aria-haspopup]").shouldBe(visible).click();
        $x("//button[@class and contains(text(),'Показывать по 12')]").shouldBe(visible).click();
        sleep(timeout);
        $$x("//div[@data-zone-name='snippetList']/article").shouldHave(size(count));
    }

    @Step("Ищем в поиске первый элемент из списка результатов")
    public void searchFirstElement(){
        String firstSearchElementName = $x("//div[@data-apiary-widget-id='/content/results']//*/article[1]//*/a[@href and @title]").attr("title");
        $x("//input[@id='header-search']").setValue(firstSearchElementName).pressEnter();
        sleep(timeout);
        SelenideElement searchResult = $x("//div[@data-apiary-widget-id='/content/results']//*/article[1]//*/a[@href and @title]");
        searchResult.shouldHave(attribute("title",firstSearchElementName));
    }
}
