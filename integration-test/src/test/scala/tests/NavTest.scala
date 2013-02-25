package tests

import org.scalatest.{BeforeAndAfterAll, FreeSpec}

/**
 * User: Dmytro Makhno
 */
class NavTest extends BrowserTestBase {

  def test(cycle:Int) = {"Navigation "+cycle - {
    "root" in {
      driver.get(Config.defaultInfrastructure)
      Site().homePage.toHome
    }

    "owners" in  {
      Site().homePage.toFindOwner
      Site().ownersPage.toAddOwner
      Site().ownersNewPage.toHome
    }

    "vets" in {
      Site().homePage.toDisplayAllVeterinarians
      Site().vetsPage.toHome
    }

    "tutorial" in {
      Site().homePage.toTutorial
      Site().tutorialPage.toHome
    }
  }}
  behave like test(1)
  behave like test(2)
  behave like test(3)
  behave like test(4)
  behave like test(5)

}
class NavTestDummy2 extends NavTest
class NavTestDummy3 extends NavTest
class NavTestDummy4 extends NavTest
class NavTestDummy5 extends NavTest
class NavTestDummy6 extends NavTest
class NavTestDummy7 extends NavTest
class NavTestDummy8 extends NavTest





trait BrowserTestBase extends FreeSpec with BeforeAndAfterAll with BrowserContext {
  implicit val config = Infrastructure.default

  private var driverInit = false
  implicit lazy val driver = {
    driverInit = true
    Browser.byName(Config.defaultBrowser)
  }

  override def afterAll() {
    super.afterAll() // To be stackable, must call super.afterAll
    if (driverInit) driver.quit()
  }
}