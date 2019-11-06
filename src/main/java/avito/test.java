package avito;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class test {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String adLine = "Объявление: №1797232316, размещено сегодня, 01:54";
		String adNumber = adLine.split(",")[0].split("№")[1];
		System.out.println(adNumber);
		String adUrl = "https://www.avito.ru/"+adNumber;
		String successFilePath = System.getProperty("user.dir")+"/DataFile/Success.txt";
		
		//BufferedReader fileData = new BufferedReader(new InputStreamReader(new FileInputStream(successFilePath)));
		BufferedWriter fileWriter = new BufferedWriter(new FileWriter(successFilePath, true));
		fileWriter.append("\r\n");
		fileWriter.append(adUrl);
		fileWriter.close();
		
	}

}
