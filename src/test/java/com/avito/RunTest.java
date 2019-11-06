package com.avito;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class RunTest extends PreConditions{

	public static int csvLine = 0;
	public static String mobile, password;

	@Test(dataProvider = "getCsvData")
	public void addingAd(String rooms, String houseType, String floor, String noOfFloors, String ownership,
			String totalArea, String kitchenArea, String livingArea, String addressLocation,
			String description, String price, String proxyIp, String imagePath, String name, String status) throws IOException, InterruptedException {
		new adbExecutor().exec(udid, proxyIp);
		logger = extent.startTest("Adding ad in Avito", rooms+","+houseType+","+floor+","+noOfFloors+","+ownership+","+totalArea+","+kitchenArea
				+","+livingArea+","+addressLocation+","+description+","+price+","+proxyIp+","+imagePath+","+status);
		if (status.equalsIgnoreCase("1")) {
			launchApp();
			String[] loginData = createAnAccount(name);
			mobile = loginData[0];
			password = loginData[1];
			openCreateAdForm();
			enterAdDetails(rooms, houseType, floor, noOfFloors, ownership, totalArea, kitchenArea, livingArea, addressLocation,
					description, price);
			attachImageFromGDriver(imagePath);
			submitCreateAdForm(mobile, password, addressLocation);
			closeApp();
		}else {
			throw new SkipException("Skipped this test case because status for this row is "+status);
		}
	}
	
	
	@DataProvider
	public Object[][] getCsvData() throws IOException{
		String pathToCsv = System.getProperty("user.dir")+"/DataFile/avitoData.csv";
		String row;
		int noOfRows = 0;
		String data[];
		BufferedReader csvReader = new BufferedReader(new InputStreamReader(new FileInputStream(pathToCsv), "UTF8"));
		csvReader.readLine();
		while ((row = csvReader.readLine()) != null) {
			noOfRows++;
		}
		csvReader = new BufferedReader(new InputStreamReader(new FileInputStream(pathToCsv), "UTF-8"));
		String firstLine = csvReader.readLine();
		int column = firstLine.split(";").length;
		Object[][] csvData = new Object[noOfRows][column];
		for (int i=0; (row = csvReader.readLine()) != null; i++) {
			data = row.split(";");
			for (int j = 0; j < column; j++) {
				csvData[i][j] = data[j];
			}
		}
		return csvData;
	}
}
