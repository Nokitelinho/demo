/*
 * ListDamageDetailsCommand.java Created on July 01, 2016
 * 
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved. 
 * 
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.dsnenquiry;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.DamagedDSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.DamagedDSNSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.DamagedDSNForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class ListDamageDetailsCommand extends BaseCommand {

	private static final String SUCCESS = "list_success";
	
	private Log log = LogFactory.getLogger("ListDamageDetailsCommand");
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.damageddsn";
	/**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
									throws CommandInvocationException {
    	log.log(Log.FINE, "\n\n in the list command----------> \n\n");
    	
    	DamagedDSNForm damagedDSNForm =
							(DamagedDSNForm)invocationContext.screenModel;
    	DamagedDSNSession damagedDSNSession = 
    									getScreenSession(MODULE_NAME,SCREEN_ID);
    	
    	ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		Collection<ErrorVO> errors = null;

	    MailTrackingDefaultsDelegate delegate = new MailTrackingDefaultsDelegate();
	    
	    DespatchDetailsVO selectedvo = damagedDSNSession.getDespatchDetailsVO();
	    
	    Collection<DespatchDetailsVO> selectedVOs = new ArrayList<DespatchDetailsVO>();
	    
	    selectedVOs.add(selectedvo);
	    
	    DamagedDSNVO damagedDSNVO = new DamagedDSNVO();
	    Collection<DamagedDSNVO> damagedDSNVOs = new ArrayList<DamagedDSNVO>();
	    
	    try{
	    	damagedDSNVOs = delegate.findDSNDamages(selectedVOs);
	    }catch(BusinessDelegateException businessDelegateException){
	    	errors = handleDelegateException(businessDelegateException);
	    }
	    
	    if(damagedDSNVOs != null && damagedDSNVOs.size()>0){
	    	for(DamagedDSNVO returnedVO:damagedDSNVOs){
	    		damagedDSNVO = returnedVO;
	    	}
	    }
	    
	    String yearPrefix = new LocalDate
								(logonAttributes.getAirportCode(),Location.ARP,false).
										toDisplayFormat("yyyy").substring(0,3);
	    String year = new StringBuffer(yearPrefix).append(damagedDSNVO.getYear()).toString();
	    damagedDSNVO.setYear(Integer.parseInt(year));
	    
	    log.log(Log.FINE, "\n\n damagedDSNVO---------->  ", damagedDSNVO);
		damagedDSNSession.setDamagedDSNVO(damagedDSNVO);
		
	    damagedDSNForm.setScreenStatusFlag
							(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		
		invocationContext.target = SUCCESS;
	}
}
