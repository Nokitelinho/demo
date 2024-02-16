/*
 * ScreenLoadCommand.java Created on Feb 28, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintainbillingmatrix;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingMatrixVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainBillingMatrixSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.BillingMatrixForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-2398
 *
 */
public class ScreenLoadCommand extends BaseCommand {

	private  Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "ScreenLoadCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.maintainbillingmatrix";

	private static final String SCREEN_SUCCESS = "screenload_success";
	
	//private static final String ENABLE = "ENABLE";
	
	private static final String KEY_RATE_STATUS =
		"mra.gpabilling.ratestatus";
	private static final String KEY_BILLED_SECTOR =
		"mailtracking.mra.billingSector";
	
	private static final String KEY_BILLING_PARTY = "mailtracking.mra.billingparty";
	private static final String BLANK = "";
	private static final String KEY_UNTCOD ="mra.gpabilling.untcod";
	private static final String SYS_PAR_OVERRIDE_ROUNDING = "mailtracking.mra.overrideroundingvalue";//added by a-7871 for ICRD-214766

	
	


	/**
	 * 	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering(CLASS_NAME,"execute");    	
			
    	BillingMatrixForm form = (BillingMatrixForm) invocationContext.screenModel;
		MaintainBillingMatrixSession session =
			(MaintainBillingMatrixSession) getScreenSession(
				MODULE_NAME, SCREENID);
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		Map<String, String> systemParameterValues = null;
		Map oneTimeHashMap 								= null;
		Collection<String> oneTimeActiveStatusList 		= new ArrayList<String>();
		session.removeBillingLneDetails();
		session.removeBillingMatrixVO();
		session.removeIndexMap();
		session.removeOneTimeVOs();
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		//BillingLineVO sessionVO = null;
		String companyCode = logonAttributes.getCompanyCode().toUpperCase();
		form.setBlgMatrixID(BLANK);
		session.removeBillingLneDetails();
		session.removeBillingMatrixVO();
		session.removeIndexMap();
		session.removeOneTimeVOs();
		session.removeBillingMatrixFilterVO();
		
		/** adding attributes to map for passing to SharedDefaultsDelegate */
		oneTimeActiveStatusList.add(KEY_RATE_STATUS);
		oneTimeActiveStatusList.add(KEY_BILLED_SECTOR);		
		oneTimeActiveStatusList.add(KEY_BILLING_PARTY);	
		oneTimeActiveStatusList.add(KEY_UNTCOD);		
		try {
			/** getting collections of OneTimeVOs */
			oneTimeHashMap = new SharedDefaultsDelegate().findOneTimeValues(companyCode, oneTimeActiveStatusList);
			systemParameterValues=new SharedDefaultsDelegate().findSystemParameterByCodes(getSystemParameterTypes());
		} catch (BusinessDelegateException e) {
    		e.getMessage();
			errors=handleDelegateException( e );
		}
		
		log.log(Log.INFO, "  the oneTimeHashMap after server call is ",
				oneTimeHashMap);
		session.setOneTimeVOs( (HashMap<String, Collection<OneTimeVO>>)oneTimeHashMap);
		
    	if(session.getBillingMatrixVO() == null){
    		log.log(Log.FINE,"No billingMatrix VO set in session.....");
    		BillingMatrixVO billingMatrixVO = new BillingMatrixVO();
    		if(form.getBlgMatrixID()!= null ){
    			billingMatrixVO.setBillingMatrixId(form.getBlgMatrixID()
    					.trim().toUpperCase());
    		
			billingMatrixVO.setBillingLineVOs(null);
			session.setBillingLineDetails(null);
			session.setIndexMap(null);
			//  set the new VO to session..
			session.setSystemparametres((HashMap<String, String>)systemParameterValues);
			session.setBillingMatrixVO(billingMatrixVO);
			log.log(Log.FINE,
					"The newly set BillingMatrixVO in session  is....",
					billingMatrixVO);
    		}
    	}
    	else{
    		log.log(Log.INFO,
					"The billing matrix vo is already present in session....",
					session.getBillingMatrixVO().toString());
			session.setBillingLineDetails(null);
    			//form.setLastPageNum(String.valueOf(session.getBillingLineDetails().size()));
    		}
    	
    	if(errors.size() > 0) {
			invocationContext.addAllError(errors);
		}
    	
    	invocationContext.target = SCREEN_SUCCESS;
		
    }
    private Collection<String> getSystemParameterTypes(){
    	log.entering("RefreshCommand", "getSystemParameterTypes");
    	ArrayList<String> systemparameterTypes = new ArrayList<String>();

    	systemparameterTypes.add(SYS_PAR_OVERRIDE_ROUNDING);
    	log.exiting("ScreenLoadCommand", "getSystemParameterTypes");
    	return systemparameterTypes;
      }
    }
