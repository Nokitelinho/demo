/*
 * ViewBillingLineForm.java Created on Mar 12, 2007
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults;

import java.util.Arrays;
import com.ibsplc.icargo.framework.client.daterange.notation.DateFieldId;
import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-2398
 *
 */
public class ViewBillingLineForm extends ScreenModel{

private static final String BUNDLE = "viewbillingline";

	//private String bundle;

	private static final String PRODUCT = "mail";

	private static final String SUBPRODUCT = "mra";

	private static final String SCREENID =
		"mailtracking.mra.defaults.viewbillingline";

	private String billingMatrixID;
	
	private String postalAdmin;
	
	private String airline;
	
	private String origin;
	
	private String destination;
	
	private String billedSector;
	
	private String validFrom;
	
	private String validTo;
	
	private String status;
	
	private String category;
	
	private String billingClass;
	
	private String subClass;
	
	private String uldType;
	
	private String catCode;
	
	private String classCode;
	
	private String displayPage="1";
	
	private String lastPageNum="0";
	
	private String [] checkboxes;
	
	private String [] orgCountry;
	
	private String [] orgCity;
	
	private String [] orgRegion;
	
	private String [] destCountry;
	
	private String [] destRegion;
	
	private String [] destCity;
	
	private String [] upliftCountry;
	
	private String [] upliftCity;
	
	private String [] dischargeCountry;
	
	private String [] dischargeCity;
	
	private String [] billingCategory;
	
	private String [] billingClasses;
	
	private String [] billingSubClass;
	
	private String [] billingUldType;
	
	private String [] billingFlightNo;
	
	//Added as part of Bug ICRD-106391 starts
	private String [] transferredBy;
	private String [] transferredPA;
	private String [] company;
	private String [] subClassGroup;
	
	//Added by A-7540
	 private String[] orgAirport;
	 private String[] desAirport;
	

	 private String[] viaPoint;
	 private String[] mailService;
	 private String[]  paBuilt;
	
	 
	private String [] upliftAirport;
	private String [] dischargeAirport;
		
	
	//Added as part of Bug ICRD-106391 ends
	private String selectedIndexes;
	
	private String fromPage;
	
	private String copyFlag;
	
	private String billingLineID;//Added for ICRD-162338
	/*
	 * for change status pop up
	 */
	private String popupStatus;
	
	private String canClose;
	
	private String [] billingUldGrp;
	
	private String selectedIndex;
	
	private String overrideRounding;//added for ICRD-285295
	 private String[] uplAirport;
	 

	private String[] disAirport;
	
	private String unitCode ;
	
	private String originLevel;

	private String destinationLevel;

	private String uplift;

	private String discharge;

	private String upliftLevel;

	private String dischargeLevel;
	
	private String [] flownCarrier;
	
	private static final String[] EMPTY_ARRAY = new String[0];

		
	/**
	 * @return Returns the billingUldGrp.
	 */
	public String[] getBillingUldGrp() {
		return billingUldGrp;
	}

	/**
	 * @param billingUldGrp The billingUldGrp to set.
	 */
	public void setBillingUldGrp(String[] billingUldGrp) {
		this.billingUldGrp = billingUldGrp;
	}

	/**
	 * @return Returns the canClose.
	 */
	public String getCanClose() {
		return canClose;
	}

	/**
	 * @param canClose The canClose to set.
	 */
	public void setCanClose(String canClose) {
		this.canClose = canClose;
	}

	/**
	 * @return Returns the popupStatus.
	 */
	public String getPopupStatus() {
		return popupStatus;
	}

	/**
	 * @param popupStatus The popupStatus to set.
	 */
	public void setPopupStatus(String popupStatus) {
		this.popupStatus = popupStatus;
	}

	/**
	 * @return Returns the displayPage.
	 */
	public String getDisplayPage() {
		return displayPage;
	}

