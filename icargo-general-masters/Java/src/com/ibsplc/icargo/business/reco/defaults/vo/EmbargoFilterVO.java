/*
 * StockHolderFilterVO.java Created on Jul 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary 
 * information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.reco.defaults.vo;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1358
 *
 */
public class EmbargoFilterVO extends AbstractVO {
	public static  final String SHARED_SCC="SCC";
    public static  final String SHARED_COMMODITY="COM";
	public static  final String SHARED_CARRIER="CAR";
	public static  final String SHARED_PAYTYP="PAYTYP";
	public static final String AWB_PREFIX = "AWBPRE";
	public static final String DATES_ORG = "DAT_ORG";
	public static final String DATES_DST = "DAT_DST";
	public static final String DATES_VIA = "DAT_VIA";
	public static final String TIME_ORG = "TIM_ORG";
	public static final String TIME_DST = "TIM_DST";
	public static final String TIME_VIA = "TIM_VIA";
	public static final String FLTOWR = "FLTOWR";
	public static final String FLTTYPE ="FLTTYP";
	public static final String GOODS = "GOODS";
	public static final String HEIGHT = "HGT";
	public static final String LENGTH = "LEN";
	public static final String MAIL_CATEGORY = "MALCAT";
	public static final String MAIL_CLASS = "MALCLS";
	public static final String MAIL_SUB_CLS = "MALSUBCLS";
	public static final String MAIL_SUB_CLS_GRP = "MALSUBCLSGRP";
	public static final String PAYMENT_TYPE = "PAYTYP";
	public static final String PRODUCT = "PRD";
	public static final String SCC_GROUP = "SCCGRP";
	public static final String SPLIT_INDICATOR = "SPLIT";
	public static final String UN_NUMBER = "UNNUM";
	public static final String WEIGHT = "WGT";
	public static final String WIDTH = "WID";
	public static final String AIRLINE_GRP = "ARLGRP";
	public static final String AIRLINE_GRP_ORG = "ARLGRPORG";
	public static final String AIRLINE_GRP_DST = "ARLGRPDST";
	public static final String AIRLINE_GRP_VIA = "ARLGRPVIA";
	public static final String AIRLINE_GRP_ALL = "ARLGRPALL";
	public static final String FLTTYPE_ORG ="FLTTYPORG";
	public static final String FLTTYPE_DST ="FLTTYPDST";
	public static final String FLTTYPE_VIA ="FLTTYPVIA";
	public static final String FLTTYPE_ALL ="FLTTYPALL";
	public static final String FLTTYPE_ANY ="FLTTYPANY";
	public static final String FLTOWR_ORG = "FLTOWRORG";
	public static final String FLTOWR_DST = "FLTOWRDST";
	public static final String FLTOWR_VIA = "FLTOWRVIA";
	public static final String FLTOWR_ALL = "FLTOWRALL";
	public static final String FLTOWR_ANY = "FLTOWRANY";
	public static  final String FLTNUM_ORG="FLTNUMORG";
	public static  final String FLTNUM_DST="FLTNUMDST";
	public static  final String FLTNUM_VIA="FLTNUMVIA";
	public static  final String FLTNUM_ALL="FLTNUMALL";
	public static  final String FLTNUM_ANY="FLTNUMANY";
	public static  final String CARRIER_ORG="CARORG";
	public static  final String CARRIER_DST="CARDST";
	public static  final String CARRIER_VIA="CARVIA";
	public static  final String CARRIER_ALL="CARALL";
	public static  final String CARRIER_ANY="CARANY";
	public static final String DOW = "DOW";
	public static  final String AGENT="AGT";
	public static final String AGENTGRP = "AGTGRP";
	//added by A-5799 for IASCB-23507 starts
	public static final String SERVICE_CARGO_CLASS = "SRVCRGCLS";
	public static final String AIRCRAFT_CLASS_ORIGIN="ACRCLSORG";
	public static final String AIRCRAFT_CLASS_VIA_POINT="ACRCLSVIA";
	public static final String AIRCRAFT_CLASS_DESTINATION ="ACRCLSDST";
	public static final String AIRCRAFT_CLASS_ALL= "ACRCLSALL";
	public static final String SHIPPER = "SHP";
	public static final String SHIPPER_GROUP = "SHPGRP";
	public static final String CONSIGNEE = "CNS";
	public static final String CONSIGNEE_GROUP = "CNSGRP";
	public static final String SHIPMENT_TYPE = "SHPTYP";
	public static final String CONSOL = "CNSL";
	 //added by A-5799 for IASCB-23507 ends
    //added by 202766
	public static final String UNKNOWN_SHIPPER = "UNKSHP";
	//ends
	public static final String SERVICE_TYPE = "SRVCTYP";
	
	public static final String UNID_PACKGING_GROUP = "PKGGRP";
	
	public static final String UNID_SUB_RISK = "SUBRSK";
	
    private String companyCode;
    
    private String embargoRefNumber;
       
    private LocalDate startDate;
    
    private LocalDate endDate;    
    
    private String origin;
    
