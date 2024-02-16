/*
 *  DespatchRoutingForm.java Created on Sep 2, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;


/**
 * @author A-3229
 *
 */
public class DespatchRoutingForm extends ScreenModel {
	
	
	private static final String BUNDLE = "despatchroutingresources";
	private static final String PRODUCT = "mail";
	private static final String SUBPRODUCT = "mra.defaults";
	private static final String SCREENID = "mailtracking.mra.defaults.despatchrouting";
	
	private String dsn;
	
	private String dsnDate;
	
	private String[] flightCarrierId;
	
	private String[] flightCarrierCode;
	
	private String[] flightNumber;
	
	private String[] departureDate;
	
	private String[] pol;
	
	private String[] pou;
	
	private String[] agreementType;
	//For hidden flags
	
	private String[] hiddenOpFlag;
	
	private String airport;
	
	//For CheckBox
	
	private String[] checkBoxForFlight;
	
	//Pk fields
	
	private String companyCode;
	
	private String billingBasis;
	
	private String csgDocumentNumber;
	
	private String poaCode;
	
	private String csgSequenceNumber;
	
	private String closeFlag;

	private String  fromScreen;
	
	private String[] nopieces;
	
	private String[] weight;
	
	private String[] source;
	
	
	//for split route business
	private String owncarcode;
	private String origin;
	private String destn;
	private String oalPcs;
	private String oalwgt;
	private String diplayWgtUnit;	
	private String diplayWgt;	
	private String showDsnPopUp;
	
	private String[] blockSpaceType;
	private String[] bsaReference;
	private int rowCount;
	private String selectedBlockSpaceType;
	private String bsaValidationStatus;
	
	
	private String selectedFlightCarrierCode;
	
	private String selectedFlightNumber;
	
	private String selectedDepartureDate;
	
	private String selectedPol;
	
	private String selectedPou;
	
	private String[] mailSource;
	private String exactMailSource;
	
	private String transferPA;
	private String transferAirline;
	

	
	public String getDiplayWgt() {
		return diplayWgt;
	}

	public void setDiplayWgt(String diplayWgt) {
		this.diplayWgt = diplayWgt;
	}
	
	public String getDiplayWgtUnit() {
		return diplayWgtUnit;
	}

	public void setDiplayWgtUnit(String diplayWgtUnit) {
		this.diplayWgtUnit = diplayWgtUnit;
	}
	
	/**
	 * @return the showDsnPopUp
	 */
	public String getShowDsnPopUp() {
		return showDsnPopUp;
	}

	/**
	 * @param showDsnPopUp the showDsnPopUp to set
	 */
	public void setShowDsnPopUp(String showDsnPopUp) {
		this.showDsnPopUp = showDsnPopUp;
	}

	/**
	 * @return Returns the fromScreen.
	 */
	public String getFromScreen() {
		return fromScreen;
	}

	/**
	 * @param fromScreen The fromScreen to set.
	 */
	public void setFromScreen(String fromScreen) {
		this.fromScreen = fromScreen;
	}

	/**
	 * @return Returns the closeFlag.
	 */
	public String getCloseFlag() {
		return closeFlag;
	}

	/**
	 * @param closeFlag The closeFlag to set.
	 */
	public void setCloseFlag(String closeFlag) {
		this.closeFlag = closeFlag;
	}

	/**
	 * @return Returns the SCREENID.
	 */
    public String getScreenId() {
        return SCREENID;
    }

    /**
	 * @return Returns the PRODUCT.
	 */
    public String getProduct() {
        return PRODUCT;
    }
    /**
	 * @return Returns the SUBPRODUCT.
	 */
    public String getSubProduct() {
        return SUBPRODUCT;
    }
    /**
	 * @return Returns the bundle.
	 */
	public String getBundle() {
		return BUNDLE;
	
	}
	
	


	/**
	 * @return Returns the companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode the companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return Returns the departureDate
	 */

	public String[] getDepartureDate() {
		return departureDate;
	}

	/**
	 * @param departureDate the departureDate to set.
	 */
	public void setDepartureDate(String[] departureDate) {
		this.departureDate = departureDate;
	}
	/**
	 * @return Returns the dsn
	 */

	public String getDsn() {
		return dsn;
	}
	/**
	 * @param dsn the dsn to set.
	 */

	public void setDsn(String dsn) {
		this.dsn = dsn;
	}

	/**
	 * @return Returns the dsnDate
	 */
	public String getDsnDate() {
		return dsnDate;
	}

	/**
	 * @param dsnDate the dsnDate to set.
	 */
	public void setDsnDate(String dsnDate) {
		this.dsnDate = dsnDate;
	}



	/**
	 * @return Returns the flightNumber
	 */
	public String[] getFlightNumber() {
		return flightNumber;
	}
	/**
	 * @param flightNumber the flightNumber to set.
	 */

