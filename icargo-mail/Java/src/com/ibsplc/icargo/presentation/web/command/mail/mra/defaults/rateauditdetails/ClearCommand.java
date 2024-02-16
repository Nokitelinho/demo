/*
 * ClearCommand.java Created on Jul 17,2008
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.rateauditdetails;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ListRateAuditSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.RateAuditDetailsSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.RateAuditDetailsForm;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2391
 *
 */
public class ClearCommand extends BaseCommand{

	private Log log = LogFactory.getLogger("MRA DEFAULTS CLEARCOMMAND");
	private static final String CLASS_NAME = "ClearCommand";

	private static final String MODULE = "mailtracking.mra.defaults";
	private static final String SCREENID = "mailtracking.mra.defaults.rateauditdetails";
	private static final String CLEAR_SUCCESS="clear_success";
	private static final String SAVE_SUCCESS_INFO = "mailtracking.mra.defaults.rateauditdetails.saveinfo";
	private static final String SCREENID_LISTRATEAUDIT ="mailtracking.mra.defaults.listrateaudit";
	/*
	 *  (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		log.entering(CLASS_NAME,"execute");
		RateAuditDetailsForm rateAuditDetailsForm=(RateAuditDetailsForm)invocationContext.screenModel;
		RateAuditDetailsSession session=getScreenSession(MODULE,SCREENID);
		
		 ListRateAuditSession listRateAuditSession = getScreenSession(MODULE, SCREENID_LISTRATEAUDIT);
		
		rateAuditDetailsForm.setDsnNumber("");
		rateAuditDetailsForm.setDsnDate("");
		rateAuditDetailsForm.setDsnStatus("");
		rateAuditDetailsForm.setOrigin("");
		rateAuditDetailsForm.setDestination("");
		rateAuditDetailsForm.setGpaCode("");
		rateAuditDetailsForm.setConsignmentDocNo("");
		rateAuditDetailsForm.setRoute("");
		rateAuditDetailsForm.setUpdWt("");
		rateAuditDetailsForm.setCategory("");
		rateAuditDetailsForm.setSubClass("");
		rateAuditDetailsForm.setULD("");
		rateAuditDetailsForm.setFlightCarCod("");
		rateAuditDetailsForm.setFlightNo("");
		rateAuditDetailsForm.setAuditWgtCharge("");
		rateAuditDetailsForm.setDiscrepancy("");
		rateAuditDetailsForm.setApplyAudit("");
		rateAuditDetailsForm.setListFlag("N");
		rateAuditDetailsForm.setBillTo("");
		rateAuditDetailsForm.setFromScreen("");
		session.removeParChangeFlag();		
		session.removeBillToChgFlag();
		session.removeRateAuditVO();
		listRateAuditSession.removeRateAuditVOs();
	
		if(session.getStatusinfo()!=null&&"SAVE".equals(session.getStatusinfo())){
			invocationContext.addError(new ErrorVO(SAVE_SUCCESS_INFO));
		}
		session.removeStatusinfo();
		 invocationContext.target=CLEAR_SUCCESS;
		log.exiting(CLASS_NAME,"execute");

	}


}
