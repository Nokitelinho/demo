/*
 * AddStockCommand.java Created on Jan 17, 2006
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

import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.document.vo.DocumentFilterVO;
import com.ibsplc.icargo.business.shared.document.vo.DocumentVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestForOALVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.document.DocumentTypeDelegate;
import com.ibsplc.icargo.presentation.delegate.stockcontrol.defaults.StockControlDefaultsDelegate;
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
public class AddStockCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("STOCK MANAGER");

	private static final String ADD_SUCCESS = "add_stock_success";

	private static final String MODULE_NAME = "stockcontrol.defaults";

	private static final String SCREEN_ID =
		"stockcontrol.defaults.stockmanager";

	private static final String ADD_FAILURE = "add_failure";

	/**
	 * execute method 
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

		log.entering("AddStockCommand", "execute");
		/*
		 * Obtain the Stock Manager session
		 */
		StockManagerSession session = getScreenSession(MODULE_NAME, SCREEN_ID);

		StockControlDefaultsDelegate stockControlDefaultsDelegate =
			new StockControlDefaultsDelegate();
		/*
		 * Obtain the logonAttributes
		 */
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();

		String stockHolderCode = session.getStockHolderCode();
		log.log(Log.FINE, "stockHolderCode(from session) ---> ",
				stockHolderCode);
		StockManagerForm form = (StockManagerForm)invocationContext.screenModel;
		form.setAddRangeFrom("");
		form.setAddRangeTo("");
		form.setAddRemarks("");
		form.setAddDocumentType(form.getDocumentTypeHidden());
		form.setAddDocumentSubType(form.getDocumentSubTypeHidden());

		/*
		 * Validate for client errors. The method
		 * will check for mandatory fields
		 */
		Collection<ErrorVO> errors = null;

		StockFilterVO stockFilterVO = new StockFilterVO();
		stockFilterVO.setCompanyCode(companyCode);

		try {
			AirlineValidationVO airlineVO = new AirlineDelegate()
						.validateAlphaCode(logonAttributes.getCompanyCode(),
										form.getAirlineHidden().toUpperCase());
			stockFilterVO.setAirlineIdentifier(airlineVO
					.getAirlineIdentifier());
		}
		catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = ADD_FAILURE;
			return;
		}
		stockFilterVO.setAirlineCode(form.getAirlineHidden());
		stockFilterVO.setDocumentType(form.getDocumentTypeHidden());
		Collection<DocumentVO> docVOCOL = session.getDocumentVO();
		log.log(Log.FINE, "form.getDocumentSubTypeHidden().trim() ---> ", form.getDocumentSubTypeHidden().trim());
		log.log(Log.FINE, "docVOCOL : ", docVOCOL);
		if(docVOCOL != null) {
			for(DocumentVO docvo:docVOCOL) {
	    		if(docvo.getDocumentSubTypeDes().trim().equals(
	    				form.getDocumentSubTypeHidden().trim())) {
	    			log.log(Log.FINE, "docvo.getDocumentSubTypeDes() ---> ",
							docvo.getDocumentSubTypeDes());
					stockFilterVO.setDocumentSubType(
	    					docvo.getDocumentSubType());
	    		}
	    	}
		}
		//stockFilterVO.setDocumentSubType(form.getDocumentSubTypeHidden());
		stockFilterVO.setStockHolderCode(stockHolderCode);
		Collection<StockRequestForOALVO> stockRequestForOALVOs =
			new ArrayList<StockRequestForOALVO>();
		log.log(Log.FINE, "stockFilterVO(to delegate) ---> ", stockFilterVO);
		try {
			stockRequestForOALVOs = stockControlDefaultsDelegate.
					findStockRequestForOAL(stockFilterVO);
		} catch (BusinessDelegateException businessDelegateException) {
//printStackTrrace()();
			handleDelegateException(businessDelegateException);
		}
		log.log(Log.FINE, "stockRequestForOALVOs(to session) ---> ",
				stockRequestForOALVOs);
		session.setStockRequestForOALVOs(stockRequestForOALVOs);
		
		DocumentFilterVO filetrvo = new DocumentFilterVO();
		filetrvo.setCompanyCode(form.getDocumentType());
		filetrvo.setCompanyCode(logonAttributes.getCompanyCode());
		filetrvo.setDocumentSubType(form.getDocumentSubType());
		DocumentTypeDelegate  documentTypeDelegate
									= new DocumentTypeDelegate();
		Collection<DocumentVO> documentVOs
			= new ArrayList<DocumentVO>();
		try {
			documentVOs = documentTypeDelegate.findDocumentDetails(filetrvo);
		}catch(BusinessDelegateException e) {
			handleDelegateException(e);
		}
		session.setDocVO((DocumentVO)new ArrayList<DocumentVO>(
				documentVOs).get(0));
		form.setDocumentLength(
				String.valueOf(session.getDocVO().getDocumentLength()));
		invocationContext.target = ADD_SUCCESS;
		form.setScreenStatusFlag(ComponentAttributeConstants.
				SCREEN_STATUS_VIEW);
		log.exiting("AddStockCommand", "execute");

    }

}
