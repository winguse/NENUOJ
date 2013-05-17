package cn.edu.nenu.acm.oj.service;

import cn.edu.nenu.acm.oj.eto.CrawlingException;
import cn.edu.nenu.acm.oj.eto.NetworkException;
import cn.edu.nenu.acm.oj.eto.RemoteProblemNotFoundException;

/**
 * It's designed to use many times. <br>
 * 1. crawl<br>
 * 2. get **<br>
 * 3. go back to 1 if needed.<br>
 * 
 * @author Winguse
 * 
 */
public interface IProblemCrawler {
	
	/**
	 * Set the attachment storage directory.
	 * @param attachmentDirectory
	 */
	public void setAttachmentDirectory(String attachmentDirectory);
	
	/**
	 * Set the attachment local base url 
	 * @param attachmentDownloadBaseUrl
	 */
	public void setAttachmentDownloadBaseUrl(String attachmentDownloadBaseUrl);
	
	/**
	 * The judger source name, like HDU,POJ,etc.
	 * 
	 * @return source name
	 */
	public String getJudgerSource();

	/**
	 * crawl the problem,
	 * 
	 * @param number
	 * @throws NetworkException
	 * @throws CrawlingException
	 * @throws RemoteProblemNotFoundException
	 */
	public void crawl(String number) throws NetworkException, CrawlingException, RemoteProblemNotFoundException;

	/**
	 * @return problem's title
	 * @throws CrawlingException
	 */
	public String getTitle() throws CrawlingException;

	/**
	 * @return prblem's memory limitation
	 * @throws CrawlingException
	 */
	public Integer getMemoryLimit() throws CrawlingException;

	/**
	 * @return prblem's time limitation
	 * @throws CrawlingException
	 */
	public Integer getTimeLimit() throws CrawlingException;

	/**
	 * @return problem's description
	 * @throws CrawlingException
	 */
	public String getDescription() throws CrawlingException;

	/**
	 * @return problem's input description
	 * @throws CrawlingException
	 */
	public String getInput() throws CrawlingException;

	/**
	 * @return problem's output description
	 * @throws CrawlingException
	 */
	public String getOutput() throws CrawlingException;

	/**
	 * @return problem's sample input, in raw text
	 * @throws CrawlingException
	 */
	public String getSampleIn() throws CrawlingException;

	/**
	 * @return problem's sample output, in raw text
	 * @throws CrawlingException
	 */
	public String getSampleOut() throws CrawlingException;

	/**
	 * @return problem's hints
	 * @throws CrawlingException
	 */
	public String getHint() throws CrawlingException;

	/**
	 * @return problem's source
	 * @throws CrawlingException
	 */
	public String getSource() throws CrawlingException;

	/**
	 * @return problem's original url
	 * @throws CrawlingException
	 */
	public String getOriginalUrl() throws CrawlingException;

	// public static

}
