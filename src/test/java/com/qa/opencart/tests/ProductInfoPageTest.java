package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class ProductInfoPageTest extends BaseTest{
	
	@BeforeClass
	public void productInfoSetup() throws InterruptedException {
		homepage = loginpage.doLogin("mohitramgupt@gmail.com", "Pass@word88");
	}
		
	@Test
	public void productSearchHeaderTest() throws InterruptedException {
		searchResultPage = homepage.doSearch("macbook");
		productInfoPage = searchResultPage.selectProduct("MacBook Pro");
		String actualProductHeader = productInfoPage.getProductHeader();
		Assert.assertEquals(actualProductHeader, "MacBook Pro");
	}
}
