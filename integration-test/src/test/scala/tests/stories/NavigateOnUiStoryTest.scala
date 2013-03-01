package tests.stories

import net.thucydides.core.annotations.{Steps, Story, ManagedPages, Managed}
import org.openqa.selenium.WebDriver
import net.thucydides.core.pages.Pages
import org.scalatest.junit.JUnitSuite
import org.junit.runner.RunWith
import net.thucydides.junit.runners.ThucydidesRunner
import tests.PetClinic
import org.junit.Test
import tests.steps.PetOwnerSteps

/**
 * User: Dmytro Makhno
 */
@RunWith(classOf[ThucydidesRunner])
@Story(classOf[PetClinic#Navigation#PetOwner])
class NavigateOnUiStoryTest{ //todo: find how to insert scalaTest extends JUnitSuite{
  @Managed var webdriver:WebDriver = _

  @ManagedPages var pages: Pages = _

  @Steps var petOwner:PetOwnerSteps = _

  @Test def pet_owner_is_able_to_enter_clinic() {
    petOwner.goes_start
  }

  @Test def pet_owner_is_able_to_visit_owners_area() {
    petOwner.goes_to_owners
    petOwner.goes_to_add_owner
    petOwner.goes_home
  }

  @Test def pet_owner_is_able_to_visit_vets_area() {
    petOwner.goes_to_vets_list
    petOwner.goes_home
  }

  @Test def pet_owner_is_able_to_read_tutorial() {
    petOwner.goes_to_tutorial
    petOwner.goes_home
  }
}
