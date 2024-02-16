/*
 * ViewBillingLineCommand.java Created on Mar 23, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms. 
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.listbillingmatrix;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingMatrixVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
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
public class ViewBillingLineCommand extends BaseCommand{

	private  Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "ViewBillingLineCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.listbillingmatrix";
	private static final String SCREENID_BILLINGLINE="mailtracking.mra.defaults.viewbillingline";

	private static final String OPEN_VIEWBILLINGLINE = "open_viewbillingline";
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException 
	 */
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		log.entering(CLASS_NAME,"execute");
		ListBillingMatrixForm listBillingMatrixForm=(ListBillingMatrixForm)invocationContext.screenModel;
		ListBillingMatrixSession listBillingMatrixSession=getScreenSession(MODULE_NAME,SCREENID);
		ViewBillingLineSession billingLineSession=getScreenSession(MODULE_NAME,SCREENID_BILLINGLINE);
		
		LogonAttributes logonAttributes=getApplicationSession().getLogonVO();
		
		String selectedId=listBillingMatrixForm.getSelectedIndexes();
		Page<BillingMatrixVO> billingMatrixpage=listBillingMatrixSession.getBillingMatrixVOs();
		BillingMatrixVO billingMatrixVO=null;
		billingMatrixVO=billingMatrixpage.get(Integer.parseInt(selectedId));
		if(billingMatrixVO!=null){
			BillingLineFilterVO billingLineFilterVO=new BillingLineFilterVO();
			billingLineFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
			billingLineFilterVO.setBillingMatrixId(billingMatrixVO.getBillingMatrixId());			
			billingLineFilterVO.setValidityStartDate(billingMatrixVO.getValidityStartDate());
			billingLineFilterVO.setValidityEndDate(billingMatrixVO.getValidityEndDate());
			billingLineSession.setBillingLineFilterVO(billingLineFilterVO);
			billingLineSession.setParentScreenId(SCREENID);				
			
		}
		invocationContext.target=OPEN_VIEWBILLINGLINE;
		log.exiting(CLASS_NAME,"execute");
		
		
	}

}
