package training.selenium.solutions;

import java.util.List;
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
 * 1. Logs in to the test environment
 * 2. Navigates from the home page to the U.S. Equities page
 * 3. Given the name of a row, gets the ticker symbol from the same row and verifies that it is correct
 */
public class Exercise4Solution {

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
		
		String name = "1-800 Flowers.com Inc Class A";
		String ticker = "FWC";
		Assert.assertEquals(getTickerSymbol(name), ticker);
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
	
	private String getTickerSymbol(String name) {
		// get name links from all rows
		List<WebElement> names = driver.findElements(By.className("quicktake-link"));
		int match = -1; // index of row that matches name
		
		// look for row that matches name
		for (int i = 0; i < names.size(); i++) {
			if (names.get(i).getText().equals(name)) {
				match = i;
			}
		}
		
		// if no match is found, return null
		if (match == -1) {
			return null;
		} 
		
		// get ticker symbols from all rows and return the one at the index determined above
		return driver.findElements(By.cssSelector(
				"#grid-element > div:nth-child(2) > div.objbox > table > tbody > tr > td:nth-child(3)"))
				.get(match).getText();
	}
}
