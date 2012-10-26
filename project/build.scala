package petclinic

import sbt._
import Keys._
import Reference._
import sbt.Scoped.RichTaskable2
import xml.XML


object PetclinicBuild extends Build {
  import Utils._

  lazy val root = project(".")

  lazy val petclinicSettings = Seq(
    organization := "petclinic",
    version := "0.1",
    scalaVersion := "2.9.2",

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
      testOptions in scope <+= target.map { t => Tests.Argument(TestFrameworks.ScalaTest, "stdout(config=\"durations\")") }, //, "junitxml(directory=\"%s\")" format (t / "test-reports"), native listener ignores skipped tests in junitoutput.
      testListeners <<= junitListeners
    ) //Will make sure that parallelExecution is off for jacoco during test execution. (see: http://ronalleva.com/2012/04/25/jacoco-and-play.html)
  }
  /**
   * @note Issue reported: https://play.lighthouseapp.com/projects/82401-play-20/tickets/619-junitxmltestlistener-works-only-for-testnameendswithtest
   */
  lazy val junitListeners:RichTaskable2[File, Keys.TaskStreams]#App[Seq[TestReportListener]] = (target, streams).map((t, s) => Seq(new eu.henkelmann.sbt.JUnitXmlTestsListener(t.getAbsolutePath, s.log) {
    override def endGroup(name: String, result: TestResult.Value) = {
      XML.save(new File(targetDir, testSuite.name.split('.').takeRight(1).mkString + ".xml").getAbsolutePath, testSuite.stop(), "UTF-8", true, null)
    }
  }
  ))

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
