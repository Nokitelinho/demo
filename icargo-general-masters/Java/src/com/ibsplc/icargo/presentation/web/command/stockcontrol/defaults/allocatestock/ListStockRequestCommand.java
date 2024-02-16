/*
 * ListStockRequestCommand.java Created on Sep 5, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.allocatestock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestVO;
import com.ibsplc.icargo.framework.security.privilege.TransactionPrivilegeHelper;
import com.ibsplc.icargo.framework.security.privilege.vo.TransactionPrivilegeNewVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.stockcontrol.defaults.StockControlDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.AllocateStockSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.workflow.defaults.MessageInboxSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.AllocateStockForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;
/**
 * @author A-1870
 *
 */
public class ListStockRequestCommand extends BaseCommand {
	/**
	 * log
	 */
	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	
	private static final String TXN_COD_ALLOCATE_STOCK = "ALLOCATE_STOCK"; 
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @return
	 */

    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	log.log(Log.FINE,"********************** **********************************");
    		AllocateStockSession session= getScreenSession( "stockcontrol.defaults","stockcontrol.defaults.allocatestock");
			AllocateStockForm allocateStockForm=(AllocateStockForm)invocationContext.screenModel;
			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
			//Added by A-7364 as part of ICRD-227512 starts
			MessageInboxSession messageInboxSession = 
					(MessageInboxSession)getScreenSession("workflow.defaults", "workflow.defaults.messageinbox");
			if(messageInboxSession.getMessageDetails() != null){
				populateMessageInboxSessionDetails(messageInboxSession, allocateStockForm);
			}
			//Added by A-7364 as part of ICRD-227512 ends
			log.log(Log.FINE,"**********************before *****************************************");
			errors = validateForm(allocateStockForm,session);
			session.setData(allocateStockForm.getStockControlFor());
			if (errors != null && errors.size() > 0) {
				invocationContext.addAllError(errors);
				invocationContext.target = "list_failure";
				return;
			}
			log.log(Log.FINE,"**********************after*****************************************");
			StockRequestFilterVO stockRequestFilterVO=getSearchDetails(allocateStockForm);
			session.setRangeVO(null);
			session.setCheck(null);
			//Privilege check done for ICRD-105821
			//CRQ_ ICRD-105821_Bhaskar_17Apr2015
			TransactionPrivilegeNewVO privilegeNewVO = getPrivilegeVO(TXN_COD_ALLOCATE_STOCK);
			if(privilegeNewVO!=null){
				stockRequestFilterVO.setPrivilegeLevelType(privilegeNewVO.getLevelType());
				stockRequestFilterVO.setPrivilegeLevelValue(privilegeNewVO.getTypeValue());
				stockRequestFilterVO.setPrivilegeRule(privilegeNewVO.getPrivilegeCode());
			}
			stockRequestFilterVO.setAllocateCall(true);
			int displayPage=Integer.parseInt(allocateStockForm.getDisplayPage());

