/*
 * MRAProcessManagerForm.java Created on Jun 20, 2007	
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults;

import com.ibsplc.icargo.framework.client.daterange.notation.DateFieldId;
import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 *  
 * @author A-2270
 * 
 */
/*
 * Revision History
 * -------------------------------------------------------------------------
 * Revision 		Date 					Author 		Description
 * ------------------------------------------------------------------------- 
 * 0.1     		  Jun 20, 2007			A-2270		Created
 */
public class MRAProcessManagerForm extends ScreenModel {

	//private static final String BUNDLE = "processmanager";
	
	private String bundle = "mraprocessmanager";
	
	private static final String PRODUCT = "mail";

	private static final String SUBPRODUCT = "mra";

	private static final String SCREENID = "mailtracking.mra.defaults.processmanager";
	
	private String flightNumber;
	
	private String carrierCode;
	
	private String processOneTime;
	
	private String flightDate;
	//Added by A-7531 for ICRD-132508 starts
	private String fromDate;
	
	private String toDate;
	
	private String mailCategory;  
	
	private String mailSubclass;
	
	private String originOfficeOfExchange;
	
	private String origin;
	
	private String destination;
	
	private String destinationOfficeOfExchange;
	
	private String gpaCode;
	
	private String toAirlineCode;
	
	private String fromAirlineCode;
	//Added by A-7531 for ICRD-132508 starts

	//Added by a-7531 for icrd-132487
	private String prorateException;
	private String fromDateforProrate;
	private String toDateforProrate;
	private String originforProrate;
	private String destinationforProrate;
	private String mailCategoryforProrate;
	private String mailSubclassforProrate;

	private String clearancePeriod;
	//Added by A-4809 for ICRD-232299
	private String mailbag;
	private String mailFromDate;
	private String mailToDate;
	private String paCode;

	
	private String originOEReRate;
	private String destinationOEReRate;
	private String transferAirlineReRate;
	private String transferPAReRate;
	private String upliftAirportReRate;
	private String dischargeAirportReRate;
	
	private String transferAirlineException;
	private String transferPAException;
	private String upliftAirportException;
	private String dischargeAirportException;
	private String originAirportException;
	private String destinationAirportException;
	
	private String periodNumber;
	private String passBillingPeriodFrom;
	private String passBillingPeriodTo;
	
	private String completionFlag;
	private int totalInvoiceCount;
	private int finalizedInvoiceCount;
	private int nextFetchValue;
	
	
	
	/**
	 * 	Getter for originOEReRate 
	 *	Added by : A-8061 on 16-Feb-2021
	 * 	Used for :
	 */
	public String getOriginOEReRate() {
		return originOEReRate;
	}

	/**
	 *  @param originOEReRate the originOEReRate to set
	 * 	Setter for originOEReRate 
	 *	Added by : A-8061 on 16-Feb-2021
	 * 	Used for :
	 */
	public void setOriginOEReRate(String originOEReRate) {
		this.originOEReRate = originOEReRate;
	}

	/**
	 * 	Getter for destinationOEReRate 
	 *	Added by : A-8061 on 16-Feb-2021
	 * 	Used for :
	 */
	public String getDestinationOEReRate() {
		return destinationOEReRate;
	}

	/**
	 *  @param destinationOEReRate the destinationOEReRate to set
	 * 	Setter for destinationOEReRate 
	 *	Added by : A-8061 on 16-Feb-2021
	 * 	Used for :
	 */
	public void setDestinationOEReRate(String destinationOEReRate) {
		this.destinationOEReRate = destinationOEReRate;
	}

	/**
	 * 	Getter for transferAirlineReRate 
	 *	Added by : A-8061 on 16-Feb-2021
	 * 	Used for :
	 */
	public String getTransferAirlineReRate() {
		return transferAirlineReRate;
	}

