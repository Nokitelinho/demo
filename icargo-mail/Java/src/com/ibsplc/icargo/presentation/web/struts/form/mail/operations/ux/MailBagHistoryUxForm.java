package com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux;

import java.util.ArrayList;

import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.model.ScreenModel;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.MeasureAnnotation;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.MailBagHistoryUxForm.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	12-Sep-2018		:	Draft
 */
public class MailBagHistoryUxForm extends ScreenModel {
	
	private static final String SCREEN_ID = "mail.operations.ux.mailbaghistory";
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "operations";
	private static final String BUNDLE = "mailbagHistoryResources";

	private String bundle;
	private String mailbagId;
	private String dsn;
	private String ooe;
	private String doe;
	private String catogory;
	private String mailClass;
	private String year;
	private String rsn;
	private String mailSubclass; 
	private String enquiryFlag; 
	
	//Added by A-8464 for ICRD-243079
	private String fromScreenId;
	private long mailSequenceNumber;
	
	@MeasureAnnotation(mappedValue="weightMeasure",unitType="MWT")
	private String weight;
	private Measure weightMeasure;
	private String btnDisableReq;
	private String reqDeliveryTime;
	private String mailRemarks;
	private String totalViewRecords;
	private String displayPopupPage;
	@MeasureAnnotation(mappedValue="actualWeightMeasure",unitType="MWT")
	private String actualWeight;
	private Measure actualWeightMeasure;
	private String mailSerLvl;//added by A-8353 for ICRD-ICRD-327150
	private String index;

	private String isPopUp;
	private ArrayList<MailbagVO> mailBagVOs;
	private String origin;
	private String destination;
	private String consignmentNumber;
	private String consignmentDate;
	private String transportWindowDate;
	private String transportWindowTime;
	private String poacod;
	
	private String billingStatus;
	private String acceptancePostalContainerNumber;

	public String getAcceptancePostalContainerNumber() {
		return acceptancePostalContainerNumber;
	}
	public void setAcceptancePostalContainerNumber(String acceptancePostalContainerNumber) {
		this.acceptancePostalContainerNumber = acceptancePostalContainerNumber;
	}
	public String getMailbagId() {
		return mailbagId;
	}
	public void setMailbagId(String mailbagId) {
		this.mailbagId = mailbagId;
	}
	public String getDsn() {
		return dsn;
	}
	public void setDsn(String dsn) {
		this.dsn = dsn;
	}
	public String getOoe() {
		return ooe;
	}
	public void setOoe(String ooe) {
		this.ooe = ooe;
	}
	public String getDoe() {
		return doe;
	}
	public void setDoe(String doe) {
		this.doe = doe;
	}
	public String getCatogory() {
		return catogory;
	}
	public void setCatogory(String catogory) {
		this.catogory = catogory;
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
	public String getRsn() {
		return rsn;
	}
	public void setRsn(String rsn) {
		this.rsn = rsn;
	}
	public String getMailSubclass() {
		return mailSubclass;
	}
	public void setMailSubclass(String mailSubclass) {
		this.mailSubclass = mailSubclass;
	}
	public String getEnquiryFlag() {
		return enquiryFlag;
	}
	public void setEnquiryFlag(String enquiryFlag) {
		this.enquiryFlag = enquiryFlag;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public Measure getWeightMeasure() {
		return weightMeasure;
	}
	public void setWeightMeasure(Measure weightMeasure) {
		this.weightMeasure = weightMeasure;
	}
	public String getBtnDisableReq() {
		return btnDisableReq;
	}
	public void setBtnDisableReq(String btnDisableReq) {
		this.btnDisableReq = btnDisableReq;
	}
	public String getReqDeliveryTime() {
		return reqDeliveryTime;
	}
	public void setReqDeliveryTime(String reqDeliveryTime) {
		this.reqDeliveryTime = reqDeliveryTime;
	}
	public String getMailRemarks() {
		return mailRemarks;
	}
	public void setMailRemarks(String mailRemarks) {
		this.mailRemarks = mailRemarks;
	}
	public String getTotalViewRecords() {
		return totalViewRecords;
	}
	public void setTotalViewRecords(String totalViewRecords) {
		this.totalViewRecords = totalViewRecords;
	}
	public String getDisplayPopupPage() {
		return displayPopupPage;
	}
	public void setDisplayPopupPage(String displayPopupPage) {
		this.displayPopupPage = displayPopupPage;
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
	public ArrayList<MailbagVO> getMailBagVOs() {
		return mailBagVOs;
	}
	public void setMailBagVOs(ArrayList<MailbagVO> mailBagVOs) {
		this.mailBagVOs = mailBagVOs;
	}
	public String getFromScreenId() {
		return fromScreenId;
	}
	public void setFromScreenId(String fromScreenId) {
		this.fromScreenId = fromScreenId;
	}
	public long getMailSequenceNumber() {
		return mailSequenceNumber;
	}
	public void setMailSequenceNumber(long mailSequenceNumber) {
		this.mailSequenceNumber = mailSequenceNumber;
	}
	public String getActualWeight() {
		return actualWeight;
	}
	public void setActualWeight(String actualWeight) {
		this.actualWeight = actualWeight;
	}
	public Measure getActualWeightMeasure() {
		return actualWeightMeasure;
	}
	public void setActualWeightMeasure(Measure actualWeightMeasure) {
		this.actualWeightMeasure = actualWeightMeasure;
	}
	/**
	 * @author A-8353
	 * @return String
	 */
	public String getMailSerLvl() {
		return mailSerLvl;
	}
	
	/**
	 * @author A-8353
	 * @param mailSerLvl
	 */
	public void setMailSerLvl(String mailSerLvl) {
		this.mailSerLvl = mailSerLvl;
	}
	
	public String getIsPopUp() {
		return isPopUp;
	}
	public void setIsPopUp(String isPopUp) {
		this.isPopUp = isPopUp;
	}
	/**
	 * 	Getter for index 
	 *	Added by : A-7531 on 16-May-2019
	 * 	Used for :
	 */
	public String getIndex() {
		return index;
	}
	/**
	 *  @param index the index to set
	 * 	Setter for index 
	 *	Added by : A-7531 on 16-May-2019
	 * 	Used for :
	 */
	public void setIndex(String index) {
		this.index = index;
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
	public String getConsignmentNumber() {
		return consignmentNumber;
	}
	public void setConsignmentNumber(String consignmentNumber) {
		this.consignmentNumber = consignmentNumber;
	}
	public String getConsignmentDate() {
		return consignmentDate;
	}
	public void setConsignmentDate(String consignmentDate) {
		this.consignmentDate = consignmentDate;
	}
	public String getTransportWindowDate() {
		return transportWindowDate;
	}
	public void setTransportWindowDate(String transportWindowDate) {
		this.transportWindowDate = transportWindowDate;
	}
	public String getTransportWindowTime() {
		return transportWindowTime;
	}
	public void setTransportWindowTime(String transportWindowTime) {
		this.transportWindowTime = transportWindowTime;
	}
	public String getPoacod() {
		return poacod;
	}
	public void setPoacod(String poacod) {
		this.poacod = poacod;
	}
	public String getBillingStatus() {
		return billingStatus;
	}
	public void setBillingStatus(String billingStatus) {
		this.billingStatus = billingStatus;
	}
	
	
}
