/*
 * GPABillingReportsForm.java Created on Mar 13, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling;

import com.ibsplc.icargo.framework.model.ScreenModel;
import com.ibsplc.icargo.framework.client.daterange.notation.DateFieldId;

/**
 * @author A-1556
 *
 */
public class GPABillingReportsForm extends ScreenModel {

	private static final String BUNDLE ="gpabillingreports";

	//private String bundle;

	private static final String PRODUCT = "mail";

	private static final String SUBPRODUCT = "mra";

	private static final String SCREENID = "mailtracking.mra.gpabilling.gpabillingreports";
	
	private String fromDatePeriodBillSmy;
	
	private String toDatePeriodBillSmy;
	
	private String gpaCodePeriodBillSmy;
	
	private String gpaNamePeriodBillSmy;
	
	private String fromDateGpaBillSmy;
	
	private String toDateGpaBillSmy;
	
	private String gpaCodeGpaBillSmy;
	
	private String gpaNameGpaBillSmy;

	private String fromDatePeriod51;
	
	private String toDatePeriod51;
	
	private String gpaCodePeriod51;
	
	private String gpaNamePeriod51;
	
	private String fromDateGpa51;
	
	private String toDateGpa51;
	
	private String gpaCodeGpa51;
	
	private String gpaNameGpa51;
	
	private String fromDatePeriod66;
	
	private String toDatePeriod66;
	
	private String gpaCodePeriod66;
	
	private String gpaNamePeriod66;
	
	private String fromDateGpa66;
	
	private String toDateGpa66;
	
	private String gpaCodeGpa66;
	
	private String gpaNameGpa66;
	
	private String reportIdentifiers;
	
	private String country;
	
	private String selectedReport;
	
	private String gpaCode;
	
	private String gpaName;
	
	private String billingStatus;
	private String specificFlag;//Added as part of ICRD-111958
	//Added as part of ICRD-118899 starts
	private String fromDateBlbRpt;
	private String toDateBlbRpt;
	//Added as part of ICRD-118899 ends
	/**
	 * @return Returns the PRODUCT.
	 */
	public String getPRODUCT() {
		return PRODUCT;
	}

	/**
	 * @return Returns the SUBPRODUCT.
	 */
	public String getSUBPRODUCT() {
		return SUBPRODUCT;
	}
	/**
	 * @return Returns the ScreenID.
	 */
    public String getScreenId() {
        return SCREENID;
    }

    /**
	 * @return Returns the product.
	 */
    public String getProduct() {
        return PRODUCT;
    }

    /**
	 * @return Returns the subproduct.
	 */
    public String getSubProduct() {
        return SUBPRODUCT;
    }

    /**
     * @return
     */
    public static String getBUNDLE() {
		return BUNDLE;
	}
	/**
	 * @param bundle The bundle to set.
	 */
	public void setBundle(String bundle) {
		//this.bundle = bundle;
	}
	/**
	 * @return Returns the bundle.
	 */
	public String getBundle() {
		return BUNDLE;
	}

	
	

	/**
	 * @return Returns the fromDateGpa51.
	 */
	@DateFieldId(id="GPABillingReportsCN51GpaDateRange",fieldType="from") //Added by T-1927 for ICRD-9704
	public String getFromDateGpa51() {
		return fromDateGpa51;
	}

	/**
	 * @param fromDateGpa51 The fromDateGpa51 to set.
	 */
	public void setFromDateGpa51(String fromDateGpa51) {
		this.fromDateGpa51 = fromDateGpa51;
	}

	/**
	 * @return Returns the fromDatePeriod51.
	 */
	@DateFieldId(id="GPABillingReportsCN51PeriodDateRange",fieldType="from") //Added by T-1927 for ICRD-9704
	public String getFromDatePeriod51() {
		return fromDatePeriod51;
	}

	/**
	 * @param fromDatePeriod51 The fromDatePeriod51 to set.
	 */
	public void setFromDatePeriod51(String fromDatePeriod51) {
		this.fromDatePeriod51 = fromDatePeriod51;
	}

	/**
	 * @return Returns the gpaCodeGpa51.
	 */
	public String getGpaCodeGpa51() {
		return gpaCodeGpa51;
	}

