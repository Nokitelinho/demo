/*
 * SaveStockRequestCommand.java Created on Aug 26, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.maintainstockrequest;

import static com.ibsplc.icargo.framework.util.time.LocalDate.CALENDAR_DATE_FORMAT;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_UPDATE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderPriorityVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockVO;
import com.ibsplc.icargo.framework.security.privilege.TransactionPrivilegeHelper;
import com.ibsplc.icargo.framework.security.privilege.vo.TransactionPrivilegeNewVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.stockcontrol.defaults.StockControlDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.MaintainStockRequestSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.MaintainStockRequestForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

/**
 * @author A-1927
 *
 */
public class SaveStockRequestCommand extends BaseCommand {

	//Added by A-1927 @ NCA on 02-Aug-2007 for NCA Bug Fix starts

	private static final String AWB = "AWB";

	private static final String S = "S";
	private static final String UNABLE_TO_UPDATE = "stockcontrol.defaults.maintainstockrequest.unabletoupdaterequest";
	//Added by A-7373 for the date request issue ICRD-244808
	private static final String UNABLE_TO_UPDATE_DATE = "stockcontrol.defaults.maintainstockrequest.unabletoupdaterequestdate";
	//Added by A-1927 @ NCA on 02-Aug-2007 for NCA Bug Fix ends
	
	private static final String TX_COD_STOCK_REQUEST = "STOCK_REQUEST";
	
	private static final String PRVLG_RUL_STK_HLDR = "STK_HLDR_CODE";

	private static final String PRVLG_LVL_STKHLD = "STKHLD";

	private static final String AWB_PREFIX_NOT_MAPPED = "stockcontrol.defaults.maintainstockrequest.awbprefixnotmapped";
	/**
	 * Status New is set in the variable NEW
	 */
	public static final String NEW = "New";
	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	/**
	 * The execute method in BaseCommand
	 * @author A-1927
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */


	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering("SaveStockRequestCommand","execute");
		MaintainStockRequestForm maintainStockRequestForm = (MaintainStockRequestForm) invocationContext.screenModel;
		/**
		 * Added by A-4772 for ICRD-9882.Changed the 
		 * Screen id value as per standard for UISKC002
		 */
		MaintainStockRequestSession session = (MaintainStockRequestSession)getScreenSession("stockcontrol.defaults","stockcontrol.defaults.maintainstockrequest");
		ApplicationSessionImpl applicationSession = getApplicationSession();
	    LogonAttributes logonAttributes  =  applicationSession.getLogonVO();
		Map<String, Collection<OneTimeVO>> screenLoad = handleScreenLoadDetails(logonAttributes.getCompanyCode());
		Integer requestedStock = 0;
		long reqStock = 0;
		long allocatedStock=0;
		Integer allocStock = 0;
		StockRequestVO stockRequestVO = session.getStockRequestVO();
		//System.out.println("------------------"+stockRequestVO.getLastUpdateDate());
		 /*String[] stkhldvo = maintainStockRequestForm.getStockHolder();
		 Collection<StockHolderPriorityVO> stockHolderPriorityVOs = session.getPrioritizedStockHolders();
		 int count = 0;

		 for(StockHolderPriorityVO stockHolderPriority:stockHolderPriorityVOs){

			 stockHolderPriority.setStockHolderCode(stkhldvo[count]);
			 count++;

		 }*/

		 //session.setPrioritizedStockHolders(stockHolderPriorityVOs);


		if(screenLoad != null){
			 session.setOneTimeStatus(screenLoad.get("stockcontrol.defaults.status"));
		}

