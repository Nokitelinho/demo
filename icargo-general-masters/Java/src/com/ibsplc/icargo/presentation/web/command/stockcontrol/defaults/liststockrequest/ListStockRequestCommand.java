/*
 * ListStockRequestCommand.java Created on Aug 26, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.liststockrequest;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderPriorityVO;
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
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ListStockRequestSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ListStockRequestForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;



/**
 * @author A-1952
 *
 */
public class ListStockRequestCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	
	private static final String TX_COD_STOCK_REQUEST = "STOCK_REQUEST"; 

	/**
	 * The execute method in BaseCommand
	 *
	 * @author A-1952
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
  public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

	  log.entering("ListStockRequestCommand", "execute");

  	ListStockRequestForm listStockRequestForm = (ListStockRequestForm) invocationContext.screenModel;
  	ListStockRequestSession session =
  		getScreenSession("stockcontrol.defaults", "stockcontrol.defaults.liststockrequest");
  	 if("Y".equals(listStockRequestForm.getCheckList())){
  		listStockRequestForm.setDisplayPage("1");
  		listStockRequestForm.setLastPageNumber("0");
  		listStockRequestForm.setCheckList("");
        }
  	 String[] stkhldvo =listStockRequestForm.getStockholderCode();
	 Collection<StockHolderPriorityVO> stockHolderPriorityVO = session.getPrioritizedStockHolders();
	 int count = 0;
	 if(stkhldvo!=null){
	 for(StockHolderPriorityVO stockHolderPriority:stockHolderPriorityVO){
		 stockHolderPriority.setStockHolderCode(stkhldvo[count]);
		 count++;
	 }
	 }

	session.setPrioritizedStockHolders(stockHolderPriorityVO);
  	Collection<ErrorVO> errors = null;
  	 session.setPageStockRequestVO(null);
  	errors = validateForm(listStockRequestForm,session);

  	if (errors != null && errors.size() > 0) {
				invocationContext.addAllError(errors);
				invocationContext.target = "screenload_failure";
				return;
	}

  	StockRequestFilterVO stockRequestFilterVO =
  		handleSearchDetails(listStockRequestForm);
  	
  	TransactionPrivilegeNewVO privilegeNewVO = getPrivilegeVO(TX_COD_STOCK_REQUEST);
	if(privilegeNewVO!=null){
		stockRequestFilterVO.setPrivilegeLevelType(privilegeNewVO.getLevelType());
		stockRequestFilterVO.setPrivilegeLevelValue(privilegeNewVO.getTypeValue());
		stockRequestFilterVO.setPrivilegeRule(privilegeNewVO.getPrivilegeCode());
	}
  	
  	// newly added line for close command implementation
  	session.setStockRequestFilterDetails(stockRequestFilterVO);
   	StockControlDefaultsDelegate stockControlDefaultsDelegate = new StockControlDefaultsDelegate();
   	
   	/* changed by A-5216
	 * to enable last link and total record count
	 * for Jira Id: ICRD-20959 and ScreenId: STK016
	 */
		if (!StockRequestFilterVO.FLAG_YES.equals(listStockRequestForm
				.getCountTotalFlag())
				&& session.getTotalRecords() != null
				&& session.getTotalRecords().intValue() != 0) {
			stockRequestFilterVO.setTotalRecords(session.getTotalRecords()
					.intValue());
		} else {
			stockRequestFilterVO.setTotalRecords(-1);
		}
		try {
			Page<StockRequestVO> stockRequestVO = stockControlDefaultsDelegate
					.findStockRequests(stockRequestFilterVO, Integer
							.parseInt(listStockRequestForm.getDisplayPage()));
							
		if((stockRequestVO==null || stockRequestVO.size()==0 )
				&&	!"Y".equals(listStockRequestForm.getOneRow())){
			log.log(Log.FINE,
					"\n\n.....................stockRequestVO...............> ",
					stockRequestVO);
			ErrorVO error = null;
			/*Object[] obj = { "norecords" };
			error = new ErrorVO("stockcontrol.defaults.norecordsfound", obj);
			error.setErrorDisplayType(ErrorDisplayType.INFO);*/
			error = new ErrorVO("stockcontrol.defaults.norecordsornotauthorized");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
			invocationContext.addAllError(errors);
			invocationContext.target = "screenload_failure";
			return;
        }
        Collection<OneTimeVO> oneTimeVOs =session.getOneTimeStatus();

		if(stockRequestVO!=null){
			for(StockRequestVO srVO : stockRequestVO){
				if(oneTimeVOs!=null){
					srVO.setStatus(findOneTimeDescription(oneTimeVOs,srVO.getStatus()));

					}
				}
		}

  		session.setPageStockRequestVO((Page<StockRequestVO>)stockRequestVO);

    	}catch(BusinessDelegateException businessDelegateException){
    		log.log(Log.SEVERE, "BusinessDelegateException caught from findStockRequests");
		}
  	invocationContext.target ="screenload_success";
}


    /**
	 * creating the searching vo
	 * @param listStockRequestForm
	 * @return StockRequestFilterVO
	 */
	 private StockRequestFilterVO handleSearchDetails(
			 ListStockRequestForm listStockRequestForm){

		ListStockRequestSession session =
			getScreenSession("stockcontrol.defaults", "stockcontrol.defaults.liststockrequest");
		StockRequestFilterVO stockRequestFilterVOsd = new StockRequestFilterVO();
		stockRequestFilterVOsd.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
		stockRequestFilterVOsd.setDocumentType(listStockRequestForm.getDocType());
		stockRequestFilterVOsd.setManual(listStockRequestForm.isManual());
		stockRequestFilterVOsd.setRequestRefNumber(upper(listStockRequestForm.getReqRefNo()));
		stockRequestFilterVOsd.setStatus(listStockRequestForm.getStatus());
		stockRequestFilterVOsd.setDocumentSubType(listStockRequestForm.getSubType());
		stockRequestFilterVOsd.setStockHolderCode(upper(listStockRequestForm.getCode()));
		stockRequestFilterVOsd.setStockHolderType(listStockRequestForm.getStockHolderType()); 

		stockRequestFilterVOsd.setAirlineIdentifier(getAirlineIdentifier(listStockRequestForm.getAwbPrefix()));


		LocalDate from = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
		LocalDate to = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);

		if(listStockRequestForm.getFromDate()!= null &&
				listStockRequestForm.getFromDate().trim().length()!=0){

		    stockRequestFilterVOsd.setFromDate(from.setDate(listStockRequestForm.getFromDate()));
		}

		if(listStockRequestForm.getToDate()!= null &&
				listStockRequestForm.getToDate().trim().length()!=0 ){

			stockRequestFilterVOsd.setToDate(to.setDate( listStockRequestForm.getToDate()));
		}

		return stockRequestFilterVOsd;

	}
			/**
			 * method to convert to upper case
			 * @param input
			 *
			 */
		private String upper(String input){

			if(input!=null){
				return input.trim().toUpperCase();
			}else{
				return "";
			}
		}

		/**
		 * method to validateForm
		 * @param form
		 * @param session
		 * @return
		 */

		private Collection<ErrorVO> validateForm(ListStockRequestForm form,
					ListStockRequestSession session) {
				Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
				ErrorVO error = null;
				boolean isValid = true;
				if(upper(form.getReqRefNo())==null ||
						"".equals(upper(form.getReqRefNo()).trim())){

					if ("".equals(form.getDocType())) {
						isValid = false;
						Object[] obj = { "DocType" };
						error = new ErrorVO("stockcontrol.defaults.plsenterdoctype", obj);
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
					}
					if ("".equals(form.getFromDate())) {
						isValid = false;
						Object[] obj = { "FromDate" };
						error = new ErrorVO("stockcontrol.defaults.plsenterfrmdate", obj);
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
					}
					if ("".equals(form.getToDate())) {
						isValid = false;
						Object[] obj = { "ToDate" };
						error = new ErrorVO("stockcontrol.defaults.plsentertodate", obj);
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
					}
					if(!form.getFromDate().equals(form.getToDate())){
						if (!DateUtilities.isLessThan(form.getFromDate(), form.getToDate(),
								"dd-MMM-yyyy")) {
							isValid = false;
							Object[] obj = { "Date" };
							error = new ErrorVO("stockcontrol.defaults.todateearlierthanfrmdate", obj);
							error.setErrorDisplayType(ErrorDisplayType.ERROR);
							errors.add(error);
						}
					}
			}
		        return errors;

     }




		/**
		* This method will give the status description
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
	public String getAirlineIdentifier(String awbPrefixTokens){		
		if(awbPrefixTokens!=null && awbPrefixTokens.trim().length()>0){
			String[] tokens=awbPrefixTokens.split("-");
			return tokens.length>2?
					tokens[2]:""+getApplicationSession().getLogonVO().getOwnAirlineIdentifier();
		}
		return ""+getApplicationSession().getLogonVO().getOwnAirlineIdentifier();
	}


	/**
	 * getPrivilegeVO()
	 * @param transactionCode
	 * @return TransactionPrivilegeNewVO
	 */
	private TransactionPrivilegeNewVO getPrivilegeVO(
			String transactionCode) {
		log.entering("ListStockRequestCommand", "getPrivilegeVO");
		List<TransactionPrivilegeNewVO> privilegeList=null;
		try {
			privilegeList = (ArrayList<TransactionPrivilegeNewVO>) 
			TransactionPrivilegeHelper.getAllowededPrivilegesForTransaction(transactionCode);
		} catch (SystemException e) {			
			log.log(Log.SEVERE,e.getMessage());
		}
		log.exiting("ListStockRequestCommand", "getPrivilegeVO");
		if(privilegeList!=null && !privilegeList.isEmpty()){
			return privilegeList.get(0);
		}
		return null;
	}

}





