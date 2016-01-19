package training.selenium.solutions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Write a script that:
 * 1. Launches a Firefox browser instance
 * 2. Navigates to the test URL
 * 3. Uses the page title to verify that user is at the login page
 */
public class Exercise1Solution {

	private static final String TEST_URL = "http://directbeta.morningstar.com/";
	
	private WebDriver driver;
	
	@BeforeMethod 
	public void setUp() {
		driver = new FirefoxDriver();
		driver.get(TEST_URL);
	}
	
	@Test
	public void test() {
		Assert.assertEquals(driver.getTitle(), "Morningstar Log In", 
				"Expected to be at login page.");
	}

	@AfterMethod
	public void tearDown() {
		if (driver != null)
			driver.quit();
	}
	
}

