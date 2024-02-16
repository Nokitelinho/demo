/*
 * ListCommand.java created on APR 23,2009
 * Copyright 2009 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.  
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.fuelsurcharge;


import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.FuelSurchargeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.FuelSurchargeSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.FuelSurchargeForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2391
 *
 */
public class ListCommand extends BaseCommand{

	private  Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "ListCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.fuelsurcharge";

	private static final String LIST_SUCCESS = "list_success";
	private static final String LIST_FAILURE = "list_failure";

	
	
	/*
	 *  (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		log.entering(CLASS_NAME,"execute");
		FuelSurchargeSession session=getScreenSession(MODULE_NAME,SCREENID);
		LogonAttributes logonAttributes=getApplicationSession().getLogonVO();
		Collection<FuelSurchargeVO> surchargeVos=null;
		FuelSurchargeForm form = (FuelSurchargeForm) invocationContext.screenModel;
		  Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		String gpaCode=form.getGpaCode();
	
		try{
    		MailTrackingMRADelegate mailTrackingMRADelegate = new MailTrackingMRADelegate();
    		
    		surchargeVos =mailTrackingMRADelegate.displayFuelSurchargeDetails(gpaCode,logonAttributes.getCompanyCode());
			log
					.log(Log.FINE, "surchargeVos from Server is----->",
							surchargeVos);
						
		}catch(BusinessDelegateException businessDelegateException){
	    		log.log(Log.FINE,"inside try...caught businessDelegateException");
	        	businessDelegateException.getMessage();
	        	handleDelegateException(businessDelegateException);
		}  		
		if(surchargeVos!=null && surchargeVos.size()>0){
			session.setFuelSurchargeVOs(surchargeVos);
			FuelSurchargeVO vo=new FuelSurchargeVO();
			ArrayList<FuelSurchargeVO> vos =new ArrayList<FuelSurchargeVO>(surchargeVos);
			vo=vos.get(0);
			form.setCountry(vo.getCountry());
			form.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
			invocationContext.target = LIST_SUCCESS;	
		}
		else{
			ErrorVO errorVO = new ErrorVO(
			"mailtracking.mra.defaults.fuelsurcharge.msg.err.nofuelsurchargedetails");
			//form.setScreenStatusFlag(SCREEN_STATUS_SCREENLOAD);
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(errorVO);						
		}
		if( errors.size() > 0){
			log.log(Log.FINE,"!!!inside errors!= null");
			invocationContext.addAllError(errors);					
			invocationContext.target = LIST_FAILURE;
		}
	}
}
