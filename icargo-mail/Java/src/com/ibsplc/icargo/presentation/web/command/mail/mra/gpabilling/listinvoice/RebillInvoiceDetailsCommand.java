/**
 *
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.listinvoice;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.cra.agentbilling.defaults.vo.ReminderDetailsFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51SummaryVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.cra.agentbilling.defaults.ReminderListSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.ListGPABillingInvoiceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.ListGPABillingInvoiceForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5219
 *
 */
public class RebillInvoiceDetailsCommand extends BaseCommand {

	private  Log log = LogFactory.getLogger("MRA GPABILLING");

	private static final String CLASS_NAME = "RebillInvoiceDetailsCommand";

	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";

	private static final String SCREEN_ID = "mailtracking.mra.gpabilling.listgpabillinginvoice";

	private static final String CRA_MODULENAME = "cra.agentbilling";

	private static final String CRA_SCREEN_ID = "cra.agentbilling.defaults.reminderlist";

	private static final String GPA_BILLING="GB";

	private static final String PARENT_SCREEN="listgpabillinginvoice";

	private static final String SUCCESS = "success";


	public void execute(InvocationContext invocationContext)  throws CommandInvocationException {

		log.entering(CLASS_NAME,"execute");
		ListGPABillingInvoiceForm listGPABillingInvoiceForm = (ListGPABillingInvoiceForm) invocationContext.screenModel;
		ListGPABillingInvoiceSession listGPABillingInvoiceSession = (ListGPABillingInvoiceSession) getScreenSession(MODULE_NAME, SCREEN_ID);
		ReminderDetailsFilterVO reminderDetailsFilterVO = new ReminderDetailsFilterVO();
		ReminderListSession reminderDetailsSession = getScreenSession(CRA_MODULENAME,
				CRA_SCREEN_ID);
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String counter = listGPABillingInvoiceForm.getSelectedRow();
		String parent = counter.split("~")[0];
		String child = counter.split("~")[1];
		Collection<CN51SummaryVO> cnSummaryVOs = listGPABillingInvoiceSession
		.getCN51SummaryVOs();
		ArrayList<CN51SummaryVO> cnSummaryVOArraylist = new ArrayList<CN51SummaryVO>(
				cnSummaryVOs);
		reminderDetailsFilterVO.setTypeOfBilling(GPA_BILLING);
		reminderDetailsFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		if(counter != null && counter.trim().length() > 0){
			CN51SummaryVO cnSummaryVO = cnSummaryVOArraylist.get(Integer.parseInt(parent));
			ArrayList<CN51SummaryVO> rebillList = new ArrayList<CN51SummaryVO>(
					cnSummaryVO.getRebillInvoiceDetails());
			cnSummaryVO = rebillList.get(Integer.parseInt(child));
			reminderDetailsFilterVO.setInvoiceNumber(cnSummaryVO.getInvoiceNumber());
			reminderDetailsFilterVO.setSerialNumber(cnSummaryVO.getInvSerialNumber());
			reminderDetailsFilterVO.setGpaCode(cnSummaryVO.getGpaCode());
			reminderDetailsFilterVO.setGpaRebillRound(String.valueOf(cnSummaryVO.getRebillRound()));
		}
		reminderDetailsSession.setParentScreen(PARENT_SCREEN);
		reminderDetailsSession.setReminderDetailsFilterVO(reminderDetailsFilterVO);
		invocationContext.target = SUCCESS;

	}


}
