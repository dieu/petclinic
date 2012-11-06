package tests

import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.{FirefoxProfile, FirefoxDriver}
import org.openqa.selenium.htmlunit.HtmlUnitDriver
import org.openqa.selenium.WebDriver
import scala.Predef._
import org.openqa.selenium.remote.{DesiredCapabilities, RemoteWebDriver}
import java.net.URL
import tests.Config
import java.util.logging.Level

trait BrowserContext {
  implicit def driver: WebDriver
  implicit def config: Infrastructure
}

object Browser {
  final val CHROME = "Chrome"
  final val FIREFOX = "FireFox"
  final val HTMLUNIT = "HtmlUnit"
  final val REMOTE = "Remote"

  /**
   * Creates a WebBrowser of the specified class name.
   */
  def byName(clazz: String) = {
    implicit val drv:WebDriver = clazz match {
      case HTMLUNIT => {
        new HtmlUnitDriver() {
          this.setJavascriptEnabled(true)
        }
      }
      case FIREFOX => {
        if (Config.fireBugPath!=null){
          val profile = new FirefoxProfile()
          profile.addExtension(new java.io.File(Config.fireBugPath))
          profile.setPreference("extensions.firebug.currentVersion", "9.9.9") //hides ff messages about newer version of plugin if found
          new FirefoxDriver(profile)
        }
        else
        {
          new FirefoxDriver()//{ this.manage().window().maximize() }
        }
      }
      case CHROME => new ChromeDriver()
      case REMOTE => {
        val capability = DesiredCapabilities.firefox() //todo: think of randomize for chrome or firefox
        capability.setCapability("DISPLAY",":98")
        val driver = new RemoteWebDriver(new URL(gridUrl), capability)
        driver.setLogLevel(Level.INFO)
        driver
      }
      case _ => throw new IllegalArgumentException("unsupported driver")
    }
    //implicitWait(){} //turns on default implicit wait
    drv
  }

  /**
   * Gets the grid, should be set outside of sbt
   * @return grid url or local grid if not defined
   */
  private def gridUrl:String = {
    val host = Config.seleniumHost
    "http://"+host+"/wd/hub"
  }
}