/*
 * ClearDamageReportCommand.java Created on Feb 07, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.
													maintaindamagereport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.
														SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.
									defaults.misc.MaintainDamageReportSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.
										defaults.misc.MaintainDamageReportForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is invoked clearing the screen
 *
 * @author A-1862
 */
public class ClearDamageReportCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("Maintain DamageReport");
	private static final String MODULE = "uld.defaults";
	/*
	 * Screen Id of maintain damage report screen
	 */
	private static final String SCREENID =
		"uld.defaults.maintaindamagereport";
	private static final String CLEAR_SUCCESS = "clear_success";
	private static final String DAMAGESTATUS_ONETIME = "uld.defaults.damageStatus";
	private static final String OVERALLSTATUS_ONETIME = "uld.defaults.overallStatus";
	private static final String REPAIRSTATUS_ONETIME = "uld.defaults.repairStatus";
    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	ApplicationSessionImpl applicationSession = getApplicationSession();
    	LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String  compCode = logonAttributes.getCompanyCode();
    	MaintainDamageReportForm maintainDamageReportForm = 
    		(MaintainDamageReportForm)invocationContext.screenModel;
    	maintainDamageReportForm.setPicturePresent("false");
    	MaintainDamageReportSession maintainDamageReportSession = 
    			getScreenSession(MODULE, SCREENID);
    	clearForm(maintainDamageReportForm);
    	maintainDamageReportSession.setULDDamagePictureVO(null);
    	maintainDamageReportSession.setSavedULDDamageVO(null);
		maintainDamageReportSession.setULDDamageVO(null);
		maintainDamageReportSession.setULDRepairVOs(null);
		maintainDamageReportSession.setULDDamageVOs(null);
		maintainDamageReportSession.setRefNo(null);
		
		//added by saritha
		maintainDamageReportSession.removeUldNumber();
		
		
    	SharedDefaultsDelegate sharedDefaultsDelegate =
    										new SharedDefaultsDelegate();
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		Collection<ErrorVO> exception = new ArrayList<ErrorVO>();
		try {
			oneTimeValues =	sharedDefaultsDelegate.findOneTimeValues(
					compCode, getOneTimeParameterTypes());
		} catch (BusinessDelegateException e) {
			e.getMessage();
			exception = handleDelegateException(e);
		}
		maintainDamageReportSession.setOneTimeValues(
				(HashMap<String, Collection<OneTimeVO>>)oneTimeValues);
		maintainDamageReportForm.setScreenStatusValue("SCREENLOAD");
		//maintainDamageReportForm.setScreenStatusFlag("SCREENLOAD");
    	invocationContext.target = CLEAR_SUCCESS;

    }
    
	private void clearForm(MaintainDamageReportForm maintainDamageReportForm) {
			
		maintainDamageReportForm.setUldNumber("");
		maintainDamageReportForm.setSupervisor("");
		maintainDamageReportForm.setTotAmt("");
		maintainDamageReportForm.setRemarks("");
		maintainDamageReportForm.setInvRep("");
		maintainDamageReportForm.setDamageStatus("");
	}
	/**
	 * Method to populate the collection of
	 * onetime parameters to be obtained
	 * @return parameterTypes
	 */
	private Collection<String> getOneTimeParameterTypes() {
		log.entering("ClearDamageReportCommand","getOneTimeParameterTypes");
		ArrayList<String> parameterTypes = new ArrayList<String>();
		
	   	parameterTypes.add(DAMAGESTATUS_ONETIME);
		parameterTypes.add(OVERALLSTATUS_ONETIME);
		parameterTypes.add(REPAIRSTATUS_ONETIME);
		
		log.exiting("ClearDamageReportCommand","getOneTimeParameterTypes");
		return parameterTypes;    	
	}
}
