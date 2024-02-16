/*
 * Filter.java Created on 08-Feb-2016
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.txprobe.aggregator.handler.outbound;

import com.ibsplc.icargo.txprobe.api.Probe;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Arrays;

/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			08-Feb-2016       		Jens J P 			First Draft
 */
/**
 * @author A-2394
 *
 */
@Singleton
public class Filter{

	private Probe probeType;
	private String[] urlPatterns;
	private String[] modules;
	private String[] submodules;
	private String[] actions;
	private String[] soapActions;
	
	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@PostConstruct
	public void afterPropertiesSet(){
		if(this.modules != null){
			Arrays.sort(this.modules);
		}
		if(this.submodules != null){
			Arrays.sort(this.submodules);
		}
		if(this.actions != null){
			Arrays.sort(this.actions);
		}
	}

	/**
	 * @return the probeType
	 */
	public Probe getProbeType() {
		return probeType;
	}
	
	/**
	 * @param probeType the probeType to set
	 */
	public void setProbeType(Probe probeType) {
		this.probeType = probeType;
	}
	
	/**
	 * @return the urlPatterns
	 */
	public String[] getUrlPatterns() {
		return urlPatterns;
	}
	
	/**
	 * @param urlPatterns the urlPatterns to set
	 */
	public void setUrlPatterns(String[] urlPatterns) {
		this.urlPatterns = urlPatterns;
	}
	
	/**
	 * @return the modules
	 */
	public String[] getModules() {
		return modules;
	}
	
	/**
	 * @param modules the modules to set
	 */
	public void setModules(String[] modules) {
		this.modules = modules;
	}
	
	/**
	 * @return the submodules
	 */
	public String[] getSubmodules() {
		return submodules;
	}
	
	/**
	 * @param submodules the submodules to set
	 */
	public void setSubmodules(String[] submodules) {
		this.submodules = submodules;
	}
	
	/**
	 * @return the actions
	 */
	public String[] getActions() {
		return actions;
	}
	
	/**
	 * @param actions the actions to set
	 */
	public void setActions(String[] actions) {
		this.actions = actions;
	}

	/**
	 * @return the soapActions
	 */
	public String[] getSoapActions() {
		return soapActions;
	}

	/**
	 * @param soapActions the soapActions to set
	 */
	public void setSoapActions(String[] soapActions) {
		this.soapActions = soapActions;
	}
	
	
}
