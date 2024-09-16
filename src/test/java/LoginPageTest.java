import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginPageTest {

	WebDriver driver;
	String browser;
	String url;
	String userName;
	String password;

	@BeforeClass
	public void readConfig() {

		Properties prop = new Properties();

		try {
			InputStream input = new FileInputStream("src\\main\\java\\config\\config.properties");
			prop.load(input);
			browser = prop.getProperty("browser");
			url = prop.getProperty("url");
			userName = prop.getProperty("userName");
			password = prop.getProperty("password");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@BeforeMethod
	public void init() {

		if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver128.exe");
			driver = new ChromeDriver();

		} else if (browser.equalsIgnoreCase("edge")) {
			System.setProperty("webdriver.edge.driver", "drivers\\msedgedriver.exe");
			driver = new EdgeDriver();
		} else {
			System.out.println("please use a valid browser");
		}
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

//	Element List
	By USER_NAME_FIELD = By.xpath("//*[@id=\"user_name\"]");
	By PASSWORD_FIELD = By.xpath("//*[@id=\"password\"]");
	By SIGNIN_BUTTON_FIELD = By.xpath("//button[@id='login_submit']");
	By DASHBOARD_HEADER_FIELD = By.xpath("//strong[contains(text(), 'Dashboard')]");
	By CUSTOMER_MENU_FIELD = By.xpath("//span[contains(text(), 'Customers')]");
	By ADD_CUSTOMER_MENU_FIELD = By.xpath("//span[contains(text(), 'Add Customer')]");
	By ADD_CUSTOMER_HEADER_FIELD = By.xpath("//strong[contains(text(), 'New Customer')]");
	By FULL_NAME_FIELD = By.xpath("//*[@id=\"general_compnay\"]/div[1]/div/input");
	By COMPANY_DROPDOWN_FIELD = By.xpath("//select[@name='company_name']");
	By EMAIL_FIELD = By.xpath("//*[@id=\"general_compnay\"]/div[3]/div/input");
	By PHONE_FIELD = By.xpath("//*[@id=\"phone\"]");
	By COUNTRY_FIELD = By.xpath("//*[@id=\"general_compnay\"]/div[8]/div[1]/select");
	By COUNTRY_DROPDOWN = By.xpath("//*[@id=\"general_compnay\"]/div[8]/div[1]/select");
	// Test or Mock Data
	String fullName = "Selenium";
	String company = "Techfios";
	String email = "abc@techfios.com";
	String phone = "420420";
	String country ="Albania";

	// @Test
	public void loginTest() {

		driver.findElement(USER_NAME_FIELD).sendKeys(userName);
		driver.findElement(PASSWORD_FIELD).sendKeys(password);
		driver.findElement(SIGNIN_BUTTON_FIELD).click();

		Assert.assertEquals(driver.findElement(DASHBOARD_HEADER_FIELD).getText(), "Dashboard",
				"Dashboard page not found");

	}

	@Test
	public void addCustomer() {

		loginTest();
		driver.findElement(CUSTOMER_MENU_FIELD).click();
		driver.findElement(ADD_CUSTOMER_MENU_FIELD).click();
		Assert.assertEquals(driver.findElement(ADD_CUSTOMER_HEADER_FIELD).getText(), "New Customer",
				"Add Customer page not found!!!");

		

		driver.findElement(FULL_NAME_FIELD).sendKeys("fullName" + generateRandomNum(9999));
		SelectFromDropdown(driver.findElement(COMPANY_DROPDOWN_FIELD), company);

		driver.findElement(EMAIL_FIELD).sendKeys(email + generateRandomNum(999));
		driver.findElement(PHONE_FIELD).sendKeys(phone);
		
		Select sel1 = new Select(driver.findElement(COUNTRY_DROPDOWN));
		sel1.selectByVisibleText(country);
		
		SelectFromDropdown(driver.findElement(COUNTRY_DROPDOWN), country);

	}

	private void SelectFromDropdown(WebElement element, String visibleText) {
		Select sel1 = new Select(element);
		sel1.selectByVisibleText(visibleText);
		
	}


	private int generateRandomNum(int boundNum) {
		Random rnd = new Random();
		int generatedNum = rnd.nextInt(boundNum);
		return generatedNum;

	}
	
	@AfterMethod
	public void tearDown() {
		driver.close();
		driver.quit();
	}

}