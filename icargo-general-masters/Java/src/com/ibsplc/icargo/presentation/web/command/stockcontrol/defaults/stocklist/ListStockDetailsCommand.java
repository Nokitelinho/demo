/*
 * ListStockDetailsCommand.java Created on Jan 17, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.stocklist;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.document.vo.DocumentVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.stockcontrol.defaults.StockControlDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.StockListSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.StockListForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1619
 *
 */
public class ListStockDetailsCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");

	private ErrorVO error;

	private static final String ERROR_FWD = "screenlist_failure";

	private static final String SCREEN_FWD = "screenlist_success";

	private static final String SYSTEMPARAMETERVALUE = "stock.defaults.defaultstockholdercodeforcto";

	//AirlineDelegate airlineDelegate = new AirlineDelegate();

	private static final String ALL = "ALL";

	//StockControlDefaultsDelegate delegate = new StockControlDefaultsDelegate();

	/**
	 * execute method 
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		StockListForm form = (StockListForm) invocationContext.screenModel;
		StockListSession session = getScreenSession("stockcontrol.defaults",
				"stockcontrol.defaults.cto.stocklist");

		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		Page<StockVO> pg = null;    
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		Collection<ErrorVO> errorVOs = new ArrayList<ErrorVO>();
		StockFilterVO filterVO = new StockFilterVO();
		StockFilterVO stockFilterVO = new StockFilterVO();
		Collection<DocumentVO> docvoCol = session.getDocumentVO();

		if (AbstractVO.FLAG_YES.equals(form.getFromStockList())) {
			stockFilterVO = session.getFilterVO();
			form.setAirline(stockFilterVO.getAirlineCode());
			if (stockFilterVO.getDocumentType() != null) {
				form.setDocumentType(stockFilterVO.getDocumentType());
			} else {
				form.setDocumentType(ALL);
			}
			if (docvoCol != null) {
				for (DocumentVO vo : docvoCol) {
					if (stockFilterVO.getDocumentSubType() != null) {
						if (stockFilterVO.getDocumentSubType().trim().equals(
								vo.getDocumentSubType())) {
							form.setDocSubType(vo.getDocumentSubType());
						}
					} else {
						form.setDocSubType(ALL);
					}
				}
			}
		} else {
			errorVOs = getFilterVO(filterVO, form, logonAttributes, docvoCol);
			session.setFilterVO(filterVO);
			stockFilterVO = filterVO;
		}
		session.setStockVOs(pg);
		//modified By A-5111 for Bug ID :28357
		if (AbstractVO.FLAG_NO.equals(form.getIsButtonClicked())) {
			form.setDisplayPage("1");
			form.setLastPageNum("0");
		}
		//form.setIsButtonClicked("N");
		//modified By A-5111 for Bug ID :28357 
		HashMap indexMap = null; 
		HashMap finalMap = null;
		if (session.getIndexMap() != null) {
			indexMap = session.getIndexMap();
		}
		if (indexMap == null) {
			log.log(Log.FINE, "INDEX MAP IS NULL");
			indexMap = new HashMap();
			indexMap.put("1", "1");
		}
		int nAbsoluteIndex = 0;
		String toDisplayPage = form.getDisplayPage();
		int displayPage = Integer.parseInt(toDisplayPage);
		filterVO.setPageNumber(displayPage);
		//Added by A-5221 as part from the ICRD-23107 starts
		if(!"YES".equals(form.getCountTotalFlag()) && session.getTotalRecords().intValue() != 0){
			stockFilterVO.setTotalRecords(session.getTotalRecords().intValue());
	    }else{
	    	stockFilterVO.setTotalRecords(-1);
	    }
		//Added by A-5221 as part from the ICRD-23107 end
		String strAbsoluteIndex = (String) indexMap.get(toDisplayPage);
		if (strAbsoluteIndex != null) {
			nAbsoluteIndex = Integer.parseInt(strAbsoluteIndex);
		}
		log.log(Log.INFO, "errorVOs", errorVOs);
		if (errorVOs != null && errorVOs.size() > 0) {
			log.log(Log.INFO, "errorVOs1111", errorVOs);
			invocationContext.addAllError(errorVOs);
			invocationContext.target = ERROR_FWD;

		} else {
			String stockHolder = getStockHolder( 
					 logonAttributes);
			filterVO.setStockHolderCode(stockHolder);
			StockControlDefaultsDelegate delegate = new StockControlDefaultsDelegate();
			try {

				log.log(Log.INFO, "FilterVO", filterVO);
				log.log(Log.INFO, "FilterVO", filterVO);
				pg = delegate.findStockList(stockFilterVO);
			} catch (BusinessDelegateException e) {
				handleDelegateException(e);

			}

			if (pg == null || pg.size() == 0) {

				 error = new ErrorVO(
						"stockcontrol.defaults.nocolstockexists");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
				invocationContext.addAllError(errors);
				invocationContext.target = ERROR_FWD;
			} else { 
				for (StockVO stockvo : pg) {
					if (stockvo != null) {
						AirlineDelegate airlineDelegate = new AirlineDelegate();
						try {
							AirlineValidationVO airlineVO = airlineDelegate
									.findAirline(logonAttributes
											.getCompanyCode(), stockvo
											.getAirlineId());
							stockvo.setAirline(airlineVO.getAlphaCode()
									.toUpperCase());
						} catch (BusinessDelegateException e) {
//printStackTrrace()();
							handleDelegateException(e);

						}
					}
					if (docvoCol != null) {
						for (DocumentVO vo : docvoCol) {
							if (stockvo.getDocumentSubType().trim().equals(
									vo.getDocumentSubType())) {
								stockvo.setDocumentSubType(vo
										.getDocumentSubType());
							}
						}
					}
				}
				session.setTotalRecords(pg.getTotalRecordCount());
				session.setStockVOs(pg);
				finalMap = indexMap;
				if (session.getStockVOs() != null) {
					finalMap = buildIndexMap(indexMap, session.getStockVOs());
				}
				session.setIndexMap(finalMap);
				session.setDocumentVO(docvoCol);
				invocationContext.target = SCREEN_FWD; 
		//	}
		}
		}

	} 

	/**
	 * @param vo
	 * @param form
	 * @param logonAttributes
	 * @param docVOCOL
	 * @return
	 */ 
	private Collection<ErrorVO> getFilterVO(StockFilterVO vo,
			StockListForm form, LogonAttributes logonAttributes,
			Collection<DocumentVO> docVOCOL) {

		Collection<ErrorVO> errorvos = new ArrayList<ErrorVO>();
		if (form.getDocumentType() != null
				&& form.getDocumentType().trim().length() > 0
				&& !form.getDocumentType().equals(ALL)) {
			vo.setDocumentType(form.getDocumentType());
		}
		
		if (form.getDocSubType() != null
				&& form.getDocSubType().trim().length() > 0
				&& !form.getDocSubType().equals(ALL)) {
			for (DocumentVO docvo : docVOCOL) {
				if (docvo.getDocumentSubType().trim().equals(
						form.getDocSubType().trim())) {
					
					vo.setDocumentSubType(docvo.getDocumentSubType());
				}
			}
		}
		vo.setAirportCode(logonAttributes.getAirportCode());
		vo.setCompanyCode(logonAttributes.getCompanyCode());
		if (form.getAirline() != null && form.getAirline().trim().length() > 0) {
			AirlineDelegate airlineDelegate = new AirlineDelegate();
			try {
				AirlineValidationVO airlineVO = airlineDelegate
						.validateAlphaCode(logonAttributes.getCompanyCode(),
								form.getAirline().toUpperCase());
				vo.setAirlineIdentifier(airlineVO
						.getAirlineIdentifier());
				vo.setAirlineCode(form.getAirline());
			} catch (BusinessDelegateException e) {
//printStackTrrace()();
				errorvos = handleDelegateException(e);
			}
		}
		//Added by A-5174 for BUG icrd-32653 starts here
		if("".equals(form.getDocumentType())){
			error=new ErrorVO("stockcontrol.defaults.doctypemandatory");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errorvos.add(error);
		}
		if("".equals(form.getDocSubType())){
			error=new ErrorVO("stockcontrol.defaults.docsubtypemandatory");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errorvos.add(error);
		}
		log.log(Log.INFO, "errorVOs", errorvos);
		return errorvos;
	}

	/**
	 * @param existingMap
	 * @param page
	 * @return HashMap
	 */
	private HashMap buildIndexMap(HashMap existingMap, Page page) {
		HashMap finalMap = existingMap;
		String currentPage = String.valueOf(page.getPageNumber());
		String currentAbsoluteIndex = String.valueOf(page.getAbsoluteIndex());
		String indexPage = String.valueOf((page.getPageNumber() + 1));

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

	/**
	 * @return
	 */
	private String getStockHolder(LogonAttributes logonAttributes) {

		HashMap<String, String> sysparVal = new HashMap<String, String>();
		AreaDelegate areaDelegate = new AreaDelegate();
		Collection<String> sysPar = new ArrayList<String>();
		sysPar.add(SYSTEMPARAMETERVALUE);
		try {
			sysparVal = (HashMap<String, String>) areaDelegate.
			findAirportParametersByCode(
					logonAttributes.getCompanyCode(),
					logonAttributes.getAirportCode(), sysPar);
					
		} catch (BusinessDelegateException e) {
			handleDelegateException(e);
		}
		return sysparVal.get(SYSTEMPARAMETERVALUE);

	}

}
