/*
 * RemoveOkCommand.java Created on Jan 17, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.
											stockcontrol.defaults.stockmanager;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.document.vo.DocumentVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAllocationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.stockcontrol.defaults.StockControlDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.StockListSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.StockManagerSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.StockManagerForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1619
 *
 */
public class RemoveOkCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("STOCK MANAGER");
	
	private static final String REMOVE_OK_SUCCESS = "remove_ok_success";

	private static final String MODULE_NAME = "stockcontrol.defaults";

	private static final String SCREEN_ID = 
		"stockcontrol.defaults.stockmanager";
	
	private static final String REMOVE_OK_FAILURE = "remove_ok_failure";

	/**
	 * execute method 
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
		log.entering("RemoveOkCommand", "execute");
		/*
		 * The form for the Stock Manager screen
		 */
		StockManagerForm form = (StockManagerForm)invocationContext.screenModel;
		
		/*
		 * Obtain the Stock Manager session
		 */
		StockManagerSession session = 
			getScreenSession(MODULE_NAME, SCREEN_ID);
		
		StockControlDefaultsDelegate stockControlDefaultsDelegate = 
			new StockControlDefaultsDelegate();
		/*
		 * Obtain the logonAttributes
		 */
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		
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
			form.setErrorStatusRemove("Y");
			invocationContext.addAllError(errors);
			invocationContext.target = REMOVE_OK_FAILURE;
			return;
		}
		
		StockAllocationVO stockAllocationVO = new StockAllocationVO();
		ArrayList<RangeVO> rangeVOs = (ArrayList<RangeVO>)
									session.getNewCollForRemoval();
		log.log(Log.FINE, "Collection(Edited) from session-------->", rangeVOs);
		Collection<RangeVO> rangeVOsfromServer = session.getCollForRemoval();
		log.log(Log.FINE, "Coll(Origl)from session-------->",
				rangeVOsfromServer);
		int pageIndex=Integer.parseInt(form.getCurrentPageNum())-1;		
		RangeVO newRangeVO = rangeVOs.get(pageIndex);
		
		log.log(Log.FINE, "form.getCurrentPageNum() ----> ", form.getCurrentPageNum());
		log.log(Log.FINE, "rangeVO to be updated in the Collection ----> ",
				newRangeVO);
		updateVOinCollection(logonAttributes,form,newRangeVO,stockAllocationVO);
		log.log(Log.FINE, "rangeVO updated in the Collection ----> ",
				newRangeVO);
		rangeVOs.set(Integer.parseInt(form.getCurrentPageNum())-1,newRangeVO);
		
		ArrayList<RangeVO> listArray = new ArrayList<RangeVO>();
		for(RangeVO vo:rangeVOs){
			if(vo.getStartRange()!=null){
				listArray.add(vo);
			}
		}
		RangeVO oneRange = listArray.get(0);
		StockListSession listSession = getScreenSession(MODULE_NAME, 
			"stockcontrol.defaults.cto.stocklist");
		Collection<DocumentVO> docVOCOL = null;
		if(listSession.getDocumentVO() != null) {
			docVOCOL = listSession.getDocumentVO();
		}else {
			docVOCOL = session.getDocumentVO();
		}
		//Collection<DocumentVO> docVOCOL = session.getDocumentVO();
		stockAllocationVO.setCompanyCode(companyCode);
		stockAllocationVO.setStockControlFor(session.getStockHolderCode());
		stockAllocationVO.setAirlineIdentifier(oneRange.getAirlineIdentifier());
		stockAllocationVO.setLastUpdateUser(logonAttributes.getUserId());
		stockAllocationVO.setRanges(listArray);
		stockAllocationVO.setDocumentType(oneRange.getDocumentType());
		stockAllocationVO.setAirportCode(logonAttributes.getAirportCode());
		log.log(Log.INFO, "docVOCOL :", docVOCOL);
		log.log(Log.INFO, "oneRange :", oneRange);
		log.log(Log.INFO, "form.getDocumentSubTypeHidden() :", form.getRemDocumentSubType());
		if(docVOCOL != null){
			for(DocumentVO docvo:docVOCOL) {
	    		if(docvo.getDocumentSubTypeDes().trim().equals(form.getRemDocumentSubType().trim())) {
	    			stockAllocationVO.setDocumentSubType(docvo.getDocumentSubType());   
	    			log.log(Log.FINE, "setDocumentSubType---------->",
							stockAllocationVO.getDocumentSubType());
	    		}        		
			}
		}
		//stockAllocationVO.setDocumentSubType(oneRange.getDocumentSubType());
		stockAllocationVO.setNewStockFlag(false);
		log.log(Log.FINE,
				"(remove command)stockAllocationVO to delegate---------->",
				stockAllocationVO);
		StockAllocationVO allocVO = null;
		try {
			allocVO = stockControlDefaultsDelegate.allocateStock(stockAllocationVO);
		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		log.log(Log.FINE, "After depelete from server\n", allocVO);
		if(allocVO != null && allocVO.isHasMinReorderLevel()){
			session.setReorderLevel("Y");		
		}
		if (errors != null && errors.size() > 0) {
			//form.setErrorStatusRemove("Y");
			invocationContext.addAllError(errors);
			invocationContext.target = REMOVE_OK_FAILURE;
			return;
		}
		
		form.setErrorStatusRemove("N");
		invocationContext.target = REMOVE_OK_SUCCESS;
		form.setScreenStatusFlag(ComponentAttributeConstants.
				SCREEN_STATUS_VIEW);
		log.exiting("RemoveOkCommand", "execute");
    }
    
	/**
	 * Method to check for mandatory fields. 
	 * @param form
	 * @return errors
	 */
	private Collection<ErrorVO> validateForm(StockManagerForm form) {
		log.entering("AddOkCommand", "validateForm");
		
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
		return errors;
	}
	
	
    /**
     * @param logonAttributes
     * @param form
     * @param newRangeVO
     * @param stockAllocationVO
     */
    public void updateVOinCollection(LogonAttributes logonAttributes,StockManagerForm form, RangeVO newRangeVO, 
    		StockAllocationVO stockAllocationVO) {
    	
    	log.log(Log.FINE, "<---Entering in updateVOinCollection  ----> ");
    	log.log(Log.FINE, "RangeVO  ----> ", newRangeVO);
		StockManagerSession session = 
			getScreenSession(MODULE_NAME, SCREEN_ID);
    	StockListSession listSession = getScreenSession(MODULE_NAME, 
			"stockcontrol.defaults.cto.stocklist");
    	Collection<DocumentVO> docVOCOL = null;
		if(listSession.getDocumentVO() != null) {
			docVOCOL = listSession.getDocumentVO();
		}else {
			docVOCOL = session.getDocumentVO();
		}
    	//Collection<DocumentVO> docVOCOL = session.getDocumentVO();
    	newRangeVO.setStartRange(form.getToRangeFrom());
    	newRangeVO.setEndRange(form.getToRangeTo());
    	newRangeVO.setDocumentType(form.getRemDocumentType());
    	newRangeVO.setStockAcceptanceDate(new LocalDate(logonAttributes.getAirportCode(),
    			Location.ARP,true));
    	for(DocumentVO docvo:docVOCOL) {
    		if(docvo.getDocumentSubTypeDes().trim().equals(form.getRemDocumentSubType().trim())) {
    			newRangeVO.setDocumentSubType(docvo.getDocumentSubType());        			
    		}        		
    	}
    	//newRangeVO.setDocumentSubType(form.getRemDocumentSubType());
    	newRangeVO.setOperationFlag("U");
    	//stockAllocationVO.getRanges().add(rangeVO);
    	stockAllocationVO.setRemarks(stockAllocationVO.getRemarks()+form.getRemRemarks());
    	log.log(Log.FINE, "<---Exiting from updateVOinCollection  ----> ");    	
    }	
    
}