	public void setFlightNumber(String[] flightNumber) {
		this.flightNumber = flightNumber;
	}

	/**
	 * @return Returns the pol
	 */
	public String[] getPol() {
		return pol;
	}

	/**
	 * @param pol the pol to set.
	 */
	public void setPol(String[] pol) {
		this.pol = pol;
	}

	/**
	 * @return Returns the pou
	 */
	public String[] getPou() {
		return pou;
	}

	/**
	 * @param pou the pou to set.
	 */
	public void setPou(String[] pou) {
		this.pou = pou;
	}
	/**
	 * @return Returns the agreementType
	 */
	public String[] getAgreementType() {
		return agreementType;
	}
	/**
	 * @param agreementType the agreementType to set.
	 */
	public void setAgreementType(String[] agreementType) {
		this.agreementType = agreementType;
	}

	/**
	 * @return Returns the hiddenOpFlag
	 */
	public String[] getHiddenOpFlag() {
		return hiddenOpFlag;
	}

	/**
	 * @param hiddenOpFlag the hiddenOpFlag to set.
	 */
	public void setHiddenOpFlag(String[] hiddenOpFlag) {
		this.hiddenOpFlag = hiddenOpFlag;
	}

	/**
	 * @return Returns the flightCarrierCode
	 */
	public String[] getFlightCarrierCode() {
		return flightCarrierCode;
	}

	/**
	 * @param flightCarrierCode the flightCarrierCode to set.
	 */
	public void setFlightCarrierCode(String[] flightCarrierCode) {
		this.flightCarrierCode = flightCarrierCode;
	}

	/**
	 * @return Returns the flightCarrierId
	 */
	public String[] getFlightCarrierId() {
		return flightCarrierId;
	}

	/**
	 * @param flightCarrierId the flightCarrierId to set.
	 */

	public void setFlightCarrierId(String[] flightCarrierId) {
		this.flightCarrierId = flightCarrierId;
	}
	/**
	 * @return Returns the airport
	 */
	public String getAirport() {
		return airport;
	}

	/**
	 * @param airport the airport to set.
	 */
	public void setAirport(String airport) {
		this.airport = airport;
	}

	/**
	 * @return Returns the billingBasis
	 */
	public String getBillingBasis() {
		return billingBasis;
	}

	/**
	 * @param billingBasis the billingBasis to set.
	 */
	public void setBillingBasis(String billingBasis) {
		this.billingBasis = billingBasis;
	}

	/**
	 * @return Returns the csgDocumentNumber
	 */
	public String getCsgDocumentNumber() {
		return csgDocumentNumber;
	}
	/**
	 * @param csgDocumentNumber the csgDocumentNumber to set.
	 */

	public void setCsgDocumentNumber(String csgDocumentNumber) {
		this.csgDocumentNumber = csgDocumentNumber;
	}
	/**
	 * @return Returns the csgSequenceNumber
	 */

	public String getCsgSequenceNumber() {
		return csgSequenceNumber;
	}
	/**
	 * @param csgSequenceNumber the csgSequenceNumber to set.
	 */
	public void setCsgSequenceNumber(String csgSequenceNumber) {
		this.csgSequenceNumber = csgSequenceNumber;
	}

	/**
	 * @return Returns the poaCode
	 */
	public String getPoaCode() {
		return poaCode;
	}

	/**
	 * @param poaCode the poaCode to set.
	 */
	public void setPoaCode(String poaCode) {
		this.poaCode = poaCode;
	}

	/**
	 * @return the nopieces
	 */
	public String[] getNopieces() {
		return nopieces;
	}

	/**
	 * @param nopieces the nopieces to set
	 */
	public void setNopieces(String[] nopieces) {
		this.nopieces = nopieces;
	}

	/**
	 * @return the weight
	 */
	public String[] getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(String[] weight) {
		this.weight = weight;
	}

	/**
	 * @return the owncarcode
	 */
	public String getOwncarcode() {
		return owncarcode;
	}

	/**
	 * @param owncarcode the owncarcode to set
	 */
	public void setOwncarcode(String owncarcode) {
		this.owncarcode = owncarcode;
	}

	/**
	 * @return the destn
	 */
	public String getDestn() {
		return destn;
	}

	/**
	 * @param destn the destn to set
	 */
	public void setDestn(String destn) {
		this.destn = destn;
	}

	/**
	 * @return the origin
	 */
	public String getOrigin() {
		return origin;
	}

	/**
	 * @param origin the origin to set
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	/**
	 * @return the oalPcs
	 */
	public String getOalPcs() {
		return oalPcs;
	}

	/**
	 * @param oalPcs the oalPcs to set
	 */
	public void setOalPcs(String oalPcs) {
		this.oalPcs = oalPcs;
	}

	/**
	 * @return the oalwgt
	 */
	public String getOalwgt() {
		return oalwgt;
	}

