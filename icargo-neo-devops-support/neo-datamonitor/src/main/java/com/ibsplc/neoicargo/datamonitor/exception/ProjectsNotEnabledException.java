package com.ibsplc.neoicargo.datamonitor.exception;

public class ProjectsNotEnabledException extends RuntimeException {

	public static final String NO_PROJECTS_ENABLED = "No projects are enabled for the monitoring of data for tenant <%s> in environment <%s>";

	public ProjectsNotEnabledException(String environment, String tenant) {
		super(String.format(NO_PROJECTS_ENABLED, tenant, environment));
	}

}
