package com.qa.opencart.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
//import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.aventstack.chaintest.service.ChainPluginService;
//import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.CommonsPage;
import com.qa.opencart.pages.HomePage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductInfoPage;
import com.qa.opencart.pages.SearchResultsPage;

import io.qameta.allure.Description;

//@Listeners(ChainTestListener.class)
public class BaseTest {
	
	WebDriver driver;
	DriverFactory df;
	protected Properties prop;
	
	protected CommonsPage commonsPage;
	protected LoginPage loginpage; 
	protected HomePage homepage; 
	protected SearchResultsPage searchResultPage;
	protected ProductInfoPage productInfoPage;
	
	@Parameters({"browser"})
	@BeforeTest(description = "setup: init the driver and properties")
	public void setup(String browserName) {
		df = new DriverFactory();
		prop = df.initProp();
		
		if(browserName !=null) {
			prop.setProperty("browser", browserName);
		}
		driver = df.initDriver(prop);
		ChainPluginService.getInstance().addSystemInfo("Build#", "1.0");
		ChainPluginService.getInstance().addSystemInfo("Owner Name#", "Mohit");
		loginpage = new LoginPage(driver);
		commonsPage = new CommonsPage(driver);
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.get(prop.getProperty("url"));
		
	}
	
	
	
	
	/*
	 * This method will help to attach screenshot for every steps
	 */
//	@AfterMethod
//	public void attachScreensgot() {
//		ChainTestListener.embed(df.getScreenshotFile(), "image/png");
//	}
	
	
	
	
	
	/*
	 * This method will help to attach screenshot only for failuer test
	 * Other supported types
	 *    "image/png",
            "image/jpg",
            "image/jpeg",
            "image/gif",
            "image/bmp",
            "image/webp",
            "image/tiff"
	 */
	
	
	@AfterMethod
	public void attachScreensgot(ITestResult result) {
		
		if(!result.isSuccess()) {
		ChainTestListener.embed(df.getScreenshotFile(), "image/png");
	}
}
	
	
	@AfterTest(description = "closing the browser")
	public void tearDown() {
		//driver.quit();
	}

}
