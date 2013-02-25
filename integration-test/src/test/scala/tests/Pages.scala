package tests

import org.openqa.selenium.support.FindBy
import org.openqa.selenium.WebElement

/**
 * User: Dmytro Makhno
 */
abstract class HomePage extends BrowserContext with Footer{

  @FindBy(xpath="//a[text()='Find owner']")
  var findOwnerLink:WebElement = null

  @FindBy(xpath="//a[text()='Display all veterinarians']")
  var displayAllVeterinariansLink:WebElement = null

  @FindBy(xpath="//a[text()='Tutorial']")
  var tutorialLink:WebElement = null

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

abstract class OwnersPage extends BrowserContext with Footer {
  @FindBy(xpath="//a[text()='Add Owner']")
  var addOwnerLink:WebElement = null

  @FindBy(name="lastName")
  var lastNameField:WebElement = null

  @FindBy(xpath ="//input[@type='submit']")
  var findOwnerButton:WebElement = null

  def toAddOwner {
    addOwnerLink.click()
  }
}

abstract class OwnersNewPage extends BrowserContext with Footer {

}

abstract class VetsPage extends BrowserContext with Footer {

}

abstract class TutorialPage extends BrowserContext with Footer {

}

trait Footer {
  this: BrowserContext =>
  @FindBy(xpath="//a[text()='Home']")
  var homeLink:WebElement = null

  def toHome {
    homeLink.click()
  }
}
