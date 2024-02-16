/*
 * DeleteConsignmentCommand.java Created on Jan 14, 2019
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.consignment;


import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.MaintainConsignmentModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.Consignment;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-8672
 *
 */
public class DeleteConsignmentCommand extends AbstractCommand{
	
	private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.consignment";
	
	/**
	 * This method overrides the execute method of BaseComand class
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(ActionContext actionContext) throws CommandInvocationException {

		log.entering("DeleteConsignmentCommand", "execute");
		MaintainConsignmentModel maintainConsignmentModel = (MaintainConsignmentModel) actionContext.getScreenModel();
		
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		
		ConsignmentFilterVO consignmentFilterVO = new ConsignmentFilterVO();
		consignmentFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		consignmentFilterVO.setConsignmentNumber(maintainConsignmentModel.getConDocNo().toUpperCase());
		consignmentFilterVO.setPaCode(maintainConsignmentModel.getPaCode().toUpperCase());
		consignmentFilterVO.setPageNumber(Integer.parseInt(maintainConsignmentModel.getDisplayPage()));
		
		ConsignmentDocumentVO consignmentDocumentVO = null;
		Consignment consignment = new Consignment();
		List<ErrorVO> errors = null;
		
		try {
			consignmentDocumentVO = new MailTrackingDefaultsDelegate()
					.findConsignmentDocumentDetails(consignmentFilterVO);

		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessageVO().getErrors();
			handleDelegateException(businessDelegateException);
		}
		
		consignmentDocumentVO.setOperationFlag("D"); 
	       Page<MailInConsignmentVO> mailInConsignmentVOs = consignmentDocumentVO.getMailInConsignmentVOs();
	        if(mailInConsignmentVOs != null && mailInConsignmentVOs.size() != 0) {
	        	for (MailInConsignmentVO mailInConsignmentVO : mailInConsignmentVOs) {
	        		mailInConsignmentVO.setOperationFlag("D"); 
	        	}
	        }
	        consignmentDocumentVO.setMailInConsignmentVOs(mailInConsignmentVOs);
	       
	     	log.log(Log.FINE, "Going To Delete ...in command",
					consignmentDocumentVO);
			try {
			    new MailTrackingDefaultsDelegate().deleteConsignmentDocumentDetails(consignmentDocumentVO);
	       }catch (BusinessDelegateException businessDelegateException) {
	 			errors = handleDelegateException(businessDelegateException);
	 	    }
	 	  	if (errors != null && errors.size() > 0) {
	 	  		actionContext.addAllError(errors);
	 	  		return;
	 	  	}
		
		ResponseVO responseVO = new ResponseVO();	  
	    responseVO.setStatus("deleteConsignment_success");
	    actionContext.setResponseVO(responseVO);  
		log.exiting("DeleteConsignmentCommand","execute");
	}

}
