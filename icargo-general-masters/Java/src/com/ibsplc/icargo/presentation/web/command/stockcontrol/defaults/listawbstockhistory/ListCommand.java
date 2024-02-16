/*
 * ListCommand.java Created on Jan 14, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */



package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.listawbstockhistory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.shared.document.vo.DocumentVO;
import com.ibsplc.icargo.business.shared.document.vo.SharedRangeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockDepleteFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRangeFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRangeHistoryVO;
import com.ibsplc.icargo.framework.security.privilege.TransactionPrivilegeHelper;
import com.ibsplc.icargo.framework.security.privilege.vo.TransactionPrivilegeNewVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.document.DocumentTypeDelegate;
import com.ibsplc.icargo.presentation.delegate.stockcontrol.defaults.StockControlDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ListStockRangeHistorySession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ListAwbStockHistoryForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;
/**
 * @Author-3155 & A-3184
 * 
 */
public class ListCommand extends BaseCommand {
	 
	private Log log = LogFactory.getLogger("STOCK CONTROLLER");
	private static final String TX_COD_MONITOR_STOCK = "MONITOR_STOCK";
	
	
	
	/**
	 * The execute method in BaseCommand
	 * 
	 * @author A-3184
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
    throws CommandInvocationException {

		log.entering("ListCommand","execute--->"); 
		Collection<ErrorVO> errors = null;
		
		
		ListAwbStockHistoryForm listAwbStockHistoryForm = (ListAwbStockHistoryForm) invocationContext.screenModel;
		ListStockRangeHistorySession session =
			getScreenSession("stockcontrol.defaults","stockcontrol.defaults.listawbstockhistory");
		
		ApplicationSessionImpl applicationSession = getApplicationSession();
	    LogonAttributes logonAttributesVO  =  applicationSession.getLogonVO();
	    errors = validateForm(listAwbStockHistoryForm,session);
	    if (errors != null && errors.size() > 0) {
	    	listAwbStockHistoryForm.setErrorFlag("Y");
	    	session.setStockRangeHistoryVOs(null);
			invocationContext.addAllError(errors);
			invocationContext.target = "screenload_success";
			listAwbStockHistoryForm.setOnList("N");
			return; 
	    }  		 
		LocalDate start= new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
		LocalDate end= new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
		String startRange= listAwbStockHistoryForm.getRangeFrom();
		String endRange=listAwbStockHistoryForm.getRangeTo();
		String stockHolderCode=listAwbStockHistoryForm.getStockHolderCode();
		String status=listAwbStockHistoryForm.getStockStatus();
		String stockType=listAwbStockHistoryForm.getStockType();
		String accountNumber=listAwbStockHistoryForm.getAccountNumber();     
		String docsubtype=listAwbStockHistoryForm.getDocSubType();
		
		 //Added as part of ICRD-76008
		String companyCode=logonAttributesVO.getCompanyCode();
		if(startRange != null && startRange.trim().length() > 0
        		&& endRange != null && endRange.trim().length() > 0){		
		DocumentTypeDelegate documentTypeDelegate=new DocumentTypeDelegate();
		SharedRangeVO sharedRangeVO=new SharedRangeVO();
		DocumentVO documentVO=new DocumentVO();
		documentVO.setCompanyCode(companyCode);
        documentVO.setDocumentType(listAwbStockHistoryForm.getDocType());        
        documentVO.setDocumentSubType(listAwbStockHistoryForm.getDocSubType());
        sharedRangeVO.setFromrange(startRange);
        sharedRangeVO.setToRange(endRange); 
        
	        Collection<SharedRangeVO> sharedRangeVOs=new ArrayList<SharedRangeVO>();
	        sharedRangeVOs.add(sharedRangeVO);
	        documentVO.setRange(sharedRangeVOs);
	        try {
				documentTypeDelegate.validateRange(documentVO);
			} catch (BusinessDelegateException e) {
				ErrorVO error = null;
				error = new ErrorVO("stockcontrol.defaults.invalidalphanumericrange");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);	
				invocationContext.addAllError(errors);
				invocationContext.target = "screenload_success";
				listAwbStockHistoryForm.setOnList("N");
				return;
				
			}
        }      	
		 
		//session.removeAllAttributes();   
		
		
		
		StockRangeFilterVO stockRangeFilterVO= new StockRangeFilterVO();
		Page<StockRangeHistoryVO> stockRangeHistoryVOs= null; //Modified by A-2881
		stockRangeFilterVO.setCompanyCode(companyCode);
		//Modified by A-2881 for ICRD-3082
		stockRangeFilterVO.setAirlineIdentifier((getAirlineIdentifier(listAwbStockHistoryForm
				.getAwbPrefix())));
		//Edited as part of ICRD-46860
		stockRangeFilterVO.setStartRange(startRange);
		stockRangeFilterVO.setEndRange(endRange);
		if(stockHolderCode == null || stockHolderCode.trim().length()==0){
		stockRangeFilterVO.setStartDate(start.setDate(listAwbStockHistoryForm.getStartDate()));
		stockRangeFilterVO.setEndDate(end.setDate(listAwbStockHistoryForm.getEndDate()));
		}else if ((!"".equals(listAwbStockHistoryForm.getEndDate())||listAwbStockHistoryForm.getEndDate().length()>0)&&
				 (!"".equals(listAwbStockHistoryForm.getStartDate())||listAwbStockHistoryForm.getStartDate().length()>0)){
			stockRangeFilterVO.setStartDate(start.setDate(listAwbStockHistoryForm.getStartDate()));
			stockRangeFilterVO.setEndDate(end.setDate(listAwbStockHistoryForm.getEndDate()));
		}
		stockRangeFilterVO.setFromStockHolderCode(stockHolderCode);
		stockRangeFilterVO.setStatus(status);
		stockRangeFilterVO.setDocumentSubType(docsubtype);  
		stockRangeFilterVO.setDocumentType(listAwbStockHistoryForm.getDocType());
		
		stockRangeFilterVO.setAccountNumber(accountNumber);
		if(listAwbStockHistoryForm.isHistory()){
			stockRangeFilterVO.setHistory(true); 
					}else{
						stockRangeFilterVO.setHistory(false);
	    }
		if("B".equalsIgnoreCase(stockType)){
			stockRangeFilterVO.setRangeType("");
					}else{
						stockRangeFilterVO.setRangeType(stockType);
	    }
		//Added by A-2881 for ICRD-3082
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
		
		//Added by A-2881
		String dispPage = listAwbStockHistoryForm.getDisplayPage();
		int displayPage = Integer.parseInt(dispPage);
		stockRangeFilterVO.setPageNumber(displayPage); 
		
		log.log(Log.INFO, "navigationMode: ", listAwbStockHistoryForm.getNavigationMode());
		log.log(Log.INFO, "lastPageNumber: ", listAwbStockHistoryForm.getLastPageNum());
		if(ListAwbStockHistoryForm.NAV_MOD_LIST.equalsIgnoreCase(listAwbStockHistoryForm.getNavigationMode()) || listAwbStockHistoryForm.getNavigationMode() == null){
			log.log(Log.INFO, "list action");
			stockRangeFilterVO.setTotalRecordsCount(-1);
			stockRangeFilterVO.setPageNumber(1);
		}else if(ListAwbStockHistoryForm.NAV_MOD_PAGINATION.equalsIgnoreCase(listAwbStockHistoryForm.getNavigationMode())){
			log.log(Log.INFO, "navigation action");
			if( session !=null ){
				stockRangeFilterVO.setTotalRecordsCount(session.getTotalRecordCount());
			}
			if(listAwbStockHistoryForm.getDisplayPage() != null && !("").equals(listAwbStockHistoryForm.getDisplayPage())){
				stockRangeFilterVO.setPageNumber(Integer.parseInt(listAwbStockHistoryForm.getDisplayPage()));
			}
		}
		log.log(Log.INFO, "pageNumber: ", stockRangeFilterVO.getPageNumber());
		
		TransactionPrivilegeNewVO privilegeNewVO = getPrivilegeVO(TX_COD_MONITOR_STOCK);
		if(privilegeNewVO!=null){
			stockRangeFilterVO.setPrivilegeLevelType(privilegeNewVO.getLevelType());
			stockRangeFilterVO.setPrivilegeLevelValue(privilegeNewVO.getTypeValue());
			stockRangeFilterVO.setPrivilegeRule(privilegeNewVO.getPrivilegeCode());
		}
		
		//Added by A-5220 for ICRD-20959 ends
		
		try{
			log.log(Log.INFO, "$$$$ test Command: ", stockRangeFilterVO);
			StockControlDefaultsDelegate stockControlDefaultsDelegate=new StockControlDefaultsDelegate();
			
			StockDepleteFilterVO stockDepleteFilterVO = new StockDepleteFilterVO();
			stockDepleteFilterVO.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
			stockDepleteFilterVO.setAirlineId(getApplicationSession().getLogonVO().getOwnAirlineIdentifier());
			//Commented by A-5153 for CRQ_ICRD-38007
			//stockControlDefaultsDelegate.autoDepleteStock(stockDepleteFilterVO);
			stockRangeHistoryVOs =stockControlDefaultsDelegate.findStockRangeHistoryForPage(stockRangeFilterVO);
			
			if(stockRangeHistoryVOs==null || stockRangeHistoryVOs.size()==0 )
				{
				session.setPageStockRangeHistoryVOs(null); 
				ErrorVO error = null;
				//Added by A-4803 for bug ICRD-14360
				listAwbStockHistoryForm.setStatusFlag("N");				
				error = new ErrorVO("stockcontrol.defaults.norecordsmonitornotauthorized");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
				invocationContext.addAllError(errors);
				invocationContext.target = "screenload_success";
				listAwbStockHistoryForm.setOnList("N");
				listAwbStockHistoryForm.setErrorFlag("Y");
				return;

				}
			 Collection<OneTimeVO> oneTimeVOs = new ArrayList<OneTimeVO>();
			 	if(session.getOneTimeStockStatus()!=null){
			 		oneTimeVOs.addAll(session.getOneTimeStockStatus());
			 	}
			 	if(session.getOneTimeAwbType()!=null){
			 		oneTimeVOs.addAll(session.getOneTimeAwbType());
			 	}
			 	if(session.getOneTimeStockUtilizationStatus()!=null){
			 		oneTimeVOs.addAll(session.getOneTimeStockUtilizationStatus());
			 	}
				if(stockRangeHistoryVOs!=null){
					for(StockRangeHistoryVO srhVO : stockRangeHistoryVOs){
						if(oneTimeVOs!=null){
							srhVO.setStatus(findOneTimeDescription(oneTimeVOs,srhVO.getStatus()));
							srhVO.setRangeType(findOneTime(oneTimeVOs,srhVO.getRangeType()));
							}

						}
				}
				//Added by A-5220 for ICRD-20959 starts
				session.setTotalRecordCount(stockRangeHistoryVOs.getTotalRecordCount());
				//Added by A-5220 for ICRD-20959 starts
				session.setPageStockRangeHistoryVOs(stockRangeHistoryVOs);
				
		}
		catch(BusinessDelegateException businessDelegateException){
//printStackTrrace()();
			log.log(Log.FINE,"\n\n<---------------------INSIDE EXCEPTION------------>");
			errors = handleDelegateException(businessDelegateException);
			invocationContext.addAllError(errors);
			return;

}
		//Added by A-4803 for bug ICRD-14360
		listAwbStockHistoryForm.setStatusFlag("Y");
		log.log(Log.INFO,"List Command Executed Complete!!!!!!!!!!! ");
		invocationContext.target = "screenload_success";
	}
	private String findOneTimeDescription(Collection<OneTimeVO> oneTimeVOs, String status){
		for (OneTimeVO oneTimeVO:oneTimeVOs){
			if(status.equals(oneTimeVO.getFieldValue())){
				return oneTimeVO.getFieldDescription();
			}
		}
		return "";
	}
	private String findOneTime(Collection<OneTimeVO> oneTimeVOs, String stockType){
		for (OneTimeVO oneTimeVO:oneTimeVOs){
			if(stockType.equals(oneTimeVO.getFieldValue())){
				return oneTimeVO.getFieldDescription();
			}
		}
		return null;
	}
	/**
	 * method to validateForm
	 * @param form
	 * @param session
	 * @return
	 */

