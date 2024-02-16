/*
 * ListMUCTrackingCommand.java Created on Aug 07, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.muctracking;

import static com.ibsplc.xibase.server.framework.vo.ErrorDisplayType.STATUS;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionFilterVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionListVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.MUCTrackingSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.MUCTrackingForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-3045
 *
 */
public class ListMUCTrackingCommand extends BaseCommand{
	
	/**
	 * Logger for Maintain Uld discripency
	 */
	private Log log = LogFactory.getLogger("MUC Tracking");

	/**
	 * The Module Name
	 */
	private static final String MODULE_NAME = "uld.defaults";

	/**
	 * Screen Id of maintain uld screen
	 */
	private static final String SCREEN_ID = "uld.defaults.messaging.muctracking";

	/**
	 * Target if success
	 */
	private static final String LIST_SUCCESS = "list_success";
	private static final String ENABLE_FLAG = "enable";//A-5202
	
	private static final String BLANK = "";
	
	private static final String UPDATED = "U";
	
	private static final String RESENT = "R";
	
	private static final String SENT = "S";
	
	private static final String STRUPDATED = "Updated";
	
	private static final String STRRESENT = "Re-Sent";
	
	private static final String STRSENT = "Sent";
	
	private static final String LISTSTATUS = "noListForm";
	
