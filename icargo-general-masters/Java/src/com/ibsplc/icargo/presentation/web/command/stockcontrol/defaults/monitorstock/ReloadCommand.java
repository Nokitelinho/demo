package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.monitorstock;

import java.util.Objects;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockFilterVO;
import com.ibsplc.icargo.framework.security.SecurityAgent;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.MonitorStockSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.MonitorStockForm;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *
 * @author A-1865
 *
 */
public class ReloadCommand extends BaseCommand{
	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	private static final String RELOAD_SUCCESS = "reload_success";
	private static final String TRUE = "true";
	private static final String FALSE = "false";
	private static final String DELETE_STOCK_PRIVILEGE = "stockcontrol.defaults.deletestock.delete";
	/**
	 * The execute method in BaseCommand
	 * 
	 * @author A-1865
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)throws CommandInvocationException {
		log.entering("\n\nReloadCommand", "execute");
		
		MonitorStockForm form = (MonitorStockForm) invocationContext.screenModel;
		MonitorStockSession session = getScreenSession("stockcontrol.defaults", "stockcontrol.defaults.monitorstock");
		reloadDetails(session,form);
		if(form.getPartnerPrefix()==null ||form.getPartnerPrefix().isEmpty()) {
		 LogonAttributes logon = null;
			try {
				logon = ContextUtils.getSecurityContext().getLogonAttributesVO();
			} catch (SystemException e) {
				log.log(Log.SEVERE,e);
			}
			if(Objects.nonNull(logon)){
				form.setPartnerPrefix(logon.getOwnAirlineNumericCode());
			}
		}
		
		log.exiting("ReloadCommand", "execute");
		invocationContext.target = RELOAD_SUCCESS;
	}
	private void reloadDetails(MonitorStockSession session,MonitorStockForm form){
		StockFilterVO stockFilterVO = session.getStockFilterDetails();
		if(stockFilterVO != null){
			log.log(Log.INFO,"\n\n\n FILTER DETAILS IS THER IN THE SESSION");
			form.setStockHolderType(stockFilterVO.getStockHolderType());
			form.setStockHolderCode(stockFilterVO.getStockHolderCode());
			form.setDocType(stockFilterVO.getDocumentType());
			form.setSubType(stockFilterVO.getDocumentSubType());
		}
		//populating privilege for delete button
		populatePrivilege(form);
	}
	
	/**
	 * A-5642
	 * @param form
	 */
	private void populatePrivilege(MonitorStockForm form) {
		try {
			log.log(Log.FINE, "populatePrivilege");
			boolean hasDeleteStockPrivilege = SecurityAgent.getInstance()
			.checkBusinessPrivilege(DELETE_STOCK_PRIVILEGE);
			if (hasDeleteStockPrivilege) {
				form.setDeleteButtonPrivilege(TRUE);	
			} else {
				form.setDeleteButtonPrivilege(FALSE);	
			}
		} catch (SystemException e1) {
			log.log(Log.SEVERE, "Exception populatePrivilege");
		}
	}
}