package com.dangtuan.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.dangtuan.model.DayMovingAverageModel;
import com.dangtuan.model.PriceCloseModel;
import com.dangtuan.model.StockPriceResponseMultiTickerModel;
import com.dangtuan.service.impl.StockPriceServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:application.yml")
public class StockPriceServiceImplTest {

	@InjectMocks
	private StockPriceServiceImpl stockPriceServiceImpl;

	@Spy
	private ObjectMapper mapper;

	@Mock
	private RestTemplate restTemplate;

	@Before
	public void setupTarget() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void get200DayMovingAverageWhenServiceReturnValidWillReturnValidValue() throws Exception {
		String ticker = "GE";
		LocalDate startDate = LocalDate.now();
		LocalDate endDate = LocalDate.now();
		DayMovingAverageModel dayMovingAverageModel = new DayMovingAverageModel();
		dayMovingAverageModel.setTicker(ticker);
		dayMovingAverageModel.setAvg(0.0);
		String result = "{\"dataset\":{\"id\":9775709,\"dataset_code\":\"GE\",\"database_code\":\"WIKI\",\"name\":\"General Electric Co\",\"description\":\"GE\",\"refreshed_at\":\"2017-04-28T21:47:39.087Z\",\"newest_available_date\":\"2017-04-28\",\"oldest_available_date\":\"1962-01-02\",\"column_names\":[\"Date\",\"Open\",\"High\",\"Low\",\"Close\",\"Volume\",\"Ex-Dividend\",\"Split Ratio\",\"Adj. Open\",\"Adj. High\",\"Adj. Low\",\"Adj. Close\",\"Adj. Volume\"],\"frequency\":\"daily\",\"type\":\"Time Series\",\"premium\":false,\"limit\":null,\"transform\":null,\"column_index\":null,\"start_date\":\"2017-04-04\",\"end_date\":\"2017-04-04\",\"data\":[[\"2017-04-04\",29.9,30.09,29.7,30.02,25056918.0,0.0,1.0,29.9,30.09,29.7,30.02,25056918.0]],\"collapse\":null,\"order\":null,\"database_id\":4922}}";
		Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.anyObject())).thenReturn(result);
		DayMovingAverageModel dayMovingAverageModelResponse = stockPriceServiceImpl.get200DayMovingAverage(ticker,
				startDate, endDate);
		Assert.assertEquals(ticker, dayMovingAverageModelResponse.getTicker());
		Assert.assertEquals(30.02, dayMovingAverageModelResponse.getAvg(), 2);
	}

	@Test(expected = IOException.class)
	public void get200DayMovingAverageWhenServiceReturnErrorValueWillThrowIOException() throws Exception {
		String ticker = "GE";
		LocalDate startDate = LocalDate.now();
		LocalDate endDate = LocalDate.now();
		DayMovingAverageModel dayMovingAverageModel = new DayMovingAverageModel();
		dayMovingAverageModel.setTicker(ticker);
		dayMovingAverageModel.setAvg(0.0);
		String result = "{\"quandl_error\":{\"code\":\"QECx02\",\"message\":\"You have submitted an incorrect Quandl code. Please check your Quandl codes and try again.\"}}";
		Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.anyObject())).thenReturn(result);
		stockPriceServiceImpl.get200DayMovingAverage(ticker, startDate, endDate);
	}

	@SuppressWarnings("unchecked")
	@Test(expected = IOException.class)
	public void get200DayMovingAverageWhenServiceThrowExceptionWillThrowIOException() throws Exception {
		String ticker = "GE";
		LocalDate startDate = LocalDate.now();
		LocalDate endDate = LocalDate.now();
		DayMovingAverageModel dayMovingAverageModel = new DayMovingAverageModel();
		dayMovingAverageModel.setTicker(ticker);
		dayMovingAverageModel.setAvg(0.0);
		Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.anyObject())).thenThrow(IOException.class);
		DayMovingAverageModel dayMovingAverageModelResponse = stockPriceServiceImpl.get200DayMovingAverage(ticker,
				startDate, endDate);
		Assert.assertEquals(ticker, dayMovingAverageModelResponse.getTicker());
		Assert.assertEquals(30.02, dayMovingAverageModelResponse.getAvg(), 2);
	}

	@SuppressWarnings("unchecked")
	@Test(expected = HttpClientErrorException.class)
	public void get200DayMovingAverageWhenServiceThrowExceptionWillThrowHttpClientErrorException() throws Exception {
		String ticker = "GE";
		LocalDate startDate = LocalDate.now();
		LocalDate endDate = LocalDate.now();
		DayMovingAverageModel dayMovingAverageModel = new DayMovingAverageModel();
		dayMovingAverageModel.setTicker(ticker);
		dayMovingAverageModel.setAvg(0.0);
		Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.anyObject()))
				.thenThrow(HttpClientErrorException.class);
		DayMovingAverageModel dayMovingAverageModelResponse = stockPriceServiceImpl.get200DayMovingAverage(ticker,
				startDate, endDate);
		Assert.assertEquals(ticker, dayMovingAverageModelResponse.getTicker());
		Assert.assertEquals(30.02, dayMovingAverageModelResponse.getAvg(), 2);
	}

	@Test(expected = IOException.class)
	public void get200DayMovingAverageWhenServiceReturnInValidDateWillThrowException() throws Exception {
		String ticker = "GE";
		LocalDate startDate = LocalDate.now();
		LocalDate endDate = LocalDate.now();
		DayMovingAverageModel dayMovingAverageModel = new DayMovingAverageModel();
		dayMovingAverageModel.setTicker(ticker);
		dayMovingAverageModel.setAvg(0.0);
		String result = "{\"dataset\":{\"id\":9775709,\"dataset_code\":\"GE\",\"database_code\":\"WIKI\",\"name\":\"General Electric Co\",\"description\":\"GE\",\"refreshed_at\":\"2017-04-28T21:47:39.087Z\",\"newest_available_date\":\"2017-04-28\",\"oldest_available_date\":\"1962-01-02\",\"column_names\":[\"Date\",\"Open\",\"High\",\"Low\",\"Close\",\"Volume\",\"Ex-Dividend\",\"Split Ratio\",\"Adj. Open\",\"Adj. High\",\"Adj. Low\",\"Adj. Close\",\"Adj. Volume\"],\"frequency\":\"daily\",\"type\":\"Time Series\",\"premium\":false,\"limit\":null,\"transform\":null,\"column_index\":null,\"start_date\":\"2017-04-04\",\"end_date\":\"2017-04-04\",\"data\":[[\"2017-04-04\",29.9,30.09,29.7]],\"collapse\":null,\"order\":null,\"database_id\":4922}}";
		Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.anyObject())).thenReturn(result);
		stockPriceServiceImpl.get200DayMovingAverage(ticker, startDate, endDate);
	}

	@Test
	public void findFirst200DayMovingAverageWhenServiceReturnValidDataWillReturnValidValue() throws Exception {
		String ticker = "GE";
		LocalDate startDate = LocalDate.now();
		LocalDate endDate = LocalDate.now();
		DayMovingAverageModel dayMovingAverageModel = new DayMovingAverageModel();
		dayMovingAverageModel.setTicker(ticker);
		dayMovingAverageModel.setAvg(0.0);
		String result = "{\"dataset\":{\"id\":9775709,\"dataset_code\":\"GE\",\"database_code\":\"WIKI\",\"name\":\"General Electric Co\",\"description\":\"GE\",\"refreshed_at\":\"2017-04-28T21:47:39.087Z\",\"newest_available_date\":\"2017-04-28\",\"oldest_available_date\":\"1962-01-02\",\"column_names\":[\"Date\",\"Open\",\"High\",\"Low\",\"Close\",\"Volume\",\"Ex-Dividend\",\"Split Ratio\",\"Adj. Open\",\"Adj. High\",\"Adj. Low\",\"Adj. Close\",\"Adj. Volume\"],\"frequency\":\"daily\",\"type\":\"Time Series\",\"premium\":false,\"limit\":null,\"transform\":null,\"column_index\":null,\"start_date\":\"2017-04-04\",\"end_date\":\"2017-04-04\",\"data\":[[\"2017-04-04\",29.9,30.09,29.7,30.02,25056918.0,0.0,1.0,29.9,30.09,29.7,30.02,25056918.0]],\"collapse\":null,\"order\":null,\"database_id\":4922}}";
		Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.anyObject())).thenReturn(result);
		DayMovingAverageModel dayMovingAverageModelResponse = stockPriceServiceImpl.findFirst200DayMovingAverage(ticker,
				startDate, endDate);
		Assert.assertEquals(ticker, dayMovingAverageModelResponse.getTicker());
		Assert.assertEquals(30.02, dayMovingAverageModelResponse.getAvg(), 2);
	}

	@Test(expected = IOException.class)
	public void findFirst200DayMovingAverageWhenServiceReturnEmptyDataWillThrowIOException() throws Exception {
		String ticker = "GE";
		LocalDate startDate = LocalDate.now();
		LocalDate endDate = LocalDate.now();
		DayMovingAverageModel dayMovingAverageModel = new DayMovingAverageModel();
		dayMovingAverageModel.setTicker(ticker);
		dayMovingAverageModel.setAvg(0.0);
		String result = "{\"dataset\":{\"id\":9775709,\"dataset_code\":\"GE\",\"database_code\":\"WIKI\",\"name\":\"General Electric Co\",\"description\":\"GE\",\"refreshed_at\":\"2017-04-28T21:47:39.087Z\",\"newest_available_date\":\"2017-04-28\",\"oldest_available_date\":\"1962-01-02\",\"column_names\":[\"Date\",\"Open\",\"High\",\"Low\",\"Close\",\"Volume\",\"Ex-Dividend\",\"Split Ratio\",\"Adj. Open\",\"Adj. High\",\"Adj. Low\",\"Adj. Close\",\"Adj. Volume\"],\"frequency\":\"daily\",\"type\":\"Time Series\",\"premium\":false,\"limit\":null,\"transform\":null,\"column_index\":null,\"start_date\":\"2017-04-04\",\"end_date\":\"2017-04-04\",\"data\":[],\"collapse\":null,\"order\":null,\"database_id\":4922}}";
		Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.anyObject())).thenReturn(result);
		stockPriceServiceImpl.findFirst200DayMovingAverage(ticker, startDate, endDate);
	}

	@Test(expected = IOException.class)
	public void findFirst200DayMovingAverageWhenServiceReturnEmptyDataAndColumnsWillThrowIOException()
			throws Exception {
		String ticker = "GE";
		LocalDate startDate = LocalDate.now();
		LocalDate endDate = LocalDate.now();
		DayMovingAverageModel dayMovingAverageModel = new DayMovingAverageModel();
		dayMovingAverageModel.setTicker(ticker);
		dayMovingAverageModel.setAvg(0.0);
		String result = "{\"dataset\":{\"id\":9775709,\"dataset_code\":\"GE\",\"database_code\":\"WIKI\",\"name\":\"General Electric Co\",\"description\":\"GE\",\"refreshed_at\":\"2017-04-28T21:47:39.087Z\",\"newest_available_date\":\"2017-04-28\",\"oldest_available_date\":\"1962-01-02\",\"column_names\":[],\"frequency\":\"daily\",\"type\":\"Time Series\",\"premium\":false,\"limit\":null,\"transform\":null,\"column_index\":null,\"start_date\":\"2017-04-04\",\"end_date\":\"2017-04-04\",\"data\":[],\"collapse\":null,\"order\":null,\"database_id\":4922}}";
		Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.anyObject())).thenReturn(result);
		stockPriceServiceImpl.findFirst200DayMovingAverage(ticker, startDate, endDate);
	}

	@Test(expected = IOException.class)
	public void findFirst200DayMovingAverageWhenServiceReturnEmptyColumnsWillThrowIOException() throws Exception {
		String ticker = "GE";
		LocalDate startDate = LocalDate.now();
		LocalDate endDate = LocalDate.now();
		DayMovingAverageModel dayMovingAverageModel = new DayMovingAverageModel();
		dayMovingAverageModel.setTicker(ticker);
		dayMovingAverageModel.setAvg(0.0);
		String result = "{\"dataset\":{\"id\":9775709,\"dataset_code\":\"GE\",\"database_code\":\"WIKI\",\"name\":\"General Electric Co\",\"description\":\"GE\",\"refreshed_at\":\"2017-04-28T21:47:39.087Z\",\"newest_available_date\":\"2017-04-28\",\"oldest_available_date\":\"1962-01-02\",\"column_names\":[],\"frequency\":\"daily\",\"type\":\"Time Series\",\"premium\":false,\"limit\":null,\"transform\":null,\"column_index\":null,\"start_date\":\"2017-04-04\",\"end_date\":\"2017-04-04\",\"data\":[[\"2017-04-04\",29.9,30.09,29.7,30.02,25056918.0,0.0,1.0,29.9,30.09,29.7,30.02,25056918.0]],\"collapse\":null,\"order\":null,\"database_id\":4922}}";
		Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.anyObject())).thenReturn(result);
		stockPriceServiceImpl.findFirst200DayMovingAverage(ticker, startDate, endDate);
	}

	@Test(expected = IOException.class)
	public void findFirst200DayMovingAverageWhenServiceReturnErrorWillThrowIOException() throws Exception {
		String ticker = "GE";
		LocalDate startDate = LocalDate.now();
		LocalDate endDate = LocalDate.now();
		DayMovingAverageModel dayMovingAverageModel = new DayMovingAverageModel();
		dayMovingAverageModel.setTicker(ticker);
		dayMovingAverageModel.setAvg(0.0);
		String result = "{\"quandl_error\":{\"code\":\"QECx02\",\"message\":\"You have submitted an incorrect Quandl code. Please check your Quandl codes and try again.\"}}";
		Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.anyObject())).thenReturn(result);
		stockPriceServiceImpl.findFirst200DayMovingAverage(ticker, startDate, endDate);
	}

	@SuppressWarnings("unchecked")
	@Test(expected = IOException.class)
	public void findFirst200DayMovingAverageWhenServiceThrowExceptionWillThrowException() throws Exception {
		String ticker = "GE";
		LocalDate startDate = LocalDate.now();
		LocalDate endDate = LocalDate.now();
		DayMovingAverageModel dayMovingAverageModel = new DayMovingAverageModel();
		dayMovingAverageModel.setTicker(ticker);
		dayMovingAverageModel.setAvg(0.0);
		Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.anyObject())).thenThrow(IOException.class);
		stockPriceServiceImpl.findFirst200DayMovingAverage(ticker, startDate, endDate);
	}

	@SuppressWarnings("unchecked")
	@Test(expected = HttpClientErrorException.class)
	public void findFirst200DayMovingAverageWhenServiceThrowExceptionWillThrowHttpClientErrorException()
			throws Exception {
		String ticker = "GE";
		LocalDate startDate = LocalDate.now();
		LocalDate endDate = LocalDate.now();
		DayMovingAverageModel dayMovingAverageModel = new DayMovingAverageModel();
		dayMovingAverageModel.setTicker(ticker);
		dayMovingAverageModel.setAvg(0.0);
		Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.anyObject()))
				.thenThrow(HttpClientErrorException.class);
		stockPriceServiceImpl.findFirst200DayMovingAverage(ticker, startDate, endDate);
	}

	@Test(expected = IOException.class)
	public void findFirst200DayMovingAverageWhenServiceReturnInValidWillThrowException() throws Exception {
		String ticker = "GE";
		LocalDate startDate = LocalDate.now();
		LocalDate endDate = LocalDate.now();
		DayMovingAverageModel dayMovingAverageModel = new DayMovingAverageModel();
		dayMovingAverageModel.setTicker(ticker);
		dayMovingAverageModel.setAvg(0.0);
		String result = "{\"dataset\":{\"id\":9775709,\"dataset_code\":\"GE\",\"database_code\":\"WIKI\",\"name\":\"General Electric Co\",\"description\":\"GE\",\"refreshed_at\":\"2017-04-28T21:47:39.087Z\",\"newest_available_date\":\"2017-04-28\",\"oldest_available_date\":\"1962-01-02\",\"column_names\":[\"Date\",\"Open\",\"High\",\"Low\",\"Close\",\"Volume\",\"Ex-Dividend\",\"Split Ratio\",\"Adj. Open\",\"Adj. High\",\"Adj. Low\",\"Adj. Close\",\"Adj. Volume\"],\"frequency\":\"daily\",\"type\":\"Time Series\",\"premium\":false,\"limit\":null,\"transform\":null,\"column_index\":null,\"start_date\":\"2017-04-04\",\"end_date\":\"2017-04-04\",\"data\":[[\"2017-04-04\",29.9,30.09,29.7]],\"collapse\":null,\"order\":null,\"database_id\":4922}}";
		Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.anyObject())).thenReturn(result);
		stockPriceServiceImpl.findFirst200DayMovingAverage(ticker, startDate, endDate);
	}

	@Test
	public void getClosePriceWhenServiceReturnValidWillReturnValidValue() throws Exception {
		String ticker = "GE";
		LocalDate startDate = LocalDate.now();
		LocalDate endDate = LocalDate.now();
		String result = "{\"dataset\":{\"id\":9775709,\"dataset_code\":\"GE\",\"database_code\":\"WIKI\",\"name\":\"General Electric Co\",\"description\":\"GE\",\"refreshed_at\":\"2017-04-28T21:47:39.087Z\",\"newest_available_date\":\"2017-04-28\",\"oldest_available_date\":\"1962-01-02\",\"column_names\":[\"Date\",\"Open\",\"High\",\"Low\",\"Close\",\"Volume\",\"Ex-Dividend\",\"Split Ratio\",\"Adj. Open\",\"Adj. High\",\"Adj. Low\",\"Adj. Close\",\"Adj. Volume\"],\"frequency\":\"daily\",\"type\":\"Time Series\",\"premium\":false,\"limit\":null,\"transform\":null,\"column_index\":null,\"start_date\":\"2017-04-04\",\"end_date\":\"2017-04-04\",\"data\":[[\"2017-04-04\",29.9,30.09,29.7,30.02,25056918.0,0.0,1.0,29.9,30.09,29.7,30.02,25056918.0]],\"collapse\":null,\"order\":null,\"database_id\":4922}}";
		Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.anyObject())).thenReturn(result);
		PriceCloseModel priceCloseModel = stockPriceServiceImpl.getClosePrice(ticker, startDate, endDate);
		Assert.assertNotNull(priceCloseModel);
		Assert.assertEquals(ticker, priceCloseModel.getTicker());
		Assert.assertNotNull(priceCloseModel.getDateClose());
		Assert.assertEquals(2, priceCloseModel.getTicker().length());
	}

	@Test(expected = IOException.class)
	public void getClosePriceWhenServiceReturnErrorWillThrowIOException() throws Exception {
		String ticker = "GE";
		LocalDate startDate = LocalDate.now();
		LocalDate endDate = LocalDate.now();
		String result = "{\"quandl_error\":{\"code\":\"QECx02\",\"message\":\"You have submitted an incorrect Quandl code. Please check your Quandl codes and try again.\"}}";
		Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.anyObject())).thenReturn(result);
		stockPriceServiceImpl.getClosePrice(ticker, startDate, endDate);
	}

	@Test(expected = IOException.class)
	public void getClosePriceWhenServiceReturnEmptyDataWillThrowIOException() throws Exception {
		String ticker = "GE";
		LocalDate startDate = LocalDate.now();
		LocalDate endDate = LocalDate.now();
		String result = "{\"dataset\":{\"id\":9775709,\"dataset_code\":\"GE\",\"database_code\":\"WIKI\",\"name\":\"General Electric Co\",\"description\":\"GE\",\"refreshed_at\":\"2017-04-28T21:47:39.087Z\",\"newest_available_date\":\"2017-04-28\",\"oldest_available_date\":\"1962-01-02\",\"column_names\":[\"Date\",\"Open\",\"High\",\"Low\",\"Close\",\"Volume\",\"Ex-Dividend\",\"Split Ratio\",\"Adj. Open\",\"Adj. High\",\"Adj. Low\",\"Adj. Close\",\"Adj. Volume\"],\"frequency\":\"daily\",\"type\":\"Time Series\",\"premium\":false,\"limit\":null,\"transform\":null,\"column_index\":null,\"start_date\":\"2017-04-04\",\"end_date\":\"2017-04-04\",\"data\":[],\"collapse\":null,\"order\":null,\"database_id\":4922}}";
		Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.anyObject())).thenReturn(result);
		stockPriceServiceImpl.getClosePrice(ticker, startDate, endDate);
	}

	@Test(expected = IOException.class)
	public void getClosePriceWhenServiceReturnEmptyDataAndColumnsWillThrowIOException() throws Exception {
		String ticker = "GE";
		LocalDate startDate = LocalDate.now();
		LocalDate endDate = LocalDate.now();
		String result = "{\"dataset\":{\"id\":9775709,\"dataset_code\":\"GE\",\"database_code\":\"WIKI\",\"name\":\"General Electric Co\",\"description\":\"GE\",\"refreshed_at\":\"2017-04-28T21:47:39.087Z\",\"newest_available_date\":\"2017-04-28\",\"oldest_available_date\":\"1962-01-02\",\"column_names\":[],\"frequency\":\"daily\",\"type\":\"Time Series\",\"premium\":false,\"limit\":null,\"transform\":null,\"column_index\":null,\"start_date\":\"2017-04-04\",\"end_date\":\"2017-04-04\",\"data\":[],\"collapse\":null,\"order\":null,\"database_id\":4922}}";
		Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.anyObject())).thenReturn(result);
		stockPriceServiceImpl.getClosePrice(ticker, startDate, endDate);
	}

	@Test(expected = IOException.class)
	public void getClosePriceWhenServiceReturnEmptyColumnsWillThrowIOException() throws Exception {
		String ticker = "GE";
		LocalDate startDate = LocalDate.now();
		LocalDate endDate = LocalDate.now();
		String result = "{\"dataset\":{\"id\":9775709,\"dataset_code\":\"GE\",\"database_code\":\"WIKI\",\"name\":\"General Electric Co\",\"description\":\"GE\",\"refreshed_at\":\"2017-04-28T21:47:39.087Z\",\"newest_available_date\":\"2017-04-28\",\"oldest_available_date\":\"1962-01-02\",\"column_names\":[],\"frequency\":\"daily\",\"type\":\"Time Series\",\"premium\":false,\"limit\":null,\"transform\":null,\"column_index\":null,\"start_date\":\"2017-04-04\",\"end_date\":\"2017-04-04\",\"data\":[[\"2017-04-04\",29.9,30.09,29.7,30.02,25056918.0,0.0,1.0,29.9,30.09,29.7,30.02,25056918.0]],\"collapse\":null,\"order\":null,\"database_id\":4922}}";
		Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.anyObject())).thenReturn(result);
		stockPriceServiceImpl.getClosePrice(ticker, startDate, endDate);
	}

	@Test(expected = IOException.class)
	public void getClosePriceWhenServiceReturnInvalidWillThrowIOException() throws Exception {
		String ticker = "GE";
		LocalDate startDate = LocalDate.now();
		LocalDate endDate = LocalDate.now();
		String result = "{\"dataset\":{\"id\":9775709,\"dataset_code\":\"GE\",\"database_code\":\"WIKI\",\"name\":\"General Electric Co\",\"description\":\"GE\",\"refreshed_at\":\"2017-04-28T21:47:39.087Z\",\"newest_available_date\":\"2017-04-28\",\"oldest_available_date\":\"1962-01-02\",\"column_names\":[\"Date\",\"Open\",\"High\",\"Low\",\"Close\",\"Volume\",\"Ex-Dividend\",\"Split Ratio\",\"Adj. Open\",\"Adj. High\",\"Adj. Low\",\"Adj. Close\",\"Adj. Volume\"],\"frequency\":\"daily\",\"type\":\"Time Series\",\"premium\":false,\"limit\":null,\"transform\":null,\"column_index\":null,\"start_date\":\"2017-04-04\",\"end_date\":\"2017-04-04\",\"data\":[[\"2017-04-04\",29.9,30.09,29.7]],\"collapse\":null,\"order\":null,\"database_id\":4922}}";
		Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.anyObject())).thenReturn(result);
		stockPriceServiceImpl.getClosePrice(ticker, startDate, endDate);
	}

	@Test
	public void get200DayMovingAverage1000TickersWhenInputValidWillReturnValidValue() throws Exception {
		List<String> tickers = new ArrayList<>();
		tickers.add("GE");
		tickers.add("FB");
		LocalDate startDate = LocalDate.now();
		LocalDate endDate = LocalDate.now();
		String result = "{\"dataset\":{\"id\":9775709,\"dataset_code\":\"GE\",\"database_code\":\"WIKI\",\"name\":\"General Electric Co\",\"description\":\"GE\",\"refreshed_at\":\"2017-04-28T21:47:39.087Z\",\"newest_available_date\":\"2017-04-28\",\"oldest_available_date\":\"1962-01-02\",\"column_names\":[\"Date\",\"Open\",\"High\",\"Low\",\"Close\",\"Volume\",\"Ex-Dividend\",\"Split Ratio\",\"Adj. Open\",\"Adj. High\",\"Adj. Low\",\"Adj. Close\",\"Adj. Volume\"],\"frequency\":\"daily\",\"type\":\"Time Series\",\"premium\":false,\"limit\":null,\"transform\":null,\"column_index\":null,\"start_date\":\"2017-04-04\",\"end_date\":\"2017-04-04\",\"data\":[[\"2017-04-04\",29.9,30.09,29.7,30.02,25056918.0,0.0,1.0,29.9,30.09,29.7,30.02,25056918.0]],\"collapse\":null,\"order\":null,\"database_id\":4922}}";
		Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.anyObject())).thenReturn(result);
		StockPriceResponseMultiTickerModel stockPriceResponseMultiTickerModel = stockPriceServiceImpl
				.get200DayMovingAverage1000Tickers(startDate, endDate, tickers);
		Assert.assertNotNull(stockPriceResponseMultiTickerModel);
		Assert.assertNotNull(stockPriceResponseMultiTickerModel.getDayMovingAverageModels());
		Assert.assertEquals(2, stockPriceResponseMultiTickerModel.getDayMovingAverageModels().size());
		Assert.assertEquals("GE", stockPriceResponseMultiTickerModel.getDayMovingAverageModels().get(0).getTicker());
		Assert.assertEquals(30.02, stockPriceResponseMultiTickerModel.getDayMovingAverageModels().get(0).getAvg(), 2);
		Assert.assertEquals("FB", stockPriceResponseMultiTickerModel.getDayMovingAverageModels().get(1).getTicker());
		Assert.assertEquals(30.02, stockPriceResponseMultiTickerModel.getDayMovingAverageModels().get(1).getAvg(), 2);
		Assert.assertTrue(stockPriceResponseMultiTickerModel.getMessageModels().isEmpty());
	}

	@Test
	public void get200DayMovingAverage1000TickersWhenInputSomeInValidWillReturnValueAndMessages() throws Exception {
		List<String> tickers = new ArrayList<>();
		tickers.add("GE");
		tickers.add(" ");
		tickers.add(null);
		LocalDate startDate = LocalDate.now();
		LocalDate endDate = LocalDate.now();
		String result = "{\"dataset\":{\"id\":9775709,\"dataset_code\":\"GE\",\"database_code\":\"WIKI\",\"name\":\"General Electric Co\",\"description\":\"GE\",\"refreshed_at\":\"2017-04-28T21:47:39.087Z\",\"newest_available_date\":\"2017-04-28\",\"oldest_available_date\":\"1962-01-02\",\"column_names\":[\"Date\",\"Open\",\"High\",\"Low\",\"Close\",\"Volume\",\"Ex-Dividend\",\"Split Ratio\",\"Adj. Open\",\"Adj. High\",\"Adj. Low\",\"Adj. Close\",\"Adj. Volume\"],\"frequency\":\"daily\",\"type\":\"Time Series\",\"premium\":false,\"limit\":null,\"transform\":null,\"column_index\":null,\"start_date\":\"2017-04-04\",\"end_date\":\"2017-04-04\",\"data\":[[\"2017-04-04\",29.9,30.09,29.7,30.02,25056918.0,0.0,1.0,29.9,30.09,29.7,30.02,25056918.0]],\"collapse\":null,\"order\":null,\"database_id\":4922}}";
		Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.anyObject())).thenReturn(result);
		StockPriceResponseMultiTickerModel stockPriceResponseMultiTickerModel = stockPriceServiceImpl
				.get200DayMovingAverage1000Tickers(startDate, endDate, tickers);
		Assert.assertNotNull(stockPriceResponseMultiTickerModel);
		Assert.assertNotNull(stockPriceResponseMultiTickerModel.getDayMovingAverageModels());
		Assert.assertEquals(1, stockPriceResponseMultiTickerModel.getDayMovingAverageModels().size());
		Assert.assertEquals("GE", stockPriceResponseMultiTickerModel.getDayMovingAverageModels().get(0).getTicker());
		Assert.assertEquals(30.02, stockPriceResponseMultiTickerModel.getDayMovingAverageModels().get(0).getAvg(), 2);
		Assert.assertFalse(stockPriceResponseMultiTickerModel.getMessageModels().isEmpty());
		Assert.assertEquals(2, stockPriceResponseMultiTickerModel.getMessageModels().size());
	}
}
