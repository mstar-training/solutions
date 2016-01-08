package training.selenium.solutions;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Write a script that:
 * 1. Navigates and logs in to the test environment
 * 2. Navigates from the home page to the U.S. Equities page
 * 3. Clicks on the name field of any row, causing the "Snapshot" overlay to appear
 * 4. Switches to the overlay frame and verifies that it displays the correct name
 * 5. Switches back to the default content and closes the overlay
 */
public class Exercise6Solution {

	private static final String TEST_URL = "http://directbeta.morningstar.com/";
	private static final String USERNAME = "myUsername";
	private static final String PASSWORD = "myPassword";
	
	private WebDriver driver;
	
	@BeforeMethod 
	public void setUp() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(TEST_URL);
	}
	
	@Test
	public void test() {
		Assert.assertEquals(driver.getTitle(), "Morningstar Log In", 
				"Expected to be at login page.");
		
		login(USERNAME, PASSWORD);
		Assert.assertTrue(driver.findElements(By.id("home")).size() > 0,
				"Expected to be at home page.");
		
		// click US Equities link
		driver.findElement(By.cssSelector("[data-name='USEquities']")).click();
		Assert.assertEquals(driver.findElement(By.cssSelector("#primary-header .hiddenValue")).getText(),
				"U.S. Equities", "Expected to be at U.S. Equities page.");
		
		// click on a name
		String name = "1-800 Flowers.com Inc Class A";
		getNameLink(name).click();
		
		// switch to "Snapshot" overlay frame and verify that the correct name is displayed
		driver.switchTo().frame(driver.findElement(By.cssSelector("#report-flyover iframe")));
		Assert.assertEquals(driver.findElement(By.cssSelector("div.r_title h1")).getText(), name, 
				"Expected name in overlay to be the same as name in table.");
		
		// switch back to the main frame
		driver.switchTo().defaultContent();
		
		// close the overlay and verify that it is closed
		driver.findElement(By.cssSelector("#report-flyover-hdr .ic-remove"));
		Assert.assertFalse(driver.findElements(By.cssSelector("#report-flyover iframe")).size() == 0,
				"Expected overlay to be closed.");
	}

	@AfterMethod
	public void tearDown() {
		if (driver != null)
			driver.quit();
	}
	
	private void login(String username, String password) {
		driver.findElement(By.id("email")).clear();
		driver.findElement(By.id("email")).sendKeys(username);
		driver.findElement(By.id("strPassword")).clear();
		driver.findElement(By.id("strPassword")).sendKeys(password);
		driver.findElement(By.id("Login")).click();
	}
	
	private WebElement getNameLink(String name) {
		for (WebElement link: driver.findElements(By.className("quicktake-link"))) {
			if (link.getText().equals(name)) {
				return link;
			}
		}
		return null;
	}
	
}
