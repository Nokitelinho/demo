/*
 * MailbagEnquiryModel Created on Jun 08, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.model.mail.operations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.web.json.PageResult;
import com.ibsplc.icargo.framework.web.spring.model.AbstractScreenModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ContainerDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.DamagedMailbag;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.Mailbag;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailbagFilter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.PostalAdministrationModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.FlightValidation;
import com.ibsplc.icargo.presentation.web.model.shared.defaults.common.OneTime;

/**
 * 
 * 
 * Revision History Revision Date Author Description 0.1 Jun 08, 2018 A-2257
 * First draft
 */

public class MailbagEnquiryModel extends AbstractScreenModel {


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
	private static final String SCREENID = "mail.operations.ux.mailbagenquiry";
	
	private String companyCode;
	private String mailbagId;
	private String flightNumber;
	private String flightDate;
	private String carrierCode;
	private int carrierID;
	private String ooe;
	private String doe;
	private String mailCategoryCode;
	private String mailSubclass;
	private String year;
	private String despatchSerialNumber;
	private String receptacleSerialNumber;
	private String conDocNo;
	private String fromDate;
	private String toDate;
	private String paCode;
	private String uldNumber;
	private String originAirport;
	private String destAirport;
	private String mailStatus;
	private String airportCode;
	private String mailOrigin;
	private String mailDestination;
	private String dummyAirportForDomMail;
	private MailbagFilter mailbagFilter;
	
	private Map<String, Collection<OneTime>> oneTimeValues;
	private PageResult<Mailbag> mailbags;
	private Collection<MailbagVO> mailbagsVO;
	private Collection<Mailbag> selectedMailbags;
	private ContainerDetails selectedContainer;
	private Collection<ContainerDetails> containerDetails;
	
	//For damage listing
	private Collection<DamagedMailbag> damagedMailbags;
	
	
	private String displayPage="1";	
	
	private String originOE;
	private String destnOE;

	private String category;
	private String subClass;	
	private String dsn;
	private String rsn;
	private String currentStatus;
	private String port;	
	private String userId;
	private String containerType;
	private String containerNo;
	private boolean isDamaged;
	private String flightCarrierCode;	
	private String carditStatus="";		
	private String status;	
	private String fromScreen;		
	// filelds for offload popup
	private String offloadReason;
	private String offloadRemarks;
	
	
	
	private String transit;
	private String consigmentNumber;
	private String upuCode;
	//Added for ICRD-214795 starts
	private String reqDeliveryDate;
	private String reqDeliveryTime;
	
	//For Reassign popup
	private String assignToFlight;
	
	private String destination;
	private String destinationCity;
	
	private String scanDate;
	private String scanTime;
	
	//FOr flight Validation VO
	
	FlightValidation flightValidation;
	String duplicateFlightStatus;
	
	//For return mail popup
	
	private String returnMailFlag;
	private String returnPostalAdmin;	
	private String successFlag;
	private String paBuiltFlag;
	private String flagSBReturn;
	private ArrayList<PostalAdministrationModel> postalAdministrations;
	
