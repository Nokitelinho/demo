package com.ibsplc.neoicargo.datamonitor.model;

import com.ibsplc.neoicargo.framework.probe.CorrelationStrategy.GenerateNewCorrelationIdStrategy;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataMonitorConfig {
	private String id;
	private String module;
	private boolean cqrs;
	private String description;
	private boolean iterativeAlert;
	private String sqlquery;
	private boolean disabled;
	private String channels;
	//private long frequency;
	private ScheduleConfig scheduler;
	

}
