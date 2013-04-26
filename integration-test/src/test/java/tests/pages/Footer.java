package tests.pages;

import net.thucydides.core.pages.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * User: Julia Astakhova
 * Date: 4/25/13
 */
public class Footer extends PageObject {

    public Footer(final WebDriver driver) {
        super(driver);
    }

    @FindBy(linkText = "Home")
    private WebElement homeLink;

    public void openHomePage() {
        clickOn(homeLink);
    }
}
