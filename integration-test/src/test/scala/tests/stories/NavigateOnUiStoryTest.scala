package tests.stories

import net.thucydides.core.annotations._
import org.openqa.selenium.WebDriver
import net.thucydides.core.pages.Pages
import org.junit.runner.RunWith
import net.thucydides.junit.runners.{ThucydidesRunner}
import tests.{ThucydidesSuite, PetClinic}
import org.junit.Test
import tests.steps.PetOwnerStepsEx
import org.scalatest._
import junit.JUnitSuite
import org.junit.runner.notification.RunNotifier
import org.junit.runners.model.{Statement, FrameworkMethod}

/**
 * User: Dmytro Makhno
 */
@RunWith(classOf[ThucydidesRunner])
@Story(classOf[PetClinic#Navigation#PetOwner])
class NavigateOnUiStoryTest extends JUnitSuite{
  @Managed var webdriver:WebDriver = _

  @ManagedPages var pages: Pages = _

  @Steps var petOwner:PetOwnerStepsEx = _

  @Issue("#4")
  @Test def pet_owner_is_able_to_enter_clinic() {
    petOwner.goes_start
    petOwner.verify_title
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

/*class ThucydidesRunnerEx(klass: Class[_]) extends ThucydidesRunner(klass) {
  /**
   * Runs the tests in the acceptance test case.
   */
  override def run(notifier: RunNotifier) {
    println("ThucydidesRunnerEx.run...")
    super.run(notifier)
    println("...ThucydidesRunnerEx.run")
  }

  protected override def runChild(method: FrameworkMethod, notifier: RunNotifier) {
    println("ThucydidesRunnerEx.runChild '%s'...".format(method.getName))
    super.runChild(method,notifier)
    println("...ThucydidesRunnerEx.runChild")
  }

  /**
   * Running a unit test, which represents a test scenario.
   */
  protected override def methodInvoker(method: FrameworkMethod, test: AnyRef): Statement = {
    println("ThucydidesRunnerEx.methodInvoker '%s'...".format(method.getName))
    val statement = super.methodInvoker(method,test)
    println("...ThucydidesRunnerEx.methodInvoker")
    statement
  }
}



class NavigateOnUiStoryTestSt extends FreeSpec with ThucydidesSuite {
  val klass = classOf[NavigateOnUiStoryTestSt]

  "parent scope" - {
    "child scope" - {
      "test 1" in {
        info("Test 1 Info")
      }
      "test 2" in {
        markup("Test 2 Markup")
      }
      behave like testN("3")
    }
  }


  class SequentialTest(alias:String) extends FreeSpec with CancelAfterFailure {//with ThucydidesSuite {
    "for %s".format(alias) - {
      "Phase 1" in {
        for (j <- 0 until 10) {
          //println("%s: working %s     Phase-1 %s".format(DateTime.now(),alias,j))
          //Thread.sleep(100)
        }
      }
      "Phase 2" in {
        for (j <- 0 until 10) {
          //println("%s: working %s     Phase-2 %s".format(DateTime.now(),alias,j))
          //Thread.sleep(100)
        }
      }
    }
  }
  override val nestedSuites = Vector(" Config1", " Config2").map(new SequentialTest(_))

  def testN(num:String) {
    "test "+num in {
      info("Some Test Info")
    }
  }
} */