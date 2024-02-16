/*
 ConsignmentForm.java Created on Jul 15, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.operations;



import com.ibsplc.icargo.framework.model.ScreenModel;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.MeasureAnnotation;

/**
 * @author A-1876
 *
 */
public class ConsignmentForm extends ScreenModel {

	private static final String SCREEN_ID = "mailtracking.defaults.consignment";
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "operations";
	private static final String BUNDLE = "consignmentResources";

	
	private String conDocNo;
	private String paCode;
	private String direction;
	private String conDate;
	private String type;
	//Added as part of CR ICRD-103713 by A-5526
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
	
	@MeasureAnnotation(mappedValue="weightMeasure",unit="weightUnit",unitType="MWT")
	private String[] weight;
	private Measure[] weightMeasure;	
	private String[] weightUnit;//added by A_8353 for ICRD-274933 
	

	private String[] uldNum;
	private String[] mailbagId;//Added for ICRD-205027
	
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
	
	private String displayPage="1";
	
	private String lastPageNum="0";
	
	private String countTotalFlag;
	
	private String fromPopupflg;
	
	private String afterPopupSaveFlag;
	
	private String newRoutingFlag;
	private String[] declaredValue;
	private String reportFlag;
	//Added for ICRD-205027 starts 
	private String mailOOE;
	private String mailDOE;
	private String mailCat;
	private String mailSC;
	private String mailYr;
	private String mailDSN;
	private String mailRSN;
	private String hni;
	private String ri;
	
	@MeasureAnnotation(mappedValue="mailWtMeasure",unitType="MWT")
	private String mailWt;
	private Measure mailWtMeasure;	
	

	private String mailId;
	//Added for ICRD-205027 ends
  //added by a7531 for icrd 192557
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
    
    private String printMailTag;//added by a-7871 for ICRD-234913
    private String selectedIndexes;//added by a-7871 for ICRD-234913
    private String  defWeightUnit;//added by A_8353 for ICRD-274933 
	
    
	 public String getSelectedIndexes() {
		return selectedIndexes;
	}

	public void setSelectedIndexes(String selectedIndexes) {
		this.selectedIndexes = selectedIndexes;
	}

	public String getPrintMailTag() {
		return printMailTag;
	}

	public void setPrintMailTag(String printMailTag) {
		this.printMailTag = printMailTag;
	}

	private int totalReceptacles;//added by a7531 for icrd 192557
	 
	 
    /**
	 * @return the weightMeasure
	 */
	public Measure[] getWeightMeasure() {
		return weightMeasure;
	}

	/**
	 * @param weightMeasure the mailWtMeasure to set
	 */
	public void setWeightMeasure(Measure[] weightMeasure) {
		this.weightMeasure = weightMeasure;
	}
	/**
	 * @return the mailWtMeasure
	 */
	public Measure getMailWtMeasure() {
		return mailWtMeasure;
	}

