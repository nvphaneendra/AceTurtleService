package stepUtil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import junit.framework.Assert;

public class TestingClass {
	
	private static final Logger logger = Logger.getLogger(TestingClass.class);
	
	WebDriver driver;
	Properties prop;
	SoftAssert as = new SoftAssert();
	String path = System.getProperty("user.dir")+"\\src\\test\\resources\\puma.properties";
	
	@Test
	
	public void Test() {
		
		// Performing actions on the product
		logger.info("Performing an action on Men Puma products");
		Actions action = new Actions(driver);
		WebElement Men = driver.findElement(By.linkText("MEN"));
		action.moveToElement(Men).perform();
		
		// Using Explictwait
		logger.info("Using WebDriver to perform element click");
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//li[@id='men-subnav']/div/div/div[2]/ul/li[2]/a")));
		driver.findElement(By.xpath("//li[@id='men-subnav']/div/div/div[2]/ul/li[2]/a")).click();
		
		// Click on the second shoe from listing page
		logger.info("Click on the second shoe from listing page");
		driver.findElement(By.xpath("//div[@class='category-products']/ul/li[2]/a")).click();
		logger.info("Second shoe opening in new tab.");
		String ParentURL = driver.getWindowHandle();
		System.out.println(ParentURL);
		Set<String> ChildURL = driver.getWindowHandles();
		for(String Newtab:ChildURL)
		{
			System.out.println(Newtab);
			driver.switchTo().window(Newtab);
		}	
		
		logger.info("Select shoe size.");
		driver.findElement(By.className("product-size-click-btn")).click();
		WebElement dd = driver.findElement(By.id("configurable_swatch_size"));
		List<WebElement> Size = dd.findElements(By.tagName("li"));
		Size.get(5).click();
		WebElement Add2Cart = driver.findElement(By.xpath("//div[@class='add-to-cart-buttons']/button"));
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click()", Add2Cart);
		
		logger.info("SoftAssert will collect all assertions");
		as.assertAll();

	}
	
	@BeforeMethod	
	public void beforeMethod() throws FileNotFoundException {
		 
		  // Creating an Object for Properties call
		prop = new Properties();	
		  logger.info("Getting properties file");
		  FileInputStream objFile = new FileInputStream(path);
		  
		  try {			  
			  prop.load(objFile);
			  } catch (IOException e) {
			  System.out.println(e.getMessage());
			  e.printStackTrace();
			  }
		  
		  // Create a new instance of the browser
		  logger.info("Launch Chrome driver");
	      System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
	      ChromeOptions options = new ChromeOptions();
		  options.addArguments("--test-type");
		  options.addArguments("--no-sandbox");
	      driver = new ChromeDriver(options);
	      	 
	      // Put a Implicit wait, this means that any search for elements on the page could take the time the implicit wait is set for before throwing exception
	      logger.info("Using implicitywait");
	      driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	      
	      // Delete all cookies
	      driver.manage().deleteAllCookies();
		  logger.info("*********************Automation Testsuite Runner Log Info **********************************");
		  logger.info("New Browser Invoked");
		  
		  // Maximize the browser
	      logger.info("Maximize the browser");
	      driver.manage().window().maximize();
	 
	      // Launch the Puma online shopping site
	      logger.info("Launching Puma Shopping site");
	      driver.get(prop.getProperty("URL"));
	      
	      // Performing an assertion
	      logger.info("Doing assertion");
	      Assert.assertEquals("Buy Sports T-Shirts, Tracks, Running Shoes and Accessories Online - in.puma.com", driver.getTitle());
	      
	      logger.info("SoftAssert will collect all assertions");
	      as.assertAll();	 
	  }
	
	@AfterMethod
	 
	  public void afterMethod() {	 
		
		// Close the driver
		logger.info("Quite from Chrome browser");
	    driver.quit();
	 
	  }
}
