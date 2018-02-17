package com.productshop.helpers;

public class RouterHelper {

	public static String getRoutePartRegExp() {
		String firstSymbol = "A-Za-z";
		String lastSymbol = firstSymbol + "0-9";
		String internalSymbol = lastSymbol + "\\-\\_";
		return "[" + firstSymbol + "][" + internalSymbol + "]*[" + lastSymbol + "]/?";
	}
	
	public static String getRoutePartRegExpAsParam() {
		String paramFirstSymbol = "A-Za-zА-Яа-я0-9";
		String paramLastSymbol = paramFirstSymbol;
		String paramInternalSymbol = paramLastSymbol + "\\-\\_";
		String param = "[" + paramFirstSymbol + "][" + paramInternalSymbol + "]*[" + paramLastSymbol + "]";
		String alternateParam = "[A-Za-zА-Яа-я0-9]+";
		return "(" + param + ")|(" + alternateParam  + ")/?";
	}

}