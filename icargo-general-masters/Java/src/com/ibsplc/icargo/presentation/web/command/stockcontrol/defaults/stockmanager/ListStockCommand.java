/*
 * ListStockCommand.java Created on Jan 17, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.stockmanager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.document.vo.DocumentVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAllocationVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.stockcontrol.defaults.StockControlDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.StockListSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.StockManagerSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.StockManagerForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1619
 *
 */
public class ListStockCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("STOCK MANAGER");

	private static final String LIST_SUCCESS =
		"list_stockmanager_success";

	private static final String MODULE_NAME = "stockcontrol.defaults";

	private static final String SCREEN_ID =
		"stockcontrol.defaults.stockmanager";

	private static final String LIST_FAILURE = "list_failure";

	/**
	 * execute method 
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

		log.entering("ListStockCommand", "execute");
		/*
		 * The form for the start cashdraw screen
		 */
		StockManagerForm form = (StockManagerForm)invocationContext.screenModel;
		/*
		 * Obtain the start cashdraw session
		 */
		StockManagerSession session =
			getScreenSession(MODULE_NAME, SCREEN_ID);
		StockListSession listSession = getScreenSession(MODULE_NAME, 
				"stockcontrol.defaults.cto.stocklist");

		StockControlDefaultsDelegate stockControlDefaultsDelegate =
			new StockControlDefaultsDelegate();
		
		Collection<DocumentVO> docVOCOL = null;
		log.log(Log.INFO, "@@@@@@@@@@@ -> listSession.getDocumentVO :",
				listSession.getDocumentVO());
		if(listSession.getDocumentVO() != null) {
			docVOCOL = listSession.getDocumentVO();
		}else {
			docVOCOL = session.getDocumentVO();
		}
		/*
		 * Obtain the logonAttributes
		 */
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		
		String companyCode = logonAttributes.getCompanyCode();

		String stockHolderCode = session.getStockHolderCode();
		log.log(Log.FINE, "stockHolderCode(from session) ---> ",
				stockHolderCode);
		/*
		 * Validate for client errors. The method
		 * will check for mandatory fields
		 */
		Collection<ErrorVO> errors = null;
		StockFilterVO newVO = listSession.getFilterFromList();
		log.log(Log.FINE, "newVO ---> ", newVO);
		log.log(Log.FINE, "docVOCOL ---> ", docVOCOL);
		if(listSession.getDocumentVO() != null && newVO != null){
			form.setAirline(newVO.getAirlineCode());
			form.setDocumentType(newVO.getDocumentType());
			if(docVOCOL != null){
				for(DocumentVO docvo:docVOCOL) {
					if(docvo.getDocumentSubType().trim().equals(newVO.getDocumentSubType().trim())) {
						form.setDocumentSubType(docvo.getDocumentSubTypeDes());
					}
	    		}        		
	    	}
		}
		log.log(Log.FINE, "form.setDocumentSubType ---> ", form.getDocumentSubType());
		//form.setDocumentSubType(newVO.getDocumentSubType());
		errors = validateForm(form);
		if(errors!=null && errors.size() > 0 ) {
			/*
			 * if error is present then set the errors to
			 * invocationContext and then return
			 */
			invocationContext.addAllError(errors);
			invocationContext.target = LIST_FAILURE;
			return;
		}
		
		if(logonAttributes.getOwnAirlineCode().equalsIgnoreCase(
				form.getAirline())) {
			invocationContext.addError(
				new ErrorVO("stockcontrol.defaults.cannotmanageforownairline"));
			invocationContext.target = LIST_FAILURE;
			return;			
		}
		StockAllocationVO stockAllocationVO = new StockAllocationVO();
		StockFilterVO stockFilterVO = new StockFilterVO();
		int id = 0;
		try {
			AirlineValidationVO airlineVO = new AirlineDelegate()
						.validateAlphaCode(logonAttributes.getCompanyCode(),
											form.getAirline().toUpperCase());
			stockFilterVO.setAirlineIdentifier(airlineVO
					.getAirlineIdentifier());
			id = airlineVO.getAirlineIdentifier();
			session.setAirlineId(id);
		}
		catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		if (errors != null && errors.size() > 0) {
			session.setStockAllocationVO(null);
			invocationContext.addAllError(errors);
			invocationContext.target = LIST_FAILURE;
			return;
		}
		
		log.log(Log.FINE, "form.getForMessage() ---> ", form.getForMessage());
		if("Y".equals(form.getForMessage())) {
			log.log(Log.FINE, "form.getForMessaginside loope() ---> ", form.getForMessage());
			form.setForMessage("N");
			StockAllocationVO newStockAllocationVO = new StockAllocationVO();
			newStockAllocationVO.setCompanyCode(companyCode);
			newStockAllocationVO.setAirlineIdentifier(id);
			newStockAllocationVO.setAirlineCode(form.getAirline().toUpperCase());
			newStockAllocationVO.setDocumentType(form.getDocumentType().toUpperCase());
			for(DocumentVO docvo:docVOCOL) {
	    		if(docvo.getDocumentSubTypeDes().trim().equals(form.getDocumentSubType().trim())) {
	    			log.log(Log.FINE,
							"form.getForMessage() -****************--> ",
							docVOCOL);
					newStockAllocationVO.setDocumentSubType(docvo.getDocumentSubType());        			
	    		}        		
	    	}
			//newStockAllocationVO.setDocumentSubType(form.getDocumentSubType().toUpperCase());
			Collection<RangeVO> newRangeVO = new ArrayList<RangeVO>();
			newStockAllocationVO.setRanges(newRangeVO);
			newStockAllocationVO.setOperationFlag("I");
			session.setStockAllocationVO(newStockAllocationVO);
			form.setSummaryCount("0");
		}else {
			log.log(Log.FINE, "form.inside else..............() -****************--> ");
			String code = session.getHomeairlinecode();
			if(form.getAirline().equalsIgnoreCase(code)) {
				form.setCheckForHomeAirline("Y");
			}

			stockFilterVO.setCompanyCode(companyCode);
			stockFilterVO.setAirlineCode(form.getAirline().toUpperCase());
			stockFilterVO.setDocumentType(form.getDocumentType().toUpperCase());
			log.log(Log.FINE, "docVOCOL ---> ", docVOCOL);
			for(DocumentVO docvo:docVOCOL) {
	    		if(docvo.getDocumentSubTypeDes().trim().equals(form.getDocumentSubType().trim())) {
	    			stockFilterVO.setDocumentSubType(docvo.getDocumentSubType());
	    		}        		
	    	}
			//stockFilterVO.setDocumentSubType(form.getDocumentSubType().toUpperCase());
			stockFilterVO.setStockHolderCode(stockHolderCode);

			log.log(Log.FINE, "stockFilterVO(sent to delegate) ---> ",
					stockFilterVO);
			session.setStockAllocationVO(null);
			if(listSession.getFilterFromList() != null) {				
				try {
					BeanHelper.copyProperties(stockFilterVO,listSession.getFilterFromList());
				}catch(SystemException ex){
					log.log(Log.INFO,"ERROR IN COPY");
				}
			}
			try {
				log.log(Log.FINE, "form.stockFilterVO() -****************--> ",
						stockFilterVO);
				stockAllocationVO = stockControlDefaultsDelegate.
									findStockForAirline(stockFilterVO);
			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
			if (errors != null && errors.size() > 0) {
				invocationContext.addAllError(errors);
				invocationContext.target = LIST_FAILURE;
				return;
			}

			if(stockAllocationVO == null) {
				invocationContext.addError(
						new ErrorVO("stockcontrol.defaults.forlistingnew"));
				invocationContext.target = LIST_FAILURE;
				return;
			}
			
			if(stockAllocationVO != null) {
				if(stockAllocationVO.getRanges() != null) {
					ArrayList<RangeVO> rangeVOs = 
						new ArrayList<RangeVO>(stockAllocationVO.getRanges());
					Collections.sort(rangeVOs,new RangeVOComparator());
					stockAllocationVO.setRanges(rangeVOs);
				}
			}
			
			StockAllocationVO newStockAllocationVO = new StockAllocationVO();  
			try{
				BeanHelper.copyProperties(newStockAllocationVO,stockAllocationVO);
			}catch(SystemException ex){
				log.log(Log.INFO,"ERROR IN COPY");
			}
			session.setStockAllocationVO(newStockAllocationVO);

			Collection<RangeVO> newColl = new ArrayList<RangeVO>();
			long count = 0;
			newColl = stockAllocationVO.getRanges();
			if(newColl != null){
				for(RangeVO iter : newColl) {
					count += iter.getNumberOfDocuments();
				}
			}
			form.setSummaryCount(count+"");
			log.log(Log.FINE, "stockAllocationVO(set in session) ---> ",
					newStockAllocationVO);
		}
		String docsubtype = "";
		for(DocumentVO docvo:docVOCOL) {
    		if(docvo.getDocumentSubTypeDes().trim().equals(form.getDocumentSubType().trim())) {
    			docsubtype = docvo.getDocumentSubTypeDes().trim();
    			stockFilterVO.setDocumentSubType(docvo.getDocumentSubType());
    		}
    	}
		if(("Y").equals(session.getReorderLevel())) {
			session.removeReorderLevel();
			Collection<ErrorVO> reorderErrors = new ArrayList<ErrorVO>();
			ErrorVO error = new ErrorVO("stockcontrol.defaults.reorderlevelreduced");
			error.setErrorDisplayType(ErrorDisplayType.INFO);
			reorderErrors.add(error);						
			invocationContext.addAllError(reorderErrors);	
		}
		log.log(Log.FINE, "form.getDocumentType() ---> ", form.getDocumentType());
		log.log(Log.FINE, "form.getDocumentSubType() ---> ", form.getDocumentSubType());
		form.setDocumentType(form.getDocumentType());
		log.log(Log.FINE, "form.getDocumentType(after setting) ---> ", form.getDocumentType());
		form.setDocumentSubType(docsubtype);
		log.log(Log.FINE, "form.getDocumentSubType(after setting) ---> ", form.getDocumentSubType());
		invocationContext.target = LIST_SUCCESS;
		form.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		log.exiting("ListStockCommand", "execute");

    }

	/**
	 * Method to check for mandatory fields.
	 * @param form
	 * @return errors
	 */
	private Collection<ErrorVO> validateForm(StockManagerForm form) {
		log.entering("ListStockCommand", "validateForm");

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		/*
		 * Validate whether Airline Code entered is blank
		 */
		if(form.getAirline() == null || (form.getAirline().length() == 0)) {
			error = new ErrorVO("stockcontrol.defaults.mandatory",
					 new Object[] {"Airline Code"});
			errors.add(error);
		}
		/*
		 * Validate whether Document Type entered is blank
		 */
		if(form.getDocumentType() == null ||
				(form.getDocumentType().length() == 0)) {
			error = new ErrorVO("stockcontrol.defaults.mandatory",
					 new Object[] {"Document Type"});
			errors.add(error);
		}
		log.log(Log.FINE, "form.setDocumentSubType(validateForm) ---> ", form.getDocumentSubType());
		if(form.getDocumentSubType() == null ||
				(form.getDocumentSubType().length() == 0) ) {
			error = new ErrorVO("stockcontrol.defaults.mandatory",
					 new Object[] {"Document SubType"});
			errors.add(error);
		}
		return errors;
	}
	/*
	 * RangeVOComparator.java Created on Jan 17, 2006
	 *
	 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
	 *
	 * This software is the proprietary information of IBS Software Services (P) Ltd.
	 * Use is subject to license terms.
	 */
	/**
	 *	Comparator class to compare the secondRangeVO
	 *  in stockAllocationVO and sort the  collection 
	 *  in the order of startRange
	 */
	private static class RangeVOComparator implements Comparator<RangeVO> {
	
		/**
		* Method to compare the routingSequenceNumber
		* @param firstRangeVO
		* @param secondRangeVO
		* @return int
		*/	
		public int compare(RangeVO firstRangeVO, RangeVO secondRangeVO) {
			int index=0;
			if(firstRangeVO.getStartRange()!=null&&secondRangeVO.getStartRange()!=null){
				index = Long.parseLong(firstRangeVO.getStartRange()) >
				Long.parseLong(secondRangeVO.getStartRange()) ? 1 : 0 ;
			}
			return index;
		}
	}


}
