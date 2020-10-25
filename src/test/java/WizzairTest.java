import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.*;
//Write test using bare Selenide which will:
//        1) Navigate to airline site
//        2) Input "Vienna" in FROM field
//        3) Input "Kyiv" in TO field
//        4) Choose dates: 19-22 Nov
//        5) Choose 2 adults
//        6) Click search
//        7) Verify that there are 2 blocks: Vienna-Kyiv, Kyiv-Vienna
//        8) Verify dates are 19 Nov and 22 Nov accordingly

public class WizzairTest {

    @Test
    public void searchTicketsWizzair() {
        Configuration.baseUrl = "https://wizzair.com";
        Configuration.timeout = 40000;

        open("/");
        $(By.id("search-departure-station")).val("Vienna");
        $x("//mark[contains(text(),'Vienna')]").click();
        $(By.id("search-arrival-station")).val("Kyiv");
        $x("//mark[contains(text(),'Kyiv')]").click();

        $(By.id("search-departure-date")).click();
        $x("//button[@data-pika-year='2020'][@data-pika-month='10'][@data-pika-day='19']").click();
        $x("//button[@data-pika-year='2020'][@data-pika-month='10'][@data-pika-day='22']").click();
        $$x("//button[@class='base-button base-button--medium base-button--primary']").first().click();

        $(By.id("search-passenger")).click();
        $$x("//button[@class='stepper__button stepper__button--add']").first().click();
        $$x("//button[@class='base-button base-button--medium base-button--primary']").get(1).click();

        $(".flight-search__panel__cta-btn").click();

        switchTo().window(1);

        $$(".flight-select__fare-selector").shouldHaveSize(2);
        $$(".flight-select__flight-info__day").get(0).shouldHave(Condition.text(" Thu, 19 Nov 2020 "));
        $$(".flight-select__flight-info__day").get(1).shouldHave(Condition.text(" Sun, 22 Nov 2020 "));
    }
}


