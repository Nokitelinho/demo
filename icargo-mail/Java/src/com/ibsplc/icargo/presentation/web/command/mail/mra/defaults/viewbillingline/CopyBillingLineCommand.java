/*
 * CopyBillingLineCommand.java Created on Mar 12, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.viewbillingline;

import java.util.ArrayList;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateLineVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.CopyBlgLineSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainBillingMatrixSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ViewBillingLineSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ViewBillingLineForm;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author A-2391
 *
 */
public class CopyBillingLineCommand extends BaseCommand {
	private  Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "ClearCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.viewbillingline";
	private static final String SCREEN_ID_BM = "mailtracking.mra.defaults.maintainbillingmatrix";
	private static final String SCREEN_ID = "mailtracking.mra.defaults.copyblgline";
	private static final String OPEN_COPYBLGLINE = "open_copyblgline";
	private static final String BLGLINE_STATUS_NEW="N";

	
	/*
	 *  (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
    throws CommandInvocationException {
    log.entering(CLASS_NAME,"execute");
    ViewBillingLineForm form = (ViewBillingLineForm) invocationContext.screenModel;
    ViewBillingLineSession session = (ViewBillingLineSession) getScreenSession(
			MODULE_NAME, SCREENID);
	MaintainBillingMatrixSession maintainBMsession = (MaintainBillingMatrixSession) getScreenSession(
			MODULE_NAME, SCREEN_ID_BM);
	log.log(Log.INFO,"session1 obtained");	
	
	CopyBlgLineSession copyLineSession=getScreenSession(MODULE_NAME,SCREEN_ID);
	log.log(Log.INFO,"session2 obtained");	
	Page<BillingLineVO> billingLineVOs = session.getBillingLineDetails();
	//String[] rowId = session.getRowId();
	String[] rowId=form.getCheckboxes();
	//log.log(Log.INFO,"row id in changestatus command" +form.getCheck());
	String[] actualRowIds=rowId[0].split(",");
	String selectedId = null;
	ArrayList<BillingLineVO> selBillingLineVOs=new ArrayList<BillingLineVO>();
	BillingLineFilterVO billingLineFilterVO = null;
	ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
	LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
	String companyCode = logonAttributes.getCompanyCode();
	if(rowId != null && billingLineVOs != null){
		log.log(Log.INFO, "rowid length-->", actualRowIds.length);
		try {
		for(int i=0; i < actualRowIds.length; i++){
			selectedId = actualRowIds[i];
		//	log.log(Log.INFO,"selected ID in "+i+"th loop"+selectedId);
			BillingLineVO billingLineVO=new BillingLineVO();
			
			
				BeanHelper.copyProperties(billingLineVO,billingLineVOs.get(Integer.parseInt( selectedId )));
			//rateLineVO.setRatelineSequenceNumber();
				billingLineVO.setOperationFlag(RateLineVO.OPERATION_FLAG_INSERT);
				billingLineVO.setBillingLineStatus(BLGLINE_STATUS_NEW);
				selBillingLineVOs.add(billingLineVO);

		}
		} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.getMessage();
			} catch (SystemException e) {
				// TODO Auto-generated catch block
				e.getMessage();
			}

}
	form.setCopyFlag("COPY");
	log.log(Log.INFO, "selected RateLines passing to copy rate popup-->",
			selBillingLineVOs);
/*	billingLineFilterVO =
			populateFilterVO(companyCode,form,invocationContext);*/
	//session.setBillingLineFilterVO(billingLineFilterVO); 
	copyLineSession.setSelectedBlgLines(selBillingLineVOs);
	maintainBMsession.setParentScreenId(SCREEN_ID_BM);
	invocationContext.target=OPEN_COPYBLGLINE;


}


}
