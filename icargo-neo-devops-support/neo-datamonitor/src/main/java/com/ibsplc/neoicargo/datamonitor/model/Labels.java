package com.ibsplc.neoicargo.datamonitor.model;

import com.ibsplc.neoicargo.common.types.Pair;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Labels {
	/*private String alertname;
	private String masterDocumentNumber;
	private String executionAirport;
	private String application;
	private String tenant;*/
	
	private Pair<String, String> pair;
}