    private String originType;
    
    private String destination;        
    
    private String destinationType;
    
    private String status;
    
    private String parameterCode;
    
    private String parameterValues;

	private String embargoLevel;
	
	private String viaPoint;
	
	private String viaPointType;
	
	private String daysOfOperation;
	
	private String daysOfOperationApplicableOn;

	private int totalRecordCount;
	
	private int pageNumber;
	
	private String numberOfDaysPriorEmbargoExpiry;
	
	/** Added by A-5867 for ICRD-68630**/
	private Collection<OneTimeVO> leftPanelParameters;
	
	private Collection<OneTimeVO> categories;
	
	private Collection<OneTimeVO> complianceTypes;
	
	private Collection<OneTimeVO> parameterCodes;
	
	private Collection<OneTimeVO> flightTypes;
	
	private String airportCodeOrigin;
	
	private String countryCodeOrigin;
	
	private String airportGroupOrigin;
	
	private String countryGroupOrigin;
	
	private String airportCodeDestination;
	
	private String countryCodeDestination;
	
	private String airportGroupDestination;
	
	private String countryGroupDestination;
	//Added by A-7924 as part of ICRD-318460 starts
    private String airportCodeSegmentOrigin;
	private String countryCodeSegmentOrigin;
	private String airportGroupSegmentOrigin;
	private String countryGroupSegmentOrigin;
	private String airportCodeSegmentDestination;
	private String countryCodeSegmentDestination;
	private String airportGroupSegmentDestination;
	private String countryGroupSegmentDestination;
	//Added by A-7924 as part of ICRD-318460 ends
	private String airportCodeViaPoint;
	
	private String countryCodeViaPoint;
	
	private String airportGroupViaPoint;
	
	private String countryGroupViaPoint;
	
	private String airportCodeOriginExc;
	
	private String countryCodeOriginExc;
	
	private String airportGroupOriginExc;
	
	private String countryGroupOriginExc;
	
	private String airportCodeDestinationExc;
	
	private String countryCodeDestinationExc;
	
	private String airportGroupDestinationExc;
	
	private String countryGroupDestinationExc;
	
	private String airportCodeViaPointExc;
	
	private String countryCodeViaPointExc;
	
	private String airportGroupViaPointExc;
	
	private String countryGroupViaPointExc;
	
	private String scc;

	private String sccGroup;
	
	private String sccExc;

	private String sccGroupExc;
	
	private boolean simpleSearch;
	
	private LocalDate embargoDate;
	
	private String geographicLevelType;
	
	private String geographicLevel;

	private String ruleType;
	private String category;
	private String complianceType;
	private String applicableTransactions;
	
	private String product;
	private String unNumber;
	private String packingInstruction;
	private String awbPrefix;
	private String airlineCode;
	private String flightOwner;
	private String geographicalDate;
	private String natureOfGoods;
	private String commodity;
	private String flightNumber;
	private String paymentType;
	private String flightType;
	private String splitIndicator;
	private String time;
	private String weight;
	private String length;
	private String width;
	private String height;
	private String airlineGroup;
    private Collection<EmbargoGeographicLevelVO> embargoGeographicLevelVOs;
	private Collection<EmbargoParameterVO> embargoParameterVOs;
	private String productExc;
	private boolean isHighestInactiveVersionFetch;
	// Added by A-5290 for ICRD-203884
	private String suspendFlag;	  
	// Added by A-7924 as part of ICRD-313966 starts    
    private String segmentOrigin;
    private String segmentOriginType;
    private String segmentDestination;        
    private String segmentDestinationType;
    // Added by A-7924 as part of ICRD-313966 starts 
	//Added by A-8130 for ICRD-232462
	private String unWeight;
	private String dvForCustoms;
	private String dvForCarriage;
	private String unIds;//Added for ICRD-254555
	
	//added by A-5799 for IASCB-23507 Starts
  	private String serviceCargoClass;
  	private String aircraftClassification;
  	private String shipperCode;
  	private String shipperGroup;
  	private String consigneeCode;
  	private String consigneeGroup;
  	private String shipmentType;
  	private String serviceType;
	private String consol;
  	//added by A-5799 for IASCB-23507 ends
	private String unPg;
	private String subRisk;
	
