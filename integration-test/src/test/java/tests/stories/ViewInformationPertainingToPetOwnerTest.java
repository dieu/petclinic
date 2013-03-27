package tests.stories;

import org.scalatest.junit.JUnitSuite;
import tests.PetClinicApplication;
import tests.steps.PetOwnerSteps;
import net.thucydides.core.annotations.*;
import net.thucydides.core.pages.Pages;
import net.thucydides.junit.runners.ThucydidesRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import static net.thucydides.core.matchers.BeanMatchers.the;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;

/**
 * @author Alimenkou Mikalai
 * @version 1.0
 */
@RunWith(ThucydidesRunner.class)
@Story(PetClinicApplication.PetOwnerManagement.ViewInformationPertainingToPetOwner.class)
public class ViewInformationPertainingToPetOwnerTest extends JUnitSuite {
    @Managed
    public WebDriver webDriver;

    @ManagedPages
    public Pages pages;

    @Steps
    public PetOwnerSteps steps;

    @Test
    public void petOwnerCanBeSearchedByLastName() {
        steps.openPetOwnersSearchPage();
        steps.searchPetOwnerByLastName("Davis");
        steps.petOwnerWithFollowingDataShouldPresent(
                the("Name", is("Betty Davis")),
                the("Address", is("638 Cardinal Ave.")),
                the("City", is("Sun Prairie")),
                the("Telephone", startsWith("608")));
    }
}
