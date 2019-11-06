package com.avito;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.DesiredCapabilities;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class adbExecutor {

	public static void exec(String deviceId,String ipAddress) throws IOException, InterruptedException {
        Process process = null;
        String commandString;
        if(deviceId != null) {
            commandString = String.format("%s", "adb -s " + deviceId + " shell settings put global http_proxy " + ipAddress);
        }else
            commandString = String.format("%s", "adb shell settings put global http_proxy " + ipAddress);
        System.out.print("Command is "+commandString+"\n");
        try {
            process = Runtime.getRuntime().exec(commandString);
            process.waitFor(3, TimeUnit.SECONDS);
            System.out.println("adb commad is run successfully");
        } catch (Exception e) {
        	System.out.println("Running adb command is failed");
        	e.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.print(line+"\n");
        }
    }
	public static ThreadLocal<AndroidDriver> webDriver = new ThreadLocal<AndroidDriver>();
	
	public void setDriver(AndroidDriver<MobileElement> driver) {
		webDriver.set(driver);
	}
	
	public static AndroidDriver<MobileElement> getDriver() {
		return webDriver.get();
	}
}
