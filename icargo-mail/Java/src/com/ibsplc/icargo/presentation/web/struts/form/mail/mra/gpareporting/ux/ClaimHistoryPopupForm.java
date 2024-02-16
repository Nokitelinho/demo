package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting.ux;

import com.ibsplc.icargo.framework.model.ScreenModel;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.MeasureAnnotation;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.gpareporting.ux.ClaimHistoryPopupForm.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	a-7929	:	14-JAN-2019	:	Draft
 */
public class ClaimHistoryPopupForm extends ScreenModel{
	
	private static final String SCREEN_ID = "mail.mra.gpareporting.ux";
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "mra";
	private static final String BUNDLE = "ClaimHistoryPopupResources";
	
	
	private String bundle;
	private String originExchangeOffice;
	private String destinationExchangeOffice;
	private String mailClass;
	private String year;
    private String mailbagId;
    private String pou;
    private String mailCategoryCode;
	private String mailSubclass;
	private String rsn;
	private String dsn;
	private String reqDeliveryTime;
	private String deliveryStatus;
	private long malseqnum;
	
	


	@MeasureAnnotation(mappedValue="weightMeasure",unitType="MWT")
	private double weight;
	private Measure weightMeasure;
	
	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getOriginExchangeOffice() {
		return originExchangeOffice;
	}

	public void setOriginExchangeOffice(String originExchangeOffice) {
		this.originExchangeOffice = originExchangeOffice;
	}

	public String getDestinationExchangeOffice() {
		return destinationExchangeOffice;
	}

	public void setDestinationExchangeOffice(String destinationExchangeOffice) {
		this.destinationExchangeOffice = destinationExchangeOffice;
	}

	public String getMailClass() {
		return mailClass;
	}

	public void setMailClass(String mailClass) {
		this.mailClass = mailClass;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMailbagId() {
		return mailbagId;
	}

	public void setMailbagId(String mailbagId) {
		this.mailbagId = mailbagId;
	}

	public String getPou() {
		return pou;
	}

	public void setPou(String pou) {
		this.pou = pou;
	}

	public String getMailCategoryCode() {
		return mailCategoryCode;
	}

	public void setMailCategoryCode(String mailCategoryCode) {
		this.mailCategoryCode = mailCategoryCode;
	}

	public String getMailSubclass() {
		return mailSubclass;
	}

	public void setMailSubclass(String mailSubclass) {
		this.mailSubclass = mailSubclass;
	}

	public String getRsn() {
		return rsn;
	}

	public void setRsn(String rsn) {
		this.rsn = rsn;
	}
    
	public String getDsn() {
		return dsn;
	}

	public void setDsn(String dsn) {
		this.dsn = dsn;
	}

	public String getReqDeliveryTime() {
		return reqDeliveryTime;
	}

	public void setReqDeliveryTime(String reqDeliveryTime) {
		this.reqDeliveryTime = reqDeliveryTime;
	}

	public String getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}
	

	public Measure getWeightMeasure() {
		return weightMeasure;
	}

	public void setWeightMeasure(Measure weightMeasure) {
		this.weightMeasure = weightMeasure;
	}
	
	public long getMalseqnum() {
		return malseqnum;
	}

	public void setMalseqnum(long malseqnum) {
		this.malseqnum = malseqnum;
	}

	public static String getProductName() {
		return PRODUCT_NAME;
	}

	public static String getSubproductName() {
		return SUBPRODUCT_NAME;
	}

	public String getBundle() {
		return BUNDLE;
	}

	public String getProduct() {
		return PRODUCT_NAME;
	}

	
	public String getScreenId() {
		return SCREEN_ID;
	}

	
	public String getSubProduct() {
		return SUBPRODUCT_NAME;
	}

}
