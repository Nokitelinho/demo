/*
 * ViewProrationCommand.java Created on June 8, 2009
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.listrateaudit;

import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationExceptionsFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ListProrationExceptionsSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ListRateAuditSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListRateAuditForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-3434
 *
 */
public class ViewProrationCommand extends BaseCommand {

	private  Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "ViewProrationCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID =
		"mailtracking.mra.defaults.listrateaudit";
	private static final String VIEW_SCREEN_ID = "mailtracking.mra.defaults.listmailprorationexceptions";
	
	private static final String NAVIGATE_SUCCESS = "navigate_success";
	
	private static final String FROM_SCREEN="fromProrationException";
	

	/**
	 * Method to implement the navigate operation
	 *to ListProrationException Screen
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering(CLASS_NAME,"execute");

    	ListRateAuditForm listRateAuditForm=(ListRateAuditForm)invocationContext.screenModel;

		ListRateAuditSession listRateAuditSession = getScreenSession(
				MODULE_NAME, SCREENID);
		
		ListProrationExceptionsSession listExceptionSession = getScreenSession(
				MODULE_NAME, VIEW_SCREEN_ID);
    	LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		
		int select= listRateAuditForm.getSelectedRowIndex();
		log.log(Log.FINE, "Select : ", select);
		Collection<RateAuditVO> rateAuditVOs = listRateAuditSession.getRateAuditVOs();
	
    	ArrayList<RateAuditVO> rateAuditVOArraylist = new ArrayList<RateAuditVO>(
    			rateAuditVOs);
    	RateAuditVO rateAuditVO;
    		
    	rateAuditVO= rateAuditVOArraylist.get(select);
    	log.log(Log.FINE, "Inside NavigateCommand ..rateAuditVO. >>",
				rateAuditVO);
		ProrationExceptionsFilterVO proExpFilterVO = new ProrationExceptionsFilterVO();
    	
    	proExpFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
    	
    	proExpFilterVO.setDispatchNo(rateAuditVO.getDsn());
    	
    	LocalDate currentDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
    	
		proExpFilterVO.setToDate(currentDate);
		
		log.log(Log.FINE, "Inside NavigateCommand ..proExpFilterVO. >>",
				proExpFilterVO);
		listExceptionSession.setProrationExceptionFilterVO(proExpFilterVO);
    	
    	//listExceptionSession.setFromScreenFlag("Y");
    	listRateAuditSession.setFromScreen(FROM_SCREEN);
    	
    	invocationContext.target =  NAVIGATE_SUCCESS;
    	
    }
}