	/**
	 *  @param transferAirlineReRate the transferAirlineReRate to set
	 * 	Setter for transferAirlineReRate 
	 *	Added by : A-8061 on 16-Feb-2021
	 * 	Used for :
	 */
	public void setTransferAirlineReRate(String transferAirlineReRate) {
		this.transferAirlineReRate = transferAirlineReRate;
	}

	/**
	 * 	Getter for transferPAReRate 
	 *	Added by : A-8061 on 16-Feb-2021
	 * 	Used for :
	 */
	public String getTransferPAReRate() {
		return transferPAReRate;
	}

	/**
	 *  @param transferPAReRate the transferPAReRate to set
	 * 	Setter for transferPAReRate 
	 *	Added by : A-8061 on 16-Feb-2021
	 * 	Used for :
	 */
	public void setTransferPAReRate(String transferPAReRate) {
		this.transferPAReRate = transferPAReRate;
	}

	/**
	 * 	Getter for upliftAirportReRate 
	 *	Added by : A-8061 on 16-Feb-2021
	 * 	Used for :
	 */
	public String getUpliftAirportReRate() {
		return upliftAirportReRate;
	}

	/**
	 *  @param upliftAirportReRate the upliftAirportReRate to set
	 * 	Setter for upliftAirportReRate 
	 *	Added by : A-8061 on 16-Feb-2021
	 * 	Used for :
	 */
	public void setUpliftAirportReRate(String upliftAirportReRate) {
		this.upliftAirportReRate = upliftAirportReRate;
	}

	/**
	 * 	Getter for dischargeAirportReRate 
	 *	Added by : A-8061 on 16-Feb-2021
	 * 	Used for :
	 */
	public String getDischargeAirportReRate() {
		return dischargeAirportReRate;
	}

	/**
	 *  @param dischargeAirportReRate the dischargeAirportReRate to set
	 * 	Setter for dischargeAirportReRate 
	 *	Added by : A-8061 on 16-Feb-2021
	 * 	Used for :
	 */
	public void setDischargeAirportReRate(String dischargeAirportReRate) {
		this.dischargeAirportReRate = dischargeAirportReRate;
	}

	/**
	 * 	Getter for transferAirlineException 
	 *	Added by : A-8061 on 16-Feb-2021
	 * 	Used for :
	 */
	public String getTransferAirlineException() {
		return transferAirlineException;
	}

	/**
	 *  @param transferAirlineException the transferAirlineException to set
	 * 	Setter for transferAirlineException 
	 *	Added by : A-8061 on 16-Feb-2021
	 * 	Used for :
	 */
	public void setTransferAirlineException(String transferAirlineException) {
		this.transferAirlineException = transferAirlineException;
	}

	/**
	 * 	Getter for transferPAException 
	 *	Added by : A-8061 on 16-Feb-2021
	 * 	Used for :
	 */
	public String getTransferPAException() {
		return transferPAException;
	}

	/**
	 *  @param transferPAException the transferPAException to set
	 * 	Setter for transferPAException 
	 *	Added by : A-8061 on 16-Feb-2021
	 * 	Used for :
	 */
	public void setTransferPAException(String transferPAException) {
		this.transferPAException = transferPAException;
	}

	/**
	 * 	Getter for upliftAirportException 
	 *	Added by : A-8061 on 16-Feb-2021
	 * 	Used for :
	 */
	public String getUpliftAirportException() {
		return upliftAirportException;
	}

	/**
	 *  @param upliftAirportException the upliftAirportException to set
	 * 	Setter for upliftAirportException 
	 *	Added by : A-8061 on 16-Feb-2021
	 * 	Used for :
	 */
	public void setUpliftAirportException(String upliftAirportException) {
		this.upliftAirportException = upliftAirportException;
	}

	/**
	 * 	Getter for dischargeAirportException 
	 *	Added by : A-8061 on 16-Feb-2021
	 * 	Used for :
	 */
	public String getDischargeAirportException() {
		return dischargeAirportException;
	}

