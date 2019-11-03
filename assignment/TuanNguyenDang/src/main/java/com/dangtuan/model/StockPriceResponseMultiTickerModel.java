package com.dangtuan.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StockPriceResponseMultiTickerModel {
	@JsonProperty(value = "200dma1000t")
	List<DayMovingAverageModel> dayMovingAverageModels;

	@JsonProperty(value = "messages")
	List<MessageModel> messageModels;

	public List<DayMovingAverageModel> getDayMovingAverageModels() {
		return dayMovingAverageModels;
	}

	public void setDayMovingAverageModels(List<DayMovingAverageModel> dayMovingAverageModels) {
		this.dayMovingAverageModels = dayMovingAverageModels;
	}

	public List<MessageModel> getMessageModels() {
		return messageModels;
	}

	public void setMessageModels(List<MessageModel> messageModels) {
		this.messageModels = messageModels;
	}

}
