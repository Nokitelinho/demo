package com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux;
import java.util.Arrays;
import com.ibsplc.icargo.framework.model.ScreenModel;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

public class MailPerformanceForm extends ScreenModel{

	private static final String SCREEN_ID = "mail.operations.ux.mailperformance";
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "operations";
	private static final String BUNDLE = "MailPerformanceResources";
	private static final String[] EMPTY_ARRAY = new String[0];
	private String pacode;
	private String coPacode;

	private String coAirport;
	private String[] coOperationFlag;

	private String[] coRowId;


	private String[] coAirportCodes;
	private String airport;
	private String rdtPacode;
	private String rdtAirport;
	private String filterResdit;
	private  String receivedFromTruck;
	private String screenFlag;
	private String statusFlag;
	private String filterCalender;
	private String calValidFrom;
	private String calValidTo;
	private String calPacode;
	//Added for ICRD-243401 starts
	private String fromPage;
	public String getRdtPacode() {
		return rdtPacode;
	}

	public void setRdtPacode(String rdtPacode) {
		this.rdtPacode = rdtPacode;
	}

	public String getRdtAirport() {
		return rdtAirport;
	}

	public void setRdtAirport(String rdtAirport) {
		this.rdtAirport = rdtAirport;
	}

	private String hoPaCode;
	private String hoAirport;
	private String handoverTime;
	private String hoMailClass;
	private String hoExchangeOffice;
	private String hoMailSubClass;
	//for pagination
	private String pagination;
	private String lastPageNum = "";
	private String displayPage = "1";
	private Page handoverPage;
	private String defaultPageSize ="10";
	private int totalRecords;
	private int recordsPerPage;
	//Added for ICRD-243401 ends
	private String serviceStandardsPacode;
	private String serviceLevel;
	private String serviceStandardsOrigin;
	private String serviceStandardsDestination;
	private String scanningWaived;
	private String serviceStandard;
	private String contractId;
	private String region;
	private String serviceStdrowId;
	private String[] originCode;
	private String[] destinationCode;
	private String[] servicelevel;
	private String[] servicestandard;
	private String[] contractid;
	private String[] scanWaived;
	private String[] serviceStdoperationFlag;
	//For Contract Id
	private String conPaCode;
	private String originAirport;
	private String destinationAirport;
	private String contractID;
	private Page gpaContractPage;
   //For Incentives
	private String incPaCode;
	private String incFlag;
	private String serviceResponsiveFlag;
	private String[] parCodeValue;
	private String servRespFlag;//For checking screen status Added for ICRD-308870
	private String disIncFlag;//For checking screen status  Added for ICRD-308870

	private String[] rowId;
	private String[] operationFlag;


	public String[] getRdtRowId() {
		return rdtRowId;
	}

	public void setRdtRowId(String[] rdtRowId) {
		this.rdtRowId = rdtRowId;
	}

	public String[] getRdtOperationFlag() {
		return rdtOperationFlag;
	}

	public void setRdtOperationFlag(String[] rdtOperationFlag) {
		this.rdtOperationFlag = rdtOperationFlag;
	}

	private String[] rdtRowId;
	private String[] rdtOperationFlag;
	private String disableSave;
	private  String[] airportCodes;
	private  String[] resditModes;
	private String[] truckFlag;
	//Added for ICRD-243401 starts
	private String[] hoAirportCodes;
	private String[] hoMailClasses;
	private String[] exchangeOffice;
	private String[] mailSubClass;
	public String[] getRdtAirportCodes() {
		return rdtAirportCodes;
	}

	public void setRdtAirportCodes(String[] rdtAirportCodes) {
		this.rdtAirportCodes = rdtAirportCodes;
	}

	private String[] rdtAirportCodes;
	private String[] handoverTimes;
	private String[] hoRowId;
	private String[] hoOperationFlags;
	//Added for ICRD-243401 ends

	private long[] seqnum;

	private String[] calRowIds;
	private String[] calOperationFlags;
	private long[] calSeqnum;
	private String[] periods;
	private String[] fromDates;
	private String[] toDates;
	private String[] cgrSubmissions;
	private String[] cgrDeleteCutOffs;
	private String[] cutWeek1s;
	private String[] cutWeek2s;
	private String[] cutWeek3s;
	private String[] cutWeek4s;
	private String[] cutWeek5s;
	private String[] companyCodes;
	private String[] paymEffectiveDates;
	private String[] incCalcDates;
	private String[] incEffectiveDates;
	private String[] incRecvDates;
	//Added by A-8527 for ICRD-262451
	private String[] clmGenDate;
    private int[] rdtOffset;
	private String[] originAirports;
	private String[] destinationAirports;
	private String[] contractIDs;
	private String[] cidFromDates;
	private String[] regions;
	private String[] cidToDates;
	private String[] conOperationFlags;
	private String[] conRowId;
	private String[] incRowId;
	private String[] incDetailRowId;
	private String[] disIncRowId;
	private String[] disIncDetailRowId;

	private String[] disIncSrvRowId;
	private String[] disIncSrvDetailRowId;

	private String[] disIncNonSrvRowId;
	private String[] disIncNonSrvDetailRowId;

	private String[] disIncBothSrvRowId;
	private String[] disIncBothSrvDetailRowId;

	private String[] incOperationFlags;
	private String[] disIncOperationFlags;
	private String[] disIncSrvOperationFlags;
	private String[] disIncNonSrvOperationFlags;
	private String[] disIncBothSrvOperationFlags;
	private String[] incSerialNumber;
	private String[] incSequenceNumber;
	private String[] formula;
	private String[] basis;
	private Double[] incPercentage;
	private Double[] disIncPercentage;
	private String[] incValidFrom;
	private String[] incValidTo;
	private String[] disIncValidFrom;
	private String[] disIncValidTo;

	private String[] srvFormula;
	private String[] nonSrvFormula;
	private String[] bothSrvFormula;
	private String[] srvBasis;
	private String[] nonSrvBasis;
	private String[] bothSrvBasis;
	private String[] tempSrvBasis;
	private String[] tempNonSrvBasis;
	private String[] tempBothSrvBasis;
	private Double[] disIncSrvPercentage;
	private Double[] disIncNonSrvPercentage;
	private Double[] disIncBothSrvPercentage;

	private String[] disIncSrvValidFrom;
	private String[] disIncSrvValidTo;
	private String[] disIncNonSrvValidFrom;
	private String[] disIncNonSrvValidTo;
	private String[] disIncBothSrvValidFrom;
	private String[] disIncBothSrvValidTo;

	private String[] incParameterType;
	private String[] incParameterCode;
	private String[] incParameterValue;
	private String[] disIncParameterType;
	private String[] disIncParameterCode;
	private String[] disIncParameterValue;
	private String[] excFlag;
	private String[] displayFormula;

	private String[] disIncParameter;

	private String[] srvExcFlag;
	private String[] srvDisplayFormula;
	private String[] nonSrvExcFlag;
	private String[] nonSrvDisplayFormula;
	private String[] bothSrvExcFlag;
	private String[] bothSrvDisplayFormula;
	private String[] disIncSrvParameter;
	private String[] disIncNonSrvParameter;
	private String[] disIncBothSrvParameter;

	private String[] disIncSrvParameterType;
	private String[] disIncSrvParameterCode;
	private String[] disIncSrvParameterValue;
	private String[] disIncNonSrvParameterType;
	private String[] disIncNonSrvParameterCode;
	private String[] disIncNonSrvParameterValue;
	private String[] disIncBothSrvParameterType;
	private String[] disIncBothSrvParameterCode;
	private String[] disIncBothSrvParameterValue;

	private String[] srvDisplayParameter;
	private String[] nonSrvDisplayParameter;
	private String[] bothSrvDisplayParameter;

	private String postalPeriod;
	private String postalFromDate;
	private String postalToDate;
	private String postalDiscEftDate;
	private String postalIncCalcDate;
	private String postalIncEftDate;
	private String postalIncRecvDate;
	private String postalClaimGenDate;
	private String postalCalendarAction;
	private int selectedRow;
	private String[] selectedIndex;
    private String[] amot;	
    private String paCodeValue;
	public String getPaCodeValue() {
		return paCodeValue;
	}

