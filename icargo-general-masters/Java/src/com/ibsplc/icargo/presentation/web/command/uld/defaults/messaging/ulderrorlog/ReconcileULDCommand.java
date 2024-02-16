/*
 * ReconcileULDCommand.java Created on Jul 20, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.ulderrorlog;

import static com.ibsplc.xibase.server.framework.vo.ErrorDisplayType.WARNING;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDMovementVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionFilterVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionListVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.MaintainULDSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.ULDErrorLogSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.ux.misc.MaintainDamageReportSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.RecordUldMovementSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.ListULDTransactionSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.LoanBorrowULDSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.ULDErrorLogForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is invoked on the start up of the 
 * ReconcileULDCommand screen
 * 
 * @author A-1862
 */

public class ReconcileULDCommand extends BaseCommand {
    
private Log log = LogFactory.getLogger("LIST ULD Error Logs");
	
	private static final String MODULE = "uld.defaults";
	
	private static final String SCREENID = "uld.defaults.ulderrorlog";
	
	private static final String SCREENID_MAINTAINULD = "uld.defaults.maintainuld";
	private static final String SCREEN_ID_LOANBORROWULD= "uld.defaults.loanborrowuld";
	private static final String SCREEN_ID_MAINTAINULD= "uld.defaults.ux.maintaindamagereport";
	private static final String SCREEN_ID_RETURN= "uld.defaults.loanborrowdetailsenquiry";
	private static final String SCREENID_RECORDULD ="uld.defaults.misc.recorduldmovement";
    
