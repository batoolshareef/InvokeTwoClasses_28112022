import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SearchWordGoogle {

	public WebDriver driver;
	public String urlGoogle = "https://www.google.com/?hl=en";
	SoftAssert softAsserProcess = new SoftAssert();

	@BeforeTest
	public void loginSite() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get(urlGoogle);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1000));

	}

	@Test()
	public void searchWordGoogleSite() {

		PassParameters.changeWordToUpperCase();

		driver.findElement(By.name("q")).sendKeys(PassParameters.wordSearch + Keys.ENTER);

		List<WebElement> searchResultList = driver.findElements(By.tagName("h3"));

		String searchResultStr;

		for (int i = 0; i < searchResultList.size(); i++) {
			searchResultStr = searchResultList.get(i).getText().toUpperCase();

			if (searchResultStr.contains("BMW")) {

				System.out.println((i + 1) + ": " + searchResultStr);
			}

			else {
				System.out.println((i + 1) + ": I didn't find any link contains BMW");
			}

			softAsserProcess.assertEquals(searchResultStr.contains("BMW"), true, "Check if Text exist as upper Case");
			softAsserProcess.assertAll();
		}
	}

	@AfterTest
	public void quitApplication() throws InterruptedException {
		Thread.sleep(3000);
	driver.quit();
}

}