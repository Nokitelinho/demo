/*
 * GetNextStockDetailsCommand.java Created on Jan 17, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.stockmanager;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.document.vo.DocumentVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAllocationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.StockManagerSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.StockManagerForm;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1619
 *
 */
public class GetNextStockDetailsCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("STOCK MANAGER");
	
	private static final String GET_NEXT_SUCCESS = "get_next_success";

	private static final String MODULE_NAME = "stockcontrol.defaults";

	private static final String SCREEN_ID = 
		"stockcontrol.defaults.stockmanager";
	
	private static final String GET_NEXT_FAILURE = "get_next_failure";
	/**
	 * execute method 
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
		log.entering("GetNextStockDetailsCommand", "execute");
		/*
		 * The form for the Stock Manager screen
		 */
		StockManagerForm form = (StockManagerForm)invocationContext.screenModel;
		
		/*
		 * Obtain the Stock Manager session
		 */
		StockManagerSession session = 
			getScreenSession(MODULE_NAME, SCREEN_ID);
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
	    LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		/*
		 * Validate for client errors. The method 
		 * will check for mandatory fields
		 */
		Collection<ErrorVO> errors = null;
		errors = validateForm(form);
		if(errors!=null && errors.size() > 0 ) {
			/*
			 * if error is present then set the errors to
			 * invocationContext and then return
			 */
			form.setDisplayPage(form.getCurrentPageNum());
			invocationContext.addAllError(errors);
			invocationContext.target = GET_NEXT_FAILURE;
			return;
		}
		StockAllocationVO stockAllocationVO = session.getStockAllocationVO();
		log.log(Log.FINE, "stockAllocationVO from session ----> ",
				stockAllocationVO);
		//ArrayList<StockVO> listStockVO = new ArrayList<StockVO>(
		Collection<RangeVO> collRangeVO = session.getCollForRemoval();
		log.log(Log.FINE, "rangeVOs from the session ----> ", collRangeVO);
		ArrayList<RangeVO> listRangeVO = new ArrayList<RangeVO>(collRangeVO);
		
		Collection<RangeVO> newCollRangeVO = session.getNewCollForRemoval();
		log.log(Log.FINE, "NEW rangeVOs from the session ----> ",
				newCollRangeVO);
		ArrayList<RangeVO> newListRangeVO = new ArrayList<RangeVO>(newCollRangeVO);
		
		int pageIndex=Integer.parseInt(form.getCurrentPageNum())-1;
		RangeVO rangeVO = listRangeVO.get(pageIndex);
		RangeVO newRangeVO = newListRangeVO.get(pageIndex);
		
		log.log(Log.FINE, "form.getCurrentPageNum() ----> ", form.getCurrentPageNum());
		log.log(Log.FINE, "rangeVO updated in the Collection ----> ", rangeVO);
		updateVOinCollection(logonAttributes,form,newRangeVO,stockAllocationVO);
		newListRangeVO.set(Integer.parseInt(form.getCurrentPageNum())-1,newRangeVO);
		Collection<RangeVO> newcollRangeVO = new ArrayList<RangeVO>(newListRangeVO);
		log.log(Log.FINE, "updated Collection from the ArrayList ----> ",
				newcollRangeVO);
		RangeVO nextrangeVO = listRangeVO.get(Integer.parseInt(
													form.getDisplayPage())-1);
		RangeVO copyNextrangeVO = newListRangeVO.get(Integer.parseInt(
				form.getDisplayPage())-1);
		log
				.log(Log.FINE, "form.getDisplayPage() ----> ", form.getDisplayPage());
		log.log(Log.FINE, "newrangeVO populated in the form ----> ",
				nextrangeVO);
		populateForm(form,nextrangeVO,copyNextrangeVO);
		
       	//setting the page nos.
		form.setCurrentPageNum(form.getDisplayPage());
		form.setLastPageNum(String.valueOf(newcollRangeVO.size()));
		form.setTotalRecords(String.valueOf(newcollRangeVO.size()));
		
		//setting the stockAllocationVO to session
		session.setStockAllocationVO(stockAllocationVO);
		session.setNewCollForRemoval(newcollRangeVO);
		
		invocationContext.target = GET_NEXT_SUCCESS;
		form.setScreenStatusFlag(ComponentAttributeConstants.
				SCREEN_STATUS_VIEW);
		log.exiting("GetNextStockDetailsCommand", "execute");
    }
    
	/**
     * Populate the form from the VO 
     * @param form
     * @param rangeVO
     * @param newRangeVO
     */
    public void populateForm(StockManagerForm form, RangeVO rangeVO,RangeVO newRangeVO) {
    	
    	log.log(Log.FINE, "<---Entering in populateForm  ----> ");
    	log.log(Log.FINE, "rangeVO  ----> ", rangeVO);
		StockManagerSession session = 
			getScreenSession(MODULE_NAME, SCREEN_ID);
    	form.setCurRangeFrom(rangeVO.getStartRange());
    	form.setCurRangeTo(rangeVO.getEndRange());
    	form.setRemDocumentType(rangeVO.getDocumentType());
    	Collection<DocumentVO> docVOCOL = session.getDocumentVO();
    	log.log(Log.FINE, "rangeVO.getDocumentSubType()----->", rangeVO.getDocumentSubType());
		if(docVOCOL != null){
			for(DocumentVO docvo:docVOCOL) {
	    		if(docvo.getDocumentSubTypeDes().trim().equals(rangeVO.getDocumentSubType().trim())) {
	    			form.setRemDocumentSubType(docvo.getDocumentSubType());   
	    			log
							.log(
									Log.FINE,
									"(populateForm)form.getRemDocumentSubType()---------->",
									form.getRemDocumentSubType());
	    		}        		
			}
		}
    	//form.setRemDocumentSubType(rangeVO.getDocumentSubType());
    	if(newRangeVO.getStartRange()!=null && newRangeVO.getOperationFlag() != null){
    		form.setToRangeFrom(newRangeVO.getStartRange());
    	}
    	else {
    		form.setToRangeFrom("");
    	}
    	if(newRangeVO.getEndRange()!=null && newRangeVO.getOperationFlag() != null){
    		form.setToRangeTo(newRangeVO.getEndRange());
    	}
    	else {
    		form.setToRangeTo("");
    	}
    	//form.setRemRemarks(stockAllocationVO.getRemarks());
    	log.log(Log.FINE, "<---Exiting from populateForm  ----> ");
    	
    }
    	
	/**
     * Update the VO from the form to Collection  
     * @param stockAllocationVO
     * @param logonAttributes  
     * @param newRangeVO  
     * @param form  
     */
    public void updateVOinCollection(LogonAttributes logonAttributes,StockManagerForm form, RangeVO newRangeVO, 
    		StockAllocationVO stockAllocationVO) {
    	
    	log.log(Log.FINE, "<---Entering in updateVOinCollection  ----> ");
    	log.log(Log.FINE, "RangeVO  ----> ", newRangeVO);
		StockManagerSession session = 
			getScreenSession(MODULE_NAME, SCREEN_ID);
    	newRangeVO.setStartRange(form.getToRangeFrom());
    	newRangeVO.setEndRange(form.getToRangeTo());
    	newRangeVO.setDocumentType(form.getRemDocumentType());
    	newRangeVO.setStockAcceptanceDate(new LocalDate(logonAttributes.getAirportCode(),
    			Location.ARP,true));
    	Collection<DocumentVO> docVOCOL = session.getDocumentVO();
    	log.log(Log.FINE, "form.getRemDocumentSubType()----->", form.getRemDocumentSubType());
		if(docVOCOL != null){
			for(DocumentVO docvo:docVOCOL) {
	    		if(docvo.getDocumentSubTypeDes().trim().equals(form.getRemDocumentSubType().trim())) {
	    			newRangeVO.setDocumentSubType(docvo.getDocumentSubType());   
	    			log
							.log(
									Log.FINE,
									"(updateVOinCollection)newRangeVO.setDocumentSubType---------->",
									newRangeVO.getDocumentSubType());
	    		}        		
			}
		}
    	//newRangeVO.setDocumentSubType(form.getRemDocumentSubType());
    	newRangeVO.setOperationFlag("U");
    	//stockAllocationVO.getRanges().add(rangeVO);
    	stockAllocationVO.setRemarks(stockAllocationVO.getRemarks()+form.getRemRemarks());
    	log.log(Log.FINE, "<---Exiting from updateVOinCollection  ----> ");    	
    }
    
	/**
	 * Method to check for client errors and mandatory fields. 
	 * @param form
	 * @return errors
	 */
	private Collection<ErrorVO> validateForm(StockManagerForm form) {
		
		log.entering("GetNextStockDetailsCommand", "validateForm");
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		
		/*
		 * Validate whether Range From entered is blank
		 */
		if(form.getToRangeFrom() == null || (form.getToRangeFrom().length() == 0)) {
			error = new ErrorVO("stockcontrol.defaults.mandatory",
					 new Object[] {"Range From"});
			errors.add(error);
		}
		/*
		 * Validate whether Range To entered is blank
		 */
		if(form.getToRangeTo() == null || (form.getToRangeTo().length() == 0)) {
			error = new ErrorVO("stockcontrol.defaults.mandatory",
					 new Object[] {"Range To"});
			errors.add(error);
		}
		
		if(form.getToRangeFrom() != null && form.getToRangeFrom().length() > 0 
				&& form.getToRangeTo() != null 
				&& form.getToRangeTo().length() > 0 ) {
			
			if( (Integer.parseInt(form.getCurRangeFrom()) > 
				Integer.parseInt(form.getToRangeFrom())) || 
	    			(Integer.parseInt(form.getToRangeTo()) >
	    			Integer.parseInt(form.getCurRangeTo())) ) {
	    		error = new ErrorVO("stockcontrol.defaults.rangenotfound");
	    		error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}
		}
		
		return errors;
	}
	
}//EOC
		