	public void setPaCodeValue(String paCodeValue) {
		this.paCodeValue = paCodeValue;
	}

	
        public String[] getAmot() {
		 if (amot!=null &&amot.length>0){
		 return Arrays.copyOf(amot,amot.length);}
		 else{
			 return EMPTY_ARRAY;
		 }
	}
	
	public void setAmot(String[] amot) {
	 if (amot!=null &&amot.length>0){
	 this.amot = Arrays.copyOf(amot,amot.length);}
	}

	public String getServiceStdrowId() {
		return serviceStdrowId;
	}

	public void setServiceStdrowId(String serviceStdrowId) {
		this.serviceStdrowId = serviceStdrowId;
	}

	public String[] getServiceStdoperationFlag() {
		return serviceStdoperationFlag;
	}

	public void setServiceStdoperationFlag(String[] serviceStdoperationFlag) {
		this.serviceStdoperationFlag = serviceStdoperationFlag;
	}

	public String[] getOriginCode() {
		return originCode;
	}

	public void setOriginCode(String[] originCode) {
		this.originCode = originCode;
	}

	public String[] getDestinationCode() {
		return destinationCode;
	}

	public void setDestinationCode(String[] destinationCode) {
		this.destinationCode = destinationCode;
	}

	public String[] getServicelevel() {
		return servicelevel;
	}

	public void setServicelevel(String[] servicelevel) {
		this.servicelevel = servicelevel;
	}

	public String[] getServicestandard() {
		return servicestandard;
	}

	public void setServicestandard(String[] servicestandard) {
		this.servicestandard = servicestandard;
	}

	public String[] getContractid() {
		return contractid;
	}

	public void setContractid(String[] contractid) {
		this.contractid = contractid;
	}

	public String[] getScanWaived() {
		return scanWaived;
	}

	public void setScanWaived(String[] scanWaived) {
		this.scanWaived = scanWaived;
	}

	public String getServiceStandard() {
		return serviceStandard;
	}

	public void setServiceStandard(String serviceStandard) {
		this.serviceStandard = serviceStandard;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public String getServiceStandardsPacode() {
		return serviceStandardsPacode;
	}

	public void setServiceStandardsPacode(String serviceStandardsPacode) {
		this.serviceStandardsPacode = serviceStandardsPacode;
	}

	public String getServiceLevel() {
		return serviceLevel;
	}

	public void setServiceLevel(String serviceLevel) {
		this.serviceLevel = serviceLevel;
	}
	public String getServiceStandardsOrigin() {
		return serviceStandardsOrigin;
	}

	public void setServiceStandardsOrigin(String serviceStandardsOrigin) {
		this.serviceStandardsOrigin = serviceStandardsOrigin;
	}

	public String getServiceStandardsDestination() {
		return serviceStandardsDestination;
	}

	public void setServiceStandardsDestination(String serviceStandardsDestination) {
		this.serviceStandardsDestination = serviceStandardsDestination;
	}
	public String getScanningWaived() {
		return scanningWaived;
	}

	public void setScanningWaived(String scanningWaived) {
		this.scanningWaived = scanningWaived;
	}

	@Override
	public String getProduct() {
		return PRODUCT_NAME;
	}

	@Override
	public String getScreenId() {
		return SCREEN_ID;
	}

	@Override
	public String getSubProduct() {
		return SUBPRODUCT_NAME;
	}

	public String getBundle() {
		return BUNDLE;
	}

	public String getPacode() {
		return pacode;
	}

	public void setPacode(String pacode) {
		this.pacode = pacode;
	}

	public String getAirport() {
		return airport;
	}

	public void setAirport(String airport) {
		this.airport = airport;
	}

	public String getFilterResdit() {
		return filterResdit;
	}

	public void setFilterResdit(String filterResdit) {
		this.filterResdit = filterResdit;
	}

	public String getReceivedFromTruck() {
		return receivedFromTruck;
	}

	public void setReceivedFromTruck(String receivedFromTruck) {
		this.receivedFromTruck = receivedFromTruck;
	}

	public String getScreenFlag() {
		return screenFlag;
	}

	public void setScreenFlag(String screenFlag) {
		this.screenFlag = screenFlag;
	}

	public String getPagination() {
		return pagination;
	}
	public void setPagination(String pagination) {
		this.pagination = pagination;
	}
	/**
	 * @return the handoverPage
	 */
	public Page getHandoverPage() {
		return handoverPage;
	}
	/**
	 * @param handoverPage the handoverPage to set
	 */
	public void setHandoverPage(Page handoverPage) {
		this.handoverPage = handoverPage;
	}
	/**
	 * @return the lastPageNum
	 */
	public String getLastPageNum() {
		return lastPageNum;
	}
	/**
	 * @param lastPageNum the lastPageNum to set
	 */
	public void setLastPageNum(String lastPageNum) {
		this.lastPageNum = lastPageNum;
	}
	/**
	 * @return the displayPage
	 */
	public String getDisplayPage() {
		return displayPage;
	}
	/**
	 * @param displayPage the displayPage to set
	 */
	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}
	/**
	 * @return the defaultPageSize
	 */
	public String getDefaultPageSize() {
		return defaultPageSize;
	}
	/**
	 * @param defaultPageSize the defaultPageSize to set
	 */
	public void setDefaultPageSize(String defaultPageSize) {
		this.defaultPageSize = defaultPageSize;
	}
	/**
	 * @return the totalRecords
	 */
	public int getTotalRecords() {
		return totalRecords;
	}
	/**
	 * @param totalRecords the totalRecords to set
	 */
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
	/**
	 * @return the recordsPerPage
	 */
	public int getRecordsPerPage() {
		return recordsPerPage;
	}
	/**
	 * @param recordsPerPage the recordsPerPage to set
	 */
	public void setRecordsPerPage(int recordsPerPage) {
		this.recordsPerPage = recordsPerPage;
	}
	/**
	 * @return the originAirport
	 */
	public String getOriginAirport() {
		return originAirport;
	}

	/**
	 * @param originAirport the originAirport to set
	 */
	public void setOriginAirport(String originAirport) {
		this.originAirport = originAirport;
	}

	/**
	 * @return the destinationAirport
	 */
	public String getDestinationAirport() {
		return destinationAirport;
	}

	/**
	 * @param destinationAirport the destinationAirport to set
	 */
	public void setDestinationAirport(String destinationAirport) {
		this.destinationAirport = destinationAirport;
	}

	/**
	 * @return the contractID
	 */
	public String getContractID() {
		return contractID;
	}

	/**
	 * @param contractID the contractID to set
	 */
	public void setContractID(String contractID) {
		this.contractID = contractID;
	}

	/**
	 * @return the gpaContractPage
	 */
	public Page getGpaContractPage() {
		return gpaContractPage;
	}

	/**
	 * @param gpaContractPage the gpaContractPage to set
	 */
	public void setGpaContractPage(Page gpaContractPage) {
		this.gpaContractPage = gpaContractPage;
	}



	public String[] getRowId() {
		return rowId;
	}

	public void setRowId(String[] rowId) {
		this.rowId = rowId;
	}

	public String[] getOperationFlag() {
		return operationFlag;
	}

	public void setOperationFlag(String[] operationFlag) {
		this.operationFlag = operationFlag;
	}

	public String getDisableSave() {
		return disableSave;
	}

	public void setDisableSave(String disableSave) {
		this.disableSave = disableSave;
	}

	public String[] getAirportCodes() {
		return airportCodes;
	}

	public void setAirportCodes(String[] airportCodes) {
		this.airportCodes = airportCodes;
	}

	public String[] getResditModes() {
		return resditModes;
	}

	public void setResditModes(String[] resditModes) {
		this.resditModes = resditModes;
	}

	public String[] getTruckFlag() {
		return truckFlag;
	}

	public void setTruckFlag(String[] truckFlag) {
		this.truckFlag = truckFlag;
	}

	public long[] getSeqnum() {
		return seqnum;
	}

