package avito;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.testng.annotations.Test;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;


public class test {

	public static void main1() throws IOException {
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
	public String pathToCsv = System.getProperty("user.dir")+"/DataFile/avitoData.csv";
	@Test
	public void updateStatusInDataFile() throws IOException {
		String imagePath = "1870358980.jpg";
		File inputFile = new File(pathToCsv);
		CSVReader reader = new CSVReader(new FileReader(inputFile), ';');
		List<String[]> csvBody = reader.readAll();
		for (int i = 0; i < csvBody.size(); i++) {
			System.out.println(csvBody.get(i)[12]);
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
		System.out.println("DONE");
	}

}
