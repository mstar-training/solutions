package training.selenium.solutions;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Launch a Firefox browser using an existing profile.
 */
public class Exercise7Solution {

	private static final String TEST_URL = "http://directbeta.morningstar.com/";

	private WebDriver driver;

	@BeforeMethod
	public void setUp() {
		FirefoxProfile profile = new FirefoxProfile(new File("path/to/my/profile"));
		driver = new FirefoxDriver(profile);
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
