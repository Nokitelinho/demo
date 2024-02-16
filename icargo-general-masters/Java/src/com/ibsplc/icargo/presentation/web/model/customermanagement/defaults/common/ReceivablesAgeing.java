package com.ibsplc.icargo.presentation.web.model.customermanagement.defaults.common;


public class ReceivablesAgeing{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	private double value;
	private int awbCount;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public int getAwbCount() {
		return awbCount;
	}
	public void setAwbCount(int awbCount) {
		this.awbCount = awbCount;
	}
	
	}
