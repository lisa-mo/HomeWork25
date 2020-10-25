import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$$;

//Write test using bare Selenide which will:
//        1) Navigate to airline site
//        2) Input "Vienna" in FROM field
//        3) Input "Kyiv" in TO field
//        4) Choose dates: 19-22 Nov
//        5) Choose 2 adults
//        6) Click search
//        7) Verify that there are 2 blocks: Vienna-Kyiv, Kyiv-Vienna
//        8) Verify dates are 19 Nov and 22 Nov accordingly

public class FlyuiaTest {
    @Test
    public void searchTicketsFlyuia() {
        Configuration.baseUrl = "https://www.flyuia.com/";
        Configuration.timeout = 120000;
        Configuration.startMaximized = true;

        String findInputDepart = "input[placeholder='Departure']";
        String cityDepart = "Vienna";
        String findInputArrival= "input[placeholder='Arrival']";
        String cityArrival = "Kyiv";
        String clickAside = "input[placeholder='PROMO CODE']";

        open("/");

        $("span[class='country-chooser__country']").click();
        $(By.name("lang_chooser_input")).click();
        $("a[href='/ua/en/home']").click();
        $("button[class='btn-v2 btn-blue btn-small country-chooser__btn']").click();

        inputFillIn(findInputDepart, cityDepart, findInputArrival, cityArrival, clickAside);

        $$("div[class=value-as-text-container]").get(2).click();
        $("i[class=obe-sw-icon-navigate_next]").click();
        $x("//button[contains(text(),'19')]").click();
        $$("div[class=value-as-text-container]").get(3).click();
        $x("//button[contains(text(),'22')]").click();

        $x("//div[contains(text(),'Passenger')]").click();
        $$x("//button[contains(text(),'+')]").get(0).click();

        $("form[class=search-flights-button]").doubleClick();

        switchTo().window(1);

        $("i[class=icon-close]").click();

        $$("div[class=product__title]").shouldHaveSize(2);
        $$("div[class=date-title]").get(6).shouldHave(Condition.text("Thu, 19 Nov"));
        $$("div[class=date-title]").get(19).shouldHave(Condition.text("Sun, 22 Nov"));
        $$("div[class=search-details__item--text]").get(1).shouldHave(Condition.text("Thu, 19 Nov - Sun, 22 Nov"));
    }

    public void inputFillIn(String findInputDepart, String cityDepart, String findInputArrival, String cityArrival, String clickAside) {
        for (int i = 0; i < 3; i++) {
            $(findInputDepart).val(cityDepart).pressEnter();
            $(clickAside).contextClick();
            $(findInputArrival).val(cityArrival).pressEnter();
            $(clickAside).contextClick();
        }
    }
}

