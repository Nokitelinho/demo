package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults;


/**
 * MRARoutingCarrierForm
 * 
 * @author A-4452
 * 
 */



import com.ibsplc.icargo.framework.model.ScreenModel;

public class MRARoutingCarrierForm extends ScreenModel {
	private static final String BUNDLE = "despatchroutingcarrierconfigresources";
	private static final String PRODUCT = "mail";
	private static final String SUBPRODUCT = "mra.defaults";
	private static final String SCREENID = "mailtracking.mra.defaults.dispatchroutingcarrierconfig";
	
	private String companyCode;
	private String originCity;
	private String destCity;
	private String carrier;
	private String validFromDate;
	private String validToDate;
	
	
	private String [] origincode;
	private String [] destcode;
	private String [] carriercode;	
	private String [] ownSectorFrm;
	private String [] ownSectorTo;
	private String [] oalSectorFrm;
	private String [] oalSectorTo;
	private String [] hiddenOpFlag;
	private String [] checkBoxForRoutingCarrier;
	private String checkResult;
	private String [] validFrom;
	private String [] validTo;
	

	public String getProduct() {
		return PRODUCT;
	}

	public String getScreenId() {
		return SCREENID;
	}

	public String getSubProduct() {
		return SUBPRODUCT;
	}
	 /**
	 * @return Returns the bundle.
	 */
	public String getBundle() {
		return BUNDLE;
	
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getOriginCity() {
		return originCity;
	}
	public void setOriginCity(String originCity) {
		this.originCity = originCity;
	}
	public String getDestCity() {
		return destCity;
	}
	public void setDestCity(String destCity) {
		this.destCity = destCity;
	}
	public String getCarrier() {
		return carrier;
	}
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	public String[] getOrigincode() {
		return origincode;
	}
	public void setOrigincode(String[] origincode) {
		this.origincode = origincode;
	}
	public String[] getDestcode() {
		return destcode;
	}
	public void setDestcode(String[] destcode) {
		this.destcode = destcode;
	}
	public String[] getCarriercode() {
		return carriercode;
	}
	public void setCarriercode(String[] carriercode) {
		this.carriercode = carriercode;
	}
	public String[] getOwnSectorFrm() {
		return ownSectorFrm;
	}
	public void setOwnSectorFrm(String[] ownSectorFrm) {
		this.ownSectorFrm = ownSectorFrm;
	}
	public String[] getOwnSectorTo() {
		return ownSectorTo;
	}
	public void setOwnSectorTo(String[] ownSectorTo) {
		this.ownSectorTo = ownSectorTo;
	}
	public String[] getOalSectorFrm() {
		return oalSectorFrm;
	}
	public void setOalSectorFrm(String[] oalSectorFrm) {
		this.oalSectorFrm = oalSectorFrm;
	}
	public String[] getOalSectorTo() {
		return oalSectorTo;
	}
	public void setOalSectorTo(String[] oalSectorTo) {
		this.oalSectorTo = oalSectorTo;
	}
	public String[] getHiddenOpFlag() {
		return hiddenOpFlag;
	}
	public void setHiddenOpFlag(String[] hiddenOpFlag) {
		this.hiddenOpFlag = hiddenOpFlag;
	}
	public String[] getCheckBoxForRoutingCarrier() {
		return checkBoxForRoutingCarrier;
	}
	public void setCheckBoxForRoutingCarrier(String[] checkBoxForRoutingCarrier) {
		this.checkBoxForRoutingCarrier = checkBoxForRoutingCarrier;
	}
	public String getCheckResult() {
		return checkResult;
	}
	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}
	public String[] getValidFrom() {
		return validFrom;
	}
	public void setValidFrom(String[] validFrom) {
		this.validFrom = validFrom;
	}
	public String[] getValidTo() {
		return validTo;
	}
	public void setValidTo(String[] validTo) {
		this.validTo = validTo;
	}
	public String getValidFromDate() {
		return validFromDate;
	}
	public void setValidFromDate(String validFromDate) {
		this.validFromDate = validFromDate;
	}
	public String getValidToDate() {
		return validToDate;
	}
	public void setValidToDate(String validToDate) {
		this.validToDate = validToDate;
	}
	
	
	
	
	
}
