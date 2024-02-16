package com.ibsplc.neoicargo.datamonitor.model;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Alert {
	private Map<String, String> labels;
	private Map<String, String> annotations;

}
