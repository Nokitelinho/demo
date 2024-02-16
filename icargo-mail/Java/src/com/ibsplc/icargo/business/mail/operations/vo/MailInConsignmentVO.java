/**
 * 
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author a-3109
 *
 */
public class MailInConsignmentVO extends AbstractVO {
	
	private int statedBags;
	//private double statedWeight;
	private Measure statedWeight;//added by A-7371
	private String uldNumber;
	private String mailId;
	private String mailCategoryCode;
	private String mailSubclass;
	private String receptacleSerialNumber;
	private String registeredOrInsuredIndicator;
	private String highestNumberedReceptacle;
	private String companyCode;
	private String consignmentNumber;
	private String paCode;
	private int consignmentSequenceNumber;
	private String dsn;
	private String originExchangeOffice;
	private String destinationExchangeOffice;
	private String mailClass;
	private int year;
	private long mailSequenceNumber;
	private double declaredValue;
	private LocalDate consignmentDate;
	private String airportCode;
	private int carrierId;
	//private double volume;
     private Measure volume;//added by A-7371
    // added for ICRD-212235 starts
    private int totalLetterBags;
    
 	private int totalParcelBags;
 	private Measure totalLetterWeight;
 	private String paBuiltFlag ;
 	private Measure totalParcelWeight;
 	//added for ICRD-212235 ends
	private LocalDate reqDeliveryTime;//Added as part of ICRD-214795
	private String displayUnit;//added by A-7371 for ICRD-234919
	private String mailOrigin;
	private String mailDestination;
	private String mailSource; // Added by A-4809 for ICRD-232299
	private String contractIDNumber;  //Added by A-7929 as part of IASCB-28260
	
	private LocalDate transWindowEndTime;
	private String mailServiceLevel;
	private String operation;
	private String mailbagJrnIdr;//addedas part of IASCB-48353
	private String mailSubClassGroup;
	private String sealNumber;
	private Measure totalEmsWeight;
	private int totalEmsBags;
	private Measure totalSVWeight;
	private int totalSVbags;
	private String shipmentPrefix;
	private String masterDocumentNumber;
    private String mailStatus;
    private int mailDuplicateNumber;
    private int sequenceNumberOfMailbag;
    private int mailBagDocumentOwnerIdr;
    private String keyCondition;
	public String getMailStatus() {
		return mailStatus;
	}
	public void setMailStatus(String mailStatus) {
		this.mailStatus = mailStatus;
	} 
	
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getMailServiceLevel() {
		return mailServiceLevel;
	}
	public void setMailServiceLevel(String mailServiceLevel) {
		this.mailServiceLevel = mailServiceLevel;
	}
	private boolean isAwbAttached;
	
