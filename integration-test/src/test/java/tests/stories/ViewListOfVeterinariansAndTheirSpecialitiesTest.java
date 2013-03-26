package tests.stories;

import tests.PetClinicApplication;
import tests.steps.PetOwnerSteps;
import net.thucydides.core.annotations.*;
import net.thucydides.core.pages.Pages;
import net.thucydides.junit.runners.ThucydidesRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

/**
 * @author Alimenkou Mikalai
 * @version 1.0
 */
@RunWith(ThucydidesRunner.class)
@Story(PetClinicApplication.VeterinarianVisits.ViewListOfVeterinariansAndTheirSpecialities.class)
public class ViewListOfVeterinariansAndTheirSpecialitiesTest {
    @Managed
    public WebDriver webDriver;

    @ManagedPages
    public Pages pages;

    @Steps
    public PetOwnerSteps steps;

    @Test
    public void listOfVeterinariansIsPredefined() {
        steps.openVeterinariansPage();
        steps.shouldBeListOfPredefinedVeterinarians(6);
    }

    @Test
    @Pending
    public void listOfVeterinariansIsAvailableInXmlFormat() {}
}
