/*
 * ScreenLoadCommand.java Created on Mar 23, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.invoicesettlement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPASettlementVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.InvoiceSettlementSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.InvoiceSettlementForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2408
 *
 */
public class ScreenLoadCommand extends BaseCommand {
	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";

    private static final String SCREEN_ID ="mailtracking.mra.gpabilling.invoicesettlement";
    
    private static final String SCREENLOAD_SUCCESS ="screenload_success";
    
    private static final String CLASS_NAME = "ScreenLoadCommand";
    
    private static final String KEY_SETTLEMENT_STATUS_ONETIME = "mailtracking.mra.invoicestatus";
    
    private static final String SCREENSTATUS_SCREENLOAD="screenload";
    /**
	 * execute method
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
    throws CommandInvocationException{

    	Log log = LogFactory.getLogger("MRA_GPABILLING");
    	log.entering(CLASS_NAME, "execute");
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	InvoiceSettlementSession session=(InvoiceSettlementSession)getScreenSession(MODULE_NAME,SCREEN_ID);
    	InvoiceSettlementForm form=(InvoiceSettlementForm)invocationContext.screenModel;
    	String companyCode = getApplicationSession().getLogonVO().getCompanyCode();
    	Map oneTimeHashMap 								= null;
		Collection<String> oneTimeActiveStatusList 		= new ArrayList<String>();
		oneTimeActiveStatusList.add(KEY_SETTLEMENT_STATUS_ONETIME);
		
		try {
			/** getting collections of OneTimeVOs */
			oneTimeHashMap = new SharedDefaultsDelegate().findOneTimeValues(companyCode, oneTimeActiveStatusList);
		} catch (BusinessDelegateException e) {
    		e.getMessage();
			errors=handleDelegateException( e );
		}
		session.setOneTimeVOs( (HashMap<String, Collection<OneTimeVO>>)oneTimeHashMap);
		form.setFrmPopUp(null); //added by a-5133 as part of ICRD-23808
		form.setScreenStatus(SCREENSTATUS_SCREENLOAD);
		session.removeInvoiceSettlementVOs();
		session.removeInvoiceSettlementHistoryVOs();
		//session.setGPASettlementVO(null);
		//session.setGPASettlementVOs(null);
		session.setInvoiceSettlementFilterVO(null);
		session.setInvoiceSettlementHistoryVOs(null);
		if(session.getGPASettlementVO()!=null && session.getGPASettlementVO().size()>0){			
			for(GPASettlementVO gpasVo: session.getGPASettlementVO()){
					//form.setGpaCodeFilter(gpasVo.getGpaCode());
					form.setSettlementReferenceNumber(gpasVo.getSettlementId());
					form.setSettleCurrency(gpasVo.getSettlementCurrency());					
					if(gpasVo.getSettlementDate()!=null){ 
					form.setSettlementDate(gpasVo.getSettlementDate().toString());
					}
			}
		}
		if(errors!=null && errors.size()>0){
	    	invocationContext.addAllError(errors);
	    	}
		form.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
    	invocationContext.target = SCREENLOAD_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
    }

}