	/**
	 *  @param dischargeAirportException the dischargeAirportException to set
	 * 	Setter for dischargeAirportException 
	 *	Added by : A-8061 on 16-Feb-2021
	 * 	Used for :
	 */
	public void setDischargeAirportException(String dischargeAirportException) {
		this.dischargeAirportException = dischargeAirportException;
	}

	/**
	 * 	Getter for originAirportException 
	 *	Added by : A-8061 on 16-Feb-2021
	 * 	Used for :
	 */
	public String getOriginAirportException() {
		return originAirportException;
	}

	/**
	 *  @param originAirportException the originAirportException to set
	 * 	Setter for originAirportException 
	 *	Added by : A-8061 on 16-Feb-2021
	 * 	Used for :
	 */
	public void setOriginAirportException(String originAirportException) {
		this.originAirportException = originAirportException;
	}

	/**
	 * 	Getter for destinationAirportException 
	 *	Added by : A-8061 on 16-Feb-2021
	 * 	Used for :
	 */
	public String getDestinationAirportException() {
		return destinationAirportException;
	}

	/**
	 *  @param destinationAirportException the destinationAirportException to set
	 * 	Setter for destinationAirportException 
	 *	Added by : A-8061 on 16-Feb-2021
	 * 	Used for :
	 */
	public void setDestinationAirportException(String destinationAirportException) {
		this.destinationAirportException = destinationAirportException;
	}
	


	private String filterMode;
	private String fromDateImportmail;
	private String toDateImportmail; 
	private String byPassWarning;
	
	

/**
 * 	Method		:	MRAProcessManagerForm.getGpaCode
 *	Added by 	:	A-7531 
 *	Parameters	:	@return 
 *	Return type	: 	String
 */
	public String getGpaCode() {
		return gpaCode;
	}

	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
	}
/**
 * 	Method		:	MRAProcessManagerForm.getToAirlineCode
 *	Added by 	:	A-7531 
 *	Parameters	:	@return 
 *	Return type	: 	String
 */
	public String getToAirlineCode() {
		return toAirlineCode;
	}

	public void setToAirlineCode(String toAirlineCode) {
		this.toAirlineCode = toAirlineCode;
	}
/**
 * 	Method		:	MRAProcessManagerForm.getFromAirlineCode
 *	Added by 	:	A-7531 
 *	Parameters	:	@return 
 *	Return type	: 	String
 */
	public String getFromAirlineCode() {
		return fromAirlineCode;
	}

	public void setFromAirlineCode(String fromAirlineCode) {
		this.fromAirlineCode = fromAirlineCode;
	}
	/**
	 * 	Method		:	MRAProcessManagerForm.getOriginOfficeOfExchange
	 *	Added by 	:	A-7531 o
	 *	Parameters	:	@return 
	 *	Return type	: 	String
	 */
	public String getOriginOfficeOfExchange() {
		return originOfficeOfExchange;
	}

	public void setOriginOfficeOfExchange(String originOfficeOfExchange) {
		this.originOfficeOfExchange = originOfficeOfExchange;
	}
   /**
    * 	Method		:	MRAProcessManagerForm.getDestinationOfficeOfExchange
    *	Added by 	:	A-7531 
    *	Parameters	:	@return 
    *	Return type	: 	String
    */
	public String getDestinationOfficeOfExchange() {
		return destinationOfficeOfExchange;
	}

	public void setDestinationOfficeOfExchange(String destinationOfficeOfExchange) {
		this.destinationOfficeOfExchange = destinationOfficeOfExchange;
	}
    /**
     * 	Method		:	MRAProcessManagerForm.getMailSubclass
     *	Added by 	:	A-7531 
     *	Parameters	:	@return 
     *	Return type	: 	String
     */
	public String getMailSubclass() {
		return mailSubclass;
	}

	public void setMailSubclass(String mailSubclass) {
		this.mailSubclass = mailSubclass;
	}