	/**
	 * @param gpaCodeGpa51 The gpaCodeGpa51 to set.
	 */
	public void setGpaCodeGpa51(String gpaCodeGpa51) {
		this.gpaCodeGpa51 = gpaCodeGpa51;
	}

	/**
	 * @return Returns the gpaCodePeriod51.
	 */
	public String getGpaCodePeriod51() {
		return gpaCodePeriod51;
	}

	/**
	 * @param gpaCodePeriod51 The gpaCodePeriod51 to set.
	 */
	public void setGpaCodePeriod51(String gpaCodePeriod51) {
		this.gpaCodePeriod51 = gpaCodePeriod51;
	}

	/**
	 * @return Returns the gpaNameGpa51.
	 */
	public String getGpaNameGpa51() {
		return gpaNameGpa51;
	}

	/**
	 * @param gpaNameGpa51 The gpaNameGpa51 to set.
	 */
	public void setGpaNameGpa51(String gpaNameGpa51) {
		this.gpaNameGpa51 = gpaNameGpa51;
	}

	/**
	 * @return Returns the gpaNamePeriod51.
	 */
	public String getGpaNamePeriod51() {
		return gpaNamePeriod51;
	}

	/**
	 * @param gpaNamePeriod51 The gpaNamePeriod51 to set.
	 */
	public void setGpaNamePeriod51(String gpaNamePeriod51) {
		this.gpaNamePeriod51 = gpaNamePeriod51;
	}

	/**
	 * @return Returns the toDateGpa51.
	 */
	@DateFieldId(id="GPABillingReportsCN51GpaDateRange",fieldType="to") //Added by T-1927 for ICRD-9704
	public String getToDateGpa51() {
		return toDateGpa51;
	}

	/**
	 * @param toDateGpa51 The toDateGpa51 to set.
	 */
	public void setToDateGpa51(String toDateGpa51) {
		this.toDateGpa51 = toDateGpa51;
	}

	/**
	 * @return Returns the toDatePeriod51.
	 */
	@DateFieldId(id="GPABillingReportsCN51PeriodDateRange",fieldType="to") //Added by T-1927 for ICRD-9704
	public String getToDatePeriod51() {
		return toDatePeriod51;
	}

	/**
	 * @param toDatePeriod51 The toDatePeriod51 to set.
	 */
	public void setToDatePeriod51(String toDatePeriod51) {
		this.toDatePeriod51 = toDatePeriod51;
	}

	/**
	 * @return Returns the reportIdentifiers.
	 */
	public String getReportIdentifiers() {
		return reportIdentifiers;
	}

	/**
	 * @param reportIdentifiers The reportIdentifiers to set.
	 */
	public void setReportIdentifiers(String reportIdentifiers) {
		this.reportIdentifiers = reportIdentifiers;
	}

	/**
	 * @return Returns the fromDateGpaBillSmy.
	 */
	@DateFieldId(id="GPABillingReportsGPAWiseDateRange",fieldType="from") //Added by T-1927 for ICRD-9704
	public String getFromDateGpaBillSmy() {
		return fromDateGpaBillSmy;
	}

	/**
	 * @param fromDateGpaBillSmy The fromDateGpaBillSmy to set.
	 */
	public void setFromDateGpaBillSmy(String fromDateGpaBillSmy) {
		this.fromDateGpaBillSmy = fromDateGpaBillSmy;
	}

	/**
	 * @return Returns the fromDatePeriodBillSmy.
	 */
	@DateFieldId(id="GPABillingReportsDateRange",fieldType="from") //Added by T-1927 for ICRD-9704
	public String getFromDatePeriodBillSmy() {
		return fromDatePeriodBillSmy;
	}

	/**
	 * @param fromDatePeriodBillSmy The fromDatePeriodBillSmy to set.
	 */
	public void setFromDatePeriodBillSmy(String fromDatePeriodBillSmy) {
		this.fromDatePeriodBillSmy = fromDatePeriodBillSmy;
	}

	/**
	 * @return Returns the gpaCodeGpaBillSmy.
	 */
	public String getGpaCodeGpaBillSmy() {
		return gpaCodeGpaBillSmy;
	}

	/**
	 * @param gpaCodeGpaBillSmy The gpaCodeGpaBillSmy to set.
	 */
	public void setGpaCodeGpaBillSmy(String gpaCodeGpaBillSmy) {
		this.gpaCodeGpaBillSmy = gpaCodeGpaBillSmy;
	}

