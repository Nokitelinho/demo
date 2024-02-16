/*
 * ShipmentDetailsVO.java Created on Sep 9, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary
 * information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.reco.defaults.vo;

import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.capacity.booking.vo.BookingFlightDetailVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1358
 * This class is used to supply information about the shipment to the
 * embargo module. Embargos are identified based on this information
 */
public class ShipmentDetailsVO extends AbstractVO {

	public static  final String SHARED_SCC="SCC";
    public static  final String SHARED_COMMODITY="COM";
	public static  final String SHARED_CARRIER="CAR";
	public static  final String SHARED_PAYTYP="PAYTYP";
	public static  final String SHARED_AIRPORT="ARP";
	public static  final String SHARED_FLTNUM="FLTNUM";
	public static final String EMBARGO_LEVEL_INFO = "I";
	public static final String EMBARGO_LEVEL_WARNING = "W";
	public static final String EMBARGO_LEVEL_ERROR = "E";
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
	public static final String UNIDs = "UNCLS";
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
	public static final String PKGINS = "PKGINS";
	public static final String WEIGHT = "WGT";
	public static final String PER_PIECE_WEIGHT = "PERPIECEWGT";
	public static final String NUMSTP = "NUMSTP";
	public static final String WIDTH = "WID";
	public static final String AIRLINE_GRP = "ARLGRP";
	public static final String SEGORG = "SEGORG";
	public static final String SEGDST = "SEGDST";
	public static final String APP_TRANSACTION_BKG = "BKG";
	public static final String APP_TRANSACTION_AWB_SAVE = "AWBSAVE";
	public static final String APP_TRANSACTION_AWB_EXE = "AWBEXE";
	public static final String APP_TRANSACTION_HAWB = "HAWB";
	public static final String APP_TRANSACTION_ACCEPTANCE = "GAC";
	public static final String APP_TRANSACTION_BUILDUP = "BUILDUP";
	public static final String APP_TRANSACTION_MAIL_ACCEPTANCE = "MAL";
	public static final String APP_TRANSACTION_NOTOC = "NOTOC";
	public static final String APP_TRANSACTION_CREATEVISITDECLARATION ="CRTVSTDCL";

	public static final String AIRLINE_GRP_ORG = "ARLGRPORG";
	public static final String AIRLINE_GRP_DST = "ARLGRPDST";
	public static final String AIRLINE_GRP_VIA = "ARLGRPVIA";
	public static final String AIRLINE_GRP_ALL = "ARLGRPALL";
	public static final String FLTTYPE_ORG ="FLTTYPORG";
	public static final String FLTTYPE_DST ="FLTTYPDST";
	public static final String FLTTYPE_VIA ="FLTTYPVIA";
	public static final String FLTTYPE_ALL ="FLTTYPALL";
	public static final String FLTOWR_ORG = "FLTOWRORG";
	public static final String FLTOWR_DST = "FLTOWRDST";
	public static final String FLTOWR_VIA = "FLTOWRVIA";
	public static final String FLTOWR_ALL = "FLTOWRALL";
	public static  final String FLTNUM_ORG="FLTNUMORG";
	public static  final String FLTNUM_DST="FLTNUMDST";
	public static  final String FLTNUM_VIA="FLTNUMVIA";
	public static  final String FLTNUM_ALL="FLTNUMALL";
	public static  final String CARRIER_ORG="CARORG";
	public static  final String CARRIER_DST="CARDST";
	public static  final String CARRIER_VIA="CARVIA";
	public static  final String CARRIER_ALL="CARALL";
	public static  final String AIRPORT_GRP="ARPGRP";
	public static  final String COUNTRY_GRP="CNTGRP";
	//constant used for populating type map only
	public static  final String COUNTRY="CNT";
	public static final String PARMETER_CONDITION_INCLUDE = "I";
	public static final String PARMETER_CONDITION_EXCLUDE = "E";
	public static final String SHARED_PRODUCT = "PRD";
	public static final String UNID_WEIGHT = "UNWGT";
	public static final String DV_CUSTOMS = "DVCST";
	public static final String DV_CARRIAGE = "DVCRG";
	public static final String AGENT = "AGT";
	public static final String AGENTGRP = "AGTGRP";
	public static final String VOLUME="VOL";
	public static final String AIRCRAFT_TYPE_ORIGIN="ACRTYPORG";
	public static final String AIRCRAFT_TYPE_VIA_POINT="ACRTYPVIA";
	public static final String AIRCRAFT_TYPE_DESTINATION ="ACRTYPDST";
	public static final String AIRCRAFT_TYPE_GROUP_ORIGIN="ACRTYPGRPORG";
	public static final String AIRCRAFT_TYPE_GROUP_VIA_POINT="ACRTYPGRPVIA";
	public static final String AIRCRAFT_TYPE_GROUP_DESTINATION ="ACRTYPGRPDST";
	public static final String ULD= "ULD";
	public static final String AIRCRAFT_TYPE_ALL= "ACRTYPALL";
	public static final String AIRCRAFT_TYPE_GROUP_ALL= "ACRTYPGRPALL";
	private boolean isFromPrecheck;
	public static final String ULD_TYPE="ULDTYP";
	public static final String ULD_POSITION="ULDPOS";

