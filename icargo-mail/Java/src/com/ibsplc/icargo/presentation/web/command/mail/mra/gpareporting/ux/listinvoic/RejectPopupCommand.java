package com.ibsplc.icargo.presentation.web.command.mail.mra.gpareporting.ux.listinvoic;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpareporting.ux.ListInvoicSession;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicVO;
public class RejectPopupCommand extends BaseCommand {

	private static final String MODULE_NAME = "mail.mra";
	private static final String SCREENID = "mail.mra.gpareporting.ux.listinvoic";
	private static final String TARGET = "process_success";
	private Log log = LogFactory.getLogger("List invoic Screen ");

	

	@Override
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		 invocationContext.target = "list_success";
		 ListInvoicSession listinvoicsession =getScreenSession(MODULE_NAME,SCREENID);
		 Page <InvoicVO> invoicVOs=listinvoicsession.getListinvoicvos();
		 listinvoicsession.setListinvoicvos(invoicVOs);
		 String value=listinvoicsession.getSelectedRecords();
		 listinvoicsession.setSelectedRecords(value);
			
	}
	

}
