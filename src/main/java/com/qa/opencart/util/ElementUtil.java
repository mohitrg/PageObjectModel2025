package com.qa.opencart.util;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.management.RuntimeErrorException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ElementUtil {
	
	private WebDriver driver;
	
	public ElementUtil(WebDriver driver) {
		this.driver = driver;
	}
	
	public WebElement getElement(By locator) {
		//return waitForElementVisible(locator, 10);
		return driver.findElement(locator);
		
	}
	
	private void nullCheck(CharSequence... value) {
		if(value==null) {
			throw new RuntimeException("===Value/Prop/Attr can not be null===");
		}
	}
	
	public void doSendKeys(By locator, CharSequence... value) throws InterruptedException {
		Thread.sleep(2000);
		nullCheck(value);
		WebElement element = getElement(locator);
		element.clear();
		element.sendKeys(value);
	}
	
	public void doSendKeys(WebElement element, CharSequence... value) throws InterruptedException {
		Thread.sleep(2000);
		nullCheck(value);
		element.clear();
		element.sendKeys(value);
	}
	
	public void doClick(By locator) throws InterruptedException {
		Thread.sleep(2000);
		getElement(locator).click();
	}
	
	public String doElementGetText(By locator) {
		return getElement(locator).getText();
	}
	
	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}
	
	public boolean doElementIsDisplayed(By locator) {
		try {
		return getElement(locator).isDisplayed();
	} catch (NoSuchElementException e) {
		System.out.println("element is not presnt");
		return false;
		}
	}
	
	public boolean IsElementEnabled(By locator) {
		return getElement(locator).isEnabled();
	}
	
	public boolean IsElementSelected(By locator) {
		return getElement(locator).isSelected();
	}

	
	// Utility written to verify single element
	public boolean isElementPresent(By locator) {
		if(getElements(locator).size()==1) {
			System.out.println("email field is available on the page");
			return true;
		} else {
			System.out.println("email field is not available on the page");
			return false;
		}
	}
	
	// Utility written to verify more than one element
	
	public boolean isElementPresent(By locator, int elementCount) {
		if(getElements(locator).size()==elementCount) {
			System.out.println("Element is available on the page " +elementCount+ " time" );
			return true;
		} else {
			System.out.println("Element is not available on the page " +elementCount+ " time" );
			return false;
		}
	}
	
	public String doGetDomAttribute(By locator, String AttrName) {
		nullCheck(AttrName);
		return getElement(locator).getDomAttribute(AttrName);
	}
	
	public String doGetDomProperty(By locator, String PropName) {
		nullCheck(PropName);
		return getElement(locator).getDomProperty(PropName);
	}
	
	/*****************DropDown Utils************************/
	
	public void doSelectDropdownbyIndex(By locator, int indexValue) {
		Select select = new Select(getElement(locator));
		select.selectByIndex(indexValue);
		
	}
	
	public void doSelectDropdownbyVisibleText(By locator, String TextValue) {
		Select select = new Select(getElement(locator));
		select.selectByVisibleText(TextValue);
		
	}
	
	public void doSelectDropdownbyValue(By locator, String Value) {
		Select select = new Select(getElement(locator));
		select.selectByValue(Value);
		
	}
	
	public void doSelectMultipleDropdown(By locator, String value) {
		Select select = new Select(getElement(locator));
		System.out.println(select.isMultiple());
		
		if(select.isMultiple()) {
			select.selectByVisibleText(value);
			
		}
	}
	
	public void doPrintDropdownTexts(By locator) {
		Select select = new Select(getElement(locator));
		List<WebElement> optionsList = select.getOptions();
		System.out.println("option Size "+ optionsList.size());
				
		for(WebElement e : optionsList) {
			String text = e.getText();
			System.out.println(text);
		}
		
		
	}
	
	public List<String> doGetDropdownOptions(By locator) {
		Select select = new Select(getElement(locator));
		List<WebElement> optionsList = select.getOptions();
		System.out.println("option Size "+ optionsList.size());
		
		List<String> optionsValueList = new ArrayList<String>();
		for(WebElement e : optionsList) {
			String text = e.getText();
			optionsValueList.add(text);
		}
		
		return optionsValueList;
	}
	
	/************Select Dropdwon without using select methods***************/
	
	public void doClickDropdown(By locator, String value) {
		Select select = new Select(getElement(locator));
		List<WebElement> optionsList = select.getOptions();
		boolean flag = false;
		
		for (WebElement e : optionsList) {
			String text = e.getText();
			if(text.contains(value)) {
				e.click();
				flag=true;
				break;
				} 
			}
		
		if (flag) {
			System.out.println(value + " is selected and available");
		} else {
			System.out.println(value + " is not available");
		}
		
	}
	
	/************Search***************/
	
	public void doSearch(By searchField, By suggestions, String searchKey, String actualValue) throws InterruptedException {
		
		
		doSendKeys(searchField, searchKey);
		Thread.sleep(2000);
		
		List<WebElement> suggLists = getElements(suggestions);
		System.out.println(suggLists.size());
		boolean flag = false;
		
		for(WebElement e : suggLists) {
			String text = e.getText();
			System.out.println(text);
			
			if(text.contains(actualValue)) {
				e.click();
				flag=true;
				break;
			}
		}
		if (flag) {
			System.out.println(actualValue + " is selected and available");
		} else {
			System.out.println(actualValue + " is not available");
		}
	}
	
	/**
	 * This method os handling single/multiple/all choice selection. Please pass "all" to select all the choices.
	 * selectChoice(multiSelectSeacrhBox, multiChoices, "all");
	 * @param multiSelectSeacrhBox
	 * @param multiChoices
	 * @param choiceValue
	 * @throws InterruptedException
	 */
	public void selectChoice(By multiSelectSeacrhBox, By multiChoices, String... choiceValue) throws InterruptedException {
		
		doClick(multiSelectSeacrhBox);
		Thread.sleep(3000);

		List<WebElement> choices = getElements(multiChoices);
		System.out.println(choices.size());

		if (choiceValue[0].equalsIgnoreCase("all")) {
			// select all the choices one by one
			for (WebElement e : choices) {
				e.click();
			}
		} else {

			for (WebElement e : choices) {
				String text = e.getText();
				System.out.println(text);

				for (String ch : choiceValue) {
					if (text.equalsIgnoreCase(ch)) {
						e.click();
					}
				}

			}

		}
	}
	
	//**********************Actions Util*******************//
	
	public void doActionsSendKeys(By locator, CharSequence... value) {
		Actions act = new Actions(driver);
		act.sendKeys(getElement(locator), value).build().perform();
		
	}
	
	public void doActionsClick(By locator) {
		Actions act = new Actions(driver);
		act.click(getElement(locator)).build().perform();
		
	}
	
	public void doHandleSubMenu(By level1, By level2, By level3, By level4) throws InterruptedException {
		
		Actions act = new Actions(driver);
		getElement(level1).click();
		Thread.sleep(4000);
		act.moveToElement(getElement(level2)).build().perform();
		Thread.sleep(2000);
		act.moveToElement(getElement(level3)).build().perform();
		Thread.sleep(2000);
		getElement(level4).click();
		
	}
	
	public void doHandleSubMenu(By level1, By level2) throws InterruptedException {
		
		Actions act = new Actions(driver);
		act.moveToElement(getElement(level1)).build().perform();
		Thread.sleep(2000);
		getElement(level2).click();
		
	}
	
	public void doSendKeysWithPause(By locator, String value, long pauseTime) {
		
		Actions act = new Actions(driver);
	
		char val[] = value.toCharArray();
		
		for(char ch : val) {
			act.pause(pauseTime).sendKeys(getElement(locator), String.valueOf(ch))
				.pause(pauseTime).perform();
		}
		
		
	}
	
	//**************** Wait for Element(s)*********************//
	
	/**
	 * An expectation for checking that an element is present on the DOM of a page.
	 * This does not necessarily mean that the element is visible
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	
	public WebElement waitForElementPresence(By locator, long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		
	}
	
	public WebElement waitForElementPresence(By locator, long timeOut, long pollingTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofSeconds(pollingTime));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		
	}
	
	/**
	 * An expectation for checking that an element is present on the DOM of a page and visible.
	 * Visibility means that the element is not only displayed but also has a height and width that is greater than 0.
	 * @param locator
	 * @param timeOut
	 * @return
	 */
		
	public WebElement waitForElementVisible(By locator, long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		
	}
	
	public WebElement waitForElementVisible(By locator, long timeOut, long pollingTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofSeconds(pollingTime));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		
	}
	
	/**
	 * An expectation for checking that there is at least one element present on a web page.
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	
	public List<WebElement> waitForElementsPresence( By locator, long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
		}catch (TimeoutException e) {
			return Collections.emptyList();
		}
		
		
	}
	
	/**
	 * An expectation for checking that all elements present on the web page that match the locator are visible.
	 * Visibility means that the elements are not only displayed but also have a height and width that is greater than 0.
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	
	public List<WebElement> waitForElementsVisible( By locator, long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
		}catch (TimeoutException e) {
			return Collections.emptyList();
		}
		
		
	}
	
	/**
	 * An expectation for checking an element is visible and enabled such that you can click it.
	 * @param locator
	 * @param timeOut
	 */
	public void clickElementWhenReady(By locator, long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
	}
	
	
	public WebElement waitForElementVisibleUsingFluentFeatures(By locator, long timeOut, long pollingTime) {
		
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofSeconds(pollingTime))
				.ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class)
				.withMessage("====element is not found====");
		
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

	}
	
	
	//**************** Wait for title*********************//
	
	/**
	 * An expectation for checking that the title contains a case-sensitive substring
	 * @param fractionText
	 * @param timeOut
	 * @return
	 */
	
	public String waitForTitleContains(String fractionText, long timeOut) {
		
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		
		try {
			if(wait.until(ExpectedConditions.titleContains(fractionText))) {
			return driver.getTitle();
			}
		}	catch(TimeoutException e) {
			System.out.println("title is not present after "+ timeOut + " seconds");
			
		}
		
		return null;
	}
	
	/**
	 * An expectation for checking the title of a page.
	 * @param title
	 * @param timeOut
	 * @return
	 */
	
	public String waitForTitleToBE(String title, long timeOut) {
		
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		
		try {
			if(wait.until(ExpectedConditions.titleIs(title))) {
			return driver.getTitle();
			}
		}	catch(TimeoutException e) {
			System.out.println("title is not present after "+ timeOut + " seconds");
			
		}
		
		return null;
	}
	
	//**************** Wait for URL*********************//
	
	public String waitForURLContains(String fractionURL, long timeOut) {
		
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		
		try {
			if(wait.until(ExpectedConditions.urlContains(fractionURL))) {
			return driver.getCurrentUrl();
			}
		}	catch(TimeoutException e) {
			System.out.println("URL is not present after "+ timeOut + " seconds");
			
		}
		
		return null;
	}
	
	public String waitForURLToBE(String URL, long timeOut) {
		
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		
		try {
			if(wait.until(ExpectedConditions.urlToBe(URL))) {
			return driver.getCurrentUrl();
			}
		}	catch(TimeoutException e) {
			System.out.println("URL is not present after "+ timeOut + " seconds");
			
		}
		
		return null;
	}
	
	//**************** Wait for alert*********************//
	
	
	public Alert waitForAlertsUsingFluentFeatures(long timeOut, long pollingTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofSeconds(pollingTime))
				.ignoring(NoSuchElementException.class)
				.withMessage("====No ALert is not found====");
		
		return wait.until(ExpectedConditions.alertIsPresent());

	}
	
	public Alert waitForAlerts(long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.alertIsPresent());
	}
	
	public String getAlertText(long timeOut) {
		return waitForAlerts(timeOut).getText();
	}
	
	public void acceptAlert(long timeOut) {
		waitForAlerts(timeOut).accept();
	}
	
	public void dismissAlert(long timeOut) {
		waitForAlerts(timeOut).dismiss();
	}
	
	public void alertSendKeys(String text, long timeOut) {
		waitForAlerts(timeOut).sendKeys(text);
	}
	
	//**************** Wait for frame*********************//
	
	
	public void waitForFrameUsingByLocatorAndSwitchToItUsingFluentFeatures(By frameLocator, long timeOut, long pollingTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofSeconds(pollingTime))
				.ignoring(NoSuchElementException.class)
				.withMessage("====Frame is not found====");
		
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
	}
	
	public void waitForFrameUsingByLocatorAndSwitchToIt(By frameLocator, long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
	}
	
	public void waitForFrameUsingUndixAndSwitchToIt(int frameIndex, long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
	}
	
	public void waitForFrameUsingIDOrNameAndSwitchToIt(String frameIDOrName, long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIDOrName));
	}
	
	public void waitForFrameUsingElementAndSwitchToIt(WebElement frameElement, long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
	}
	
	//**************** Wait for Window*********************//
	
	public Boolean waitForWindow(int numberOfWindows, long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
		return wait.until(ExpectedConditions.numberOfWindowsToBe(numberOfWindows));
	}
		catch(TimeoutException e) {
			System.out.println("number of windows are not matched....");
			return false;
		}

	}
	
	public boolean isPageLoaded(long timeOut) {
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		String isPageLoaded = 
				wait.until(ExpectedConditions.jsReturnsValue("return document.readyState == 'complete'")).toString();
		return Boolean.parseBoolean(isPageLoaded);
		
	}
	
}