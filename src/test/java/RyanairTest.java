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

public class RyanairTest {
    @Test
    public void searchTicketsRyanair() {
        Configuration.baseUrl = "https://www.ryanair.com/";
        Configuration.timeout = 20000;
//        Configuration.startMaximized = true;

        open("/");

        $("icon[class='language-selector__button-chevron ng-star-inserted']").click();
        $("a[href='/us/en']").click();
        $(By.id("input-button__departure")).clear();
        $(By.id("input-button__departure")).val("Vienna");
        $x("//span[contains(text(),'Vienna')]").click();
        $(By.id("input-button__destination")).val("Kyiv");
        $x("//span[contains(text(),'Kyiv')]").click();

        $("div[class='m-toggle__month m-toggle__month--after-selected']").click();

        $x("//div[@data-id='2020-11-19']").click();
        $x("//div[@data-id='2020-11-24']").click();

        $x("//div[contains(text(),'Adult')]").click();
        $$x("//div[@class='counter__button-wrapper--enabled']").first().click();

        $("button[data-ref=flight-search-widget__cta]").click();

        $("button[data-ref='cookie.accept-all']").click();
        $$("div[class=card-info__cols-container]").get(0).scrollIntoView(true);
        $$("div[class=card-info__cols-container]").shouldHaveSize(2);

        $$("span[class='date-item__day-of-month h4 date-item__day-of-month--selected']").get(0).shouldHave(Condition.text("19"));
        $$("span[class='date-item__month h4 date-item__month--selected']").get(0).shouldHave(Condition.text("Nov"));
        $$("span[class='date-item__day-of-month h4 date-item__day-of-month--selected']").get(1).shouldHave(Condition.text("24"));
        $$("span[class='date-item__month h4 date-item__month--selected']").get(1).shouldHave(Condition.text("Nov"));
    }
}
