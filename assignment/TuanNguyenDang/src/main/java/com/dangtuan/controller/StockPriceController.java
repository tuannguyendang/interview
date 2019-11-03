package com.dangtuan.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.dangtuan.model.DayMovingAverageModel;
import com.dangtuan.model.MessageModel;
import com.dangtuan.model.PriceCloseModel;
import com.dangtuan.model.StockPriceResponseMultiTickerModel;
import com.dangtuan.service.StockPriceService;
import com.dangtuan.utils.ApplicationConstant;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * The Class StockPriceController.
 * 
 * @author tuannguyen
 *
 */
@RestController
@RequestMapping("/api/v2")
public class StockPriceController {

	/** The stock price service. */
	@Autowired
	private StockPriceService stockPriceService;

	/**
	 * Gets the close price.
	 *
	 * @param tickersymbol
	 *            the ticker symbol
	 * @param startDate
	 *            the start date
	 * @param endDate
	 *            the end date
	 * @return the close price
	 */
	@RequestMapping(value = "/{tickersymbol}/closePrice", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getClosePrice(@PathVariable("tickersymbol") String tickersymbol,
			@RequestParam("startDate") LocalDate startDate, @RequestParam("endDate") LocalDate endDate) {
		if (tickersymbol == null || tickersymbol.trim().isEmpty()) {
			return new ResponseEntity<>(
					new MessageModel(String.format(ApplicationConstant.INVALID_TICKER, tickersymbol)),
					HttpStatus.NOT_FOUND);
		}
		if (startDate.isAfter(endDate) || endDate.isAfter(LocalDate.now())) {
			return new ResponseEntity<>(new MessageModel(ApplicationConstant.INVALID_RANGE_DATE), HttpStatus.NOT_FOUND);
		}
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, true);
		try {
			PriceCloseModel priceCloseModel = stockPriceService.getClosePrice(tickersymbol.trim(), startDate, endDate);
			return new ResponseEntity<>(mapper.writeValueAsString(priceCloseModel), HttpStatus.OK);
		} catch (HttpClientErrorException e) {
			return new ResponseEntity<>(new MessageModel(ApplicationConstant.TICKER_NOT_FOUND), e.getStatusCode());
		} catch (IOException e) {
			return new ResponseEntity<>(new MessageModel(e.getMessage()), HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Gets the 200 day moving average price.
	 *
	 * @param tickersymbol
	 *            the ticker symbol
	 * @param startDate
	 *            the start date
	 * @return the 200 day moving average price
	 */
	@RequestMapping(value = "/{tickersymbol}/200dma", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> get200DayMovingAveragePrice(@PathVariable("tickersymbol") String tickersymbol,
			@RequestParam("startDate") LocalDate startDate) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, true);
		LocalDate toDate = LocalDate.now();
		LocalDate endDate = startDate.plusDays(ApplicationConstant.AMOUNT_MOVING_DAY);
		if (tickersymbol == null || tickersymbol.trim().isEmpty()) {
			return new ResponseEntity<>(
					new MessageModel(String.format(ApplicationConstant.INVALID_TICKER, tickersymbol)),
					HttpStatus.NOT_FOUND);
		}
		try {
			// find first day possible
			if (endDate.isAfter(toDate)) {
				startDate = toDate.minusDays(ApplicationConstant.AMOUNT_MOVING_DAY);
				stockPriceService.findFirst200DayMovingAverage(tickersymbol.trim(), startDate, toDate);
				return new ResponseEntity<>(
						new MessageModel(String.format(ApplicationConstant.MDV200_SUGGEST, startDate.toString())),
						HttpStatus.NOT_FOUND);
			} else {
				DayMovingAverageModel dayMovingAverageModel = stockPriceService.get200DayMovingAverage(tickersymbol.trim(),
						startDate, endDate);
				return new ResponseEntity<>(mapper.writeValueAsString(dayMovingAverageModel), HttpStatus.OK);
			}
		} catch (HttpClientErrorException e) {
			return new ResponseEntity<>(new MessageModel(ApplicationConstant.TICKER_NOT_FOUND), e.getStatusCode());
		} catch (IOException e) {
			return new ResponseEntity<>(new MessageModel(e.getMessage()), HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Gets the 200 day moving average 1000 tickers.
	 *
	 * @param startDate
	 *            the start date
	 * @param tickers
	 *            the tickers
	 * @return the 200 day moving average 1000 tickers
	 */
	@RequestMapping(value = "/200dma1000t", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> get200DayMovingAverage1000Tickers(@RequestParam("startDate") LocalDate startDate,
			@RequestParam("tickers") List<String> tickers) {
		ObjectMapper mapper = new ObjectMapper();
		StockPriceResponseMultiTickerModel stockPriceResponseMultiTickerModel = new StockPriceResponseMultiTickerModel();
		LocalDate toDate = LocalDate.now();
		LocalDate endDate = startDate.plusDays(ApplicationConstant.AMOUNT_MOVING_DAY);
		if (tickers == null || tickers.isEmpty()) {
			return new ResponseEntity<>(new MessageModel(ApplicationConstant.TICKERS_EMPTY), HttpStatus.NOT_FOUND);
		}
		try {
			if (endDate.isAfter(toDate)) {
				startDate = toDate.minusDays(ApplicationConstant.AMOUNT_MOVING_DAY);
				stockPriceService.get200DayMovingAverage1000Tickers(toDate, startDate, tickers);
				return new ResponseEntity<>(
						new MessageModel(String.format(ApplicationConstant.MDV200_SUGGEST, startDate.toString())),
						HttpStatus.NOT_FOUND);
			} else {
				stockPriceResponseMultiTickerModel = stockPriceService.get200DayMovingAverage1000Tickers(endDate,
						startDate, tickers);
				return new ResponseEntity<>(mapper.writeValueAsString(stockPriceResponseMultiTickerModel),
						HttpStatus.OK);
			}
		} catch (HttpClientErrorException e) {
			return new ResponseEntity<>(new MessageModel(ApplicationConstant.TICKER_NOT_FOUND), e.getStatusCode());
		} catch (IOException e) {
			return new ResponseEntity<>(new MessageModel(e.getMessage()), HttpStatus.NOT_FOUND);
		}
	}
}
