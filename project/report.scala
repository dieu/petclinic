import akka.event.Logging
import grizzled.slf4j.Logging
import io.Source
import java.io.IOException
import java.net.URLClassLoader
import net.thucydides.core.guice.{Injectors, ThucydidesModule}
import net.thucydides.core.reports.html.HtmlAggregateStoryReporter
import net.thucydides.core.util.EnvironmentVariables
import net.thucydides.core.{ThucydidesSystemProperties, ThucydidesSystemProperty}
import org.apache.commons.codec.digest.DigestUtils
import sbt._
import Keys._
import sbt.TaskKey

object ThucydidesReporter extends Logging{

  val aggregatedReport = TaskKey[Unit]("thucydides", "Generate an aggregated html story report.")
  val thucydidesSourceDirectory = TaskKey[Unit]("thucydides-prepare", "Prepares source dir, where all raw outcomes will be put.")
  val testAll = TaskKey[Unit]("test-all", "Tests and generate report.")

  val outputPrefix = "/site"
  val sourcePrefix = outputPrefix //somehow it should be equal to output

  val thucydidesPlugin = new ThucydidesManager

  //Todo: Refactor as task that introduce source dir on return
  val defineSourceDirTask = thucydidesSourceDirectory <<= target map {(targetDir:File) =>
    thucydidesPlugin.setDefaultSourceDirectory(targetDir.getPath+sourcePrefix)
    logger.info("Thucydides Sources are going to be put here: "+thucydidesPlugin.outputDirectoryPath)
  }

  val aggregateTask = aggregatedReport <<= target map {(targetDir:File) => {

    dependencyHack

    Thread.currentThread().setContextClassLoader(classOf[ThucydidesModule].getClassLoader) //magic string that resolves location of persistence.xml

    //val outputDir = targetDir.getPath +outputPrefix
    //logger.info("Thucydides Site is going to be generated here: "+outputDir)

    System.setProperty("thucydides.project.key", "Petclinic-Project")
    thucydidesPlugin.generate //(outputDir,targetDir.getPath+sourcePrefix)
    logger.info("Report is generated see here: "+thucydidesPlugin.outputDirectoryPath)
  }}


  val thucydidesSettings = Seq (
    aggregateTask,
    defineSourceDirTask,
    test in Test <<= test in Test dependsOn thucydidesSourceDirectory,
    testOnly in Test <<= testOnly in Test dependsOn thucydidesSourceDirectory,
    testQuick in Test <<= testQuick in Test dependsOn thucydidesSourceDirectory,
    aggregatedReport <<= aggregatedReport dependsOn thucydidesSourceDirectory,
    //test in Test <<= (test in Test, aggregatedReport in Test) map ((a,b) => {logger.info("test+report is done")})
    testAll in Test <<= aggregatedReport dependsOn (test in Test)

    //fork in aggregateTask := true
  )



  /*
  Very rude delete of .jar incorporated with sbt, due to later version exists in class path.
   */
  def dependencyHack {
    val clazz = classOf[DigestUtils].getResource("DigestUtils.class")
    if (clazz.getFile.contains("commons-codec-1.2.jar")){
      logger.warn("commons-codec-1.2.jar was detected, and is going to be removed")
      val fileName = clazz.getFile.split("!/org/").head.substring(5)
      println(fileName)
      try {
        val deleted = new File(fileName).delete()
        if (!deleted) throw new RuntimeException("commons-codec-1.2.jar exists and was not deleted")
      }
      catch {
        case e: SecurityException => {
          throw new RuntimeException("commons-codec-1.2.jar was not deleted due to security permissions", e)
        }
      }
      logger.info("hack was applied :-)")
    }
  }
}

