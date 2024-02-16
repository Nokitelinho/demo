/*
 * ListEstimatedULDStockForm.java Created on Oct 22, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-2934
 *
 */
public class ListExcessStockAirportsForm extends ScreenModel {
    
	private static final String BUNDLE = "listexcessstockairports";
	private static final String PRODUCT = "uld";
	private static final String SUBPRODUCT = "defaults";
	private static final String SCREENID = "uld.defaults.stock.findairportswithexcessstock";
	public static final String PAGINATION_MODE_FROM_NAVIGATION="NAVIGATION";
	
	private String airport;
	private String uldType;
	
	private String airlinecode;	
	private String lastUpdatedDate;
	
	private String uldTypeCodes[];
	private String currentAvailability[];
	private String ucmUldIn[];
	private String ucmUldOut[];
	private String projectedULDCount[];
	private String minimumQuantity[];
	private String stockDeviation[];
	
	private String displayPage = "1";
	private String  lastPageNum = "0";
	private String bundle;  
	private int selectFlag;	
	
	
	//ADDED A-5125 
	private String fltDisplayPage="1";
	private String  fltlastPageNum = "0";
	private String origin;
	private String destination;
	private String fromDate;
	private String toDate;
	private String flightNumber[];
	private String org[];
	private String destn[];
	private String depature[];
	private String arrival[];
	private String aircraftType[];
	private String altmntWeight[];
	private String altmntVolume[];
	private String altmntQ7[];
	private String altmntQ6[];
	private String altmntL7[];
	private String altmntL3[];
	private String freesaleWeight[];
	private String freesaleVolume[];
	private String freesaleQ7[];
	private String freesaleQ6[];
	private String freesaleL7[];
	private String freesaleL3[];
	private String restrictions[];
	private String screenInvokeActionStatus;
	private String navigationMode;
	public String getNavigationMode() {
		return navigationMode;
	}
	public void setNavigationMode(String navigationMode) {
		this.navigationMode = navigationMode;
	}
	
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String[] getFlightNumber() {
		return flightNumber;
	}
	public void setFlightNumber(String[] flightNumber) {
		this.flightNumber = flightNumber;
	}
	public String[] getOrg() {
		return org;
	}
	public void setOrg(String[] org) {
		this.org = org;
	}
	public String[] getDestn() {
		return destn;
	}
	public void setDestn(String[] destn) {
		this.destn = destn;
	}
	public String[] getDepature() {
		return depature;
	}
	public void setDepature(String[] depature) {
		this.depature = depature;
	}
	public String[] getArrival() {
		return arrival;
	}
	public void setArrival(String[] arrival) {
		this.arrival = arrival;
	}
	public String[] getAircraftType() {
		return aircraftType;
	}
	public void setAircraftType(String[] aircraftType) {
		this.aircraftType = aircraftType;
	}
	public String[] getAltmntWeight() {
		return altmntWeight;
	}
	public void setAltmntWeight(String[] altmntWeight) {
		this.altmntWeight = altmntWeight;
	}
	public String[] getAltmntVolume() {
		return altmntVolume;
	}
	public void setAltmntVolume(String[] altmntVolume) {
		this.altmntVolume = altmntVolume;
	}
	public String[] getAltmntQ7() {
		return altmntQ7;
	}
	public void setAltmntQ7(String[] altmntQ7) {
		this.altmntQ7 = altmntQ7;
	}
	public String[] getAltmntQ6() {
		return altmntQ6;
	}
	public void setAltmntQ6(String[] altmntQ6) {
		this.altmntQ6 = altmntQ6;
	}
	public String[] getAltmntL7() {
		return altmntL7;
	}
	public void setAltmntL7(String[] altmntL7) {
		this.altmntL7 = altmntL7;
	}
	public String[] getAltmntL3() {
		return altmntL3;
	}
	public void setAltmntL3(String[] altmntL3) {
		this.altmntL3 = altmntL3;
	}
	public String[] getFreesaleWeight() {
		return freesaleWeight;
	}
	public void setFreesaleWeight(String[] freesaleWeight) {
		this.freesaleWeight = freesaleWeight;
	}
	public String[] getFreesaleVolume() {
		return freesaleVolume;
	}
	public void setFreesaleVolume(String[] freesaleVolume) {
		this.freesaleVolume = freesaleVolume;
	}
	public String[] getFreesaleQ7() {
		return freesaleQ7;
	}
	public void setFreesaleQ7(String[] freesaleQ7) {
		this.freesaleQ7 = freesaleQ7;
	}
	public String[] getFreesaleQ6() {
		return freesaleQ6;
	}
	public void setFreesaleQ6(String[] freesaleQ6) {
		this.freesaleQ6 = freesaleQ6;
	}
	public String[] getFreesaleL7() {
		return freesaleL7;
	}
	public void setFreesaleL7(String[] freesaleL7) {
		this.freesaleL7 = freesaleL7;
	}
	public String[] getFreesaleL3() {
		return freesaleL3;
	}
	public void setFreesaleL3(String[] freesaleL3) {
		this.freesaleL3 = freesaleL3;
	}
	public String[] getRestrictions() {
		return restrictions;
	}
	public void setRestrictions(String[] restrictions) {
		this.restrictions = restrictions;
	}
	public String getAirport() {
		return airport;
	}
	public void setAirport(String airport) {
		this.airport = airport;
	}
	public String getUldType() {
		return uldType;
	}
	public void setUldType(String uldType) {
		this.uldType = uldType;
	}
	public String getAirlinecode() {
		return airlinecode;
	}
	public void setAirlinecode(String airlinecode) {
		this.airlinecode = airlinecode;
	}
	public String getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(String lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	public String[] getUldTypeCodes() {
		return uldTypeCodes;
	}
	public void setUldTypeCodes(String[] uldTypeCode) {
		this.uldTypeCodes = uldTypeCode;
	}
	public String[] getCurrentAvailability() {
		return currentAvailability;
	}
	public void setCurrentAvailability(String[] currentAvailability) {
		this.currentAvailability = currentAvailability;
	}
	public String[] getUcmUldIn() {
		return ucmUldIn;
	}
	public void setUcmUldIn(String[] ucmUldIn) {
		this.ucmUldIn = ucmUldIn;
	}
	public String[] getUcmUldOut() {
		return ucmUldOut;
	}
	public void setUcmUldOut(String[] ucmUldOut) {
		this.ucmUldOut = ucmUldOut;
	}
	public String[] getProjectedULDCount() {
		return projectedULDCount;
	}
	public void setProjectedULDCount(String[] projectedULDCount) {
		this.projectedULDCount = projectedULDCount;
	}
	public String[] getMinimumQuantity() {
		return minimumQuantity;
	}
	public void setMinimumQuantity(String[] minimumQuantity) {
		this.minimumQuantity = minimumQuantity;
	}
	public String[] getStockDeviation() {
		return stockDeviation;
	}
	public void setStockDeviation(String[] stockDeviation) {
		this.stockDeviation = stockDeviation;
	}
	public String getDisplayPage() {
		return displayPage;
	}
	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}
	public String getFltDisplayPage() {
		return fltDisplayPage;
	}
	public void setFltDisplayPage(String fltDisplayPage) {
		this.fltDisplayPage = fltDisplayPage;
	}
	public String getFltlastPageNum() {
		return fltlastPageNum;
	}
	public void setFltlastPageNum(String fltlastPageNum) {
		this.fltlastPageNum = fltlastPageNum;
	}
	public static String getSubproduct() {
		return SUBPRODUCT;
	}
	public static String getScreenid() {
		return SCREENID;
	}
	public String getLastPageNum() {
		return lastPageNum;
	}
	public void setLastPageNum(String lastPageNum) {
		this.lastPageNum = lastPageNum;
	}
	public int getSelectFlag() {
		return selectFlag;
	}
	public void setSelectFlag(int selectFlag) {
		this.selectFlag = selectFlag;
	}
	public String getBundle() {
		return BUNDLE;
	}
	public String getProduct() {
		return PRODUCT;
	}

	public String getScreenId() {
		return SCREENID;
	}
	public void setBundle(String bundle) {
		this.bundle = bundle;
	}

	@Override
	public String getSubProduct() {
		// subproductttt
		return SUBPRODUCT;
	}
	/**
	 * @return the screenInvokeActionStatus
	 */
	public String getScreenInvokeActionStatus() {
		return screenInvokeActionStatus;
	}
	/**
	 * @param screenInvokeActionStatus the screenInvokeActionStatus to set
	 */
	public void setScreenInvokeActionStatus(String screenInvokeActionStatus) {
		this.screenInvokeActionStatus = screenInvokeActionStatus;
	}
    
 }
