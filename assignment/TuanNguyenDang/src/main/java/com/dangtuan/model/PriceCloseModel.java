package com.dangtuan.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value= "Prices")
public class PriceCloseModel {
	@JsonProperty("Ticker")
	private String ticker;
	@JsonProperty("DateClose")
	private List<String[]> dateClose;

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public List<String[]> getDateClose() {
		return dateClose;
	}

	public void setDateClose(List<String[]> dateClose) {
		this.dateClose = dateClose;
	}

}