	/**
	 * @param oalwgt the oalwgt to set
	 */
	public void setOalwgt(String oalwgt) {
		this.oalwgt = oalwgt;
	}

	/**
	 * @return the checkBoxForFlight
	 */
	public String[] getCheckBoxForFlight() {
		return checkBoxForFlight;
	}

	/**
	 * @param checkBoxForFlight the checkBoxForFlight to set
	 */
	public void setCheckBoxForFlight(String[] checkBoxForFlight) {
		this.checkBoxForFlight = checkBoxForFlight;
	}
/**
 * 
 * @return blockSpaceType
 */
	public String[] getBlockSpaceType() {
		return blockSpaceType;
	}
/**
 * 
 * @param blockSpaceType
 */
	public void setBlockSpaceType(String[] blockSpaceType) {
		this.blockSpaceType = blockSpaceType;
	}
/**
 * 
 * @return bsaReference
 */
	public String[] getBsaReference() {
		return bsaReference;
	}
/**
 * 
 * @param bsaReference
 */
	public void setBsaReference(String[] bsaReference) {
		this.bsaReference = bsaReference;
	}
/**
 * 
 * @return rowCount
 */
	public int getRowCount() {
		return rowCount;
	}
/**
 * 
 * @param rowCount
 */
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
/**
 * 
 * @return selectedBlockSpaceType
 */
	public String getSelectedBlockSpaceType() {
		return selectedBlockSpaceType;
	}
/**
 * 
 * @param selectedBlockSpaceType
 */
	public void setSelectedBlockSpaceType(String selectedBlockSpaceType) {
		this.selectedBlockSpaceType = selectedBlockSpaceType;
	}
/**
 * 
 * @return bsaValidationStatus
 */
	public String getBsaValidationStatus() {
		return bsaValidationStatus;
	}
/**
 * 
 * @param bsaValidationStatus
 */
	public void setBsaValidationStatus(String bsaValidationStatus) {
		this.bsaValidationStatus = bsaValidationStatus;
	}

public String getSelectedFlightCarrierCode() {
	return selectedFlightCarrierCode;
}

public void setSelectedFlightCarrierCode(String selectedFlightCarrierCode) {
	this.selectedFlightCarrierCode = selectedFlightCarrierCode;
}

public String getSelectedFlightNumber() {
	return selectedFlightNumber;
}

public void setSelectedFlightNumber(String selectedFlightNumber) {
	this.selectedFlightNumber = selectedFlightNumber;
}

public String getSelectedDepartureDate() {
	return selectedDepartureDate;
}

public void setSelectedDepartureDate(String selectedDepartureDate) {
	this.selectedDepartureDate = selectedDepartureDate;
}

public String getSelectedPol() {
	return selectedPol;
}

public void setSelectedPol(String selectedPol) {
	this.selectedPol = selectedPol;
}

public String getSelectedPou() {
	return selectedPou;
}

public void setSelectedPou(String selectedPou) {
	this.selectedPou = selectedPou;
}

public String[] getMailSource() {
	return mailSource;
}

public void setMailSource(String[] mailSource) {
	this.mailSource = mailSource;
}

public String getExactMailSource() {
	return exactMailSource;
}

public void setExactMailSource(String exactMailSource) {
	this.exactMailSource = exactMailSource;
}

/**
 * 
 * 	Method		:	DespatchRoutingForm.getSource
 *	Added by 	:	A-5219 on 02-Apr-2020
 * 	Used for 	:
 *	Parameters	:	@return 
 *	Return type	: 	String[]
 */
public String[] getSource(){
	return source;
}

/**
 * 
 * 	Method		:	DespatchRoutingForm.setSource
 *	Added by 	:	A-5219 on 02-Apr-2020
 * 	Used for 	:
 *	Parameters	:	@param source 
 *	Return type	: 	void
 */
public void setSource(String[] source){
	this.source = source;
}

/**
 * 	Getter for transferPA 
 *	Added by : A-8061 on 30-Dec-2020
 * 	Used for :
 */
public String getTransferPA() {
	return transferPA;
}

/**
 *  @param transferPA the transferPA to set
 * 	Setter for transferPA 
 *	Added by : A-8061 on 30-Dec-2020
 * 	Used for :
 */
public void setTransferPA(String transferPA) {
	this.transferPA = transferPA;
}

/**
 * 	Getter for transferAirline 
 *	Added by : A-8061 on 30-Dec-2020
 * 	Used for :
 */
public String getTransferAirline() {
	return transferAirline;
}

/**
 *  @param transferAirline the transferAirline to set
 * 	Setter for transferAirline 
 *	Added by : A-8061 on 30-Dec-2020
 * 	Used for :
 */
public void setTransferAirline(String transferAirline) {
	this.transferAirline = transferAirline;
}

	

}
