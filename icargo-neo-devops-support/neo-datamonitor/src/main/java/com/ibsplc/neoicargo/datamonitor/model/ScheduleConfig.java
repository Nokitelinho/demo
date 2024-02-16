package com.ibsplc.neoicargo.datamonitor.model;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleConfig {
	private long fixedFrequencyInSeconds;
	private String cronExpression;
	private long fixedDelayInSeconds;
}
