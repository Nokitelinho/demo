/*
 * SurchargeDetailsCommand.java Created on Jul 17, 2015
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.cn51cn66;


import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51CN66FilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN66DetailsVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.ListCN51CN66Session;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.SurChargeBillingDetailSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.ListCN51CN66Form;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-5255 
 * @version	0.1, Jul 17, 2015
 * 
 *
 */
/**
 * Revision History Revision Date Author Description 0.1 Jul 17, 2015 A-5255
 * First draft
 */

public class SurchargeDetailsCommand extends BaseCommand {
	/**
	 * @author A-5255
	 * @param arg0
	 * @throws CommandInvocationException
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";
	private static final String SCREENID = "mailtracking.mra.gpabilling.listcn51cn66";
	private static final String SURCHARGESCREENID = "mailtracking.mra.gpabilling.surcharge.surchargepopup";
	private static final String ACTION_SUCCESS = "success";
	private static final String EMPTYSTRING = "";

	@Override
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		ListCN51CN66Form listCN51CN66Form = (ListCN51CN66Form) invocationContext.screenModel;
		ListCN51CN66Session listCN51CN66Session = (ListCN51CN66Session) getScreenSession(
				MODULE_NAME, SCREENID);
		SurChargeBillingDetailSession surchargeSession = (SurChargeBillingDetailSession) getScreenSession(
				MODULE_NAME, SURCHARGESCREENID);
		String select = listCN51CN66Form.getSelectedRow();
		CN51CN66FilterVO cn51cn66FilterVO = null;
		Page<CN66DetailsVO> cn66DetailsVOs = listCN51CN66Session.getCN66VOs();
		int selectedIndex = 0;
		if (select != null && select.trim().length() > 0) {
			selectedIndex = Integer.parseInt(select);
			if (cn66DetailsVOs != null && cn66DetailsVOs.size() > 0) {
				CN66DetailsVO cn66DetailsVO = cn66DetailsVOs.get(selectedIndex);
				if (cn66DetailsVO != null) {
					cn51cn66FilterVO = new CN51CN66FilterVO();
					cn51cn66FilterVO.setCompanyCode(cn66DetailsVO
							.getCompanyCode());
					cn51cn66FilterVO.setInvoiceNumber(cn66DetailsVO
							.getInvoiceNumber());
					cn51cn66FilterVO.setMailSeqnum(cn66DetailsVO.getMailSequenceNumber());
					cn51cn66FilterVO.setSequenceNumber(cn66DetailsVO
							.getSequenceNumber());
					cn51cn66FilterVO.setBillingBasis(cn66DetailsVO
							.getBillingBasis());
					cn51cn66FilterVO.setGpaCode(cn66DetailsVO.getGpaCode());
					cn51cn66FilterVO.setConsigneeDocumentNumber(cn66DetailsVO
							.getConsDocNo());
					if(cn66DetailsVO.getConsSeqNo()!=null && !cn66DetailsVO.getConsSeqNo().isEmpty()){
					cn51cn66FilterVO.setConsigneeSequenceNumber(Integer
							.parseInt(cn66DetailsVO.getConsSeqNo()));
					} 
					surchargeSession.setGpaBillingFilterVO(cn51cn66FilterVO);
				}
			}

		}
		invocationContext.target = ACTION_SUCCESS;
	}

}