	/**
	 * @return Returns the gpaCodePeriodBillSmy.
	 */
	public String getGpaCodePeriodBillSmy() {
		return gpaCodePeriodBillSmy;
	}

	/**
	 * @param gpaCodePeriodBillSmy The gpaCodePeriodBillSmy to set.
	 */
	public void setGpaCodePeriodBillSmy(String gpaCodePeriodBillSmy) {
		this.gpaCodePeriodBillSmy = gpaCodePeriodBillSmy;
	}

	/**
	 * @return Returns the gpaNameGpaBillSmy.
	 */
	public String getGpaNameGpaBillSmy() {
		return gpaNameGpaBillSmy;
	}

	/**
	 * @param gpaNameGpaBillSmy The gpaNameGpaBillSmy to set.
	 */
	public void setGpaNameGpaBillSmy(String gpaNameGpaBillSmy) {
		this.gpaNameGpaBillSmy = gpaNameGpaBillSmy;
	}

	/**
	 * @return Returns the gpaNamePeriodBillSmy.
	 */
	public String getGpaNamePeriodBillSmy() {
		return gpaNamePeriodBillSmy;
	}

	/**
	 * @param gpaNamePeriodBillSmy The gpaNamePeriodBillSmy to set.
	 */
	public void setGpaNamePeriodBillSmy(String gpaNamePeriodBillSmy) {
		this.gpaNamePeriodBillSmy = gpaNamePeriodBillSmy;
	}

	/**
	 * @return Returns the toDateGpaBillSmy.
	 */
	@DateFieldId(id="GPABillingReportsGPAWiseDateRange",fieldType="to") //Added by T-1927 for ICRD-9704
	public String getToDateGpaBillSmy() {
		return toDateGpaBillSmy;
	}

	/**
	 * @param toDateGpaBillSmy The toDateGpaBillSmy to set.
	 */
	public void setToDateGpaBillSmy(String toDateGpaBillSmy) {
		this.toDateGpaBillSmy = toDateGpaBillSmy;
	}

	/**
	 * @return Returns the toDatePeriodBillSmy.
	 */
	@DateFieldId(id="GPABillingReportsDateRange",fieldType="to") //Added by T-1927 for ICRD-9704
	public String getToDatePeriodBillSmy() {
		return toDatePeriodBillSmy;
	}

	/**
	 * @param toDatePeriodBillSmy The toDatePeriodBillSmy to set.
	 */
	public void setToDatePeriodBillSmy(String toDatePeriodBillSmy) {
		this.toDatePeriodBillSmy = toDatePeriodBillSmy;
	}

	/**
	 * @return Returns the country.
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country The country to set.
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return Returns the selectedReport.
	 */
	public String getSelectedReport() {
		return selectedReport;
	}

	/**
	 * @param selectedReport The selectedReport to set.
	 */
	public void setSelectedReport(String selectedReport) {
		this.selectedReport = selectedReport;
	}

	/**
	 * @return Returns the fromDateGpa66.
	 */
	@DateFieldId(id="GPABillingReportsCN66GpaDateRange",fieldType="from") //Added by T-1927 for ICRD-9704
	public String getFromDateGpa66() {
		return fromDateGpa66;
	}

	/**
	 * @param fromDateGpa66 The fromDateGpa66 to set.
	 */
	public void setFromDateGpa66(String fromDateGpa66) {
		this.fromDateGpa66 = fromDateGpa66;
	}

	/**
	 * @return Returns the fromDatePeriod66.
	 */
	@DateFieldId(id="GPABillingReportsCN66PeriodDateRange",fieldType="from") //Added by T-1927 for ICRD-9704
	public String getFromDatePeriod66() {
		return fromDatePeriod66;
	}

	/**
	 * @param fromDatePeriod66 The fromDatePeriod66 to set.
	 */
	public void setFromDatePeriod66(String fromDatePeriod66) {
		this.fromDatePeriod66 = fromDatePeriod66;
	}

	/**
	 * @return Returns the gpaCodeGpa66.
	 */
	public String getGpaCodeGpa66() {
		return gpaCodeGpa66;
	}

