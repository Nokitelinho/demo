/*
 * CN66TabClearCommand.java Created on Jan 10, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.cn51cn66;



import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51CN66VO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51DetailsVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.ListCN51CN66Session;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.ListCN51CN66Form;

/**
 * @author A-1556
 * @author A-2270
 * 
 */
public class CN66TabClearCommand extends BaseCommand {

	private  Log log = LogFactory.getLogger("MRA GPABILLING");

	private static final String CLASS_NAME = "CN66TabClearCommand";

	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";

	private static final String SCREENID = "mailtracking.mra.gpabilling.listcn51cn66";

	private static final String CLEAR_SUCCESS = "clear_success";

	private static final String CLEAR_FAILURE = "clear_failure";

	private static final String BLANK = "";

	/**
	 * Method to implement the clear operation
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering(CLASS_NAME,"execute");

    	ListCN51CN66Form form = (ListCN51CN66Form)invocationContext.screenModel;
    	CN51CN66VO cN51CN66Vo 	= new CN51CN66VO();
    	ListCN51CN66Session session = (ListCN51CN66Session)getScreenSession(MODULE_NAME, SCREENID);
    	if (session.getCN51CN66VO() != null) {
			cN51CN66Vo = session.getCN51CN66VO();
			if (cN51CN66Vo.getCn51DetailsVOs() != null
					&& cN51CN66Vo.getCn51DetailsVOs().size() > 0) {
				for (CN51DetailsVO cn51VO : cN51CN66Vo.getCn51DetailsVOs()) {
					if (cn51VO.getTotalBilledAmount() != null) {
						form.setTotalBilledAmount(cn51VO.getTotalBilledAmount()
								.getAmount());
					}
				}
			}
		}

    	// clearing vos in session
    		session.removeCN66VOs();
    	// clearing form fields
    		form.setDestination(BLANK);
    		form.setOrigin(BLANK);
    		form.setCategory(BLANK);
    		form.setDsnNumber(BLANK);
    		form.setSaveBtnStatus("N");
    		session.getCN51CN66FilterVO().setCategory(null);
    		session.getCN51CN66FilterVO().setOrgin(null);
    		session.getCN51CN66FilterVO().setDestination(null);  
    		session.getCN51CN66FilterVO().setDsnNumber(null);
    	invocationContext.target = CLEAR_SUCCESS;
    }

}
