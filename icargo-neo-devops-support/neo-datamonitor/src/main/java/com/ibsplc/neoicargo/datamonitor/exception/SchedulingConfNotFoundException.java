package com.ibsplc.neoicargo.datamonitor.exception;

public class SchedulingConfNotFoundException extends RuntimeException {

	private static final String MESSAGE = "Scheduling details not found for the monitor with id %s";

	public SchedulingConfNotFoundException(String key) {
		super(String.format(MESSAGE, key));
	}

}