	private static final String MAINTAINULD_SUCCESS = "maintainULD_success";    
	private static final String RECONCILE_FAIL = "reconcile_fail";  
	private static final String RECONCILE_SUCCESS = "reconcile_success";  
	private static final String BORROW_SUCCESS = "borrow_success";    
	private static final String DMGREP_SUCCESS = "damagerep_success";    
	private static final String RETURN_SUCCESS = "returntxn_success"; 
	private static final String RECORDULD_SUCCESS = "recorduld_success"; 
 
    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	/**
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String  compCode = logonAttributes.getCompanyCode();		
		ULDErrorLogSession uldErrorLogSession = 
			(ULDErrorLogSession)getScreenSession(MODULE,SCREENID);
		Collection<ErrorVO> error = null;
		ULDErrorLogForm uldErrorLogForm = 
			(ULDErrorLogForm) invocationContext.screenModel;
		log.log(Log.FINE, "ucmErrorLogForm.getRowindex()-------------------->",
				uldErrorLogForm.getUldrowindex());
		ULDFlightMessageReconcileDetailsVO updatedULDFlightMessageReconcileDetailsVO = null;
		
		Page<ULDFlightMessageReconcileDetailsVO> uldFlightMessageReconcileDetailsVOs=uldErrorLogSession.getULDFlightMessageReconcileDetailsVOs();
		ULDFlightMessageReconcileDetailsVO uldFlightMessageReconcileDetailsVO=uldFlightMessageReconcileDetailsVOs.get(Integer.parseInt(uldErrorLogForm.getUldrowindex()));
		log.log(Log.FINE,
				"uldFlightMessageReconcileDetailsVO-------------------->",
				uldFlightMessageReconcileDetailsVO);
		String oldErrorCode=uldFlightMessageReconcileDetailsVO.getErrorCode();
		String newErrorCode="";
		Collection<ErrorVO> errorsdel = new ArrayList<ErrorVO>();
		try {
			log.log(Log.FINE, "\n reconcile  delegate " );
			newErrorCode=new ULDDefaultsDelegate().reconcileUCMULDError(uldFlightMessageReconcileDetailsVO);
		}
		catch(BusinessDelegateException businessDelegateException) {				
			businessDelegateException.getMessage();	
			errorsdel = handleDelegateException(businessDelegateException);
		}
		log.log(Log.FINE, "newErrorCode", newErrorCode);
		log.log(Log.FINE, "oldErrorCode", oldErrorCode);
		if(newErrorCode==null || ("").equals(newErrorCode) || !newErrorCode.equals(oldErrorCode)){
	    	uldErrorLogForm.setErrorCode("");
	    	uldErrorLogSession.setPageURL("fromreconcile");
			uldErrorLogSession.setULDFlightMessageReconcileDetailsVO(null);			
	    	invocationContext.target = RECONCILE_SUCCESS;
			return;	    	
	    }else{
		    Page<ULDFlightMessageReconcileDetailsVO> updatedUldFlightMessageReconcileDetailsVOs = null;
			 error = new ArrayList<ErrorVO>();
			try {
				updatedUldFlightMessageReconcileDetailsVOs= new ULDDefaultsDelegate().
				listUldErrors(uldErrorLogSession.getFlightFilterMessageVOSession());
			}
			catch (BusinessDelegateException businessDelegateException) {
				businessDelegateException.getMessage();
				error = handleDelegateException(businessDelegateException);
			}
			if(updatedUldFlightMessageReconcileDetailsVOs!= null){
				updatedULDFlightMessageReconcileDetailsVO = new ULDFlightMessageReconcileDetailsVO();
				updatedULDFlightMessageReconcileDetailsVO = updatedUldFlightMessageReconcileDetailsVOs.get(Integer.parseInt(uldErrorLogForm.getUldrowindex()));
			}
			
	    }
	    //Added as part of bug 107159 by A-3767 on 24Feb11
	    if(updatedULDFlightMessageReconcileDetailsVO!=null) {
			uldFlightMessageReconcileDetailsVO.setLastUpdateTime(updatedULDFlightMessageReconcileDetailsVO.getLastUpdateTime());
		}
		AreaDelegate areaDelegate =new AreaDelegate();	
		AirportVO airportVO=null;
		Collection<ErrorVO> excep = new ArrayList<ErrorVO>();
		try {
			airportVO =	areaDelegate.findAirportDetails(compCode,logonAttributes.getAirportCode());
		} catch (BusinessDelegateException e) {
		e.getMessage();
		excep = handleDelegateException(e);
		}
		log.log(Log.FINE, "airportVO---------------->", airportVO);
			String isGHA="";
			if(logonAttributes.isAirlineUser()){
				isGHA="N";
			}else
			{
				isGHA="Y";
			}
			
			String isAirlineGHA="";
			if(airportVO!=null)
			{
				if(airportVO.getUsedAirportVO() != null && airportVO.getUsedAirportVO().isUldGHAFlag()){
					isAirlineGHA="Y";
				}else{
					isAirlineGHA="N";
				}
			}			
			log.log(Log.FINE, "isGHA----------------->", isGHA);
			log
					.log(Log.FINE, "isAirlineGHA-------------------->",
							isAirlineGHA);
		if((("N").equals(isGHA) || (("Y").equals(isGHA) && ("Y").equals(isAirlineGHA))) 
				&& ("E3").equals(uldFlightMessageReconcileDetailsVO.getErrorCode()))
		{
		
		String uldnum=uldFlightMessageReconcileDetailsVO.getUldNumber();
		
		Collection<ErrorVO> err = null;
		AirlineDelegate airlineDelegate = new AirlineDelegate();
		AirlineValidationVO airlineValidationVO = null;
		try {
			log
					.log(
							Log.FINE,
							"logonAttributes.getOwnAirlineIdentifier()--------------->",
							logonAttributes.getOwnAirlineIdentifier());
			airlineValidationVO = airlineDelegate.findAirline(compCode,logonAttributes.getOwnAirlineIdentifier());

		} catch (BusinessDelegateException businessDelegateException) {
			err = handleDelegateException(businessDelegateException);
		}
			
		String airlineCode=airlineValidationVO.getAlphaCode();
		log.log(Log.FINE, "airlineCode-------------------->", airlineCode);
		int airlineCodeSize=airlineCode.length();
		log.log(Log.FINE, "airlineCodeSize------------------->",
				airlineCodeSize);
		String uldAirlineCode=uldnum.substring(uldnum.length()-airlineCodeSize,uldnum.length());
		log
				.log(Log.FINE, "uldAirlineCode-------------------->",
						uldAirlineCode);
		log.log(Log.FINE, "airlineCode-------------------->", airlineCode);
		if(uldAirlineCode.equals(airlineCode)){	
			MaintainULDSession maintainULDSession = 
				(MaintainULDSession)getScreenSession(MODULE,SCREENID_MAINTAINULD);	
			
			log.log(Log.FINE, "\n\n\n\n\n\n\n*********RECONCILE TO MAINTAIN ULD***************" );
			
			maintainULDSession.setPageURL("fromulderrorlog");
			maintainULDSession.setULDFlightMessageReconcileDetailsVO(uldFlightMessageReconcileDetailsVO);
			uldErrorLogForm.setUldrowindex("");
			invocationContext.target = MAINTAINULD_SUCCESS;
			return;
		}
		else
		{
			log.log(Log.FINE, "\n\n\n\n\n\n\n*********RECONCILE TO BORROW ULD***************" );
			
			LoanBorrowULDSession loanBorrowULDSession = getScreenSession(MODULE, SCREEN_ID_LOANBORROWULD);
			loanBorrowULDSession.setPageURL("fromulderrorlogforborrow");
			loanBorrowULDSession.setULDFlightMessageReconcileDetailsVO(updatedULDFlightMessageReconcileDetailsVO);
			uldErrorLogForm.setUldrowindex("");
			invocationContext.target = BORROW_SUCCESS;
			return;
		}
		}
		if((("N").equals(isGHA) || ("Y").equals(isAirlineGHA)) && ("E4").equals(uldFlightMessageReconcileDetailsVO.getErrorCode())){
			
			log.log(Log.FINE, "\n\n\n\n\n\n\n*********RECONCILE TO MAINTAIN DAMAGE REPORT***************" );
			
			MaintainDamageReportSession maintainDamageReportSession = 
				(MaintainDamageReportSession)getScreenSession(MODULE,SCREEN_ID_MAINTAINULD);
			maintainDamageReportSession.setPageURL("fromulderrorlog");
			maintainDamageReportSession.setULDFlightMessageReconcileDetailsVO(updatedULDFlightMessageReconcileDetailsVO);
			uldErrorLogForm.setUldrowindex("");
			invocationContext.target = DMGREP_SUCCESS;
			return;
		}
		
		if((("N").equals(isGHA) || ("Y").equals(isAirlineGHA)) && ("E7").equals(uldFlightMessageReconcileDetailsVO.getErrorCode())){
			
			
			String uldnum=uldFlightMessageReconcileDetailsVO.getUldNumber();				
			ULDDefaultsDelegate uldDefaultsDelegate =  new ULDDefaultsDelegate();
			Integer oprAirline=0;
			 error = new ArrayList<ErrorVO>();
			try {
				oprAirline=uldDefaultsDelegate.findOperationalAirlineForULD(compCode,uldnum);
			} catch (BusinessDelegateException businessDelegateException) {
				businessDelegateException.getMessage();
				error = handleDelegateException(businessDelegateException);
			}
			log.log(Log.FINE, "oprAirline-------------------->", oprAirline);
			Collection<ErrorVO> errors = null;
			AirlineDelegate airlineDelegate = new AirlineDelegate();
			AirlineValidationVO airlineValidationVO = null;
			try {
				log
						.log(
								Log.FINE,
								"logonAttributes.getOwnAirlineIdentifier()--------------->",
								logonAttributes.getOwnAirlineIdentifier());
				airlineValidationVO = airlineDelegate.findAirline(compCode,logonAttributes.getOwnAirlineIdentifier());

			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
				
			String airlineCode=airlineValidationVO.getAlphaCode();
			log.log(Log.FINE, "airlineCode-------------------->", airlineCode);
			int airlineCodeSize=airlineCode.length();
			log.log(Log.FINE, "airlineCodeSize------------------->",
					airlineCodeSize);
			String uldAirlineCode=uldnum.substring(uldnum.length()-airlineCodeSize,uldnum.length());
			log.log(Log.FINE, "uldAirlineCode-------------------->",
					uldAirlineCode);
			log.log(Log.FINE, "airlineCode-------------------->", airlineCode);
			airlineValidationVO = null;
			try {
				log
						.log(
								Log.FINE,
								"logonAttributes.getOwnAirlineIdentifier()--------------->",
								logonAttributes.getOwnAirlineIdentifier());
				airlineValidationVO = airlineDelegate.findAirline(compCode,oprAirline);

			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
			String oprAirlineCode=airlineValidationVO.getAlphaCode();
			String carrierCode=uldFlightMessageReconcileDetailsVO.getCarrierCode();
			log.log(Log.FINE, "\n airlineCode-------------------->",
					airlineCode);
			log.log(Log.FINE, "\n uldAirlineCode-------------------->",
					uldAirlineCode);
			log.log(Log.FINE, "\n oprAirlineCode-------------------->",
					oprAirlineCode);
			log.log(Log.FINE, "\n carrierCode-------------------->",
					carrierCode);
			// CASE 1
			if(airlineCode.equals(carrierCode) ){
				log.log(Log.FINE, "\n\n\n\n\n\n\n*********case 1***************" );
				/*if(isGHA.equals("N")){
					
					log.log(Log.FINE, "\n\n\n\n\n\n\n*****CASE 1****RECONCILE TO RETURN TRANSACTION (LOAN)********" );
					
					ListULDTransactionSession listULDTransactionSession = getScreenSession(
																		MODULE, SCREEN_ID_RETURN);
					listULDTransactionSession.setPageURL("fromulderrorlog");
					listULDTransactionSession.setULDFlightMessageReconcileDetailsVO(uldFlightMessageReconcileDetailsVO);
					
					TransactionListVO   transactionListVO = new TransactionListVO();
					TransactionFilterVO   transactionFilterVO = new TransactionFilterVO();
					transactionFilterVO.setCompanyCode(compCode);
					transactionFilterVO.setUldNumber(uldFlightMessageReconcileDetailsVO.getUldNumber());
					transactionFilterVO.setTransactionType("L");
					transactionFilterVO.setTransactionStatus("T");
					try {
						 transactionListVO = uldDefaultsDelegate.listULDTransactionDetails(transactionFilterVO);
						 }catch (BusinessDelegateException businessDelegateException) {		
//printStackTrrace()();
					     }
					//ULDTransactionDetailsVO=transactionListVO.getUldTransactionsDetails()
					transactionListVO.setTransactionType("L");
					log.log(Log.FINE, "\n*********transactionListVO****" +transactionListVO);
					listULDTransactionSession.setReturnTransactionListVO(transactionListVO);
					
					uldErrorLogForm.setUldrowindex("");
					uldErrorLogForm.setReturnTxn("RETURNTXN");
					invocationContext.target = RETURN_SUCCESS;
					return;						
				}*/
				if(("Y").equals(isAirlineGHA)||("N").equals(isGHA)){
					
					ListULDTransactionSession listULDTransactionSession = getScreenSession(
							MODULE, SCREEN_ID_RETURN);
					listULDTransactionSession.setPageURL("fromulderrorlogforflight");
					listULDTransactionSession.setULDFlightMessageReconcileDetailsVO(updatedULDFlightMessageReconcileDetailsVO);
				
					TransactionListVO   transactionListVO = new TransactionListVO();
					TransactionFilterVO   transactionFilterVO = new TransactionFilterVO();
					transactionFilterVO.setCompanyCode(compCode);
					transactionFilterVO.setTransactionType("ALL");
					transactionFilterVO.setUldNumber(updatedULDFlightMessageReconcileDetailsVO.getUldNumber());
					transactionFilterVO.setTransactionStatus("T");
					transactionFilterVO.setPageNumber(1);
					Collection<ErrorVO> exp = new ArrayList<ErrorVO>();
					try {
						transactionListVO = uldDefaultsDelegate.listULDTransactionDetails(transactionFilterVO);
					}catch (BusinessDelegateException businessDelegateException) {		
					businessDelegateException.getMessage();
					exp = handleDelegateException(businessDelegateException);
					}
					ArrayList<ULDTransactionDetailsVO> coll;
					if(transactionListVO.getTransactionDetailsPage()!=null){   //Added by A-7978 for ICRD-248049
						coll = new ArrayList<ULDTransactionDetailsVO>(transactionListVO.getTransactionDetailsPage());
					}
					else{
						coll= new ArrayList<ULDTransactionDetailsVO>();
					}
					transactionListVO.setUldTransactionsDetails(coll);
					log.log(Log.FINE, "\n********coll************", coll);
					
					boolean isLoan=false;
					for(ULDTransactionDetailsVO uldTransactionDetailsVO:coll){
						if(("L").equals(uldTransactionDetailsVO.getTransactionType())){
							isLoan=true;
							break;
						}
					}
					
					if(isLoan)
					{
						transactionListVO.setTransactionType("L");
						log.log(Log.FINE, "\n\n\n\n\n\n\n***CASE 1******RECONCILE TO RETURN loan***************" );
						listULDTransactionSession.setReturnTransactionListVO(transactionListVO);
						
						uldErrorLogForm.setUldrowindex("");
						uldErrorLogForm.setReturnTxn("RETURNTXN");
						invocationContext.target = RETURN_SUCCESS;
						return;				
						
					}else
					{
						log.log(Log.FINE, "\n\n\n\n\n\n\n****CASE 1*****RECONCILE TO LOAN***************" );
						LoanBorrowULDSession loanBorrowULDSession = getScreenSession(MODULE, SCREEN_ID_LOANBORROWULD);
						loanBorrowULDSession.setPageURL("fromulderrorlogforloanforflight");
						loanBorrowULDSession.setULDFlightMessageReconcileDetailsVO(updatedULDFlightMessageReconcileDetailsVO);
						uldErrorLogForm.setUldrowindex("");
						invocationContext.target = BORROW_SUCCESS;
						return;
					}
					
					
				}
				
			}
			
			
