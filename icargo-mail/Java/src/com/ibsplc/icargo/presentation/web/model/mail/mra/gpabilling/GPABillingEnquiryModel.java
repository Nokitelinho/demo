/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.model.mail.mra.gpabilling.GPABillingEnquiryModel.java
 *
 *	Created by	:	A-4809
 *	Created on	:	Dec 17, 2018
 *
 *  Copyright 2018 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.model.mail.mra.gpabilling;

import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.framework.web.json.PageResult;
import com.ibsplc.icargo.framework.web.spring.model.AbstractScreenModel;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.GPABillingEntryDetails;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.GPABillingEntryFilter;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.SurchargeDetails;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.ConsignmentDetails;
import com.ibsplc.icargo.presentation.web.model.shared.defaults.common.OneTime;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.model.mail.mra.gpabilling.GPABillingEnquiryModel.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	Dec 17, 2018	:	Draft
 */
public class GPABillingEnquiryModel extends AbstractScreenModel{
	
	private static final String PRODUCT = "mail";
	private static final String SUBPRODUCT = "mra";
	private static final String SCREENID = "mail.mra.gpabilling.ux.billingentries";
	
	private Map<String, Collection<OneTime>> oneTimeValues;
	private Map<String, Collection<OneTime>> oneTimeValuesStatus;
	private Map<String, String> systemParameters;
	private GPABillingEntryFilter gpaBillingEntryFilter; 
	private PageResult<GPABillingEntryDetails> gpaBillingEntryDetails;
	private PageResult<ConsignmentDetails> consignmentDetails;
	private Collection<GPABillingEntryDetails> selectedBillingDetails;
	private Collection<ConsignmentDetails> selectedConsignmentDetails;
	private String mcaPrivilege;
	private String fromBillingList;
	private String fromConsignmentList;
	private Collection<SurchargeDetails> surchargeDetails;
	private String dsn;
	private String billingStatus;
	private String remarks;
	private String records;
	private String containerRatingPAList;
	private String maxPageCount;

	@Override
	public String getProduct() {
		return PRODUCT;
	}

	@Override
	public String getScreenId() {
		return SUBPRODUCT;
	}

	@Override
	public String getSubProduct() {
		return SCREENID;
	}

	public Map<String, Collection<OneTime>> getOneTimeValues() {
		return oneTimeValues;
	}

	public void setOneTimeValues(Map<String, Collection<OneTime>> oneTimeValues) {
		this.oneTimeValues = oneTimeValues;
	}

	public Map<String, String> getSystemParameters() {
		return systemParameters;
	}

	public void setSystemParameters(Map<String, String> systemParameters) {
		this.systemParameters = systemParameters;
	}

	public GPABillingEntryFilter getGpaBillingEntryFilter() {
		return gpaBillingEntryFilter;
	}

	public void setGpaBillingEntryFilter(GPABillingEntryFilter gpaBillingEntryFilter) {
		this.gpaBillingEntryFilter = gpaBillingEntryFilter;
	}

	public PageResult<GPABillingEntryDetails> getGpaBillingEntryDetails() {
		return gpaBillingEntryDetails;
	}

	public void setGpaBillingEntryDetails(PageResult<GPABillingEntryDetails> gpaBillingEntryDetails) {
		this.gpaBillingEntryDetails = gpaBillingEntryDetails;
	}

	public PageResult<ConsignmentDetails> getConsignmentDetails() {
		return consignmentDetails;
	}

	public void setConsignmentDetails(PageResult<ConsignmentDetails> consignmentDetails) {
		this.consignmentDetails = consignmentDetails;
	}

	public Collection<GPABillingEntryDetails> getSelectedBillingDetails() {
		return selectedBillingDetails;
	}

	public void setSelectedBillingDetails(Collection<GPABillingEntryDetails> selectedBillingDetails) {
		this.selectedBillingDetails = selectedBillingDetails;
	}

	public String getMcaPrivilege() {
		return mcaPrivilege;
	}

	public void setMcaPrivilege(String mcaPrivilege) {
		this.mcaPrivilege = mcaPrivilege;
	}

	public String getFromBillingList() {
		return fromBillingList;
	}

	public void setFromBillingList(String fromBillingList) {
		this.fromBillingList = fromBillingList;
	}

	public String getFromConsignmentList() {
		return fromConsignmentList;
	}

	public void setFromConsignmentList(String fromConsignmentList) {
		this.fromConsignmentList = fromConsignmentList;
	}

	public Collection<SurchargeDetails> getSurchargeDetails() {
		return surchargeDetails;
	}

	public void setSurchargeDetails(Collection<SurchargeDetails> surchargeDetails) {
		this.surchargeDetails = surchargeDetails;
	}

	public String getDsn() {
		return dsn;
	}

	public void setDsn(String dsn) {
		this.dsn = dsn;
	}

	public String getBillingStatus() {
		return billingStatus;
	}

	public void setBillingStatus(String billingStatus) {
		this.billingStatus = billingStatus;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getRecords() {
		return records;
	}

	public void setRecords(String records) {
		this.records = records;
	}

	public Collection<ConsignmentDetails> getSelectedConsignmentDetails() {
		return selectedConsignmentDetails;
	}

	public void setSelectedConsignmentDetails(Collection<ConsignmentDetails> selectedConsignmentDetails) {
		this.selectedConsignmentDetails = selectedConsignmentDetails;
	}

	public Map<String, Collection<OneTime>> getOneTimeValuesStatus() {
		return oneTimeValuesStatus;
	}

	public void setOneTimeValuesStatus(Map<String, Collection<OneTime>> oneTimeValuesStatus) {
		this.oneTimeValuesStatus = oneTimeValuesStatus;
	}

	/**
	 * 	Getter for containerRatingPAList 
	 *	Added by : A-8061 on 09-Oct-2020
	 * 	Used for :
	 */
	public String getContainerRatingPAList() {
		return containerRatingPAList;
	}

	/**
	 *  @param containerRatingPAList the containerRatingPAList to set
	 * 	Setter for containerRatingPAList 
	 *	Added by : A-8061 on 09-Oct-2020
	 * 	Used for :
	 */
	public void setContainerRatingPAList(String containerRatingPAList) {
		this.containerRatingPAList = containerRatingPAList;
	}

	public String getMaxPageCount() {
		return maxPageCount;
	}

	public void setMaxPageCount(String maxPageCount) {
		this.maxPageCount = maxPageCount;
	}
	
	


}
