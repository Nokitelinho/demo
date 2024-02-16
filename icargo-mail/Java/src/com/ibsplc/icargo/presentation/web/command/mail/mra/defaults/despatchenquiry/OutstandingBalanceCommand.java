/*
 * OutstandingBalanceCommand.java Created on Jan 9,2009
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.despatchenquiry;

import static com.ibsplc.xibase.server.framework.vo.ErrorDisplayType.ERROR;

import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNPopUpVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.OutstandingBalanceVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.DSNPopUpSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.DespatchEnqSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.DespatchEnquiryForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3434
 *
 */

public class OutstandingBalanceCommand extends BaseCommand{
	private  Log log = LogFactory.getLogger("MRA DEFAULTS DespatchEnquiry");

	private static final String CLASS_NAME = "OutstandingBalanceCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.despatchenquiry";

	private static final String SCREEN_SUCCESS = "screenload_success";
	
	private static final String LIST_FAILURE = "list_failure";
	
	private static final String SCREEN_ID = "mailtracking.mra.defaults.dsnselectpopup";
	
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
		DSNPopUpSession popUpSession=getScreenSession(MODULE_NAME,SCREEN_ID);
		DespatchEnquiryForm despatchEnquiryForm=(DespatchEnquiryForm)invocationContext.screenModel;
		DespatchEnqSession despatchEnqSession=getScreenSession(MODULE_NAME,SCREENID);
		MailTrackingMRADelegate delegate=new MailTrackingMRADelegate();
		Collection<OutstandingBalanceVO> outstandingBalanceVOs = new ArrayList<OutstandingBalanceVO>();
		OutstandingBalanceVO outstandingBalanceVO=new OutstandingBalanceVO();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		DSNPopUpVO popUpVO = null;
		popUpVO = popUpSession.getSelectedDespatchDetails();
		log.log(Log.FINE, "popUpVO-----  ", popUpVO);
		if(popUpVO == null) {
			popUpVO = despatchEnqSession.getDispatchFilterVO();
		}
		if(popUpVO != null) {
			despatchEnquiryForm.setDespatchNum(popUpVO.getBlgBasis());
			despatchEnquiryForm.setDsnFilterDate(popUpVO.getDsnDate());
			outstandingBalanceVO.setCompanycode(popUpVO.getCompanyCode());
			outstandingBalanceVO.setBillingBasis(popUpVO.getBlgBasis());
			outstandingBalanceVO.setPoaCode(popUpVO.getGpaCode());
			outstandingBalanceVO.setConsignmentDocumentNumber(popUpVO.getCsgdocnum());
			outstandingBalanceVO.setConsignmentSequenceNumber(popUpVO.getCsgseqnum());
			outstandingBalanceVO.setSubSystem("M");
		}
		try {
			outstandingBalanceVOs=delegate.findOutstandingBalances(outstandingBalanceVO);
		} catch (BusinessDelegateException e) {
			errors=handleDelegateException(e);
		}// TODO Auto-generated catch block
		if(outstandingBalanceVOs==null || outstandingBalanceVOs.size()==0){
			log.log(Log.FINE,"!!!inside resultList== null");
			ErrorVO errorVO = new ErrorVO(
			"mailtracking.mra.defaults.despatchenquiry.outstanding.nodetails");
			errorVO.setErrorDisplayType(ERROR);
			errors.add(errorVO);
				
			}
		if(errors != null && errors.size() > 0){
			invocationContext.addAllError(errors);					
			invocationContext.target = LIST_FAILURE;
		}
		else{
		despatchEnqSession.setOutStandingBalances(outstandingBalanceVOs);
		invocationContext.target=SCREEN_SUCCESS;
		}
	}
	
}