//			 CASE 2
			if(airlineCode.equals(oprAirlineCode) ){
				if(uldAirlineCode.equals(carrierCode)){	
					log.log(Log.FINE, "\n\n\n\n\n\n\n*********CASE 2 RECONCILE TO RETURN (BORROW)/loan**********" );
					ListULDTransactionSession listULDTransactionSession = getScreenSession(
							MODULE, SCREEN_ID_RETURN);
					listULDTransactionSession.setPageURL("fromulderrorlogforflight");
					listULDTransactionSession.setULDFlightMessageReconcileDetailsVO(updatedULDFlightMessageReconcileDetailsVO);
					TransactionListVO   transactionListVO = new TransactionListVO();
					TransactionFilterVO   transactionFilterVO = new TransactionFilterVO();
					transactionFilterVO.setCompanyCode(compCode);
					transactionFilterVO.setTransactionType("ALL");
					transactionFilterVO.setUldNumber(updatedULDFlightMessageReconcileDetailsVO.getUldNumber());
					transactionFilterVO.setTransactionStatus("T");
					transactionFilterVO.setPageNumber(1);
					Collection<ErrorVO> exception = new ArrayList<ErrorVO>();
					try {
						transactionListVO = uldDefaultsDelegate.listULDTransactionDetails(transactionFilterVO);
					}catch (BusinessDelegateException businessDelegateException) {		
					businessDelegateException.getMessage();
					exception = handleDelegateException(businessDelegateException);
					}
					ArrayList<ULDTransactionDetailsVO> coll= new ArrayList<ULDTransactionDetailsVO>(transactionListVO.getTransactionDetailsPage());
					transactionListVO.setUldTransactionsDetails(coll);
					log.log(Log.FINE, "\n********coll************", coll);
					transactionListVO.setTransactionType(coll.get(0).getTransactionType());
					log.log(Log.FINE, "\n*********transactionListVO****",
							transactionListVO);
					listULDTransactionSession.setReturnTransactionListVO(transactionListVO);
					
					uldErrorLogForm.setUldrowindex("");
					uldErrorLogForm.setReturnTxn("RETURNTXN");
					invocationContext.target = RETURN_SUCCESS;
					return;
				
				}
				else				
				{
					log.log(Log.FINE, "\n\n\n\n\n\n\n********* CASE 2 RECONCILE TO LOAN***************" );
					LoanBorrowULDSession loanBorrowULDSession = getScreenSession(MODULE, SCREEN_ID_LOANBORROWULD);
					loanBorrowULDSession.setPageURL("fromulderrorlogforloanforflight");
					loanBorrowULDSession.setULDFlightMessageReconcileDetailsVO(updatedULDFlightMessageReconcileDetailsVO);
					uldErrorLogForm.setUldrowindex("");
					invocationContext.target = BORROW_SUCCESS;
					return;
					
				}
				
			}
			
