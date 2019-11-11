package com.avito;

import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.PointOption.point;
import static java.time.Duration.ofSeconds;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.avito.objects.appPageObjects;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import io.appium.java_client.MobileElement;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.touch.offset.PointOption;

public class PreConditions extends adbExecutor{
	
	String deviceName = "Redmi 4";
	String platformVersion = "7.1.2";
	//String udid = "192.168.183.102:5555";
	String udid = "127.0.0.1:21533";
	//String udid = "127.0.0.1:21513";
	//String udid = "emulator-5554";
	//String udid = "93EAY0AA4F";
	//String udid = "2bb1d9b99805";
	//String udid = "F9AZCY074642";
	public static AppiumDriverLocalService service;
	private AppiumServiceBuilder builder;
	private DesiredCapabilities cap;
	public static AndroidDriver<MobileElement> driver;
	ExtentReports extent;
	ExtentTest logger;
	appPageObjects appObjects = new appPageObjects();
	public String pathToCsv = System.getProperty("user.dir")+"/DataFile/avitoData.csv";
	String get_number_url = "http://sms-activate.ru/stubs/handler_api.php?api_key=4eA7312b9d239c8008fe440f798f7e32&action=getNumber&service=av&operator=any&country=0";
	String get_otp_url = "https://sms-activate.ru/stubs/handler_api.php?api_key=4eA7312b9d239c8008fe440f798f7e32&action=getStatus&id=";
	public static String number_access_id;
	String cancel_number_url = "http://sms-activate.ru/stubs/handler_api.php?api_key=4eA7312b9d239c8008fe440f798f7e32&action=setStatus&status=-1&id=";
	String complete_activation_url = "http://sms-activate.ru/stubs/handler_api.php?api_key=4eA7312b9d239c8008fe440f798f7e32&action=setStatus&status=6&id=";


	public void launchApp() throws MalformedURLException {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
		capabilities.setCapability("deviceName", deviceName);
		capabilities.setCapability("platformVersion", platformVersion);
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("automationName", "uiAutomator2");
		capabilities.setCapability("udid", udid);
		capabilities.setCapability("adbExecTimeout", 60*3);
		capabilities.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, true);
		capabilities.setCapability("appPackage", "com.avito.android");
		capabilities.setCapability("appActivity", "com.avito.android.home.HomeActivity");
		capabilities.setCapability("ignoreUnimportantViews", true);
		
