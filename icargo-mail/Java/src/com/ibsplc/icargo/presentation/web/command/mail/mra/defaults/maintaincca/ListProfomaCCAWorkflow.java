/*
 * ListProfomaCCAWorkflow.java Created on July-14, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintaincca;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.workflow.defaults.vo.ParameterConstantsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.framework.util.unit.UnitFormatter;
import com.ibsplc.icargo.framework.util.unit.vo.UnitRoundingVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainCCASession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MRAMaintainCCAForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.presentation.web.session.interfaces.workflow.defaults.MessageInboxSession;


/**
 * @author A-3227 RENO K ABRAHAM
 *
 */
public class ListProfomaCCAWorkflow  extends BaseCommand {

	/**
	 * Logger and the file name
	 */
	private Log log = LogFactory.getLogger("MRA DEFAULTS");

	/**
	 * Class name
	 */
	private static final String CLASS_NAME = "ListCommand";

	/**
	 * Module name
	 */
	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	/**
	 * Screen ID
	 */
	private static final String MAINTAINCCA_SCREEN = "mailtracking.mra.defaults.maintaincca";
	
	private static final String CCATYPE_ONETIME = "mra.defaults.ccatype";
	private static final String ISSUINGPARTY_ONETIME = "mra.defaults.issueparty";

	private static final String SCREEN_ID_INBOX = "workflow.defaults.messageinbox";
	private static final String MODULE_NAME_INBOX = "workflow.defaults";
	private static final String LIST_SUCCESS = "list_success";
	private static final String LIST_FAILURE = "list_failure";

	/**
	 * Execute method
	 * 
	 * @param invocationContext *
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {

		log.entering(CLASS_NAME, "execute");
		MRAMaintainCCAForm maintainCCAForm = (MRAMaintainCCAForm) invocationContext.screenModel;
		MaintainCCASession maintainCCASession = 
			(MaintainCCASession) getScreenSession(MODULE_NAME, MAINTAINCCA_SCREEN);

		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode().toUpperCase();		

		maintainCCAForm.setUsrCCANumFlg("");
		maintainCCAForm.setRateAuditedFlag("N");
		maintainCCAForm.setAutoratedFlag("N");
		Map<String, Collection<OneTimeVO>> oneTimeValues = fetchOneTimeDetails(logonAttributes.getCompanyCode());		
		maintainCCASession.setOneTimeVOs((HashMap<String,Collection<OneTimeVO>>)oneTimeValues);
		setUnitComponent(logonAttributes.getStationCode(),maintainCCASession);
		log.log(Log.FINE, "onetimes----->", maintainCCASession.getOneTimeVOs());
		/*
		 * Picking CCA RefNum From Workflow Message 
		 */
		MessageInboxSession messageInboxSession = 
			(MessageInboxSession) getScreenSession(MODULE_NAME_INBOX, SCREEN_ID_INBOX);
		HashMap<String,String> messageMap = messageInboxSession.getMessageMap();

		if(messageMap != null && messageMap.get(ParameterConstantsVO.WRKFLO_PARCOD_CCAREFNUM) != null ) {
			maintainCCAForm.setCcaNum(messageMap.get(ParameterConstantsVO.WRKFLO_PARCOD_CCAREFNUM));
			log.log(Log.FINE, "CCA No", messageMap.get(ParameterConstantsVO.WRKFLO_PARCOD_CCAREFNUM));
		}
		
		if(maintainCCAForm.getCcaNum() != null && 
				maintainCCAForm.getCcaNum().trim().length() > 0) {
			invocationContext.target = LIST_SUCCESS;
		}else {
			invocationContext.target = LIST_FAILURE;			
		}
	}
	/**
	 * @author A-3227 Reno K Abraham
	 * @param companyCode
	 * @return
	 */
	private Map<String, Collection<OneTimeVO>> fetchOneTimeDetails(String companyCode) {
		log.entering(CLASS_NAME,"fetchOneTimeDetails");
		Map<String, Collection<OneTimeVO>> hashMap =new HashMap<String, Collection<OneTimeVO>>();
		Collection<String> oneTimeList=new ArrayList<String>();
		oneTimeList.add(CCATYPE_ONETIME);
		oneTimeList.add(ISSUINGPARTY_ONETIME);
		
		SharedDefaultsDelegate sharedDefaultsDelegate =
			new SharedDefaultsDelegate();
		try {
			hashMap = sharedDefaultsDelegate.findOneTimeValues(companyCode,
					oneTimeList);
		} catch (BusinessDelegateException e) {			
			handleDelegateException(e);
		}
		log.exiting(CLASS_NAME,"fetchOneTimeDetails");
		return hashMap;
	}

	 /**
	 * @author A-3227 Reno K Abraham
	 * @param stationCode
	 * @param maintainCCASession
	 */
	private void setUnitComponent(String stationCode,
			 MaintainCCASession maintainCCASession){
			UnitRoundingVO unitRoundingVO = null;
			try{
				log.log(Log.FINE, "\n\n<----STATION CODE IS----------->",
						stationCode);
				unitRoundingVO = UnitFormatter.getStationDefaultUnit(
						stationCode, UnitConstants.WEIGHT);			
				log.log(Log.FINE,
						"\n\n<----UNIT ROUNDING VO FOR WEIGHT IN SESSION--->",
						unitRoundingVO);
				maintainCCASession.setWeightRoundingVO(unitRoundingVO);
				unitRoundingVO = UnitFormatter.getStationDefaultUnit(
						stationCode, UnitConstants.VOLUME);
				log.log(Log.FINE,
						"\n\n<----UNIT ROUNDING VO FOR VOLUME IN SESSION--->",
						unitRoundingVO);
				maintainCCASession.setVolumeRoundingVO(unitRoundingVO);
				
			   }catch(UnitException unitException) {
					unitException.getErrorCode();
			   }
			
		}


}
