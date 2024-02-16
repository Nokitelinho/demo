/*
 * ClearULDMovementHistoryCommand.java Created on Feb 2, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.uldmovementhistory;



import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDMovementFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.ListULDMovementSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ListULDMovementForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is invoked for clearing the screen
 *
 * @author A-1940
 */
public class ClearULDMovementHistoryCommand extends BaseCommand {

    private static final String SCREENID = "uld.defaults.misc.listuldmovement";
	private static final String MODULE = "uld.defaults";
	private static final String CLEAR_SUCCESS = "clear_success";
	private static final String CURRENTSTATUS_ONETIME = "uld.defaults.overallStatus";
	private static final String CONTENT_ONETIME = "uld.defaults.contentcodes";
	private static final String REPAIRSTATUS_ONETIME = "uld.defaults.repairStatus";
	private static final String POSITION_ONETIME = "uld.defaults.position";
	private static final String DAMAGESTATUS_ONETIME = "uld.defaults.damageStatus";
    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	ListULDMovementForm listULDMovementForm = 
    					(ListULDMovementForm)invocationContext.screenModel;
    	ListULDMovementSession listUldMovementSession = 
    				(ListULDMovementSession)getScreenSession(MODULE,SCREENID);
		Log log = LogFactory.getLogger("ULD_MOVEMENT_HISTORY");
		listULDMovementForm.setScreenStatusFlag
				(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		listUldMovementSession.setUldMovementDetails(null);
		listUldMovementSession.setUldMovementFilterVO(null);
		listUldMovementSession.removeAllAttributes();
		listULDMovementForm.setCurrentStn("");
		listULDMovementForm.setCurrentStatus("");
		listULDMovementForm.setFromDate("");
		listULDMovementForm.setOwnerCode("");
		listULDMovementForm.setOwnerStation("");
		listULDMovementForm.setToDate("");
		listULDMovementForm.setUldNumber("");
		listULDMovementForm.setSelectAll(false);
		listULDMovementForm.setListStatus("");	
		listULDMovementForm.setIsUldValid(ULDMovementFilterVO.FLAG_NO);
		listULDMovementForm.setTransactionFlag(ULDMovementFilterVO.FLAG_NO);
		listULDMovementForm.setDisplayPage("1");		
		// added author A-5125 for ICRD-25613 
		HashMap<String,Collection<OneTimeVO>> oneTimeValues= getOneTimeValues();
		listUldMovementSession.setOneTimeValues(oneTimeValues);
		invocationContext.target = CLEAR_SUCCESS;
		log.exiting("ClearULDMovementHistoryCommand","execute");
    }
	private HashMap<String, Collection<OneTimeVO>> getOneTimeValues(){
		/*
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		/*
		 * the shared defaults delegate
		 */
		SharedDefaultsDelegate sharedDefaultsDelegate = 
			new SharedDefaultsDelegate();
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		Collection<ErrorVO> error = new ArrayList<ErrorVO>();
		try {
			oneTimeValues =	sharedDefaultsDelegate.findOneTimeValues(
					logonAttributes.getCompanyCode(), 
					getOneTimeParameterTypes());
		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
			error = handleDelegateException(businessDelegateException);
		}
		return (HashMap<String, Collection<OneTimeVO>>)oneTimeValues;
	}
	   private Collection<String> getOneTimeParameterTypes() {
	    	ArrayList<String> parameterTypes = new ArrayList<String>();
	    	parameterTypes.add(CURRENTSTATUS_ONETIME);    	
	    	parameterTypes.add(CONTENT_ONETIME);
	    	parameterTypes.add(REPAIRSTATUS_ONETIME);
	    	parameterTypes.add(DAMAGESTATUS_ONETIME);
	    	parameterTypes.add(POSITION_ONETIME);
	    	return parameterTypes;    	
    }
}
