/*
 * PrintCommand.java Created on Dec 08, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.listuldtransaction.report;


//import java.util.ArrayList;
//import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionFilterVO;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.ListULDTransactionForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

 /**
  * 
  * @author A-2122
  *
  */
public class PrintCommand extends AbstractPrintCommand {

	private static final String REPORT_ID = "RPTLST048";
	private Log log = LogFactory.getLogger("Loan Borrow ULD");
	
		
	/*private static final String TXNTYPE_ONETIME = "uld.defaults.TxnType";
    private static final String PARTYTYPE_ONETIME = "uld.defaults.PartyType";
    private static final String TXNSTATUS_ONETIME = "uld.defaults.transactionStatus";
    private static final String ACCESSCODE_ONETIME = "uld.defaults.accessoryCode";
    */
	
    private static final String ACTION = "printLoanBorrowULD";
    private static final String PRINT_UNSUCCESSFUL = "normal-report-error-jsp";
	
    private static final String forLoadTesting = "N";
	
    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */

public void execute(InvocationContext invocationContext) 
							throws CommandInvocationException {
//Commented by Manaf for INT ULD510
//Collection<ErrorVO> errorVos= new ArrayList<ErrorVO>();

ListULDTransactionForm listULDTransactionForm =
	(ListULDTransactionForm) invocationContext.screenModel;
	/*	
Map<String, Collection<OneTimeVO>>  oneTimes=getOneTimeDetails();
Collection<OneTimeVO> txnType = new ArrayList<OneTimeVO>();
Collection<OneTimeVO> partyType = new ArrayList<OneTimeVO>();
Collection<OneTimeVO> txnStatus = new ArrayList<OneTimeVO>();
Collection<OneTimeVO> accessCode = new ArrayList<OneTimeVO>();
if(oneTimes!=null){//to get onetime values
	txnType =	oneTimes.get(TXNTYPE_ONETIME);
	log.log(Log.FINE,"\n\n\n\n*************transactionTypes********"+
			txnType);	
	partyType = oneTimes.get(PARTYTYPE_ONETIME);
	log.log(Log.FINE,"\n\n\n\n*************partyType********"+
			partyType);
	txnStatus = oneTimes.get(TXNSTATUS_ONETIME);
	log.log(Log.FINE,"\n\n\n\n*************transactionStatus********"+
			txnStatus);
	accessCode = oneTimes.get(ACCESSCODE_ONETIME);
	log.log(Log.FINE,"\n\n\n\n*************accessoryCode********"+
			accessCode);
		}
		*/
		TransactionFilterVO transactionFilterVO =new TransactionFilterVO();
		
transactionFilterVO = 	makeFilter(listULDTransactionForm);
	log.log(Log.FINE, "ListULDTransactionCommand ~~~~~~transactionFilterVO~~~",
			transactionFilterVO);
		ReportSpec reportSpec = getReportSpec();
		reportSpec.setReportId(REPORT_ID);
		reportSpec.setProductCode(listULDTransactionForm.getProduct());
		reportSpec.setSubProductCode(listULDTransactionForm.getSubProduct());
		reportSpec.setPreview(true);
		reportSpec.setHttpServerBase(invocationContext.httpServerBase);
		reportSpec.addFilterValue(transactionFilterVO);
		reportSpec.setResourceBundle("loanBorrowDetailsEnquiryResources");
		reportSpec.setAction(ACTION);
generateReport();

		if(getErrors() != null && getErrors().size() > 0){
			invocationContext.addAllError(getErrors());
			invocationContext.target = PRINT_UNSUCCESSFUL;
			return;
		}
		
		log.exiting("LOANBORROW ULD","PrintCommand exit");
		
invocationContext.target = getTargetPage();
log.log(Log.FINE, "\n\n\n----------report----->", invocationContext.target);
}

/**
 * Method to make filter VO
 * @param listULDTransactionForm
 * @return transactionFilterVO
 */
private TransactionFilterVO makeFilter
(ListULDTransactionForm listULDTransactionForm) {
	log.entering("PrintCommand","makeFilter");
	ApplicationSessionImpl applicationSession = getApplicationSession();
	LogonAttributes logonAttributesVO  =  applicationSession.getLogonVO();
	TransactionFilterVO transactionFilterVO =new TransactionFilterVO();
	
	LocalDate txntodt = null;
	LocalDate txnfromdt = null;
	LocalDate retodt = null;
	LocalDate refromdt = null;
	
	if(logonAttributesVO.isAirlineUser()){
		listULDTransactionForm.setEnquiryDisableStatus("airline");
	}
	else{
		transactionFilterVO.setTransactionStationCode(logonAttributesVO.getAirportCode());
		listULDTransactionForm.setEnquiryDisableStatus("GHA");
	}
    //changed for ICRD-31853
	if(!("").equalsIgnoreCase(listULDTransactionForm.getTxnStation()) 
			&& listULDTransactionForm.getTxnStation() != null){
		txntodt = new LocalDate(listULDTransactionForm.getTxnStation()
				.toUpperCase(),Location.ARP,true);		
    	txnfromdt = new LocalDate(listULDTransactionForm.getTxnStation()
    			.toUpperCase(),Location.ARP,true);
	}else{
		txntodt = new LocalDate(logonAttributesVO.getAirportCode(),Location.ARP,true);		
    	txnfromdt = new LocalDate(logonAttributesVO.getAirportCode(),Location.ARP,true);
	}
	if(!("").equalsIgnoreCase(listULDTransactionForm.getReturnStation()) && listULDTransactionForm.getReturnStation() != null){
		retodt = new LocalDate(listULDTransactionForm.getReturnStation().toUpperCase(),Location.ARP,false);
    	refromdt = new LocalDate(listULDTransactionForm.getReturnStation().toUpperCase(),Location.ARP,false);
	}else{
		retodt = new LocalDate(logonAttributesVO.getAirportCode(),Location.ARP,false);
    	refromdt = new LocalDate(logonAttributesVO.getAirportCode(),Location.ARP,false);
	}
	 transactionFilterVO.
	 		setCompanyCode(logonAttributesVO.getCompanyCode());
	if(!("").equalsIgnoreCase(listULDTransactionForm.getUldNum()) && 
			listULDTransactionForm.getUldNum() != null){
	         transactionFilterVO.setUldNumber
	         			(listULDTransactionForm.getUldNum().toUpperCase());
	}
	if(!("").equalsIgnoreCase(listULDTransactionForm.getUldTypeCode()) && 
			listULDTransactionForm.getUldTypeCode() != null){
			transactionFilterVO.setUldTypeCode
						(listULDTransactionForm.getUldTypeCode().trim().toUpperCase());
	}
	if(!("").equalsIgnoreCase(listULDTransactionForm.getAccessoryCode()) && 
			listULDTransactionForm.getAccessoryCode() != null){
			transactionFilterVO.setAccessoryCode
						(listULDTransactionForm.getAccessoryCode().trim());
	}
	if(!("").equalsIgnoreCase(listULDTransactionForm.getTxnType()) && 
			listULDTransactionForm.getTxnType() != null){
			transactionFilterVO.setTransactionType
						(listULDTransactionForm.getTxnType().trim());
	}
	if(!("").equalsIgnoreCase(listULDTransactionForm.getTxnStatus()) &&
			listULDTransactionForm.getTxnStatus() != null){
			transactionFilterVO.setTransactionStatus
						(listULDTransactionForm.getTxnStatus().trim());
	}
	if(!("").equalsIgnoreCase(listULDTransactionForm.getPartyType()) && 
			listULDTransactionForm.getPartyType() != null){
			transactionFilterVO.setPartyType
						(listULDTransactionForm.getPartyType().trim());
	}
	if(!("").equalsIgnoreCase(listULDTransactionForm.getFromPartyCode()) && 
			listULDTransactionForm.getFromPartyCode() != null){
			transactionFilterVO.setFromPartyCode
						(listULDTransactionForm.getFromPartyCode().trim().toUpperCase());
	}
	if(!("").equalsIgnoreCase(listULDTransactionForm.getToPartyCode()) && 
			listULDTransactionForm.getToPartyCode() != null){
			transactionFilterVO.setToPartyCode
						(listULDTransactionForm.getToPartyCode().trim().toUpperCase());
	}
	if(!("").equalsIgnoreCase(listULDTransactionForm.getTxnStation()) && 
			listULDTransactionForm.getTxnStation() != null){
			transactionFilterVO.setTransactionStationCode
						(listULDTransactionForm.getTxnStation().trim().toUpperCase());
	}
	else
	{
		transactionFilterVO.setTransactionStationCode("");
	}
	if(!("").equalsIgnoreCase(listULDTransactionForm.getReturnStation()) && 
			listULDTransactionForm.getReturnStation() != null){
			transactionFilterVO.setReturnedStationCode
					  (listULDTransactionForm.getReturnStation().trim().toUpperCase());
	}
	if(!("").equalsIgnoreCase(listULDTransactionForm.getTxnFromDate()) && 
			listULDTransactionForm.getTxnFromDate() != null){
		//added for ICRD-31853
		String txnFrmDate = listULDTransactionForm.getTxnFromDate();
		StringBuilder txnFrmDateAndTime = new StringBuilder(txnFrmDate);
		String txnFrmTime = listULDTransactionForm.getTxnFrmTime();
		if (!txnFrmTime.contains(":")) {
			txnFrmTime = txnFrmTime.concat(":00");
		}
		transactionFilterVO.setStrTxnFrmTime(txnFrmTime);
		 txnFrmDateAndTime.append(" ").append(txnFrmTime).append(":00");				
		transactionFilterVO.setTxnFromDate(txntodt
				.setDateAndTime(txnFrmDateAndTime.toString()));
		//added for ICRD-31853 ends
			transactionFilterVO.setStrTxnFromDate
						(listULDTransactionForm.getTxnFromDate());
	}
	else
	{
		transactionFilterVO.setStrTxnFromDate("");
	}
	if(!("").equalsIgnoreCase(listULDTransactionForm.getTxnToDate()) && 
			listULDTransactionForm.getTxnToDate() != null){
		//added for ICRD-31853
		String txnToDate = listULDTransactionForm.getTxnToDate();
		StringBuilder txnToDateAndTime = new StringBuilder(txnToDate);
		String txnToTime = listULDTransactionForm.getTxnToTime();
		if (!txnToTime.contains(":")) {
			txnToTime = txnToTime.concat(":00");
		}
		txnToDateAndTime.append(" ").append(txnToTime ).append(":00");
		transactionFilterVO.setTxnToDate(txnfromdt
				.setDateAndTime(txnToDateAndTime.toString()));
		//added for ICRD-31853 ends
			transactionFilterVO.setStrTxnToDate
						(listULDTransactionForm.getTxnToDate());
	}
	else
	{
		transactionFilterVO.setStrTxnToDate("");
	}
	if(!("").equalsIgnoreCase(listULDTransactionForm.getReturnFromDate()) && 
			listULDTransactionForm.getReturnFromDate() != null){
			transactionFilterVO.setReturnFromDate(retodt.setDate
			(listULDTransactionForm.getReturnFromDate()));
			transactionFilterVO.setStrReturnFromDate
						(listULDTransactionForm.getReturnFromDate());
	}
	else
	{
		transactionFilterVO.setStrReturnFromDate("");
	}
	if(!("").equalsIgnoreCase(listULDTransactionForm.getReturnToDate()) && 
			listULDTransactionForm.getReturnToDate() != null){
			transactionFilterVO.setReturnToDate(refromdt.setDate
			  (listULDTransactionForm.getReturnToDate()));
			transactionFilterVO.setStrReturnToDate
						(listULDTransactionForm.getReturnToDate());
	}
	else
	{
		transactionFilterVO.setStrReturnToDate("");
	}
	//added by A-4443 for bug icrd-3608 starts
	if(!("").equalsIgnoreCase(listULDTransactionForm.getMucStatus()) && 
			listULDTransactionForm.getMucStatus()!=null){
		transactionFilterVO.setMucStatus(listULDTransactionForm.getMucStatus());
	}else{
		transactionFilterVO.setMucStatus("");
	}
	if(!("").equalsIgnoreCase(listULDTransactionForm.getControlReceiptNoPrefix()) && 
			listULDTransactionForm.getControlReceiptNoPrefix()!=null){
		transactionFilterVO.setPrefixControlReceiptNo(listULDTransactionForm.getControlReceiptNoPrefix());
	}
	else{
		transactionFilterVO.setPrefixControlReceiptNo("");
	}
	if(!("").equalsIgnoreCase(listULDTransactionForm.getControlReceiptNoMid()) && 
			listULDTransactionForm.getControlReceiptNoMid()!=null){
		transactionFilterVO.setMidControlReceiptNo(listULDTransactionForm.getControlReceiptNoMid());
	}
	else{
		transactionFilterVO.setMidControlReceiptNo("");
	}
	if(!("").equalsIgnoreCase(listULDTransactionForm.getControlReceiptNo()) && 
			listULDTransactionForm.getControlReceiptNo()!=null){
		transactionFilterVO.setControlReceiptNo(listULDTransactionForm.getControlReceiptNo());
	}
	else{
		transactionFilterVO.setControlReceiptNo("");
	}
	String toDisplayPage = listULDTransactionForm.getDisplayPage();
	int displayPage = Integer.parseInt(toDisplayPage);	
	transactionFilterVO.setPageNumber(displayPage);
	log.exiting("PrintCommand","makeFilter");
	return transactionFilterVO;    	
}
/**
 * This method is used to get the one time details
 * @return
 */
	/*public Map<String, Collection<OneTimeVO>> getOneTimeDetails() {
			//server call for onetime
	log.entering("PrintCommand","getOneTimeDetails");
	Map<String, Collection<OneTimeVO>> oneTimes = null;
	Collection<ErrorVO> error = new ArrayList<ErrorVO>();
	try{
		Collection<String> fieldValues = new ArrayList<String>();
		
		fieldValues.add(TXNTYPE_ONETIME);
		fieldValues.add(PARTYTYPE_ONETIME);
		fieldValues.add(TXNSTATUS_ONETIME);
		fieldValues.add(ACCESSCODE_ONETIME);
		oneTimes =
			new SharedDefaultsDelegate().
			findOneTimeValues(
		   getApplicationSession().getLogonVO().getCompanyCode(),
		   fieldValues) ;
		
	}catch (BusinessDelegateException businessDelegateException) {
//printStackTrrace()();
//printStackTrrace()();
		error = handleDelegateException(businessDelegateException);
	}
	log.exiting("PrintCommand","getOneTimeDetails");
	return oneTimes;
	}*/

}





