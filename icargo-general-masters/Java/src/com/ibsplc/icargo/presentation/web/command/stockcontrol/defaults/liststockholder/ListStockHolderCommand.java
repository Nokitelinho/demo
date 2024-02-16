/*
 * ListStockHolderCommand.java Created on Aug 26, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.liststockholder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderDetailsVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderPriorityVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.stockcontrol.defaults.StockControlDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ListStockHolderSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ListStockHolderForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *
 * @author A-1754
 *
 */
public class ListStockHolderCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");

	/**
	 * The execute method in BaseCommand
	 * 
	 * @author A-1754
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext) 
							throws CommandInvocationException {
		String companyCode;
		log.entering("ListStockHolderCommand","execute");
		companyCode = getApplicationSession().getLogonVO().getCompanyCode();
		ListStockHolderForm form = (ListStockHolderForm)invocationContext.screenModel;
		Collection<ErrorVO> errors = null;

		ListStockHolderSession session = getScreenSession(
				"stockcontrol.defaults",
				"stockcontrol.defaults.liststockholder");
		session.setStockHolderDetails(null);
		StockHolderFilterVO filterVO = null;

		log
				.log(
						Log.FINE,
						"form.getFromListMonitor()**************************************",
						form.getFromListMonitor());
		if (form.getFromMonitorStock() != null
				&& AbstractVO.FLAG_YES.equalsIgnoreCase(form.getFromMonitorStock())) {
			log.log(Log.FINE,
					"Inside If*****************************************");
			filterVO = session.getStockHolderFilterDetails();

			form.setStockHolderType(filterVO.getStockHolderType());
			log.log(Log.FINE, "getStockHolderType*********", filterVO.getStockHolderType());
			form.setStockHolderCode(filterVO.getStockHolderCode());
			form.setDocType(filterVO.getDocumentType());
			form.setDocSubType(filterVO.getDocumentSubType());
		}
		errors = validateForm(form);
		if (errors != null && errors.size() > 0) {
			log.log(Log.FINE,"errors not null*****************************************");
			invocationContext.addAllError(errors);
			invocationContext.target = "screenload_success";
			return;
		}
		if (form.getFromMonitorStock() != null
				&& !AbstractVO.FLAG_YES.equalsIgnoreCase(form.getFromMonitorStock())) {
			filterVO = populateStockHolderFilterVO(form, session, companyCode);
		}

		log
				.log(Log.FINE, "IfilterVO********************************",
						filterVO);
		//newly added for close command 
		session.setStockHolderFilterDetails(filterVO);

		Page<StockHolderDetailsVO> detailsVOs = null;
		//Modified by A-5237 for ICRD-20902 starts
		HashMap<String, String> indexMap = getIndexMap(session.getIndexMap(), invocationContext);
		//Modified by A-5237 for ICRD-20902 ends
		HashMap<String, String> finalMap = null;
		if (session.getIndexMap() != null) {
			indexMap = session.getIndexMap();
		}
		if (indexMap == null) {
			log.log(Log.INFO, "INDEX MAP IS NULL");
			indexMap = new HashMap<String, String>();
			indexMap.put("1", "1");
		}
		int nAbsoluteIndex = 0;
		String displayPage = form.getDisplayPage();
		String strAbsoluteIndex = indexMap.get(displayPage);
		if (strAbsoluteIndex != null) {
			nAbsoluteIndex = Integer.parseInt(strAbsoluteIndex);
		}
		filterVO.setAbsoluteIndex(nAbsoluteIndex);
		
		if(form.isPartnerAirline()){
			filterVO.setAirlineIdentifier(getAirlineIdentifier(form.getAwbPrefix()));
		}
		
		//Added by A-5175 for icrd-20959 starts
		if(ListStockHolderForm.PAGINATION_MODE_FROM_FILTER.equals(form.getNavigationMode())) {
			log.log(Log.FINE, "Navigation Mode>>>>>>>>>", form.getNavigationMode());
			log.log(Log.FINE, "Disply Page>>>>>>>>>", form.getDisplayPage());
			log.log(Log.FINE, "Last Page>>>>>>>>>", form.getLastPageNum());
			filterVO.setTotalRecordCount(-1);
			filterVO.setPageNumber(1);
			
		}else if(ListStockHolderForm.PAGINATION_MODE_FROM_NAVIGATION.equals(form.getNavigationMode())) {
			log.log(Log.FINE, "Navigation Mode>>>>>>>>>", form.getNavigationMode());
			log.log(Log.FINE, "Disply Page>>>>>>>>>", form.getDisplayPage());
			log.log(Log.FINE, "Last Page>>>>>>>>>", form.getLastPageNum());
			filterVO.setTotalRecordCount(session.getTotalRecords());
			filterVO.setPageNumber(Integer.parseInt(form.getDisplayPage()));
		}else {
			filterVO.setTotalRecordCount(-1);
			//filterVO.setPageNumber(1);
		}
		//Added by A-5175 for icrd-20959 ends

		try{
			log
					.log(
							Log.INFO,
							"\n\n\n  before calling StockControlDefaultsDelegate().findStockHolders------------------------------>>");
			detailsVOs = new StockControlDefaultsDelegate()
					.findStockHolders(filterVO);

			if (detailsVOs == null || detailsVOs.size() == 0
					&& !(("cancel").equals(form.getStatusFlag()))) {
			log.log(Log.INFO,"\n\n\n NO RECORDS FOUND");
			ErrorVO error = null;
			Object[] obj = { "norecords" };
			errors = new ArrayList<ErrorVO>();
			error = new ErrorVO("stockcontrol.defaults.norecordsfound", obj);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
			invocationContext.addAllError(errors);
			invocationContext.target = "screenload_success";
			return;
		}else{
			log.log(Log.FINE, "Total Records>>>>>>>>>", detailsVOs.getTotalRecordCount());
			log.log(Log.FINE,"Caching in Session>>>>>>>>>");
			session.setTotalRecords(detailsVOs.getTotalRecordCount());
			//Added by A-5175 for icrd-20959 ends
			Collection<StockHolderPriorityVO> stkHldrType = session
						.getPrioritizedStockHolders();
			if(stkHldrType!=null){
				for(StockHolderDetailsVO dtlsVO : detailsVOs){
					for(StockHolderPriorityVO type : stkHldrType){
						if(type.getStockHolderType()!=null){
							if(type.getStockHolderType().equalsIgnoreCase(
									dtlsVO.getStockHolderType())){
									dtlsVO.setStockHolderType(type
											.getStockHolderDescription());
								}
							}
						}
					}
				}

				for (StockHolderDetailsVO dt : detailsVOs) {

					log.log(Log.FINE,
							"getDocSubType..............................>", dt.getDocSubType());
					log.log(Log.FINE,
							"getDocType()..............................>", dt.getDocType());
					log.log(Log.FINE,
							"getReorderLevel()..............................>",
							dt.getReorderLevel());
					log
							.log(
									Log.FINE,
									"getReorderQuantity()..............................>",
									dt.getReorderQuantity());
					log
							.log(
									Log.FINE,
									"getStockHolderCode()..............................>",
									dt.getStockHolderCode());
					log
							.log(
									Log.FINE,
									"getStockHolderType()..............................>",
									dt.getStockHolderType());

			}
			session.setStockHolderDetails(detailsVOs);
				log
						.log(
								Log.FINE,
								"Stock Holder Details VO..............................>",
								detailsVOs);
				finalMap = indexMap;
				if (session.getStockHolderDetails() != null) {
					finalMap = buildIndexMap(indexMap, session
							.getStockHolderDetails());
				}
				//Added by A-5237 for ICRD-20902 starts
				session.setIndexMap((HashMap<String,String>)super.setIndexMap(finalMap, invocationContext));
				//Added by A-5237 for ICRD-20902 ends

			}
		} catch (BusinessDelegateException businessDelegateException) {
//printStackTrrace()();
			errors = handleDelegateException(businessDelegateException);
			log.log(Log.FINE, "\n\n......EXCEPTIONNNNNNNN.........");
		}

		if (errors != null && errors.size() > 0) {
			log.log(Log.FINE, "\n\n..eoorooooooooo.......", errors);
			invocationContext.addAllError(errors);
			invocationContext.target = "screenload_success";
			return;
		}

		invocationContext.target = "screenload_success";
		log.exiting("ListStockHolderCommand","execute");
	}
	private String getAirlineIdentifier(String awbPrefix) {
		if(awbPrefix!=null && awbPrefix.trim().length()>0){
			String[] tokens=awbPrefix.split("-");
			if(tokens.length>2){
				return tokens[2];
			}
		}
		
		return null;
	}
	/**
	 *
	 * @param form
	 * @param session
	 * @param companyCode
	 * @return
	 */
	private StockHolderFilterVO populateStockHolderFilterVO(
			ListStockHolderForm form, ListStockHolderSession session,
			String companyCode) {
		StockHolderFilterVO filterVO = new StockHolderFilterVO();
		filterVO.setCompanyCode(companyCode);
		filterVO.setDocumentSubType(form.getDocSubType());
		filterVO.setDocumentType(form.getDocType());
		filterVO.setStockHolderCode(upper(form.getStockHolderCode()));
		filterVO.setStockHolderType(form.getStockHolderType());
		log
				.log(
						Log.FINE,
						"\n\n\n ==================filterVO------------------------------->",
						filterVO);
		int pageNumber = 0;
		if (form.getDisplayPage() != null
				&& form.getDisplayPage().trim().length() > 0) {
			pageNumber = Integer.parseInt(form.getDisplayPage());
		}
		filterVO.setPageNumber(pageNumber);
		return filterVO;
	}

	/**
	 *
	 * @param form
	 * @return errors
	 */
	private Collection<ErrorVO> validateForm(ListStockHolderForm form) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		log
				.log(Log.FINE,
						"inside validate form*****************************************");
		/*
		 * if ("".equals(form.getDocType())) { log.log(Log.FINE,"inside
		 * getDocType form*****************************************");
		 * 
		 * Object[] obj = { "DocType" }; error = new
		 * ErrorVO("stockcontrol.defaults.doctypemandatory", obj);
		 * error.setErrorDisplayType(ErrorDisplayType.ERROR); errors.add(error); }
		 * if ("".equals(form.getDocSubType())) {
		 * 
		 * Object[] obj = { "DocSubType" }; error = new
		 * ErrorVO("stockcontrol.defaults.docsubtypemandatory", obj);
		 * error.setErrorDisplayType(ErrorDisplayType.ERROR); errors.add(error); }
		 */
		/*
		 * if ("".equals(form.getStockHolderCode() )) { Object[] obj = {
		 * "StockHlderCode" }; error = new
		 * ErrorVO("stockcontrol.defaults.plsenterfrmdate", obj);
		 * error.setErrorDisplayType(ErrorDisplayType.ERROR); errors.add(error); }
		 */
		if ("".equals(form.getStockHolderType())) {
			Object[] obj = { "ToDate" };
			error = new ErrorVO("stockcontrol.defaults.stkhldtypeismandatory",
					obj);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		
		//Added by A-5174 for BUG icrd-32653 starts here
		if ("".equals(form.getDocType())) {
			
			error = new ErrorVO("stockcontrol.defaults.doctypemandatory");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}

		if ("".equals(form.getDocSubType())) {
			
			error = new ErrorVO("stockcontrol.defaults.docsubtypemandatory");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		//Added by A-5174 for BUG icrd-32653 ends here
		return errors;
	}

	/**
	 *
	 * @param input
	 * @return
	 */
	private String upper(String input){

		if(input!=null){
			return input.trim().toUpperCase();
		}else{
			return "";
		}
	}

	/**
	 * Method to bulid the hashmap to maintain absoluteindex
	 * 
	 * @param existingMap
	 *            HashMap<String, String>
	 * @param page
	 *            Page
	 * @return HashMap<String, String>
	 */
	private HashMap<String, String> buildIndexMap(
			HashMap<String, String> existingMap, Page page) {
		HashMap<String, String> finalMap = existingMap;
		String indexPage = String.valueOf((page.getPageNumber() + 1));
		boolean pageExits = false;
		Set<Map.Entry<String, String>> set = existingMap.entrySet();
		for (Map.Entry<String, String> entry : set) {
			String pageNum = entry.getKey();
			if (pageNum.equals(indexPage)) {
				pageExits = true;
			}
		}
		if (!pageExits) {
			finalMap.put(indexPage, String.valueOf(page.getAbsoluteIndex()));
		}

		return finalMap;
	}

}
