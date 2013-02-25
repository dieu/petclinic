package tests

import tests.Browser

/**
 * @author Dmytro Makhno
 */

/**
 * External configurations for tests
 * use "sbt -Dprop=value, to set these properties
 * e.g in jobs use <pre>sbt -Dtest.gui.infr=prod -Dtest.browser=Remote -Dselenium.grid.host="23.22.223.104:4444"</pre>
 * local for firefox debug <pre>sbt -Dfirefox.firebug.path="/home/tester/Downloads/firebug-1.10.0-fx.xpi"</pre>
 */
object Config {

  lazy val defaultInfrastructure = System.getProperty("test.gui.infr", "http://54.242.189.227/")

  lazy val defaultBrowser = {
    System.getProperty("test.browser", Browser.FIREFOX)
  }

  lazy val seleniumHost = {
    System.getProperty("selenium.grid.host", "localhost:4444")
  }

  lazy val fireBugPath = {
    System.getProperty("firefox.firebug.path") //"/home/dmakhno/Downloads/firebug-1.10.0-fx.xpi"
  }

  lazy val primaryBackend = {
    System.getProperty("test.backend")
  }
}

case class Infrastructure(host:String)
object Infrastructure {
  val default = Infrastructure(Config.defaultInfrastructure)
}