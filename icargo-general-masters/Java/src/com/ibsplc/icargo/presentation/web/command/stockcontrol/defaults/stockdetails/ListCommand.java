/*
 * ListCommand.java Created on May18,2011
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.stockdetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockDetailsFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockDetailsVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.stockcontrol.defaults.StockControlDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.StockDetailsSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.StockDetailsForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class ListCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	private ErrorVO error = null;
	private Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {

		log.entering("ListStockDetailsCommand", "execute");
		StockDetailsForm stockDetailsForm = (StockDetailsForm) invocationContext.screenModel;
		StockDetailsSession session = getScreenSession("stockcontrol.defaults",
				"stockcontrol.defaults.stockdetails");
		StockDetailsFilterVO stockDetailsFilterVO = null;
		StockControlDefaultsDelegate stockControlDefaultsDelegate = new StockControlDefaultsDelegate();
		stockDetailsFilterVO=handleSearchDetails(stockDetailsForm,session);
		HashMap indexMap = null;
		HashMap finalMap = null;	
		if(session.getIndexMap()!=null){
			indexMap=session.getIndexMap();
		}
		if (indexMap == null) {
			log.log(Log.FINE,"INDEX MAP IS NULL");					
			indexMap = new HashMap();
			indexMap.put("1", "1");
		}
		int nAbsoluteIndex = 0;
		String strAbsoluteIndex = (String)indexMap.get(stockDetailsForm.getDisplayPage());
		if(strAbsoluteIndex != null){
			nAbsoluteIndex = Integer.parseInt(strAbsoluteIndex);
		}
		
		stockDetailsFilterVO.setAbsoluteIndex(nAbsoluteIndex);
		stockDetailsForm.setAbsoluteIndex(String.valueOf(nAbsoluteIndex));
		stockDetailsFilterVO.setPageNumber(Integer.parseInt(stockDetailsForm.getDisplayPage()));
		
	
		log.log(Log.FINE, "stockDetailsFilterVO", stockDetailsFilterVO);
		try{

			 errors = validateForm(stockDetailsForm,stockDetailsFilterVO);
			//Integer.parseInt(displayPage
			 
			 if(errors!=null && errors.size()>0){
				 invocationContext.addAllError(errors);
				 invocationContext.target ="list_success";
					
				 return;
			 }
			session.setStockDetails(null);
			Page<StockDetailsVO>  stockDetailsVO=stockControlDefaultsDelegate.listStockDetails(stockDetailsFilterVO);
			session.setStockDetails(stockDetailsVO);
			if(stockDetailsVO!=null && stockDetailsVO.size()>0){
				session.setStockDetails(stockDetailsVO);
				finalMap = indexMap;
				finalMap = 	buildIndexMap(indexMap,session.getStockDetails());
				session.setIndexMap(finalMap);
			}
			else{
				Object[] obj = { "norecords" };
				error = new ErrorVO("stockcontrol.defaults.norecordsfound",obj);
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
				invocationContext.addAllError(errors);
				 invocationContext.target ="list_success";
				 return;
				
			}
			
		}
		catch(BusinessDelegateException businessDelegateException) {
//printStackTrrace()();
			errors = handleDelegateException(businessDelegateException);
			log.log(Log.FINE, "\n\n......EXCEPTIONNNNNNNN.........");
		}
		if(errors!=null){
			 invocationContext.addAllError(errors);
			 invocationContext.target ="list_success";
				
			 return;
		 }
		invocationContext.target ="list_success";
		
	}
	public Collection<ErrorVO> validateForm(StockDetailsForm stockDetailsForm,
			StockDetailsFilterVO stockDetailsFilterVO ){
		
		LocalDate fromDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
		LocalDate toDt = new LocalDate(LocalDate.NO_STATION, Location.NONE,
				false);	
		fromDate.setDate(stockDetailsFilterVO.getStartDate().toDisplayDateOnlyFormat());
		fromDate.addDays(90);
		if(fromDate.before(stockDetailsFilterVO.getEndDate())){
			Object[] obj = { "Difference more than ninety" };
			error = new ErrorVO("stockcontrol.defaults.err.differencemorethanninety", obj);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		
		
		if (stockDetailsForm.getFromDate().trim().length() != 0
				&& stockDetailsForm.getToDate().trim().length() != 0) {
			if (toDt.setDate(stockDetailsForm.getToDate()).before(
					fromDate.setDate(stockDetailsForm.getFromDate()))) {
				error = new ErrorVO("stockcontrol.defaults.err.invaliddates",null);
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}
		}
		return errors;
	}
	public StockDetailsFilterVO handleSearchDetails(StockDetailsForm stockDetailsForm,
														StockDetailsSession session){
	
		StockDetailsFilterVO stockDetailsFilterVOs =new StockDetailsFilterVO();
		stockDetailsFilterVOs.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
		stockDetailsFilterVOs.setStockHolderType(stockDetailsForm.getStockHolderType());
		stockDetailsFilterVOs.setStockHolderCode(upper(stockDetailsForm.getStockHolderCode()));
		stockDetailsFilterVOs.setDocumentType(stockDetailsForm.getDocType());
		stockDetailsFilterVOs.setDocumentSubType(stockDetailsForm.getSubType());
		stockDetailsFilterVOs.setPageSize(100);
		if(stockDetailsForm.getFromDate()!=null && stockDetailsForm.getFromDate().trim().length()!=0){
			LocalDate from = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
		
		stockDetailsFilterVOs.setStartDate(from.setDate(stockDetailsForm.getFromDate()));
		}
		if(stockDetailsForm.getToDate()!=null && stockDetailsForm.getToDate().trim().length()!=0){
			LocalDate to = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
		
		stockDetailsFilterVOs.setEndDate(to.setDate(stockDetailsForm.getToDate()));
		}
		return stockDetailsFilterVOs;
	}
	private String upper(String input) {

		if (input != null) {
			return input.trim().toUpperCase();
		} else {
			return "";
		}
	}
	  private HashMap buildIndexMap(HashMap existingMap, Page page) {
			HashMap finalMap = existingMap;
			String currentPage = String.valueOf(page.getPageNumber());
			String currentAbsoluteIndex = String.valueOf(page.getAbsoluteIndex());
			String indexPage = String.valueOf((page.getPageNumber()+1));
			
			boolean isPageExits = false;
			Set<Map.Entry<String, String>> set = existingMap.entrySet();
			for (Map.Entry<String, String> entry : set) {
				String pageNum = entry.getKey();
				if (pageNum.equals(indexPage)) {
					isPageExits = true;
				}
			}
			
			if (!isPageExits) {
				finalMap.put(indexPage, String.valueOf(page.getAbsoluteIndex()));
			}		
			return finalMap;
		}
}
