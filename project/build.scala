import sbt._
import Keys._
import sbt.Scoped.RichTaskable2
import xml.XML
import net.thucydides.core.reports.html.HtmlAggregateStoryReporter


object PetclinicBuild extends Build {
  import Utils._

  lazy val root = project(".")

  ThucydidesReporter.dependencyHack

  val testThreads = 8
  lazy val integrationTests = project("integration-test")
    .libraryDependencies(Dependencies.test)
    .settings(
      parallelExecution in Test := true,
      concurrentRestrictions in Global := Seq(
        Tags.limit(Tags.Test, testThreads)))
    //.settings(ThucydidesReporter.sitePath := target)
    .settings(ThucydidesReporter.thucydidesSettings: _*)
    .settings(net.virtualvoid.sbt.graph.Plugin.graphSettings: _*)


  lazy val petclinicSettings = Seq(
    organization := "petclinic",
    version := "0.1",
    scalaVersion := "2.10.0",

    scalacOptions := Seq("-deprecation", "-unchecked", "-explaintypes"),

    resolvers ++= Seq(
      "Typesafe repo" at "http://repo.typesafe.com/typesafe/repo/",
      "Typesafe releases" at "http://repo.typesafe.com/typesafe/releases",
      "Typesafe snapshots" at "http://repo.typesafe.com/typesafe/snapshots",
      "Sonatype releases" at "https://oss.sonatype.org/content/repositories/releases",
      "Cometera Inc snapshots" at "https://raw.github.com/cometera/mvn-repo/master/snapshots"
    )
  )

  lazy val testSettings = Seq(Test, IntegrationTest).flatMap {
    scope => Seq(
      parallelExecution in scope := false,
      testOptions in scope <+= target.map { t => Tests.Argument(TestFrameworks.ScalaTest, "stdout(config=\"durations\")", "junitxml(directory=\"%s\")" format (t / "test-reports")) }
    )}

  lazy val ideaPluginSettings =  {
    org.sbtidea.SbtIdeaPlugin.ideaSettings ++
      Seq(
        org.sbtidea.SbtIdeaPlugin.addGeneratedClasses := true,
        org.sbtidea.SbtIdeaPlugin.includeScalaFacet := true,
        org.sbtidea.SbtIdeaPlugin.defaultClassifierPolicy := true,
        org.sbtidea.SbtIdeaPlugin.commandName := "gen-idea"
      )
  }

  def commonSettings = Defaults.defaultSettings ++ 
    petclinicSettings ++
    testSettings ++
    ideaPluginSettings

  object Utils {
    implicit def richProject(p: Project) = new {
      def libraryDependencies(d: Seq[ModuleID]): Project = p.settings(Keys.libraryDependencies ++= d)
    }

    def project(path: String) = Project(
      id = if (path == ".") "petclinic" else "petclinic-" + path.replace('/', '-'),
      base = file(path),
      settings = commonSettings)
  }
}

object Dependencies {
  val test = Seq(
    //"org.hibernate" % "hibernate-entitymanager" % "3.5.4-Final" exclude("commons-logging", "commons-logging") exclude("org.slf4j","slf4j-api") exclude("commons-collections","commons-collections") exclude("cglib","cglib") exclude("xml-apis","xml-apis"),
    "org.scalatest" %% "scalatest" % "2.0.M6-SNAP9" % "test",
    "org.seleniumhq.selenium" % "selenium-java" % "2.31.0" % "test",
    //"ru.yandex.qatools.htmlelements" % "htmlelements" % "1.8" % "test" from("http://repo.typesafe.com/typesafe/repo/ru/yandex/qatools/htmlelements/htmlelements-java/1.8-SNAPSHOT/htmlelements-java-1.8-20120930.005728-3.jar"),
    "ru.yandex.qatools.htmlelements"% "htmlelements-java" % "1.9" % "test", //uses old selenium-java
    "com.google.code.findbugs" % "jsr305" % "1.3.+" % "test", //somehow required for 2.x selenium, in scala should be referenced explicitly
    "net.thucydides" % "thucydides-junit" % "0.9.98" % "test",
    //"com.novocode" % "junit-interface" % "0.10-M1" % "test",
    "org.joda" % "joda-convert" % "1.2" % "test",
    "com.weiglewilczek.slf4s" % "slf4s_2.9.1" % "1.0.7" % "test"
  )
}