	//added by A-5799 for IASCB-23507 starts
	public static final String SERVICE_CARGO_CLASS = "SRVCRGCLS";
	public static final String AIRCRAFT_CLASSIFICATION="ACRCLS";
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
	public static final String SERVICE_TYPE = "SRVCTYP";
	public static final String SERVICE_TYPE_FOR_TECHNICAL_STOP = "SRVCTYPFRTECSTP";
	 //added by A-5799 for IASCB-23507 ends
	public static final String UNID_PACKGING_GROUP = "PKGGRP";
	public static final String UNID_SUB_RISK = "SUBRSK";
	//added by 202766
	public static final String UNKNOWN_SHIPPER = "UNKSHP";

    private String companyCode;

    /*
     * Origin of the shipment
     */

    private String orgCountry;


    private String dstCountry;


    private String orgStation;

    /*
     * Destination of the shipment
     */
    private String dstStation;

    private String coolAtAirport;
    private String frozenAtAirport;
    private String ccShipment;
    private String bkgOrgStation;
    private String bkgDstStation;
    
    private boolean enhancedChecks;
    /*
     * Applicable date. To be filled only if the embargo is to be checked for
     * a specific date
     */
    private LocalDate shipmentDate;

    /*
     * Applicable from date. To be filled if the embargo is to be checked for
     * a period
     */
    private LocalDate fromDate;

    /*
     * Applicable to date. To be filled if the embargo is to be checked for
     * a period
     */
    private LocalDate toDate;
   
   
	/*
     * Calling module should provide the parameters as a String-Collection
     * map. Eg: map.put(SHARED_SCC,{PER,VAL})
     */
    private Map<String,Collection<String>> map;
    //added by A-4823 as part of implementing viapoint check in embargo
    private Collection<String> viaPointStation;
    private Collection<String> viaPointCountry;
    //added by A-4823 as part of implementing DOW check in embargo
    private Collection<String> dayOfWeekOrg;
    private Collection<String> dayOfWeekDst;
    private Collection<String> dayOfWeekViaPt;

    private String orgArpGrp;
    private String orgCntGrp;
    private String dstArpGrp;
    private String dstCntGrp;
    private Collection<String> viaPntArpGrp;
    private Collection<String> viaPntCntGrp;
    //for mail only
  
    private String orgPaCod;
    private String dstPaCod;
    private String ooe;
    private String doe;
    private String applicableTransaction;
    private String shipmentID;
    //Added by A-5642 for ICRD-69895
	private String embargoLevel;
	private String splitIndicator;
	//Map used for storing a code plus its possible types Eg: OriginCode "-" It's Airport group, Country, CountryGroup
	//Eg: key:TRV-ARPGRP value:INDIAGRP, key:TRV-CNT value:IN, key:TRV-CNTGRP value:ASGRP
	private Map<String,String> typeMap;
	//added for ICRD-213193 by A-7815
	private String userLocale;
	private boolean userLocaleNeeded;
	//Added by 202766 for IASCB-159851
   	private String unknownShipper;
	public String getUnknownShipper() {
		return unknownShipper;
	}
	public void setUnknownShipper(String unknownShipper) {
		this.unknownShipper = unknownShipper;
	}
	//ended
	public String getSplitIndicator() {
		return splitIndicator;
	}
	public void setSplitIndicator(String splitIndicator) {
		this.splitIndicator = splitIndicator;
	}
    public String getEmbargoLevel() {
		return embargoLevel;
	}
	public void setEmbargoLevel(String embargoLevel) {
		this.embargoLevel = embargoLevel;
	}
    