	/**
	 * @param gpaCodeGpa66 The gpaCodeGpa66 to set.
	 */
	public void setGpaCodeGpa66(String gpaCodeGpa66) {
		this.gpaCodeGpa66 = gpaCodeGpa66;
	}

	/**
	 * @return Returns the gpaCodePeriod66.
	 */
	public String getGpaCodePeriod66() {
		return gpaCodePeriod66;
	}

	/**
	 * @param gpaCodePeriod66 The gpaCodePeriod66 to set.
	 */
	public void setGpaCodePeriod66(String gpaCodePeriod66) {
		this.gpaCodePeriod66 = gpaCodePeriod66;
	}

	/**
	 * @return Returns the gpaNameGpa66.
	 */
	public String getGpaNameGpa66() {
		return gpaNameGpa66;
	}

	/**
	 * @param gpaNameGpa66 The gpaNameGpa66 to set.
	 */
	public void setGpaNameGpa66(String gpaNameGpa66) {
		this.gpaNameGpa66 = gpaNameGpa66;
	}

	/**
	 * @return Returns the gpaNamePeriod66.
	 */
	public String getGpaNamePeriod66() {
		return gpaNamePeriod66;
	}

	/**
	 * @param gpaNamePeriod66 The gpaNamePeriod66 to set.
	 */
	public void setGpaNamePeriod66(String gpaNamePeriod66) {
		this.gpaNamePeriod66 = gpaNamePeriod66;
	}

	/**
	 * @return Returns the toDateGpa66.
	 */
	@DateFieldId(id="GPABillingReportsCN66GpaDateRange",fieldType="to") //Added by T-1927 for ICRD-9704
	public String getToDateGpa66() {
		return toDateGpa66;
	}

	/**
	 * @param toDateGpa66 The toDateGpa66 to set.
	 */
	public void setToDateGpa66(String toDateGpa66) {
		this.toDateGpa66 = toDateGpa66;
	}

	/**
	 * @return Returns the toDatePeriod66.
	 */
	@DateFieldId(id="GPABillingReportsCN66PeriodDateRange",fieldType="to") //Added by T-1927 for ICRD-9704
	public String getToDatePeriod66() {
		return toDatePeriod66;
	}

	/**
	 * @param toDatePeriod66 The toDatePeriod66 to set.
	 */
	public void setToDatePeriod66(String toDatePeriod66) {
		this.toDatePeriod66 = toDatePeriod66;
	}

	/**
	 * @return Returns the billingStatus.
	 */
	public String getBillingStatus() {
		return billingStatus;
	}

	/**
	 * @param billingStatus The billingStatus to set.
	 */
	public void setBillingStatus(String billingStatus) {
		this.billingStatus = billingStatus;
	}

	/**
	 * @return Returns the gpaCode.
	 */
	public String getGpaCode() {
		return gpaCode;
	}

	/**
	 * @param gpaCode The gpaCode to set.
	 */
	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
	}

	/**
	 * @return Returns the gpaName.
	 */
	public String getGpaName() {
		return gpaName;
	}

	/**
	 * @param gpaName The gpaName to set.
	 */
	public void setGpaName(String gpaName) {
		this.gpaName = gpaName;
	}
	/**
	 * @return the specificFlag
	 */
	public String getSpecificFlag() {
		return specificFlag;
	}
	/**
	 * @param specificFlag the specificFlag to set
	 */
	public void setSpecificFlag(String specificFlag) {
		this.specificFlag = specificFlag;
	}
	/**
	 * @return the fromDateBlbRpt
	 */
	@DateFieldId(id="GPABillableReportDateRange",fieldType="from")
	public String getFromDateBlbRpt() {
		return fromDateBlbRpt;
	}
	/**
	 * @param fromDateBlbRpt the fromDateBlbRpt to set
	 */
	public void setFromDateBlbRpt(String fromDateBlbRpt) {
		this.fromDateBlbRpt = fromDateBlbRpt;
	}
	/**
	 * @return the toDateBlbRpt
	 */
	@DateFieldId(id="GPABillableReportDateRange",fieldType="to")
	public String getToDateBlbRpt() {
		return toDateBlbRpt;
	}
	/**
	 * @param toDateBlbRpt the toDateBlbRpt to set
	 */
	public void setToDateBlbRpt(String toDateBlbRpt) {
		this.toDateBlbRpt = toDateBlbRpt;
	}
	
}
