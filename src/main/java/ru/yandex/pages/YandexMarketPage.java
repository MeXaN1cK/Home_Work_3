package ru.yandex.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.util.Objects;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.CollectionCondition.sizeLessThanOrEqual;
import static com.codeborne.selenide.Condition.*;
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

    @Step("Переходим в категорию Электроника")
    public void goToElectronic(){
        $x("//a[@href='/catalog--elektronika/54440']").pressEnter();
    }

    @Step("Переходим в категорию Смартфоны")
    public void goToSmartphones(){
        $x("//a[@href='/catalog--mobilnye-telefony/54726/list?hid=91491']").pressEnter();
    }

    @Step("Выбираем фильтры для поиска: Производитель {manufacturer}")
    public void addFilters(String manufacturer){
        if ($x("//legend[@class and contains(text(),'Производитель')]").is(visible)) {
            if ($x("//legend[@class and contains(text(),'Производитель')]/parent::*/child::*/li/child::*//*[contains(text(),'"+manufacturer+"')]/parent::*/parent::*/parent::*/child::*").is(enabled)) {
                $x("//legend[@class and contains(text(),'Производитель')]/parent::*/child::*/li/child::*//*[contains(text(),'" + manufacturer + "')]/parent::*/parent::*/parent::*/child::*").shouldBe(visible).click();
            } else {
                $x("//legend[@class and contains(text(),'Производитель')]/parent::*/child::*//*[contains(text(),'Показать всё')]").shouldBe(visible).pressEnter();
                $x("//legend[@class and contains(text(),'Производитель')]/parent::*/child::*/child::*/input").setValue(manufacturer);
                sleep(1500);
                $x("//legend[@class and contains(text(),'Производитель')]/parent::*/child::*/child::*/child::*//*[contains(text(),'"+manufacturer+"')]/parent::*/parent::*/div/span").shouldBe(visible).shouldBe(enabled).click();
            }
        }
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
        //sleep(timeout);
        $x("//div[@data-apiary-widget-id='/content/results']").shouldBe(enabled).shouldBe(visible);
        $x("//button[@type='button' and @aria-haspopup]").shouldBe(visible).click();
        $x("//button[@class and contains(text(),'Показывать по 12')]").shouldBe(visible).click();
        $x("//div[@data-apiary-widget-id='/content/results']").shouldBe(enabled).shouldBe(visible);
        //sleep(timeout);
        $$x("//div[@data-zone-name='snippetList']/article").shouldHave(sizeLessThanOrEqual(count));
    }

    @Step("Ищем в поиске первый элемент из списка результатов")
    public void searchFirstElement(){
        String firstSearchElementName = $x("//div[@data-apiary-widget-id='/content/results']//*/article[1]//*/a[@href and @title]").attr("title");
        $x("//input[@id='header-search']").setValue(firstSearchElementName).pressEnter();
        sleep(timeout);
        SelenideElement searchResult = $x("//div[@data-apiary-widget-id='/content/results']//*/article[1]//*/a[@href and @title]");
        searchResult.shouldHave(attribute("title",firstSearchElementName));
    }

    @Step("Проверяем результаты поиска по модели: {model} или производителю: {manufacturer}")
    public void checkResults(String model, String manufacturer){
        while ($x("//a[@aria-label='Следующая страница']").is(Condition.enabled)){
            ElementsCollection results = $$x("//div[@data-zone-name='snippetList']/article");
            results.forEach(el->
                    el.$x("child::*/child::*//*/a[@href and @title]").shouldHave(Condition.attributeMatching("title","(.*?)(?i)("+model+"|"+manufacturer+")(.*?)"))
            );
            $x("//a[@aria-label='Следующая страница']").pressEnter();
            $x("//div[@data-apiary-widget-id='/content/results']").shouldBe(enabled).shouldBe(visible);
        }
    }
    @Step("Проверяем результаты поиска по модели: {model} или производителю: {manufacturer}")
    public void checkResults(String manufacturer){
        while ($x("//a[@aria-label='Следующая страница']").is(Condition.enabled)){
            ElementsCollection results = $$x("//div[@data-zone-name='snippetList']/article");
            results.forEach(el->
                    el.$x("child::*/child::*//*/a[@href and @title]").shouldHave(Condition.attributeMatching("title","(.*?)(?i)("+manufacturer+")(.*?)"))
            );
            $x("//a[@aria-label='Следующая страница']").pressEnter();
            $x("//div[@data-apiary-widget-id='/content/results']").shouldBe(enabled).shouldBe(visible);
        }
    }
}