    /** The booking flight detail v os. */
    private Collection<BookingFlightDetailVO> bookingFlightDetailVOs;
	//LAT
    /** The last acceptance time. */
	private LocalDate lastAcceptanceTime;
    //TOA
    /** The time of acceptance. */
    private LocalDate timeOfAcceptance;
    
    /** The day of week viapoint. */
    private Map<String,String> dayOfWeekViapoint;
    
	public Collection<BookingFlightDetailVO> getBookingFlightDetailVOs() {
		return bookingFlightDetailVOs;
	}

	public void setBookingFlightDetailVOs(
			Collection<BookingFlightDetailVO> bookingFlightDetailVOs) {
		this.bookingFlightDetailVOs = bookingFlightDetailVOs;
	}

	public LocalDate getLastAcceptanceTime() {
		return lastAcceptanceTime;
	}

	public void setLastAcceptanceTime(LocalDate lastAcceptanceTime) {
		this.lastAcceptanceTime = lastAcceptanceTime;
	}

	public LocalDate getTimeOfAcceptance() {
		return timeOfAcceptance;
	}

	public void setTimeOfAcceptance(LocalDate timeOfAcceptance) {
		this.timeOfAcceptance = timeOfAcceptance;
	}

	public Map<String, String> getDayOfWeekViapoint() {
		return dayOfWeekViapoint;
	}

	public void setDayOfWeekViapoint(Map<String, String> dayOfWeekViapoint) {
		this.dayOfWeekViapoint = dayOfWeekViapoint;
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
	 * @return Returns the dstCountry.
	 */
	public String getDstCountry() {
		return dstCountry;
	}

	/**
	 * @param dstCountry The dstCountry to set.
	 */
	public void setDstCountry(String dstCountry) {
		this.dstCountry = dstCountry;
	}

	/**
	 * @return Returns the dstStation.
	 */
	public String getDstStation() {
		return dstStation;
	}

	/**
	 * @param dstStation The dstStation to set.
	 */
	public void setDstStation(String dstStation) {
		this.dstStation = dstStation;
	}

	/**
	 * @return Returns the map.
	 */
	public Map<String, Collection<String>> getMap() {
		return map;
	}

	/**
	 * @param map The map to set.
	 */
	public void setMap(Map<String, Collection<String>> map) {
		this.map = map;
	}

	/**
	 * @return Returns the orgCountry.
	 */
	public String getOrgCountry() {
		return orgCountry;
	}

	/**
	 * @param orgCountry The orgCountry to set.
	 */
	public void setOrgCountry(String orgCountry) {
		this.orgCountry = orgCountry;
	}

	/**
	 * @return Returns the orgStation.
	 */
	public String getOrgStation() {
		return orgStation;
	}

	/**
	 * @param orgStation The orgStation to set.
	 */
	public void setOrgStation(String orgStation) {
		this.orgStation = orgStation;
	}

	/**
	 * @return Returns the shipmentDate.
	 */
	public LocalDate getShipmentDate() {
		return shipmentDate;
	}

	/**
	 * @param shipmentDate The shipmentDate to set.
	 */
	public void setShipmentDate(LocalDate shipmentDate) {
		this.shipmentDate = shipmentDate;
	}

	/**
	 * @return Returns the fromDate.
	 */
	public LocalDate getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate The fromDate to set.
	 */
	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return Returns the toDate.
	 */
	public LocalDate getToDate() {
		return toDate;
	}

	/**
	 * @param toDate The toDate to set.
	 */
	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}
	/**
	 * @return Returns the ccShipment.
	 */
	public String getCcShipment() {
		return ccShipment;
	}

	/**
	 * @param ccShipment The ccShipment to set.
	 */
	public void setCcShipment(String ccShipment) {
		this.ccShipment = ccShipment;
	}

	/**
	 * @return Returns the coolAtAirport.
	 */
	public String getCoolAtAirport() {
		return coolAtAirport;
	}

	/**
	 * @param coolAtAirport The coolAtAirport to set.
	 */
	public void setCoolAtAirport(String coolAtAirport) {
		this.coolAtAirport = coolAtAirport;
	}

	/**
	 * @return Returns the frozenAtAirport.
	 */
	public String getFrozenAtAirport() {
		return frozenAtAirport;
	}

	/**
	 * @param frozenAtAirport The frozenAtAirport to set.
	 */
	public void setFrozenAtAirport(String frozenAtAirport) {
		this.frozenAtAirport = frozenAtAirport;
	}