	public void setSeqnum(long[] seqnum) {
		this.seqnum = seqnum;
	}
	/**
	 * @return the hoMailClasses
	 */
	public String[] getHoMailClasses() {
		return hoMailClasses;
	}
	/**
	 * @param hoMailClasses the hoMailClasses to set
	 */
	public void setHoMailClasses(String[] hoMailClasses) {
		this.hoMailClasses = hoMailClasses;
	}
	/**
	 * @return the handoverTimes
	 */
	public String[] getHandoverTimes() {
		return handoverTimes;
	}
	/**
	 * @param handoverTimes the handoverTimes to set
	 */
	public void setHandoverTimes(String[] handoverTimes) {
		this.handoverTimes = handoverTimes;
	}
	public String getStatusFlag() {
		return statusFlag;
	}

	public void setStatusFlag(String statusFlag) {
		this.statusFlag = statusFlag;
	}

	public String getFilterCalender() {
		return filterCalender;
	}

	public void setFilterCalender(String filterCalender) {
		this.filterCalender = filterCalender;
	}

	public String getCalValidFrom() {
		return calValidFrom;
	}

	public void setCalValidFrom(String calValidFrom) {
		this.calValidFrom = calValidFrom;
	}

	public String getCalValidTo() {
		return calValidTo;
	}

	public void setCalValidTo(String calValidTo) {
		this.calValidTo = calValidTo;
	}

	public String getCalPacode() {
		return calPacode;
	}

	public void setCalPacode(String calPacode) {
		this.calPacode = calPacode;
	}
	public String[] getCalRowIds() {
		return calRowIds;
	}
	public void setCalRowIds(String[] calRowIds) {
		this.calRowIds = calRowIds;
	}
	public String[] getCalOperationFlags() {
		return calOperationFlags;
	}
	public void setCalOperationFlags(String[] calOperationFlags) {
		this.calOperationFlags = calOperationFlags;
	}
	public long[] getCalSeqnum() {
		return calSeqnum;
	}
	public void setCalSeqnum(long[] calSeqnum) {
		this.calSeqnum = calSeqnum;
	}
	public String[] getPeriods() {
		return periods;
	}
	public void setPeriods(String[] periods) {
		this.periods = periods;
	}
	public String[] getFromDates() {
		return fromDates;
	}
	public void setFromDates(String[] fromDates) {
		this.fromDates = fromDates;
	}
	public String[] getToDates() {
		return toDates;
	}
	public void setToDates(String[] toDates) {
		this.toDates = toDates;
	}
	public String[] getCgrSubmissions() {
		return cgrSubmissions;
	}
	public void setCgrSubmissions(String[] cgrSubmissions) {
		this.cgrSubmissions = cgrSubmissions;
	}
	public String[] getCgrDeleteCutOffs() {
		return cgrDeleteCutOffs;
	}
	public void setCgrDeleteCutOffs(String[] cgrDeleteCutOffs) {
		this.cgrDeleteCutOffs = cgrDeleteCutOffs;
	}
	public String[] getCutWeek1s() {
		return cutWeek1s;
	}
	public void setCutWeek1s(String[] cutWeek1s) {
		this.cutWeek1s = cutWeek1s;
	}
	public String[] getCutWeek2s() {
		return cutWeek2s;
	}
	public void setCutWeek2s(String[] cutWeek2s) {
		this.cutWeek2s = cutWeek2s;
	}
	public String[] getCutWeek3s() {
		return cutWeek3s;
	}
	public void setCutWeek3s(String[] cutWeek3s) {
		this.cutWeek3s = cutWeek3s;
	}
	public String[] getCutWeek4s() {
		return cutWeek4s;
	}
	public void setCutWeek4s(String[] cutWeek4s) {
		this.cutWeek4s = cutWeek4s;
	}
	public String[] getCutWeek5s() {
		return cutWeek5s;
	}
	public void setCutWeek5s(String[] cutWeek5s) {
		this.cutWeek5s = cutWeek5s;
	}
	public String[] getCompanyCodes() {
		return companyCodes;
	}
	public void setCompanyCodes(String[] companyCodes) {
		this.companyCodes = companyCodes;
	}
	public String[] getPaymEffectiveDates() {
		return paymEffectiveDates;
	}
	public void setPaymEffectiveDates(String[] paymEffectiveDates) {
		this.paymEffectiveDates = paymEffectiveDates;
	}
	public String[] getIncCalcDates() {
		return incCalcDates;
	}
	public void setIncCalcDates(String[] incCalcDates) {
		this.incCalcDates = incCalcDates;
	}
	public String[] getIncEffectiveDates() {
		return incEffectiveDates;
	}
	public void setIncEffectiveDates(String[] incEffectiveDates) {
		this.incEffectiveDates = incEffectiveDates;
	}
	public String[] getIncRecvDates() {
		return incRecvDates;
	}
	public void setIncRecvDates(String[] incRecvDates) {
		this.incRecvDates = incRecvDates;
	}

	/**
	 * @return the conPaCode
	 */
	public String getConPaCode() {
		return conPaCode;
	}

	/**
	 * @param conPaCode the conPaCode to set
	 */
	public void setConPaCode(String conPaCode) {
		this.conPaCode = conPaCode;
	}

	/**
	 * @return the originAirports
	 */
	public String[] getOriginAirports() {
		return originAirports;
	}

	/**
	 * @param originAirports the originAirports to set
	 */
	public void setOriginAirports(String[] originAirports) {
		this.originAirports = originAirports;
	}

	/**
	 * @return the destinationAirports
	 */
	public String[] getDestinationAirports() {
		return destinationAirports;
	}

	/**
	 * @param destinationAirports the destinationAirports to set
	 */
	public void setDestinationAirports(String[] destinationAirports) {
		this.destinationAirports = destinationAirports;
	}

	/**
	 * @return the contractIDs
	 */
	public String[] getContractIDs() {
		return contractIDs;
	}

	/**
	 * @param contractIDs the contractIDs to set
	 */
	public void setContractIDs(String[] contractIDs) {
		this.contractIDs = contractIDs;
	}

	/**
	 * @return the cidFromDates
	 */
	public String[] getCidFromDates() {
		return cidFromDates;
	}

	/**
	 * @param cidFromDates the cidFromDates to set
	 */
	public void setCidFromDates(String[] cidFromDates) {
		this.cidFromDates = cidFromDates;
	}

	/**
	 * @return the cidToDates
	 */
	public String[] getCidToDates() {
		return cidToDates;
	}

	/**
	 * @param cidToDates the cidToDates to set
	 */
	public void setCidToDates(String[] cidToDates) {
		this.cidToDates = cidToDates;
	}

	/**
	 * @return the conOperationFlags
	 */
	public String[] getConOperationFlags() {
		return conOperationFlags;
	}

	/**
	 * @param conOperationFlags the conOperationFlags to set
	 */
	public void setConOperationFlags(String[] conOperationFlags) {
		this.conOperationFlags = conOperationFlags;
	}

	/**
	 * @return the conRowId
	 */
	public String[] getConRowId() {
		return conRowId;
	}

	/**
	 * @param conRowId the conRowId to set
	 */
	public void setConRowId(String[] conRowId) {
		this.conRowId = conRowId;
	}





	 	/**
	 * @return the fromPage
	 */
	public String getFromPage() {
		return fromPage;
	}

	/**
	 * @param fromPage the fromPage to set
	 */
	public void setFromPage(String fromPage) {
		this.fromPage = fromPage;
	}


	/**
	 * @return the hoPaCode
	 */
	public String getHoPaCode() {
		return hoPaCode;
	}

	/**
	 * @param hoPaCode the hoPaCode to set
	 */
	public void setHoPaCode(String hoPaCode) {
		this.hoPaCode = hoPaCode;
	}

	/**
	 * @return the hoAirport
	 */
	public String getHoAirport() {
		return hoAirport;
	}

	/**
	 * @param hoAirport the hoAirport to set
	 */
	public void setHoAirport(String hoAirport) {
		this.hoAirport = hoAirport;
	}