	//added by 202766
	private String unknownShipper;
	//parameter code that will be specifiaclly searched in parameterSelectiveEmbargoSearch method
	//set this parameter and corresponding chnages to be done in EmbargoRulesSqlDao.parameterSelectiveEmbargoSearch()
	//used to return embargoes whose all parameters other than the searchParametrCode is null irrespective of the OD specified
	private String searchParameterCode;
    /**
	 * This map stores all the parameters IN RECO and corresponding column Inc. 
	 * Eg: SCC-SCCCODINC
	 * Whenver a new parameter is added in REC_MVW same needs to added in this map
	 */
	private static HashMap<String, String> parameterColumnMap;
	static {
		parameterColumnMap = new HashMap<String, String>();
		parameterColumnMap.put(CARRIER_ORG, "CARRORGINC");	
		parameterColumnMap.put(CARRIER_DST, "CARRDSTINC");	
		parameterColumnMap.put(CARRIER_VIA, "CARRVIAINC");	
		parameterColumnMap.put(CARRIER_ALL, "CARRALLINC");
		parameterColumnMap.put(CARRIER_ANY, "CARRANYINC");
		parameterColumnMap.put(SHARED_COMMODITY, "COMINC");
		parameterColumnMap.put(SHARED_SCC, "SCCCODINC");
		parameterColumnMap.put(SCC_GROUP, "SCCGRPINC");
		parameterColumnMap.put(PAYMENT_TYPE, "PAYTYPINC");
		parameterColumnMap.put(FLTNUM_ORG, "FLTNUMORGINC");
		parameterColumnMap.put(FLTNUM_DST, "FLTNUMDSTINC");
		parameterColumnMap.put(FLTNUM_VIA, "FLTNUMVIAINC");
		parameterColumnMap.put(FLTNUM_ALL, "FLTNUMVIAINC");
		parameterColumnMap.put(FLTNUM_ANY, "FLTNUMALLINC");		
		parameterColumnMap.put(GOODS, "NATINC");
		parameterColumnMap.put(AWB_PREFIX, "AWBPFXINC");		
		parameterColumnMap.put(FLTTYPE_ORG, "FLTTYPORGINC");
		parameterColumnMap.put(FLTTYPE_DST, "FLTTYPDSTINC");
		parameterColumnMap.put(FLTTYPE_VIA, "FLTTYPVIAINC");
		parameterColumnMap.put(FLTTYPE_ALL, "FLTTYPALLINC");
		parameterColumnMap.put(FLTTYPE, "FLTTYPANYINC");		
		parameterColumnMap.put(FLTOWR_ORG, "FLTOWRORGINC");
		parameterColumnMap.put(FLTOWR_DST, "FLTOWRDSTINC");
		parameterColumnMap.put(FLTOWR_VIA, "FLTOWRVIAINC");
		parameterColumnMap.put(FLTOWR_ALL, "FLTOWRALLINC");
		parameterColumnMap.put(FLTOWR, "FLTOWRANYEXC");		
		parameterColumnMap.put(AIRLINE_GRP_ORG, "ARLGRPORGINC");
		parameterColumnMap.put(AIRLINE_GRP_DST, "ARLGRPDSTINC");
		parameterColumnMap.put(AIRLINE_GRP_VIA, "ARLGRPVIAINC");
		parameterColumnMap.put(AIRLINE_GRP_ALL, "ARLGRPALLINC");
		parameterColumnMap.put(AIRLINE_GRP, "ARLGRPANYINC");		
		parameterColumnMap.put("HEIGHT_STR", "HGTSTR");
		parameterColumnMap.put("HEIGHT_END", "HGTEND");
		parameterColumnMap.put("LENGTH_STR", "LENSTR");
		parameterColumnMap.put("LENGTH_END", "LENEND");
		parameterColumnMap.put("WIDTH_STR", "WIDSTR");
		parameterColumnMap.put("WIDTH_END", "WIDEND");
		parameterColumnMap.put("WEIGHT_STR", "WGTSTR");
		parameterColumnMap.put("WEIGHT_END", "WGTEND");
		parameterColumnMap.put("UNDNUM", "UNDNUMINC");		
		parameterColumnMap.put("TIME_ORG_STR", "ORGSTRTIM");
		parameterColumnMap.put("TIME_DST_STR", "DSTSTRTIM");
		parameterColumnMap.put("TIME_DST_END", "DSTENDTIM");
		parameterColumnMap.put("TIME_VIA_STR", "VIASTRTIM");
		parameterColumnMap.put("TIME_VIA_END", "VIAENDTIM");
		parameterColumnMap.put("TIME_ALL_STR", "ALLSTRTIM");
		parameterColumnMap.put("TIME_ALL_END", "ALLENDTIM");
		parameterColumnMap.put("TIME_ANY_STR", "ANYSTRTIM");
		parameterColumnMap.put("TIME_ANY_END", "ANYENDTIM");			
		parameterColumnMap.put(DATES_ORG, "ORGDATINC");
		parameterColumnMap.put(DATES_DST, "DSTDATINC");
		parameterColumnMap.put(DATES_VIA, "VIADATINC");
		parameterColumnMap.put("DATES_ALL", "ALLDATINC");
		parameterColumnMap.put("DATES_ANY", "ANYDATINC");		
		parameterColumnMap.put("SLTIND", "SLTIND");
		parameterColumnMap.put(SERVICE_CARGO_CLASS,"SRVCRGCLSINC");
		parameterColumnMap.put(AIRCRAFT_CLASS_ORIGIN,"ACRCLSORGINC");
		parameterColumnMap.put(AIRCRAFT_CLASS_VIA_POINT,"ACRCLSVIAINC");
		parameterColumnMap.put(AIRCRAFT_CLASS_DESTINATION,"ACRCLSDSTINC");
		parameterColumnMap.put(AIRCRAFT_CLASS_ALL,"ACRCLSALLINC");
		parameterColumnMap.put(SHIPPER,"SHPINC");
		parameterColumnMap.put(SHIPPER_GROUP,"SHPGRPINC");
		parameterColumnMap.put(CONSIGNEE,"CNSINC");
		parameterColumnMap.put(CONSIGNEE_GROUP,"CNSGRPINC");
		parameterColumnMap.put(SHIPMENT_TYPE,"SHPTYPINC");
		parameterColumnMap.put(CONSOL,"CNSLINC");
		parameterColumnMap.put(SERVICE_TYPE,"SRVCTYPINC");
		parameterColumnMap.put(UNID_PACKGING_GROUP,"PKGGRPINC");
		parameterColumnMap.put(UNID_SUB_RISK, "SUBRSKINC");
		//added by 202766
		parameterColumnMap.put(UNKNOWN_SHIPPER,UNKNOWN_SHIPPER);
		//ends
	}		
    public Collection<String> getParameterColumnMapKeySet(){
    	return parameterColumnMap.keySet();
    }
	public String getParameterColumn(String key) {
		return parameterColumnMap.get(key);
	}
	public Collection<EmbargoParameterVO> getEmbargoParameterVOs() {
		return embargoParameterVOs;
	}
	private LocalDate lastUpdatedTime;

