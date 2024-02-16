
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.ux.invoicesettlementmailbag;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPASettlementVO;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.ux.InvoiceSettlementMailbagSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.ux.InvoiceSettlementMailbagForm;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class RemarksCommand extends BaseCommand
{
	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";

	private static final String SCREEN_ID ="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel";

	private static final String REMARKS_SUCCESS = "remarks_success"; 

	 private static final String CLASS_NAME = "RemarksCommand";
	
	@Override
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		
		Log log = LogFactory.getLogger("mailtracking.mra.gpabilling");
		log.entering(CLASS_NAME, "execute"); 
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		InvoiceSettlementMailbagSession invoiceSettlementMailbagSession = (InvoiceSettlementMailbagSession) getScreenSession(MODULE_NAME, SCREEN_ID);
		InvoiceSettlementMailbagForm invoiceSettlementMailbagForm = (InvoiceSettlementMailbagForm) invocationContext.screenModel;
		String remark = "";
		String[] remarks=invoiceSettlementMailbagForm.getRemarks();
		String [] selectedRow= invoiceSettlementMailbagForm.getSelectedIndex();
		Collection<GPASettlementVO>  selectedGPASettlementVOs = invoiceSettlementMailbagSession.getSelectedGPASettlementVO();
		Page<InvoiceSettlementVO> invoiceSettlementVOs=   selectedGPASettlementVOs.iterator().next().getInvoiceSettlementVO();
		if (remarks!=null)
		{
			remark = remarks[0];
			if(invoiceSettlementVOs!=null) {
				for (int i = 0; i < invoiceSettlementVOs.size(); i++) {
					if(i == Integer.parseInt(selectedRow[0])) {
						InvoiceSettlementVO invoiceSettlementVO = (InvoiceSettlementVO) invoiceSettlementVOs.toArray()[i];
						invoiceSettlementVO.setRemarks(remark);
					}
				}
			}
			
			selectedGPASettlementVOs.iterator().next().setInvoiceSettlementVO(invoiceSettlementVOs);
			invoiceSettlementMailbagSession.setSelectedGPASettlementVO(selectedGPASettlementVOs);
			
		}
		invoiceSettlementMailbagForm.setActionFlag("REMARK_CLOSE");	
	invocationContext.target = REMARKS_SUCCESS;
}}

	   

	
	
	
	

