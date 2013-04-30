package tests.steps;

import tests.pages.*;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.matchers.BeanMatcher;
import net.thucydides.core.pages.Pages;
import net.thucydides.core.steps.ScenarioSteps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PetOwnerSteps extends ScenarioSteps {

    public PetOwnerSteps(final Pages pages) {
        super(pages);
    }

    @Step
    public void openVeterinariansPage() {
        onMainPage().openVeterinariansPage();
    }

    @Step
    public void openPetOwnersSearchPage() {
        onMainPage().openPetOwnersSearchPage();
    }

    @Step
    public void searchPetOwnerByLastName(String lastName) {
        pages().currentPageAt(PetOwnersSearchPage.class)
                .searchForPetOwners(lastName);
    }

    @Step
    public void shouldBeListOfPredefinedVeterinarians(int expectedCount) {
        int existingCount = pages().currentPageAt(VeterinariansPage.class)
                .getAllExistingVeterinarians().size();
        assertEquals(expectedCount, existingCount);
    }

    @Step
    public void petOwnerWithFollowingDataShouldPresent(BeanMatcher... matchers) {
        assertTrue(pages().currentPageAt(PetOwnersPage.class).isPetOwnerFound(matchers));
    }

    private MainPage onMainPage() {
        MainPage mainPage = pages().get(MainPage.class);
        mainPage.open();
        return mainPage;
    }

    @Step
    public void openTutorialPage() {
        onMainPage().openTutorialPage();
    }

    @Step
    public void openHomePage() {
        pages().currentPageAt(Footer.class).openHomePage();
    }

    @Step
    public void verifyTitle() {
        assertTrue(onMainPage().isGreetingSameWith("Welcome"));
    }
}
