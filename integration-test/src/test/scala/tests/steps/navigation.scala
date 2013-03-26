package tests.steps

import net.thucydides.core.pages.Pages
import net.thucydides.core.steps.ScenarioSteps
import net.thucydides.core.annotations.{Issue, Step}
import tests.pages._
import org.junit.Assert._
import org.hamcrest.CoreMatchers._

/**
 * User: Dmytro Makhno
 */
class PetOwnerStepsEx(pages:Pages) extends PetOwnerSteps(pages) {
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

  @Step
  def verify_title {
    val page = getPages.currentPageAt(classOf[HomePage])
    assertThat(page.greeting.getText.trim, is("Welcome"))
  }
}
