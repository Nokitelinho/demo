/*
 * ScreenLoadCommand.java Created on Jul 1 2016
 *
 * Copyright 2010 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.mailbagreconciliation;

import static com.ibsplc.icargo.framework.util.time.Location.ARP;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailbagReconciliationSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailbagReconciliationForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 * @author A-3270
 *
 */
public class ScreenLoadCommand extends BaseCommand {
	
	private static final String SCREENLOADSUCCESS=
		"screenload_success";
	private static final String MODULENAME = "mail.operations";
	
	private static final String SCREENID = "mailtracking.defaults.MailbagReconciliation";
	
	private static final String CATEGORY = "mailtracking.defaults.mailcategory";
	
	private static final String DELAYPERIOD = "mailtracking.defaults.resditreconciliation.delayperiod";
	
	private Log log = LogFactory.getLogger("Screen Load Forwarded Area command");
	/**
	 * Method to execute the command
	 * @param invocationContext
	 * @exception  CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)throws CommandInvocationException {
		log.log(Log.FINE,"***inside screen load****");
		String companyCode=null; 
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
	    companyCode = logonAttributes.getCompanyCode();
	    MailbagReconciliationForm form = 
    		(MailbagReconciliationForm)invocationContext.screenModel;
	    MailbagReconciliationSession session = 
    		getScreenSession(MODULENAME, SCREENID);
	    Map onetimeMap = null;
	    HashMap<String, String> systemParameterCodes = new HashMap<String, String>();
		Collection<String> oneTimeList = new ArrayList<String>();
		Collection<String> sysParList = new ArrayList<String>();
		sysParList.add(DELAYPERIOD);
		oneTimeList.add(CATEGORY);
		Collection<ErrorVO> errors=new ArrayList<ErrorVO>();
		try {
			onetimeMap =new SharedDefaultsDelegate().findOneTimeValues(companyCode,oneTimeList);
			systemParameterCodes = (HashMap<String, String>) (new SharedDefaultsDelegate()
			.findSystemParameterByCodes(sysParList)); 
			log.log(Log.FINEST, "hash map*****************************",
					onetimeMap);

		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		String sysParDelayPeriod=systemParameterCodes.get(DELAYPERIOD);
		if(sysParDelayPeriod!=null && sysParDelayPeriod.trim().length()>0){
			session.setDelayPeriod(Integer.parseInt(sysParDelayPeriod));
		}else{
			session.setDelayPeriod(0);
		}
		
		Collection<OneTimeVO> types = (Collection<OneTimeVO>) onetimeMap.get(CATEGORY);
		session.setOneTimeType(types);
		LocalDate fromdate = new LocalDate(logonAttributes.getAirportCode(),ARP,true);
		LocalDate todate = new LocalDate(logonAttributes.getAirportCode(),ARP,true);
		fromdate.addDays(-30);
		form.setFromDate(fromdate.toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
		form.setToDate(todate.toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
	    session.removeMailReconciliationFilterVO();
	    session.removeMailReconciliationDetailsVOs();
	    invocationContext.target=SCREENLOADSUCCESS;
	}

}