//			 CASE 3
		/*	if(airlineCode.equals(uldAirlineCode) && !airlineCode.equals(oprAirlineCode) 
					&& !airlineCode.equals(carrierCode) ){
				
				log.log(Log.FINE, "\n\n\n\n\n\n\n*********CASE 3 RECONCILE TO RETURN TRANSACTION (LOAN)**********" );
				
				ListULDTransactionSession listULDTransactionSession = getScreenSession(
																	MODULE, SCREEN_ID_RETURN);
				listULDTransactionSession.setPageURL("fromulderrorlog");
				listULDTransactionSession.setULDFlightMessageReconcileDetailsVO(uldFlightMessageReconcileDetailsVO);
				
				TransactionListVO   transactionListVO = new TransactionListVO();
				TransactionFilterVO   transactionFilterVO = new TransactionFilterVO();
				transactionFilterVO.setCompanyCode(compCode);
				transactionFilterVO.setUldNumber(uldFlightMessageReconcileDetailsVO.getUldNumber());
				transactionFilterVO.setTransactionType("L");
				transactionFilterVO.setTransactionStatus("T");
				try {
					 transactionListVO = uldDefaultsDelegate.listULDTransactionDetails(transactionFilterVO);
					 }catch (BusinessDelegateException businessDelegateException) {		
//printStackTrrace()();
				     }
				//ULDTransactionDetailsVO=transactionListVO.getUldTransactionsDetails()
				transactionListVO.setTransactionType("L");
				log.log(Log.FINE, "\n*********transactionListVO****" +transactionListVO);
				listULDTransactionSession.setReturnTransactionListVO(transactionListVO);
				
				uldErrorLogForm.setUldrowindex("");
				uldErrorLogForm.setReturnTxn("RETURNTXN");
				invocationContext.target = RETURN_SUCCESS;
				return;						
			}*/
			