		driver = new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		PageFactory.initElements(new AppiumFieldDecorator(driver), appObjects);
	}
	
	@BeforeTest
	public void startReport(){
	 extent = new ExtentReports (System.getProperty("user.dir") +"/AvitoReport.html", true);
	 extent.addSystemInfo("Host Name", "Avito App")
	       .addSystemInfo("Environment", "Automation Testing")
	       .addSystemInfo("User Name", "Sreesai");
	  extent.loadConfig(new File(System.getProperty("user.dir")+"/extent-config.xml"));
	 }
	
	@AfterMethod
	public void getResults(ITestResult result) throws Exception {
		if(result.getStatus() == ITestResult.FAILURE){
			 logger.log(LogStatus.FAIL, "Test Case Failed is "+result.getName());
			 logger.log(LogStatus.FAIL, "Test Case Failed is "+result.getThrowable());
			 String screenshotPath = getScreenshot(result.getName());
			 logger.log(LogStatus.FAIL, logger.addScreenCapture(screenshotPath));
		}else if(result.getStatus() == ITestResult.SKIP){
			 logger.log(LogStatus.SKIP, "Test Case Skipped is "+result.getName());
		}else if (result.getStatus() == ITestResult.SUCCESS) {
			logger.log(LogStatus.PASS, result.getName()+" Test Case is passed");
		}
		extent.endTest(logger);
	}
	
	@AfterTest
	public void endReport(){
       extent.flush();
       extent.close();
	 }
	
	
	public void closeApp() {
		driver.quit();
	}
	
	public String getMobileNumber() throws IOException{
		String responce = sendGET(get_number_url);
		String[] data = responce.split(":");
		number_access_id = data[1];
		String mobile = data[2].substring(1);
		return mobile;
	}
	
	public String[] createAnAccount(String name) throws IOException, InterruptedException {
		waitTillTheElementIsVisible(appObjects.addPostButtonInHomeScreen);
		appObjects.addPostButtonInHomeScreen.click();
		waitTillTheElementIsVisible(appObjects.userRegistration);
		appObjects.userRegistration.click();
		String mobileNumber = getMobileNumber();
		waitTillTheElementIsVisible(appObjects.mobileInRegistration);
		appObjects.mobileInRegistration.sendKeys(mobileNumber);
		appObjects.continueButtonInRegistration.click();
		String responce, access_code = null, password = mobileNumber+"@qqqq";
		int count = 10;
		while (count > 0) {
			responce = sendGET(get_otp_url+number_access_id);
			if (responce.split(":")[0].equalsIgnoreCase("STATUS_OK")) {
				access_code = responce.split(":")[1];
				break;
			}else {
				appObjects.backButtonInRegistration.isEnabled();
			}
			count--;
			Thread.sleep(5000);
			//appObjects.accessCodeFieldInRegistration.isDisplayed();
		}
		if (access_code == null) {
			String cancel_responce = sendGET(cancel_number_url+number_access_id);
			if (cancel_responce.equalsIgnoreCase("ACCESS_CANCEL")) {
				System.out.println(mobileNumber+":Активация Отменена");
			}else {
				System.out.println(mobileNumber+"Activation is not cancelled. Getting responce:"+cancel_responce);
			}
			appObjects.backButtonInRegistration.click();
			appObjects.backButtonInRegistration.click();
			tapOnPoint(450, 700);
			createAnAccount(name);
		}else {
			String activation_responce = sendGET(complete_activation_url+number_access_id);
			if (activation_responce.equalsIgnoreCase("ACCESS_ACTIVATION")) {
				System.out.println(mobileNumber+": service successfully activated");
			}else {
				System.out.println(mobileNumber+" service is not activated. Getting different responce :"+activation_responce);
			}
		}
		//waitTillTheElementIsVisible(appObjects.accessCodeFieldInRegistration);
		 appObjects.accessCodeFieldInRegistration.setValue(access_code);
		//appObjects.sendButtonInRegistration.click();
		if (isElementIsDisplayed(appObjects.createAnotherAccount)) {
			appObjects.createAnotherAccount.click();
		}
		waitTillTheElementIsVisible(appObjects.nameFieldInRegistraton);
		appObjects.nameFieldInRegistraton.sendKeys(name);
		appObjects.passwordFieldInRegistration.sendKeys(mobileNumber+"@qqqq");
		appObjects.registerButton.click();
		String[] loginData = {mobileNumber, password};
		return loginData;
	}
	
	public boolean isElementIsDisplayed(MobileElement element) {
		try {
			return element.isDisplayed();
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
	public void loginToUserAccount(String mobile, String password) throws InterruptedException {
		appObjects.menuButton.click();
		appObjects.userProfileButton.click();
		appObjects.loginButton.click();
		appObjects.mobileNumber.sendKeys(mobile);
		appObjects.password.sendKeys(password);
		appObjects.loginToSubmitButton.click();
		Thread.sleep(3000);
		waitTillTheElementIsVisible(appObjects.menuButton);
		Assert.assertTrue(appObjects.menuButton.isDisplayed(), "Unable to find menu button because user Login is failed");
	}
	
	public void openCreateAdForm() {
		//appObjects.menuButton.click();
		//appObjects.addPostItemInMenu.click();
		waitTillTheElementIsVisible(appObjects.propertyButton);
		appObjects.propertyButton.click();
		waitTillTheElementIsVisible(appObjects.apartmentButton);
		appObjects.apartmentButton.click();
		waitTillTheElementIsVisible(appObjects.sellOffButton);
		appObjects.sellOffButton.click();
		waitTillTheElementIsVisible(appObjects.resellersButton);
		appObjects.resellersButton.click();
		waitTillTheElementIsVisible(appObjects.createAdTitle);
		Assert.assertTrue(appObjects.createAdTitle.isDisplayed(), "Create ad page is not opened. Unable to find the create ad page title");
	}
	
	public void enterAdDetails(String rooms, String houseType, String floor, String noOfFloors, String ownership,
			String totalArea, String kitchenArea, String livingArea, String addressLocation,
			String description, String price) throws InterruptedException {
		appObjects.roomsDropDownButton.click();
		waitTillTheElementIsVisible(appObjects.roomsTextList.get(0));
		for (int i = 0; i < appObjects.roomsTextList.size(); i++) {
			System.out.println(appObjects.roomsTextList.get(i).getText());
			if (appObjects.roomsTextList.get(i).getText().equalsIgnoreCase(rooms)) {
				appObjects.roomsTextList.get(i).click();
				break;
			}
		}
		
		appObjects.houseTypeDropDownButton.click();
		waitTillTheElementIsVisible(appObjects.typesOfHouseTextList.get(0));
		appObjects.typesOfHouseTextList.get(Integer.valueOf(houseType)-1).click();
		
		appObjects.floorDropDownButton.click();
		waitTillTheElementIsVisible(appObjects.floorNumberTextList.get(0));
		boolean flag = true;
		while (flag) {
			for (int i = 0; i < appObjects.floorNumberTextList.size(); i++) {
				System.out.println(appObjects.floorNumberTextList.get(i).getText());
				if (appObjects.floorNumberTextList.get(i).getText().equalsIgnoreCase(floor)) {
					appObjects.floorNumberTextList.get(i).click();
					flag = false;
					break;
				}
			}
			if (flag) {
				swipeUp();
			}
		}
		appObjects.noOfFloorsDropDownButton.click();
		waitTillTheElementIsVisible(appObjects.noOfFloorsTextList.get(0));
		flag = true;
		while (flag) {
			for (int i = 0; i < appObjects.noOfFloorsTextList.size(); i++) {
				System.out.println(appObjects.noOfFloorsTextList.get(i).getText());
				if (appObjects.noOfFloorsTextList.get(i).getText().equalsIgnoreCase(noOfFloors)) {
					appObjects.noOfFloorsTextList.get(i).click();
					flag = false;
					break;
				}
			}
			if (flag) {
				swipeUp();
			}
		}
		appObjects.ownershipDropDownButton.click();
		waitTillTheElementIsVisible(appObjects.ownershipTextList.get(0));
		appObjects.ownershipTextList.get(0).click();
		
		appObjects.totalArea.sendKeys(totalArea);
		appObjects.kitchenArea.sendKeys(kitchenArea);
		swipeUp();
		appObjects.livingArea.sendKeys(livingArea);
		addLocation(addressLocation);
		appObjects.descriptionField.sendKeys(description);
		swipeUp();
		appObjects.priceField.sendKeys(price+"0");
	}
	
	//Need to complete the code based on google drive location
	public void attachImageFromGDriver(String imagePath) throws InterruptedException {
		appObjects.addImagesButton.click();
		appObjects.galleryButton.click();
		boolean flag = true;
		while (flag) {
			for (int i = 0; i < appObjects.galleryImagesList.size(); i++) {
				if (appObjects.galleryImagesList.get(i).getText().equalsIgnoreCase(imagePath)) {
					appObjects.galleryImagesList.get(i).click();
					flag = false;
					break;
				}
			}
			if (flag) {
				Thread.sleep(2000);// no
				swipeUp();
				//swipeUp();
			}
		}
		Thread.sleep(5000);
		waitTillTheElementIsVisible(appObjects.continueButtonInImagesList);
		appObjects.continueButtonInImagesList.click();
		Thread.sleep(3000);
	}
	
	//complete code and Verify after submitting the ad
	public void submitCreateAdForm(String mobile, String password, String location, String imagePath) throws InterruptedException, IOException {
		swipeUp();
		Thread.sleep(5000);
		waitTillTheElementIsVisible(appObjects.continueButton);
		while (true) {
			try {
				appObjects.continueButton.isDisplayed();
				appObjects.continueButton.click();
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		//Thread.sleep(5000);
		for (int i = 0; i < appObjects.noEmailAtEnd.size(); i++) {
			if (appObjects.noEmailAtEnd.get(i).getText().equalsIgnoreCase("Электронная почта")) {
				System.out.println(mobile+"@gmail.com");
				appObjects.emailField.sendKeys(mobile+"@gmail.com");
				break;
			}
		}
		
		//appObjects.submitAdButton.click();
		//Thread.sleep(1000);
		appObjects.submitAdButton.click();
		waitTillTheElementIsVisible(appObjects.noPromotionAds);
		appObjects.noPromotionAds.click();
		swipeUp();
		swipeUp();
		String adNumber = appObjects.advatiseNumber.getText().split(",")[0].split("№")[1];
		addAdvatiseNumberToFile(adNumber, mobile, password, location);
		updateStatusInDataFile(imagePath);
		//Assert.assertTrue(appObjects.ad.isDisplayed(), "Post is not added successfully");
	}
	

	public void addAdvatiseNumberToFile(String adNumber, String mobile, String password, String location) throws IOException {
		String adUrl = "https://www.avito.ru/"+adNumber+";"+mobile+";"+password+";LOCATION-"+location;
		String successFilePath = System.getProperty("user.dir")+"/DataFile/Success.txt";
		
		BufferedWriter fileWriter = new BufferedWriter(new FileWriter(successFilePath, true));
		fileWriter.append("\r\n");
		fileWriter.append(adUrl);
		fileWriter.close();
	}
	
	//Need to complete the code for adding location
	public void addLocation(String addressLocation) throws InterruptedException {
		appObjects.addressLocation.click();
		waitTillTheElementIsVisible(appObjects.enterLocation);
		Thread.sleep(3000);
		appObjects.enterLocation.sendKeys(addressLocation);
		Thread.sleep(6000);
		appObjects.enterLocation.sendKeys(addressLocation);
		appObjects.enterLocation.click();
		appObjects.locationsDropDownList.get(0).click();
		Thread.sleep(3000);
		appObjects.submitLocation.click();
	}
	
	public void swipeUp() {
	      Dimension size = driver.manage().window().getSize();
	      int starty = (int) (size.height * 0.8);
	      int endy = (int) (size.height * 0.2);
	      int startx = (int) (size.width / 2.2);
	      try {
	          new TouchAction((PerformsTouchActions) driver).press(point(startx, starty)).waitAction(waitOptions(ofSeconds(3)))
	                  .moveTo(point(startx, endy)).release().perform();
	          //reportLogging("Swipe up");
	      } catch (Exception e) {
	    	  swipeUp();
	      }
	  }
	
	public static String getScreenshot(String screenshotName) throws Exception {
        //below line is just to append the date format with the screenshot name to avoid duplicate names 
	String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
	TakesScreenshot ts = (TakesScreenshot) driver;
	File source = ts.getScreenshotAs(OutputType.FILE);
	//after execution, you could see a folder "FailedTestsScreenshots" under src folder
	String destination = System.getProperty("user.dir") + "/FailedTestsScreenshots/"+screenshotName+dateName+".png";
	File finalDestination = new File(destination);
	FileUtils.copyFile(source, finalDestination);
	        //Returns the captured file path
	return destination;
	}
	
	public static String sendGET(String get_url) throws IOException {
		URL obj = new URL(get_url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		int responseCode = con.getResponseCode();
		System.out.println("GET Response Code :: " + responseCode);
		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// print result
			System.out.println(response.toString());
			return response.toString();
		} else {
			System.out.println("GET request not worked");
			return null;
		}
	}
	public void updateStatusInDataFile(String imagePath) throws IOException {
		File inputFile = new File(pathToCsv);
		CSVReader reader = new CSVReader(new FileReader(inputFile), ';');
		List<String[]> csvBody = reader.readAll();
		for (int i = 0; i < csvBody.size(); i++) {
			if (csvBody.get(i)[12].equalsIgnoreCase(imagePath)) {
				csvBody.get(i)[14] = "1";
				break;
			}
		}
		reader.close();
		CSVWriter writer = new CSVWriter(new FileWriter(inputFile), ';', CSVWriter.NO_QUOTE_CHARACTER);
		writer.writeAll(csvBody);
		writer.flush();
		writer.close();
	}
	
	public void tapOnPoint(int x1, int y1) {
	      new TouchAction((AndroidDriver<MobileElement>) driver).tap(PointOption.point(x1, y1)).perform();
	  }
	
	public void waitTillTheElementIsVisible(MobileElement element) {
	      WebDriverWait wait = new WebDriverWait(driver, 10);
	      wait.until(ExpectedConditions.visibilityOf(element));
	  }
}
