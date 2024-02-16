package com.ibsplc.icargo.presentation.web.command.mail.mra.gpareporting.ux.claimDetails;

import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpareporting.ux.ClaimDetailsSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting.ux.ClaimDetailsForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class ClearCommand  extends BaseCommand {
	
	private Log log = LogFactory.getLogger("Mail Mra claimDetails ClearCommand  ");
	private static final String TARGET = "clear_success";
	private static final String MODULE_NAME = "mail.mra";
	private static final String SCREENID = "mail.mra.gpareporting.ux.claimDetails";

	
	@Override
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("clearCommand of List invoic", "execute");
		ClaimDetailsForm claimDetailsForm= (ClaimDetailsForm) invocationContext.screenModel;
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
		    claimDetailsForm.setFromDate("");
		    claimDetailsForm.setToDate("");
		    claimDetailsForm.setPaCode("");
		    claimDetailsForm.setMailId("");
		    claimDetailsForm.setStatus("ALL");
		    claimDetailsForm.setClaimtype("");
		    claimDetailsForm.setClaimFileName("");
			claimDetailsForm.setActionFlag("CLEAR");
		    claimDetailsSession.setTotalRecords(0);
		    claimDetailsSession.setFilterParamValues(null);
		    claimDetailsSession.setListclaimdtlsvos(null);

		log.exiting("clearCommand","execute");
	}
	

}