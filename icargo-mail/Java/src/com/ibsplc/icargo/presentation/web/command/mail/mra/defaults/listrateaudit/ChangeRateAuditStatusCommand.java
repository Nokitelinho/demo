/*
 * ChangeRateAuditStatusCommand.java created on July 16,2008
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.  
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.listrateaudit;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ListRateAuditSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListRateAuditForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author A-3108
 *
 */
public class ChangeRateAuditStatusCommand  extends BaseCommand{
	
	
	private  Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "ChangeRateAuditStatusCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID =
		"mailtracking.mra.defaults.listrateaudit";

	private static final String SCREEN_SUCCESS = "rateaudit_success";
	private static final String BLANK = "";
	private static final String COMMA = ",";
	
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		log.entering(CLASS_NAME,"execute");
		ListRateAuditForm listRateAuditForm=(ListRateAuditForm)invocationContext.screenModel;
		ListRateAuditSession listRateAuditSession = getScreenSession(
				MODULE_NAME, SCREENID);
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		MailTrackingMRADelegate mailTrackingMRADelegate = new MailTrackingMRADelegate();			
		if(listRateAuditSession.getRateAuditVOs()!=null){
		ArrayList<RateAuditVO> listRateAuditVOs=new ArrayList<RateAuditVO>(listRateAuditSession.getRateAuditVOs());
		Collection<RateAuditVO> rateAuditVOs=new ArrayList<RateAuditVO>();
		Collection<RateAuditVO> rateAuditVOsForaplyAudit=new ArrayList<RateAuditVO>();
		String[] selectedrows= listRateAuditForm.getSelectedRows().split(COMMA);
		String[] applyAutdits= listRateAuditForm.getApplyAutdits();
		Integer counter=0;
		for(String selectedrow:selectedrows){
			
			//int selrow = Integer.parseInt(selectedrow);
			
			//if("Y".equals(applyAutdits[selrow])){
				rateAuditVOs.add(listRateAuditVOs.get(Integer.parseInt(selectedrow)));
			//}			
			}
			
		
		//Added By A-3434 for AirNZ CR 174
		for(RateAuditVO rateAuditVO:listRateAuditVOs){
			
			rateAuditVO.setLastUpdateUser(logonAttributes.getUserId());
				
			if(counter<applyAutdits.length){
					
				if("Y".equals(applyAutdits[counter])){
				rateAuditVO.setApplyAutd("Y");
				
				rateAuditVOsForaplyAudit.add(rateAuditVO);
				}
				 if("N".equals(applyAutdits[counter])){
					
					rateAuditVO.setApplyAutd("N");
					
					rateAuditVOsForaplyAudit.add(rateAuditVO);
				}
				 
			}
				counter=counter+1;
				
		}
		
			
		log.log(Log.FINE,
				"\n\n selected rateAuditVOs for issuing actual cca -->>",
				rateAuditVOs);
		for(RateAuditVO rateAuditVO:rateAuditVOs){
			rateAuditVO.setFromRateAudit("fromrateaudit");
		}
		
		log.log(Log.FINE, "rateAuditVOsForaplyAudit.... ",
				rateAuditVOsForaplyAudit);
		try {
			mailTrackingMRADelegate.changeRateAuditDsnStatus(rateAuditVOs,rateAuditVOsForaplyAudit);

		} catch (BusinessDelegateException businessDelegateException) {
			handleDelegateException(businessDelegateException);
			log.log(Log.FINE, "caught Exception");
		}			
		
		clearForm(listRateAuditForm);
		listRateAuditSession.removeRateAuditVOs();
		
		invocationContext.target=SCREEN_SUCCESS;
		log.exiting(CLASS_NAME,"execute");
	}
	}
	private void clearForm(ListRateAuditForm form){
		
		form.setDsn(BLANK);
		form.setDsnDate(BLANK);		
		form.setCarrierCode(BLANK);
		form.setFlightNo(BLANK);
		form.setFlightDate(BLANK);
		form.setFromDate(BLANK);
		form.setToDate(BLANK);
		form.setGpaCode(BLANK);
		form.setSubClass(BLANK);
		form.setDsnStatus(BLANK);
		
	}

}
