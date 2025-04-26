package com.qa.opencart.tests;

import static org.testng.Assert.assertEquals;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.base.BaseTest;

import cpm.qa.opencart.constants.AppConstants;
import cpm.qa.opencart.constants.AppError;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import io.qameta.allure.Story;


@Epic("Epic 100: design login page for open cart")
@Story("US 101: design the various features open cart login page")
@Feature("Feature 50: Login Page Feature")
@Owner("Mohit Gupta")
public class LoginPageTest extends BaseTest {
	
	
	@Test
	@Description("checking login page title....")
	@Severity(SeverityLevel.NORMAL)
	
	
	public void loginPageTitleTest() {
		ChainTestListener.log("Verifying loginpage title");
		String actTitle = loginpage.getLoginPageTitle();
		Assert.assertEquals(actTitle, AppConstants.LOGIN_PAGE_TITLE, AppError.TITLE_NOT_FOUND_ERROR);
	}
	
	
	@Test
	@Description("checking login page URL....")
	@Severity(SeverityLevel.NORMAL)
	@Step("getting login page URL step....")
	public void loginPageUrlTest() {
		String actUrl = loginpage.getLoginPageURL();
		Assert.assertTrue(actUrl.contains(AppConstants.LOGIN_PAGE_URL_FRACTION), AppError.URL_NOT_FOUND_ERROR);
	}
	
	
	@Test
	@Description("checking forgot password link....")
	@Severity(SeverityLevel.BLOCKER)
	@Step("Verifying forgot password link is exist or not step....")
	public void forgotPwdLinkExistTest() {
		Assert.assertTrue(loginpage.isPasswordLinkExist(), AppError.ELEMENT_NOT_FOUND_ERROR);
	}
	
	@Test
	@Description("checking register button link....")
	@Severity(SeverityLevel.NORMAL)
	@Step("Verifying register link is exist or not...")
	public void registerLinkExistTest() {
		Assert.assertTrue(loginpage.isCreteRegisterPageButtonExist(), AppError.ELEMENT_NOT_FOUND_ERROR);
	}
	
	
	@Test(priority = Integer.MAX_VALUE)
	@Description("checking user is able to login with correct credentials....")
	@Severity(SeverityLevel.CRITICAL)
	public void loginTest() throws InterruptedException {
	homepage = loginpage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	Assert.assertEquals(homepage.getHomePageTitle(), AppConstants.Home_PAGE_TITLE, AppError.TITLE_NOT_FOUND_ERROR);
	}
	
	//
	
	
	@Test(enabled = true)
	@Description("checking page logo....")
	@Severity(SeverityLevel.CRITICAL)
	public void logoTest() {
		Assert.assertTrue(commonsPage.isLogoDisplayed(), AppError.LOGO_NOT_FOUND_ERROR);
	}
	
	
	@DataProvider
	public Object[][] getFooterData() {
		return new Object[][] {
			{"About Us"},
			{"Contact Us"},
			{"Specials"},
			{"Order History"}
		};
	}
	
	//@Description("checking page footers....")
	//@Severity(SeverityLevel.NORMAL)
	@Test(dataProvider = "getFooterData")
	@Description("checking page footers....")
	@Severity(SeverityLevel.MINOR)
	public void footerTest(String footerLink) {
		Assert.assertTrue(commonsPage.checkFooterLink(footerLink));
	}

}