//			 CASE 4
			if(!airlineCode.equals(oprAirlineCode) && !oprAirlineCode.equals(carrierCode) 
					&& !airlineCode.equals(carrierCode) ){
				
				log.log(Log.FINE, "\n\n\n\n\n\n\n*********CASE 4 - 3 PARTY**********" );
				
				if(uldAirlineCode.equals(oprAirlineCode)){
					log.log(Log.FINE, "\n\n\n\n\n\n\n********* CASE 4 RECONCILE TO LOAN***************" );
					LoanBorrowULDSession loanBorrowULDSession = getScreenSession(MODULE, SCREEN_ID_LOANBORROWULD);
					loanBorrowULDSession.setPageURL("fromulderrorlogforloanforflight");
					loanBorrowULDSession.setULDFlightMessageReconcileDetailsVO(updatedULDFlightMessageReconcileDetailsVO);
					uldErrorLogForm.setUldrowindex("");
					invocationContext.target = BORROW_SUCCESS;
					return;
				}
				if(uldAirlineCode.equals(carrierCode) ){
					log.log(Log.FINE, "\n\n\n\n\n\n\n*********CASE 4 RECONCILE TO RETURN (BORROW)/loan**********" );
					ListULDTransactionSession listULDTransactionSession = getScreenSession(
							MODULE, SCREEN_ID_RETURN);
					listULDTransactionSession.setPageURL("fromulderrorlogforflight");
					listULDTransactionSession.setULDFlightMessageReconcileDetailsVO(uldFlightMessageReconcileDetailsVO);
					TransactionListVO   transactionListVO = new TransactionListVO();
					TransactionFilterVO   transactionFilterVO = new TransactionFilterVO();
					transactionFilterVO.setCompanyCode(compCode);
					transactionFilterVO.setTransactionType("ALL");
					transactionFilterVO.setUldNumber(uldFlightMessageReconcileDetailsVO.getUldNumber());
					transactionFilterVO.setTransactionStatus("T");
					transactionFilterVO.setPageNumber(1);
					Collection<ErrorVO> exception = new ArrayList<ErrorVO>();
					try {
						transactionListVO = uldDefaultsDelegate.listULDTransactionDetails(transactionFilterVO);
					}catch (BusinessDelegateException businessDelegateException) {		
					businessDelegateException.getMessage();
					exception = handleDelegateException(businessDelegateException);
					}
					ArrayList<ULDTransactionDetailsVO> coll= new ArrayList<ULDTransactionDetailsVO>(transactionListVO.getTransactionDetailsPage());
					transactionListVO.setUldTransactionsDetails(coll);
					log.log(Log.FINE, "\n********coll************", coll);
					transactionListVO.setTransactionType(coll.get(0).getTransactionType());
					log.log(Log.FINE, "\n*********transactionListVO****",
							transactionListVO);
					listULDTransactionSession.setReturnTransactionListVO(transactionListVO);
					
					uldErrorLogForm.setUldrowindex("");
					uldErrorLogForm.setReturnTxn("RETURNTXN");
					invocationContext.target = RETURN_SUCCESS;
					return;
				}
				if(!uldAirlineCode.equals(oprAirlineCode) && !oprAirlineCode.equals(carrierCode) 
						&& !uldAirlineCode.equals(carrierCode) ){
					ListULDTransactionSession listULDTransactionSession = getScreenSession(
							MODULE, SCREEN_ID_RETURN);
					listULDTransactionSession.setPageURL("fromulderrorlogforflight");
					listULDTransactionSession.setULDFlightMessageReconcileDetailsVO(uldFlightMessageReconcileDetailsVO);
				
					TransactionListVO   transactionListVO = new TransactionListVO();
					TransactionFilterVO   transactionFilterVO = new TransactionFilterVO();
					transactionFilterVO.setCompanyCode(compCode);
					transactionFilterVO.setTransactionType("ALL");
					transactionFilterVO.setUldNumber(uldFlightMessageReconcileDetailsVO.getUldNumber());
					transactionFilterVO.setTransactionStatus("T");
					transactionFilterVO.setPageNumber(1);
					Collection<ErrorVO> exception = new ArrayList<ErrorVO>();
					try {
						transactionListVO = uldDefaultsDelegate.listULDTransactionDetails(transactionFilterVO);
					}catch (BusinessDelegateException businessDelegateException) {		
					businessDelegateException.getMessage();
					exception = handleDelegateException(businessDelegateException);
					}
					ArrayList<ULDTransactionDetailsVO> coll= new ArrayList<ULDTransactionDetailsVO>(transactionListVO.getTransactionDetailsPage());
					transactionListVO.setUldTransactionsDetails(coll);
					log.log(Log.FINE, "\n********coll************", coll);
					boolean isLoan=false;
					for(ULDTransactionDetailsVO uldTransactionDetailsVO:coll){
						if(("L").equals(uldTransactionDetailsVO.getTransactionType()) &&
						   (carrierCode).equals(uldTransactionDetailsVO.getFromPartyCode()) &&
						   (oprAirlineCode).equals(uldTransactionDetailsVO.getToPartyCode())){
							isLoan=true;
							break;
						}
					}
					if(isLoan)
					{
						transactionListVO.setTransactionType("L");
						log.log(Log.FINE, "\n\n\n\n\n\n\n***CASE 4******RECONCILE TO RETURN loan***************" );
						listULDTransactionSession.setReturnTransactionListVO(transactionListVO);
						
						uldErrorLogForm.setUldrowindex("");
						uldErrorLogForm.setReturnTxn("RETURNTXN");
						invocationContext.target = RETURN_SUCCESS;
						return;				
						
					}else
					{
						log.log(Log.FINE, "\n\n\n\n\n\n\n****CASE 4*****RECONCILE TO LOAN***************" );
						LoanBorrowULDSession loanBorrowULDSession = getScreenSession(MODULE, SCREEN_ID_LOANBORROWULD);
						loanBorrowULDSession.setPageURL("fromulderrorlogforloanforflight");
						loanBorrowULDSession.setULDFlightMessageReconcileDetailsVO(updatedULDFlightMessageReconcileDetailsVO);
						uldErrorLogForm.setUldrowindex("");
						invocationContext.target = BORROW_SUCCESS;
						return;
					}
				}
									
			}
			}
		if((("N").equals(isGHA) || ("Y").equals(isAirlineGHA)) && 
				("E6").equals(uldFlightMessageReconcileDetailsVO.getErrorCode())){
			
			log.log(Log.FINE, "\n\n\n\n\n\n\n*********RECONCILE TO RECORD ULD***************" );
			
			RecordUldMovementSession recordUldMovementSession = getScreenSession(MODULE, SCREENID_RECORDULD);
			ULDDefaultsDelegate uldDefaultsDelegate = 
				   new ULDDefaultsDelegate();
			String pol="";
			Collection<ErrorVO> exception = new ArrayList<ErrorVO>();
			try {
				pol=uldDefaultsDelegate.findULDCurrentStation(compCode,uldFlightMessageReconcileDetailsVO.getUldNumber());
			} catch (BusinessDelegateException businessDelegateException) {
				businessDelegateException.getMessage();
				exception = handleDelegateException(businessDelegateException);
			}
			ULDMovementVO uldMovementVo = new ULDMovementVO();
			Collection<ULDMovementVO> uldMovementVos = new ArrayList<ULDMovementVO>();
			/*uldMovementVo.setCarrierCode(uldFlightMessageReconcileDetailsVO.getCarrierCode());
			uldMovementVo.setFlightNumber(uldFlightMessageReconcileDetailsVO.getFlightNumber());
			uldMovementVo.setFlightDate(uldFlightMessageReconcileDetailsVO.getFlightDate());*/
			uldMovementVo.setPointOfLading(pol);
			uldMovementVo.setDummyMovement(true);
			/*int index = uldFlightMessageReconcileDetailsVO.getFlightDate().toDefaultStringFormat().indexOf(",");
			String fltDate = uldFlightMessageReconcileDetailsVO.getFlightDate().toDefaultStringFormat().substring(0 ,index);
			uldMovementVo.setFlightDateString(fltDate);*/
			uldMovementVo.setContent(uldFlightMessageReconcileDetailsVO.getContent());
			uldMovementVo.setPointOfUnLading(uldFlightMessageReconcileDetailsVO.getAirportCode());
			uldMovementVos.add(uldMovementVo);
			recordUldMovementSession.setULDMovementVOs(uldMovementVos);
			Collection<String> coll= new ArrayList<String>();
			coll.add(uldFlightMessageReconcileDetailsVO.getUldNumber());
			recordUldMovementSession.setULDNumbers(coll);
			
			recordUldMovementSession.setPageURL("fromulderrorlog");
			recordUldMovementSession.setULDFlightMessageReconcileDetailsVO(uldFlightMessageReconcileDetailsVO);
			uldErrorLogForm.setUldrowindex("");
			uldErrorLogForm.setReturnTxn("RECORDULDMOVT");
			invocationContext.target = RECORDULD_SUCCESS;
			return;
		}
		if((("N").equals(isGHA) || ("Y").equals(isAirlineGHA)) && 
				("E5").equals(uldFlightMessageReconcileDetailsVO.getErrorCode())){
			
			log.log(Log.FINE, "\n\n\n\n\n\n\n*********RECONCILE TO RECORD ULD WITH MESSAGE***************" );
			
			RecordUldMovementSession recordUldMovementSession = getScreenSession(MODULE, SCREENID_RECORDULD);
			ULDDefaultsDelegate uldDefaultsDelegate =  new ULDDefaultsDelegate();
			String pol="";
			Collection<ErrorVO> exception = new ArrayList<ErrorVO>();
			try {
				pol=uldDefaultsDelegate.findULDCurrentStation(compCode,uldFlightMessageReconcileDetailsVO.getUldNumber());
			} catch (BusinessDelegateException businessDelegateException) {
				businessDelegateException.getMessage();
				exception = handleDelegateException(businessDelegateException);
			}
			ULDMovementVO uldMovementVo = new ULDMovementVO();
			Collection<ULDMovementVO> uldMovementVos = new ArrayList<ULDMovementVO>();
			/*uldMovementVo.setCarrierCode(uldFlightMessageReconcileDetailsVO.getCarrierCode());
			uldMovementVo.setFlightNumber(uldFlightMessageReconcileDetailsVO.getFlightNumber());
			uldMovementVo.setFlightDate(uldFlightMessageReconcileDetailsVO.getFlightDate());
			int index = uldFlightMessageReconcileDetailsVO.getFlightDate().toDefaultStringFormat().indexOf(",");
			String fltDate = uldFlightMessageReconcileDetailsVO.getFlightDate().toDefaultStringFormat().substring(0 ,index);
			uldMovementVo.setFlightDateString(fltDate);*/
			uldMovementVo.setDummyMovement(true);
			uldMovementVo.setContent(uldFlightMessageReconcileDetailsVO.getContent());
			uldMovementVo.setPointOfLading(pol);
			uldMovementVo.setPointOfUnLading(uldFlightMessageReconcileDetailsVO.getAirportCode());
			uldMovementVos.add(uldMovementVo);
			recordUldMovementSession.setULDMovementVOs(uldMovementVos);
			Collection<String> coll= new ArrayList<String>();
			coll.add(uldFlightMessageReconcileDetailsVO.getUldNumber());
			recordUldMovementSession.setULDNumbers(coll);
			
			recordUldMovementSession.setPageURL("fromulderrorlogMessage");
			recordUldMovementSession.setULDFlightMessageReconcileDetailsVO(uldFlightMessageReconcileDetailsVO);
			uldErrorLogForm.setUldrowindex("");
			uldErrorLogForm.setReturnTxn("RECORDULDMOVT");
			
			invocationContext.target = RECORDULD_SUCCESS;
			return;
		}
		if(("E15").equals(uldFlightMessageReconcileDetailsVO.getErrorCode())){
			
			log.log(Log.FINE, "\n\n\n\n\n\n\n*********RECONCILE TO Warning MESSAGE***************" );			
			
			uldErrorLogSession.setULDFlightMessageReconcileDetailsVO(uldFlightMessageReconcileDetailsVO);
			ErrorVO errorVO = new ErrorVO("uld.defaults.ulderrorlog.intransit");
		    errorVO.setErrorDisplayType(WARNING);	   	     
		    invocationContext.addError(errorVO);
			invocationContext.target = RECONCILE_FAIL;
			return;
		}		
		invocationContext.target = RECONCILE_FAIL;
        
    }

}
