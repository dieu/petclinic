package tests.stories;

import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.ManagedPages;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Story;
import net.thucydides.core.annotations.Issue;
import net.thucydides.core.pages.Pages;
import net.thucydides.junit.runners.ThucydidesRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import tests.PetClinicApplication;
import tests.steps.PetOwnerSteps;

import static net.thucydides.core.matchers.BeanMatchers.the;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;

@RunWith(ThucydidesRunner.class)
@Story(PetClinicApplication.PetOwnerManagement.ViewInformationPertainingToPetOwner.class)
public class NavigateOnUIStoryTest {

    @Managed
    public WebDriver webDriver;

    @ManagedPages
    public Pages pages;

    @Steps
    public PetOwnerSteps steps;

    @Issue("#4")
    @Test
    public void petOwnerIsAbleToEnterClinic() {
        steps.openHomePage();
        steps.verifyTitle();
    }

    @Test
    public void petOwnerIsAbleToVisitOwnersArea() {
        steps.openPetOwnersSearchPage();
        steps.openHomePage();
    }

    @Test
    public void petOwnerIsAbleToVisitVetsArea() {
        steps.openVeterinariansPage();
        steps.openHomePage();
    }

    @Test
    public void petOwnerIsAbleToReadTutorial() {
        steps.openTutorialPage();
        steps.openHomePage();
    }
}
