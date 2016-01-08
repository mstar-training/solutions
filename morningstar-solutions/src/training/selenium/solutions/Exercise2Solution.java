package training.selenium.solutions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Write a script that:
 * 1. Navigates to the test URL
 * 2. Logs in using the credentials provided to you
 * 3. Verifies that the user is logged in and taken to the home page
 */
public class Exercise2Solution {

	private static final String TEST_URL = "http://directbeta.morningstar.com/";
	private static final String USERNAME = "myUsername";
	private static final String PASSWORD = "myPassword";
	
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
		login(USERNAME, PASSWORD);
		Assert.assertTrue(driver.findElements(By.cssSelector("div#home")).size() > 0,
				"Expected to be at home page.");
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
	
}
