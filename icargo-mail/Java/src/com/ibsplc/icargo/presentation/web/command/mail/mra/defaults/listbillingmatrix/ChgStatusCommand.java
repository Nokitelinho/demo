/*
 * ScreenLoadChgStatusCommand.java created on Mar 1, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.  
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.listbillingmatrix;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingMatrixFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingMatrixVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ListBillingMatrixSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ViewBillingLineSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListBillingMatrixForm;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2280
 *
 */
public class ChgStatusCommand extends BaseCommand{

	private static final Log LOG = LogFactory.getLogger("MRA:DEFAULTS");

	private static final String CLASS_NAME = "ScreenLoadCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.listbillingmatrix";

	private static final String SCREEN_SUCCESS = "popup_success";
	
	private static final String SCREENID_BILLINGLINE="mailtracking.mra.defaults.viewbillingline";
	/*
	 *  (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		LOG.entering(CLASS_NAME,"execute");
		
		ListBillingMatrixForm listBillingMatrixForm=(ListBillingMatrixForm)invocationContext.screenModel;
		ListBillingMatrixSession listBillingMatrixSession=getScreenSession(MODULE_NAME,SCREENID);
		ViewBillingLineSession billingLineSession=getScreenSession(MODULE_NAME,SCREENID_BILLINGLINE);
		BillingMatrixFilterVO blgMatrixFilterVO = listBillingMatrixSession.getBillingMatrixFilterVO();
		Page<BillingMatrixVO> billingMatrixpage=listBillingMatrixSession.getBillingMatrixVOs();
		BillingMatrixVO billingMatrixVO=null;
		billingMatrixVO=billingMatrixpage.get(Integer.parseInt(listBillingMatrixForm.getSelectedIndexes()));
	
			BillingLineFilterVO billingLineFilterVO=new BillingLineFilterVO();
			blgMatrixFilterVO.setBillingMatrixId(billingMatrixVO.getBillingMatrixId());
			billingLineFilterVO.setBillingMatrixId(billingMatrixVO.getBillingMatrixId());			
			billingLineFilterVO.setValidityStartDate(billingMatrixVO.getValidityStartDate());
			billingLineFilterVO.setValidityEndDate(billingMatrixVO.getValidityEndDate());
			billingLineFilterVO.setBillingLineStatus(billingMatrixVO.getBillingMatrixStatus());
			billingLineSession.setBillingLineFilterVO(billingLineFilterVO);
			billingLineSession.setBillingLineFilterVO(billingLineFilterVO);
			listBillingMatrixSession.setBillingMatrixFilterVO(blgMatrixFilterVO);
			billingLineSession.setParentScreenId(SCREENID);				
	
		listBillingMatrixForm.getSelectedIndexes();
		LOG.log(Log.INFO, "New selectedStatus value---->>", listBillingMatrixForm.getSelectedIndexes());
		listBillingMatrixForm.getStatus();
		LOG.log(Log.INFO, "New selectedStatus value---->>", listBillingMatrixForm.getStatus());
	
	
	
		
		invocationContext.target=SCREEN_SUCCESS;
		LOG.exiting(CLASS_NAME,"execute");
		
		
	}


}
