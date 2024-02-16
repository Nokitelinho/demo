/**
 * ClearCommand.java Created on March 06, 2019
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpareporting.ux.generateandlistclaim;

import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpareporting.ux.GenerateandListClaimSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting.ux.GenerateandListClaimForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class ClearCommand  extends BaseCommand {
	
	private Log log = LogFactory.getLogger("Mail Mra generateandlistclaim ClearCommand ");
	private static final String TARGET = "clear_success";
	private static final String MODULE_NAME = "mail.mra";
	private static final String SCREENID = "mail.mra.gpareporting.ux.generateandlistclaim";

	
	@Override
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("clearCommand of generateandListClaim", "execute");
		GenerateandListClaimForm generateandListClaimForm = (GenerateandListClaimForm) invocationContext.screenModel;
		invocationContext.target = TARGET;
		GenerateandListClaimSession listclaimsession =getScreenSession(MODULE_NAME,SCREENID);
		
		   LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		    String companyCode = logonAttributes.getCompanyCode();
		    String stationCode = logonAttributes.getStationCode();
		    String userId = logonAttributes.getUserId();
		    this.log.log(3, new Object[] { "companyCode =  ", companyCode });
		    this.log.log(3, new Object[] { "stationCode =  ", stationCode });
		    this.log.log(3, new Object[] { "userId =  ", userId });
		    generateandListClaimForm.setCompanycode(companyCode);
		    generateandListClaimForm.setFromDate("");
		    generateandListClaimForm.setToDate("");
		    generateandListClaimForm.setPaCode("");
		    generateandListClaimForm.setActionFlag("CLEAR");
		    listclaimsession.setTotalRecords(0);
		    listclaimsession.setFilterParamValues(null);
		    listclaimsession.setListclaimbulkvos(null);

		log.exiting("clearCommand","execute");
	}
	

}