	/**
	 * @return the handoverTime
	 */
	public String getHandoverTime() {
		return handoverTime;
	}

	/**
	 * @param handoverTime the handoverTime to set
	 */
	public void setHandoverTime(String handoverTime) {
		this.handoverTime = handoverTime;
	}

	/**
	 * @return the hoRowId
	 */
	public String[] getHoRowId() {
		return hoRowId;
	}

	/**
	 * @param hoRowId the hoRowId to set
	 */
	public void setHoRowId(String[] hoRowId) {
		this.hoRowId = hoRowId;
	}

	/**
	 * @return the hoOperationFlags
	 */
	public String[] getHoOperationFlags() {
		return hoOperationFlags;
	}

	/**
	 * @param hoOperationFlags the hoOperationFlags to set
	 */
	public void setHoOperationFlags(String[] hoOperationFlags) {
		this.hoOperationFlags = hoOperationFlags;
	}

	/**
	 * @return the hoAirportCodes
	 */
	public String[] getHoAirportCodes() {
		return hoAirportCodes;
	}

	/**
	 * @param hoAirportCodes the hoAirportCodes to set
	 */
	public void setHoAirportCodes(String[] hoAirportCodes) {
		this.hoAirportCodes = hoAirportCodes;
	}

	/**
	 * @return the hoMailClass
	 */
	public String getHoMailClass() {
		return hoMailClass;
	}

	/**
	 * @param hoMailClass the hoMailClass to set
	 */
	public void setHoMailClass(String hoMailClass) {
		this.hoMailClass = hoMailClass;
	}
	/**
	 * @return the hoExchangeOffice
	 */
	public String getHoExchangeOffice() {
		return hoExchangeOffice;
	}
	/**
	 * @param hoExchangeOffice the hoExchangeOffice to set
	 */
	public void setHoExchangeOffice(String hoExchangeOffice) {
		this.hoExchangeOffice = hoExchangeOffice;
	}

	/**
	 * @return the incPaCode
	 */
	public String getIncPaCode() {
		return incPaCode;
	}

	/**
	 * @param incPaCode the incPaCode to set
	 */
	public void setIncPaCode(String incPaCode) {
		this.incPaCode = incPaCode;
	}

	/**
	 * @return the incRowId
	 */
	public String[] getIncRowId() {
		return incRowId;
	}

	/**
	 * @param incRowId the incRowId to set
	 */
	public void setIncRowId(String[] incRowId) {
		this.incRowId = incRowId;
	}

	/**
	 * @return the incDetailRowId
	 */
	public String[] getIncDetailRowId() {
		return incDetailRowId;
	}

	/**
	 * @param incDetailRowId the incDetailRowId to set
	 */
	public void setIncDetailRowId(String[] incDetailRowId) {
		this.incDetailRowId = incDetailRowId;
	}

	/**
	 * @return the disIncRowId
	 */
	public String[] getDisIncRowId() {
		return disIncRowId;
	}

	/**
	 * @param disIncRowId the disIncRowId to set
	 */
	public void setDisIncRowId(String[] disIncRowId) {
		this.disIncRowId = disIncRowId;
	}

	/**
	 * @return the disIncDetailRowId
	 */
	public String[] getDisIncDetailRowId() {
		return disIncDetailRowId;
	}

	/**
	 * @param disIncDetailRowId the disIncDetailRowId to set
	 */
	public void setDisIncDetailRowId(String[] disIncDetailRowId) {
		this.disIncDetailRowId = disIncDetailRowId;
	}

	/**
	 * @return the incOperationFlags
	 */
	public String[] getIncOperationFlags() {
		return incOperationFlags;
	}

	/**
	 * @param incOperationFlags the incOperationFlags to set
	 */
	public void setIncOperationFlags(String[] incOperationFlags) {
		this.incOperationFlags = incOperationFlags;
	}
	/**
	 * @return the disIncOperationFlags
	 */
	public String[] getDisIncOperationFlags() {
		return disIncOperationFlags;
	}

	/**
	 * @param disIncOperationFlags the disIncOperationFlags to set
	 */
	public void setDisIncOperationFlags(String[] disIncOperationFlags) {
		this.disIncOperationFlags = disIncOperationFlags;
	}

	/**
	 * @return the disIncSrvOperationFlags
	 */
	public String[] getDisIncSrvOperationFlags() {
		return disIncSrvOperationFlags;
	}

	/**
	 * @param disIncSrvOperationFlags the disIncSrvOperationFlags to set
	 */
	public void setDisIncSrvOperationFlags(String[] disIncSrvOperationFlags) {
		this.disIncSrvOperationFlags = disIncSrvOperationFlags;
	}

	/**
	 * @return the disIncNonSrvOperationFlags
	 */
	public String[] getDisIncNonSrvOperationFlags() {
		return disIncNonSrvOperationFlags;
	}

	/**
	 * @param disIncNonSrvOperationFlags the disIncNonSrvOperationFlags to set
	 */
	public void setDisIncNonSrvOperationFlags(String[] disIncNonSrvOperationFlags) {
		this.disIncNonSrvOperationFlags = disIncNonSrvOperationFlags;
	}

	/**
	 * @return the disIncBothSrvOperationFlags
	 */
	public String[] getDisIncBothSrvOperationFlags() {
		return disIncBothSrvOperationFlags;
	}

	/**
	 * @param disIncBothSrvOperationFlags the disIncBothSrvOperationFlags to set
	 */
	public void setDisIncBothSrvOperationFlags(String[] disIncBothSrvOperationFlags) {
		this.disIncBothSrvOperationFlags = disIncBothSrvOperationFlags;
	}

	/**
	 * @return the incFlag
	 */
	public String getIncFlag() {
		return incFlag;
	}

	/**
	 * @param incFlag the incFlag to set
	 */
	public void setIncFlag(String incFlag) {
		this.incFlag = incFlag;
	}

	/**
	 * @return the serviceResponsiveFlag
	 */
	public String getServiceResponsiveFlag() {
		return serviceResponsiveFlag;
	}

	/**
	 * @param serviceResponsiveFlag the serviceResponsiveFlag to set
	 */
	public void setServiceResponsiveFlag(String serviceResponsiveFlag) {
		this.serviceResponsiveFlag = serviceResponsiveFlag;
	}
	/**
	 * @return the formula
	 */
	public String[] getFormula() {
		return formula;
	}

	/**
	 * @param formula the formula to set
	 */
	public void setFormula(String[] formula) {
		this.formula = formula;
	}

	/**
	 * @return the basis
	 */
	public String[] getBasis() {
		return basis;
	}

	/**
	 * @param basis the basis to set
	 */
	public void setBasis(String[] basis) {
		this.basis = basis;
	}


	/**
	 * @return the incPercentage
	 */
	public Double[] getIncPercentage() {
		return incPercentage;
	}

	/**
	 * @param incPercentage the incPercentage to set
	 */
	public void setIncPercentage(Double[] incPercentage) {
		this.incPercentage = incPercentage;
	}

	/**
	 * @return the disIncPercentage
	 */
	public Double[] getDisIncPercentage() {
		return disIncPercentage;
	}

	/**
	 * @param disIncPercentage the disIncPercentage to set
	 */
	public void setDisIncPercentage(Double[] disIncPercentage) {
		this.disIncPercentage = disIncPercentage;
	}

	/**
	 * @return the incValidFrom
	 */
	public String[] getIncValidFrom() {
		return incValidFrom;
	}

	/**
	 * @param incValidFrom the incValidFrom to set
	 */
	public void setIncValidFrom(String[] incValidFrom) {
		this.incValidFrom = incValidFrom;
	}

	/**
	 * @return the incValidTo
	 */
	public String[] getIncValidTo() {
		return incValidTo;
	}

	/**
	 * @param incValidTo the incValidTo to set
	 */
	public void setIncValidTo(String[] incValidTo) {
		this.incValidTo = incValidTo;
	}

	/**
	 * @return the disIncValidFrom
	 */
	public String[] getDisIncValidFrom() {
		return disIncValidFrom;
	}

