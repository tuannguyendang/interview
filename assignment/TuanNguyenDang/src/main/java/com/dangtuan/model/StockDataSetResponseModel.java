package com.dangtuan.model;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown= true)
public class StockDataSetResponseModel {
	private String[] column_names;
	private ArrayList<String[]> data;

	public String[] getColumn_names() {
		return column_names;
	}

	public void setColumn_names(String[] column_names) {
		this.column_names = column_names;
	}

	public ArrayList<String[]> getData() {
		return data;
	}

	public void setData(ArrayList<String[]> data) {
		this.data = data;
	}
}
