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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.edu.nenu.acm.oj.eto.ReplayDataInvalidException;

import com.Ostermiller.util.CSVParser;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ExcelTools {
	/**
	 * Get the first sheet from an excel file. thanks to Isun's virtual-judge.
	 * 
	 * @author Isun
	 * @param xls
	 * @throws IndexOutOfBoundsException
	 *             ,BiffException,FileNotFoundException,IOException
	 * @return a two dimension of String array.
	 */
	public static String[][] splitCellsFromExcel(File xls) throws IndexOutOfBoundsException, BiffException,
			FileNotFoundException, IOException {
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
	 * 
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

	public static Pair<Map<String, Map<String, Integer>>, Map<Integer, RankListCellExpression>> getParseInfo(
			File replayData,String replayDataContentType,String replayDataFileName,int problemCount,long contestLength) throws ReplayDataInvalidException {
		String cells[][] = null;
		Map<String, Map<String, Integer>> selections = null;
		Map<Integer, RankListCellExpression> indexedExpression = null;
		try {
			System.out.println(replayData.getName());
			if (replayDataFileName.toLowerCase().endsWith(".xls")||"application/vnd.ms-excel".endsWith(replayDataContentType)) {
				cells = ExcelTools.splitCellsFromExcel(replayData);
			} else if (replayDataFileName.toLowerCase().endsWith(".csv")) {
				cells = ExcelTools.splitCellsFromCsv(replayData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (cells != null) {
			RankListCellParser cellParser = new RankListCellParser();
			cellParser.setContestLength(contestLength);
			for (int i = 0; i < cells.length; i++) {
				if(cells[i].length<problemCount+1){
					throw new ReplayDataInvalidException("Replay data file do not cantain enough information.");
				}
				for (int j = 1; j < cells[i].length; j++) {
					cellParser.recognize(cells[i][j]);
				}
			}
			indexedExpression = new HashMap<Integer, RankListCellExpression>();
			int idx = 0;
			selections = new HashMap<String, Map<String, Integer>>();
			for (Entry<String, Pair<String, List<RankListCellExpression>>> e : cellParser.getPatterns().entrySet()) {
				Pattern pattern = Pattern.compile(e.getKey());
				String example = e.getValue().first;
				Matcher matcher = pattern.matcher(example);
				List<RankListCellExpression> lstExpression = e.getValue().second;
				Map<String, Integer> options = new HashMap<String, Integer>();
				for (RankListCellExpression exp : lstExpression) {
					String display = matcher.replaceAll(exp.getDescription());
					indexedExpression.put(idx, exp);
					options.put(display, idx);
					idx++;
				}
				selections.put("<"+example+"><"+e.getKey()+">", options);
			}
		}else{
			throw new ReplayDataInvalidException("Could not read submitted replay data file.");
		}
		return new Pair<Map<String, Map<String, Integer>>, Map<Integer, RankListCellExpression>>(selections,
				indexedExpression);
	}

}
