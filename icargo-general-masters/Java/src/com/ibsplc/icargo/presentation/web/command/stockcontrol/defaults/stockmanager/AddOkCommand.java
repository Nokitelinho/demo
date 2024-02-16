/*
 * AddOkCommand.java Created on Jan 17, 2006
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
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestForOALVO;
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
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1619
 *
 */
public class AddOkCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("STOCK MANAGER");
	private static final String ADD_OK_SUCCESS = "add_stock_ok_success";

	private static final String MODULE_NAME = "stockcontrol.defaults";

	private static final String SCREEN_ID =
		"stockcontrol.defaults.stockmanager";

	private static final String ADD_OK_FAILURE = "add_ok_failure";

	/**
	 * execute method
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

		log.entering("AddOkCommand", "execute");
		/*
		 * The form for the Stock Manager screen
		 */
		StockManagerForm form = (StockManagerForm)invocationContext.screenModel;
		/*
		 * Obtain the Stock Manager session
		 */
		StockAllocationVO allocationVOAftersave = null;
		StockManagerSession session = getScreenSession(MODULE_NAME, SCREEN_ID);
		/*
		 * Obtain the logonAttributes
		 */
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		String airportCode = logonAttributes.getAirportCode();

		StockControlDefaultsDelegate stockControlDefaultsDelegate =
			new StockControlDefaultsDelegate();

		if("Y".equals(form.getForAddOkMessage())) {
			form.setForAddOkMessage("N");
			try {

				session.getStockAllocationVOTosave().setOperationFlag(null);
				session.getStockAllocationVOTosave().setConfirmationRequired(false);
				stockControlDefaultsDelegate.allocateStock(session.getStockAllocationVOTosave());
			} catch (BusinessDelegateException businessDelegateException) {
				handleDelegateException(businessDelegateException);
			}
		} else {
			Collection<StockRequestForOALVO> newStockRequestForOALVOs =
				new ArrayList<StockRequestForOALVO>();
			Collection<StockRequestForOALVO> stockRequestForOALVOs =
				session.getStockRequestForOALVOs();
			ArrayList<StockRequestForOALVO> list =
				new ArrayList<StockRequestForOALVO>(stockRequestForOALVOs);

			String[] checkVal = form.getCheckAction();
			//log.log(Log.INFO,"checkVal.length ---------->"+checkVal.length);
			if(checkVal != null){
				for(int i=0;i<checkVal.length;i++) {
					StockRequestForOALVO stockRequestForOALVO = new StockRequestForOALVO();
					stockRequestForOALVO = list.get(Integer.parseInt(checkVal[i]));
					newStockRequestForOALVOs.add(stockRequestForOALVO);
				}
			}
			StockListSession listSession = getScreenSession(MODULE_NAME,
				"stockcontrol.defaults.cto.stocklist");

			Collection<DocumentVO> docVOCOL = null;
			if(listSession.getDocumentVO() != null) {
				docVOCOL = listSession.getDocumentVO();
			}else {
				docVOCOL = session.getDocumentVO();
			}

			LocalDate ldate = new LocalDate(logonAttributes.getAirportCode(),
					Location.ARP,true);
			for(StockRequestForOALVO requestVO : newStockRequestForOALVOs) {
				requestVO.setOperationFlag("U");
				requestVO.setActionTime(ldate);
				requestVO.setDocumentType(form.getAddDocumentType());
				for(DocumentVO docmentvo:docVOCOL) {
		    		if(docmentvo.getDocumentSubTypeDes().trim().equals(
		    				form.getAddDocumentSubType().trim())) {
		    			requestVO.setDocumentSubType(docmentvo.getDocumentSubType());
		    		}
		    	}
				//requestVO.setDocumentSubType(form.getAddDocumentSubType());
				requestVO.setCompanyCode(companyCode);
				requestVO.setAirportCode(airportCode);
				requestVO.setAirlineCode(form.getAirlineHidden());
				requestVO.setAirlineId(session.getAirlineId());
			}
			log.log(Log.FINE, "newStockRequestForOALVOs---------->",
					newStockRequestForOALVOs);
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
				invocationContext.addAllError(errors);
				form.setErrorStatus("Y");
				log.log(Log.FINE, "form.setErrorStatus------>", form.getErrorStatus());
				invocationContext.target = ADD_OK_FAILURE;
				return;
			}
			//validating documentlength
			DocumentVO docvo = session.getDocVO();
			if(docvo!=null) {
				Collection<ErrorVO> errs = new ArrayList<ErrorVO>();
				ErrorVO error = null;
				if(form.getAddRangeFrom().trim().length()
						!= docvo.getDocumentLength()) {
					error = new ErrorVO("stockcontrol.defaults.invalidlength",
							 new Object[] {"From Range"});
					errs.add(error);
				}
				if(form.getAddRangeTo().trim().length()
						!= docvo.getDocumentLength()) {
					error = new ErrorVO("stockcontrol.defaults.invalidlength",
							 new Object[] {"To Range"});
					errs.add(error);
				}
				if(errors!=null && errors.size() > 0 ) {
					/*
					 * if error is present then set the errors to
					 * invocationContext and then return
					 */
					invocationContext.addAllError(errors);
					form.setErrorStatus("Y");
					log.log(Log.FINE, "form.setErrorStatus------>", form.getErrorStatus());
					invocationContext.target = ADD_OK_FAILURE;
					return;
				}

			}

			//StockAllocationVO stockAllocationVO = new StockAllocationVO();
			StockAllocationVO stockAllocationVO = session.getStockAllocationVO();
			Collection<RangeVO> existingRanges = null;
			if(stockAllocationVO != null && stockAllocationVO.getRanges() != null){
				existingRanges = new ArrayList<RangeVO>(stockAllocationVO.getRanges());
			}
			stockAllocationVO.setCompanyCode(companyCode);
			stockAllocationVO.setAirlineIdentifier(session.getAirlineId());
			stockAllocationVO.setStockHolderCode(session.getStockHolderCode());
			stockAllocationVO.setDocumentType(form.getAddDocumentType().toUpperCase());
			stockAllocationVO.setAirportCode(airportCode);
			for(DocumentVO documentvo:docVOCOL) {
	    		if(documentvo.getDocumentSubTypeDes().trim().equals(
	    				form.getAddDocumentSubType().trim())) {
	    			stockAllocationVO.setDocumentSubType(documentvo.getDocumentSubType());
	    		}
	    	}
			//stockAllocationVO.setDocumentSubType(form.getAddDocumentSubType().toUpperCase());
			Collection<RangeVO> rangeVOs = new ArrayList<RangeVO>();
			RangeVO rangeVO = new RangeVO();
			rangeVO.setCompanyCode(companyCode);
			rangeVO.setDocumentType(form.getAddDocumentType().toUpperCase());
			for(DocumentVO document:docVOCOL) {
	    		if(document.getDocumentSubTypeDes().trim().equals(form.getAddDocumentSubType().trim())) {
	    			rangeVO.setDocumentSubType(document.getDocumentSubType());
	    		}
	    	}
			//rangeVO.setDocumentSubType(form.getAddDocumentSubType().toUpperCase());
			rangeVO.setStartRange(form.getAddRangeFrom());
			rangeVO.setEndRange(form.getAddRangeTo());
			rangeVO.setOperationFlag("I");
			rangeVO.setStockAcceptanceDate(new LocalDate(
					logonAttributes.getAirportCode(),Location.ARP,true));
			rangeVOs.add(rangeVO);
			stockAllocationVO.setRanges(rangeVOs);
			stockAllocationVO.setNewStockFlag(true);
			stockAllocationVO.setConfirmationRequired(true);
			stockAllocationVO.setRemarks(form.getAddRemarks());
			log
					.log(
							Log.FINE,
							"newStockRequestForOALVOs(in stockAllocationVO)---------->",
							newStockRequestForOALVOs);
			stockAllocationVO.setStockForOtherAirlines(newStockRequestForOALVOs);
			log.log(Log.FINE, "(add command)stockAllocationVO---------->",
					stockAllocationVO);
			/*			try{
				BeanHelper.copyProperties(newStockAllocationVO,stockAllocationVO);
			}catch(SystemException ex){
				log.log(Log.INFO,"ERROR IN COPY");
			}
		log.log(Log.FINE, "newStockAllocationVO---------->"+newStockAllocationVO);
			//session.setStockAllocationVO(newStockAllocationVO);
*/
			try {

				log
						.log(
								Log.FINE,
								"****************ALLOCATESTOCK DELEGATE CALL---------->",
								stockAllocationVO);
				allocationVOAftersave = stockControlDefaultsDelegate.allocateStock(stockAllocationVO);
			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
				log.log(Log.INFO, "existingRanges : ", existingRanges);
				log.log(Log.INFO,
						"stockallocationvo before setting existing ranges : ",
						stockAllocationVO);
				log
						.log(
								Log.INFO,
								"stockAllocationVO from session before setting  existing ranges:",
								session.getStockAllocationVO());
				stockAllocationVO.setRanges(existingRanges);
				log.log(Log.INFO,
						"stockallocationvo after setting existing ranges : ",
						stockAllocationVO);
				log
						.log(
								Log.INFO,
								"stockAllocationVO from session after setting existing ranges:",
								session.getStockAllocationVO());
				session.setStockAllocationVO(stockAllocationVO);
				log.log(Log.INFO,
						"stockallocationvo after setting into session : ",
						stockAllocationVO);
				log
						.log(
								Log.INFO,
								"stockAllocationVO from session after setting into session:",
								session.getStockAllocationVO());

				/*if(errors != null) {
					for(ErrorVO errorVO : errors) {
						if(errorVO.getErrorCode().equals("stockcontrol.defaults.unacceptabledocumentsexists")){
							Object[] obj = errorVO.getErrorData();
							newVO = (StockAllocationVO)obj[2];
							newVO.setConfirmed(false);
						}

					}
				}*/
			}
			log
					.log(Log.INFO, "allocationVOAftersave : ",
							allocationVOAftersave);
			if(allocationVOAftersave!=null) {
				if(allocationVOAftersave.getRejectedDocuments()!=null &&
						allocationVOAftersave.getRejectedDocuments().size() > 0 &&
						allocationVOAftersave.getNumberOfProcessedDocs()<=0) {

					session.setStockAllocationVOTosave(allocationVOAftersave);
					int rejectedDocs = allocationVOAftersave.getRejectedDocuments().size();
					int requestedDocs = allocationVOAftersave.getNoOfReqDocuments();
					ErrorVO error = new ErrorVO("stockcontrol.defaults.docrejected");
					Object[] data = new Object[3];
					data[0] = String.valueOf(requestedDocs);
					data[1] = String.valueOf(requestedDocs-rejectedDocs);
					data[2] = String.valueOf(rejectedDocs);
					error.setErrorData(data);
					error.setErrorDisplayType(ErrorDisplayType.WARNING);
					errors.add(error);
				}else if(allocationVOAftersave.getRejectedDocuments()!=null &&
						allocationVOAftersave.getRejectedDocuments().size() > 0 &&
						allocationVOAftersave.getNumberOfProcessedDocs()>0) {

					session.setStockAllocationVOTosave(allocationVOAftersave);
					int rejectedDocs = allocationVOAftersave.getRejectedDocuments().size();
					int requestedDocs = allocationVOAftersave.getNoOfReqDocuments();
					ErrorVO error = new ErrorVO("stockcontrol.defaults.docrejectedandprocessed");
					Object[] data = new Object[4];
					data[0] = String.valueOf(requestedDocs);
					data[1] = String.valueOf(requestedDocs-rejectedDocs);
					data[2] = String.valueOf(rejectedDocs);
					data[3] = String.valueOf(allocationVOAftersave.getNumberOfProcessedDocs());
					error.setErrorData(data);
					error.setErrorDisplayType(ErrorDisplayType.WARNING);
					errors.add(error);
				}else if(allocationVOAftersave.getNumberOfProcessedDocs()>0) {

					session.setStockAllocationVOTosave(allocationVOAftersave);
					ErrorVO error = new ErrorVO("stockcontrol.defaults.docprocessed");
					Object[] data = new Object[1];
					data[0] = String.valueOf(allocationVOAftersave.getNumberOfProcessedDocs());
					error.setErrorData(data);
					error.setErrorDisplayType(ErrorDisplayType.WARNING);
					errors.add(error);
				}

			}
			if (errors != null && errors.size() > 0) {
				//form.setErrorStatus("Y");
				//session.setStockAllocationVO(newStockAllocationVO);
				invocationContext.addAllError(errors);
				//invocationContext.addError(	new ErrorVO("stockcontrol.defaults.unacceptabledocumentsexists"));
				invocationContext.target = ADD_OK_FAILURE;
				return;
			}
		}
		form.setErrorStatus("N");
		log.log(Log.FINE, "form.setErrorStatus------>", form.getErrorStatus());
		invocationContext.target = ADD_OK_SUCCESS;
		form.setScreenStatusFlag(ComponentAttributeConstants.
				SCREEN_STATUS_VIEW);

		log.exiting("AddOkCommand", "execute");

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
		if(form.getAddRangeFrom() == null || (form.getAddRangeFrom().length() == 0)) {
			error = new ErrorVO("stockcontrol.defaults.mandatory",
					 new Object[] {"Range From"});
			errors.add(error);
		}
		/*
		 * Validate whether Range To entered is blank
		 */
		if(form.getAddRangeTo() == null || (form.getAddRangeTo().length() == 0)) {
			error = new ErrorVO("stockcontrol.defaults.mandatory",
					 new Object[] {"Range To"});
			errors.add(error);
		}
		/*
		 * Validate whether Document Type entered is blank
		 */
		if(form.getAddDocumentType() == null || (form.getAddDocumentType().length() == 0)) {
			error = new ErrorVO("stockcontrol.defaults.mandatory",
					 new Object[] {"Document Type"});
			errors.add(error);
		}
		/*
		 * Validate whether Document SubType entered is blank
		 */
		if(form.getAddDocumentSubType() == null || (form.getAddDocumentSubType().length() == 0) ) {
			error = new ErrorVO("stockcontrol.defaults.mandatory",
					 new Object[] {"Document SubType"});
			errors.add(error);
		}
		return errors;
	}


}
