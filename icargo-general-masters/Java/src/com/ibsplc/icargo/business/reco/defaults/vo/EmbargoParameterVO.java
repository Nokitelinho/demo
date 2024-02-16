/*
 * EmbargoParameterVO.java Created on Jul 28, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary 
 * information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.reco.defaults.vo;

import java.util.List;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1358
 * 
 */
public class EmbargoParameterVO extends AbstractVO {

	private String aplFlg;
	
	private String companyCode;

	private String embargoReferenceNumber;

	private String parameterCode;

	private String applicable;

	private String parameterValues;

	private String operationalFlag;

	private String parameterDescription;
	
	private String carrierCode;
	
	private String flightNumber;

	private String applicableLevel;

	private String parameterLevel;

	private int embargoVersion;
	
	/** Added by A-5867 for ICRD-68630**/
	private List<String> subModules;
	private String sccGroup;
	public String getSccGroup() {
		return sccGroup;
	}

	public void setSccGroup(String sccGroup) {
		this.sccGroup = sccGroup;
	}

	public int getEmbargoVersion() {
		return embargoVersion;
	}

	public void setEmbargoVersion(int embargoVersion) {
		this.embargoVersion = embargoVersion;
	}

	public String getParameterLevel() {
		return parameterLevel;
	}

	public void setParameterLevel(String parameterLevel) {
		this.parameterLevel = parameterLevel;
	}

	public String getApplicableLevel() {
		return applicableLevel;
	}

	public void setApplicableLevel(String applicableLevel) {
		this.applicableLevel = applicableLevel;
	}

	public String getParameterDescription() {
		return parameterDescription;
	}

	public void setParameterDescription(String parameterDescription) {
		this.parameterDescription = parameterDescription;
	}

	/**
	 * @return Returns the aplFlg.
	 */
	public String getAplFlg() {
		return aplFlg;
	}

	/**
	 * @param aplFlg The aplFlg to set.
	 */
	public void setAplFlg(String aplFlg) {
		this.aplFlg = aplFlg;
	}

	/**
	 * @return Returns the companyCode.
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return Returns the embargoReferenceNumber.
	 */
	public String getEmbargoReferenceNumber() {
		return embargoReferenceNumber;
	}

	/**
	 * @param embargoReferenceNumber The embargoReferenceNumber to set.
	 */
	public void setEmbargoReferenceNumber(String embargoReferenceNumber) {
		this.embargoReferenceNumber = embargoReferenceNumber;
	}

	

	public String getApplicable() {
		return applicable;
	}

	public void setApplicable(String applicable) {
		this.applicable = applicable;
	}

	/**
	 * @return Returns the operationalFlag.
	 */
	public String getOperationalFlag() {
		return operationalFlag;
	}

	/**
	 * @param operationalFlag The operationalFlag to set.
	 */
	public void setOperationalFlag(String operationalFlag) {
		this.operationalFlag = operationalFlag;
	}

	/**
	 * @return Returns the parameterCode.
	 */
	public String getParameterCode() {
		return parameterCode;
	}

	/**
	 * @param parameterCode The parameterCode to set.
	 */
	public void setParameterCode(String parameterCode) {
		this.parameterCode = parameterCode;
	}

	/**
	 * @return Returns the parameterValues.
	 */
	public String getParameterValues() {
		return parameterValues;
	}

	/**
	 * @param parameterValues The parameterValues to set.
	 */
	public void setParameterValues(String parameterValues) {
		this.parameterValues = parameterValues;
	}

	/**
	 * @return Returns the carrierCode.
	 */
	public String getCarrierCode() {
		return carrierCode;
	}

	/**
	 * @param carrierCode The carrierCode to set.
	 */
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	/**
	 * @return Returns the flightNumber.
	 */
	public String getFlightNumber() {
		return flightNumber;
	}

	/**
	 * @param flightNumber The flightNumber to set.
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public void setSubModules(List<String> subModules) {
		this.subModules = subModules;
	}

	public List<String> getSubModules() {
		return subModules;
	}

	


}
