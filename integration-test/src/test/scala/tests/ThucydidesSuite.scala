package tests

import net.thucydides.core.webdriver.{WebdriverProxyFactory, SupportedWebDriver, WebdriverManager, Configuration}
import net.thucydides.core.batches.{UnsupportedBatchStrategyException, BatchStrategy, BatchManager}
import net.thucydides.core.util.EnvironmentVariables
import net.thucydides.core.ThucydidesSystemProperty
import net.thucydides.core.annotations._
import org.scalatest.{Args, Suite, SuiteMixin}
import com.weiglewilczek.slf4s.Logging
import org.openqa.selenium.WebDriver
import net.thucydides.core.pages.Pages
import steps.PetOwnerStepsEx
import net.thucydides.core.guice.Injectors
import net.thucydides.core.steps.{StepData, StepAnnotations, StepEventBus, StepFactory}
import org.apache.commons.lang3.StringUtils
import net.thucydides.core.model.TestOutcome
import org.apache.commons.lang.NotImplementedException
import net.thucydides.core.reports.ReportService
import scala.collection.JavaConverters._
import org.scalatest.Args
import net.thucydides.core.Thucydides._

/**
 * User: Dmytro Makhno
 */
object ThucydidesSuite {


  private def getBatchManager(configuration: Configuration): BatchManager = {
    val environmentVariables: EnvironmentVariables = configuration.getEnvironmentVariables
    val batchManagerProperty: String = ThucydidesSystemProperty.BATCH_STRATEGY.from(environmentVariables, BatchStrategy.DIVIDE_EQUALLY.name)
    try {
      return BatchStrategy.valueOf(batchManagerProperty).instance(environmentVariables)
    }
    catch {
      case e: Exception => {
        throw new UnsupportedBatchStrategyException(batchManagerProperty + " is not a supported batch strategy.", e)
      }
    }
  }
}

