package com.ibsplc.icargo.presentation.web.model.mail.operations;

import java.util.Collection;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.vo.UnitRoundingVO;
import com.ibsplc.icargo.framework.web.json.PageResult;
import com.ibsplc.icargo.framework.web.spring.model.AbstractScreenModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.Consignment;


public class MaintainConsignmentModel extends AbstractScreenModel {
	
	/*
	 * The constant variable for product mail
	 */
	private static final String PRODUCT = "mail";
	/*
	 * The constant for sub product operations
	 */
	private static final String SUBPRODUCT = "operations";
	/*
	 * The constant for screen id
	 */
	private static final String SCREENID = "mail.operations.ux.consignment";

	private String conDocNo;
	private String paCode;
	private String direction;
	private String conDate;
	private String type;
	private String subType;
	private String[] selectRoute;
	private String flightNo;
	private String[] flightCarrierCode;
	private String[] flightNumber;
	private String[] depDate;
	private String[] pou;
	private String[] pol;
	private String remarks;
	private String[] selectMail;
	private String[] originOE;
	private String[] destinationOE;
	private String[] category;
	private String[] mailClass;
	private String[] subClass;
	private String[] year;
	private String[] dsn;
	private String[] rsn;
	private String[] numBags;
	private String[] mailHI;
	private String[] mailRI;
	private String[] weight;
	private Measure[] weightMeasure;
	private String[] uldNum;
	private String[] mailbagId;
	private String closeFlag;
	private String initialFocus;
	private String tableFocus;
	private String fromScreen;
	private String disableListSuccess;
	private String currentDialogOption;
	private String currentDialogId;
	private String[] routeOpFlag;
	private String[] mailOpFlag;
	private String duplicateFlightStatus;
	private String displayPage = "1";
	private String lastPageNum = "0";
	private String countTotalFlag;
	private String fromPopupflg;
	private String afterPopupSaveFlag;
	private String newRoutingFlag;
	private String[] declaredValue;
	private String reportFlag;
	private String mailOOE;
	private String mailDOE;
	private String mailCat;
	private String mailSC;
	private String mailYr;
	private String mailDSN;
	private String mailRSN;
	private String hni;
	private String ri;
	private String mailWt;
	private Measure mailWtMeasure;
	private String mailId;
	private String orginOfficeOfExchange;
	private String destOfficeOfExchange;
	private String mailCategory;
	private String mailClassType;
	private String mailSubClass;
	private String mailYear;
	private String mailDsn;
	private String highestNumberIndicator;
	private String registeredIndicator;
	private String rsnRangeFrom;
	private String rsnRangeTo;
	private String totalBags;
	private String actionName;
	private String displayPopupPage = "1";
	private String totalViewRecords = "0";
	private String lastPopupPageNum = "0";
	private String defWeightUnit;
	private int totalReceptacles;
	private String[] currencyCode;
	private Collection<OneTimeVO> oneTimeCat;
	private Collection<OneTimeVO> oneTimeRSN;
	private Collection<OneTimeVO> oneTimeHNI;
	private Collection<OneTimeVO> oneTimeMailClass;
	private Collection<OneTimeVO> oneTimeType;
	private Collection<OneTimeVO> oneTimeSubType;
	private Collection<OneTimeVO> oneTimeFlightType;
	private Collection<OneTimeVO> oneTimeMailServiceLevel;
	private Consignment consignment;
	private String screenStatusFlag;
	private Integer totalRecords;
	private UnitRoundingVO weightRoundingVO;
	private String[] orgDestAirCodes;
	private Collection<OneTimeVO> oneTimeTransportStageQualifier;
	
	private PageResult<Consignment> mailBagDetails;	
	
	private static final int maxPageLimit = 25;
	private int pageSize = 25;

	@Override
	public String getProduct() {
		return PRODUCT;
	}

	public Collection<OneTimeVO> getOneTimeMailServiceLevel() {
		return oneTimeMailServiceLevel;
	}

