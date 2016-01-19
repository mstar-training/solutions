package training.selenium.solutions;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

/**
 * Implement TestListenerAdapter to take a screenshot upon failure of a test.
 */
@Listeners(Exercise8Solution.class)
public class Exercise8Solution extends TestListenerAdapter {

private static final String TEST_URL = "http://directbeta.morningstar.com/";
	
	private WebDriver driver;
	
	@BeforeMethod 
	public void setUp() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(TEST_URL);
	}
	
	@Test
	public void test() {
		Assert.assertTrue(false, "Intentionally failed script.");
	}

	@AfterMethod
	public void tearDown() {
		if (driver != null)
			driver.quit();
	}

	@Override
	public void onTestFailure(ITestResult tr) {
		/*
		 * At runtime, TestNG will create 2 instances of this class: one to run the 
		 * test, and another to run this method. So even though we are implementing 
		 * this method in the same class as the test code (above), we must retrieve 
		 * the instance on which the test is run. Of course, it is implemented this
		 * way for the sake of example, and in a real framework this method would
		 * never reside in the same class as test code.
		 */
		Exercise8Solution instance = (Exercise8Solution) tr.getInstance();
		WebDriver wd = instance.driver;
		if (wd != null) {
			File screenshot = ((TakesScreenshot)wd).getScreenshotAs(OutputType.FILE);
			String targetFilename = System.getProperty("user.dir") + "/target/screenshot" 
					+ new Date().getTime() + ".png";
			try {
				FileUtils.copyFile(screenshot, new File(targetFilename));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
