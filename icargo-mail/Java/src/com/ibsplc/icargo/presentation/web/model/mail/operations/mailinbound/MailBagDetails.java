package com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailBagDetails.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	27-Sep-2018		:	Draft
 */
public class MailBagDetails {
	
	private String mailBagId;
	
	private String originDestPair;
	
	private String scanDate;
	
	private String mailBagStatus;
	
	private String ooe;
	
	private String doe;
	
	private String category;
	
	private String subClass;
	
	private String year;
	
	private String DSN;
	
	private String RSN;
	
	private String weight;
	
	private String weightUnit;
	
	private String stationWeight;
	
	private String stationUnit;
	
	private String volume;
	
	private String transfferCarrier;
	
	private String consignmentNumber;
	
	private String sealNo;
	
	private String damageInfo;
	
	private String bellyCartId;
	
	private String awb;
	
	private String shipmentPrefix;
	
	private String paCode;
	
	private String remarks;
	
	private String carditNo;
	
	private String hni;
	
	private String ri;
	
	private String pou;
	
	private boolean checkBoxSelect;
	
	private String readyForDeliveryTime;
	
	private String readyForDeliveryDate;
	
	private String routingAvlFlag;
	
	private String transitFlag;
	
	private String pltEnableFlag;
	
	private String arriveFlag;
	
	private String transferFlag;
	
	private String deliverFlag;
	
	private long mailSequenceNumber;
	
	//Added by A-7929 as part of ICRD-346830
	private String onTimeDelivery;
	private String scanningWavedFlag;
	private String reqDeliveryDateAndTime;
	private String servicelevel;	
	private String transportSrvWindow;
//Added by A-8893
private String mailCompanyCode;
	
	public String getMailBagId() {
		return mailBagId;
	}

	public void setMailBagId(String mailBagId) {
		this.mailBagId = mailBagId;
	}

	public String getOriginDestPair() {
		return originDestPair;
	}

	public void setOriginDestPair(String originDestPair) {
		this.originDestPair = originDestPair;
	}

	public String getScanDate() {
		return scanDate;
	}

	public void setScanDate(String scanDate) {
		this.scanDate = scanDate;
	}

	public String getMailBagStatus() {
		return mailBagStatus;
	}

