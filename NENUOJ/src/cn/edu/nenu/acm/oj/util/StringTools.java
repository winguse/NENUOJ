package cn.edu.nenu.acm.oj.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringTools {

	public StringTools() {
	}

	public static String regexFind(String string, String regex, int index, boolean isCaseSensitive) {
		Matcher matcher = null;
		if (isCaseSensitive) {
			matcher = Pattern.compile(regex, Pattern.CASE_INSENSITIVE).matcher(string);
		} else {
			matcher = Pattern.compile(regex).matcher(string);
		}
		return matcher.find() ? matcher.group(index) : "";
	}

	public static String regexFind(String string, String regex, int index) {
		return regexFind(string, regex, index, true);
	}

	public static String regexFind(String string, String regex) {
		return regexFind(string, regex, 1, true);
	}
}