	/**
	 * @param mailWtMeasure the mailWtMeasure to set
	 */
	public void setMailWtMeasure(Measure mailWtMeasure) {
		this.mailWtMeasure = mailWtMeasure;
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

	public String getTotalBags() {
		return totalBags;
	}

	public void setTotalBags(String totalBags) {
		this.totalBags = totalBags;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
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
	
	
	

	/**
	 * @return the declaredValue
	 */
	public String[] getDeclaredValue() {
		return declaredValue;
	}

	/**
	 * @param declaredValue the declaredValue to set
	 */
	public void setDeclaredValue(String[] declaredValue) {
		this.declaredValue = declaredValue;
	}

	/**
	 * @return the currencyCode
	 */
	public String[] getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * @param currencyCode the currencyCode to set
	 */
	public void setCurrencyCode(String[] currencyCode) {
		this.currencyCode = currencyCode;
	}
  
	 private String[] currencyCode;
		
	 private static final int maxPageLimit = 150;

	/**
	 * @return the countTotalFlag
	 */
	public String getCountTotalFlag() {
		return countTotalFlag;
	}

	/**
	 * @param countTotalFlag the countTotalFlag to set
	 */
	public void setCountTotalFlag(String countTotalFlag) {
		this.countTotalFlag = countTotalFlag;
	}

	public String getDuplicateFlightStatus() {
		return duplicateFlightStatus;
	}

	public void setDuplicateFlightStatus(String duplicateFlightStatus) {
		this.duplicateFlightStatus = duplicateFlightStatus;
	}

	/**
	 * @return Returns the currentDialogId.
	 */
	public String getCurrentDialogId() {
		return currentDialogId;
	}

	/**
	 * @param currentDialogId The currentDialogId to set.
	 */
	public void setCurrentDialogId(String currentDialogId) {
		this.currentDialogId = currentDialogId;
	}

	/**
	 * @return Returns the currentDialogOption.
	 */
	public String getCurrentDialogOption() {
		return currentDialogOption;
	}

	/**
	 * @param currentDialogOption The currentDialogOption to set.
	 */
	public void setCurrentDialogOption(String currentDialogOption) {
		this.currentDialogOption = currentDialogOption;
	}
		

	/**
	 * @return Returns the flightCarrierCode.
	 */
	public String[] getFlightCarrierCode() {
		return flightCarrierCode;
	}

	/**
	 * @param flightCarrierCode The flightCarrierCode to set.
	 */
	public void setFlightCarrierCode(String[] flightCarrierCode) {
		this.flightCarrierCode = flightCarrierCode;
	}

	/**
	 * @return Returns the flightNumber.
	 */
	public String[] getFlightNumber() {
		return flightNumber;
	}

	/**
	 * @param flightNumber The flightNumber to set.
	 */
	public void setFlightNumber(String[] flightNumber) {
		this.flightNumber = flightNumber;
	}

	/**
     * @return SCREEN_ID - String
     */
    public String getScreenId() {
        return SCREEN_ID;
    }

    /**
     * @return PRODUCT_NAME - String
     */
    public String getProduct() {
        return PRODUCT_NAME;
    }

    /**
     * @return SUBPRODUCT_NAME - String
     */
    public String getSubProduct() {
        return SUBPRODUCT_NAME;
    }

	/**
	 * @return Returns the bundle.
	 */
	public String getBundle() {
		return BUNDLE;
	}

	/**
	 * @return Returns the closeFlag.
	 */
	public String getCloseFlag() {
		return this.closeFlag;
	}

	/**
	 * @param closeFlag The closeFlag to set.
	 */
	public void setCloseFlag(String closeFlag) {
		this.closeFlag = closeFlag;
	}
	
	/**
	 * @return Returns the flightNo.
	 */
	public String getFlightNo() {
		return this.flightNo;
	}

	/**
	 * @param flightNo The flightNo to set.
	 */
	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	/**
	 * @return Returns the category.
	 */
	public String[] getCategory() {
		return this.category;
	}

	/**
	 * @param category The category to set.
	 */
	public void setCategory(String[] category) {
		this.category = category;
	}

	/**
	 * @return Returns the conDate.
	 */
	public String getConDate() {
		return this.conDate;
	}

	/**
	 * @param conDate The conDate to set.
	 */
	public void setConDate(String conDate) {
		this.conDate = conDate;
	}

	/**
	 * @return Returns the conDocNo.
	 */
	public String getConDocNo() {
		return this.conDocNo;
	}

	/**
	 * @param conDocNo The conDocNo to set.
	 */
	public void setConDocNo(String conDocNo) {
		this.conDocNo = conDocNo;
	}

	/**
	 * @return Returns the depDate.
	 */
	public String[] getDepDate() {
		return this.depDate;
	}

	/**
	 * @param depDate The depDate to set.
	 */
	public void setDepDate(String[] depDate) {
		this.depDate = depDate;
	}

	/**
	 * @return Returns the destinationOE.
	 */
	public String[] getDestinationOE() {
		return this.destinationOE;
	}

	/**
	 * @param destinationOE The destinationOE to set.
	 */
	public void setDestinationOE(String[] destinationOE) {
		this.destinationOE = destinationOE;
	}

	/**
	 * @return Returns the direction.
	 */
	public String getDirection() {
		return this.direction;
	}

	/**
	 * @param direction The direction to set.
	 */
	public void setDirection(String direction) {
		this.direction = direction;
	}

	/**
	 * @return Returns the dsn.
	 */
	public String[] getDsn() {
		return this.dsn;
	}

	/**
	 * @param dsn The dsn to set.
	 */
	public void setDsn(String[] dsn) {
		this.dsn = dsn;
	}

	/**
	 * @return Returns the fromScreen.
	 */
	public String getFromScreen() {
		return this.fromScreen;
	}

	/**
	 * @param fromScreen The fromScreen to set.
	 */
	public void setFromScreen(String fromScreen) {
		this.fromScreen = fromScreen;
	}

	/**
	 * @return Returns the initialFocus.
	 */
	public String getInitialFocus() {
		return this.initialFocus;
	}

	/**
	 * @param initialFocus The initialFocus to set.
	 */
	public void setInitialFocus(String initialFocus) {
		this.initialFocus = initialFocus;
	}

	/**
	 * @return Returns the mailClass.
	 */
	public String[] getMailClass() {
		return this.mailClass;
	}

	/**
	 * @param mailClass The mailClass to set.
	 */
	public void setMailClass(String[] mailClass) {
		this.mailClass = mailClass;
	}

	/**
	 * @return Returns the mailHI.
	 */
	public String[] getMailHI() {
		return this.mailHI;
	}

	/**
	 * @param mailHI The mailHI to set.
	 */
	public void setMailHI(String[] mailHI) {
		this.mailHI = mailHI;
	}

	/**
	 * @return Returns the mailRI.
	 */
	public String[] getMailRI() {
		return this.mailRI;
	}

	/**
	 * @param mailRI The mailRI to set.
	 */
	public void setMailRI(String[] mailRI) {
		this.mailRI = mailRI;
	}

	/**
	 * @return Returns the numBags.
	 */
	public String[] getNumBags() {
		return this.numBags;
	}

	/**
	 * @param numBags The numBags to set.
	 */
	public void setNumBags(String[] numBags) {
		this.numBags = numBags;
	}

	/**
	 * @return Returns the originOE.
	 */
	public String[] getOriginOE() {
		return this.originOE;
	}

	/**
	 * @param originOE The originOE to set.
	 */
	public void setOriginOE(String[] originOE) {
		this.originOE = originOE;
	}

	/**
	 * @return Returns the paCode.
	 */
	public String getPaCode() {
		return this.paCode;
	}

	/**
	 * @param paCode The paCode to set.
	 */
	public void setPaCode(String paCode) {
		this.paCode = paCode;
	}

	/**
	 * @return Returns the pou.
	 */
	public String[] getPou() {
		return this.pou;
	}

	/**
	 * @param pou The pou to set.
	 */
	public void setPou(String[] pou) {
		this.pou = pou;
	}

	/**
	 * @return Returns the remarks.
	 */
	public String getRemarks() {
		return this.remarks;
	}

	/**
	 * @param remarks The remarks to set.
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return Returns the rsn.
	 */
	public String[] getRsn() {
		return this.rsn;
	}

	/**
	 * @param rsn The rsn to set.
	 */
	public void setRsn(String[] rsn) {
		this.rsn = rsn;
	}

	/**
	 * @return Returns the selectMail.
	 */
	public String[] getSelectMail() {
		return this.selectMail;
	}

	/**
	 * @param selectMail The selectMail to set.
	 */
	public void setSelectMail(String[] selectMail) {
		this.selectMail = selectMail;
	}

	/**
	 * @return Returns the selectRoute.
	 */
	public String[] getSelectRoute() {
		return this.selectRoute;
	}

	/**
	 * @param selectRoute The selectRoute to set.
	 */
	public void setSelectRoute(String[] selectRoute) {
		this.selectRoute = selectRoute;
	}

	/**
	 * @return Returns the subClass.
	 */
	public String[] getSubClass() {
		return this.subClass;
	}

	/**
	 * @param subClass The subClass to set.
	 */
	public void setSubClass(String[] subClass) {
		this.subClass = subClass;
	}

	/**
	 * @return Returns the type.
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * @param type The type to set.
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return Returns the uldNum.
	 */
	public String[] getUldNum() {
		return this.uldNum;
	}

	/**
	 * @param uldNum The uldNum to set.
	 */
	public void setUldNum(String[] uldNum) {
		this.uldNum = uldNum;
	}

	/**
	 * @return Returns the weight.
	 */
	public String[] getWeight() {
		return this.weight;
	}

	/**
	 * @param weight The weight to set.
	 */
	public void setWeight(String[] weight) {
		this.weight = weight;
	}

	/**
	 * @return Returns the year.
	 */
	public String[] getYear() {
		return this.year;
	}

	/**
	 * @param year The year to set.
	 */
	public void setYear(String[] year) {
		this.year = year;
	}

	/**
	 * @return Returns the pol.
	 */
	public String[] getPol() {
		return this.pol;
	}

	/**
	 * @param pol The pol to set.
	 */
	public void setPol(String[] pol) {
		this.pol = pol;
	}

	/**
	 * @return Returns the disableListSuccess.
	 */
	public String getDisableListSuccess() {
		return this.disableListSuccess;
	}

	/**
	 * @param disableListSuccess The disableListSuccess to set.
	 */
	public void setDisableListSuccess(String disableListSuccess) {
		this.disableListSuccess = disableListSuccess;
	}

	/**
	 * @return Returns the tableFocus.
	 */
	public String getTableFocus() {
		return this.tableFocus;
	}

	/**
	 * @param tableFocus The tableFocus to set.
	 */
	public void setTableFocus(String tableFocus) {
		this.tableFocus = tableFocus;
	}

	public String[] getMailOpFlag() {
		return mailOpFlag;
	}

	public void setMailOpFlag(String[] mailOpFlag) {
		this.mailOpFlag = mailOpFlag;
	}

	public String[] getRouteOpFlag() {
		return routeOpFlag;
	}

	public void setRouteOpFlag(String[] routeOpFlag) {
		this.routeOpFlag = routeOpFlag;
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
	 * @return the afterPopupSaveFlag
	 */
	public String getAfterPopupSaveFlag() {
		return afterPopupSaveFlag;
	}

	/**
	 * @param afterPopupSaveFlag the afterPopupSaveFlag to set
	 */
	public void setAfterPopupSaveFlag(String afterPopupSaveFlag) {
		this.afterPopupSaveFlag = afterPopupSaveFlag;
	}

	/**
	 * @return the fromPopupflg
	 */
	public String getFromPopupflg() {
		return fromPopupflg;
	}

	/**
	 * @param fromPopupflg the fromPopupflg to set
	 */
	public void setFromPopupflg(String fromPopupflg) {
		this.fromPopupflg = fromPopupflg;
	}

	/**
	 * @return the maxPageLimit
	 */
	public int getMaxPageLimit() {
		return maxPageLimit;
	}

	/**
	 * @return the newRoutingFlag
	 */
	public String getNewRoutingFlag() {
		return newRoutingFlag;
	}

	/**
	 * @param newRoutingFlag the newRoutingFlag to set
	 */
	public void setNewRoutingFlag(String newRoutingFlag) {
		this.newRoutingFlag = newRoutingFlag;
	}
	/**
	 * @param reportFlag the reportFlag to set
	 */
	public void setReportFlag(String reportFlag) {
		this.reportFlag = reportFlag;
	}
	/**
	 * @return the reportFlag
	 */
	public String getReportFlag() {
		return reportFlag;
	}

/**
 * Method to get subtype
 * @return
 */
	public String getSubType() {
		return subType;
	}
/**
 * Method to set sub type
 * @param subType
 */
	public void setSubType(String subType) {
		this.subType = subType;
	}

/**
 * @return the mailbagId
 */
public String[] getMailbagId() {
	return mailbagId;
}

/**
 * @param mailbagId the mailbagId to set
 */
public void setMailbagId(String[] mailbagId) {
	this.mailbagId = mailbagId;
}

/**
 * @return the mailOOE
 */
public String getMailOOE() {
	return mailOOE;
}

/**
 * @param mailOOE the mailOOE to set
 */
public void setMailOOE(String mailOOE) {
	this.mailOOE = mailOOE;
}

/**
 * @return the mailDOE
 */
public String getMailDOE() {
	return mailDOE;
}

/**
 * @param mailDOE the mailDOE to set
 */
public void setMailDOE(String mailDOE) {
	this.mailDOE = mailDOE;
}

/**
 * @return the mailCat
 */
public String getMailCat() {
	return mailCat;
}

/**
 * @param mailCat the mailCat to set
 */
public void setMailCat(String mailCat) {
	this.mailCat = mailCat;
}

/**
 * @return the mailSC
 */
public String getMailSC() {
	return mailSC;
}

/**
 * @param mailSC the mailSC to set
 */
public void setMailSC(String mailSC) {
	this.mailSC = mailSC;
}

/**
 * @return the mailYr
 */
public String getMailYr() {
	return mailYr;
}

/**
 * @param mailYr the mailYr to set
 */
public void setMailYr(String mailYr) {
	this.mailYr = mailYr;
}

/**
 * @return the mailDSN
 */
public String getMailDSN() {
	return mailDSN;
}

/**
 * @param mailDSN the mailDSN to set
 */
public void setMailDSN(String mailDSN) {
	this.mailDSN = mailDSN;
}

/**
 * @return the mailRSN
 */
public String getMailRSN() {
	return mailRSN;
}

/**
 * @param mailRSN the mailRSN to set
 */
public void setMailRSN(String mailRSN) {
	this.mailRSN = mailRSN;
}

/**
 * @return the hni
 */
public String getHni() {
	return hni;
}

/**
 * @param hni the hni to set
 */
public void setHni(String hni) {
	this.hni = hni;
}

/**
 * @return the ri
 */
public String getRi() {
	return ri;
}

/**
 * @param ri the ri to set
 */
public void setRi(String ri) {
	this.ri = ri;
}

/**
 * @return the mailWt
 */
public String getMailWt() {
	return mailWt;
}

/**
 * @param mailWt the mailWt to set
 */
public void setMailWt(String mailWt) {
	this.mailWt = mailWt;
}

/**
 * 	Getter for mailId 
 *	Added by : a-6245 on 22-Jun-2017
 * 	Used for :
 */
public String getMailId() {
	return mailId;
}

/**
 *  @param mailId the mailId to set
 * 	Setter for mailId 
 *	Added by : a-6245 on 22-Jun-2017
 * 	Used for :
 */
public void setMailId(String mailId) {
	this.mailId = mailId;
}  

public String getMailYear() {
	return mailYear;
}

public void setMailYear(String mailYear) {
	this.mailYear = mailYear;
}

public int getTotalReceptacles() {
	return totalReceptacles;
}

public void setTotalReceptacles(int totalReceptacles) {
	this.totalReceptacles = totalReceptacles;
}
/**
 * Getter for weightUnit
 * Added by:A-8353
 * @return weightUnit
 */
public String [] getWeightUnit() {
	return weightUnit;
}
/**
 * @param weightUnit
 * Setter for weightUnit
 *  Added by:A-8353
 */
public void setWeightUnit(String [] weightUnit) {
	this.weightUnit = weightUnit;
}

public String getDefWeightUnit() {
	return defWeightUnit;
}

public void setDefWeightUnit(String defWeightUnit) {
	this.defWeightUnit = defWeightUnit;
}


}
