/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.ux.invoicesettlementmailbag.ScreenLoadCommand.java
 *
 *	Created by	:	A-7531
 *	Created on	:	24-Apr-2018
 *
 *  Copyright 2018 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.ux.invoicesettlementmailbag;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;


import java.util.Map;


import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
//import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;

import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.ux.InvoiceSettlementMailbagSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.ux.InvoiceSettlementMailbagForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.ux.invoicesettlementmailbag.ScreenLoadCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7531	:	24-Apr-2018	:	Draft
 */
public class ScreenLoadCommand extends BaseCommand {

	 Log log = LogFactory.getLogger("MRA_GPABILLING");
	
	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";

    private static final String SCREEN_ID ="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel";
    
    private static final String SCREENLOAD_SUCCESS ="screenload_success";
    
    private static final String SCREENLOAD_FAILED ="screenload_failure";
    
    private static final String CLASS_NAME = "ScreenLoadCommand";
    
    private static final String KEY_SETTLEMENT_STATUS_ONETIME = "mailtracking.mra.invoicestatus";
    private static final String KEY_PAYMENT_STATUS_ONETIME =  "mailtracking.mra.gpabilling.paymentstatus";
    private static final String CHEQUE_DETAILS_PARAMETER = "mailtracking.mra.gpabilling.ChequeDetailsMandatory";
  // added by 8331
    private static final String OVERRIDEROUNDING = "mailtracking.mra.overrideroundingvalue";
    
    
    
    //  private static final String SCREENSTATUS_SCREENLOAD="screenload";
    /**
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 *	Added by 			: A-7531 on 24-Apr-2018
	 * 	Used for 	:
	 *	Parameters	:	@param arg0
	 *	Parameters	:	@throws CommandInvocationException 
	 */
	@Override
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		// TODO Auto-generated method stub
		Log log = LogFactory.getLogger("MRA_GPABILLING");
    	log.entering(CLASS_NAME, "execute");
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	InvoiceSettlementMailbagSession session = (InvoiceSettlementMailbagSession)getScreenSession(MODULE_NAME,SCREEN_ID);
    	InvoiceSettlementMailbagForm form=(InvoiceSettlementMailbagForm)invocationContext.screenModel;
    	String companyCode = getApplicationSession().getLogonVO().getCompanyCode();
    	Map oneTimeHashMap = null;
    	Collection<String> oneTimeList 		= new ArrayList<String>();
    	oneTimeList.add(KEY_SETTLEMENT_STATUS_ONETIME);  
				  Map<String, String> systemParameterValues = null;

    	oneTimeList.add(KEY_PAYMENT_STATUS_ONETIME);
    	
    	try {
			/** getting collections of OneTimeVOs */
			oneTimeHashMap = new SharedDefaultsDelegate().findOneTimeValues(companyCode, oneTimeList);
			   systemParameterValues=new SharedDefaultsDelegate().findSystemParameterByCodes(getSystemParameterTypes());
} catch (BusinessDelegateException e) {
    		e.getMessage();
			errors=handleDelegateException( e );
		}
    	if(oneTimeHashMap!=null && oneTimeHashMap.size()>0){
    	session.setOneTimeVOs((HashMap<String,Collection<OneTimeVO>>)oneTimeHashMap);
    	}
    	 session.setSystemparametres((HashMap<String, String>)systemParameterValues);
    	
		invocationContext.target = SCREENLOAD_SUCCESS;
	}  
 
private Collection<String> getSystemParameterTypes(){
                                log.entering("Screenloadcommand", "getSystemParameterTypes");
                                ArrayList<String> systemparameterTypes = new ArrayList<String>();
                            	
                       //added by 8331         
                                
                                systemparameterTypes.add(OVERRIDEROUNDING);
                                
                                
                                //
                                systemparameterTypes.add(CHEQUE_DETAILS_PARAMETER);
                                log.exiting("ScreenLoadCommand", "getSystemParameterTypes");
                                return systemparameterTypes;

}

	
	
	
	
	



}