package com.qa.opencart.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.util.ElementUtil;

import cpm.qa.opencart.constants.AppConstants;
import io.qameta.allure.Step;

public class LoginPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	private static final Logger log = LogManager.getLogger(DriverFactory.class);
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	//1. By Locator: page objects: OR
	
	private By emailID = By.id("input-email");
	private By password= By.id("input-password");
	private By loginButton = By.xpath("//input[@value='Login']");
	private By forgotPWDLink = By.linkText("Forgotten Password");
	private By createRegisterPageLink = By.linkText("Continue");
	
	//2. public page actions - methods (features)
	@Step("getting login page title step....")
	public String getLoginPageTitle() {
		String title = eleUtil.waitForTitleToBE(AppConstants.LOGIN_PAGE_TITLE, AppConstants.DEFAULT_TIME_OUT);
		log.info("the page title is: "+ title);
		//System.out.println("the page title is: "+ title);
		ChainTestListener.log("login page title ==>" + title);
		return title;
	}
	
	public String getLoginPageURL() {
		String url = eleUtil.waitForURLContains(AppConstants.LOGIN_PAGE_URL_FRACTION, AppConstants.DEFAULT_TIME_OUT);
		log.info("the page url is: "+ url);
		//System.out.println("the page url is: "+ url);
		return url;
	}
	
	public boolean isPasswordLinkExist() {
		return eleUtil.doElementIsDisplayed(forgotPWDLink);
	}
	
	@Step("Verifying register page button....")
	public boolean isCreteRegisterPageButtonExist() {
		return eleUtil.doElementIsDisplayed(createRegisterPageLink);
	}
	
	@Step("login with username: {0} and passowrd: {1} step...")
	public HomePage doLogin(String username, String pwd) throws InterruptedException {
		log.info("App creads are: ===> "+ username + " : " + pwd);
		//System.out.println("App creads are: ===> "+ username + " : " + pwd);
		eleUtil.doSendKeys(emailID, username);
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doClick(loginButton);
		return new HomePage(driver);
	}
	
	

}
