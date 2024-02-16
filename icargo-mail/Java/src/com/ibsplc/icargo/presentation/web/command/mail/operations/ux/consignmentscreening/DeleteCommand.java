/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.consignmentscreening.SaveScreeningDetailsCommand.java;
 *
 *	Created by	:	A-9084
 *	Created on	:	Nov 10, 2020
 *
 *  Copyright 2019 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.consignmentscreening;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentScreeningVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.ConsignmentScreeningModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ApplicableRegulation;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ConsignerDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ScreeningDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.SecurityExemption;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.consignmentscreening.SaveScreeningDetailsCommand.java;;
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-10383	:	16-May-2022	:	Draft
 */
public class DeleteCommand extends AbstractCommand{
	 private static final String DELETE_SUCCESS = "DELETE_SUCCESS";
	 private static final String DELETE_FAIL = "DELETE_FAIL";
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		
		LogonAttributes logonAttributes =getLogonAttribute();
		Collection<ErrorVO> errors =null;
		ConsignmentScreeningModel consignmentScreeningModel = (ConsignmentScreeningModel)actionContext.getScreenModel();
		ConsignmentScreeningVO consignmentScreeningvo =new ConsignmentScreeningVO();
		Collection<ConsignmentScreeningVO> consignmentScreeningVO =new ArrayList<>();
		MailTrackingDefaultsDelegate delegate = new MailTrackingDefaultsDelegate();
		ScreeningDetails screeningDetails= consignmentScreeningModel.getScreeningDetails();
		ConsignerDetails consignerDetails=consignmentScreeningModel.getConsignerDetails();
		ApplicableRegulation applicableRegulation = consignmentScreeningModel.getApplicableRegulation();
		SecurityExemption securityExemption =consignmentScreeningModel.getSecurityExemption();
		ResponseVO responseVO = new ResponseVO();
		long sernum;
		consignmentScreeningvo.setCompanyCode(logonAttributes.getCompanyCode()); 
		if(consignmentScreeningModel.getScreeningDetails()!=null){
			 sernum = Long.parseLong(screeningDetails.getSerialnum());
		}
		else if(consignmentScreeningModel.getConsignerDetails()!=null){
			 sernum = Long.parseLong(consignerDetails.getSerialnum());
		}
		else if(consignmentScreeningModel.getSecurityExemption()!=null){
			 sernum = Long.parseLong(securityExemption.getSerialnum());
		}
		else{
			 sernum = Long.parseLong(applicableRegulation.getSerialnum());
		}
			consignmentScreeningvo.setSerialNumber(sernum);
			consignmentScreeningVO.add(consignmentScreeningvo);
			
			try {
				delegate.deletescreeningDetails( consignmentScreeningVO);
				} catch (BusinessDelegateException businessDelegateException) {
					errors=handleDelegateException(businessDelegateException);
					businessDelegateException.getMessage();
				}
			String response=((errors==null)?DELETE_SUCCESS:DELETE_FAIL);
			responseVO.setStatus(response);
			actionContext.setResponseVO(responseVO);
	}
}
