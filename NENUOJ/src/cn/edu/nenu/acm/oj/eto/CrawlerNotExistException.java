package cn.edu.nenu.acm.oj.eto;

public class CrawlerNotExistException extends Exception {
	private static final long serialVersionUID = -5055994846582356882L;
	public CrawlerNotExistException(){
	}
	public CrawlerNotExistException(String message){
		super(message);
	}
}