	 private Collection<ErrorVO> validateForm(ListAwbStockHistoryForm form,
			 ListStockRangeHistorySession session) {

			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
			ErrorVO error = null;
			boolean isValid=true;
			String[] historyStatus={"A","T","R","G","H","I"};  
			String[] blackListStatus={"B","O"};
			if((form.getStockHolderCode()==null || form.getStockHolderCode().trim().length()==0)
					&& ("".equals(form.getStartDate())||form.getStartDate().length()==0)
					 && ("".equals(form.getEndDate())||form.getEndDate().length()==0)){
						isValid=false;
						Object[] obj = { "MandatoryFields" };
						error = new ErrorVO("stockcontrol.defaults.mandatory", obj);
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
						return errors;
			}
			if(form.getStockHolderCode()==null || form.getStockHolderCode().trim().length()==0){
				if ("".equals(form.getStartDate())||form.getStartDate().length()==0){
	
						isValid=false;
						Object[] obj = { "StartDate" };
						error = new ErrorVO("stockcontrol.defaults.plsenterfrmdate", obj);
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
				}
			}else if (("".equals(form.getStartDate())||form.getStartDate().length()==0)&&
					(!"".equals(form.getEndDate())||form.getEndDate().length()>0)){
						isValid=false;
						Object[] obj = { "StartDate" };
						error = new ErrorVO("stockcontrol.defaults.plsenterfrmdate", obj);
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
			}
			if(form.getStockHolderCode()==null || form.getStockHolderCode().trim().length()==0){
				if ("".equals(form.getEndDate())||form.getEndDate().length()==0){
	
						isValid=false;
						Object[] obj = { "EndDate" };
						error = new ErrorVO("stockcontrol.defaults.plsentertodate", obj);
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
				}
			}else if ((!"".equals(form.getStartDate())||form.getStartDate().length()>0)&&
					("".equals(form.getEndDate())||form.getEndDate().length()==0)){
						isValid=false;
						Object[] obj = { "EndDate" };
						error = new ErrorVO("stockcontrol.defaults.plsentertodate", obj);
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
			}
			if(form.getStartDate().length()>0 && form.getEndDate().length()>0){
				if (isValid && DateUtilities.isLessThan(form.getEndDate(),form.getStartDate(), "dd-MMM-yyyy")){
							isValid = false;
							Object[] obj = { "FromDateExceedsToDate" };
							error = new ErrorVO("stockcontrol.defaults.todateearlierthanfrmdate", obj);
							error.setErrorDisplayType(ErrorDisplayType.ERROR);
							errors.add(error);
				}	
			}
			if (!"".equals(form.getRangeTo()) && findLong(form.getRangeFrom())>findLong(form.getRangeTo())){

					Object[] obj = { "RangeFrom exceeds" };
					error = new ErrorVO("SKCM_099", obj);
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
			}
			//code getting system parameter removed for ICRD-30533
			//Added by A-2881 for ICRD-14358 - Ends
			if(Arrays.asList(historyStatus).contains(form.getStockStatus())){
				if(form.getStockHolderCode()==null ||form.getStockHolderCode().trim().length()==0){
					isValid = false;
					error = new ErrorVO("stockcontrol.defaults.listawbstockhistory.stockholdercodemandatory");
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
				}
			}
			
			if(Arrays.asList(blackListStatus).contains(form.getStockStatus())){
				if(form.getUserId()==null ||form.getUserId().trim().length()==0){
					isValid = false;
					error = new ErrorVO("stockcontrol.defaults.listawbstockhistory.useridmandatory");
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
				}
			}
			
            //Added by A-2881 for ICRD-14358 -Ends
		return errors;
}
	/** To get the numeric value of the string
	 *
	 * @param range
	 * @return Numeric value
	 */
	private  long findLong(String range){
			log.log(Log.FINE,"...........Entering ascii conversion.......");
			char[] sArray=range.toCharArray();
			long base=1;
			long sNumber=0;
			for(int i=sArray.length-1;i>=0;i--){
				sNumber+=base*calculateBase(sArray[i]);
				int count=sArray[i];
				if (count>57) {
					base*=26;
				} else {
					base*=10;
				}
			}
			return sNumber;
	}