	/**
	 * @param disIncValidFrom the disIncValidFrom to set
	 */
	public void setDisIncValidFrom(String[] disIncValidFrom) {
		this.disIncValidFrom = disIncValidFrom;
	}

	/**
	 * @return the disIncValidTo
	 */
	public String[] getDisIncValidTo() {
		return disIncValidTo;
	}

	/**
	 * @param disIncValidTo the disIncValidTo to set
	 */
	public void setDisIncValidTo(String[] disIncValidTo) {
		this.disIncValidTo = disIncValidTo;
	}

	/**
	 * @return the incParameterType
	 */
	public String[] getIncParameterType() {
		return incParameterType;
	}

	/**
	 * @param incParameterType the incParameterType to set
	 */
	public void setIncParameterType(String[] incParameterType) {
		this.incParameterType = incParameterType;
	}

	/**
	 * @return the incParameterCode
	 */
	public String[] getIncParameterCode() {
		return incParameterCode;
	}

	/**
	 * @param incParameterCode the incParameterCode to set
	 */
	public void setIncParameterCode(String[] incParameterCode) {
		this.incParameterCode = incParameterCode;
	}

	/**
	 * @return the incParameterValue
	 */
	public String[] getIncParameterValue() {
		return incParameterValue;
	}

	/**
	 * @param incParameterValue the incParameterValue to set
	 */
	public void setIncParameterValue(String[] incParameterValue) {
		this.incParameterValue = incParameterValue;
	}

	/**
	 * @return the disIncParameterType
	 */
	public String[] getDisIncParameterType() {
		return disIncParameterType;
	}

	/**
	 * @param disIncParameterType the disIncParameterType to set
	 */
	public void setDisIncParameterType(String[] disIncParameterType) {
		this.disIncParameterType = disIncParameterType;
	}

	/**
	 * @return the disIncParameterCode
	 */
	public String[] getDisIncParameterCode() {
		return disIncParameterCode;
	}

	/**
	 * @param disIncParameterCode the disIncParameterCode to set
	 */
	public void setDisIncParameterCode(String[] disIncParameterCode) {
		this.disIncParameterCode = disIncParameterCode;
	}

	/**
	 * @return the disIncParameterValue
	 */
	public String[] getDisIncParameterValue() {
		return disIncParameterValue;
	}

	/**
	 * @param disIncParameterValue the disIncParameterValue to set
	 */
	public void setDisIncParameterValue(String[] disIncParameterValue) {
		this.disIncParameterValue = disIncParameterValue;
	}

	/**
	 * @return the incSerialNumber
	 */
	public String[] getIncSerialNumber() {
		return incSerialNumber;
	}

	/**
	 * @param incSerialNumber the incSerialNumber to set
	 */
	public void setIncSerialNumber(String[] incSerialNumber) {
		this.incSerialNumber = incSerialNumber;
	}

	/**
	 * @return the incSequenceNumber
	 */
	public String[] getIncSequenceNumber() {
		return incSequenceNumber;
	}

	/**
	 * @param incSequenceNumber the incSequenceNumber to set
	 */
	public void setIncSequenceNumber(String[] incSequenceNumber) {
		this.incSequenceNumber = incSequenceNumber;
	}

	/**
	 * @return the excFlag
	 */
	public String[] getExcFlag() {
		return excFlag;
	}

	/**
	 * @param excFlag the excFlag to set
	 */
	public void setExcFlag(String[] excFlag) {
		this.excFlag = excFlag;
	}

	/**
	 * @return the displayFormula
	 */
	public String[] getDisplayFormula() {
		return displayFormula;
	}

	/**
	 * @param displayFormula the displayFormula to set
	 */
	public void setDisplayFormula(String[] displayFormula) {
		this.displayFormula = displayFormula;
	}
/**
	 * 	Getter for rdtOffset
	 *	Added by : A-6991 on 20-Jul-2018
	 * 	Used for :ICRD-212544
	 */
	public int[] getRdtOffset() {
		return rdtOffset;
	}

	/**
	 *  @param rdtOffset the rdtOffset to set
	 * 	Setter for rdtOffset
	 *	Added by : A-6991 on 20-Jul-2018
	 * 	Used for :ICRD-212544
	 */
	public void setRdtOffset(int[] rdtOffset) {
		this.rdtOffset = rdtOffset;
	}

	/**
	 * @return the parCodeValue
	 */
	public String[] getParCodeValue() {
		return parCodeValue;
	}

	/**
	 * @param parCodeValue the parCodeValue to set
	 */
	public void setParCodeValue(String[] parCodeValue) {
		this.parCodeValue = parCodeValue;
	}

	/**
	 * @return the disIncParameter
	 */
	public String[] getDisIncParameter() {
		return disIncParameter;
	}

	/**
	 * @param disIncParameter the disIncParameter to set
	 */
	public void setDisIncParameter(String[] disIncParameter) {
		this.disIncParameter = disIncParameter;
	}
	public String getCoPacode() {
		return coPacode;
	}

	public void setCoPacode(String coPacode) {
		this.coPacode = coPacode;
	}

	public String getCoAirport() {
		return coAirport;
	}

	public void setCoAirport(String coAirport) {
		this.coAirport = coAirport;
	}

	public String[] getCoOperationFlag() {
		return coOperationFlag;
	}

	public void setCoOperationFlag(String[] coOperationFlag) {
		this.coOperationFlag = coOperationFlag;
	}

	public String[] getCoAirportCodes() {
		return coAirportCodes;
	}

	public void setCoAirportCodes(String[] coAirportCodes) {
		this.coAirportCodes = coAirportCodes;
	}
	public String[] getCoRowId() {
		return coRowId;
	}

	public void setCoRowId(String[] coRowId) {
		this.coRowId = coRowId;
	}
	/**
	 * @return the servRespFlag
	 */
	public String getServRespFlag() {
		return servRespFlag;
	}
	/**
	 * @param servRespFlag the servRespFlag to set
	 */
	public void setServRespFlag(String servRespFlag) {
		this.servRespFlag = servRespFlag;
	}
	/**
	 * @return the disIncFlag
	 */
	public String getDisIncFlag() {
		return disIncFlag;
	}
	/**
	 * @param disIncFlag the disIncFlag to set
	 */
	public void setDisIncFlag(String disIncFlag) {
		this.disIncFlag = disIncFlag;
	}

	/**
	 * @return the disIncSrvRowId
	 */
	public String[] getDisIncSrvRowId() {
		return disIncSrvRowId;
	}

	/**
	 * @param disIncSrvRowId the disIncSrvRowId to set
	 */
	public void setDisIncSrvRowId(String[] disIncSrvRowId) {
		this.disIncSrvRowId = disIncSrvRowId;
	}

	/**
	 * @return the disIncSrvDetailRowId
	 */
	public String[] getDisIncSrvDetailRowId() {
		return disIncSrvDetailRowId;
	}

	/**
	 * @param disIncSrvDetailRowId the disIncSrvDetailRowId to set
	 */
	public void setDisIncSrvDetailRowId(String[] disIncSrvDetailRowId) {
		this.disIncSrvDetailRowId = disIncSrvDetailRowId;
	}

	/**
	 * @return the disIncNonSrvRowId
	 */
	public String[] getDisIncNonSrvRowId() {
		return disIncNonSrvRowId;
	}

	/**
	 * @param disIncNonSrvRowId the disIncNonSrvRowId to set
	 */
	public void setDisIncNonSrvRowId(String[] disIncNonSrvRowId) {
		this.disIncNonSrvRowId = disIncNonSrvRowId;
	}

	/**
	 * @return the disIncNonSrvDetailRowId
	 */
	public String[] getDisIncNonSrvDetailRowId() {
		return disIncNonSrvDetailRowId;
	}

	/**
	 * @param disIncNonSrvDetailRowId the disIncNonSrvDetailRowId to set
	 */
	public void setDisIncNonSrvDetailRowId(String[] disIncNonSrvDetailRowId) {
		this.disIncNonSrvDetailRowId = disIncNonSrvDetailRowId;
	}

	/**
	 * @return the disIncBothSrvRowId
	 */
	public String[] getDisIncBothSrvRowId() {
		return disIncBothSrvRowId;
	}

