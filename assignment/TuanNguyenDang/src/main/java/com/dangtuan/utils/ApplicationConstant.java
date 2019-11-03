package com.dangtuan.utils;

/**
 * The Class ApplicationConstant.
 * 
 * @author tuannguyen
 */
public class ApplicationConstant {
	
	/** The invalid ticker. */
	public static String INVALID_TICKER = "Ticker %s Invalid!";
	
	/** The invalid range date. */
	public static String INVALID_RANGE_DATE = "Range Date Invalid! Start Date CanNot After End Date & End Date CanNot After Today";
	
	/** The ticker not found. */
	public static String TICKER_NOT_FOUND = "Ticker Not Found!";
	
	/** The amount moving day. */
	public static int AMOUNT_MOVING_DAY = 200;
	
	/** The mdv200 suggest. */
	public static String MDV200_SUGGEST = "First Possible 200 Moving Day Average %s!";
	
	/** The no data return. */
	public static String NO_DATA_RETURN = "No Valid Data Found For Ticker %s!";
	
	/** The tickers empty. */
	public static String TICKERS_EMPTY = "Ticker List Cannot Empty!";
}
