import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.github.bonigarcia.wdm.WebDriverManager;


public class GetTheTitleFacebook {

	public WebDriver driver;
	public String urlFaceBook="https://www.facebook.com/";
	SoftAssert softAsserProcess= new SoftAssert();

@BeforeTest
public void loginSite() {
	WebDriverManager.chromedriver().setup();
	driver = new ChromeDriver();
	driver.manage().window().maximize();
	driver.get(urlFaceBook);
	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1000));
		
}

@Test()
public void testTheTitle () {
	
	String actualTitle = driver.getTitle();
	System.out.println("The Actual Title is: " + actualTitle);
	
	softAsserProcess.assertEquals(actualTitle, PassParameters.expectedTitle,"test titles");
	softAsserProcess.assertAll();
}


@AfterTest
public void quitApplication () throws InterruptedException {
	Thread.sleep(3000);
	driver.quit();
}



}