/**
 * 	Method		:	MRAProcessManagerForm.getMailCategory
 *	Added by 	:	A-7531 
 *	Parameters	:	@return 
 *	Return type	: 	String
 */
	public String getMailCategory() {
		return mailCategory;
	}

	public void setMailCategory(String mailCategory) {
		this.mailCategory = mailCategory;
	}
	/**
	 * 	Method		:	MRAProcessManagerForm.getFromDate
	 *	Added by 	:	A-7531 
	 *	Parameters	:	@return 
	 *	Return type	: 	String
	 */

	@DateFieldId(id="ProcessManagerReRateDateRange",fieldType="from")

		public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
 /**
  * 	Method		:	MRAProcessManagerForm.getToDate
  *	Added by 	:	A-7531 
  *	Parameters	:	@return 
  *	Return type	: 	String
  */

	@DateFieldId(id="ProcessManagerReRateDateRange",fieldType="to")

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	
		/**
	 * 
	 */
	public String getScreenId() {
		return SCREENID;
	}

	/**
	 * 
	 */
	public String getProduct() {
		return PRODUCT;
	}

	/**
	 * 
	 */
	public String getSubProduct() {
		return SUBPRODUCT;
	}

	/**
	 *  @return Returns the bundle.
	 */
	public String getBundle() {
		return this.bundle;
	}

	/**
	 * 
	 * @param bundle
	 */
	public void setBundle(String bundle) {
		this.bundle = bundle;
	}

	
	/**
	 * @return Returns the flightNumber.
	 */
	public String getFlightNumber() {
		return flightNumber;
	}

	/**
	 * @param flightNumber The flightNumber to set.
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	/**
	 * @return Returns the processOneTime.
	 */
	public String getProcessOneTime() {
		return processOneTime;
	}

	/**
	 * @param processOneTime The processOneTime to set.
	 */
	public void setProcessOneTime(String processOneTime) {
		this.processOneTime = processOneTime;
	}

	/**
	 * @return Returns the carrierCode.
	 */
	public String getCarrierCode() {
		return carrierCode;
	}

	/**
	 * @param carrierCode The carrierCode to set.
	 */
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	/**
	 * @return Returns the flightDate.
	 */
	public String getFlightDate() {
		return flightDate;
	}

	/**
	 * @param flightDate The flightDate to set.
	 */
	public void setFlightDate(String flightDate) {
		this.flightDate = flightDate;
	}
   /**
    * 	Method		:	MRAProcessManagerForm.getOrigin
    *	Added by 	:	A-7531 
    *	Parameters	:	@return 
    *	Return type	: 	String
    */
	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}