	/**
	 * @param displayPage The displayPage to set.
	 */
	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}

	/**
	 * @return Returns the lastPageNum.
	 */
	public String getLastPageNum() {
		return lastPageNum;
	}

	/**
	 * @param lastPageNum The lastPageNum to set.
	 */
	public void setLastPageNum(String lastPageNum) {
		this.lastPageNum = lastPageNum;
	}

	/**
	 *
	 */
	public String getScreenId() {
		// TODO Auto-generated method stub
		return SCREENID;
	}

	/**
	 *
	 */
	public String getProduct() {
		// TODO Auto-generated method stub
		return PRODUCT;
	}

	/**
	 *
	 */
	public String getSubProduct() {
		// TODO Auto-generated method stub
		return SUBPRODUCT;
	}

	/**
	 * @return Returns the bundle.
	 */
	public String getBundle() {
		return BUNDLE;
	}

	

	/**
	 * @return Returns the airline.
	 */
	public String getAirline() {
		return airline;
	}

	/**
	 * @param airline The airline to set.
	 */
	public void setAirline(String airline) {
		this.airline = airline;
	}

	/**
	 * @return Returns the billedSector.
	 */
	public String getBilledSector() {
		return billedSector;
	}

	/**
	 * @param billedSector The billedSector to set.
	 */
	public void setBilledSector(String billedSector) {
		this.billedSector = billedSector;
	}

	/**
	 * @return Returns the billingClass.
	 */
	public String getBillingClass() {
		return billingClass;
	}

	/**
	 * @param billingClass The billingClass to set.
	 */
	public void setBillingClass(String billingClass) {
		this.billingClass = billingClass;
	}

	/**
	 * @return Returns the billingMatrixID.
	 */
	public String getBillingMatrixID() {
		return billingMatrixID;
	}

	/**
	 * @param billingMatrixID The billingMatrixID to set.
	 */
	public void setBillingMatrixID(String billingMatrixID) {
		this.billingMatrixID = billingMatrixID;
	}

	/**
	 * @return Returns the category.
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category The category to set.
	 */
	public void setCategory(String category) {
		this.category = category;
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
	 * @return Returns the postalAdmin.
	 */
	public String getPostalAdmin() {
		return postalAdmin;
	}

	/**
	 * @param postalAdmin The postalAdmin to set.
	 */
	public void setPostalAdmin(String postalAdmin) {
		this.postalAdmin = postalAdmin;
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
	 * @return Returns the subClass.
	 */
	public String getSubClass() {
		return subClass;
	}

	/**
	 * @param subClass The subClass to set.
	 */
	public void setSubClass(String subClass) {
		this.subClass = subClass;
	}

	/**
	 * @return Returns the uldType.
	 */
	public String getUldType() {
		return uldType;
	}

	/**
	 * @param uldType The uldType to set.
	 */
	public void setUldType(String uldType) {
		this.uldType = uldType;
	}

	/**
	 * @return Returns the validFrom.
	 */
	@DateFieldId(id="ListBillingLinesDateRange",fieldType="from")//Added By T-1925 for ICRD-9704
	public String getValidFrom() {
		return validFrom;
	}

	/**
	 * @param validFrom The validFrom to set.
	 */
	public void setValidFrom(String validFrom) {
		this.validFrom = validFrom;
	}

	/**
	 * @return Returns the validTo.
	 */
	@DateFieldId(id="ListBillingLinesDateRange",fieldType="to")//Added By T-1925 for ICRD-9704
	public String getValidTo() {
		return validTo;
	}

	/**
	 * @param validTo The validTo to set.
	 */
	public void setValidTo(String validTo) {
		this.validTo = validTo;
	}

	/**
	 * @return Returns the billingCategory.
	 */
	public String[] getBillingCategory() {
		return billingCategory;
	}

	/**
	 * @param billingCategory The billingCategory to set.
	 */
	public void setBillingCategory(String[] billingCategory) {
		this.billingCategory = billingCategory;
	}

	/**
	 * @return Returns the billingClasses.
	 */
	public String[] getBillingClasses() {
		return billingClasses;
	}

	/**
	 * @param billingClasses The billingClasses to set.
	 */
	public void setBillingClasses(String[] billingClasses) {
		this.billingClasses = billingClasses;
	}

	/**
	 * @return Returns the billingFlightNo.
	 */
	public String[] getBillingFlightNo() {
		return billingFlightNo;
	}

	/**
	 * @param billingFlightNo The billingFlightNo to set.
	 */
	public void setBillingFlightNo(String[] billingFlightNo) {
		this.billingFlightNo = billingFlightNo;
	}

	/**
	 * @return Returns the billingSubClass.
	 */
	public String[] getBillingSubClass() {
		return billingSubClass;
	}

	/**
	 * @param billingSubClass The billingSubClass to set.
	 */
	public void setBillingSubClass(String[] billingSubClass) {
		this.billingSubClass = billingSubClass;
	}

	/**
	 * @return Returns the billingUldType.
	 */
	public String[] getBillingUldType() {
		return billingUldType;
	}

	/**
	 * @param billingUldType The billingUldType to set.
	 */
	public void setBillingUldType(String[] billingUldType) {
		this.billingUldType = billingUldType;
	}

	/**
	 * @return Returns the checkboxes.
	 */
	public String[] getCheckboxes() {
		return checkboxes;
	}

	/**
	 * @param checkboxes The checkboxes to set.
	 */
	public void setCheckboxes(String[] checkboxes) {
		this.checkboxes = checkboxes;
	}

	/**
	 * @return Returns the destCity.
	 */
	public String[] getDestCity() {
		return destCity;
	}

	/**
	 * @param destCity The destCity to set.
	 */
	public void setDestCity(String[] destCity) {
		this.destCity = destCity;
	}

	/**
	 * @return Returns the destCountry.
	 */
	public String[] getDestCountry() {
		return destCountry;
	}

	/**
	 * @param destCountry The destCountry to set.
	 */
	public void setDestCountry(String[] destCountry) {
		this.destCountry = destCountry;
	}

	/**
	 * @return Returns the destRegion.
	 */
	public String[] getDestRegion() {
		return destRegion;
	}

	/**
	 * @param destRegion The destRegion to set.
	 */
	public void setDestRegion(String[] destRegion) {
		this.destRegion = destRegion;
	}

	/**
	 * @return Returns the dischargeCity.
	 */
	public String[] getDischargeCity() {
		return dischargeCity;
	}

	/**
	 * @param dischargeCity The dischargeCity to set.
	 */
	public void setDischargeCity(String[] dischargeCity) {
		this.dischargeCity = dischargeCity;
	}

	/**
	 * @return Returns the dischargeCountry.
	 */
	public String[] getDischargeCountry() {
		return dischargeCountry;
	}

	/**
	 * @param dischargeCountry The dischargeCountry to set.
	 */
	public void setDischargeCountry(String[] dischargeCountry) {
		this.dischargeCountry = dischargeCountry;
	}

	/**
	 * @return Returns the orgCity.
	 */
	public String[] getOrgCity() {
		return orgCity;
	}

	/**
	 * @param orgCity The orgCity to set.
	 */
	public void setOrgCity(String[] orgCity) {
		this.orgCity = orgCity;
	}

	/**
	 * @return Returns the orgCountry.
	 */
	public String[] getOrgCountry() {
		return orgCountry;
	}

	/**
	 * @param orgCountry The orgCountry to set.
	 */
	public void setOrgCountry(String[] orgCountry) {
		this.orgCountry = orgCountry;
	}

	/**
	 * @return Returns the orgRegion.
	 */
	public String[] getOrgRegion() {
		return orgRegion;
	}

	/**
	 * @param orgRegion The orgRegion to set.
	 */
	public void setOrgRegion(String[] orgRegion) {
		this.orgRegion = orgRegion;
	}

	/**
	 * @return Returns the upliftCity.
	 */
	public String[] getUpliftCity() {
		return upliftCity;
	}

	/**
	 * @param upliftCity The upliftCity to set.
	 */
	public void setUpliftCity(String[] upliftCity) {
		this.upliftCity = upliftCity;
	}

	/**
	 * @return Returns the upliftCountry.
	 */
	public String[] getUpliftCountry() {
		return upliftCountry;
	}

	/**
	 * @param upliftCountry The upliftCountry to set.
	 */
	public void setUpliftCountry(String[] upliftCountry) {
		this.upliftCountry = upliftCountry;
	}

	/**
	 * @return Returns the bUNDLE.
	 */
	public static String getBUNDLE() {
		return BUNDLE;
	}

	/**
	 * @return Returns the catCode.
	 */
	public String getCatCode() {
		return catCode;
	}

	/**
	 * @param catCode The catCode to set.
	 */
	public void setCatCode(String catCode) {
		this.catCode = catCode;
	}

	/**
	 * @return Returns the classCode.
	 */
	public String getClassCode() {
		return classCode;
	}

	/**
	 * @param classCode The classCode to set.
	 */
	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	/**
	 * @return Returns the selectedIndexes.
	 */
	public String getSelectedIndexes() {
		return selectedIndexes;
	}

	/**
	 * @param selectedIndexes The selectedIndexes to set.
	 */
	public void setSelectedIndexes(String selectedIndexes) {
		this.selectedIndexes = selectedIndexes;
	}

	/**
	 * @return Returns the fromPage.
	 */
	public String getFromPage() {
		return fromPage;
	}

	/**
	 * @param fromPage The fromPage to set.
	 */
	public void setFromPage(String fromPage) {
		this.fromPage = fromPage;
	}

	/**
	 * @return Returns the copyFlag.
	 */
	public String getCopyFlag() {
		return copyFlag;
	}

	/**
	 * @param copyFlag The copyFlag to set.
	 */
	public void setCopyFlag(String copyFlag) {
		this.copyFlag = copyFlag;
	}
	
	//Added as part of Bug ICRD-106391 starts
/**
 * 
 * @return transferredBy
 */
	public String[] getTransferredBy() {
		return transferredBy;
	}
/**
 * 
 * @param transferredBy
 */
	public void setTransferredBy(String[] transferredBy) {
		this.transferredBy = transferredBy;
	}
/**
 * 
 * @return transferredPA
 */
	public String[] getTransferredPA() {
		return transferredPA;
	}
/**
 * 
 * @param transferredPA
 */
	public void setTransferredPA(String[] transferredPA) {
		this.transferredPA = transferredPA;
	}
/**
 * 
 * @return company
 */
	public String[] getCompany() {
		return company;
	}
/**
 * 
 * @param company
 */
	public void setCompany(String[] company) {
		this.company = company;
	}
/**
 * 
 * @return subClassGroup
 */
	public String[] getSubClassGroup() {
		return subClassGroup;
	}
/**
 * 
 * @param subClassGroup
 */
	public void setSubClassGroup(String[] subClassGroup) {
		this.subClassGroup = subClassGroup;
	}
	//Added as part of Bug ICRD-106391 ends
/**
 * @return the billingLineID
 */
public String getBillingLineID() {
	return billingLineID;
}
/**
 * @param billingLineID the billingLineID to set
 */
public void setBillingLineID(String billingLineID) {
	this.billingLineID = billingLineID;
}

/**
 * @return the selectedIndex
 */
public String getSelectedIndex() {
	return selectedIndex;
}

/**
 * @param selectedIndex the selectedIndex to set
 */
public void setSelectedIndex(String selectedIndex) {
	this.selectedIndex = selectedIndex;
}

public String[] getOrgAirport() {
	return orgAirport;
}

public void setOrgAirport(String[] orgAirport) {
	this.orgAirport = orgAirport;
}

public String[] getDesAirport() {
	return desAirport;
}

public void setDesAirport(String[] desAirport) {
	this.desAirport = desAirport;
}

public String[] getViaPoint() {
	return viaPoint;
}

public void setViaPoint(String[] viaPoint) {
	this.viaPoint = viaPoint;
}

public String[] getMailService() {
	return mailService;
}

public void setMailService(String[] mailService) {
	this.mailService = mailService;
}

public String[] getPaBuilt() {
	return paBuilt;
}

public void setPaBuilt(String[] paBuilt) {
	this.paBuilt = paBuilt;
}

public String getOverrideRounding() {
	return overrideRounding;
}

public void setOverrideRounding(String overrideRounding) {
	this.overrideRounding = overrideRounding;
}
public String[] getUplAirport() {
	return uplAirport;
}

public void setUplAirport(String[] uplAirport) {
	this.uplAirport = uplAirport;
}

public String[] getDisAirport() {
	return disAirport;
}

public void setDisAirport(String[] disAirport) {
	this.disAirport = disAirport;
}	

public String getUnitCode() {
	return unitCode;
}

public void setUnitCode(String unitCode) {
	this.unitCode = unitCode;
}

	public String getOriginLevel() {
		return originLevel;
	}

	public void setOriginLevel(String originLevel) {
		this.originLevel = originLevel;
	}

	public String getDestinationLevel() {
		return destinationLevel;
	}

	public void setDestinationLevel(String destinationLevel) {
		this.destinationLevel = destinationLevel;
	}

	public String getUplift() {
		return uplift;
	}

	public void setUplift(String uplift) {
		this.uplift = uplift;
	}

	public String getDischarge() {
		return discharge;
	}

	public void setDischarge(String discharge) {
		this.discharge = discharge;
	}

	public String getUpliftLevel() {
		return upliftLevel;
	}

	public void setUpliftLevel(String upliftLevel) {
		this.upliftLevel = upliftLevel;
	}

	public String getDischargeLevel() {
		return dischargeLevel;
	}

	public void setDischargeLevel(String dischargeLevel) {
		this.dischargeLevel = dischargeLevel;
	}

	public String[] getUpliftAirport() {
		return upliftAirport;
	}

	public void setUpliftAirport(String[] upliftAirport) {
		this.upliftAirport = upliftAirport;
	}

	public String[] getDischargeAirport() {
		return dischargeAirport;
	}

	public void setDischargeAirport(String[] dischargeAirport) {
		this.dischargeAirport = dischargeAirport;
	}
	
	public String[] getFlownCarrier() {
		if (flownCarrier != null && flownCarrier.length > 0) {
			return Arrays.copyOf(flownCarrier, flownCarrier.length);
		} else {
			return EMPTY_ARRAY;
		}
	}

	public void setFlownCarrier(String[] flownCarrier) {
		if (flownCarrier != null && flownCarrier.length > 0) {
			this.flownCarrier = Arrays.copyOf(flownCarrier, flownCarrier.length);
		}
	}
	
}