	private  long calculateBase(char str){
			long formatStr=str;
			long base=0;
			try{
				base=Integer.parseInt(""+str);
			}catch(NumberFormatException numberFormatException){
				base=formatStr-65;
			}
			return base;
    }
	
	
	/**
	 * @author A-2881
	 * @param awbPrefixFromForm
	 * @return
	 */
	private int getAirlineIdentifier(String awbPrefixFromForm) {		
		if (awbPrefixFromForm != null && awbPrefixFromForm.trim().length() > 0) {
			return Integer.parseInt(extractTokensFromAwbPrefix(awbPrefixFromForm, 2));
		}else{
			return getApplicationSession().getLogonVO()
			.getOwnAirlineIdentifier();
		}
	}
	
	/**
	 * @author A-2881
	 * @param awbPrefixFromForm
	 * @param tokenNum
	 * @return
	 */
	private String extractTokensFromAwbPrefix(String awbPrefixFromForm,int tokenNum){
		String[] tokens = awbPrefixFromForm.split("-");
		String token="";
		switch (tokenNum) {
		case 0: {
			break;
		}
		case 1: {
			token = tokens[tokenNum];
			break;
		}
		case 2: {
			token = (tokens != null && tokens.length > 2) ? tokens[tokenNum]
					: "" + getApplicationSession().getLogonVO().getOwnAirlineIdentifier();
			break;
		}
		default: {

		}
		}
		
		return token;
			
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
}

