package tests

import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.PageFactory

/**
 * User: Dmytro Makhno
 */
case class Site(implicit driver: WebDriver, config:Infrastructure) {
  lazy val _driver = driver
  val _config = config
  def initPage[T](page:T) = {PageFactory.initElements(driver,page);page}


  class HomePageImpl extends HomePage {val driver = _driver; val config = _config; initPage(this)}
  def homePage:HomePage = new HomePageImpl

  class OwnersPageImpl extends OwnersPage {val driver = _driver; val config = _config; initPage(this)}
  def ownersPage:OwnersPage = new OwnersPageImpl

  class OwnersNewPageImpl extends OwnersNewPage {val driver = _driver; val config = _config; initPage(this)}
  def ownersNewPage:OwnersNewPage = new OwnersNewPageImpl

  class VetsPageImpl extends VetsPage {val driver = _driver; val config = _config; initPage(this)}
  def vetsPage:VetsPage = new VetsPageImpl

  class TutorialPageImpl extends TutorialPage {val driver = _driver; val config = _config; initPage(this)}
  def tutorialPage:TutorialPage = new TutorialPageImpl

}
