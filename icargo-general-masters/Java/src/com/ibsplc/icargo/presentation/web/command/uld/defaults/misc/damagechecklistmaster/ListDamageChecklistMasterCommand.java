/*
 * ListDamageChecklistMasterCommand.java Created on May 5, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.damagechecklistmaster;

import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageChecklistVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.DamageChecklistMasterSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.DamageChecklistMasterForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3459
 *
 */
public class ListDamageChecklistMasterCommand  extends BaseCommand{
	
	    private static final String LIST_SUCCESS = "list_success";
	    private static final String LIST_FAILURE = "list_failure";
	    private static final String SCREENID = "uld.defaults.damagechecklistmaster";
	    private static final String MODULE = "uld.defaults";
		private Log log = LogFactory.getLogger("ListCommand");
		
		
		/**
	     * execute method
	     * @param invocationContext
	     * @throws CommandInvocationException
	    */
	public void execute(InvocationContext invocationContext)
         throws CommandInvocationException {
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes  =  applicationSession.getLogonVO();
        log.entering("ListCommand---------------->>>>","Entering");
        String companyCode = logonAttributes.getCompanyCode();
        DamageChecklistMasterForm damageChecklistMasterForm = 
			(DamageChecklistMasterForm) invocationContext.screenModel;
        DamageChecklistMasterSession damageChecklistMasterSession  = 
			getScreenSession(MODULE, SCREENID);
        damageChecklistMasterSession.removeULDDamageChecklistVO();
        String section= damageChecklistMasterForm.getSection();
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
        Collection<ULDDamageChecklistVO>damageChecklistVOs=new ArrayList <ULDDamageChecklistVO>();
       
        ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();
        try {	        	
        	damageChecklistVOs = delegate.listULDDamageChecklistMaster(companyCode,section);
    	log
				.log(
						Log.FINE,
						"damageChecklistVOs getting from delegate--------->>>>>>>>>>>>>>",
						damageChecklistVOs);        	
	} catch (BusinessDelegateException e) {
		e.getMessage();
		errors= handleDelegateException(e);
	} 
      if(damageChecklistVOs==null ||damageChecklistVOs.size()==0)
      {
    	 invocationContext.addError(new ErrorVO(
  	            "uld.defaults.nodetails",null));
  			invocationContext.addAllError(errors);
  			damageChecklistMasterForm.setStatusFlag("list_success");
  			damageChecklistMasterForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
  			invocationContext.target = LIST_FAILURE;
  			return;
      }
      else
      {		
    	     	
    	  damageChecklistMasterSession.setULDDamageChecklistVO(damageChecklistVOs);
    	  damageChecklistMasterForm.setStatusFlag("list_success");
			damageChecklistMasterForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
    	  invocationContext.target = LIST_SUCCESS;
      }
        
}
}
