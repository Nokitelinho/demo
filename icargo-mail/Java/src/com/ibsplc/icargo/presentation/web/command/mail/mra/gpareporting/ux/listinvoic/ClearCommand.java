package com.ibsplc.icargo.presentation.web.command.mail.mra.gpareporting.ux.listinvoic;

import java.util.Collection;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting.ux.ListInvoicForm;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpareporting.ux.ListInvoicSession;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;

public class ClearCommand  extends BaseCommand {
	
	private Log log = LogFactory.getLogger("Mail Mra Invoic Listing ");
	private static final String TARGET = "clear_success";
	private static final String MODULE_NAME = "mail.mra";
	private static final String SCREENID = "mail.mra.gpareporting.ux.listinvoic";

	
	@Override
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("clearCommand of List invoic", "execute");
		ListInvoicForm listInvoicForm = (ListInvoicForm) invocationContext.screenModel;
		invocationContext.target = TARGET;
		ListInvoicSession listinvoicsession =getScreenSession(MODULE_NAME,SCREENID);
		
		   LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		    String companyCode = logonAttributes.getCompanyCode();
		    String stationCode = logonAttributes.getStationCode();
		    String userId = logonAttributes.getUserId();
		    this.log.log(3, new Object[] { "companyCode =  ", companyCode });
		    this.log.log(3, new Object[] { "stationCode =  ", stationCode });
		    this.log.log(3, new Object[] { "userId =  ", userId });
		    listInvoicForm.setCompanycode(companyCode);
		    listInvoicForm.setFromDate("");
		    listInvoicForm.setToDate("");
		    listInvoicForm.setPaCode("");
		    listInvoicForm.setFileName("");
			listInvoicForm.setInvoicRefId("");
		    listInvoicForm.setStatus("ALL");
			listinvoicsession.setTotalRecords(0);
			listinvoicsession.setFilterParamValues(null);
			listinvoicsession.setListinvoicvos(null);

		log.exiting("clearCommand","execute");
	}
	

}