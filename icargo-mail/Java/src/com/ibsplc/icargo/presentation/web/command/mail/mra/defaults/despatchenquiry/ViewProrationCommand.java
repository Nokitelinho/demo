/*
 * ViewProrationCommand.java Created on NOV 06, 2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.despatchenquiry;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNPopUpVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationFilterVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.DespatchEnqSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MRAViewProrationSession;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 * @author A-3227 RENO K ABRAHAM
 *
 */
public class ViewProrationCommand  extends BaseCommand {
	
	private Log log = LogFactory.getLogger("MRA DEFAULTS DespatchEnquiry ViewProrationCommand");
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	private static final String SCREENID = "mailtracking.mra.defaults.despatchenquiry";
	private static final String SCREENID_VIEW_PRORATION ="mailtracking.mra.defaults.viewproration";
	private static final String SYS_PAR_OVERRIDE_ROUNDING = "mailtracking.mra.overrideroundingvalue";

	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	 public void execute(InvocationContext invocationContext)
     throws CommandInvocationException {
		 
		log.entering("ViewProrationCommand", "execute");
		MRAViewProrationSession mailProrationSession = getScreenSession(MODULE_NAME,SCREENID_VIEW_PRORATION);

		DespatchEnqSession despatchEnqSession=getScreenSession(MODULE_NAME,SCREENID);
		DSNPopUpVO dsnPopUpVO=despatchEnqSession.getDispatchFilterVO();
		log.log(Log.INFO, "ViewProrationCommand----dsnPopUpVO--in view----> ",
				dsnPopUpVO);
		ProrationFilterVO prorationFilterVO = new ProrationFilterVO();
		
		prorationFilterVO.setCompanyCode(dsnPopUpVO.getCompanyCode());
		prorationFilterVO.setDespatchSerialNumber(dsnPopUpVO.getDsn());	
		prorationFilterVO.setBillingBasis(dsnPopUpVO.getBlgBasis());
		prorationFilterVO.setConsigneeDocumentNumber(dsnPopUpVO.getCsgdocnum());			
		prorationFilterVO.setConsigneeSequenceNumber(dsnPopUpVO.getCsgseqnum());
		prorationFilterVO.setPoaCode(dsnPopUpVO.getGpaCode());
		
		mailProrationSession.setProrationFilterVO(prorationFilterVO);
		log
				.log(Log.INFO, "prorationFilterVO---in view---> ",
						prorationFilterVO);
		
			//added by A-7371  for ICRD-216344
		Map<String, String> systemParameterValues = null;
		try {
			/** getting collections of OneTimeVOs */
			systemParameterValues=new SharedDefaultsDelegate().findSystemParameterByCodes(getSystemParameterTypes());
		} catch (BusinessDelegateException e) {
			handleDelegateException( e );
		}
		
		mailProrationSession.setSystemparametres((HashMap<String, String>)systemParameterValues);


		log.exiting(" ViewProrationCommand", "execute");
		 
		invocationContext.target = SCREENLOAD_SUCCESS;
		 
	 }
	 
	/**
	 * 
	 * 	Method		:	ViewProrationCommand.getSystemParameterTypes
	 *	Added by 	:	A-7371 on 19-Jul-2017
	 * 	Used for 	:   Resolving ICRD-216344
	 *	Parameters	:	@return 
	 *	Return type	: 	Collection<String>
	 */
	 private Collection<String> getSystemParameterTypes(){
	    	log.entering("ViewProrationCommand", "getSystemParameterTypes");
	    	ArrayList<String> systemparameterTypes = new ArrayList<String>();
	    	systemparameterTypes.add(SYS_PAR_OVERRIDE_ROUNDING);
	    	log.exiting("ViewProrationCommand", "getSystemParameterTypes");
	    	return systemparameterTypes;
	 }


}

