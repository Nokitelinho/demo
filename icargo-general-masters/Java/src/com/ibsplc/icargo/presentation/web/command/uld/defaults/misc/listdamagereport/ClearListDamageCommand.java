/*
 * ClearListDamageCommand.java Created on Jan 16, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.listdamagereport;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.ListDamageReportSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ListDamageReportForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;

/**
 * @author A-1617
 *
 */
public class ClearListDamageCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("ClearListDamageCommand");
	
	private static final String SCREENID = "uld.defaults.listdamagereport";
	
	private static final String MODULE_NAME = "uld.defaults";

	private static final String CLEAR_SUCCESS= "Clear_success";
	
	 /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	ApplicationSessionImpl applicationSessionImpl=getApplicationSession();
		LogonAttributes logonAttributes=applicationSessionImpl.getLogonVO();
		
    	ListDamageReportForm form = (ListDamageReportForm) invocationContext.screenModel;
    	ListDamageReportSession session = getScreenSession(MODULE_NAME, SCREENID);
    	form
		.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
    	session.setULDDamagePictureVO(null);
		form.setUldNo("");
		form.setDamageRefNo("");
		form.setUldTypeCode("");
		form.setCurrentStn("");
		form.setReportedStn("");
		form.setRepairedDateFrom("");
		form.setRepairedDateTo("");
		form.setDisplayPage("1");//Modified to resolve compilation issue
		form.setUldStatus("ALL");
		form.setUldDamageStatus("ALL");
		form.setParty("");
		form.setPartyType("");
		form.setLocation("");
		form.setFacilityType("");
		
		if(form.getListPage()!=null){
			form.setListPage(null);
		}
		session.removeULDDamageRepairDetailsVOs();
		session.removeULDDamageFilterVO();
		session.removeScreenId();
		ULDDamageFilterVO filterVO = new ULDDamageFilterVO();
		if(logonAttributes.isAirlineUser()){    		
    		form.setDamageDisableStatus("airline");
    	}
    	else{
    		filterVO.setCurrentStation(logonAttributes.getAirportCode());
    		form.setDamageDisableStatus("GHA");
    	}
		form.setListStatus("");
		session.setULDDamageFilterVO(filterVO);
		
		invocationContext.target =CLEAR_SUCCESS;
    }

}