/**
 * 	Method		:	MRAProcessManagerForm.getDestination
 *	Added by 	:	A-7531 
 *	Parameters	:	@return 
 *	Return type	: 	String
 */
	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	@DateFieldId(id="ProcessManagerProrateDateRange",fieldType="from")
	public String getFromDateforProrate() {
		return fromDateforProrate;
	}

	public void setFromDateforProrate(String fromDateforProrate) {
		this.fromDateforProrate = fromDateforProrate;
	}
	@DateFieldId(id="ProcessManagerProrateDateRange",fieldType="to")
	public String getToDateforProrate() {
		return toDateforProrate;
	}

	public void setToDateforProrate(String toDateforProrate) {
		this.toDateforProrate = toDateforProrate;
	}

	public String getOriginforProrate() {
		return originforProrate;
	}

	public void setOriginforProrate(String originforProrate) {
		this.originforProrate = originforProrate;
	}

	public String getDestinationforProrate() {
		return destinationforProrate;
	}

	public void setDestinationforProrate(String destinationforProrate) {
		this.destinationforProrate = destinationforProrate;
	}

	public String getMailCategoryforProrate() {
		return mailCategoryforProrate;
	}

	public void setMailCategoryforProrate(String mailCategoryforProrate) {
		this.mailCategoryforProrate = mailCategoryforProrate;
	}

	public String getMailSubclassforProrate() {
		return mailSubclassforProrate;
	}

	public void setMailSubclassforProrate(String mailSubclassforProrate) {
		this.mailSubclassforProrate = mailSubclassforProrate;
	}

	public String getProrateException() {
		return prorateException;
	}

	public void setProrateException(String prorateException) {
		this.prorateException = prorateException;
	}

	public String getClearancePeriod() {
		return clearancePeriod;
	}

	public void setClearancePeriod(String clearancePeriod) {
		this.clearancePeriod = clearancePeriod;
	}
	
	public String getMailbag() {
		return mailbag;
	}
	public void setMailbag(String mailbag) {
		this.mailbag = mailbag;
	}
	@DateFieldId(id="ProcessManagerDateRange",fieldType="from")
	public String getMailFromDate() {
		return mailFromDate;
	}
	public void setMailFromDate(String mailFromDate) {
		this.mailFromDate = mailFromDate;
	}
	@DateFieldId(id="ProcessManagerDateRange",fieldType="to")
	public String getMailToDate() {
		return mailToDate;
	}
	public void setMailToDate(String mailToDate) {
		this.mailToDate = mailToDate;
	}
	public String getPaCode() {
		return paCode;
	}
	public void setPaCode(String paCode) {
		this.paCode = paCode;
	}

	/**
	 * 	Getter for filterMode 
	 *	Added by : A-8061 on 23-Oct-2020
	 * 	Used for :
	 */
	public String getFilterMode() {
		return filterMode;
	}

	/**
	 *  @param filterMode the filterMode to set
	 * 	Setter for filterMode 
	 *	Added by : A-8061 on 23-Oct-2020
	 * 	Used for :
	 */
	public void setFilterMode(String filterMode) {
		this.filterMode = filterMode;
	}

	/**
	 * 	Getter for fromDateImportmail 
	 *	Added by : A-8061 on 23-Oct-2020
	 * 	Used for :
	 */
	@DateFieldId(id="ImportMailDateRange",fieldType="from")
	public String getFromDateImportmail() {
		return fromDateImportmail;
	}

	/**
	 *  @param fromDateImportmail the fromDateImportmail to set
	 * 	Setter for fromDateImportmail 
	 *	Added by : A-8061 on 23-Oct-2020
	 * 	Used for :
	 */
	public void setFromDateImportmail(String fromDateImportmail) {
		this.fromDateImportmail = fromDateImportmail;
	}

	/**
	 * 	Getter for toDateImportmail 
	 *	Added by : A-8061 on 23-Oct-2020
	 * 	Used for :
	 */
	@DateFieldId(id="ImportMailDateRange",fieldType="to")
	public String getToDateImportmail() {
		return toDateImportmail;
	}

	/**
	 *  @param toDateImportmail the toDateImportmail to set
	 * 	Setter for toDateImportmail 
	 *	Added by : A-8061 on 23-Oct-2020
	 * 	Used for :
	 */
	public void setToDateImportmail(String toDateImportmail) {
		this.toDateImportmail = toDateImportmail;
	}  
    /**
	 * 	Getter for byPassWarning 
	 *	Added by : A-5219 on 20-Nov-2020
	 * 	Used for :
	 */
	public String getByPassWarning() {
		return byPassWarning;
	}

	/**
	 *  @param byPassWarning the byPassWarning to set
	 * 	Setter for byPassWarning 
	 *	Added by : A-5219 on 20-Nov-2020
	 * 	Used for :
	 */
	public void setByPassWarning(String byPassWarning) {
		this.byPassWarning = byPassWarning;
	}

	/**
	 * 	Getter for periodNumber 
	 *	Added by : A-8061 on 05-May-2021
	 * 	Used for :
	 */
	public String getPeriodNumber() {
		return periodNumber;
	}

	/**
	 *  @param periodNumber the periodNumber to set
	 * 	Setter for periodNumber 
	 *	Added by : A-8061 on 05-May-2021
	 * 	Used for :
	 */
	public void setPeriodNumber(String periodNumber) {
		this.periodNumber = periodNumber;
	}

	/**
	 * 	Getter for passBillingPeriodFrom 
	 *	Added by : A-8061 on 05-May-2021
	 * 	Used for :
	 */
	public String getPassBillingPeriodFrom() {
		return passBillingPeriodFrom;
	}

	/**
	 *  @param passBillingPeriodFrom the passBillingPeriodFrom to set
	 * 	Setter for passBillingPeriodFrom 
	 *	Added by : A-8061 on 05-May-2021
	 * 	Used for :
	 */
	public void setPassBillingPeriodFrom(String passBillingPeriodFrom) {
		this.passBillingPeriodFrom = passBillingPeriodFrom;
	}

	/**
	 * 	Getter for passBillingPeriodTo 
	 *	Added by : A-8061 on 05-May-2021
	 * 	Used for :
	 */
	public String getPassBillingPeriodTo() {
		return passBillingPeriodTo;
	}

	/**
	 *  @param passBillingPeriodTo the passBillingPeriodTo to set
	 * 	Setter for passBillingPeriodTo 
	 *	Added by : A-8061 on 05-May-2021
	 * 	Used for :
	 */
	public void setPassBillingPeriodTo(String passBillingPeriodTo) {
		this.passBillingPeriodTo = passBillingPeriodTo;
	}

	/**
	 * 	Getter for completionFlag 
	 *	Added by : A-8061 on 05-May-2021
	 * 	Used for :
	 */
	public String getCompletionFlag() {
		return completionFlag;
	}

	/**
	 *  @param completionFlag the completionFlag to set
	 * 	Setter for completionFlag 
	 *	Added by : A-8061 on 05-May-2021
	 * 	Used for :
	 */
	public void setCompletionFlag(String completionFlag) {
		this.completionFlag = completionFlag;
	}

	/**
	 * 	Getter for totalInvoiceCount 
	 *	Added by : A-8061 on 05-May-2021
	 * 	Used for :
	 */
	public int getTotalInvoiceCount() {
		return totalInvoiceCount;
	}

	/**
	 *  @param totalInvoiceCount the totalInvoiceCount to set
	 * 	Setter for totalInvoiceCount 
	 *	Added by : A-8061 on 05-May-2021
	 * 	Used for :
	 */
	public void setTotalInvoiceCount(int totalInvoiceCount) {
		this.totalInvoiceCount = totalInvoiceCount;
	}

	/**
	 * 	Getter for finalizedInvoiceCount 
	 *	Added by : A-8061 on 05-May-2021
	 * 	Used for :
	 */
	public int getFinalizedInvoiceCount() {
		return finalizedInvoiceCount;
	}

	/**
	 *  @param finalizedInvoiceCount the finalizedInvoiceCount to set
	 * 	Setter for finalizedInvoiceCount 
	 *	Added by : A-8061 on 05-May-2021
	 * 	Used for :
	 */
	public void setFinalizedInvoiceCount(int finalizedInvoiceCount) {
		this.finalizedInvoiceCount = finalizedInvoiceCount;
	}

	/**
	 * 	Getter for nextFetchValue 
	 *	Added by : A-8061 on 05-May-2021
	 * 	Used for :
	 */
	public int getNextFetchValue() {
		return nextFetchValue;
	}

	/**
	 *  @param nextFetchValue the nextFetchValue to set
	 * 	Setter for nextFetchValue 
	 *	Added by : A-8061 on 05-May-2021
	 * 	Used for :
	 */
	public void setNextFetchValue(int nextFetchValue) {
		this.nextFetchValue = nextFetchValue;
	}
}