			try{
				StockControlDefaultsDelegate stockControlDefaultsDelegate=new StockControlDefaultsDelegate();
			     Page<StockRequestVO> pageStock=stockControlDefaultsDelegate.findStockRequests(stockRequestFilterVO,displayPage);
				/*Page<StockRequestVO> pageStock=hardcode();*/
			     if(pageStock==null || pageStock.size()==0){
						ErrorVO error = null;
						error = new ErrorVO("stockcontrol.defaults.norecordsallocationnotauthorized");
						 error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
						if(errors!=null && errors.size()>0){
							session.setPageStockRequestVO(null);
							invocationContext.addAllError(errors);
							invocationContext.target = "list_failure";
							return;
						}
				  }
			     Collection<OneTimeVO> oneTimeVOs =session.getStatus();
			     if(pageStock!=null){
						for(StockRequestVO stockRequestVo : pageStock){
							if(oneTimeVOs!=null){
								stockRequestVo.setStatus(findOneTimeDescription(oneTimeVOs,stockRequestVo.getStatus()));

							}

						}
					}
			     session.setPageStockRequestVO(pageStock);
			}catch(BusinessDelegateException businessDelegateException){
//printStackTrrace()();
			}
			session.setFilterDetails(stockRequestFilterVO);  
			invocationContext.target ="list_success";
		}



	/**
	 * creating the searching vo
	 * @param allocateStockForm
	 * @return StockRequestFilterVO
	 */
	private StockRequestFilterVO getSearchDetails(AllocateStockForm allocateStockForm)
	{
		StockRequestFilterVO stockRequestFilterVO=new StockRequestFilterVO();


			stockRequestFilterVO.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
			stockRequestFilterVO.setDocumentType(allocateStockForm.getDocType());
			stockRequestFilterVO.setDocumentSubType(allocateStockForm.getDocSubType());
			stockRequestFilterVO.setManual(allocateStockForm.isManual());
			stockRequestFilterVO.setStatus(allocateStockForm.getStatus());
			stockRequestFilterVO.setStockHolderType(allocateStockForm.getStockHolderType());
			stockRequestFilterVO.setStockHolderCode(upper(allocateStockForm.getStockHolderCode()));
			stockRequestFilterVO.setApprover(allocateStockForm.getStockControlFor());
			stockRequestFilterVO.setAirlineIdentifier(""+getAirlineIdentifier(allocateStockForm.getAwbPrefix()));

			LocalDate from = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);

			if(allocateStockForm.getFromDate()!= null &&
					allocateStockForm.getFromDate().trim().length()!=0){
				stockRequestFilterVO.setFromDate(from.setDate(allocateStockForm.getFromDate()));
			}

			if(allocateStockForm.getToDate()!= null &&
					allocateStockForm.getToDate().trim().length()!=0 ){
				LocalDate to = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
				stockRequestFilterVO.setToDate(to.setDate( allocateStockForm.getToDate()));
			}



		return stockRequestFilterVO;
	}
	 private int getAirlineIdentifier(String awbPrefix) {
	    	int ownAirlineIdentifier=getApplicationSession().getLogonVO().getOwnAirlineIdentifier();
			if(awbPrefix!=null && awbPrefix.trim().length()>0){
				String[] tokens=awbPrefix.split("-");
				return (tokens!=null && tokens.length>2)?Integer.parseInt(tokens[2]):ownAirlineIdentifier;
			} 
			return ownAirlineIdentifier;
		}



	/**
	 * This method will the dstatus escription
	 * corresponding to the value from onetime
	 * @param oneTimeVOs
	 * @param status
	 * @return String
	 */
	private String findOneTimeDescription(Collection<OneTimeVO> oneTimeVOs, String status){
		for (OneTimeVO oneTimeVO:oneTimeVOs){
			if(status.equals(oneTimeVO.getFieldValue())){
				return oneTimeVO.getFieldDescription();
			}
		}
		return null;
	}
	private Collection<ErrorVO> validateForm(AllocateStockForm form,
			AllocateStockSession session) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		boolean isValid = true;
		if ("".equals(form.getDocType())) {
			isValid = false;
			Object[] obj = { "DocType" };
			error = new ErrorVO("stockcontrol.defaults.doctypemandatory", obj);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		if ("".equals(form.getDocSubType())) {
			isValid = false;
			Object[] obj = { "DocSubType" };
			error = new ErrorVO("stockcontrol.defaults.docsubtypemandatory", obj);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		if ("".equals(form.getFromDate() )) {
			isValid = false;
			Object[] obj = { "FromDate" };
			error = new ErrorVO("stockcontrol.defaults.frmdatemandatory", obj);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}else{
			if (!DateUtilities.isValidDate(form.getFromDate(),
			"dd-MMM-yyyy")) {
			log.log(Log.FINE,"inside isValidDate from*****************************************");
			Object[] obj = { "ToDate" };
			error = new ErrorVO("stockcontrol.defaults.fromdatenotvalid", obj);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		}
		if ("".equals(form.getToDate())) {
			isValid = false;
			Object[] obj = { "ToDate" };
			error = new ErrorVO("stockcontrol.defaults.todatemandatory", obj);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}else{
			if (!DateUtilities.isValidDate(form.getToDate(),
			"dd-MMM-yyyy")) {
				log.log(Log.FINE,"inside isValidDate to************************************");
				Object[] obj = { "ToDate" };
				error = new ErrorVO("stockcontrol.defaults.todatenotvalid", obj);
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}
		}
		if ("".equals(form.getStockControlFor())) {
			isValid = false;
			Object[] obj = { "StockControlFor" };
			error = new ErrorVO("stockcontrol.defaults.stkcontrolformandatory", obj);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		if ("".equals(form.getStatus())) {
			isValid = false;
			Object[] obj = { "Status" };
			error = new ErrorVO("stockcontrol.defaults.statusmandatory", obj);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		if(form.isPartnerAirline()&& (form.getAwbPrefix()==null || form.getAwbPrefix().trim().length()==0)){
			isValid=false;
			error=new ErrorVO("stockcontrol.defaults.allocatestock.partnerairline.ismandatory");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		return errors;
	}
	private String upper(String input){

		if(input!=null){
			return input.trim().toUpperCase();
		}else{
			return "";
		}
	}
	
	/**
	 * getPrivilegeVO()
	 * @param transactionCode
	 * @return TransactionPrivilegeNewVO
	 */
	private TransactionPrivilegeNewVO getPrivilegeVO(
			String transactionCode) {
		log.entering("ListMonitorStockCommand", "getPrivilegeVO");
		List<TransactionPrivilegeNewVO> privilegeList=null;
		try {
			privilegeList = (ArrayList<TransactionPrivilegeNewVO>) 
			TransactionPrivilegeHelper.getAllowededPrivilegesForTransaction(transactionCode);
		} catch (SystemException e) {			
			log.log(Log.SEVERE,e.getMessage());
		}
		log.exiting("ListMonitorStockCommand", "getPrivilegeVO");
		if(privilegeList!=null && !privilegeList.isEmpty()){
			return privilegeList.get(0);
		}
		return null;
	}
	/**
	 * 
	 * 	Method		:	ListStockRequestCommand.populateMessageInboxSessionDetails
	 *	Added by 	:	A-7364 on 08-Dec-2017 as part of ICRD-227512
	 * 	Used for 	:
	 *	Parameters	:	@param messageInboxSession
	 *	Parameters	:	@param allocateStockForm 
	 *	Return type	: 	void
	 */
	private void populateMessageInboxSessionDetails(MessageInboxSession messageInboxSession,
			AllocateStockForm allocateStockForm){
		if(messageInboxSession.getParameterMap().get("STKHLDTYP")!=null){
			allocateStockForm.setStockHolderType(messageInboxSession.getParameterMap().get("STKHLDTYP"));
		}
		if(messageInboxSession.getParameterMap().get("STKHLD")!=null){
			allocateStockForm.setStockHolderCode(messageInboxSession.getParameterMap().get("STKHLD"));
		}
		if(messageInboxSession.getParameterMap().get("DOCTYP")!=null){
			allocateStockForm.setDocType(messageInboxSession.getParameterMap().get("DOCTYP"));
		}
		if(messageInboxSession.getParameterMap().get("DOCSUBTYP")!=null){
			allocateStockForm.setDocSubType(messageInboxSession.getParameterMap().get("DOCSUBTYP"));
		}
		if(messageInboxSession.getParameterMap().get("REQDAT")!=null){
			allocateStockForm.setFromDate(messageInboxSession.getParameterMap().get("REQDAT"));
			allocateStockForm.setToDate(messageInboxSession.getParameterMap().get("REQDAT"));
		}
		if(messageInboxSession.getParameterMap().get("STKREQSTA")!=null){
			allocateStockForm.setStatus(messageInboxSession.getParameterMap().get("STKREQSTA"));
		}
		String approver=null;
		try{
			approver = new StockControlDefaultsDelegate().findApproverCode(messageInboxSession.getParameterMap().get("CMPCOD"), messageInboxSession.getParameterMap().get("STKHLD"),
					messageInboxSession.getParameterMap().get("DOCTYP"), messageInboxSession.getParameterMap().get("DOCSUBTYP"));
		}catch(BusinessDelegateException e){
			log.log(Log.SEVERE,"no approver found..");
		}
		if(approver!=null){
			allocateStockForm.setStockControlFor(approver);
		}
	}
}
