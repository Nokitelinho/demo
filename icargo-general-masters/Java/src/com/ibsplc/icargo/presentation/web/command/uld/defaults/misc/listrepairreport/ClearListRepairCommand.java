/*
 * ClearListRepairCommand.java Created on Jan 16, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.listrepairreport;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.ListRepairReportSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ListRepairReportForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2046
 *
 */
public class ClearListRepairCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("ClearListRepairCommand");

	private static final String SCREENID = "uld.defaults.listrepairreport";

	private static final String MODULE_NAME = "uld.defaults";

	private static final String CLEAR_SUCCESS= "Clear_success";

	private static final String BLANK = "";

    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	ApplicationSessionImpl applicationSessionImpl=getApplicationSession();
		LogonAttributes logonAttributes=applicationSessionImpl.getLogonVO();
		
    	ListRepairReportForm form = (ListRepairReportForm) invocationContext.screenModel;
    	ListRepairReportSession session = getScreenSession(MODULE_NAME, SCREENID);
		form.setUldNo(BLANK );
		form.setUldTypeCode(BLANK );
		form.setRepairHead(BLANK);
		form.setUldStatus(BLANK);
		form.setRepairedStn(BLANK);
		form.setCurrentStn(BLANK);
		form.setRepairStatus(BLANK);
		form.setRepairedDateFrom(BLANK);
		form.setRepairedDateTo(BLANK);
		form.setDisplayPage("1");

		if(form.getListPage()!=null){
			form.setListPage(null);
		}
		session.removeULDDamageRepairDetailsVOs();
		session.removeULDRepairFilterVO();
		
		ULDRepairFilterVO filterVO = new ULDRepairFilterVO();
		if(logonAttributes.isAirlineUser()){    		
    		form.setRepairDisableStatus("airline");
    	}
    	else{
    		filterVO.setCurrentStation(logonAttributes.getAirportCode());
    		form.setRepairDisableStatus("GHA");
    	}
		session.setULDRepairFilterVO(filterVO);

		invocationContext.target =CLEAR_SUCCESS;
    }




}
