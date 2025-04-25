package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.util.ElementUtil;

public class ProductInfoPage {
	
private WebDriver driver;
private ElementUtil eleUtil;
	
	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	private By productHeader = By.tagName("h1");
	
	public String getProductHeader() {
		String header = eleUtil.doElementGetText(productHeader);
		System.out.println("product header: ==>" + header);
		return header;
	}

}
