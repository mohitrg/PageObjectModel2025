package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import org.openqa.selenium.io.FileHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.qa.opencart.exception.FrameworkException;

import cpm.qa.opencart.constants.AppConstants;
import io.qameta.allure.Step;

public class DriverFactory {
	
	WebDriver driver;
	Properties prop;
	
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
	private static final Logger log = LogManager.getLogger(DriverFactory.class);
	
	@Step("init the driver using properties: {0}")
	public WebDriver initDriver(Properties prop) {
		String browserName = prop.getProperty("browser");
		
		//System.out.println("browser name is: "+ browserName);
		log.info("browser name is: "+ browserName);
		
		switch (browserName.trim().toLowerCase()) {
		case "chrome":
			driver = new ChromeDriver();
			break;
		case "firefox":
			driver = new FirefoxDriver();
			break;

		default:
			//System.out.println("plz pass the valid browser name: "+ browserName);
			log.error("plz pass the valid browser name: "+ browserName);
			throw new FrameworkException("===invalid browser name===");
			
		}
		
		return driver;
		
	}
	/**
	 * This method is used to init the properties from .properties file
	 * @return
	 */
	
	public Properties initProp() {
		prop = new Properties();
		try {
			FileInputStream ip = new FileInputStream(AppConstants.CONFIG_PROP_FILE_PATH);
			prop.load(ip);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return prop;
	}
	
	public static WebDriver getDriver() {
		return tlDriver.get();
	}

	/**
	 * take screenshot
	 */
	
//	public String getScreenshot() {
//		File srcFile = ((TakesScreenshot)(driver)).getScreenshotAs(OutputType.FILE);// temp dir
//		String path = System.getProperty("user.dir") + "/screenshot/" + "_" + System.currentTimeMillis() + ".png";
//		File destination = new File(path);
//
//		try {
//			FileHandler.copy(srcFile, destination);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		return path;
//	}

	public File getScreenshotFile() {
		File srcFile = ((TakesScreenshot) (driver)).getScreenshotAs(OutputType.FILE);// temp dir
		return srcFile;
	}
//
//	public static byte[] getScreenshotByte() {
//		return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);// temp dir
//
//	}
//
//	public static String getScreenshotBase64() {
//		return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BASE64);// temp dir
//
//	}

}