	/**
	 * @return Returns the bkgDstStation.
	 */
	public String getBkgDstStation() {
		return bkgDstStation;
	}

	/**
	 * @param bkgDstStation The bkgDstStation to set.
	 */
	public void setBkgDstStation(String bkgDstStation) {
		this.bkgDstStation = bkgDstStation;
	}

	/**
	 * @return Returns the bkgOrgStation.
	 */
	public String getBkgOrgStation() {
		return bkgOrgStation;
	}

	/**
	 * @param bkgOrgStation The bkgOrgStation to set.
	 */
	public void setBkgOrgStation(String bkgOrgStation) {
		this.bkgOrgStation = bkgOrgStation;
	}

	/**
	 * @return the viaPointStation
	 */
	public Collection<String> getViaPointStation() {
		return viaPointStation;
	}

	/**
	 * @param viaPointStation the viaPointStation to set
	 */
	public void setViaPointStation(Collection<String> viaPointStation) {
		this.viaPointStation = viaPointStation;
	}

	/**
	 * @return the viaPointCountry
	 */
	public Collection<String> getViaPointCountry() {
		return viaPointCountry;
	}

	/**
	 * @param viaPointCountry the viaPointCountry to set
	 */
	public void setViaPointCountry(Collection<String> viaPointCountry) {
		this.viaPointCountry = viaPointCountry;
	}

	

	/**
	 * @return the dayOfWeekViaPt
	 */
	public Collection<String> getDayOfWeekViaPt() {
		return dayOfWeekViaPt;
	}

	/**
	 * @param dayOfWeekViaPt the dayOfWeekViaPt to set
	 */
	public void setDayOfWeekViaPt(Collection<String> dayOfWeekViaPt) {
		this.dayOfWeekViaPt = dayOfWeekViaPt;
	}

	/**
	 * @return the dayOfWeekOrg
	 */
	public Collection<String> getDayOfWeekOrg() {
		return dayOfWeekOrg;
	}

	/**
	 * @param dayOfWeekOrg the dayOfWeekOrg to set
	 */
	public void setDayOfWeekOrg(Collection<String> dayOfWeekOrg) {
		this.dayOfWeekOrg = dayOfWeekOrg;
	}

	/**
	 * @return the dayOfWeekDst
	 */
	public Collection<String> getDayOfWeekDst() {
		return dayOfWeekDst;
	}

	/**
	 * @param dayOfWeekDst the dayOfWeekDst to set
	 */
	public void setDayOfWeekDst(Collection<String> dayOfWeekDst) {
		this.dayOfWeekDst = dayOfWeekDst;
	}
	/**
	 * @return the orgArpGrp
	 */
	public String getOrgArpGrp() {
		return orgArpGrp;
	}
	/**
	 * @param orgArpGrp the orgArpGrp to set
	 */
	public void setOrgArpGrp(String orgArpGrp) {
		this.orgArpGrp = orgArpGrp;
	}
	/**
	 * @return the orgCntGrp
	 */
	public String getOrgCntGrp() {
		return orgCntGrp;
	}
	/**
	 * @param orgCntGrp the orgCntGrp to set
	 */
	public void setOrgCntGrp(String orgCntGrp) {
		this.orgCntGrp = orgCntGrp;
	}
	/**
	 * @return the dstArpGrp
	 */
	public String getDstArpGrp() {
		return dstArpGrp;
	}
	/**
	 * @param dstArpGrp the dstArpGrp to set
	 */
	public void setDstArpGrp(String dstArpGrp) {
		this.dstArpGrp = dstArpGrp;
	}
	/**
	 * @return the dstCntGrp
	 */
	public String getDstCntGrp() {
		return dstCntGrp;
	}
	/**
	 * @param dstCntGrp the dstCntGrp to set
	 */
	public void setDstCntGrp(String dstCntGrp) {
		this.dstCntGrp = dstCntGrp;
	}
	
