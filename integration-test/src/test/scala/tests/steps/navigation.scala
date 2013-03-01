package tests.steps

import net.thucydides.core.pages.Pages
import net.thucydides.core.steps.ScenarioSteps
import net.thucydides.core.annotations.Step
import tests.pages._

/**
 * User: Dmytro Makhno
 */
class PetOwnerSteps(pages:Pages) extends ScenarioSteps(pages) {
  @Step
  def goes_start {
    val page = getPages.currentPageAt(classOf[HomePage])
    page.open()
  }

  @Step
  def goes_to_owners {
    val page = getPages.currentPageAt(classOf[HomePage])
    page.findOwnerLink.click()
  }

  @Step
  def goes_to_add_owner {
    val page = getPages.currentPageAt(classOf[OwnersPage])
    page.addOwnerLink.click()
  }

  @Step
  def goes_to_vets_list {
    val page = getPages.currentPageAt(classOf[HomePage])
    page.displayAllVeterinariansLink.click()
  }

  @Step
  def goes_to_tutorial {
    val page = getPages.currentPageAt(classOf[HomePage])
    page.tutorialLink.click()
  }

  @Step
  def goes_home {
    val page = getPages.currentPageAt(classOf[GeneralFooterPage])
    page.toHome
  }
}
