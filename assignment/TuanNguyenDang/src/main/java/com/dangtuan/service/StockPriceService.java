package com.dangtuan.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import com.dangtuan.model.DayMovingAverageModel;
import com.dangtuan.model.PriceCloseModel;
import com.dangtuan.model.StockPriceResponseMultiTickerModel;

/**
 * The Interface StockPriceService.
 * 
 * @author tuannguyen
 */
public interface StockPriceService {
	
	/**
	 * Gets the close price.
	 *
	 * @param ticker the ticker
	 * @param startDate the start date
	 * @param endDate the end date
	 * @return the close price
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public PriceCloseModel getClosePrice(String ticker, LocalDate startDate, LocalDate endDate) throws IOException;

	/**
	 * Gets the 200 day moving average.
	 *
	 * @param ticker the ticker
	 * @param startDate the start date
	 * @param endDate the end date
	 * @return the 200 day moving average
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public DayMovingAverageModel get200DayMovingAverage(String ticker, LocalDate startDate, LocalDate endDate)
			throws IOException;

	/**
	 * Find first 200 day moving average.
	 *
	 * @param ticker the ticker
	 * @param startDate the start date
	 * @param endDate the end date
	 * @return the day moving average model
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public DayMovingAverageModel findFirst200DayMovingAverage(String ticker, LocalDate startDate, LocalDate endDate)
			throws IOException;

	/**
	 * Gets the 200 day moving average 1000 tickers.
	 *
	 * @param endDate the end date
	 * @param startDate the start date
	 * @param tickers the tickers
	 * @return the 200 day moving average 1000 tickers
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public StockPriceResponseMultiTickerModel get200DayMovingAverage1000Tickers(LocalDate endDate, LocalDate startDate,
			List<String> tickers) throws IOException;
}
