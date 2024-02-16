/*
 * ClearCommand.java Created on Jan 19, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.cn51cn66;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.ListCN51CN66Session;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.ListCN51CN66Form;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1556
 * @author a-2270
 */
public class ClearCommand extends BaseCommand {

	private  Log log = LogFactory.getLogger("MRA GPABILLING");

	private static final String CLASS_NAME = "ClearCommand";

	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";

	private static final String SCREENID = "mailtracking.mra.gpabilling.listcn51cn66";

	private static final String CLEAR_SUCCESS = "clear_success";


	private static final String BLANK = "";

	/**
	 * Method to implement the clear operation
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	/* (non-Javadoc)
     * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
     */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering(CLASS_NAME,"execute");
    	ListCN51CN66Form form = (ListCN51CN66Form)invocationContext.screenModel;
    	ListCN51CN66Session session = (ListCN51CN66Session)getScreenSession(MODULE_NAME, SCREENID);

    	// clearing vos in session
    	session.removeCN51CN66VO();
    	session.removeCN66VOs();
    	session.removeCN51CN66FilterVO();
    	session.removeCN51DetailsVOs();
    	session.setCN51CN66FilterVO(null);
    	
    	//session.removeAllAttributes();

    	// clearing form fields

    	form.setInvoiceNumber(BLANK);
    	form.setGpaCode(BLANK);
    	form.setGpaName(BLANK);
    	form.setAirlineCode(getApplicationSession().getLogonVO().getOwnAirlineCode());
    	// clearing filter fields in cn66 Tab
    	form.setCategory(BLANK);
    	form.setOrigin(BLANK);
    	form.setDestination(BLANK);
    	form.setInvokingScreen(BLANK);
    	form.setBtnStatus("N");
    	form.setDisplayPage("1");
    	form.setDisplayPageCN66("1");
		form.setFileName(BLANK);
    	form.setGpaType(BLANK);
    	form.setInvStatusDesc(BLANK);
    	invocationContext.target = CLEAR_SUCCESS;
    }

}