@Story(classOf[PetClinic#Navigation#PetOwner])
trait ThucydidesSuite extends SuiteMixin with Logging {
  this: Suite =>
  @Managed var webdriver:WebDriver = _

  @ManagedPages var pages2: Pages = _

  @Steps var petOwner:PetOwnerStepsEx = _


  //type ThisType = ThucydidesSuite
  ///BEGIN ThucydidesRunner Constructor port...
  val klass: Class[_] //= ThisType
  val webdriverManager: WebdriverManager = Injectors.getInjector.getInstance(classOf[WebdriverManager])
  val configuration: Configuration = Injectors.getInjector.getInstance(classOf[Configuration])
  val requestedDriver = getSpecifiedDriver(klass)
  if (TestCaseAnnotations.supportsWebTests(klass)) {
    checkRequestedDriverType
  }

  //dmakhno: batches are subject of parallel run, may be good to remove this at all
  val batchManager: BatchManager = ThucydidesSuite.getBatchManager(configuration)
  batchManager.registerTestCase(klass)


  private def getSpecifiedDriver(klass: Class[_]): String = {
    if (ManagedWebDriverAnnotatedField.hasManagedWebdriverField(klass)) {
      return ManagedWebDriverAnnotatedField.findFirstAnnotatedField(klass).getDriver
    }
    else {
      return null
    }
  }

  /**
   * Ensure that the requested driver type is valid before we start the tests.
   * Otherwise, throw an InitializationError.
   */
  private def checkRequestedDriverType {
    if (requestedDriverSpecified) {
      SupportedWebDriver.getDriverTypeFor(requestedDriver)
    }
    else {
      configuration.getDriverType
    }
  }
  private def requestedDriverSpecified: Boolean = !StringUtils.isEmpty(this.requestedDriver)
  ///END Constructor port


















  /*abstract override def withFixture(test: NoArgTest) {
    println("      withFixture '%s'...".format(test.name))
    super.withFixture(test)
    println("      ...withFixture '%s'".format(test.name))
  }
  abstract override def run(testName: Option[String], args: Args) = {
    println("run '%s'...".format(testName.getOrElse("NONE")))
    val status = super.run(testName, args)
    println("...run '%s'".format(testName.getOrElse("NONE")))
    status
  }
  abstract override def runNestedSuites(args: Args) = {
    println("  runNestedSuites...")
    val status = super.runNestedSuites(args)
    println("  ...runNestedSuites")
    status
  }*/


  abstract override def runTests(testName: Option[String], args: Args) = {
    try {
      //todo: initializeDriversAndListeners()
      super.runTests(testName, args)
    }
    finally {
      notifyTestSuiteFinished
      generateReports
      //todo: dropListeners(notifier)
      closeDrivers
    }
  }
  private def testInvoke(test: AnyRef) = {
    if (webtestsAreSupported) {
      injectDriverInto(test)
      injectAnnotatedPagesObjectInto(test)
      //uniqueSession = TestCaseAnnotations.forTestCase(test).isUniqueSession
    }
    injectScenarioStepsInto(test)
    useStepFactoryForDataDrivenSteps
    //val baseStatement: Statement = super.methodInvoker(method, test)
    //return new ThucydidesStatement(baseStatement, stepListener.getBaseStepListener)
  }

  private def webtestsAreSupported = TestCaseAnnotations.supportsWebTests(klass)
  private lazy val pages = new Pages(getDriver, configuration)
  private lazy val stepFactory = new StepFactory(pages)
  private def useStepFactoryForDataDrivenSteps { StepData.setDefaultStepFactory(stepFactory)}

  /**
   * Instantiates the @ManagedPages-annotated Pages instance using current WebDriver.
   */
  protected def injectScenarioStepsInto(testCase: AnyRef) {
    StepAnnotations.injectScenarioStepsInto(testCase, stepFactory)
  }

  /**
   * Instantiates the @ManagedPages-annotated Pages instance using current WebDriver.
   */
  protected def injectAnnotatedPagesObjectInto(testCase: AnyRef) { StepAnnotations.injectAnnotatedPagesObjectInto(testCase, pages) }

  /**
   * Instantiate the @Managed-annotated WebDriver instance with current WebDriver.
   */
  protected def injectDriverInto(testCase: AnyRef) {
    TestCaseAnnotations.forTestCase(testCase).injectDriver(getDriver)
  }

  //private def skipThisTest:Boolean = throw new NotImplementedException()//  (batchManager != null) && (!batchManager.shouldExecuteThisTest(getDescription.testCount))
  private def closeDrivers { webdriverManager.closeAllCurrentDrivers }
  private def notifyTestSuiteFinished {
    try {
      StepEventBus.getEventBus.testSuiteFinished
    }
    catch {
      case listenerException: Throwable => {
        logger.error("Test event bus error: " + listenerException.getMessage, listenerException)
      }
    }
  }
  protected def generateReports { generateReportsFor(getTestOutcomes) }

  lazy val reportService = new ReportService(configuration.getOutputDirectory, defaultReporters)
  /**
   * A test runner can generate reports via Reporter instances that subscribe
   * to the test runner. The test runner tells the reporter what directory to
   * place the reports in. Then, at the end of the test, the test runner
   * notifies these reporters of the test outcomes. The reporter's job is to
   * process each test run outcome and do whatever is appropriate.
   * @param testOutcomeResults the test results from the previous test run.
   */
  private def generateReportsFor(testOutcomeResults: List[TestOutcome]) {
    reportService.generateReportsFor(testOutcomeResults.asJava)
  }

  def getTestOutcomes: List[TestOutcome] = {
    throw new NotImplementedException()
  }

  /**
   * The default reporters applicable for standard test runs.
   */
  protected def defaultReporters = ReportService.getDefaultReporters








  abstract override def runTest(testName: String, args: Args) = {
    initializeTestSession
    resetBrowserFromTimeToTime

    super.runTest(testName, args)
    //todo: add to reported ignored and pending tests. what about canceled?
  }

  val uniqueSession = TestCaseAnnotations.forTestCase(klass).isUniqueSession //todo: does it work?
  protected def restartBrowserBeforeTest = !uniqueSession
  protected def resetBrowserFromTimeToTime {
    if (restartBrowserBeforeTest) {
      WebdriverProxyFactory.resetDriver(getDriver)
    }
  }

  protected def getDriver: WebDriver = webdriverManager.getWebdriver(requestedDriver)
}

