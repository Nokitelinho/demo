/*
 * RateAuditDetailsScreenLoadCommand.java Created on Jul 17, 2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.rateauditdetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
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
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.RateAuditDetailsSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.RateAuditDetailsForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 * @author A-2391
 *
 */
public class RateAuditDetailsScreenLoadCommand  extends BaseCommand {
	
	private Log log = LogFactory.getLogger("RATE AUDITDETAILS RateAuditDetailsScreenLoad");
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
	private static final String MODULE = "mailtracking.mra.defaults";
	private static final String SCREENID = "mailtracking.mra.defaults.rateauditdetails";
	private static final String NO = "N";
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	 public void execute(InvocationContext invocationContext)
     throws CommandInvocationException {
		 
		 log.entering("RateAuditDetailsScreenLoad", "execute");
		 ApplicationSessionImpl applicationSession = getApplicationSession();
			LogonAttributes logonAttributes = applicationSession.getLogonVO();
		 RateAuditDetailsForm rateAuditDetailsForm=(RateAuditDetailsForm)invocationContext.screenModel;
		 RateAuditDetailsSession session=getScreenSession(MODULE,SCREENID);
		 session.removeAllAttributes();
		 rateAuditDetailsForm.setListFlag(NO);
		 session.setParChangeFlag(NO);
		 
		 /*
			 * Getting OneTime values
			 */
		 
		 Map<String, Collection<OneTimeVO>> oneTimes = null;
			Collection<ErrorVO> errors = null;
			try{
				Collection<String> fieldValues = new ArrayList<String>();
				
				fieldValues.add("mailtracking.defaults.mailcategory");				
				oneTimes = new SharedDefaultsDelegate().findOneTimeValues(logonAttributes.getCompanyCode(),fieldValues) ;
			}catch(BusinessDelegateException businessDelegateException){
				errors = handleDelegateException(businessDelegateException);
			}			
			if(oneTimes!=null){
				Collection<OneTimeVO> catVOs = oneTimes.get("mailtracking.defaults.mailcategory");
				session.setOneTimeCatVOs(catVOs);
			}
		 
			/**
			 * @author A-2554
			 * to implement rounding for weight & volume
			 */
			UnitRoundingVO unitRoundingVO = new UnitRoundingVO();
			session.setWeightRoundingVO(unitRoundingVO);
			session.setVolumeRoundingVO(unitRoundingVO);		
			setUnitComponent(logonAttributes.getStationCode(),session); 
		 
		 log.exiting("RateAuditDetailsScreenLoad", "execute");
		 
		 invocationContext.target = SCREENLOAD_SUCCESS;
		 
	 }

	 
	 	/**
		 * @author A-2554
		 * @param stationCode
		 * @param maintainCCASession
		 */
		private void setUnitComponent(String stationCode,
				RateAuditDetailsSession rateAuditDetailsSession){
				UnitRoundingVO unitRoundingVO = null;
				try{
					log.log(Log.FINE, "\n\n<----STATION CODE IS----------->",
							stationCode);
					unitRoundingVO = UnitFormatter.getStationDefaultUnit(
							stationCode, UnitConstants.WEIGHT);			
					log
							.log(
									Log.FINE,
									"\n\n<----UNIT ROUNDING VO FOR WEIGHT IN SESSION--->",
									unitRoundingVO);
					rateAuditDetailsSession.setWeightRoundingVO(unitRoundingVO);
					unitRoundingVO = UnitFormatter.getStationDefaultUnit(
							stationCode, UnitConstants.VOLUME);
					log
							.log(
									Log.FINE,
									"\n\n<----UNIT ROUNDING VO FOR VOLUME IN SESSION--->",
									unitRoundingVO);
					rateAuditDetailsSession.setVolumeRoundingVO(unitRoundingVO);
					
				   }catch(UnitException unitException) {
						unitException.getErrorCode();
				   }
				
			}

}
