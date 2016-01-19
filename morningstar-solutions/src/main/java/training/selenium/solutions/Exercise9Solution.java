package training.selenium.solutions;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 1.	Download the latest version of the selenium-server-standalone JAR at 
 * 		http://www.seleniumhq.org/download/.
 * 2.	Launch the JAR as the grid hub and as a grid node.
 * 3.	Write a script that uses RemoteWebDriver to connect to your local grid server.
 */
public class Exercise9Solution {

	private static final String TEST_URL = "http://directbeta.morningstar.com/";

	private WebDriver driver;

	@BeforeMethod
	public void setUp() throws MalformedURLException {
		URL gridHubUrl = new URL("http://127.0.0.1:4444/wd/hub/");
		DesiredCapabilities capabilities = new DesiredCapabilities("firefox", null, Platform.ANY);
		driver = new RemoteWebDriver(gridHubUrl, capabilities);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
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
