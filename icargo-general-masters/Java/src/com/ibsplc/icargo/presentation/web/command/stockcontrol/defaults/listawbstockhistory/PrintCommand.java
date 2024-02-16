/*
 * PrintCommand.java Created on Feb 07, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.listawbstockhistory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRangeFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ListStockRangeHistorySession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ListAwbStockHistoryForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author a-2870
 *
 */
public class PrintCommand extends AbstractPrintCommand {

	private Log log = LogFactory.getLogger("AWB STOCK HISTORY PrintCommand");

	private static final String REPORT_ID_VIEWSTOCKRANGEHISTORY = "RPRSTK005";

	private static final String ACTION_VIEWSTOCKRANGEHISTORY = "findStockRangeHistoryReport";

	private static final String ERROR_ERR = "normal-report-error-jsp";
	//ICRD-3082 added by a-4562 STARTS
	private static final String STOCKSTATUS_ONETIME = "stockcontrol.defaults.stockstatus";
	private static final String AWBTYPE_ONETIME = "stockcontrol.defaults.awbtype";
	private static final String STOCKUTILIZATION_STATUS_ONETIME = "stockcontrol.defaults.stockutilizationstatus";
	//ICRD-3082 ENDS
	/**
	 * @author a-2870
	 * @param invocationContext
	 * @return  
	 * @exception CommandInvocationException
	 */ 
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("listawbstockhistory's PrintCommand RRRR", "execute");
		
		
		Collection<ErrorVO> errors = null;
		ListAwbStockHistoryForm listAwbStockHistoryForm = (ListAwbStockHistoryForm) invocationContext.screenModel;
		ListStockRangeHistorySession session = getScreenSession(
				"stockcontrol.defaults",
				"stockcontrol.defaults.listawbstockhistory");

		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributesVO = applicationSession.getLogonVO();
		LocalDate start = new LocalDate(LocalDate.NO_STATION, Location.NONE,
				false);
		LocalDate end = new LocalDate(LocalDate.NO_STATION, Location.NONE,
				false);
		//icrd-3082 added by a-4562 starts
		Map<String, Collection<OneTimeVO>> oneTimeValues = getOneTimeValues();
		Collection<OneTimeVO> stockstatus = null;
		Collection<OneTimeVO> awbtype = null;
		Collection<OneTimeVO> stockutilizationstatus = null;
		//icrd-3082 added by a-4562 ends
		
		/*errors = validateForm(listAwbStockHistoryForm, session);*/
		// Collection<ErrorVO> errors = null;
		
		
		if (oneTimeValues != null) {
			stockstatus = oneTimeValues.get(STOCKSTATUS_ONETIME);
			awbtype = oneTimeValues.get(AWBTYPE_ONETIME);
			stockutilizationstatus = oneTimeValues
					.get(STOCKUTILIZATION_STATUS_ONETIME);
		}
		String startRange = listAwbStockHistoryForm.getRangeFrom();
		String endRange = listAwbStockHistoryForm.getRangeTo();
		String stockHolderCode = listAwbStockHistoryForm.getStockHolderCode();
		String status = listAwbStockHistoryForm.getStockStatus();
		String stockType = listAwbStockHistoryForm.getStockType();
		String accountNumber = listAwbStockHistoryForm.getAccountNumber();
		String docsubtype=listAwbStockHistoryForm.getDocSubType();
		// session.setremoveAllAttributes();

		String companyCode = logonAttributesVO.getCompanyCode();
		StockRangeFilterVO stockRangeFilterVO = new StockRangeFilterVO();
		stockRangeFilterVO.setCompanyCode(companyCode);
		stockRangeFilterVO.setDocumentSubType(docsubtype);
		stockRangeFilterVO.setAirlineIdentifier(getApplicationSession().getLogonVO().getOwnAirlineIdentifier());
	
		stockRangeFilterVO.setStartRange(startRange);
		stockRangeFilterVO.setEndRange(endRange);
		
		
		if(listAwbStockHistoryForm
				.getStartDate()!=null) {
			stockRangeFilterVO.setStartDate(start.setDate(listAwbStockHistoryForm
					.getStartDate()));
		}
		if(listAwbStockHistoryForm
				.getEndDate()!=null) {
			stockRangeFilterVO.setEndDate(end.setDate(listAwbStockHistoryForm
					.getEndDate()));
		}
		stockRangeFilterVO.setFromStockHolderCode(stockHolderCode);
		stockRangeFilterVO.setStatus(status);
		
		
		stockRangeFilterVO.setAccountNumber(accountNumber);
		if (listAwbStockHistoryForm.isHistory()) {
			stockRangeFilterVO.setHistory(true);
		} else {
			stockRangeFilterVO.setHistory(false);
		}
		if("B".equalsIgnoreCase(stockType)){
			stockRangeFilterVO.setRangeType("");
		} else {
			stockRangeFilterVO.setRangeType(stockType);
		}
		
