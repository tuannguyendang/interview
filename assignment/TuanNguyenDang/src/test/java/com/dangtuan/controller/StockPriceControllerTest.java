package com.dangtuan.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.dangtuan.model.DayMovingAverageModel;
import com.dangtuan.model.MessageModel;
import com.dangtuan.model.PriceCloseModel;
import com.dangtuan.model.StockPriceResponseMultiTickerModel;
import com.dangtuan.service.StockPriceService;
import com.dangtuan.utils.ApplicationConstant;
import com.dangtuan.utils.LocalDateConverter;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class StockPriceControllerTest {
	@Autowired
	private MockMvc mockmvc;

	@MockBean
	private StockPriceService stockPriceService;

	LocalDateConverter localDateConverter = new LocalDateConverter("yyyy-MM-dd");

	@Test
	public void getClosePriceWhenInputValidWillReturnData() throws Exception {
		String ticker = "GE";
		List<String[]> dateCloses = new ArrayList<>();
		String[] dateClose = new String[1];
		dateClose[0] = "[\"2017-04-01\", \"30.02\"]";
		dateCloses.add(dateClose);
		PriceCloseModel priceCloseModel = new PriceCloseModel();
		priceCloseModel.setTicker(ticker);
		priceCloseModel.setDateClose(dateCloses);
		String source = "2017-04-01";
		LocalDate startDate = localDateConverter.convert(source);
		given(this.stockPriceService.getClosePrice(ticker, startDate, startDate)).willReturn(priceCloseModel);
		mockmvc.perform(get("/api/v2/{ticker}/closePrice?startDate={startDate}&endDate={endDate}", "GE", "2017-04-01",
				"2017-04-01")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.Prices.Ticker").value("GE")).andExpect(jsonPath("$.Prices.DateClose").isArray());
	}

	@Test
	public void getClosePriceWhenInputEmptyWillReturnMessage() throws Exception {
		String ticker = " ";
		mockmvc.perform(get("/api/v2/{ticker}/closePrice?startDate={startDate}&endDate={endDate}", ticker, "2017-04-01",
				"2017-04-01")).andExpect(status().isNotFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.message").value(String.format(ApplicationConstant.INVALID_TICKER, ticker)));
	}

	@Test
	public void getClosePriceeWhenInputEndDateAfterStartDateWillReturnMessage() throws Exception {
		String ticker = "GE";
		mockmvc.perform(get("/api/v2/{ticker}/closePrice?startDate={startDate}&endDate={endDate}", ticker, "2017-04-05",
				"2017-04-01")).andExpect(status().isNotFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.message").value(ApplicationConstant.INVALID_RANGE_DATE));
	}

	@Test
	public void get200DayMovingAveragePriceWhenInputInValidDayWillReturnMessageSuggest() throws Exception {
		String ticker = "GE";
		String source = "2017-04-01";
		LocalDate startDate = localDateConverter.convert(source);
		DayMovingAverageModel dayMovingAverageModel = new DayMovingAverageModel();
		dayMovingAverageModel.setTicker(ticker);
		dayMovingAverageModel.setAvg(0.0);
		given(this.stockPriceService.findFirst200DayMovingAverage(ticker, startDate, startDate))
				.willReturn(dayMovingAverageModel);
		mockmvc.perform(get("/api/v2/{ticker}/200dma?startDate={startDate}", "GE", "2017-04-01"))
				.andExpect(status().isNotFound()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.message").isNotEmpty());
	}

	@Test
	public void get200DayMovingAveragePriceWhenInputValidWillReturnValidValue() throws Exception {
		String ticker = "GE";
		LocalDate startDate = localDateConverter.convert("2016-10-14");
		LocalDate endDate = localDateConverter.convert("2017-05-02");
		DayMovingAverageModel dayMovingAverageModel = new DayMovingAverageModel();
		dayMovingAverageModel.setTicker(ticker);
		dayMovingAverageModel.setAvg(0.0);
		given(this.stockPriceService.get200DayMovingAverage(ticker, startDate, endDate))
				.willReturn(dayMovingAverageModel);
		mockmvc.perform(get("/api/v2/{ticker}/200dma?startDate={startDate}", "GE", "2016-10-14"))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.200dma.Ticker").value(ticker)).andExpect(jsonPath("$.200dma.Avg").value(0.0));
	}

	@Test
	public void get200DayMovingAveragePricesWhenInputEmptyTickerWillReturnMessage() throws Exception {
		String ticker = "  ";
		LocalDate startDate = localDateConverter.convert("2016-10-14");
		LocalDate endDate = localDateConverter.convert("2017-05-02");
		DayMovingAverageModel dayMovingAverageModel = new DayMovingAverageModel();
		dayMovingAverageModel.setTicker(ticker);
		dayMovingAverageModel.setAvg(0.0);
		given(this.stockPriceService.get200DayMovingAverage(ticker, startDate, endDate))
				.willReturn(dayMovingAverageModel);
		mockmvc.perform(get("/api/v2/{ticker}/200dma?startDate={startDate}", ticker, "2016-10-14"))
				.andExpect(status().isNotFound()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.message").value(String.format(ApplicationConstant.INVALID_TICKER, ticker)));
	}

	@Test
	public void get200DayMovingAverage1000TickersWhenInputEmptyTickersWillReturnMessageSuggest() throws Exception {
		mockmvc.perform(get("/api/v2/200dma1000t?startDate={startDate}&tickers={tickers}", "2017-04-01", ""))
				.andExpect(status().isNotFound()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.message").value(ApplicationConstant.TICKERS_EMPTY));
	}

	@Test
	public void get200DayMovingAverage1000TickersWhenInputInValidDayWillReturnMessageSuggest() throws Exception {
		List<String> tickers = new ArrayList<>();
		tickers.add("FE");
		tickers.add("GE");
		tickers.add(null);
		tickers.add("DK");
		LocalDate startDate = localDateConverter.convert("2017-04-01");
		LocalDate endDate = localDateConverter.convert("2017-04-01");
		given(this.stockPriceService.get200DayMovingAverage1000Tickers(endDate, startDate, tickers))
				.willReturn(new StockPriceResponseMultiTickerModel());
		mockmvc.perform(
				get("/api/v2/200dma1000t?startDate={startDate}&tickers={tickers}", "2017-04-01", "FE, GE, null, DK"))
				.andExpect(status().isNotFound()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.message").isNotEmpty());
	}

	@Test
	public void get200DayMovingAverage1000TickersWhenInputValidAndInValidDayWillReturnDataAndMessage()
			throws Exception {
		List<String> tickers = new ArrayList<>();
		tickers.add("FE");
		tickers.add("GE");
		tickers.add("null");
		tickers.add("DK");
		List<MessageModel> messageModels = new ArrayList<>();
		messageModels.add(new MessageModel(String.format(ApplicationConstant.INVALID_TICKER, tickers.get(2))));
		LocalDate startDate = localDateConverter.convert("2016-04-01");
		LocalDate endDate = localDateConverter.convert("2016-10-18");
		StockPriceResponseMultiTickerModel stockPriceResponseMultiTickerModel = new StockPriceResponseMultiTickerModel();
		stockPriceResponseMultiTickerModel.setDayMovingAverageModels(new ArrayList<>());
		stockPriceResponseMultiTickerModel.setMessageModels(messageModels);
		given(this.stockPriceService.get200DayMovingAverage1000Tickers(endDate, startDate, tickers))
				.willReturn(stockPriceResponseMultiTickerModel);
		mockmvc.perform(
				get("/api/v2/200dma1000t?startDate={startDate}&tickers={tickers}", "2016-04-01", "FE, GE, null, DK"))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.200dma1000t").isEmpty()).andExpect(jsonPath("$.messages").isNotEmpty());
	}
}
