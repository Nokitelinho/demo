/*
 * PrintCommand.java Created on Dec 08, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.listinvoice.report;



//import com.ibsplc.icargo.framework.report.vo.ReportMetaData;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
//import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ChargingInvoiceFilterVO;
//import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDChargingInvoiceVO;
//import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.ListInvoiceForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;

 /**
  * 
  * @author A-2122
  *
  */
public class PrintCommand extends AbstractPrintCommand {

	private static final String REPORT_ID = "RPTLST034";
	private Log log = LogFactory.getLogger("List Invoice");
	private static final String TXNTYPE_ONETIME = "uld.defaults.TxnType";
	private static final String PARTYTYPE_ONETIME = "uld.defaults.PartyType";
	private static final String ACTION = "printULDChargingInvoice";
	private static final String PRODUCT = "uld";
	private static final String SUBPRODUCT = "defaults";
	private static final String PRINT_UNSUCCESSFUL = "normal-report-error-jsp";
	 private static final String SELECT_ALL = "ALL"; 
    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */

	public void execute(InvocationContext invocationContext) 
							throws CommandInvocationException {

		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributesVO  =  applicationSession.getLogonVO();
		//Collection<ErrorVO> errorVos= new ArrayList<ErrorVO>();

		ListInvoiceForm listInvoiceForm = (ListInvoiceForm)
											invocationContext.screenModel;
		/*Map<String, Collection<OneTimeVO>>  oneTimes=getOneTimeDetails();
		 Collection<OneTimeVO> txnType = new ArrayList<OneTimeVO>();

		if(oneTimes!=null){//to get onetime values
		txnType =	oneTimes.get(TXNTYPE_ONETIME);
		log.log(Log.FINE,"\n\n\n\n*************transactionTypes********"+
			txnType);	
		}*/
		//ULDDefaultsDelegate uldDefaultsDelegate = new ULDDefaultsDelegate();
		
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ChargingInvoiceFilterVO chargingInvoiceFilterVO =new ChargingInvoiceFilterVO();
		errors = loadInvoiceFilterFromForm(listInvoiceForm,chargingInvoiceFilterVO);
		
		listInvoiceForm.setDisplayPage(listInvoiceForm.getDisplayPage());
		listInvoiceForm.setLastPageNum(listInvoiceForm.getLastPageNum());  
		log.log(Log.FINE,
				"ListInvoicePrintCommand ~~~~~~chargingInvoiceFilterVO~~~",
				chargingInvoiceFilterVO);
		chargingInvoiceFilterVO.setCompanyCode(logonAttributesVO.getCompanyCode());
		/*Page<ULDChargingInvoiceVO> uldChargingInvoiceVOs=new Page<ULDChargingInvoiceVO>(
		new ArrayList<ULDChargingInvoiceVO>(), 0, 0, 0, 0, 0, false);
		try {
				uldChargingInvoiceVOs = uldDefaultsDelegate.
				listULDChargingInvoice(chargingInvoiceFilterVO,
						Integer.parseInt(listInvoiceForm.getDisplayPage())) ;
				
		}catch(BusinessDelegateException businessDelegateException){
//printStackTrrace()();
			errorVos = handleDelegateException(businessDelegateException);
    	}
    	log.log(Log.FINE,"\n\n--Obtained uldChargingInvoiceVOs from the server->"
    		 + uldChargingInvoiceVOs);
    	Collection<ULDChargingInvoiceVO> vos = new 
    									ArrayList<ULDChargingInvoiceVO>();
    	int pageSize = uldChargingInvoiceVOs.size();
    	log.log(Log.FINE,"Page Sizeis ------->>"+pageSize);
    	for(int i=0;i<pageSize;i++){
    		vos.add(uldChargingInvoiceVOs.get(i));
    		
    	}
    	if(chargingInvoiceFilterVO!=null){
    	getReportSpec().setReportId(REPORT_ID);
		ReportMetaData parameterMetaData = new ReportMetaData();
		parameterMetaData.setFieldNames(new String[]{"invoiceRefNumber",
				"invoicedToCode","transactionType","invoicedDateFrom",
				"invoicedDateTo"});
		getReportSpec().addParameterMetaData(parameterMetaData);
		getReportSpec().addParameter(chargingInvoiceFilterVO);
    	}
    	ReportMetaData reportMetaData = new ReportMetaData();

		reportMetaData.setColumnNames(new String[]{
		"INVREFNUM", "TXNTYP", "INVDAT", "INVCOD","DMRAMT"});
		reportMetaData.setFieldNames(new String[] {"invoiceRefNumber",
				"transactionType","invoicedDate", "invoicedToCode",
				"demurrageAmount"});*/
		//getReportSpec().setReportMetaData(reportMetaData);
		//getReportSpec().addExtraInfo(txnType);
		//getReportSpec().setData(vos);
		getReportSpec().setProductCode(PRODUCT);
		getReportSpec().setSubProductCode(SUBPRODUCT);
		getReportSpec().setReportId(REPORT_ID);
		getReportSpec().addFilterValue(chargingInvoiceFilterVO);
		getReportSpec().addFilterValue(listInvoiceForm.getDisplayPage());
		//getReportSpec().setPreview(true);
		getReportSpec().setResourceBundle("listinvoice");
		getReportSpec().setHttpServerBase(invocationContext.httpServerBase);
		getReportSpec().setAction(ACTION);
		
		log.log(Log.FINE, "\n\n\n----------REPORT_ID----->", REPORT_ID);
		generateReport();
		
		if(getErrors() != null && getErrors().size() > 0){
			
			invocationContext.addAllError(getErrors());
			invocationContext.target = PRINT_UNSUCCESSFUL;
			return;
		}

		log.log(Log.FINE,"\n\n\n----------AFTER GENERATE REPORT----");
		invocationContext.target = getTargetPage();
		log.log(Log.FINE, "\n\n\n----------report----->",
				invocationContext.target);
	}


/**
 * @param listInvoiceForm
 * @param chargingInvoiceFilterVO
 */
private Collection<ErrorVO> loadInvoiceFilterFromForm(ListInvoiceForm 
		listInvoiceForm,ChargingInvoiceFilterVO chargingInvoiceFilterVO) {
	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
	
	if((listInvoiceForm.getInvoiceRefNumber() != null &&
			listInvoiceForm.getInvoiceRefNumber().trim().length() > 0)) {
		chargingInvoiceFilterVO.setInvoiceRefNumber(listInvoiceForm.
										getInvoiceRefNumber().toUpperCase());
		
	}
	else
	{
		chargingInvoiceFilterVO.setInvoiceRefNumber("");
	}
	
	
	if((listInvoiceForm.getPartyType() != null &&
			listInvoiceForm.getPartyType().trim().length() > 0)) {
		//added by nisha for bufix on 10Jul08
		if(SELECT_ALL.equals(listInvoiceForm.getPartyType())){
			chargingInvoiceFilterVO.setPartyType("");
		}else{
			chargingInvoiceFilterVO.setPartyType(listInvoiceForm.getPartyType().toUpperCase());
		}
		//ends
	}
	else
	{
		chargingInvoiceFilterVO.setPartyType("");
	}
	
	
	if((listInvoiceForm.getTransactionType() != null &&
			listInvoiceForm.getTransactionType().trim().length() > 0)) {
		chargingInvoiceFilterVO.setTransactionType(listInvoiceForm.
											getTransactionType().toUpperCase());
		
	}
	else
	{
		chargingInvoiceFilterVO.setTransactionType("");
	}
	
	if((listInvoiceForm.getInvoicedToCode() != null &&
			listInvoiceForm.getInvoicedToCode().trim().length() > 0)) {
		chargingInvoiceFilterVO.setInvoicedToCode(listInvoiceForm.
											getInvoicedToCode().toUpperCase());
	
	}
	else
	{
		chargingInvoiceFilterVO.setInvoicedToCode("");
	}
	    	
	if((listInvoiceForm.getInvoicedDateFrom() != null &&
			listInvoiceForm.getInvoicedDateFrom().trim().length() > 0)) {
		LocalDate invoiceDateFrom = null;
		if(DateUtilities.isValidDate(listInvoiceForm.getInvoicedDateFrom(),
															"dd-MMM-yyyy")) {
			invoiceDateFrom =new LocalDate(getApplicationSession().getLogonVO().getAirportCode(),Location.ARP,false);
			invoiceDateFrom.setDate(listInvoiceForm.getInvoicedDateFrom());
			
		}
		else {
			ErrorVO error = new ErrorVO("uld.defaults.listinvoice.invaliddate",
					new Object[]{listInvoiceForm.getInvoicedDateFrom()});
			errors.add(error);
		}
		chargingInvoiceFilterVO.setInvoicedDateFrom(invoiceDateFrom);
		    	
	}
	
	if((listInvoiceForm.getInvoicedDateTo() != null &&
			listInvoiceForm.getInvoicedDateTo().trim().length() > 0)) {
		LocalDate invoiceDateTo = null;
		if(DateUtilities.isValidDate(listInvoiceForm.getInvoicedDateTo(),
															 "dd-MMM-yyyy")) {
			invoiceDateTo = new LocalDate(getApplicationSession().getLogonVO().getAirportCode(),Location.ARP,false);
			invoiceDateTo.setDate(listInvoiceForm.getInvoicedDateTo());
			
		}
		else {
			ErrorVO error = new ErrorVO("uld.defaults.listinvoice.invaliddate",
					new Object[]{listInvoiceForm.getInvoicedDateTo()});
			errors.add(error);
		}
		chargingInvoiceFilterVO.setInvoicedDateTo(invoiceDateTo);
		
	}
	
	return errors;
	
	
		
}

/**
 * This method is used to get the one time details
 * @return
 */
public Map<String, Collection<OneTimeVO>> getOneTimeDetails() {
	//server call for onetime
	log.entering("PrintCommand","getOneTimeDetails");
	Map<String, Collection<OneTimeVO>> oneTimes = null;
	Collection<ErrorVO> error = new ArrayList<ErrorVO>();
	try{
		Collection<String> fieldValues = new ArrayList<String>();
		
		fieldValues.add(TXNTYPE_ONETIME);
		fieldValues.add(PARTYTYPE_ONETIME);
		oneTimes =
			new SharedDefaultsDelegate().
			findOneTimeValues(
		   getApplicationSession().getLogonVO().getCompanyCode(),
		   fieldValues) ;
		
	}catch (BusinessDelegateException businessDelegateException) {
		businessDelegateException.getMessage();
		businessDelegateException.getMessage();
		error = handleDelegateException(businessDelegateException);
	}
	log.exiting("PrintCommand","getOneTimeDetails");
	return oneTimes;
}

}





