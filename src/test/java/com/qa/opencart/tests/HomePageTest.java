package com.qa.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.util.ExcelUtil;

import cpm.qa.opencart.constants.AppConstants;
import cpm.qa.opencart.constants.AppError;

public class HomePageTest extends BaseTest{
	
	@BeforeClass
	public void doLoginTest() throws InterruptedException {
		homepage = loginpage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
//	@Test
//	public void dologOut() throws InterruptedException {
//		loginpage = homepage.logout();
//		Assert.assertEquals(loginpage.getLoginPageTitle(), "Account Logout", AppError.TITLE_NOT_FOUND_ERROR);
//	}
//	
	@Test
	public void HomePageTitleTest() {
		//String actTitle = homepage.getHomePageTitle();
		Assert.assertEquals(homepage.getHomePageTitle(), AppConstants.Home_PAGE_TITLE, AppError.TITLE_NOT_FOUND_ERROR);
	}
	
	@Test
	public void HomePageUrlTest() {
		String actUrl = homepage.getHomePageURL();
		Assert.assertTrue(actUrl.contains(AppConstants.Home_PAGE_URL_FRACTION), AppError.URL_NOT_FOUND_ERROR);
	}
	
	@Test
	public void logoutLinkExistTest() {
		Assert.assertTrue(homepage.isLogoutLinkExist(), AppError.ELEMENT_NOT_FOUND_ERROR);
	}
	
	
	@Test
	public void headersTest() {
		List<String> actHeaders = homepage.getHeadersList();
		System.out.println("home page headers: ==>" + actHeaders);
	}
	
	@DataProvider
	public Object[][] getSearchData(){
		return new Object[][] {
			{"macbook", 3},
			{"imac", 1},
			{"samsung", 2},
			{"airtel", 0}
		};
	}
	
	@DataProvider
	public Object[][] getProductSearchSheetData(){
		Object productData [][]= ExcelUtil.getTestData(AppConstants.PRODUCT_SHEET_NAME);
		return productData;
	}
	
	@Test(dataProvider = "getProductSearchSheetData")
	public void searchTest(String searchKey, String resultCount) throws InterruptedException {
		searchResultPage = homepage.doSearch(searchKey);
		Assert.assertEquals(searchResultPage.getProductResultsCount(), Integer.parseInt(resultCount));
		
	}
	
//	@Test(dataProvider = "getSearchData")
//	public void searchTest(String searchKey, int resultCount) throws InterruptedException {
//		searchResultPage = homepage.doSearch(searchKey);
//		Assert.assertEquals(searchResultPage.getProductResultsCount(), resultCount);
//		
//	}
}
