package cn.edu.nenu.acm.oj.service.impl.remote;

import static cn.edu.nenu.acm.oj.util.StringTools.regexFind;

import java.io.IOException;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;

import cn.edu.nenu.acm.oj.eto.CrawlingException;
import cn.edu.nenu.acm.oj.eto.NetworkException;
import cn.edu.nenu.acm.oj.eto.RemoteProblemNotFoundException;
import cn.edu.nenu.acm.oj.service.IProblemCrawler;

public class POJProblemCrawler implements IProblemCrawler {

	public static final String judgerSource = "POJ";
	public static final String homePage = "http://poj.org";
	public static final String problemBaseUrl = homePage + "/problem?id=";

	private String number;
	private Document document;	
	
	@Override
	public String getJudgerSource() {
		return judgerSource;
	}

	@Override
	public void crawl(String number) throws NetworkException,
			CrawlingException, RemoteProblemNotFoundException {
		this.number = number;
		try {
			document = Jsoup.connect(problemBaseUrl + number).get();
		} catch (IOException e) {
			e.printStackTrace();
			throw new NetworkException("IOException, Maybe network error: " + e.getMessage());
		}
		if (!document.title().equals(number + " -- A+B Problem")) {
			throw new RemoteProblemNotFoundException();
		}
	}

	@Override
	public String getTitle() throws CrawlingException {
		Element title = document.select(".ptt").first();
		if (title == null) {
			throw new CrawlingException("Problem Title Not Found.");
		}
		return title.text();
	}

	@Override
	public Integer getMemoryLimit() throws CrawlingException {
		Element limit = document.select(".plm tr").get(0).select("td").get(2);
		if (limit == null)
			throw new CrawlingException("Problem Memory Limit Not Found.");
		String limitText = limit.text(), memory = null;

		memory = regexFind(limitText, "Memory Limit: (\\d+)K");
		if (memory.equals(""))
			throw new CrawlingException("Cannot Locate Problem Memory Limit Text.");
		return new Integer(memory);
	}

	@Override
	public Integer getTimeLimit() throws CrawlingException {
		Element limit = document.select(".plm tr").get(0).select("td").get(0);
		if (limit == null)
			throw new CrawlingException("Problem Time Limit Not Found.");
		String limitText = limit.text(), time = null;
		time = regexFind(limitText, "Time Limit: (\\d+)MS");
		if (time.equals(""))
			throw new CrawlingException("Cannot Locate Problem Time Limit Text.");
		return new Integer(time);
	}

	@Override
	public String getDescription() throws CrawlingException {
		return problemSection(".pst", "Description", ".ptx");
	}

	@Override
	public String getInput() throws CrawlingException {
		return problemSection(".pst", "Input", ".ptx");
	}

	@Override
	public String getOutput() throws CrawlingException {
		return problemSection(".pst", "Output", ".ptx");
	}

	@Override
	public String getSampleIn() throws CrawlingException {
		return problemSection(".pst", "Sample Input", ".sio");
	}

	@Override
	public String getSampleOut() throws CrawlingException {
		return problemSection(".pst", "Sample Output", ".sio");
	}

	@Override
	public String getHint() throws CrawlingException {
		return problemSection(".pst", "Hint", ".ptx");
	}

	@Override
	public String getSource() throws CrawlingException {
		return problemSection(".pst", "Source", ".ptx");
	}

	@Override
	public String getOriginalUrl() throws CrawlingException {
		return problemBaseUrl + number;
	}
	
	private void changeUrl(Element element) {
		for (Element src : element.select("[src]")) {
			src.attr("src", src.absUrl("src"));
		}
//		for(Element img : element.select("img")){
//			//download img to server,
//			String stc = img.attr("src");
//			String serverSrc = null;
//
//			Pattern.compile("");
//			
//			img.attr("src", serverSrc);
//		}
	}
	

	
	private String problemSection(String firstCss, String firstSection, String secondCss, boolean textOnly) throws CrawlingException {
		Element e = document.select(firstCss + ":contains(" + firstSection + ")+" + secondCss + "").first();
		if (e == null)
			throw new CrawlingException("Session [" + firstSection + "] Not Found.");
		if (textOnly)
			return e.text();
		changeUrl(e);
		return Jsoup.clean(e.html(), Whitelist.basicWithImages());
	}		
	
	private String problemSection(String firstCss, String firstSection, String secondCss) throws CrawlingException {
		return problemSection(firstCss, firstSection, secondCss, false);
	}
}