	private static final String TXNTYPE_ONETIME = "uld.defaults.TxnType";
	//added by a-3045 for bug 18654 starts
	private static final String MUC_RESENT_SUCCESS = "Resent_Success";
	//added by a-3045 for bug 18654 ends
		
	
	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		
		log.entering("ListMUCTrackingCommand", "execute");
		/*
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		MUCTrackingForm mucTrackingForm = (MUCTrackingForm) invocationContext.screenModel;
		MUCTrackingSession mucTrackingSession = getScreenSession(MODULE_NAME,
				SCREEN_ID);
		ULDDefaultsDelegate uldDefaultsDelegate = new ULDDefaultsDelegate();
		TransactionFilterVO transactionFilterVO = null;		
		TransactionListVO transactionListVO = new TransactionListVO();		
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		String companyCode = logonAttributes.getCompanyCode();
		boolean flag = false;
		if(mucTrackingSession.getListStatus()!=null && ("noListForm").equals(mucTrackingSession.getListStatus())){
			log.log(Log.FINE,"mucTrackingSession~~~~~~~~~~~~~~~~~");
			transactionFilterVO =mucTrackingSession.getListFilterVO();
			if(transactionFilterVO != null){
				log.log(Log.FINE, "transactionFilterVO~~~~~~~~~~~~~~~~~",
						transactionFilterVO);
				mucTrackingSession.setListFilterVO(transactionFilterVO);
			}else{
				log.log(Log.FINE, "transactionFilterVO~~", transactionFilterVO);
				flag = true;
			}
		}else{
			transactionFilterVO = new TransactionFilterVO();
			transactionFilterVO.setCompanyCode(companyCode);		
			if (!(BLANK).equalsIgnoreCase(mucTrackingForm.getMucReferenceNumber())
					&& mucTrackingForm.getMucReferenceNumber() != null) {
				transactionFilterVO.setMucReferenceNumber(mucTrackingForm
								.getMucReferenceNumber().toUpperCase());
			}	
			LocalDate mucFilterDate =  new LocalDate(logonAttributes.getAirportCode(),
					Location.ARP, true);
			if (!(BLANK).equalsIgnoreCase(mucTrackingForm.getMucFilterDate())
					&& mucTrackingForm.getMucFilterDate() != null) {	
				 mucFilterDate.setDate(mucTrackingForm.getMucFilterDate());	
					transactionFilterVO.setMucDate(mucFilterDate);		
			}
			log.log(Log.FINE,
					"mucTrackingForm.getIataFilterStatus()++++++++++ ~~~~~~~~~~~~~~~~~");
			if (!(BLANK).equalsIgnoreCase(mucTrackingForm.getIataFilterStatus())
					&& mucTrackingForm.getIataFilterStatus() != null) {
				log
						.log(
								Log.FINE,
								"mucTrackingForm.getIataFilterStatus()++++++++++ ~~~~~~~~~~~~~~~~~",
								mucTrackingForm
								.getIataFilterStatus());
				transactionFilterVO.setMucIataStatus(mucTrackingForm
								.getIataFilterStatus().toUpperCase());
			}
		
		}	
		if(flag){
			mucTrackingSession.setListStatus("");
			invocationContext.target = LIST_SUCCESS;
			return;
		}
		try {
			transactionListVO = uldDefaultsDelegate
					.findULDTransactionDetailsCol(transactionFilterVO);
			log.log(Log.FINE, "TransactionListVO ~~~~~~~~~~~~~~~~~",
					transactionListVO);
			
		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
			errors = handleDelegateException(businessDelegateException);
		}
		Collection<ULDTransactionDetailsVO>	uldTransactionDetailsVO = transactionListVO.getUldTransactionsDetails();
		if((uldTransactionDetailsVO != null && uldTransactionDetailsVO.size() > 0)){
			for(ULDTransactionDetailsVO vo: uldTransactionDetailsVO){
				if(vo.getMucIataStatus().equals(UPDATED)){
					vo.setMucIataStatus(STRUPDATED);
				}else if(vo.getMucIataStatus().equals(RESENT)){
					vo.setMucIataStatus(STRRESENT);
				}else if(vo.getMucIataStatus().equals(SENT)){
					vo.setMucIataStatus(STRSENT);
				}				
				SharedDefaultsDelegate sharedDefaultsDelegate = 
					new SharedDefaultsDelegate();
				Map<String, Collection<OneTimeVO>> oneTimeValues = null;
				Collection<ErrorVO> error = new ArrayList<ErrorVO>();
				Collection<String> oneTimeContentList = new ArrayList<String>();
				oneTimeContentList.add(TXNTYPE_ONETIME);
				try {
					log.log(Log.FINE,
							"****inside try**************************",
							oneTimeContentList);
					oneTimeValues =	sharedDefaultsDelegate.findOneTimeValues(
							companyCode, oneTimeContentList);
				} catch (BusinessDelegateException businessDelegateException) {
					log.log(Log.FINE,"*****in the exception");
					businessDelegateException.getMessage();
					error = handleDelegateException(businessDelegateException);
				}
				if(oneTimeValues!=null){
					log.log(Log.INFO, "oneTimeValues TXNTYPE_ONETIME---> ",
							oneTimeValues);
					Collection<OneTimeVO> txnType = oneTimeValues.get(TXNTYPE_ONETIME);
					for(OneTimeVO oneTimeVO : txnType){
						if(vo.getTransactionType().equals(oneTimeVO.getFieldValue())){
							vo.setTransactionType(oneTimeVO.getFieldDescription());
						}
					}
				}
			}
			//added by a-3045 for bug 19990 starts
			mucTrackingForm.setRecordSize(uldTransactionDetailsVO.size());
			//added by a-3045 for bug 19990 ends
			mucTrackingForm.setEnableFlag(ENABLE_FLAG);//A-5202
		}
		if ((transactionListVO.getUldTransactionsDetails()== null || transactionListVO
				.getUldTransactionsDetails().size() == 0)) {
			//added by a-3045 for bug 19990 starts
			mucTrackingForm.setRecordSize(0);
			//added by a-3045 for bug 19990 ends
			ErrorVO errorVO = new ErrorVO(
					"uld.defaults.transaction.noenquiriesfound");
			errorVO.setErrorDisplayType(STATUS);
			errors.add(errorVO);
			invocationContext.addAllError(errors);
		}
		//added by a-3045 for bug 18654 starts
		if (MUC_RESENT_SUCCESS.equals(mucTrackingForm.getMucResentFlag())) {
			ErrorVO errorVO = new ErrorVO(
					"uld.defaults.muctracking.msg.muc.resendsuccessfully");
			errorVO.setErrorDisplayType(ErrorDisplayType.INFO);
			errors.add(errorVO);
			invocationContext.addAllError(errors);
			mucTrackingForm.setMucResentFlag(BLANK);
		}
		//added by a-3045 for bug 18654 ends
		mucTrackingSession.setListDisplayColl(transactionListVO);
		mucTrackingSession.setListFilterVO(transactionFilterVO);	
		mucTrackingSession.setListStatus("");
		invocationContext.target = LIST_SUCCESS;
		log.exiting("ListMUCTrackingCommand", "execute");			
	}		
}