		//icrd-3082 added by a-4562 starts
		if (stockRangeFilterVO.getStatus() == null) {
			stockRangeFilterVO.setStatus("");   
		}
		//Commented by A-2881 for ICR-12268
		/*else {
			for (OneTimeVO stockUtilStauts : stockutilizationstatus) {
				if (stockUtilStauts.getFieldValue().equalsIgnoreCase(
						stockRangeFilterVO.getStatus())) {
					stockRangeFilterVO.setStatus(stockUtilStauts
							.getFieldDescription());
					break;
				}
			}
		}*/
		if (stockRangeFilterVO.getRangeType() == null) {
			stockRangeFilterVO.setRangeType("");
		}else {
			for (OneTimeVO awbtypeVO : awbtype) {
				if (awbtypeVO.getFieldValue().equalsIgnoreCase(
						stockRangeFilterVO.getRangeType())) {
					stockRangeFilterVO.setRangeType(awbtypeVO.getFieldDescription());
					break;
				}
			}
		}
		if(stockRangeFilterVO.getDocumentSubType() == null) {
			stockRangeFilterVO.setDocumentSubType("");
		}
		if(listAwbStockHistoryForm.isHistory()){
			stockRangeFilterVO.setHistory(true); 
					}else{
						stockRangeFilterVO.setHistory(false);
	    }
		//icrd-3082 added by a-4562 ends
		
        //Added by A-2881 for ICRD-12188(ICRD-3082) 
		//To fetch only the status in the screen(To remove transit txns)
		if(session.getOneTimeStockStatus()!=null){
			Collection<String> availableStatus=new ArrayList<String>();
			for(OneTimeVO oneTimeVO:session.getOneTimeStockStatus()){
				availableStatus.add(oneTimeVO.getFieldValue());
			}
			stockRangeFilterVO.setAvailableStatus(availableStatus);
		}
		
		if (listAwbStockHistoryForm.getUserId() != null
				&& listAwbStockHistoryForm.getUserId().trim().length() > 0) {        
			stockRangeFilterVO.setUserId(listAwbStockHistoryForm.getUserId());
		}  
		
		stockRangeFilterVO.setDocumentType(listAwbStockHistoryForm.getDocType());
		log
				.log(
						Log.FINE,
						"before entering stockRangeFilterVO is ----------------->>>>>>",
						stockRangeFilterVO);
		getReportSpec().setReportId(REPORT_ID_VIEWSTOCKRANGEHISTORY);
		getReportSpec().setAction(ACTION_VIEWSTOCKRANGEHISTORY);
		getReportSpec().setProductCode(listAwbStockHistoryForm.getProduct());
		getReportSpec().setSubProductCode(listAwbStockHistoryForm.getSubProduct());
		getReportSpec().setPreview(true);
		getReportSpec().setHttpServerBase(invocationContext.httpServerBase);
		getReportSpec().addFilterValue(stockRangeFilterVO);
		getReportSpec().setResourceBundle(listAwbStockHistoryForm.getBundle());
		generateReport();
		if(getErrors() != null && getErrors().size()>0){
			for(ErrorVO err : getErrors()){
				if("stockcontrol.defaults.norecordsfound".equalsIgnoreCase(err.getErrorCode())){
					ErrorVO error = new ErrorVO("stockcontrol.defaults.norecordsfound");
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					invocationContext.addError(error);
				}else{
					invocationContext.addError(err);
				}
			}
			invocationContext.target=ERROR_ERR;
			return;
		}
		log.log(Log.FINE, "\n\n\n----------AFTER GENERATE REPORT----");
		invocationContext.target = getTargetPage();
	}
 
	/**
	 * Method to populate the collection of
	 * onetime parameters to be obtained
	 * @return parameterTypes
	 */
	private Collection<String> getOneTimeParameterTypes() {
		log.entering("listawbstockhistory's PrintCommand","getOneTimeParameterTypes");
		ArrayList<String> parameterTypes = new ArrayList<String>();
		parameterTypes.add(STOCKSTATUS_ONETIME);
		parameterTypes.add(STOCKUTILIZATION_STATUS_ONETIME);
		parameterTypes.add(AWBTYPE_ONETIME);		
		log.exiting("ScreenLoadCommand","getOneTimeParameterTypes");
		return parameterTypes;
	}
	/**
	 * The method to obtain the onetime values.
	 * The method will call the sharedDefaults delegate
	 * and returns the map of requested onetimes
	 * @return oneTimeValues
	 */
	private Map<String, Collection<OneTimeVO>> getOneTimeValues(){
		log.entering("listawbstockhistory's PrintCommand","getOneTimeValues");
		/*
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		/*
		 * the shared defaults delegate
		 */
		SharedDefaultsDelegate sharedDefaultsDelegate =
			new SharedDefaultsDelegate();
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;

		try {
			log.log(Log.FINE, "****inside try*", getOneTimeParameterTypes());
			oneTimeValues =	sharedDefaultsDelegate.findOneTimeValues(
					logonAttributes.getCompanyCode(),
					getOneTimeParameterTypes());
		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.FINE,"*****in the exception");
			 handleDelegateException(businessDelegateException);
		}
		log.log(Log.INFO, "oneTimeValues ---> ", oneTimeValues);
		log.exiting("listawbstockhistory's PrintCommand","getOneTimeValues");
		return oneTimeValues;
	}
}