	/**
	 * @param disIncBothSrvRowId the disIncBothSrvRowId to set
	 */
	public void setDisIncBothSrvRowId(String[] disIncBothSrvRowId) {
		this.disIncBothSrvRowId = disIncBothSrvRowId;
	}

	/**
	 * @return the disIncBothSrvDetailRowId
	 */
	public String[] getDisIncBothSrvDetailRowId() {
		return disIncBothSrvDetailRowId;
	}

	/**
	 * @param disIncBothSrvDetailRowId the disIncBothSrvDetailRowId to set
	 */
	public void setDisIncBothSrvDetailRowId(String[] disIncBothSrvDetailRowId) {
		this.disIncBothSrvDetailRowId = disIncBothSrvDetailRowId;
	}

	/**
	 * @return the srvFormula
	 */
	public String[] getSrvFormula() {
		return srvFormula;
	}

	/**
	 * @param srvFormula the srvFormula to set
	 */
	public void setSrvFormula(String[] srvFormula) {
		this.srvFormula = srvFormula;
	}

	/**
	 * @return the nonSrvFormula
	 */
	public String[] getNonSrvFormula() {
		return nonSrvFormula;
	}

	/**
	 * @param nonSrvFormula the nonSrvFormula to set
	 */
	public void setNonSrvFormula(String[] nonSrvFormula) {
		this.nonSrvFormula = nonSrvFormula;
	}

	/**
	 * @return the bothSrvFormula
	 */
	public String[] getBothSrvFormula() {
		return bothSrvFormula;
	}

	/**
	 * @param bothSrvFormula the bothSrvFormula to set
	 */
	public void setBothSrvFormula(String[] bothSrvFormula) {
		this.bothSrvFormula = bothSrvFormula;
	}

	/**
	 * @return the srvBasis
	 */
	public String[] getSrvBasis() {
		return srvBasis;
	}

	/**
	 * @param srvBasis the srvBasis to set
	 */
	public void setSrvBasis(String[] srvBasis) {
		this.srvBasis = srvBasis;
	}

	/**
	 * @return the nonSrvBasis
	 */
	public String[] getNonSrvBasis() {
		return nonSrvBasis;
	}

	/**
	 * @param nonSrvBasis the nonSrvBasis to set
	 */
	public void setNonSrvBasis(String[] nonSrvBasis) {
		this.nonSrvBasis = nonSrvBasis;
	}

	/**
	 * @return the bothSrvBasis
	 */
	public String[] getBothSrvBasis() {
		return bothSrvBasis;
	}

	/**
	 * @param bothSrvBasis the bothSrvBasis to set
	 */
	public void setBothSrvBasis(String[] bothSrvBasis) {
		this.bothSrvBasis = bothSrvBasis;
	}
	/**
	 * @return the tempSrvBasis
	 */
	public String[] getTempSrvBasis() {
		return tempSrvBasis;
	}
	/**
	 * @param tempSrvBasis the tempSrvBasis to set
	 */
	public void setTempSrvBasis(String[] tempSrvBasis) {
		this.tempSrvBasis = tempSrvBasis;
	}
	/**
	 * @return the tempNonSrvBasis
	 */
	public String[] getTempNonSrvBasis() {
		return tempNonSrvBasis;
	}
	/**
	 * @param tempNonSrvBasis the tempNonSrvBasis to set
	 */
	public void setTempNonSrvBasis(String[] tempNonSrvBasis) {
		this.tempNonSrvBasis = tempNonSrvBasis;
	}
	/**
	 * @return the tempBothSrvBasis
	 */
	public String[] getTempBothSrvBasis() {
		return tempBothSrvBasis;
	}
	/**
	 * @param tempBothSrvBasis the tempBothSrvBasis to set
	 */
	public void setTempBothSrvBasis(String[] tempBothSrvBasis) {
		this.tempBothSrvBasis = tempBothSrvBasis;
	}

	/**
	 * @return the disIncSrvPercentage
	 */
	public Double[] getDisIncSrvPercentage() {
		return disIncSrvPercentage;
	}

	/**
	 * @param disIncSrvPercentage the disIncSrvPercentage to set
	 */
	public void setDisIncSrvPercentage(Double[] disIncSrvPercentage) {
		this.disIncSrvPercentage = disIncSrvPercentage;
	}

	/**
	 * @return the disIncNonSrvPercentage
	 */
	public Double[] getDisIncNonSrvPercentage() {
		return disIncNonSrvPercentage;
	}

	/**
	 * @param disIncNonSrvPercentage the disIncNonSrvPercentage to set
	 */
	public void setDisIncNonSrvPercentage(Double[] disIncNonSrvPercentage) {
		this.disIncNonSrvPercentage = disIncNonSrvPercentage;
	}

	/**
	 * @return the disIncBothSrvPercentage
	 */
	public Double[] getDisIncBothSrvPercentage() {
		return disIncBothSrvPercentage;
	}

	/**
	 * @param disIncBothSrvPercentage the disIncBothSrvPercentage to set
	 */
	public void setDisIncBothSrvPercentage(Double[] disIncBothSrvPercentage) {
		this.disIncBothSrvPercentage = disIncBothSrvPercentage;
	}

	/**
	 * @return the disIncSrvValidFrom
	 */
	public String[] getDisIncSrvValidFrom() {
		return disIncSrvValidFrom;
	}

	/**
	 * @param disIncSrvValidFrom the disIncSrvValidFrom to set
	 */
	public void setDisIncSrvValidFrom(String[] disIncSrvValidFrom) {
		this.disIncSrvValidFrom = disIncSrvValidFrom;
	}

	/**
	 * @return the disIncSrvValidTo
	 */
	public String[] getDisIncSrvValidTo() {
		return disIncSrvValidTo;
	}

	/**
	 * @param disIncSrvValidTo the disIncSrvValidTo to set
	 */
	public void setDisIncSrvValidTo(String[] disIncSrvValidTo) {
		this.disIncSrvValidTo = disIncSrvValidTo;
	}

	/**
	 * @return the disIncNonSrvValidFrom
	 */
	public String[] getDisIncNonSrvValidFrom() {
		return disIncNonSrvValidFrom;
	}

	/**
	 * @param disIncNonSrvValidFrom the disIncNonSrvValidFrom to set
	 */
	public void setDisIncNonSrvValidFrom(String[] disIncNonSrvValidFrom) {
		this.disIncNonSrvValidFrom = disIncNonSrvValidFrom;
	}

	/**
	 * @return the disIncNonSrvValidTo
	 */
	public String[] getDisIncNonSrvValidTo() {
		return disIncNonSrvValidTo;
	}

	/**
	 * @param disIncNonSrvValidTo the disIncNonSrvValidTo to set
	 */
	public void setDisIncNonSrvValidTo(String[] disIncNonSrvValidTo) {
		this.disIncNonSrvValidTo = disIncNonSrvValidTo;
	}

	/**
	 * @return the disIncBothSrvValidFrom
	 */
	public String[] getDisIncBothSrvValidFrom() {
		return disIncBothSrvValidFrom;
	}

	/**
	 * @param disIncBothSrvValidFrom the disIncBothSrvValidFrom to set
	 */
	public void setDisIncBothSrvValidFrom(String[] disIncBothSrvValidFrom) {
		this.disIncBothSrvValidFrom = disIncBothSrvValidFrom;
	}

	/**
	 * @return the disIncBothSrvValidTo
	 */
	public String[] getDisIncBothSrvValidTo() {
		return disIncBothSrvValidTo;
	}

	/**
	 * @param disIncBothSrvValidTo the disIncBothSrvValidTo to set
	 */
	public void setDisIncBothSrvValidTo(String[] disIncBothSrvValidTo) {
		this.disIncBothSrvValidTo = disIncBothSrvValidTo;
	}

	/**
	 * @return the srvExcFlag
	 */
	public String[] getSrvExcFlag() {
		return srvExcFlag;
	}

	/**
	 * @param srvExcFlag the srvExcFlag to set
	 */
	public void setSrvExcFlag(String[] srvExcFlag) {
		this.srvExcFlag = srvExcFlag;
	}

