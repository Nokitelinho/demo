/*
 * ScreenLoadCommand.java Created on March 06, 2019
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpareporting.ux.claimDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpareporting.ux.ClaimDetailsSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting.ux.ClaimDetailsForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class ScreenLoadCommand  extends BaseCommand {
	
	private Log log = LogFactory.getLogger("Mail Mra generateandlistclaim Listing ");
	private static final String TARGET = "screenload_success";
	private static final String MODULE_NAME = "mail.mra";
	private static final String SCREENID = "mail.mra.gpareporting.ux.claimDetails";
	//private static final String STATUS_ONETIME="mailtracking.mra.gpareporting.invoicstatus";  Commented as part of ICRD-343866
	private static final String CLAIM_ONETIME="mailtracking.mra.gpareporting.claimtype";
	@Override
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("ScreenloadCommand of claimDetails", "execute");
		ClaimDetailsForm claimDetailsForm = (ClaimDetailsForm) invocationContext.screenModel;
		invocationContext.target = TARGET;
		ClaimDetailsSession claimDetailsSession =getScreenSession(MODULE_NAME,SCREENID);
		
		   LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		    String companyCode = logonAttributes.getCompanyCode();
		    String stationCode = logonAttributes.getStationCode();
		    String userId = logonAttributes.getUserId();
		this.log.log(3, new Object[] { "companyCode =  ", companyCode });
		    this.log.log(3, new Object[] { "stationCode =  ", stationCode });
		    this.log.log(3, new Object[] { "userId =  ", userId });
		    claimDetailsForm.setCompanycode(companyCode);
		    Map <String, Collection<OneTimeVO>>oneTimeValue = getOneTimeValues(companyCode);
		    String defaultSize = "10";
		    claimDetailsForm.setPaCode("");
		    claimDetailsForm.setFromDate("");
		    claimDetailsForm.setToDate("");
		    claimDetailsForm.setStatus("");
		    claimDetailsForm.setMailId("");
		    claimDetailsForm.setClaimtype("");
		    claimDetailsForm.setTotalRecords("0");
		    claimDetailsForm.setDefaultPageSize(defaultSize);
		   /* Collection<OneTimeVO> statusOneTime = (Collection<OneTimeVO>) oneTimeValue
					.get(STATUS_ONETIME);*/    //Commented as part of ICRD-343866
		    Collection<OneTimeVO> claimOneTime = (Collection<OneTimeVO>) oneTimeValue
					.get(CLAIM_ONETIME);
		 /*	if (statusOneTime != null) {
				log.log(Log.INFO, "Sizeee----", statusOneTime.size());
				for (OneTimeVO list : statusOneTime) {
					log.log(Log.INFO, "LIST----------", list.getFieldDescription());
				}
			}*/          // Commented as part of ICRD-343866
			if(claimOneTime!=null){
				log.log(Log.INFO, "Claim Type Sizeee----", claimOneTime.size());
				for (OneTimeVO list : claimOneTime) {
					log.log(Log.INFO, "claimOneTime Desc----------:", list.getFieldDescription());
				}	
			}
			//claimDetailsSession.setStatus((ArrayList<OneTimeVO>)statusOneTime);    //Commented as part of ICRD-343866
			claimDetailsSession.setClaimType((ArrayList<OneTimeVO>)claimOneTime);
		    claimDetailsSession.setTotalRecords(Integer.parseInt(claimDetailsForm.getTotalRecords()));
		    claimDetailsSession.setFilterParamValues(null);
		    claimDetailsSession.setListclaimdtlsvos(null);
		log.exiting("ScreenloadCommand","execute");
	}
	private Map<String, Collection<OneTimeVO>> getOneTimeValues(String companyCode) {
		Map<String, Collection<OneTimeVO>> oneTimeValues = new HashMap<String, Collection<OneTimeVO>>();
	    Collection <String> oneTimeList = new ArrayList<String>();
	    //oneTimeList.add(STATUS_ONETIME);  //Commented as part of ICRD-343866
	    oneTimeList.add(CLAIM_ONETIME); 
	
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		try {

			oneTimeValues = sharedDefaultsDelegate.findOneTimeValues(companyCode,oneTimeList);
		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
			handleDelegateException(businessDelegateException);
		}
		return  oneTimeValues;
	}


}