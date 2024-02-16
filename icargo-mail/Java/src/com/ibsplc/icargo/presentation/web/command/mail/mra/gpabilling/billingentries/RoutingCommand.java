/**
 * Java file       :       com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.billingentries.RoutingCommand.java
 *
 * Created by      :      A-7866
 * Created on      :      18-Oct-2017
 *
 * Copyright 2016 Copyright 2007 IBS Software Services (camera) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 *This software is the proprietary information of Copyright 2007 IBS Software Services (camera) Ltd. All Rights Reserved.  Ltd.
 *Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.billingentries;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPABillingEntriesFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.DSNRoutingSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.GPABillingEntriesSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.GPABillingEntriesForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.billingentries.RoutingCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-866	:	18-Oct-2017		:	Draft
 */
public class RoutingCommand extends BaseCommand{

	
private Log log = LogFactory.getLogger("GPABillingEntries RoutingCommand");
	
	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";
	private static final String SCREEN_ID = "mailtracking.mra.gpabilling.billingentries.listgpabillingentries";
	private static final String ACTION_SUCCESS = "routing_success";
	private static final String CLASS_NAME = "RoutingCommand";
	//Added as part of ICRD-325844
	private static final String MODULE_NAME1 = "mailtracking.mra.defaults";
	private static final String SCREEN_ID1 = "mailtracking.mra.defaults.despatchrouting";

	@Override
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		log.entering(CLASS_NAME,"execute");
		GPABillingEntriesForm form=(GPABillingEntriesForm)invocationContext.screenModel;
		String mailBagId = form.getParameterValue();
		GPABillingEntriesSession session = (GPABillingEntriesSession) getScreenSession(MODULE_NAME, SCREEN_ID);
		session.setFromScreenSessionMap("mailBagId", mailBagId);
		GPABillingEntriesFilterVO gpaBillingEntriesFilterVO = getSearchDetails(form);
		session.setGPABillingEntriesFilterVO(gpaBillingEntriesFilterVO);
		//Added as part of ICRD-325844 starts
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		SharedDefaultsDelegate defaultsDelegate = new SharedDefaultsDelegate();
		DSNRoutingSession  dsnRoutingSession = (DSNRoutingSession)getScreenSession(MODULE_NAME1, SCREEN_ID1);
		Map<String, Collection<OneTimeVO>> hashMap = new HashMap<String, Collection<OneTimeVO>>();
  	    Collection<String> oneTimeList = new ArrayList<String>();
  		    	 oneTimeList.add("mail.mra.defaults.mailsource");
					  try
  	    {
  	      hashMap = defaultsDelegate.findOneTimeValues(logonAttributes.getCompanyCode(), 
  	        oneTimeList);
  	    }
  	    catch (BusinessDelegateException localBusinessDelegateException3)
  	    {
  	     // this.log.log(7, "onetime fetch exception");
  	    }
        Collection<OneTimeVO> mailSources = (Collection<OneTimeVO>)hashMap.get("mail.mra.defaults.mailsource");
        dsnRoutingSession.setMailSources((ArrayList<OneTimeVO>)mailSources);
    	//Added as part of ICRD-325844 ends
		invocationContext.target = ACTION_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}
	
	private GPABillingEntriesFilterVO getSearchDetails(GPABillingEntriesForm form){
		GPABillingEntriesFilterVO gpaBillingEntriesFilterVO = new GPABillingEntriesFilterVO();
		if(form.getFromDate()!= null && form.getFromDate().trim().length()!=0){
			LocalDate fromDateFormat = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
			gpaBillingEntriesFilterVO.setFromDate(fromDateFormat.setDate(form.getFromDate()));
		}
		if(form.getToDate()!= null && form.getToDate().trim().length()!=0){
			LocalDate toDateFormat = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
			gpaBillingEntriesFilterVO.setToDate(toDateFormat.setDate(form.getToDate()));
		}
		gpaBillingEntriesFilterVO.setConDocNumber(form.getConsignmentNumber());
		gpaBillingEntriesFilterVO.setBillingStatus(form.getStatus());
		gpaBillingEntriesFilterVO.setGpaCode(form.getGpaCodeFilter());		
		gpaBillingEntriesFilterVO.setMailbagId(form.getMailbagId());
		gpaBillingEntriesFilterVO.setOriginOfficeOfExchange(form.getOriginOfficeOfExchange());
		gpaBillingEntriesFilterVO.setDestinationOfficeOfExchange(form.getDestinationOfficeOfExchange());
		gpaBillingEntriesFilterVO.setMailCategoryCode(form.getMailCategoryCode());
		gpaBillingEntriesFilterVO.setMailSubclass(form.getMailSubclass());
		gpaBillingEntriesFilterVO.setYear(form.getYear());
		gpaBillingEntriesFilterVO.setDsnNumber(form.getDsn());
		gpaBillingEntriesFilterVO.setRsn(form.getRecepatableSerialNumber());
		gpaBillingEntriesFilterVO.setHni(form.getHighestNumberIndicator());
		gpaBillingEntriesFilterVO.setRegInd(form.getRegisteredIndicator());				
		gpaBillingEntriesFilterVO.setContractRate(form.getContractRate());
		gpaBillingEntriesFilterVO.setUPURate(form.getUPURate());
		if(null != form.getDisplayPage() && "".equals(form.getDisplayPage())){
			gpaBillingEntriesFilterVO.setPageNumber(Integer.parseInt(form.getDisplayPage()));
		}
		return gpaBillingEntriesFilterVO;
	}
}
	
	
	