/*class ThucydidesPlugin {
  val aggregatedReport = TaskKey[Unit]("attd", "Generate an aggregated html story report.")
  val thucydidesSourceDirectory = TaskKey[Unit]("attd-prepare", "Prepares source dir, where all raw outcomes will be put.")
  val thucydidesSettings = Seq (
    aggregateTask,
    defineSourceDirTask,
    test <<= test dependsOn thucydidesSourceDirectory,
    testOnly <<= testOnly dependsOn thucydidesSourceDirectory,
    testQuick <<= testQuick dependsOn thucydidesSourceDirectory,
    aggregatedReport <<= aggregatedReport dependsOn thucydidesSourceDirectory,
    fork in aggregateTask := true
  )

  //Todo: Refactor as task that introduce source dir on return
  val defineSourceDirTask = thucydidesSourceDirectory <<= target map {(targetDir:File) =>
    thucydidesPlugin.setDefaultSourceDirectory(targetDir.getPath+sourcePrefix)
    logger.info("Thucydides Sources are going to be put here: "+targetDir.getPath+sourcePrefix)
  }
  val aggregateTask = aggregatedReport <<= target map {(targetDir:File) => {
    //check with forkin
    Thread.currentThread().setContextClassLoader(classOf[ThucydidesModule].getClassLoader) //magic string that resolves location of persistence.xml

    System.setProperty("thucydides.project.key", "Petclinic-Project")
    thucydidesPlugin.generate //(outputDir,targetDir.getPath+sourcePrefix)
  }}
} */

class ThucydidesManager {
  val thucydidesProperties = new ThucydidesSystemProperties
  import thucydidesProperties._
  import ThucydidesSystemProperty._

  val projectKey = {
    if (isDefined(PROJECT_KEY))
      getValue(PROJECT_KEY) //"thucydides.project.key"
    else {
      val pKey = "someProject"
      setValue(PROJECT_KEY, pKey)
      pKey
    }
  }

  //TODO refactor as sbt Settings...
  val statisticDriver = getValue(STATISTICS_DRIVER)      //"thucydides.statistics.driver_class"

  val statisticsUrl = getValue(STATISTICS_URL) //"thucydides.statistics.url"
  val statisticsUsername = getValue(STATISTICS_USERNAME) //"thucydides.statistics.username"
  val statisticsPassword = getValue(STATISTICS_PASSWORD) //"thucydides.statistics.password"
  val statisticsDialect = getValue(STATISTICS_DIALECT) //"thucydides.statistics.dialect"

  val requirementsBaseDir = getValue(TEST_REQUIREMENTS_ROOT) //"thucydides.test.requirements.basedir"

  val reporter = new HtmlAggregateStoryReporter(projectKey)


  lazy val outputDirectoryPath = getValue(OUTPUT_DIRECTORY)
  /*
  Sets output directory, this is required before test run, to allow thucydides make screenshots and outcomes to proper folder.
   */
  private def setSourceDirectory(outputPath:String) {
    val outputDirectory:File = new File(outputPath)
    setValue(OUTPUT_DIRECTORY,outputPath)

    if (!outputDirectory.exists()) {
      outputDirectory.mkdirs()
    }
  }

  def setDefaultSourceDirectory(outputPath:String) {
    setSourceDirectory(System.getProperty("thucydides.sbt.outputDirectory",outputPath))
  }

  def generate {//(outputPath:String, sourcePath:String) {
    //setSourceDirectory(sourcePath)
    val outputDirectory:File = new File(outputDirectoryPath)
    val sourceDirectory:File = new File(outputDirectoryPath)

    try {
      generateHtmlStoryReports(outputDirectory, sourceDirectory)
    }
    catch {
      case e: IOException => {
        e.printStackTrace()
        throw new IncompatiblePluginsException("Error generating aggregate thucydides reports", e)
      }
    }
  }

  val issueTrackerUrl:String = null
  val jiraUrl:String = null
  val jiraProject:String = null
  val jiraUsername:String = null
  val jiraPassword:String = null

  //todo: find out is it usual that sourceDirectory contains site related things.
  private def generateHtmlStoryReports(outputDirectory:File,sourceDirectory:File) {
    reporter.setOutputDirectory(outputDirectory)
    reporter.setIssueTrackerUrl(issueTrackerUrl)
    reporter.setJiraUrl(jiraUrl)
    reporter.setJiraProject(jiraProject)
    reporter.setJiraUsername(jiraUsername)
    reporter.setJiraPassword(jiraPassword)
    reporter.generateReportsForTestResultsFrom(sourceDirectory)
  }
}