	/**
	 * @return the srvDisplayFormula
	 */
	public String[] getSrvDisplayFormula() {
		return srvDisplayFormula;
	}

	/**
	 * @param srvDisplayFormula the srvDisplayFormula to set
	 */
	public void setSrvDisplayFormula(String[] srvDisplayFormula) {
		this.srvDisplayFormula = srvDisplayFormula;
	}

	/**
	 * @return the nonSrvExcFlag
	 */
	public String[] getNonSrvExcFlag() {
		return nonSrvExcFlag;
	}

	/**
	 * @param nonSrvExcFlag the nonSrvExcFlag to set
	 */
	public void setNonSrvExcFlag(String[] nonSrvExcFlag) {
		this.nonSrvExcFlag = nonSrvExcFlag;
	}

	/**
	 * @return the nonSrvDisplayFormula
	 */
	public String[] getNonSrvDisplayFormula() {
		return nonSrvDisplayFormula;
	}

	/**
	 * @param nonSrvDisplayFormula the nonSrvDisplayFormula to set
	 */
	public void setNonSrvDisplayFormula(String[] nonSrvDisplayFormula) {
		this.nonSrvDisplayFormula = nonSrvDisplayFormula;
	}

	/**
	 * @return the bothSrvExcFlag
	 */
	public String[] getBothSrvExcFlag() {
		return bothSrvExcFlag;
	}

	/**
	 * @param bothSrvExcFlag the bothSrvExcFlag to set
	 */
	public void setBothSrvExcFlag(String[] bothSrvExcFlag) {
		this.bothSrvExcFlag = bothSrvExcFlag;
	}

	/**
	 * @return the bothSrvDisplayFormula
	 */
	public String[] getBothSrvDisplayFormula() {
		return bothSrvDisplayFormula;
	}

	/**
	 * @param bothSrvDisplayFormula the bothSrvDisplayFormula to set
	 */
	public void setBothSrvDisplayFormula(String[] bothSrvDisplayFormula) {
		this.bothSrvDisplayFormula = bothSrvDisplayFormula;
	}

	/**
	 * @return the disIncSrvParameter
	 */
	public String[] getDisIncSrvParameter() {
		return disIncSrvParameter;
	}

	/**
	 * @param disIncSrvParameter the disIncSrvParameter to set
	 */
	public void setDisIncSrvParameter(String[] disIncSrvParameter) {
		this.disIncSrvParameter = disIncSrvParameter;
	}

	/**
	 * @return the disIncNonSrvParameter
	 */
	public String[] getDisIncNonSrvParameter() {
		return disIncNonSrvParameter;
	}

	/**
	 * @param disIncNonSrvParameter the disIncNonSrvParameter to set
	 */
	public void setDisIncNonSrvParameter(String[] disIncNonSrvParameter) {
		this.disIncNonSrvParameter = disIncNonSrvParameter;
	}

	/**
	 * @return the disIncBothSrvParameter
	 */
	public String[] getDisIncBothSrvParameter() {
		return disIncBothSrvParameter;
	}

	/**
	 * @param disIncBothSrvParameter the disIncBothSrvParameter to set
	 */
	public void setDisIncBothSrvParameter(String[] disIncBothSrvParameter) {
		this.disIncBothSrvParameter = disIncBothSrvParameter;
	}

	/**
	 * @return the disIncSrvParameterType
	 */
	public String[] getDisIncSrvParameterType() {
		return disIncSrvParameterType;
	}

	/**
	 * @param disIncSrvParameterType the disIncSrvParameterType to set
	 */
	public void setDisIncSrvParameterType(String[] disIncSrvParameterType) {
		this.disIncSrvParameterType = disIncSrvParameterType;
	}

	/**
	 * @return the disIncSrvParameterCode
	 */
	public String[] getDisIncSrvParameterCode() {
		return disIncSrvParameterCode;
	}

	/**
	 * @param disIncSrvParameterCode the disIncSrvParameterCode to set
	 */
	public void setDisIncSrvParameterCode(String[] disIncSrvParameterCode) {
		this.disIncSrvParameterCode = disIncSrvParameterCode;
	}

	/**
	 * @return the disIncSrvParameterValue
	 */
	public String[] getDisIncSrvParameterValue() {
		return disIncSrvParameterValue;
	}

	/**
	 * @param disIncSrvParameterValue the disIncSrvParameterValue to set
	 */
	public void setDisIncSrvParameterValue(String[] disIncSrvParameterValue) {
		this.disIncSrvParameterValue = disIncSrvParameterValue;
	}

	/**
	 * @return the disIncNonSrvParameterType
	 */
	public String[] getDisIncNonSrvParameterType() {
		return disIncNonSrvParameterType;
	}

	/**
	 * @param disIncNonSrvParameterType the disIncNonSrvParameterType to set
	 */
	public void setDisIncNonSrvParameterType(String[] disIncNonSrvParameterType) {
		this.disIncNonSrvParameterType = disIncNonSrvParameterType;
	}

	/**
	 * @return the disIncNonSrvParameterCode
	 */
	public String[] getDisIncNonSrvParameterCode() {
		return disIncNonSrvParameterCode;
	}

	/**
	 * @param disIncNonSrvParameterCode the disIncNonSrvParameterCode to set
	 */
	public void setDisIncNonSrvParameterCode(String[] disIncNonSrvParameterCode) {
		this.disIncNonSrvParameterCode = disIncNonSrvParameterCode;
	}

	/**
	 * @return the disIncNonSrvParameterValue
	 */
	public String[] getDisIncNonSrvParameterValue() {
		return disIncNonSrvParameterValue;
	}

	/**
	 * @param disIncNonSrvParameterValue the disIncNonSrvParameterValue to set
	 */
	public void setDisIncNonSrvParameterValue(String[] disIncNonSrvParameterValue) {
		this.disIncNonSrvParameterValue = disIncNonSrvParameterValue;
	}

	/**
	 * @return the disIncBothSrvParameterType
	 */
	public String[] getDisIncBothSrvParameterType() {
		return disIncBothSrvParameterType;
	}

	/**
	 * @param disIncBothSrvParameterType the disIncBothSrvParameterType to set
	 */
	public void setDisIncBothSrvParameterType(String[] disIncBothSrvParameterType) {
		this.disIncBothSrvParameterType = disIncBothSrvParameterType;
	}

	/**
	 * @return the disIncBothSrvParameterCode
	 */
	public String[] getDisIncBothSrvParameterCode() {
		return disIncBothSrvParameterCode;
	}

	/**
	 * @param disIncBothSrvParameterCode the disIncBothSrvParameterCode to set
	 */
	public void setDisIncBothSrvParameterCode(String[] disIncBothSrvParameterCode) {
		this.disIncBothSrvParameterCode = disIncBothSrvParameterCode;
	}

	/**
	 * @return the disIncBothSrvParameterValue
	 */
	public String[] getDisIncBothSrvParameterValue() {
		return disIncBothSrvParameterValue;
	}

	/**
	 * @param disIncBothSrvParameterValue the disIncBothSrvParameterValue to set
	 */
	public void setDisIncBothSrvParameterValue(String[] disIncBothSrvParameterValue) {
		this.disIncBothSrvParameterValue = disIncBothSrvParameterValue;
	}

	/**
	 * @return the srvDisplayParameter
	 */
	public String[] getSrvDisplayParameter() {
		return srvDisplayParameter;
	}

	/**
	 * @param srvDisplayParameter the srvDisplayParameter to set
	 */
	public void setSrvDisplayParameter(String[] srvDisplayParameter) {
		this.srvDisplayParameter = srvDisplayParameter;
	}

	/**
	 * @return the nonSrvDisplayParameter
	 */
	public String[] getNonSrvDisplayParameter() {
		return nonSrvDisplayParameter;
	}

	/**
	 * @param nonSrvDisplayParameter the nonSrvDisplayParameter to set
	 */
	public void setNonSrvDisplayParameter(String[] nonSrvDisplayParameter) {
		this.nonSrvDisplayParameter = nonSrvDisplayParameter;
	}

