/*
 * ListPADetailsCommand.java Created on Jun 08, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailbagenquiry;

import static com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO.MAIL_STATUS_CAP_NOT_ACCEPTED;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.MailbagEnquiryModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOperationsModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.Mailbag;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * Revision History Revision Date Author Description 0.1 Jun 07, 2018 A-2257
 * First draft
 */

public class ListPADetailsCommand extends AbstractCommand {

	private Log log = LogFactory.getLogger("ListPADetailsCommand");

	/**
	 * 
	 */
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException {
		log.entering("ListPADetailsCommand", "execute");

		LogonAttributes logonAttributes = (LogonAttributes) getLogonAttribute();

		MailbagEnquiryModel mailbagEnquiryModel = (MailbagEnquiryModel) actionContext.getScreenModel();
		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = new MailTrackingDefaultsDelegate();
		ResponseVO responseVO = new ResponseVO();

		Collection<Mailbag> selectedMailbags = null;
		ArrayList<MailbagVO> mailbagVOs = null;
		
		ArrayList<ErrorVO> errors = new ArrayList<ErrorVO>();		

		if (mailbagEnquiryModel != null && mailbagEnquiryModel.getSelectedMailbags() != null) {

			log.log(Log.FINE, "mailbagEnquiryModel.getSelectedMailbags() not null");

			selectedMailbags = mailbagEnquiryModel.getSelectedMailbags();
			log.log(Log.FINE, "selectedMailbags --------->>", selectedMailbags);

			mailbagVOs = MailOperationsModelConverter.constructMailbagVOs(selectedMailbags,logonAttributes);	
			
			for(MailbagVO mailbagVO : mailbagVOs){
				if(MAIL_STATUS_CAP_NOT_ACCEPTED.equalsIgnoreCase(mailbagVO.getLatestStatus())) {
		    		ErrorVO errorVO = new ErrorVO("mailtracking.defaults.err.capturedbutnotaccepted");	
		    		actionContext.addError(errorVO);
		    		return;
				}else{
					try {
						mailTrackingDefaultsDelegate.findMailbagDamages(mailbagVO.getCompanyCode(),mailbagVO.getMailbagId());
					} catch (BusinessDelegateException businessDelegateException) {
						errors = (ArrayList) handleDelegateException(businessDelegateException);
					}
				}
			}

			

		}
		if (errors != null && errors.size() > 0) {
			actionContext.addAllError(errors);
			return;
		}

		responseVO.setStatus("success");
		actionContext.setResponseVO(responseVO);
		log.exiting("ListPADetailsCommand", "execute");

	}
	

}