	public void setMailBagStatus(String mailBagStatus) {
		this.mailBagStatus = mailBagStatus;
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubClass() {
		return subClass;
	}

	public void setSubClass(String subClass) {
		this.subClass = subClass;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getDSN() {
		return DSN;
	}

	public void setDSN(String dSN) {
		DSN = dSN;
	}

	public String getRSN() {
		return RSN;
	}

	public void setRSN(String rSN) {
		RSN = rSN;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getWeightUnit() {
		return weightUnit;
	}

	public void setWeightUnit(String weightUnit) {
		this.weightUnit = weightUnit;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getTransfferCarrier() {
		return transfferCarrier;
	}

	public void setTransfferCarrier(String transfferCarrier) {
		this.transfferCarrier = transfferCarrier;
	}

	public String getConsignmentNumber() {
		return consignmentNumber;
	}

	public void setConsignmentNumber(String consignmentNumber) {
		this.consignmentNumber = consignmentNumber;
	}

	public String getSealNo() {
		return sealNo;
	}

	public void setSealNo(String sealNo) {
		this.sealNo = sealNo;
	}

	public String getDamageInfo() {
		return damageInfo;
	}

	public void setDamageInfo(String damageInfo) {
		this.damageInfo = damageInfo;
	}

	public String getBellyCartId() {
		return bellyCartId;
	}

	public void setBellyCartId(String bellyCartId) {
		this.bellyCartId = bellyCartId;
	}

	public String getAwb() {
		return awb;
	}

	public void setAwb(String awb) {
		this.awb = awb;
	}

	public String getPaCode() {
		return paCode;
	}

	public void setPaCode(String paCode) {
		this.paCode = paCode;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCarditNo() {
		return carditNo;
	}

	public void setCarditNo(String carditNo) {
		this.carditNo = carditNo;
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

	public String getPou() {
		return pou;
	}

	public void setPou(String pou) {
		this.pou = pou;
	}

	public boolean isCheckBoxSelect() {
		return checkBoxSelect;
	}

	public void setCheckBoxSelect(boolean checkBoxSelect) {
		this.checkBoxSelect = checkBoxSelect;
	}

	public String getReadyForDeliveryTime() {
		return readyForDeliveryTime;
	}

	public void setReadyForDeliveryTime(String readyForDeliveryTime) {
		this.readyForDeliveryTime = readyForDeliveryTime;
	}

	public String getReadyForDeliveryDate() {
		return readyForDeliveryDate;
	}

	public void setReadyForDeliveryDate(String readyForDeliveryDate) {
		this.readyForDeliveryDate = readyForDeliveryDate;
	}

	public String getRoutingAvlFlag() {
		return routingAvlFlag;
	}

	public void setRoutingAvlFlag(String routingAvlFlag) {
		this.routingAvlFlag = routingAvlFlag;
	}

	public String getTransitFlag() {
		return transitFlag;
	}

	public void setTransitFlag(String transitFlag) {
		this.transitFlag = transitFlag;
	}

	public String getPltEnableFlag() {
		return pltEnableFlag;
	}

	public void setPltEnableFlag(String pltEnableFlag) {
		this.pltEnableFlag = pltEnableFlag;
	}

	public String getArriveFlag() {
		return arriveFlag;
	}

	public void setArriveFlag(String arriveFlag) {
		this.arriveFlag = arriveFlag;
	}

	public String getTransferFlag() {
		return transferFlag;
	}

	public void setTransferFlag(String transferFlag) {
		this.transferFlag = transferFlag;
	}

	public String getDeliverFlag() {
		return deliverFlag;
	}

	public void setDeliverFlag(String deliverFlag) {
		this.deliverFlag = deliverFlag;
	}
	public long getMailSequenceNumber() {
		return mailSequenceNumber;
	}
	public void setMailSequenceNumber(long mailSequenceNumber) {
		this.mailSequenceNumber = mailSequenceNumber;
	}

	public String getStationWeight() {
		return stationWeight;
	}

	public void setStationWeight(String stationWeight) {
		this.stationWeight = stationWeight;
	}

	public String getStationUnit() {
		return stationUnit;
	}

	public void setStationUnit(String stationUnit) {
		this.stationUnit = stationUnit;
	}

	/**
	 * @return the shipmentPrefix
	 */
	public String getShipmentPrefix() {
		return shipmentPrefix;
	}

	/**
	 * @param shipmentPrefix the shipmentPrefix to set
	 */
	public void setShipmentPrefix(String shipmentPrefix) {
		this.shipmentPrefix = shipmentPrefix;
	}

	public String getOnTimeDelivery() {
		return onTimeDelivery;
	}

	public void setOnTimeDelivery(String onTimeDelivery) {
		this.onTimeDelivery = onTimeDelivery;
	}

	public String getScanningWavedFlag() {
		return scanningWavedFlag;
	}

	public void setScanningWavedFlag(String scanningWavedFlag) {
		this.scanningWavedFlag = scanningWavedFlag;
	}
	public String getReqDeliveryDateAndTime() {
		return reqDeliveryDateAndTime;
	}
	public void setReqDeliveryDateAndTime(String reqDeliveryDateAndTime) {
		this.reqDeliveryDateAndTime = reqDeliveryDateAndTime;
	}
	public String getServicelevel() {
		return servicelevel;
	}
	public void setServicelevel(String servicelevel) {
		this.servicelevel = servicelevel;
	}
	public String getTransportSrvWindow() {
		return transportSrvWindow;
	}
	public void setTransportSrvWindow(String transportSrvWindow) {
		this.transportSrvWindow = transportSrvWindow;
	}	 
public String getMailCompanyCode() {
		return mailCompanyCode;
	}
	public void setMailCompanyCode(String mailCompanyCode) {
		this.mailCompanyCode = mailCompanyCode;
	}

}
