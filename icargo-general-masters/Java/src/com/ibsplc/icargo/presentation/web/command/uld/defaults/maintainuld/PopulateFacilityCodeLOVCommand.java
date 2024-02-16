/*
 * PopulateFacilityCodeLOVCommand.java Created on Jul 18, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.maintainuld;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAirportLocationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.MaintainULDSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.FacilityCodeLOVForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1862
 *
 */
public class PopulateFacilityCodeLOVCommand  extends BaseCommand {

	private static final String SCREENLOAD_SUCCESS = "screenload_success";  
    private static final String MODULE = "uld.defaults";

	private static final String SCREENID =
		"uld.defaults.maintainuld";
    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	Log log = LogFactory.getLogger("CAP CHARTER COST HEAD LOV");    	
    	FacilityCodeLOVForm actionForm =
			(FacilityCodeLOVForm) invocationContext.screenModel;
    	ApplicationSessionImpl applicationSessionImpl=getApplicationSession();
    	LogonAttributes logonAttributes=applicationSessionImpl.getLogonVO();
    	MaintainULDSession maintainULDSession = getScreenSession(MODULE, SCREENID);
    	    String companyCode=logonAttributes.getCompanyCode().toUpperCase();
    	    maintainULDSession.setFacilityTypes(null);
        log.log(Log.FINE, "\n\n\n\n actionForm.getFacilityTypeForLov() ---> " + actionForm.getFacilityTypeForLov().toUpperCase());
        log.log(Log.FINE, "\n\n\n\n actionForm.getCurrentStationForLov() ---> " + actionForm.getCurrentStationForLov().toUpperCase());
    	Collection<ULDAirportLocationVO> uldAirportLocationVOs= null; 
    	ULDDefaultsDelegate uldDefaultsDelegate = new ULDDefaultsDelegate();
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	try {
    		uldAirportLocationVOs = uldDefaultsDelegate.listULDAirportLocation
    		(companyCode,actionForm.getCurrentStationForLov().toUpperCase()
    				,actionForm.getFacilityTypeForLov().toUpperCase());
    	}  catch(BusinessDelegateException businessDelegateException) 
    	{
    		businessDelegateException.getMessage();
    		errors = handleDelegateException(businessDelegateException);
    	}
    	
    	log.log(Log.FINE, "\n\n\n\n uldAirportLocationVOs ---> " + uldAirportLocationVOs);
    	if(uldAirportLocationVOs.size()>0){
    		maintainULDSession.setFacilityTypes(uldAirportLocationVOs);
    	}
    	
    	
    	invocationContext.target=SCREENLOAD_SUCCESS;
    	
    }


}