	/**
	 * @return the bothSrvDisplayParameter
	 */
	public String[] getBothSrvDisplayParameter() {
		return bothSrvDisplayParameter;
	}

	/**
	 * @param bothSrvDisplayParameter the bothSrvDisplayParameter to set
	 */
	public void setBothSrvDisplayParameter(String[] bothSrvDisplayParameter) {
		this.bothSrvDisplayParameter = bothSrvDisplayParameter;
	}
	public String[] getClmGenDate() {
		return clmGenDate;
	}
	public void setClmGenDate(String[] clmGenDate) {
		this.clmGenDate = clmGenDate;
	}

	/**
	 * 	Getter for postalPeriod
	 *	Added by : A-5219 on 21-Jun-2020
	 * 	Used for :
	 */
	public String getPostalPeriod() {
		return postalPeriod;
	}

	/**
	 *  @param postalPeriod the postalPeriod to set
	 * 	Setter for postalPeriod
	 *	Added by : A-5219 on 21-Jun-2020
	 * 	Used for :
	 */
	public void setPostalPeriod(String postalPeriod) {
		this.postalPeriod = postalPeriod;
	}

	/**
	 * 	Getter for postalFromDate
	 *	Added by : A-5219 on 21-Jun-2020
	 * 	Used for :
	 */
	public String getPostalFromDate() {
		return postalFromDate;
	}

	/**
	 *  @param postalFromDate the postalFromDate to set
	 * 	Setter for postalFromDate
	 *	Added by : A-5219 on 21-Jun-2020
	 * 	Used for :
	 */
	public void setPostalFromDate(String postalFromDate) {
		this.postalFromDate = postalFromDate;
	}

	/**
	 * 	Getter for postalToDate
	 *	Added by : A-5219 on 21-Jun-2020
	 * 	Used for :
	 */
	public String getPostalToDate() {
		return postalToDate;
	}

	/**
	 *  @param postalToDate the postalToDate to set
	 * 	Setter for postalToDate
	 *	Added by : A-5219 on 21-Jun-2020
	 * 	Used for :
	 */
	public void setPostalToDate(String postalToDate) {
		this.postalToDate = postalToDate;
	}

	/**
	 * 	Getter for postalDiscEftDate
	 *	Added by : A-5219 on 21-Jun-2020
	 * 	Used for :
	 */
	public String getPostalDiscEftDate() {
		return postalDiscEftDate;
	}

	/**
	 *  @param postalDiscEftDate the postalDiscEftDate to set
	 * 	Setter for postalDiscEftDate
	 *	Added by : A-5219 on 21-Jun-2020
	 * 	Used for :
	 */
	public void setPostalDiscEftDate(String postalDiscEftDate) {
		this.postalDiscEftDate = postalDiscEftDate;
	}

	/**
	 * 	Getter for postalIncCalcDate
	 *	Added by : A-5219 on 21-Jun-2020
	 * 	Used for :
	 */
	public String getPostalIncCalcDate() {
		return postalIncCalcDate;
	}

	/**
	 *  @param postalIncCalcDate the postalIncCalcDate to set
	 * 	Setter for postalIncCalcDate
	 *	Added by : A-5219 on 21-Jun-2020
	 * 	Used for :
	 */
	public void setPostalIncCalcDate(String postalIncCalcDate) {
		this.postalIncCalcDate = postalIncCalcDate;
	}

	/**
	 * 	Getter for postalIncEftDate
	 *	Added by : A-5219 on 21-Jun-2020
	 * 	Used for :
	 */
	public String getPostalIncEftDate() {
		return postalIncEftDate;
	}

	/**
	 *  @param postalIncEftDate the postalIncEftDate to set
	 * 	Setter for postalIncEftDate
	 *	Added by : A-5219 on 21-Jun-2020
	 * 	Used for :
	 */
	public void setPostalIncEftDate(String postalIncEftDate) {
		this.postalIncEftDate = postalIncEftDate;
	}

	/**
	 * 	Getter for postalIncRecvDate
	 *	Added by : A-5219 on 21-Jun-2020
	 * 	Used for :
	 */
	public String getPostalIncRecvDate() {
		return postalIncRecvDate;
	}

	/**
	 *  @param postalIncRecvDate the postalIncRecvDate to set
	 * 	Setter for postalIncRecvDate
	 *	Added by : A-5219 on 21-Jun-2020
	 * 	Used for :
	 */
	public void setPostalIncRecvDate(String postalIncRecvDate) {
		this.postalIncRecvDate = postalIncRecvDate;
	}

	/**
	 * 	Getter for postalClaimGenDate
	 *	Added by : A-5219 on 21-Jun-2020
	 * 	Used for :
	 */
	public String getPostalClaimGenDate() {
		return postalClaimGenDate;
	}

	/**
	 *  @param postalClaimGenDate the postalClaimGenDate to set
	 * 	Setter for postalClaimGenDate
	 *	Added by : A-5219 on 21-Jun-2020
	 * 	Used for :
	 */
	public void setPostalClaimGenDate(String postalClaimGenDate) {
		this.postalClaimGenDate = postalClaimGenDate;
	}

	/**
	 * 	Getter for postalCalendarAction
	 *	Added by : A-5219 on 22-Jun-2020
	 * 	Used for :
	 */
	public String getPostalCalendarAction() {
		return postalCalendarAction;
	}

	/**
	 *  @param postalCalendarAction the postalCalendarAction to set
	 * 	Setter for postalCalendarAction
	 *	Added by : A-5219 on 22-Jun-2020
	 * 	Used for :
	 */
	public void setPostalCalendarAction(String postalCalendarAction) {
		this.postalCalendarAction = postalCalendarAction;
	}

	/**
	 * 	Getter for selectedrow
	 *	Added by : A-5219 on 22-Jun-2020
	 * 	Used for :
	 */
	public int getSelectedRow() {
		return selectedRow;
	}

	/**
	 *  @param selectedrow the selectedrow to set
	 * 	Setter for selectedrow
	 *	Added by : A-5219 on 22-Jun-2020
	 * 	Used for :
	 */
	public void setSelectedRow(int selectedRow) {
		this.selectedRow = selectedRow;
	}

	/**
	 * 	Getter for selectedIndex
	 *	Added by : A-5219 on 22-Jun-2020
	 * 	Used for :
	 */
	public String[] getSelectedIndex() {
		return selectedIndex;
	}

	/**
	 *  @param selectedIndex the selectedIndex to set
	 * 	Setter for selectedIndex
	 *	Added by : A-5219 on 22-Jun-2020
	 * 	Used for :
	 */
	public void setSelectedIndex(String[] selectedIndex) {
		this.selectedIndex = selectedIndex;
	}
	/**
	 * @return the hoMailSubClass
	 */
	public String getHoMailSubClass() {
		return hoMailSubClass;
	}
	/**
	 * @param hoMailSubClass the hoMailSubClass to set
	 */
	public void setHoMailSubClass(String hoMailSubClass) {
		this.hoMailSubClass = hoMailSubClass;
	}
	/**
	 * @return the exchangeOffice
	 */
	public String[] getExchangeOffice() {
		return exchangeOffice;
	}
	/**
	 * @param exchangeOffice the exchangeOffice to set
	 */
	public void setExchangeOffice(String[] exchangeOffice) {
		this.exchangeOffice = exchangeOffice;
	}
	/**
	 * @return the mailSubClass
	 */
	public String[] getMailSubClass() {
		return mailSubClass;
	}
	/**
	 * @param mailSubClass the mailSubClass to set
	 */
	public void setMailSubClass(String[] mailSubClass) {
		this.mailSubClass = mailSubClass;
	}
	/**
	 * @return the regions
	 */
	public String[] getRegions() {
		return regions;
	}
	/**
	 * @param regions the regions to set
	 */
	public void setRegions(String[] regions) {
		this.regions = regions;
	}
	/**
	 * @return the region
	 */
	public String getRegion() {
		return region;
	}
	/**
	 * @param region the region to set
	 */
	public void setRegion(String region) {
		this.region = region;
	}

}