	/**
	 * @return the applicableTransaction
	 */
	public String getApplicableTransaction() {
		return applicableTransaction;
	}
	/**
	 * @param applicableTransaction the applicableTransaction to set
	 */
	public void setApplicableTransaction(String applicableTransaction) {
		this.applicableTransaction = applicableTransaction;
	}
	/**
	 * @return the viaPntArpGrp
	 */
	public Collection<String> getViaPntArpGrp() {
		return viaPntArpGrp;
	}
	/**
	 * @param viaPntArpGrp the viaPntArpGrp to set
	 */
	public void setViaPntArpGrp(Collection<String> viaPntArpGrp) {
		this.viaPntArpGrp = viaPntArpGrp;
	}
	/**
	 * @return the viaPntCntGrp
	 */
	public Collection<String> getViaPntCntGrp() {
		return viaPntCntGrp;
	}
	/**
	 * @param viaPntCntGrp the viaPntCntGrp to set
	 */
	public void setViaPntCntGrp(Collection<String> viaPntCntGrp) {
		this.viaPntCntGrp = viaPntCntGrp;
	}
	/**
	 * @return the orgPaCod
	 */
	public String getOrgPaCod() {
		return orgPaCod;
	}
	/**
	 * @param orgPaCod the orgPaCod to set
	 */
	public void setOrgPaCod(String orgPaCod) {
		this.orgPaCod = orgPaCod;
	}
	/**
	 * @return the dstPaCod
	 */
	public String getDstPaCod() {
		return dstPaCod;
	}
	/**
	 * @param dstPaCod the dstPaCod to set
	 */
	public void setDstPaCod(String dstPaCod) {
		this.dstPaCod = dstPaCod;
	}
	/**
	 * @return the ooe
	 */
	public String getOoe() {
		return ooe;
	}
	/**
	 * @param ooe the ooe to set
	 */
	public void setOoe(String ooe) {
		this.ooe = ooe;
	}
	/**
	 * @return the doe
	 */
	public String getDoe() {
		return doe;
	}
	/**
	 * @param doe the doe to set
	 */
	public void setDoe(String doe) {
		this.doe = doe;
	}
	/**
	 * @return the shipmentID
	 */
	public String getShipmentID() {
		return shipmentID;
	}
	/**
	 * @param shipmentID the shipmentID to set
	 */
	public void setShipmentID(String shipmentID) {
		this.shipmentID = shipmentID;
	}
	/**
	 * @return the typeMap
	 */
	public Map<String, String> getTypeMap() {
		return typeMap;
	}
	/**
	 * @param typeMap the typeMap to set
	 */
	public void setTypeMap(Map<String, String> typeMap) {
		this.typeMap = typeMap;
	}
	/**
	 * 	Getter for userLocale 
	 *	Added by : a-7815 on 07-Sep-2017
	 * 	Used for :
	 */
	public String getUserLocale() {
		return userLocale;
	}
	/**
	 *  @param userLocale the userLocale to set
	 * 	Setter for userLocale 
	 *	Added by : a-7815 on 07-Sep-2017
	 * 	Used for :
	 */
	public void setUserLocale(String userLocale) {
		this.userLocale = userLocale;
	}
	/**
	 * 	Getter for userLocaleNeeded 
	 *	Added by : a-7815 on 07-Sep-2017
	 * 	Used for :
	 */
	public boolean isUserLocaleNeeded() {
		return userLocaleNeeded;
	}
	/**
	 *  @param userLocaleNeeded the userLocaleNeeded to set
	 * 	Setter for userLocaleNeeded 
	 *	Added by : a-7815 on 07-Sep-2017
	 * 	Used for :
	 */
	public void setUserLocaleNeeded(boolean userLocaleNeeded) {
		this.userLocaleNeeded = userLocaleNeeded;
	}
	/**
	 * 	Getter for isFromPrecheck 
	 *	Added by : A-7533 on 26-Jan-2018
	 * 	Used for :
	 */
	public boolean isFromPrecheck() { 
		return isFromPrecheck;
	}
	/**
	 *  @param isFromPrecheck the isFromPrecheck to set
	 * 	Setter for isFromPrecheck 
	 *	Added by : A-7533 on 26-Jan-2018
	 * 	Used for :
	 */
	public void setFromPrecheck(boolean isFromPrecheck) {
		this.isFromPrecheck = isFromPrecheck;
	}
	/**
	 * 	Getter for enhancedChecks 
	 *	Added by : A-8146 on 07-Aug-2018
	 * 	Used for :
	 */
	public boolean isEnhancedChecks() {
		return enhancedChecks;
	}
	/**
	 *  @param enhancedChecks the enhancedChecks to set
	 * 	Setter for enhancedChecks 
	 *	Added by : A-8146 on 07-Aug-2018
	 * 	Used for :
	 */
	public void setEnhancedChecks(boolean enhancedChecks) {
		this.enhancedChecks = enhancedChecks;
	}
	


  }
