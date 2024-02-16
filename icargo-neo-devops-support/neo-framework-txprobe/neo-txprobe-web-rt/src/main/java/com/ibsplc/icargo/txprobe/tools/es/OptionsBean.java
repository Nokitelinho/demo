/*
 * OptionsBean.java Created on 22-Apr-2016
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.txprobe.tools.es;

import com.beust.jcommander.Parameter;

/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			22-Apr-2016       		Jens J P 			First Draft
 */
/**
 * @author A-2394
 *
 */
public class OptionsBean {

	@Parameter(names = {"-c", "--cid"}, required = false, description = "Correlation Id")
	public String correlationId;
	
	@Parameter(names = {"-f", "--format"}, required = false, description = "Export format xls | txt | html")
	public String format = "html";
	
	@Parameter(names = {"-d", "--dest"}, required = false, description = "Output folder")
	public String outputDirectory;
	
	@Parameter(names = {"-u", "--user"}, required = false, description = "User Id")
	public String user;
	
	@Parameter(names = {"-s", "--start"}, required = false, description = "Start Date in format yyyyMMddHHmm eg 201604011330 (01 Apr 2016 13:30)")
	public String startDate;
	
	@Parameter(names = {"-e", "--end"}, required = false, description = "End Date in format yyyyMMddHHmm eg 201604011330 (01 Apr 2016 13:30)")
	public String endDate;
	
	@Parameter(names = {"-x", "--errors"}, required = false, description = "Filter transactions which contains errors")
	public boolean errorsOnly;
	
	@Parameter(names = {"-t", "--error-type"}, required = false, description = "Error Type, Possible Values are BSE | RTE | SYE")
	public String errorType;
	
	@Parameter(names = {"-n", "--node"}, required = false, description = "Node name")
	public String nodeName;
	
	@Parameter(names = {"-i", "--iid"}, required = false, description = "InvocationId ( TransactionId in case of service calls)")
	public String invocationId;
	
	@Parameter(names = {"-m", "--module"}, required = false, description = "Module name")
	public String module;
	
	@Parameter(names = {"-b", "--submodule"}, required = false, description = "SubModule name")
	public String submodule;
	
	@Parameter(names = {"-p", "--probe"}, required = false, description = "Probe Type")
	public String probeType;
	
	@Parameter(names = {"-md", "--min-depth"}, required = false, description = "Minimum number of transactions in the group")
	public int depth = 1;
	
	@Parameter(names = {"-a", "--search"}, required = false, description = "Search String ( Strings can be separated by + (AND) or | (OR) to apply conditions ) "
			+ "default operator is AND for separator SPACE")
	public String search;
	
	@Parameter(names = {"-h", "--help"}, required = false, description = "Shows Help")
	public boolean isHelp;

	/**
	 * @return the correlationId
	 */
	public String getCorrelationId() {
		return correlationId;
	}

	/**
	 * @param correlationId the correlationId to set
	 */
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	/**
	 * @return the format
	 */
	public String getFormat() {
		return format;
	}

	/**
	 * @param format the format to set
	 */
	public void setFormat(String format) {
		this.format = format;
	}

	/**
	 * @return the outputDirectory
	 */
	public String getOutputDirectory() {
		return outputDirectory;
	}

	/**
	 * @param outputDirectory the outputDirectory to set
	 */
	public void setOutputDirectory(String outputDirectory) {
		this.outputDirectory = outputDirectory;
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the errorsOnly
	 */
	public boolean isErrorsOnly() {
		return errorsOnly;
	}

	/**
	 * @param errorsOnly the errorsOnly to set
	 */
	public void setErrorsOnly(boolean errorsOnly) {
		this.errorsOnly = errorsOnly;
	}

	/**
	 * @return the nodeName
	 */
	public String getNodeName() {
		return nodeName;
	}

	/**
	 * @param nodeName the nodeName to set
	 */
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	/**
	 * @return the invocationId
	 */
	public String getInvocationId() {
		return invocationId;
	}

	/**
	 * @param invocationId the invocationId to set
	 */
	public void setInvocationId(String invocationId) {
		this.invocationId = invocationId;
	}

	/**
	 * @return the isHelp
	 */
	public boolean isHelp() {
		return isHelp;
	}

	/**
	 * @param isHelp the isHelp to set
	 */
	public void setHelp(boolean isHelp) {
		this.isHelp = isHelp;
	}

	/**
	 * @return the module
	 */
	public String getModule() {
		return module;
	}

	/**
	 * @param module the module to set
	 */
	public void setModule(String module) {
		this.module = module;
	}

	/**
	 * @return the submodule
	 */
	public String getSubmodule() {
		return submodule;
	}

	/**
	 * @param submodule the submodule to set
	 */
	public void setSubmodule(String submodule) {
		this.submodule = submodule;
	}

	/**
	 * @return the probeType
	 */
	public String getProbeType() {
		return probeType;
	}

	/**
	 * @param probeType the probeType to set
	 */
	public void setProbeType(String probeType) {
		this.probeType = probeType;
	}

	/**
	 * @return the errorType
	 */
	public String getErrorType() {
		return errorType;
	}

	/**
	 * @param errorType the errorType to set
	 */
	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}

	/**
	 * @return the depth
	 */
	public int getDepth() {
		return depth;
	}

	/**
	 * @param depth the depth to set
	 */
	public void setDepth(int depth) {
		this.depth = depth;
	}

	/**
	 * @return the search
	 */
	public String getSearch() {
		return search;
	}

	/**
	 * @param search the search to set
	 */
	public void setSearch(String search) {
		this.search = search;
	}
	
	
	
}

