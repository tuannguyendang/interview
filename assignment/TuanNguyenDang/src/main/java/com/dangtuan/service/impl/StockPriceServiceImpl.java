package com.dangtuan.service.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.dangtuan.model.DayMovingAverageModel;
import com.dangtuan.model.MessageModel;
import com.dangtuan.model.PriceCloseModel;
import com.dangtuan.model.StockDataSetResponseModel;
import com.dangtuan.model.StockPriceResponseMultiTickerModel;
import com.dangtuan.service.StockPriceService;
import com.dangtuan.utils.ApplicationConstant;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * The Class StockPriceServiceImpl.
 * 
 * @author tuannguyen
 */
@Service
public class StockPriceServiceImpl implements StockPriceService {

	/** The rest template. */
	@Autowired
	private RestTemplate restTemplate;

	/** The api key. */
	@Value("${quandl.com.api.key}")
	private String apiKey;

	/** The mapper. */
	@Autowired
	private ObjectMapper mapper;

	/** The template api URI. */
	private String templateApiURI = "https://www.quandl.com/api/v3/datasets/WIKI/%s.json?start_date=%s&end_date=%s&api_key=%s";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dangtuan.service.StockPriceService#getClosePrice(java.lang.String,
	 * java.time.LocalDate, java.time.LocalDate)
	 */
	@Override
	@Cacheable("ticker")
	public PriceCloseModel getClosePrice(String ticker, LocalDate startDate, LocalDate endDate) throws IOException {
		PriceCloseModel priceCloseModel = null;
		JsonNode jsonResult = callQuandlAPI(ticker, startDate, endDate, true);
		for (JsonNode jsonNode : jsonResult) {
			StockDataSetResponseModel stockDataSetResponseModel = mapper.readValue(jsonNode.toString(),
					StockDataSetResponseModel.class);
			List<String[]> datas = stockDataSetResponseModel.getData();
			String[] columns = stockDataSetResponseModel.getColumn_names();
			if (datas == null || datas.isEmpty() || columns == null || columns.length == 0) {
				throw new IOException(String.format(ApplicationConstant.NO_DATA_RETURN, ticker));
			}
			Iterator<String[]> it = datas.iterator();
			ArrayList<String[]> dateCloseData = new ArrayList<>();
			while (it.hasNext()) {
				String[] data = it.next();
				if (columns.length != data.length) {
					throw new IOException(String.format(ApplicationConstant.NO_DATA_RETURN, ticker));
				}
				String[] dateClose = new String[2];
				for (int i = 0; i < data.length; i++) {
					if (columns[i].equals("Date")) {
						dateClose[0] = data[i];
					} else if (columns[i].equals("Close")) {
						dateClose[1] = data[i];
					} else {
						continue;
					}
				}
				if (dateClose[0] != null && dateClose[1] != null) {
					dateCloseData.add(dateClose);
				}
			}
			priceCloseModel = new PriceCloseModel();
			priceCloseModel.setTicker(ticker);
			priceCloseModel.setDateClose(dateCloseData);
		}
		return priceCloseModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dangtuan.service.StockPriceService#get200DayMovingAverage(java.lang.
	 * String, java.time.LocalDate, java.time.LocalDate)
	 */
	@Override
	public DayMovingAverageModel get200DayMovingAverage(String ticker, LocalDate startDate, LocalDate endDate)
			throws IOException {
		return get200DAV(ticker, startDate, endDate, true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dangtuan.service.StockPriceService#findFirst200DayMovingAverage(java.
	 * lang.String, java.time.LocalDate, java.time.LocalDate)
	 */
	@Override
	public DayMovingAverageModel findFirst200DayMovingAverage(String ticker, LocalDate startDate, LocalDate endDate)
			throws IOException {
		return get200DAV(ticker, startDate, endDate, true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dangtuan.service.StockPriceService#get200DayMovingAverage1000Tickers(
	 * java.time.LocalDate, java.time.LocalDate, java.util.List)
	 */
	@Override
	public StockPriceResponseMultiTickerModel get200DayMovingAverage1000Tickers(LocalDate endDate, LocalDate startDate,
			List<String> tickers) throws IOException {
		StockPriceResponseMultiTickerModel stockPriceResponseMultiTickerModel = new StockPriceResponseMultiTickerModel();
		List<DayMovingAverageModel> dayMovingAverageModels = new ArrayList<>();
		List<MessageModel> messages = new ArrayList<>();
		for (int t = 0; t < tickers.size(); t++) {
			String ticker = tickers.get(t);
			if (ticker == null || ticker.trim().isEmpty()) {
				messages.add(new MessageModel(String.format(ApplicationConstant.INVALID_TICKER, ticker)));
				continue;
			}
			try {
				dayMovingAverageModels.add(get200DAV(ticker.trim(), startDate, endDate, false));
			} catch (Exception e) {
				messages.add(new MessageModel(String.format(ApplicationConstant.INVALID_TICKER, ticker.trim())));
				continue;
			}
		}
		stockPriceResponseMultiTickerModel.setDayMovingAverageModels(dayMovingAverageModels);
		stockPriceResponseMultiTickerModel.setMessageModels(messages);
		return stockPriceResponseMultiTickerModel;
	}

	/**
	 * Gets the 200dav.
	 *
	 * @param ticker
	 *            the ticker
	 * @param startDate
	 *            the start date
	 * @param endDate
	 *            the end date
	 * @param WeapRootValue
	 *            the value configuration add root value in JSON response
	 * @return the 200dav
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private DayMovingAverageModel get200DAV(String ticker, LocalDate startDate, LocalDate endDate,
			boolean WeapRootValue) throws IOException {
		JsonNode jsonResult = callQuandlAPI(ticker, startDate, endDate, WeapRootValue);
		int liner = 0;
		double average = 0;
		int multipliers = 0;
		for (JsonNode jsonNode : jsonResult) {
			StockDataSetResponseModel stockDataSetResponseModel = mapper.readValue(jsonNode.toString(),
					StockDataSetResponseModel.class);
			List<String[]> datas = stockDataSetResponseModel.getData();
			String[] columns = stockDataSetResponseModel.getColumn_names();
			if (datas == null || datas.isEmpty() || columns == null || columns.length == 0) {
				throw new IOException(String.format(ApplicationConstant.NO_DATA_RETURN, ticker));
			}
			Iterator<String[]> it = datas.iterator();
			liner = datas.size();
			while (it.hasNext()) {
				String[] data = it.next();
				if (columns.length != data.length) {
					throw new IOException(String.format(ApplicationConstant.NO_DATA_RETURN, ticker));
				}
				for (int i = 0; i < data.length; i++) {
					if (columns[i].equals("Close")) {
						average += liner * Double.valueOf(data[i]);
						multipliers += liner;
						liner--;
					} else {
						continue;
					}
				}
			}
		}
		DayMovingAverageModel dayMovingAverageModel = new DayMovingAverageModel();
		dayMovingAverageModel.setTicker(ticker);
		dayMovingAverageModel.setAvg(average / multipliers);
		return dayMovingAverageModel;
	}

	/**
	 * Call quandl.com API.
	 *
	 * @param ticker
	 *            the ticker
	 * @param startDate
	 *            the start date
	 * @param endDate
	 *            the end date
	 * @param WeapRootValue
	 *            the value configuration add root value in JSON response
	 * @return the JSON node
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private JsonNode callQuandlAPI(String ticker, LocalDate startDate, LocalDate endDate, boolean WeapRootValue)
			throws IOException {
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, WeapRootValue);
		String uri = String.format(templateApiURI, ticker, startDate, endDate, apiKey);
		String result = restTemplate.getForObject(uri, String.class);
		JsonNode jsonResult = mapper.readTree(result);
		return jsonResult;
	}
}
