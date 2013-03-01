package tests.pages

import org.openqa.selenium.support.FindBy
import org.openqa.selenium.{WebDriver, WebElement}
import net.thucydides.core.pages.PageObject

/**
 * User: Dmytro Makhno
 */
class HomePage(driver:WebDriver) extends PageObject(driver) with Footer{

  @FindBy(xpath="//a[text()='Find owner']")
  var findOwnerLink:WebElement = _

  @FindBy(xpath="//a[text()='Display all veterinarians']")
  var displayAllVeterinariansLink:WebElement = _

  @FindBy(xpath="//a[text()='Tutorial']")
  var tutorialLink:WebElement = _

  def toFindOwner {
    findOwnerLink.click()
  }
  def toDisplayAllVeterinarians {
    displayAllVeterinariansLink.click()
  }
  def toTutorial {
    tutorialLink.click()
  }

}

class OwnersPage(driver:WebDriver) extends PageObject(driver) with Footer {
  @FindBy(xpath="//a[text()='Add Owner']")
  var addOwnerLink:WebElement = _

  @FindBy(name="lastName")
  var lastNameField:WebElement = _

  @FindBy(xpath ="//input[@type='submit']")
  var findOwnerButton:WebElement = _

  def toAddOwner {
    addOwnerLink.click()
  }
}

class OwnersNewPage(driver:WebDriver) extends PageObject(driver) with Footer {

}

class VetsPage(driver:WebDriver) extends PageObject(driver) with Footer {

}

class TutorialPage(driver:WebDriver) extends PageObject(driver) with Footer {

}

trait Footer {
  @FindBy(xpath="//a[text()='Home']")
  var homeLink:WebElement = _

  def toHome {
    homeLink.click()
  }
}

/*
Used in rare cases where Actual page doesn't matter
 */
class GeneralFooterPage(driver:WebDriver) extends PageObject(driver) with Footer