	//For transfer popup
	private String printTransferManifestFlag;
	private String preassignFlag;
	private String transferManifestId;
	private String onTimeDelivery;//Added for	ICRD-323389
	private String ownAirlineCode; //Added for IASCB-34119
	private String upliftAirport;
	private Collection<String> partnerCarriers;
    private String showWarning;
    private Map<String, String> warningMessagesStatus;

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.ibsplc.icargo.framework.web.ScreenData#getScreenId()
	 * @return screenId
	 */
	public String getScreenId() {
		return SCREENID;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.ibsplc.icargo.framework.web.ScreenData#getProduct()
	 * @return product name
	 */
	public String getProduct() {
		return PRODUCT;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.ibsplc.icargo.framework.web.ScreenData#getSubProduct()
	 * @return subproduct name
	 */
	public String getSubProduct() {
		return SUBPRODUCT;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getMailbagId() {
		return mailbagId;
	}

	public void setMailbagId(String mailbagId) {
		this.mailbagId = mailbagId;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public String getFlightDate() {
		return flightDate;
	}

	public void setFlightDate(String flightDate) {
		this.flightDate = flightDate;
	}

	public String getCarrierCode() {
		return carrierCode;
	}

	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
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

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getDespatchSerialNumber() {
		return despatchSerialNumber;
	}

	public void setDespatchSerialNumber(String despatchSerialNumber) {
		this.despatchSerialNumber = despatchSerialNumber;
	}

	public String getReceptacleSerialNumber() {
		return receptacleSerialNumber;
	}

	public void setReceptacleSerialNumber(String receptacleSerialNumber) {
		this.receptacleSerialNumber = receptacleSerialNumber;
	}

	public String getConDocNo() {
		return conDocNo;
	}

	public void setConDocNo(String conDocNo) {
		this.conDocNo = conDocNo;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getPaCode() {
		return paCode;
	}

	public void setPaCode(String paCode) {
		this.paCode = paCode;
	}

	public String getUldNumber() {
		return uldNumber;
	}

	public void setUldNumber(String uldNumber) {
		this.uldNumber = uldNumber;
	}

	public String getOriginAirport() {
		return originAirport;
	}

	public void setOriginAirport(String originAirport) {
		this.originAirport = originAirport;
	}

	public String getDestAirport() {
		return destAirport;
	}

	public void setDestAirport(String destAirport) {
		this.destAirport = destAirport;
	}

	public String getMailStatus() {
		return mailStatus;
	}

	public void setMailStatus(String mailStatus) {
		this.mailStatus = mailStatus;
	}

	public String getAirportCode() {
		return airportCode;
	}

	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

	public String getMailOrigin() {
		return mailOrigin;
	}

	public void setMailOrigin(String mailOrigin) {
		this.mailOrigin = mailOrigin;
	}

	public String getMailDestination() {
		return mailDestination;
	}

	public void setMailDestination(String mailDestination) {
		this.mailDestination = mailDestination;
	}
	
	public Map<String, Collection<OneTime>> getOneTimeValues() {
		return oneTimeValues;
	}

	public void setOneTimeValues(Map<String, Collection<OneTime>> oneTimeValues) {
		this.oneTimeValues = oneTimeValues;
	}

	public PageResult<Mailbag> getMailbags() {
		return mailbags;
	}

	public void setMailbags(PageResult<Mailbag> mailbags) {
		this.mailbags = mailbags;
	}

	public Collection<MailbagVO> getMailbagsVO() {
		return mailbagsVO;
	}

	public void setMailbagsVO(Collection<MailbagVO> mailbagsVO) {
		this.mailbagsVO = mailbagsVO;
	}

	public String getDisplayPage() {
		return displayPage;
	}

	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}

	public String getOriginOE() {
		return originOE;
	}

	public void setOriginOE(String originOE) {
		this.originOE = originOE;
	}

	public String getDestnOE() {
		return destnOE;
	}

	public void setDestnOE(String destnOE) {
		this.destnOE = destnOE;
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

	public String getDsn() {
		return dsn;
	}

	public void setDsn(String dsn) {
		this.dsn = dsn;
	}

	public String getRsn() {
		return rsn;
	}

	public void setRsn(String rsn) {
		this.rsn = rsn;
	}

	public String getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getContainerType() {
		return containerType;
	}

	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}

	public String getContainerNo() {
		return containerNo;
	}

	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}

	public boolean isDamaged() {
		return isDamaged;
	}

	public void setDamaged(boolean isDamaged) {
		this.isDamaged = isDamaged;
	}

	public String getFlightCarrierCode() {
		return flightCarrierCode;
	}

	public void setFlightCarrierCode(String flightCarrierCode) {
		this.flightCarrierCode = flightCarrierCode;
	}

	public String getCarditStatus() {
		return carditStatus;
	}

	public void setCarditStatus(String carditStatus) {
		this.carditStatus = carditStatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFromScreen() {
		return fromScreen;
	}

	public void setFromScreen(String fromScreen) {
		this.fromScreen = fromScreen;
	}

	public String getOffloadReason() {
		return offloadReason;
	}

	public void setOffloadReason(String offloadReason) {
		this.offloadReason = offloadReason;
	}

	public String getOffloadRemarks() {
		return offloadRemarks;
	}

	public void setOffloadRemarks(String offloadRemarks) {
		this.offloadRemarks = offloadRemarks;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getDestinationCity() {
		return destinationCity;
	}

	public void setDestinationCity(String destinationCity) {
		this.destinationCity = destinationCity;
	}

	public String getTransit() {
		return transit;
	}

	public void setTransit(String transit) {
		this.transit = transit;
	}

	public String getConsigmentNumber() {
		return consigmentNumber;
	}

	public void setConsigmentNumber(String consigmentNumber) {
		this.consigmentNumber = consigmentNumber;
	}

	public String getUpuCode() {
		return upuCode;
	}

	public void setUpuCode(String upuCode) {
		this.upuCode = upuCode;
	}

	public String getReqDeliveryDate() {
		return reqDeliveryDate;
	}

	public void setReqDeliveryDate(String reqDeliveryDate) {
		this.reqDeliveryDate = reqDeliveryDate;
	}

	public String getReqDeliveryTime() {
		return reqDeliveryTime;
	}

	public void setReqDeliveryTime(String reqDeliveryTime) {
		this.reqDeliveryTime = reqDeliveryTime;
	}
	
	public MailbagFilter getMailbagFilter() {
		return mailbagFilter;
	}

	public void setMailbagFilter(MailbagFilter mailbagFilter) {
		this.mailbagFilter = mailbagFilter;
	}

	public Collection<Mailbag> getSelectedMailbags() {
		return selectedMailbags;
	}

	public void setSelectedMailbags(Collection<Mailbag> selectedMailbags) {
		this.selectedMailbags = selectedMailbags;
	}

	
	public ContainerDetails getSelectedContainer() {
		return selectedContainer;
	}

	public void setSelectedContainer(ContainerDetails selectedContainer) {
		this.selectedContainer = selectedContainer;
	}

	public String getAssignToFlight() {
		return assignToFlight;
	}

	public void setAssignToFlight(String assignToFlight) {
		this.assignToFlight = assignToFlight;
	}

	public String getScanDate() {
		return scanDate;
	}

	public void setScanDate(String scanDate) {
		this.scanDate = scanDate;
	}

	public String getScanTime() {
		return scanTime;
	}

	public void setScanTime(String scanTime) {
		this.scanTime = scanTime;
	}

	public FlightValidation getFlightValidation() {
		return flightValidation;
	}

	public void setFlightValidation(FlightValidation flightValidation) {
		this.flightValidation = flightValidation;
	}	

	public Collection<DamagedMailbag> getDamagedMailbags() {
		return damagedMailbags;
	}

	public void setDamagedMailbags(Collection<DamagedMailbag> damagedMailbags) {
		this.damagedMailbags = damagedMailbags;
	}	

	public String getReturnMailFlag() {
		return returnMailFlag;
	}

	public void setReturnMailFlag(String returnMailFlag) {
		this.returnMailFlag = returnMailFlag;
	}

	public String getReturnPostalAdmin() {
		return returnPostalAdmin;
	}

	public void setReturnPostalAdmin(String returnPostalAdmin) {
		this.returnPostalAdmin = returnPostalAdmin;
	}

	public String getSuccessFlag() {
		return successFlag;
	}

	public void setSuccessFlag(String successFlag) {
		this.successFlag = successFlag;
	}

	public String getPaBuiltFlag() {
		return paBuiltFlag;
	}

	public void setPaBuiltFlag(String paBuiltFlag) {
		this.paBuiltFlag = paBuiltFlag;
	}

	public String getFlagSBReturn() {
		return flagSBReturn;
	}

	public void setFlagSBReturn(String flagSBReturn) {
		this.flagSBReturn = flagSBReturn;
	}

	public String getPrintTransferManifestFlag() {
		return printTransferManifestFlag;
	}

	public void setPrintTransferManifestFlag(String printTransferManifestFlag) {
		this.printTransferManifestFlag = printTransferManifestFlag;
	}

	public String getPreassignFlag() {
		return preassignFlag;
	}

	public void setPreassignFlag(String preassignFlag) {
		this.preassignFlag = preassignFlag;
	}

	public String getTransferManifestId() {
		return transferManifestId;
	}

	public void setTransferManifestId(String transferManifestId) {
		this.transferManifestId = transferManifestId;
	}

	public String getDuplicateFlightStatus() {
		return duplicateFlightStatus;
	}

	public void setDuplicateFlightStatus(String duplicateFlightStatus) {
		this.duplicateFlightStatus = duplicateFlightStatus;
	}

	public int getCarrierID() {
		return carrierID;
	}

	public void setCarrierID(int carrierID) {
		this.carrierID = carrierID;
	}

	public Collection<ContainerDetails> getContainerDetails() {
		return containerDetails;
	}

	public void setContainerDetails(Collection<ContainerDetails> containerDetails) {
		this.containerDetails = containerDetails;
	}

	public ArrayList<PostalAdministrationModel> getPostalAdministrations() {
		return postalAdministrations;
	}
	public void setPostalAdministrations(ArrayList<PostalAdministrationModel> postalAdministrations) {
		this.postalAdministrations = postalAdministrations;
	}
	/**
	 * @return the onTimeDelivery
	 */
	public String getOnTimeDelivery() {
		return onTimeDelivery;
	}
	/**
	 * @param onTimeDelivery the onTimeDelivery to set
	 */
	public void setOnTimeDelivery(String onTimeDelivery) {
		this.onTimeDelivery = onTimeDelivery;
	}
	public String getOwnAirlineCode() {
		return ownAirlineCode;
	}

	public void setOwnAirlineCode(String ownAirlineCode) {
		this.ownAirlineCode = ownAirlineCode;
	}
	public Collection<String> getPartnerCarriers() {
		return partnerCarriers;
	}

	public void setPartnerCarriers(Collection<String> partnerCarriers) {
		this.partnerCarriers = partnerCarriers;
	}
	public String getUpliftAirport() {
		return upliftAirport;
	}

	public void setUpliftAirport(String upliftAirport) {
		this.upliftAirport = upliftAirport;
	}
	public String getShowWarning() {
		return showWarning;
	}
	public void setShowWarning(String showWarning) {
		this.showWarning = showWarning;
	}
	public Map<String, String> getWarningMessagesStatus() {
		return warningMessagesStatus;
	}
	public void setWarningMessagesStatus(Map<String, String> warningMessagesStatus) {
		this.warningMessagesStatus = warningMessagesStatus;
	}
	public String getDummyAirportForDomMail() {
		return dummyAirportForDomMail;
	}
	public void setDummyAirportForDomMail(String dummyAirportForDomMail) {
		this.dummyAirportForDomMail = dummyAirportForDomMail;
	}
	
	

}
