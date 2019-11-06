package com.avito;

import org.testng.TestNG;

public class RunAvito {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TestNG testng = new TestNG();
		testng.setTestClasses(new Class[] { RunTest.class });
		testng.run();
	}

}