		Collection<ErrorVO> errors = null;
		errors = validateForm(maintainStockRequestForm,session);

		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = "screenload_failure";
			return;
		}


		if(maintainStockRequestForm.getReqStock()!=null &&
			maintainStockRequestForm.getReqStock().trim().length()!=0){
			requestedStock=new Integer(maintainStockRequestForm.getReqStock());
			reqStock=requestedStock.longValue();
		}
		if(maintainStockRequestForm.getAllocatedStock()!=null &&
			maintainStockRequestForm.getAllocatedStock().trim().length()!=0){
			allocStock=new Integer(maintainStockRequestForm.getAllocatedStock());
			allocatedStock=allocStock.longValue();
		}

		LocalDate reqDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);

		String reqRefNo=maintainStockRequestForm.getReqRefNo().toUpperCase();
		String date=maintainStockRequestForm.getDateOfReq();
		String docType=maintainStockRequestForm.getDocType();
		String subType=maintainStockRequestForm.getSubType();
		String stockHolderCode=upper(maintainStockRequestForm.getCode());
		String stockHolderType=maintainStockRequestForm.getStockHolderType();
		boolean isManual=maintainStockRequestForm.isManual();
		boolean isPartnerAirline=maintainStockRequestForm.isPartnerAirline();
		

		String remarks=maintainStockRequestForm.getRemarks();
		String appRejRemarks=maintainStockRequestForm.getAppRejRemarks();

		/*for(String str:stockHolder){
			if("".equals(str)||str.length()==0){
				break;
			}
			else
			{
				stockHolderCode=str;
				System.out.println("String length is"+str.length());
			}
		}

		ArrayList<StockHolderPriorityVO> stockHolderPriorityVO =
			(ArrayList<StockHolderPriorityVO>)session.getPrioritizedStockHolders();

		System.out.println("-------------------Size-----------" + stockHolderPriorityVO.size());

		for(int i=((stockHolderPriorityVO.size())-1);i>=0;i--){
		StockHolderPriorityVO stkhlrVO = (StockHolderPriorityVO)stockHolderPriorityVO.get(i);
		if(stkhlrVO!=null){
			System.out.println("----------------------------" +stkhlrVO.getStockHolderCode());
			if(stkhlrVO.getStockHolderCode()!=null && !"".equals(stkhlrVO.getStockHolderCode())){
				System.out.println("----------Assigned------------------" +stkhlrVO.getStockHolderCode());
				stockHolderCode = stkhlrVO.getStockHolderCode();
					break;
			}

			}

		}*/
		
		StockRequestVO stock =new StockRequestVO();


		Collection<OneTimeVO> statusColl=session.getOneTimeStatus();
		for(OneTimeVO stat:statusColl){
			if(stat.getFieldDescription().equals(maintainStockRequestForm.getStatus())) {
				stock.setStatus(stat.getFieldValue());
			}
		}

		stock.setCompanyCode(logonAttributes.getCompanyCode());
		stock.setRequestRefNumber(reqRefNo);
		stock.setStockHolderCode(stockHolderCode);
		stock.setStockHolderType(stockHolderType);
		stock.setRequestDate(reqDate.setDate(date));
		stock.setDocumentType(docType);
		stock.setDocumentSubType(subType);
		stock.setManual(isManual);
		stock.setRequestedStock(reqStock);
		stock.setAllocatedStock(allocatedStock);
		stock.setRemarks(remarks);
		stock.setApprovalRemarks(appRejRemarks);				
		
		stock.setAirlineIdentifier(getAirlineIdentifier(maintainStockRequestForm.getAwbPrefix()));	
		if(maintainStockRequestForm.isPartnerAirline()){
			stock.setAwbPrefix(maintainStockRequestForm.getAwbPrefix().substring(0,3));
		}
		else {
		stock.setAwbPrefix(maintainStockRequestForm.getPartnerPrefix().substring(0,3));
		}

		StockHolderVO stockHolderVO = null;
		boolean isequal = false;
		try{
			stockHolderVO =  new StockControlDefaultsDelegate().findStockHolderDetails(
					getApplicationSession().getLogonVO().getCompanyCode()
					,upper(maintainStockRequestForm.getCode()));
		}catch(BusinessDelegateException businessDelegateException){
			log.log(Log.SEVERE,"BusinessDelegateException caught..........");
		}
		for(StockVO stockHold : stockHolderVO.getStock()){
			if(stockHold.getAirlineIdentifier() == (Integer.parseInt(stock.getAirlineIdentifier()))){
			isequal = true;
			break;			
			}
		}
		if(!isequal){
			ErrorVO error=new ErrorVO(AWB_PREFIX_NOT_MAPPED);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			invocationContext.addError(error);
			invocationContext.target = "screenload_failure";
			return;
		}
		
		
		if(OPERATION_FLAG_INSERT.equals(maintainStockRequestForm.getMode())){
			stock.setOperationFlag(OPERATION_FLAG_INSERT);
			stock.setLastUpdateDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
			stock.setLastUpdateUser(logonAttributes.getUserId());
		}else{
			stock.setOperationFlag(OPERATION_FLAG_UPDATE);
			if(stockRequestVO != null){
			//Added By A-7373 for the BUG ID: ICRD-244808	
			String requestDateCheck = stockRequestVO.getRequestDate().toDisplayFormat(CALENDAR_DATE_FORMAT).toString();
			log.log(Log.FINE,"...........Update Flag getRequestDate.......",requestDateCheck);
				
			// modified by a-5111 for bug id:14967 starts
				if(stockRequestVO.getStatus()!=null&&stockRequestVO.getStatus().equals(stockRequestVO.STATUS_CANCEL))
				{
					ErrorVO error=new ErrorVO(UNABLE_TO_UPDATE);
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					invocationContext.addError(error);
					invocationContext.target = "screenload_failure";
					return;
				}
			   //Added By A-7373 for the BUG ID: ICRD-244808
				else if(!requestDateCheck.equals(maintainStockRequestForm.getDateOfReq()))
				{
					ErrorVO error=new ErrorVO(UNABLE_TO_UPDATE_DATE);
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					invocationContext.addError(error);
					invocationContext.target = "screenload_failure";
					return;
				}
				else{
			//modified by a-5111 ends		
				stock.setLastUpdateDate(stockRequestVO.getLastUpdateDate());
				stock.setLastUpdateUser(logonAttributes.getUserId());
			}
			}
		}
		//Calendar lastUpdateDate =Calendar.getInstance(logonAttributes.getTimeZone());
		stock.setRequestCreatedBy(logonAttributes.getUserId());

		Collection<ErrorVO> errorVos= null;
		String requestRefNo = null;
		Collection<String> stockHolderCodes=new ArrayList<String>();
		stockHolderCodes.add(stockHolderCode);
		try{
			StockControlDefaultsDelegate stockControlDefaultsDelegate=new StockControlDefaultsDelegate();
			log.log(Log.FINE,"...........Inside Controller.......");
			if (stockHolderType!=null && stockHolderType.trim().length()!=0){
				stockControlDefaultsDelegate.validateStockHolderTypeCode(stock);
			}else{
				stockControlDefaultsDelegate.validateStockHolders(logonAttributes.getCompanyCode(),stockHolderCodes);
			}

		}
		catch(BusinessDelegateException businessDelegateException){
			log.log(Log.FINE,"...........Inside catch of validateStockHolderTypeCode.......");
//printStackTrrace()();
			errorVos = handleDelegateException(businessDelegateException);
		}
		if (errorVos != null && errorVos.size() > 0) {
			log.log(Log.FINE,"...........Going to failure screen.......");
			invocationContext.addAllError(errorVos);
			invocationContext.target = "screenload_failure";
			return;
		}
		try{
			StockControlDefaultsDelegate stockControlDefaultsDelegate=new StockControlDefaultsDelegate();
			log.log(Log.FINE,"...........Going to call checkStock.......");
			stockControlDefaultsDelegate.checkStock(logonAttributes.getCompanyCode(),stockHolderCode,docType,subType);
		}
		catch(BusinessDelegateException businessDelegateException){
			log.log(Log.FINE,"...........catch of to  checkStock exception.......");
//printStackTrrace()();
			errorVos = handleDelegateException(businessDelegateException);
		}
		if (errorVos != null && errorVos.size() > 0) {
			invocationContext.addAllError(errorVos);
			invocationContext.target = "screenload_failure";
			return;
		}
		
		// Added by A-5153 for CRQ_ICRD-107872
		TransactionPrivilegeNewVO privilegeNewVO = getPrivilegeVO(TX_COD_STOCK_REQUEST);
		if(privilegeNewVO != null && PRVLG_RUL_STK_HLDR.equals(privilegeNewVO.getPrivilegeCode())&&
				PRVLG_LVL_STKHLD.equals(privilegeNewVO.getLevelType())&&
				privilegeNewVO.getTypeValue()!=null && 
				privilegeNewVO.getTypeValue().trim().length()>0){
			String[] typeValues = privilegeNewVO.getTypeValue().split(",");
			List<String> privilegedStkHldrList = Arrays.asList(typeValues);
			if(!privilegedStkHldrList.contains(stockHolderCode)){
				ErrorVO error =  new ErrorVO("stockcontrol.defaults.stockrequestnotauthorized");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
				invocationContext.addAllError(errors);
				invocationContext.target = "screenload_failure";
				return;
			}
		}
		

		try{
			StockControlDefaultsDelegate stockControlDefaultsDelegate=new StockControlDefaultsDelegate();
			log.log(Log.FINE,"...........Inside Controller.......");
			stockControlDefaultsDelegate.validateStockHolderTypeCode(stock);
			requestRefNo = stockControlDefaultsDelegate.saveStockRequestDetails(stock);
		}
		catch(BusinessDelegateException businessDelegateException){
//printStackTrrace()();
			errorVos = handleDelegateException(businessDelegateException);
		}
		if(errorVos==null || errorVos.size()==0){
			ErrorVO error = null;
			Object[] obj = {requestRefNo };
			error = new ErrorVO("stockcontrol.defaults.requestsavedwithrefno", obj);// Saved Successfully
			error.setErrorDisplayType(ErrorDisplayType.STATUS);
			errors.add(error);
			invocationContext.addAllError(errors);

		}

		maintainStockRequestForm.setReqRefNo("");
		maintainStockRequestForm.setReqStock("");
		maintainStockRequestForm.setAllocatedStock("");
		maintainStockRequestForm.setRemarks("");
		maintainStockRequestForm.setAppRejRemarks("");
		//Modified by A-1927 @ NRT on 02-Aug-2007for NCA Bug Fix starts
		//Modified by A-5117 for ICRD-4259
		maintainStockRequestForm.setDocType("");
		maintainStockRequestForm.setSubType("");
		//Modified by A-1927 @ NRT on 02-Aug-2007for NCA Bug Fix ends
		maintainStockRequestForm.setStatus(NEW);
		maintainStockRequestForm.setManual(false);
		maintainStockRequestForm.setCode("");
		maintainStockRequestForm.setStockHolderType("");
		maintainStockRequestForm.setPartnerAirline(false);
		//maintainStockRequestForm.setAwbPrefix("");
		maintainStockRequestForm.setMode("I");
		Collection<StockHolderPriorityVO> stockHlderPriorityVOs
							= session.getPrioritizedStockHolders();
        for(StockHolderPriorityVO stockHolderPriority:stockHlderPriorityVOs){
      		 stockHolderPriority.setStockHolderCode(null);
        }
		String dateString = DateUtilities.getCurrentDate(CALENDAR_DATE_FORMAT);
		maintainStockRequestForm.setDateOfReq(dateString);
		//saveStockRequestDetails(stock);
		log.exiting("SaveStockRequestCommand","execute");
		invocationContext.target = "screenload_success";
	}

	private Collection<ErrorVO> validateForm(MaintainStockRequestForm form,
			MaintainStockRequestSession session) {

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		boolean isValid = true;

		String dateStr = DateUtilities.getCurrentDate(CALENDAR_DATE_FORMAT);
		if("Allocated".equals(form.getStatus()) && "U".equals(form.getMode())){
		isValid = false;
		Object[] obj = { "Status-Allocated" };
		error = new ErrorVO("stockcontrol.defaults.stkreqofstatusalloctedcannotmodified", obj);
		error.setErrorDisplayType(ErrorDisplayType.ERROR);
		errors.add(error);
		}
		//System.out.println("\n\n --------------- Mode------------" + form.getMode());
		if("APPROVED".equals(form.getStatus().toUpperCase()) && "U".equals(form.getMode())){
				Object[] obj = { form.getStatus()};
				error = new ErrorVO("stockcontrol.defaults.stkreqofstatusapprovedcannotmodified", obj);
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
		}
		if("COMPLETED".equals(form.getStatus().toUpperCase()) && "U".equals(form.getMode())){

				Object[] obj = {form.getStatus() };
				error = new ErrorVO("stockcontrol.defaults.stkreqofstatuscompletedcannotmodified", obj);
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
		}
		/*Added by Amritha*/
		if("CANCELLED".equals(form.getStatus().toUpperCase()) && "U".equals(form.getMode())){
				Object[] obj = { form.getStatus() };
				error = new ErrorVO("stockcontrol.defaults.stkreqofstatuscancelledcannotmodified", obj);
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
		}
		if("REJECTED".equals(form.getStatus().toUpperCase()) && "U".equals(form.getMode())){
				Object[] obj = { form.getStatus() };
				error = new ErrorVO("stockcontrol.defaults.rejstkcannotmodified", obj);
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
		}
		if ("".equals(form.getDateOfReq())||form.getDateOfReq().length()==0){
				isValid = false;
				Object[] obj = { "DateOfRequest" };
				error = new ErrorVO("stockcontrol.defaults.plzenterreqdate", obj);
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
		}else{
			if (!DateUtilities.isValidDate(form.getDateOfReq(),"dd-MMM-yyyy")) {
				isValid = false;
				Object[] obj = { "DateOfReq" };
				error = new ErrorVO("stockcontrol.defaults.dateofreqnotvalid", obj);
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}
		}

		if ("".equals(form.getDocType())||form.getDocType().length()==0){

				Object[] obj = { "Document Type" };
				error = new ErrorVO("stockcontrol.defaults.selectdoctype", obj);
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
		}
		if ("".equals(form.getSubType())||form.getSubType().length()==0){

				Object[] obj = { "Document Sub Type" };
				error = new ErrorVO("stockcontrol.defaults.selectdocsubtype", obj);
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
		}
		if ("".equals(form.getReqStock())||form.getReqStock().length()==0){

				Object[] obj = { "Requested Stock" };
				error = new ErrorVO("stockcontrol.defaults.plsenterreqstk", obj);
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
		}

		/*String stockHold[]=form.getStockHolder();
		boolean checkStock=false;
		for(int i=0;i<stockHold.length;i++){
			if(!stockHold[i].trim().equals("") || !stockHold[i].trim().equals(null) ){
				checkStock=true;
				break;
			}
		}
		System.out.println("\n\n**********Value of checkStock**************\n\n"+checkStock);*/
		if ("".equals(form.getCode())||form.getCode().length()==0){
				Object[] obj = { "StockHolderCode" };
				error = new ErrorVO("stockcontrol.defaults.plsenterstkholdertype", obj);
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
		}


		if ("".equals(form.getStockHolderType())||form.getStockHolderType().length()==0){
				Object[] obj = { "StockHolderType" };
				error = new ErrorVO("stockcontrol.defaults.plsenterstkholdertype", obj);
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
		}
        //Modified by A-7373 for ICRD-244808
		if (isValid && DateUtilities.isLessThan(dateStr,form.getDateOfReq(), "dd-MMM-yyyy")&&"I".equals(form.getMode())){
				isValid = false;
				Object[] obj = { "DateOfRequestExceeds" };
				error = new ErrorVO("stockcontrol.defaults.reqdatecannotgreaterthancurrentdate", obj);
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
		}
		if ("H".equalsIgnoreCase(form.getStockHolderType())){
			Object[] obj = { "StockHolderType" };
			error = new ErrorVO("stockcontrol.defaults.hqcannotcreatereq", obj);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}

		return errors;
}

	/**
	 * This method will be invoked at the time of save
	 *
	 * @param companyCode
	 * @return
	 */
	public Map<String, Collection<OneTimeVO>> handleScreenLoadDetails(String companyCode) {
			Map<String, Collection<OneTimeVO>> oneTimes = null;
			try {

				Collection<String> fieldTypes = new ArrayList<String>();
				fieldTypes.add("stockcontrol.defaults.status");
				fieldTypes.add("stockcontrol.defaults.stockholdertypes");

				oneTimes = new SharedDefaultsDelegate().findOneTimeValues(companyCode, fieldTypes);


			} catch (BusinessDelegateException businessDelegateException) {
				log.log(Log.SEVERE, "BusinessDelegateException caught from findOneTimeValues");
			}
			return oneTimes;
	}

	private String upper(String input){

		if(input!=null){
			return input.trim().toUpperCase();
		}else{
			return "";
		}
	}

	public String getAirlineIdentifier(String awbPrefixTokens){
		LogonAttributes logonAttributes=getApplicationSession().getLogonVO();
		if(awbPrefixTokens!=null && awbPrefixTokens.trim().length()>0){
			String[] tokens=awbPrefixTokens.split("-");
			return tokens.length>2?
					tokens[2]:""+logonAttributes.getOwnAirlineIdentifier();
		}
		return ""+logonAttributes.getOwnAirlineIdentifier();
	}
	
	/**
	 * @author A-5153
	 * @param transactionCode
	 * @return TransactionPrivilegeNewVO
	 */
	private TransactionPrivilegeNewVO getPrivilegeVO(String transactionCode) {
		log.entering("SaveStockRequestCommand", "getPrivilegeVO");
		List<TransactionPrivilegeNewVO> privilegeList = null;
		try {
			privilegeList = (ArrayList<TransactionPrivilegeNewVO>) TransactionPrivilegeHelper
					.getAllowededPrivilegesForTransaction(transactionCode);
		} catch (SystemException e) {
			log.log(Log.SEVERE, e.getMessage());
		}
		log.exiting("SaveStockRequestCommand", "getPrivilegeVO");
		if (privilegeList != null && !privilegeList.isEmpty()) {
			return privilegeList.get(0);
		}
		return null;
	}

}

