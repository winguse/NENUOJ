package cn.edu.nenu.acm.oj.test;

public class Normal {

	public static void main(String[] args) {
		System.out.println("[]1231.".replaceAll("([\\$\\^\\|\\[\\]\\{\\}\\(\\)\\.\\*\\?\\+\\\\])", "\\\\$1")
				.replaceAll("\\d+", "(\\\\d+)"));
	}

}
