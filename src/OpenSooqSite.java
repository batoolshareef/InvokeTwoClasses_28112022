import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import io.github.bonigarcia.wdm.WebDriverManager;

public class OpenSooqSite {

	public WebDriver driver;
	public String urlOpenSooq = "https://jo.opensooq.com/en/cars/cars-for-sale";
	SoftAssert softAsserProcess = new SoftAssert();

	@BeforeTest
	public void login() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get(urlOpenSooq);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5000));
	}

	@Test
	public void searchCarTypeOpenSooqSite() {

//------All Variable definitions -----------------------------------------------------------------------------------
		String searchResultCountStr;
		String firstResult;
		int searchResultCountint;
		String searchResultStr2;
		String searchResultCountStr2;
		int searchResultCountint2;
		boolean searchResultbln;

//------invoke the method from the other class-----------------------------------------------------------------------------------
		PassParameters.changeWordToUpperCase();
//	System.out.println(PassParameters.carType);

//------to click on the search tab and show dropdown list -------------------------------------------------------
		WebElement searchCarType = driver.findElement(By.xpath("//*[@id=\"Brand\"]/div"));
		searchCarType.click();

//------to send the search word as the method from the other class ----------------------------------------------
		WebElement searchTab = driver.findElement(By.xpath("//*[@id=\"Brand\"]/div[2]/p/input"));
		searchTab.sendKeys(PassParameters.carType);

//------to check the box option of car type ---------------------------------------------------------------------
		WebElement checkBox = driver.findElement(By.xpath("//*[@id=\"Brand\"]/div[2]/div/ul/li[4]"));
		checkBox.click();

//------to click on search button -------------------------------------------------------------------------------
		driver.findElement(By.xpath("//*[@id=\"landingPostDynamicField\"]/div/button")).click();

//------to scroll down the page to show the result down ----------------------------------------------------------
		JavascriptExecutor js = (JavascriptExecutor) driver;
//		js.executeScript("window.scrollBy(0,800)");

//------to define the first list result --------------------------------------------------------------------------
		List<WebElement> searchResultList = driver.findElements(By.className("noEmojiText"));
		int searchResultListSize = searchResultList.size();

//------to get the result count which retrieved on page to compare with list size --------------------------------
		WebElement searchResultCount = driver.findElement(By.xpath("//*[@id=\"title_wrap\"]/span"));
		searchResultCountStr = searchResultCount.getText().substring(4, 6).trim();
		searchResultCountint = Integer.parseInt(searchResultCountStr);

//------to Check the size of List and The count of result---------------------------------------------------------
		System.out.println("The Size of List is: " + searchResultListSize);
		System.out.println("The Search Result Count Int is: " + searchResultCountint);

		System.out.println("-----------------------------------------------------------------------");
		System.out.println("");

//------to click on the first result and then take the text copy them click back ---------------------------------
		driver.findElement(By.className("noEmojiText")).click();
		firstResult = driver.findElement(By.className("postTitle")).getText();

		System.out.println("The first result text: " + firstResult);
		driver.navigate().back();
		driver.navigate().back();

//------to scroll up the page to show the search input tab --------------------------------------------------------
		js.executeScript("window.scrollBy(0,-600)");

//------to search again for the previous result ------------------------------------------------------------------
		driver.findElement(By.xpath("//*[@id=\"searchBox\"]")).sendKeys(firstResult);
		driver.findElement(By.xpath("//*[@id=\"landingPostDynamicField\"]/div/button")).click();

//------to scroll down the page to show the result down ----------------------------------------------------------
		js.executeScript("window.scrollBy(0,500)");

//------to define the second list result --------------------------------------------------------------------------
		List<WebElement> searchResultList2 = driver.findElements(By.className("noEmojiText"));
		int searchResultListSize2 = searchResultList2.size();

//------to get the second result count which retrieved on page to compare with list size --------------------------------
		WebElement searchResultCount2 = driver.findElement(By.xpath("//*[@id=\"title_wrap\"]/span[2]"));
		searchResultCountStr2 = searchResultCount2.getText().substring(4, 6).trim();
		searchResultCountint2 = Integer.parseInt(searchResultCountStr2);

//------to Check the second size of List and the second count of result---------------------------------------------------------
		System.out.println("The Second Size of List is: " + searchResultListSize2);
		System.out.println("The Second Search Result Count Int is: " + searchResultCountint2);

		System.out.println("-----------------------------------------------------------------------");
		System.out.println("");

//------to get the text of every result contains the required Word to search ---------------------------------------------------		
		for (int i = 0; i < searchResultCountint2; i++) {
			searchResultStr2 = searchResultList2.get(i).getText();

			if (searchResultStr2.contains(firstResult)) {

				System.out.println((i + 1) + " The List 2 Result is: " + searchResultStr2);
			} else {
				System.out.println((i + 1) + ": I didn't find any result contains the search text");
			}

//-------to Assert That The size of List as the Count of search resulr ---------------------------------------------------------		
			softAsserProcess.assertEquals(searchResultListSize, searchResultCountint);
			softAsserProcess.assertEquals(searchResultListSize2, searchResultCountint2);

//-------to Assert That The Search Result text contains the search word as require ---------------------------------------------		
			searchResultbln = searchResultStr2.contains(firstResult);
			System.out.println((i + 1) + " The Boolean Result is: " + searchResultbln);

			softAsserProcess.assertEquals(searchResultbln, true);
			softAsserProcess.assertAll();
		}
		System.out.println("-----------------------------------------------------------------------");
		System.out.println("");
	}

//-------to quit application after run test ------------------------------------------------------------------------------------		
	@AfterTest
	public void quitApplication() throws InterruptedException {
		Thread.sleep(5000);
		driver.quit();
	}

}