	public String getContractIDNumber() {
		return contractIDNumber;
	}
	public void setContractIDNumber(String contractIDNumber) {
		this.contractIDNumber = contractIDNumber;
	}
	/**
	 * @return the carrierId
	 */
	public int getCarrierId() {
		return carrierId;
	}
	public boolean isAwbAttached() {
		return isAwbAttached;
	}
	public void setAwbAttached(boolean isAwbAttached) {
		this.isAwbAttached = isAwbAttached;
	}
	/**
	 * @param carrierId the carrierId to set
	 */
	public void setCarrierId(int carrierId) {
		this.carrierId = carrierId;
	}
	/**
	 * @return the consignmentDate
	 */
	public LocalDate getConsignmentDate() {
		return consignmentDate;
	}
	/**
	 * @param consignmentDate the consignmentDate to set
	 */
	public void setConsignmentDate(LocalDate consignmentDate) {
		this.consignmentDate = consignmentDate;
	}
	/**
	 * @return the airportCode
	 */
	public String getAirportCode() {
		return airportCode;
	}
	/**
	 * @param airportCode the airportCode to set
	 */
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}
	/**
	 * @return the declaredValue
	 */
	public double getDeclaredValue() {
		return declaredValue;
	}
	/**
	 * @param declaredValue the declaredValue to set
	 */
	public void setDeclaredValue(double declaredValue) {
		this.declaredValue = declaredValue;
	}
	/**
	 * @return the currencyCode
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}
	/**
	 * @param currencyCode the currencyCode to set
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	private String currencyCode;
	private String operationFlag;
	
	//private String strWeight;
	private Measure strWeight;//added by A-7371
	/**
	 * 
	 * @return statedWeight
	 */
	public Measure getStatedWeight() {
		return statedWeight;
	}
	/**
	 * 
	 * @param statedWeight
	 */
	public void setStatedWeight(Measure statedWeight) {
		this.statedWeight = statedWeight;
	}
	/**
	 * 
	 * @return volume
	 */ 
	public Measure getVolume() {
		return volume;
	}
	/**
	 * 
	 * @param volume
	 */
	public void setVolume(Measure volume) {
		this.volume = volume;
	}
	/**
	 * 
	 * @return strWeight
	 */
	public Measure getStrWeight() {
		return strWeight;
	}
	/**
	 * 
	 * @param strWeight
	 */
	public void setStrWeight(Measure strWeight) {
		this.strWeight = strWeight;
	}
	/**
	 * @return Returns the companyCode.
	 */
	public String getCompanyCode() {
		return companyCode;
	}
	/**
	 * @param companyCode The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	/**
	 * @return Returns the consignmentNumber.
	 */
	public String getConsignmentNumber() {
		return consignmentNumber;
	}
	/**
	 * @param consignmentNumber The consignmentNumber to set.
	 */
	public void setConsignmentNumber(String consignmentNumber) {
		this.consignmentNumber = consignmentNumber;
	}
	/**
	 * @return Returns the consignmentSequenceNumber.
	 */
	public int getConsignmentSequenceNumber() {
		return consignmentSequenceNumber;
	}
	/**
	 * @param consignmentSequenceNumber The consignmentSequenceNumber to set.
	 */
	public void setConsignmentSequenceNumber(int consignmentSequenceNumber) {
		this.consignmentSequenceNumber = consignmentSequenceNumber;
	}
	/**
	 * @return Returns the destinationExchangeOffice.
	 */
	public String getDestinationExchangeOffice() {
		return destinationExchangeOffice;
	}
	/**
	 * @param destinationExchangeOffice The destinationExchangeOffice to set.
	 */
	public void setDestinationExchangeOffice(String destinationExchangeOffice) {
		this.destinationExchangeOffice = destinationExchangeOffice;
	}
	/**
	 * @return Returns the dsn.
	 */
	public String getDsn() {
		return dsn;
	}
	/**
	 * @param dsn The dsn to set.
	 */
	public void setDsn(String dsn) {
		this.dsn = dsn;
	}
	/**
	 * @return Returns the highestNumberedReceptacle.
	 */
	public String getHighestNumberedReceptacle() {
		return highestNumberedReceptacle;
	}
	/**
	 * @param highestNumberedReceptacle The highestNumberedReceptacle to set.
	 */
	public void setHighestNumberedReceptacle(String highestNumberedReceptacle) {
		this.highestNumberedReceptacle = highestNumberedReceptacle;
	}
	/**
	 * @return Returns the mailCategoryCode.
	 */
	public String getMailCategoryCode() {
		return mailCategoryCode;
	}
	/**
	 * @param mailCategoryCode The mailCategoryCode to set.
	 */
	public void setMailCategoryCode(String mailCategoryCode) {
		this.mailCategoryCode = mailCategoryCode;
	}
	/**
	 * @return Returns the mailClass.
	 */
	public String getMailClass() {
		return mailClass;
	}
	/**
	 * @param mailClass The mailClass to set.
	 */
	public void setMailClass(String mailClass) {
		this.mailClass = mailClass;
	}
	/**
	 * @return Returns the mailId.
	 */
	public String getMailId() {
		return mailId;
	}
	/**
	 * @param mailId The mailId to set.
	 */
	public void setMailId(String mailId) {
		this.mailId = mailId;
	}
	/**
	 * @return Returns the mailSequenceNumber.
	 */
	public long getMailSequenceNumber() {
		return mailSequenceNumber;
	}
	/**
	 * @param mailSequenceNumber The mailSequenceNumber to set.
	 */
	public void setMailSequenceNumber(long mailSequenceNumber) {
		this.mailSequenceNumber = mailSequenceNumber;
	}
	/**
	 * @return Returns the mailSubclass.
	 */
	public String getMailSubclass() {
		return mailSubclass;
	}
	/**
	 * @param mailSubclass The mailSubclass to set.
	 */
	public void setMailSubclass(String mailSubclass) {
		this.mailSubclass = mailSubclass;
	}
	/**
	 * @return Returns the originExchangeOffice.
	 */
	public String getOriginExchangeOffice() {
		return originExchangeOffice;
	}
	/**
	 * @param originExchangeOffice The originExchangeOffice to set.
	 */
	public void setOriginExchangeOffice(String originExchangeOffice) {
		this.originExchangeOffice = originExchangeOffice;
	}
	/**
	 * @return Returns the paCode.
	 */
	public String getPaCode() {
		return paCode;
	}
	/**
	 * @param paCode The paCode to set.
	 */
	public void setPaCode(String paCode) {
		this.paCode = paCode;
	}
	/**
	 * @return Returns the receptacleSerialNumber.
	 */
	public String getReceptacleSerialNumber() {
		return receptacleSerialNumber;
	}
	/**
	 * @param receptacleSerialNumber The receptacleSerialNumber to set.
	 */
	public void setReceptacleSerialNumber(String receptacleSerialNumber) {
		this.receptacleSerialNumber = receptacleSerialNumber;
	}
	/**
	 * @return Returns the registeredOrInsuredIndicator.
	 */
	public String getRegisteredOrInsuredIndicator() {
		return registeredOrInsuredIndicator;
	}
	/**
	 * @param registeredOrInsuredIndicator The registeredOrInsuredIndicator to set.
	 */
	public void setRegisteredOrInsuredIndicator(String registeredOrInsuredIndicator) {
		this.registeredOrInsuredIndicator = registeredOrInsuredIndicator;
	}
	/**
	 * @return Returns the statedBags.
	 */
	public int getStatedBags() {
		return statedBags;
	}
	/**
	 * @param statedBags The statedBags to set.
	 */
	public void setStatedBags(int statedBags) {
		this.statedBags = statedBags;
	}
	/**
	 * @return Returns the statedWeight.
	 */
	/*public double getStatedWeight() {
		return statedWeight;
	}
	*//**
	 * @param statedWeight The statedWeight to set.
	 *//*
	public void setStatedWeight(double statedWeight) {
		this.statedWeight = statedWeight;
	}*/
	/**
	 * @return Returns the uldNumber.
	 */
	public String getUldNumber() {
		return uldNumber;
	}
	/**
	 * @param uldNumber The uldNumber to set.
	 */
	public void setUldNumber(String uldNumber) {
		this.uldNumber = uldNumber;
	}
	/**
	 * @return Returns the year.
	 */
	public int getYear() {
		return year;
	}
	/**
	 * @param year The year to set.
	 */
	public void setYear(int year) {
		this.year = year;
	}
	/**
	 * @return Returns the operationFlag.
	 */
	public String getOperationFlag() {
		return this.operationFlag;
	}
	/**
	 * @param operationFlag The operationFlag to set.
	 */
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}
	/**
	 * @return Returns the strWeight.
	 */
	/*public String getStrWeight() {
		return this.strWeight;
	}
	*//**
	 * @param strWeight The strWeight to set.
	 *//*
	public void setStrWeight(String strWeight) {
		this.strWeight = strWeight;
	}*/
	/**
	 * @param volume the volume to set
	 */
	/*public void setVolume(double volume) {
		this.volume = volume;
	}
	*//**
	 * @return the volume
	 *//*
	public double getVolume() {
		return volume;
	}*/

	/**
	 * 	Getter for reqDeliveryTime 
	 *	Added by : A-6245 on 26-Jul-2017
	 * 	Used for :
	 */
	public LocalDate getReqDeliveryTime() {
		return reqDeliveryTime;
	}
	/**
	 *  @param reqDeliveryTime the reqDeliveryTime to set
	 * 	Setter for reqDeliveryTime 
	 *	Added by : A-6245 on 26-Jul-2017
	 * 	Used for :
	 */
	public void setReqDeliveryTime(LocalDate reqDeliveryTime) {
		this.reqDeliveryTime = reqDeliveryTime;
	}
	/**
	 * 
	 * @return displayUnit
	 */
	public String getDisplayUnit() {
		return displayUnit;
	}
	/**
	 * 
	 * @param displayUnit the displayUnit to set
	 */
	public void setDisplayUnit(String displayUnit) {
		this.displayUnit = displayUnit;
	}
	/**
	 * @return the totalLetterBags
	 */
	public int getTotalLetterBags() {
		return totalLetterBags;
	}
	/**
	 * @param totalLetterBags the totalLetterBags to set
	 */
	public void setTotalLetterBags(int totalLetterBags) {
		this.totalLetterBags = totalLetterBags;
	}
	/**
	 * @return the totalParcelBags
	 */
	public int getTotalParcelBags() {
		return totalParcelBags;
	}
	
	/**
	 * @param totalParcelBags the totalParcelBags to set
	 */
	public void setTotalParcelBags(int totalParcelBags) {
		this.totalParcelBags = totalParcelBags;
	}
	/**
	 * @return the totalLetterWeight
	 */
	public Measure getTotalLetterWeight() {
		return totalLetterWeight;
	}
	/**
	 * @param totalLetterWeight the totalLetterWeight to set
	 */
	public void setTotalLetterWeight(Measure totalLetterWeight) {
		this.totalLetterWeight = totalLetterWeight;
	}
	/**
	 * @return the totalParcelWeight
	 */
	public Measure getTotalParcelWeight() {
		return totalParcelWeight;
	}
	/**
	 * @param totalParcelWeight the totalParcelWeight to set
	 */
	public void setTotalParcelWeight(Measure totalParcelWeight) {
		this.totalParcelWeight = totalParcelWeight;
	}
	
	/**
	 * @return the mailOrigin
	 */
	public String getMailOrigin() {
		return mailOrigin;
	}

	/**
	 * @param mailOrigin the MailOrigin to set
	 */
	public void setMailOrigin(String mailOrigin) {
		this.mailOrigin = mailOrigin;
	}

	/**
	 * @return the mailDestination
	 */
	public String getMailDestination() {
		return mailDestination;
	}

	/**
	 * @param mailDestination the MailDestination to set
	 */
	public void setMailDestination(String mailDestination) {
		this.mailDestination = mailDestination;
	}
	/**
	 * 
	 * 	Method		:	MailInConsignmentVO.getMailSource
	 *	Added by 	:	A-4809 on Nov 19, 2018
	 * 	Used for 	:   ICRD-232299
	 *	Parameters	:	@return 
	 *	Return type	: 	String
	 */
	public String getMailSource() {
		return mailSource;
	}
	public void setMailSource(String mailSource) {
		this.mailSource = mailSource;
	}
	/**
	 * @return the transWindowEndTime
	 */
	public LocalDate getTransWindowEndTime() {
		return transWindowEndTime;
	}
	/**
	 * @param transWindowEndTime the transWindowEndTime to set
	 */
	public void setTransWindowEndTime(LocalDate transWindowEndTime) {
		this.transWindowEndTime = transWindowEndTime;
	}
	/**
	 * 
	 * @return mailbagJrnIdr
	 */
	public String getMailbagJrnIdr() {
		return mailbagJrnIdr;
	}
	/**
	 * 
	 * @param mailbagJrnIdr
	 */
	public void setMailbagJrnIdr(String mailbagJrnIdr) {
		this.mailbagJrnIdr = mailbagJrnIdr;
	}
	public String getPaBuiltFlag() {
		return paBuiltFlag;
	}
	public void setPaBuiltFlag(String paBuiltFlag) {
		this.paBuiltFlag = paBuiltFlag;
	}
	public String getMailSubClassGroup() {
		return mailSubClassGroup;
	}
	public void setMailSubClassGroup(String mailSubClassGroup) {
		this.mailSubClassGroup = mailSubClassGroup;
	}
	public String getSealNumber() {
		return sealNumber;
	}
	public void setSealNumber(String sealNumber) {
		this.sealNumber = sealNumber;
	}
	/**
	 * 	Getter for totalEmsWeight 
	 *	Added by : A-5219 on 26-Nov-2020
	 * 	Used for :
	 */
	public Measure getTotalEmsWeight() {
		return totalEmsWeight;
	}
	/**
	 *  @param totalEmsWeight the totalEmsWeight to set
	 * 	Setter for totalEmsWeight 
	 *	Added by : A-5219 on 26-Nov-2020
	 * 	Used for :
	 */
	public void setTotalEmsWeight(Measure totalEmsWeight) {
		this.totalEmsWeight = totalEmsWeight;
	}
	/**
	 * 	Getter for totalEmsBags 
	 *	Added by : A-5219 on 26-Nov-2020
	 * 	Used for :
	 */
	public int getTotalEmsBags() {
		return totalEmsBags;
	}
	/**
	 *  @param totalEmsBags the totalEmsBags to set
	 * 	Setter for totalEmsBags 
	 *	Added by : A-5219 on 26-Nov-2020
	 * 	Used for :
	 */
	public void setTotalEmsBags(int totalEmsBags) {
		this.totalEmsBags = totalEmsBags;
	}
	/**
	 * 	Getter for totalSVWeight 
	 *	Added by : A-5219 on 02-Dec-2020
	 * 	Used for :
	 */
	public Measure getTotalSVWeight() {
		return totalSVWeight;
	}
	/**
	 *  @param totalSVWeight the totalSVWeight to set
	 * 	Setter for totalSVWeight 
	 *	Added by : A-5219 on 02-Dec-2020
	 * 	Used for :
	 */
	public void setTotalSVWeight(Measure totalSVWeight) {
		this.totalSVWeight = totalSVWeight;
	}
	/**
	 * 	Getter for totalSVbags 
	 *	Added by : A-5219 on 02-Dec-2020
	 * 	Used for :
	 */
	public int getTotalSVbags() {
		return totalSVbags;
	}
	/**
	 *  @param totalSVbags the totalSVbags to set
	 * 	Setter for totalSVbags 
	 *	Added by : A-5219 on 02-Dec-2020
	 * 	Used for :
	 */
	public void setTotalSVbags(int totalSVbags) {
		this.totalSVbags = totalSVbags;
	}
	/**
	 * 	Getter for shipmentPrefix 
	 *	Added by : A-9998 on 23-Feb-2022
	 * 	Used for :
	 */
	public String getShipmentPrefix() {
		return shipmentPrefix;
	}
	/**
	 *  @param shipmentPrefix the shipmentPrefix to set
	 * 	Setter for shipmentPrefix 
	 *	Added by : A-9998 on 23-Feb-2022
	 * 	Used for :
	 */
	public void setShipmentPrefix(String shipmentPrefix) {
		this.shipmentPrefix = shipmentPrefix;
	}
	/**
	 * 	Getter for masterDocumentNumber 
	 *	Added by : A-9998 on 23-Feb-2022
	 * 	Used for :
	 */
	public String getMasterDocumentNumber() {
		return masterDocumentNumber;
	}
	
	
	/**
	 *  @param masterDocumentNumber the masterDocumentNumber to set
	 * 	Setter for masterDocumentNumber 
	 *	Added by : A-9998 on 23-Feb-2022
	 * 	Used for :
	 */
	
	public void setMasterDocumentNumber(String masterDocumentNumber) {
		this.masterDocumentNumber = masterDocumentNumber;
	}
	public int getMailDuplicateNumber() {
		return mailDuplicateNumber;
	}
	public void setMailDuplicateNumber(int mailDuplicateNumber) {
		this.mailDuplicateNumber = mailDuplicateNumber;
	}
	public int getSequenceNumberOfMailbag() {
		return sequenceNumberOfMailbag;
	}
	public void setSequenceNumberOfMailbag(int sequenceNumberOfMailbag) {
		this.sequenceNumberOfMailbag = sequenceNumberOfMailbag;
	}
	public int getMailBagDocumentOwnerIdr() {
		return mailBagDocumentOwnerIdr;
	}
	public void setMailBagDocumentOwnerIdr(int mailBagDocumentOwnerIdr) {
		this.mailBagDocumentOwnerIdr = mailBagDocumentOwnerIdr;
	}
	public String getKeyCondition() {
		return keyCondition;
	}
	public void setKeyCondition(String keyCondition) {
		this.keyCondition = keyCondition;
	}
	
}