	public void setEmbargoParameterVOs(
			Collection<EmbargoParameterVO> embargoParameterVOs) {
		this.embargoParameterVOs = embargoParameterVOs;
	}

	public Collection<EmbargoGeographicLevelVO> getEmbargoGeographicLevelVOs() {
		return embargoGeographicLevelVOs;
	}

	public void setEmbargoGeographicLevelVOs(
			Collection<EmbargoGeographicLevelVO> embargoGeographicLevelVOs) {
		this.embargoGeographicLevelVOs = embargoGeographicLevelVOs;
	}
	
	private int embargoVersion;

	public int getEmbargoVersion() {
		return embargoVersion;
	}

	public void setEmbargoVersion(int embargoVersion) {
		this.embargoVersion = embargoVersion;
	}

	public String getRuleType() {
		return ruleType;
	}

	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getComplianceType() {
		return complianceType;
	}

	public void setComplianceType(String complianceType) {
		this.complianceType = complianceType;
	}

	public String getApplicableTransactions() {
		return applicableTransactions;
	}

	public void setApplicableTransactions(String applicableTransactions) {
		this.applicableTransactions = applicableTransactions;
	}

	public String getNumberOfDaysPriorEmbargoExpiry() {
		return numberOfDaysPriorEmbargoExpiry;
	}

	public void setNumberOfDaysPriorEmbargoExpiry(
			String numberOfDaysPriorEmbargoExpiry) {
		this.numberOfDaysPriorEmbargoExpiry = numberOfDaysPriorEmbargoExpiry;
	}
	
	/**
	 * 	Getter for totalRecordCount 
	 *	Added by : A-5175 on 30-Oct-2012
	 * 	Used for : icrd-21634
	 */
	public int getTotalRecordCount() {
		return totalRecordCount;
	}

	/**
	 *  @param totalRecordCount the totalRecordCount to set
	 * 	Setter for totalRecordCount 
	 *	Added by : A-5175 on 30-Oct-2012
	 * 	Used for : icrd-21634
	 */
	public void setTotalRecordCount(int totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}

	/**
	 * 	Getter for pageNumber 
	 *	Added by : A-5175 on 30-Oct-2012
	 * 	Used for : icrd-21634
	 */
	public int getPageNumber() {
		return pageNumber;
	}

	/**
	 *  @param pageNumber the pageNumber to set
	 * 	Setter for pageNumber 
	 *	Added by : A-5175 on 30-Oct-2012
	 * 	Used for : icrd-21634
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
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
	 * @return Returns the destination.
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * @param destination The destination to set.
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}

	/**
	 * @return Returns the destinationType.
	 */
	public String getDestinationType() {
		return destinationType;
	}

	/**
	 * @param destinationType The destinationType to set.
	 */
	public void setDestinationType(String destinationType) {
		this.destinationType = destinationType;
	}

	/**
	 * @return Returns the embargoLevel.
	 */
	public String getEmbargoLevel() {
		return embargoLevel;
	}

	/**
	 * @param embargoLevel The embargoLevel to set.
	 */
	public void setEmbargoLevel(String embargoLevel) {
		this.embargoLevel = embargoLevel;
	}

	/**
	 * @return Returns the embargoRefNumber.
	 */
	public String getEmbargoRefNumber() {
		return embargoRefNumber;
	}

	/**
	 * @param embargoRefNumber The embargoRefNumber to set.
	 */
	public void setEmbargoRefNumber(String embargoRefNumber) {
		this.embargoRefNumber = embargoRefNumber;
	}

	/**
	 * @return Returns the endDate.
	 */
	public LocalDate getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate The endDate to set.
	 */
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return Returns the origin.
	 */
	public String getOrigin() {
		return origin;
	}

