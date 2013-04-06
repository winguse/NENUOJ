package cn.edu.nenu.acm.oj.util;

import info.monitorenter.cpdetector.io.CodepageDetectorProxy;
import info.monitorenter.cpdetector.io.JChardetFacade;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.Ostermiller.util.CSVParser;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ExcelTools {
	/**
	 * Get the first sheet from an excel file. thanks to Isun's virtual-judge.
	 * @author Isun
	 * @param xls
	 * @throws IndexOutOfBoundsException,BiffException,FileNotFoundException,IOException
	 * @return a two dimension of String array.
	 */
	public static String[][] splitCellsFromExcel(File xls) throws IndexOutOfBoundsException, BiffException, FileNotFoundException, IOException {
		Sheet rs = Workbook.getWorkbook(new FileInputStream(xls)).getSheet(0);
		ArrayList<String[]> tmpContent = new ArrayList<String[]>();
		for (int i = 0; i < rs.getRows(); i++) {
			List<String> row = new ArrayList<String>(); 
			for (int j = 0; j < rs.getColumns(); j++) {
				row.add(rs.getCell(j, i).getContents().trim());
			}
			tmpContent.add((String[]) row.toArray(new String[0]));
		}
		return tmpContent.toArray(new String[0][]);
	}

	/**
	 * Get the first sheet from an csv file. thanks to Isun's virtual-judge.
	 * @author Isun
	 * @param csv
	 * @return 
	 * @throws IOException
	 */
	public static String[][] splitCellsFromCsv(File csv) throws IOException {
		CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();  
		detector.add(JChardetFacade.getInstance());  
		Charset charset = Charset.forName("GB2312");
		InputStream inputStream = new BufferedInputStream(new FileInputStream(csv));
		int length = 100000;
		while (length > 5) {
			try {
				charset = detector.detectCodepage(inputStream, length);
				break;
			} catch (Exception ex) {
				ex.printStackTrace();
				length = length * 2 / 3;
			}
		}
		CSVParser shredder = new CSVParser(new InputStreamReader(inputStream, charset));
		return shredder.getAllValues();
	}
}
