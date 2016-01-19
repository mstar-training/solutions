package training.selenium.solutions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Write a script that:
 * 1. Logs in to the test environment
 * 2. Navigates from the home page to the U.S. Equities page
 * 3. Waits until the table rows are displayed
 */
public class Exercise3Solution {
	
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
		Assert.assertTrue(driver.findElements(By.id("home")).size() > 0,
				"Expected to be at home page.");
		
		WebDriverWait wait = new WebDriverWait(driver, 10);
		
		// wait visibility of and then click US Equities link
		wait.until(ExpectedConditions.elementToBeClickable(
				By.cssSelector("[data-name='USEquities']"))).click();
		
		// wait visibility of table rows
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("quicktake-link")));
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