	/**
	 * @param origin The origin to set.
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	/**
	 * @return Returns the originType.
	 */
	public String getOriginType() {
		return originType;
	}

	/**
	 * @param originType The originType to set.
	 */
	public void setOriginType(String originType) {
		this.originType = originType;
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
	 * @return Returns the startDate.
	 */
	public LocalDate getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate The startDate to set.
	 */
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return Returns the status.
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status The status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return Returns the viaPoint.
	 */
	public String getViaPoint() {
		return viaPoint;
	}

	/**
	 * @param viaPoint The viaPoint to set.
	 */
	public void setViaPoint(String viaPoint) {
		this.viaPoint = viaPoint;
	}

	/**
	 * @return Returns the viaPointType.
	 */
	public String getViaPointType() {
		return viaPointType;
	}

	/**
	 * @param viaPointType The viaPointType to set.
	 */
	public void setViaPointType(String viaPointType) {
		this.viaPointType = viaPointType;
	}

	/**
	 * @return Returns the daysOfOperation.
	 */
	public String getDaysOfOperation() {
		return daysOfOperation;
	}

	/**
	 * @param daysOfOperation The daysOfOperation to set.
	 */
	public void setDaysOfOperation(String daysOfOperation) {
		this.daysOfOperation = daysOfOperation;
	}

	/**
	 * @return Returns the daysOfOperationApplicableOn.
	 */
	public String getDaysOfOperationApplicableOn() {
		return daysOfOperationApplicableOn;
	}

	/**
	 * @param daysOfOperationApplicableOn The daysOfOperationApplicableOn to set.
	 */
	public void setDaysOfOperationApplicableOn(String daysOfOperationApplicableOn) {
		this.daysOfOperationApplicableOn = daysOfOperationApplicableOn;
	}


	public void setLeftPanelParameters(Collection<OneTimeVO> leftPanelParameters) {
		this.leftPanelParameters = leftPanelParameters;
	}

	public Collection<OneTimeVO> getLeftPanelParameters() {
		return leftPanelParameters;
	}

	public String getAirportCodeOrigin() {
		return airportCodeOrigin;
	}

	public void setAirportCodeOrigin(String airportCodeOrigin) {
		this.airportCodeOrigin = airportCodeOrigin;
	}

	public String getCountryCodeOrigin() {
		return countryCodeOrigin;
	}

	public void setCountryCodeOrigin(String countryCodeOrigin) {
		this.countryCodeOrigin = countryCodeOrigin;
	}

	public String getAirportGroupOrigin() {
		return airportGroupOrigin;
	}

	public void setAirportGroupOrigin(String airportGroupOrigin) {
		this.airportGroupOrigin = airportGroupOrigin;
	}

	public String getCountryGroupOrigin() {
		return countryGroupOrigin;
	}

	public void setCountryGroupOrigin(String countryGroupOrigin) {
		this.countryGroupOrigin = countryGroupOrigin;
	}

	public String getAirportCodeDestination() {
		return airportCodeDestination;
	}

	public void setAirportCodeDestination(String airportCodeDestination) {
		this.airportCodeDestination = airportCodeDestination;
	}

	public String getCountryCodeDestination() {
		return countryCodeDestination;
	}

	public void setCountryCodeDestination(String countryCodeDestination) {
		this.countryCodeDestination = countryCodeDestination;
	}

	public String getAirportGroupDestination() {
		return airportGroupDestination;
	}

	public void setAirportGroupDestination(String airportGroupDestination) {
		this.airportGroupDestination = airportGroupDestination;
	}

	public String getCountryGroupDestination() {
		return countryGroupDestination;
	}

	public void setCountryGroupDestination(String countryGroupDestination) {
		this.countryGroupDestination = countryGroupDestination;
	}

	public String getAirportCodeViaPoint() {
		return airportCodeViaPoint;
	}

	public void setAirportCodeViaPoint(String airportCodeViaPoint) {
		this.airportCodeViaPoint = airportCodeViaPoint;
	}

	public String getCountryCodeViaPoint() {
		return countryCodeViaPoint;
	}

	public void setCountryCodeViaPoint(String countryCodeViaPoint) {
		this.countryCodeViaPoint = countryCodeViaPoint;
	}

	public String getAirportGroupViaPoint() {
		return airportGroupViaPoint;
	}

	public void setAirportGroupViaPoint(String airportGroupViaPoint) {
		this.airportGroupViaPoint = airportGroupViaPoint;
	}

	public String getCountryGroupViaPoint() {
		return countryGroupViaPoint;
	}

	public void setCountryGroupViaPoint(String countryGroupViaPoint) {
		this.countryGroupViaPoint = countryGroupViaPoint;
	}

	public void setEmbargoDate(LocalDate embargoDate) {
		this.embargoDate = embargoDate;
	}

	public LocalDate getEmbargoDate() {
		return embargoDate;
	}

	public void setSimpleSearch(boolean simpleSearch) {
		this.simpleSearch = simpleSearch;
	}

	public boolean isSimpleSearch() {
		return simpleSearch;
	}

	public String getGeographicLevelType() {
		return geographicLevelType;
	}

	public void setGeographicLevelType(String geographicLevelType) {
		this.geographicLevelType = geographicLevelType;
	}

	public String getGeographicLevel() {
		return geographicLevel;
	}

	public void setGeographicLevel(String geographicLevel) {
		this.geographicLevel = geographicLevel;
	}

	public String getScc() {
		return scc;
	}

	public void setScc(String scc) {
		this.scc = scc;
	}

	public String getSccGroup() {
		return sccGroup;
	}

	public void setSccGroup(String sccGroup) {
		this.sccGroup = sccGroup;
	}

	public Collection<OneTimeVO> getCategories() {
		return categories;
	}

	public void setCategories(Collection<OneTimeVO> categories) {
		this.categories = categories;
	}

	public Collection<OneTimeVO> getComplianceTypes() {
		return complianceTypes;
	}

	public void setComplianceTypes(Collection<OneTimeVO> complianceTypes) {
		this.complianceTypes = complianceTypes;
	}

	public void setParameterCodes(Collection<OneTimeVO> parameterCodes) {
		this.parameterCodes = parameterCodes;
	}

	public Collection<OneTimeVO> getParameterCodes() {
		return parameterCodes;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getUnNumber() {
		return unNumber;
	}

	public void setUnNumber(String unNumber) {
		this.unNumber = unNumber;
	}

	public String getAwbPrefix() {
		return awbPrefix;
	}

	public void setAwbPrefix(String awbPrefix) {
		this.awbPrefix = awbPrefix;
	}

	public String getAirlineCode() {
		return airlineCode;
	}

	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}

	public String getFlightOwner() {
		return flightOwner;
	}

	public void setFlightOwner(String flightOwner) {
		this.flightOwner = flightOwner;
	}

	public String getGeographicalDate() {
		return geographicalDate;
	}

	public void setGeographicalDate(String geographicalDate) {
		this.geographicalDate = geographicalDate;
	}

	public String getNatureOfGoods() {
		return natureOfGoods;
	}

	public void setNatureOfGoods(String natureOfGoods) {
		this.natureOfGoods = natureOfGoods;
	}

	public String getCommodity() {
		return commodity;
	}

	public void setCommodity(String commodity) {
		this.commodity = commodity;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getFlightType() {
		return flightType;
	}

	public void setFlightType(String flightType) {
		this.flightType = flightType;
	}

	public String getSplitIndicator() {
		return splitIndicator;
	}

	public void setSplitIndicator(String splitIndicator) {
		this.splitIndicator = splitIndicator;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public void setFlightTypes(Collection<OneTimeVO> flightTypes) {
		this.flightTypes = flightTypes;
	}

	public Collection<OneTimeVO> getFlightTypes() {
		return flightTypes;
	}

	public String getAirportCodeOriginExc() {
		return airportCodeOriginExc;
	}

	public void setAirportCodeOriginExc(String airportCodeOriginExc) {
		this.airportCodeOriginExc = airportCodeOriginExc;
	}

	public String getCountryCodeOriginExc() {
		return countryCodeOriginExc;
	}

	public void setCountryCodeOriginExc(String countryCodeOriginExc) {
		this.countryCodeOriginExc = countryCodeOriginExc;
	}

	public String getAirportGroupOriginExc() {
		return airportGroupOriginExc;
	}

	public void setAirportGroupOriginExc(String airportGroupOriginExc) {
		this.airportGroupOriginExc = airportGroupOriginExc;
	}

	public String getCountryGroupOriginExc() {
		return countryGroupOriginExc;
	}

	public void setCountryGroupOriginExc(String countryGroupOriginExc) {
		this.countryGroupOriginExc = countryGroupOriginExc;
	}

	public String getAirportCodeDestinationExc() {
		return airportCodeDestinationExc;
	}

	public void setAirportCodeDestinationExc(String airportCodeDestinationExc) {
		this.airportCodeDestinationExc = airportCodeDestinationExc;
	}

	public String getCountryCodeDestinationExc() {
		return countryCodeDestinationExc;
	}

	public void setCountryCodeDestinationExc(String countryCodeDestinationExc) {
		this.countryCodeDestinationExc = countryCodeDestinationExc;
	}

	public String getAirportGroupDestinationExc() {
		return airportGroupDestinationExc;
	}

	public void setAirportGroupDestinationExc(String airportGroupDestinationExc) {
		this.airportGroupDestinationExc = airportGroupDestinationExc;
	}

	public String getCountryGroupDestinationExc() {
		return countryGroupDestinationExc;
	}

	public void setCountryGroupDestinationExc(String countryGroupDestinationExc) {
		this.countryGroupDestinationExc = countryGroupDestinationExc;
	}

	public String getAirportCodeViaPointExc() {
		return airportCodeViaPointExc;
	}

	public void setAirportCodeViaPointExc(String airportCodeViaPointExc) {
		this.airportCodeViaPointExc = airportCodeViaPointExc;
	}

	public String getCountryCodeViaPointExc() {
		return countryCodeViaPointExc;
	}

	public void setCountryCodeViaPointExc(String countryCodeViaPointExc) {
		this.countryCodeViaPointExc = countryCodeViaPointExc;
	}

	public String getAirportGroupViaPointExc() {
		return airportGroupViaPointExc;
	}

	public void setAirportGroupViaPointExc(String airportGroupViaPointExc) {
		this.airportGroupViaPointExc = airportGroupViaPointExc;
	}

	public String getCountryGroupViaPointExc() {
		return countryGroupViaPointExc;
	}

	public void setCountryGroupViaPointExc(String countryGroupViaPointExc) {
		this.countryGroupViaPointExc = countryGroupViaPointExc;
	}

	public String getSccExc() {
		return sccExc;
	}

	public void setSccExc(String sccExc) {
		this.sccExc = sccExc;
	}

	public String getSccGroupExc() {
		return sccGroupExc;
	}

	public void setSccGroupExc(String sccGroupExc) {
		this.sccGroupExc = sccGroupExc;
	}

	public void setProductExc(String productExc) {
		this.productExc = productExc;
	}

	public String getProductExc() {
		return productExc;
	}
	public boolean isHighestInactiveVersionFetch() {
		return isHighestInactiveVersionFetch;
	}
	public void setHighestInactiveVersionFetch(boolean isHighestInactiveVersionFetch) {
		this.isHighestInactiveVersionFetch = isHighestInactiveVersionFetch;
	}
	public String getAirlineGroup() {
		return airlineGroup;
	}
	public void setAirlineGroup(String airlineGroup) {
		this.airlineGroup = airlineGroup;
	}	
	/**
	 * @return the lastUpdatedTime
	 */
	public LocalDate getLastUpdatedTime() {
		return lastUpdatedTime;
	}
	/**
	 * @param lastUpdatedTime the lastUpdatedTime to set
	 */
	public void setLastUpdatedTime(LocalDate lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}
	/**
	 * @return the searchParameterCode
	 */
	public String getSearchParameterCode() {
		return searchParameterCode;
	}
	/**
	 * @param searchParameterCode the searchParameterCode to set
	 */
	public void setSearchParameterCode(String searchParameterCode) {
		this.searchParameterCode = searchParameterCode;
	}
	public String getSuspendFlag() {
		return suspendFlag;
	}
	public void setSuspendFlag(String suspendFlag) {
		this.suspendFlag = suspendFlag;
	}   
//Added by A-7924 as part of ICRD-313966 starts	 
public String getSegmentOrigin() {
		return segmentOrigin;
	}
	public void setSegmentOrigin(String segmentOrigin) {
		this.segmentOrigin = segmentOrigin;
	}
	public String getSegmentOriginType() {
		return segmentOriginType;
	}
	public void setSegmentOriginType(String segmentOriginType) {
		this.segmentOriginType = segmentOriginType;
	}
	public String getSegmentDestination() {
		return segmentDestination;
	}
	public void setSegmentDestination(String segmentDestination) {
		this.segmentDestination = segmentDestination;
	}
	public String getSegmentDestinationType() {
		return segmentDestinationType;
	}
	public void setSegmentDestinationType(String segmentDestinationType) {
		this.segmentDestinationType = segmentDestinationType;
	}
	//Added by A-7924 as part of ICRD-313966 ends	 
//Added by A-7924 as part of ICRD-318460 starts
	public String getAirportCodeSegmentOrigin() {
		return airportCodeSegmentOrigin;
	}
	public void setAirportCodeSegmentOrigin(String airportCodeSegmentOrigin) {
		this.airportCodeSegmentOrigin = airportCodeSegmentOrigin;
	}
	public String getCountryCodeSegmentOrigin() {
		return countryCodeSegmentOrigin;
	}
	public void setCountryCodeSegmentOrigin(String countryCodeSegmentOrigin) {
		this.countryCodeSegmentOrigin = countryCodeSegmentOrigin;
	}
	public String getAirportGroupSegmentOrigin() {
		return airportGroupSegmentOrigin;
	}
	public void setAirportGroupSegmentOrigin(String airportGroupSegmentOrigin) {
		this.airportGroupSegmentOrigin = airportGroupSegmentOrigin;
	}
	public String getCountryGroupSegmentOrigin() {
		return countryGroupSegmentOrigin;
	}
	public void setCountryGroupSegmentOrigin(String countryGroupSegmentOrigin) {
		this.countryGroupSegmentOrigin = countryGroupSegmentOrigin;
	}
	public String getAirportCodeSegmentDestination() {
		return airportCodeSegmentDestination;
	}
	public void setAirportCodeSegmentDestination(String airportCodeSegmentDestination) {
		this.airportCodeSegmentDestination = airportCodeSegmentDestination;
	}
	public String getCountryCodeSegmentDestination() {
		return countryCodeSegmentDestination;
	}
	public void setCountryCodeSegmentDestination(String countryCodeSegmentDestination) {
		this.countryCodeSegmentDestination = countryCodeSegmentDestination;
	}
	public String getAirportGroupSegmentDestination() {
		return airportGroupSegmentDestination;
	}
	public void setAirportGroupSegmentDestination(String airportGroupSegmentDestination) {
		this.airportGroupSegmentDestination = airportGroupSegmentDestination;
	}
	public String getCountryGroupSegmentDestination() {
		return countryGroupSegmentDestination;
	}
	public void setCountryGroupSegmentDestination(String countryGroupSegmentDestination) {
		this.countryGroupSegmentDestination = countryGroupSegmentDestination;
	}
	//Added by A-7924 as part of ICRD-318460 ends
	/**
	 * 
	 * 	Method		:	EmbargoFilterVO.getPackingInstruction
	 *	Added by 	:	A-7534 on 28-Mar-2018
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 *	Return type	: 	String
	 */
	public String getPackingInstruction() {
		return packingInstruction;
	}
	/**
	 * 
	 * 	Method		:	EmbargoFilterVO.setPackingInstruction
	 *	Added by 	:	A-7534 on 28-Mar-2018
	 * 	Used for 	:
	 *	Parameters	:	@param packingInstruction 
	 *	Return type	: 	void
	 */
	public void setPackingInstruction(String packingInstruction) {
		this.packingInstruction = packingInstruction;
	}
	
	public String getUnWeight() {
		return unWeight;
	}
	public void setUnWeight(String unWeight) {
		this.unWeight = unWeight;
	}
	public String getDvForCustoms() {
		return dvForCustoms;
	}
	public void setDvForCustoms(String dvForCustoms) {
		this.dvForCustoms = dvForCustoms;
	}
	public String getDvForCarriage() {
		return dvForCarriage;
	}
	public void setDvForCarriage(String dvForCarriage) {
		this.dvForCarriage = dvForCarriage;
	}
	/**
	 * Field 	: agentCode	of type : String
	 * Used for :
	 */
	private String agentCode;
	/**
	 * Field 	: agentGroup	of type : String
	 * Used for :
	 */
	private String agentGroup;
	/**
	 * 	Method		:	EmbargoFilterVO.getAgentCode
	 *	Added by 	:	A-8041 on 27-Sep-2018
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 *	Return type	: 	String
	 */
	public String getAgentCode() {
		return agentCode;
	}
	/**
	 * 	Method		:	EmbargoFilterVO.setAgentCode
	 *	Added by 	:	A-8041 on 27-Sep-2018
	 * 	Used for 	:
	 *	Parameters	:	@param agentCode 
	 *	Return type	: 	void
	 */
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}
	/**
	 * 	Method		:	EmbargoFilterVO.getAgentGroup
	 *	Added by 	:	A-8041 on 27-Sep-2018
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 *	Return type	: 	String
	 */
	public String getAgentGroup() {
		return agentGroup;
	}
	/**
	 * 	Method		:	EmbargoFilterVO.setAgentGroup
	 *	Added by 	:	A-8041 on 27-Sep-2018
	 * 	Used for 	:
	 *	Parameters	:	@param agentGroup 
	 *	Return type	: 	void
	 */
	public void setAgentGroup(String agentGroup) {
		this.agentGroup = agentGroup;
	}
	public String getUnIds() {
		return unIds;
	}
	public void setUnIds(String unIds) {
		this.unIds = unIds;
	}
	public String getServiceCargoClass() {
		return serviceCargoClass;
	}
	public void setServiceCargoClass(String serviceCargoClass) {
		this.serviceCargoClass = serviceCargoClass;
	}
	public String getAircraftClassification() {
		return aircraftClassification;
	}
	public void setAircraftClassification(String aircraftClassification) {
		this.aircraftClassification = aircraftClassification;
	}
	public String getShipperCode() {
		return shipperCode;
	}
	public void setShipperCode(String shipperCode) {
		this.shipperCode = shipperCode;
	}
	public String getShipperGroup() {
		return shipperGroup;
	}
	public void setShipperGroup(String shipperGroup) {
		this.shipperGroup = shipperGroup;
	}
	public String getConsigneeCode() {
		return consigneeCode;
	}
	public void setConsigneeCode(String consigneeCode) {
		this.consigneeCode = consigneeCode;
	}
	public String getConsigneeGroup() {
		return consigneeGroup;
	}
	public void setConsigneeGroup(String consigneeGroup) {
		this.consigneeGroup = consigneeGroup;
	}
	public String getShipmentType() {
		return shipmentType;
	}
	public void setShipmentType(String shipmentType) {
		this.shipmentType = shipmentType;
	}
	public String getConsol() {
		return consol;
	}
	public void setConsol(String consol) {
		this.consol = consol;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getUnPg() {
		return unPg;
	}
	//added by 202766
	public String getUnknownShipper() {
		return unknownShipper;
	}
	public void setUnPg(String unPg) {
		this.unPg = unPg;
	}
	public String getSubRisk() {
		return subRisk;
	}
	public void setSubRisk(String subRisk) {
		this.subRisk = subRisk;
	}
	public void setUnknownShipper(String unknownShipper) {
		this.unknownShipper = unknownShipper;
	}
	//ends
	}
