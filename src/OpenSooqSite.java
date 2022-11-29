import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class OpenSooqSite {

	public WebDriver driver;
	public String urlOpenSooq = "https://jo.opensooq.com/en";
	SoftAssert softAsserProcess = new SoftAssert();

	@BeforeTest
	public void login() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get(urlOpenSooq);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1000));
	}

	@Test
	public void searchCarTypeOpenSooqSite() {

		PassParameters.changeWordToUpperCase();
//	System.out.println(PassParameters.carType);

		driver.findElement(By.id("searchBox")).sendKeys(PassParameters.carType + Keys.ENTER);

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,380)");

		List<WebElement> searchResultList = driver.findElements(By.className("noEmojiText"));
		WebElement searchResultCount = driver.findElement(By.xpath("//*[@id=\"title_wrap\"]/span[2]"));

		String searchResultStr;
		String searchResultCountStr;
		int searchResultCountint;

		int searchResultListSize = searchResultList.size();
		searchResultCountStr = searchResultCount.getText().substring(4, 6).trim();
		searchResultCountint = Integer.parseInt(searchResultCountStr);

//--------------1. to Check the size of List and The count of result----------------------------------------
		System.out.println("The Size of List is: " + searchResultListSize);
//	System.out.println("The Search Result Count Str is: " + searchResultCountStr);
		System.out.println("The Search Result Count Int is: " + searchResultCountint);

		System.out.println("-----------------------------------------------");
		System.out.println("The Search Result Texts are: " );
		
//--------------2. to get the text of evey reult contains the Word "KIA FORYE 2013" -------------------------		

		for (int i = 0; i < searchResultList.size(); i++) {

			searchResultStr = searchResultList.get(i).getText().toUpperCase();

			if (searchResultStr.contains("KIA FORTE 2013")) {
				System.out.println((i + 1) + ": " + searchResultStr);
			} else {
				System.out.println((i + 1) + ": I Didn't find any result contains KIA FORTE 2013");
			}

//----3. to Assert That The size of List as the Count of search resulr -------------------------		

			softAsserProcess.assertEquals(searchResultListSize, searchResultCountint);

//----4. to Assert That The Search Result text contains the sent word "KIA FORTE 2013"-------------------------		

			softAsserProcess.assertEquals(searchResultListSize, searchResultCountint);
			softAsserProcess.assertAll();

		}
	}

	@AfterTest
	public void quitApplication() throws InterruptedException {
		Thread.sleep(3000);
		driver.quit();
	}

}