package com.dangtuan.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "200dma")
public class DayMovingAverageModel {
	@JsonProperty("Ticker")
	private String ticker;
	@JsonProperty("Avg")
	private double avg;

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public double getAvg() {
		return avg;
	}

	public void setAvg(double avg) {
		this.avg = avg;
	}

	@Override
	public String toString() {
		return "200dma" + this.ticker + this.avg;
	}
}
