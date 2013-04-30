package tests.pages;

import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.pages.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MainPage extends Footer {
    @FindBy(linkText = "Find owner")
    private WebElement findOwnerLink;

    @FindBy(linkText = "Display all veterinarians")
    private WebElement allVeterinariansLink;

    @FindBy(linkText = "Tutorial")
    private WebElement tutorialLink;

    @FindBy(css = "div h2")
    private WebElement greeting;

    public MainPage(final WebDriver driver) {
        super(driver);
    }

    public void openVeterinariansPage() {
        clickOn(allVeterinariansLink);
        waitForAllTextToAppear("Veterinarians:");
    }

    public void openPetOwnersSearchPage() {
        clickOn(findOwnerLink);
        waitForAllTextToAppear("Find Owners:");
    }

    public void openTutorialPage() {
        clickOn(tutorialLink);
        waitForAllTextToAppear("Introduction");
    }

    public boolean isGreetingSameWith(String text) {
        return greeting.getText().trim().equals(text);
    }
}
