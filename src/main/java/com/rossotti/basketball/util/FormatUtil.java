package com.rossotti.basketball.util;

public class FormatUtil {
	static public String padString(String text, int length) {
		return String.format("%1$-" + length + "s", text);
	}
}