	public void setOneTimeMailServiceLevel(Collection<OneTimeVO> oneTimeMailServiceLevel) {
		this.oneTimeMailServiceLevel = oneTimeMailServiceLevel;
	}

	@Override
	public String getScreenId() {
		return SCREENID;
	}

	@Override
	public String getSubProduct() {
		return SUBPRODUCT;
	}

	public String getConDocNo() {
		return conDocNo;
	}

	public void setConDocNo(String conDocNo) {
		this.conDocNo = conDocNo;
	}

	public String getPaCode() {
		return paCode;
	}

	public void setPaCode(String paCode) {
		this.paCode = paCode;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getConDate() {
		return conDate;
	}

	public void setConDate(String conDate) {
		this.conDate = conDate;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String[] getSelectRoute() {
		return selectRoute;
	}

	public void setSelectRoute(String[] selectRoute) {
		this.selectRoute = selectRoute;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public String[] getFlightCarrierCode() {
		return flightCarrierCode;
	}

	public void setFlightCarrierCode(String[] flightCarrierCode) {
		this.flightCarrierCode = flightCarrierCode;
	}

	public String[] getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String[] flightNumber) {
		this.flightNumber = flightNumber;
	}

	public String[] getDepDate() {
		return depDate;
	}

	public void setDepDate(String[] depDate) {
		this.depDate = depDate;
	}

	public String[] getPou() {
		return pou;
	}

	public void setPou(String[] pou) {
		this.pou = pou;
	}

	public String[] getPol() {
		return pol;
	}

	public void setPol(String[] pol) {
		this.pol = pol;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String[] getSelectMail() {
		return selectMail;
	}

	public void setSelectMail(String[] selectMail) {
		this.selectMail = selectMail;
	}

	public String[] getOriginOE() {
		return originOE;
	}

	public void setOriginOE(String[] originOE) {
		this.originOE = originOE;
	}

	public String[] getDestinationOE() {
		return destinationOE;
	}

	public void setDestinationOE(String[] destinationOE) {
		this.destinationOE = destinationOE;
	}

	public String[] getCategory() {
		return category;
	}

	public void setCategory(String[] category) {
		this.category = category;
	}

	public String[] getMailClass() {
		return mailClass;
	}

	public void setMailClass(String[] mailClass) {
		this.mailClass = mailClass;
	}

	public String[] getSubClass() {
		return subClass;
	}

	public void setSubClass(String[] subClass) {
		this.subClass = subClass;
	}

	public String[] getYear() {
		return year;
	}

	public void setYear(String[] year) {
		this.year = year;
	}

	public String[] getDsn() {
		return dsn;
	}

	public void setDsn(String[] dsn) {
		this.dsn = dsn;
	}

	public String[] getRsn() {
		return rsn;
	}

	public void setRsn(String[] rsn) {
		this.rsn = rsn;
	}

	public String[] getNumBags() {
		return numBags;
	}

	public void setNumBags(String[] numBags) {
		this.numBags = numBags;
	}

	public String[] getMailHI() {
		return mailHI;
	}

	public void setMailHI(String[] mailHI) {
		this.mailHI = mailHI;
	}

	public String[] getMailRI() {
		return mailRI;
	}

	public void setMailRI(String[] mailRI) {
		this.mailRI = mailRI;
	}

	public String[] getWeight() {
		return weight;
	}

	public void setWeight(String[] weight) {
		this.weight = weight;
	}

	public Measure[] getWeightMeasure() {
		return weightMeasure;
	}

	public void setWeightMeasure(Measure[] weightMeasure) {
		this.weightMeasure = weightMeasure;
	}

	public String[] getUldNum() {
		return uldNum;
	}

	public void setUldNum(String[] uldNum) {
		this.uldNum = uldNum;
	}

	public String[] getMailbagId() {
		return mailbagId;
	}

	public void setMailbagId(String[] mailbagId) {
		this.mailbagId = mailbagId;
	}

	public String getCloseFlag() {
		return closeFlag;
	}

	public void setCloseFlag(String closeFlag) {
		this.closeFlag = closeFlag;
	}

	public String getInitialFocus() {
		return initialFocus;
	}

	public void setInitialFocus(String initialFocus) {
		this.initialFocus = initialFocus;
	}

	public String getTableFocus() {
		return tableFocus;
	}

	public void setTableFocus(String tableFocus) {
		this.tableFocus = tableFocus;
	}

	public String getFromScreen() {
		return fromScreen;
	}

	public void setFromScreen(String fromScreen) {
		this.fromScreen = fromScreen;
	}

	public String getDisableListSuccess() {
		return disableListSuccess;
	}

	public void setDisableListSuccess(String disableListSuccess) {
		this.disableListSuccess = disableListSuccess;
	}

	public String getCurrentDialogOption() {
		return currentDialogOption;
	}

	public void setCurrentDialogOption(String currentDialogOption) {
		this.currentDialogOption = currentDialogOption;
	}

	public String getCurrentDialogId() {
		return currentDialogId;
	}

	public void setCurrentDialogId(String currentDialogId) {
		this.currentDialogId = currentDialogId;
	}

	public String[] getRouteOpFlag() {
		return routeOpFlag;
	}

	public void setRouteOpFlag(String[] routeOpFlag) {
		this.routeOpFlag = routeOpFlag;
	}

	public String[] getMailOpFlag() {
		return mailOpFlag;
	}

	public void setMailOpFlag(String[] mailOpFlag) {
		this.mailOpFlag = mailOpFlag;
	}

	public String getDuplicateFlightStatus() {
		return duplicateFlightStatus;
	}

	public void setDuplicateFlightStatus(String duplicateFlightStatus) {
		this.duplicateFlightStatus = duplicateFlightStatus;
	}

	public String getDisplayPage() {
		return displayPage;
	}

	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}

	public String getLastPageNum() {
		return lastPageNum;
	}

	public void setLastPageNum(String lastPageNum) {
		this.lastPageNum = lastPageNum;
	}

	public String getCountTotalFlag() {
		return countTotalFlag;
	}

	public void setCountTotalFlag(String countTotalFlag) {
		this.countTotalFlag = countTotalFlag;
	}

	public String getFromPopupflg() {
		return fromPopupflg;
	}

	public void setFromPopupflg(String fromPopupflg) {
		this.fromPopupflg = fromPopupflg;
	}

	public String getAfterPopupSaveFlag() {
		return afterPopupSaveFlag;
	}

	public void setAfterPopupSaveFlag(String afterPopupSaveFlag) {
		this.afterPopupSaveFlag = afterPopupSaveFlag;
	}

	public String getNewRoutingFlag() {
		return newRoutingFlag;
	}

	public void setNewRoutingFlag(String newRoutingFlag) {
		this.newRoutingFlag = newRoutingFlag;
	}

	public String[] getDeclaredValue() {
		return declaredValue;
	}

	public void setDeclaredValue(String[] declaredValue) {
		this.declaredValue = declaredValue;
	}

	public String getReportFlag() {
		return reportFlag;
	}

	public void setReportFlag(String reportFlag) {
		this.reportFlag = reportFlag;
	}

	public String getMailOOE() {
		return mailOOE;
	}

	public void setMailOOE(String mailOOE) {
		this.mailOOE = mailOOE;
	}

	public String getMailDOE() {
		return mailDOE;
	}

	public void setMailDOE(String mailDOE) {
		this.mailDOE = mailDOE;
	}

	public String getMailCat() {
		return mailCat;
	}

	public void setMailCat(String mailCat) {
		this.mailCat = mailCat;
	}

	public String getMailSC() {
		return mailSC;
	}

	public void setMailSC(String mailSC) {
		this.mailSC = mailSC;
	}

	public String getMailYr() {
		return mailYr;
	}

	public void setMailYr(String mailYr) {
		this.mailYr = mailYr;
	}

	public String getMailDSN() {
		return mailDSN;
	}

	public void setMailDSN(String mailDSN) {
		this.mailDSN = mailDSN;
	}

	public String getMailRSN() {
		return mailRSN;
	}

	public void setMailRSN(String mailRSN) {
		this.mailRSN = mailRSN;
	}

	public String getHni() {
		return hni;
	}

	public void setHni(String hni) {
		this.hni = hni;
	}

	public String getRi() {
		return ri;
	}

	public void setRi(String ri) {
		this.ri = ri;
	}

	public String getMailWt() {
		return mailWt;
	}

	public void setMailWt(String mailWt) {
		this.mailWt = mailWt;
	}

	public Measure getMailWtMeasure() {
		return mailWtMeasure;
	}

	public void setMailWtMeasure(Measure mailWtMeasure) {
		this.mailWtMeasure = mailWtMeasure;
	}

	public String getMailId() {
		return mailId;
	}

	public void setMailId(String mailId) {
		this.mailId = mailId;
	}

	public String getOrginOfficeOfExchange() {
		return orginOfficeOfExchange;
	}

	public void setOrginOfficeOfExchange(String orginOfficeOfExchange) {
		this.orginOfficeOfExchange = orginOfficeOfExchange;
	}

	public String getDestOfficeOfExchange() {
		return destOfficeOfExchange;
	}

	public void setDestOfficeOfExchange(String destOfficeOfExchange) {
		this.destOfficeOfExchange = destOfficeOfExchange;
	}

	public String getMailCategory() {
		return mailCategory;
	}

	public void setMailCategory(String mailCategory) {
		this.mailCategory = mailCategory;
	}

	public String getMailClassType() {
		return mailClassType;
	}

	public void setMailClassType(String mailClassType) {
		this.mailClassType = mailClassType;
	}

	public String getMailSubClass() {
		return mailSubClass;
	}

	public void setMailSubClass(String mailSubClass) {
		this.mailSubClass = mailSubClass;
	}

	public String getMailYear() {
		return mailYear;
	}

	public void setMailYear(String mailYear) {
		this.mailYear = mailYear;
	}

	public String getMailDsn() {
		return mailDsn;
	}

	public void setMailDsn(String mailDsn) {
		this.mailDsn = mailDsn;
	}

	public String getHighestNumberIndicator() {
		return highestNumberIndicator;
	}

	public void setHighestNumberIndicator(String highestNumberIndicator) {
		this.highestNumberIndicator = highestNumberIndicator;
	}

	public String getRegisteredIndicator() {
		return registeredIndicator;
	}

	public void setRegisteredIndicator(String registeredIndicator) {
		this.registeredIndicator = registeredIndicator;
	}

	public String getRsnRangeFrom() {
		return rsnRangeFrom;
	}

	public void setRsnRangeFrom(String rsnRangeFrom) {
		this.rsnRangeFrom = rsnRangeFrom;
	}

	public String getRsnRangeTo() {
		return rsnRangeTo;
	}

	public void setRsnRangeTo(String rsnRangeTo) {
		this.rsnRangeTo = rsnRangeTo;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getTotalBags() {
		return totalBags;
	}

	public void setTotalBags(String totalBags) {
		this.totalBags = totalBags;
	}

	public String getDisplayPopupPage() {
		return displayPopupPage;
	}

	public void setDisplayPopupPage(String displayPopupPage) {
		this.displayPopupPage = displayPopupPage;
	}

	public String getTotalViewRecords() {
		return totalViewRecords;
	}

	public void setTotalViewRecords(String totalViewRecords) {
		this.totalViewRecords = totalViewRecords;
	}

	public String getLastPopupPageNum() {
		return lastPopupPageNum;
	}

	public void setLastPopupPageNum(String lastPopupPageNum) {
		this.lastPopupPageNum = lastPopupPageNum;
	}

	public int getTotalReceptacles() {
		return totalReceptacles;
	}

	public void setTotalReceptacles(int totalReceptacles) {
		this.totalReceptacles = totalReceptacles;
	}

	public String[] getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String[] currencyCode) {
		this.currencyCode = currencyCode;
	}

	public Collection<OneTimeVO> getOneTimeCat() {
		return oneTimeCat;
	}

	public void setOneTimeCat(Collection<OneTimeVO> oneTimeCat) {
		this.oneTimeCat = oneTimeCat;
	}

	public Collection<OneTimeVO> getOneTimeRSN() {
		return oneTimeRSN;
	}

	public void setOneTimeRSN(Collection<OneTimeVO> oneTimeRSN) {
		this.oneTimeRSN = oneTimeRSN;
	}

	public Collection<OneTimeVO> getOneTimeHNI() {
		return oneTimeHNI;
	}

	public void setOneTimeHNI(Collection<OneTimeVO> oneTimeHNI) {
		this.oneTimeHNI = oneTimeHNI;
	}

	public Collection<OneTimeVO> getOneTimeMailClass() {
		return oneTimeMailClass;
	}

	public void setOneTimeMailClass(Collection<OneTimeVO> oneTimeMailClass) {
		this.oneTimeMailClass = oneTimeMailClass;
	}

	public Collection<OneTimeVO> getOneTimeType() {
		return oneTimeType;
	}

	public void setOneTimeType(Collection<OneTimeVO> oneTimeType) {
		this.oneTimeType = oneTimeType;
	}

	public Collection<OneTimeVO> getOneTimeSubType() {
		return oneTimeSubType;
	}

	public void setOneTimeSubType(Collection<OneTimeVO> oneTimeSubType) {
		this.oneTimeSubType = oneTimeSubType;
	}

	public Consignment getConsignment() {
		return consignment;
	}

	public void setConsignment(Consignment consignment) {
		this.consignment = consignment;
	}

	public UnitRoundingVO getWeightRoundingVO() {
		return weightRoundingVO;
	}

	public void setWeightRoundingVO(UnitRoundingVO weightRoundingVO) {
		this.weightRoundingVO = weightRoundingVO;
	}

	public String getScreenStatusFlag() {
		return screenStatusFlag;
	}

	public void setScreenStatusFlag(String screenStatusFlag) {
		this.screenStatusFlag = screenStatusFlag;
	}

	public Integer getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(Integer totalRecords) {
		this.totalRecords = totalRecords;
	}

	public Collection<OneTimeVO> getOneTimeFlightType() {
		return oneTimeFlightType;
	}

	public void setOneTimeFlightType(Collection<OneTimeVO> oneTimeFlightType) {
		this.oneTimeFlightType = oneTimeFlightType;
	}

	public PageResult<Consignment> getMailBagDetails() {
		return mailBagDetails;
	}

	public void setMailBagDetails(PageResult<Consignment> mailBagDetails) {
		this.mailBagDetails = mailBagDetails;
	}
	public String getDefWeightUnit() {
		return defWeightUnit;
	}
	public void setDefWeightUnit(String defWeightUnit) {
		this.defWeightUnit = defWeightUnit;
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String[] getOrgDestAirCodes() {
		return orgDestAirCodes;
	}
	public void setOrgDestAirCodes(String[] orgDestAirCodes) {
		this.orgDestAirCodes = orgDestAirCodes;
	}
	
	public Collection<OneTimeVO> getOneTimeTransportStageQualifier() {
		return oneTimeTransportStageQualifier;
	}

	public void setOneTimeTransportStageQualifier(Collection<OneTimeVO> oneTimeTransportStageQualifier) {
		this.oneTimeTransportStageQualifier = oneTimeTransportStageQualifier;
	}
}
