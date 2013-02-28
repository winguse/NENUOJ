package cn.edu.nenu.acm.oj.service.remote;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;

import cn.edu.nenu.acm.oj.eto.CrawlingException;
import cn.edu.nenu.acm.oj.eto.NetworkException;
import cn.edu.nenu.acm.oj.eto.RemoteProblemNotFoundException;

import static cn.edu.nenu.acm.oj.util.StringTools.*;

public class HDUProblemCrawler implements IProblemCrawler {

	public static final String judgerSource = "HDU";
	public static final String homePage = "http://acm.hdu.edu.cn";
	public static final String problemBaseUrl = homePage + "/showproblem.php?pid=";

	private String number;
	private Document document;

	@Override
	public final String getJudgerSource() {
		return judgerSource;
	}

	@Override
	public void crawl(String number) throws NetworkException,CrawlingException, RemoteProblemNotFoundException {
		this.number = number;
		try {
			document = Jsoup.connect(problemBaseUrl + number).get();
		} catch (IOException e) {
			e.printStackTrace();
			throw new NetworkException("IOException, Maybe network error: " + e.getMessage());
		}
		if (!document.title().equals("Problem - " + number)) {
			throw new RemoteProblemNotFoundException();
		}
	}

	@Override
	public String getTitle() throws CrawlingException {
		Element title = document.select("h1").first();
		if (title == null) {
			throw new CrawlingException("Problem Title Not Found.");
		}
		return title.text();
	}

	@Override
	public Integer getMemoryLimit() throws CrawlingException {
		Element limit = document.select("font>b>span").first();
		if (limit == null)
			throw new CrawlingException("Problem Memory Limit Not Found.");
		String limitText = limit.text(), memory = null;
		memory = regexFind(limitText, "Memory Limit: \\d+?/(\\d+?) K \\(Java/Others\\)");
		if (memory.equals(""))
			throw new CrawlingException("Cannot Locate Problem Memory Limit Text.");
		return new Integer(memory);
	}

	@Override
	public Integer getTimeLimit() throws CrawlingException {
		Element limit = document.select("font>b>span").first();
		if (limit == null)
			throw new CrawlingException("Problem Time Limit Not Found.");
		String limitText = limit.text(), time = null;
		time = regexFind(limitText, "Time Limit: \\d+?/(\\d+?) MS \\(Java/Others\\)");
		if (time.equals(""))
			throw new CrawlingException("Cannot Locate Problem Time Limit Text.");
		return new Integer(time);
	}

	@Override
	public String getDescription() throws CrawlingException {
		return problemSection("Problem Description");
	}

	@Override
	public String getInput() throws CrawlingException {
		return problemSection("Input");
	}

	@Override
	public String getOutput() throws CrawlingException {
		return problemSection("Output");
	}

	@Override
	public String getSampleIn() throws CrawlingException {
		return problemSection("Sample Input",true);
	}

	@Override
	public String getSampleOut() throws CrawlingException {
		return problemSection("Sample Output",true);
	}

	@Override
	public String getHint() {
		return "";
	}

	@Override
	public String getSource() {
		try {
			return problemSection("Source", true);
		} catch (CrawlingException e) {
			e.printStackTrace();
			return "";
		}
	}

	@Override
	public String getOriginalUrl() {
		return problemBaseUrl + number;
	}

	private void changeUrl(Element element) {
		for (Element src : element.select("[src]")) {
			src.attr("src", src.absUrl("src"));
		}
	}

	private String problemSection(String section, boolean textOnly) throws CrawlingException {
		Element e = document.select(".panel_title:contains(" + section + ")+.panel_content").first();
		if (e == null)
			throw new CrawlingException("Session [" + section + "] Not Found.");
		if (textOnly)
			return e.text();
		changeUrl(e);
		return Jsoup.clean(e.html(), Whitelist.basicWithImages());
	}

	private String problemSection(String section) throws CrawlingException {
		return problemSection(section, false);
	}

}
