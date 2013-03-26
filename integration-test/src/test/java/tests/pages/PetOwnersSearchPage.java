package tests.pages;

import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.pages.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @author Alimenkou Mikalai
 * @version 1.0
 */
@DefaultUrl("/owners/search")
public class PetOwnersSearchPage extends PageObject {
    @FindBy(id = "lastName")
    private WebElement lastNameTextField;

    @FindBy(xpath = "//input[@value='Find Owners']")
    private WebElement searchOwnersButton;

    @FindBy(linkText = "Add Owner")
    private WebElement addOwnerLink;

    public PetOwnersSearchPage(final WebDriver driver) {
        super(driver);
    }

    public void searchForPetOwners(String ownerLastName) {
        enter(ownerLastName).into(lastNameTextField);
        clickOn(searchOwnersButton);
        waitForTextToDisappear("Find Owners:");
    }
}
