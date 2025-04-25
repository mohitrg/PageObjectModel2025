package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.util.ElementUtil;

import cpm.qa.opencart.constants.AppConstants;

public class HomePage {
	
private WebDriver driver;
private ElementUtil eleUtil;
	
	public HomePage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	//1. private By locators:
		private By logoutLink = By.linkText("Logout");
		private By headers = By.cssSelector("div#content > h2");
		private By search = By.name("search");
		private By searchIcon = By.cssSelector("div#search button");
		
		//2. Public page actions
		public String getHomePageTitle() {
			String title = eleUtil.waitForTitleToBE(AppConstants.Home_PAGE_TITLE, AppConstants.DEFAULT_TIME_OUT);
			System.out.println("the page title is: "+ title);
			return title;
		}
		
		public String getHomePageURL() {
			String url = eleUtil.waitForURLContains(AppConstants.Home_PAGE_URL_FRACTION, AppConstants.DEFAULT_TIME_OUT);
			System.out.println("the page url is: "+ url);
			return url;
		}

		public boolean isLogoutLinkExist() {
			return eleUtil.doElementIsDisplayed(logoutLink);
		}
		
		public LoginPage logout() throws InterruptedException {
			if(isLogoutLinkExist()) {
				eleUtil.doClick(logoutLink);
			}
			return new LoginPage(driver);
		}
		
		public List<String> getHeadersList() {
			List<WebElement> headersList = eleUtil.waitForElementsVisible(headers, AppConstants.SHORT_TIME_OUT);
			//List<WebElement> headersList = driver.findElements(headers);
			List<String> headersValList = new ArrayList<String>();
			for(WebElement e : headersList) {
				String text = e.getText();
				headersValList.add(text);
			}
			return headersValList;
		}
		
		public SearchResultsPage doSearch(String searchKey) throws InterruptedException {
			System.out.println("search key: "+ searchKey);
			WebElement searchEle = eleUtil.waitForElementVisible(search, AppConstants.SHORT_TIME_OUT);
			eleUtil.doSendKeys(searchEle, searchKey);
			eleUtil.doClick(searchIcon);
			return new SearchResultsPage(driver);
		}

}
