/**
 *	Java file	: 	com.ibsplc.icargo.presentation.mobility.controller.mail.operations.MailbagEnquiryActionController.java
 *
 *	Created by	:	A-8464
 *	Created on	:	25-Mar-2019
 *
 *  Copyright 2018 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.mobility.controller.mail.operations;

import java.util.Arrays;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibsplc.icargo.business.mail.operations.vo.MailbagEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.mobility.controller.AbstractController;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.services.jaxws.endpoint.exception.WSBusinessException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.presentation.mobility.model.mail.operations.converter.MailOperationsModelConverter;
import com.ibsplc.icargo.presentation.mobility.model.mail.operations.mailbagenquiry.MailbagEnquiryFilter;
import com.ibsplc.icargo.presentation.mobility.model.mail.operations.mailbagenquiry.MailbagEnquiryModel;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.mobility.controller.mail.operations.MailbagEnquiryActionController.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8464	:	26-Mar-2019		:	Draft
 */

@Controller
@Module("mail")
@SubModule("operations")
@RequestMapping({"mail/operations/mailbagenquiry"})
public class MailbagEnquiryActionController extends AbstractController {
	
	private Log log = LogFactory.getLogger("MAIL OPERATIONS MAILBAGENQUIRY");
	private static final String CLASS_NAME = MailbagEnquiryActionController.class.getName();
	
	/**
	 * 	Method		:	MailbagEnquiryActionController.fetchMailbagDetails
	 *	Added by 	:	A-8464 on 26-Mar-2019
	 * 	Used for 	:	ICRD-273761
	 *	Parameters	:	@param mailbagEnquiryModel
	 *	Parameters	:	@return responseVO
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	ResponseVO
	 */
	@RequestMapping({"/fetchMailbagDetails"})
	public @ResponseBody ResponseVO fetchMailbagDetails(@RequestBody MailbagEnquiryModel mailbagEnquiryModel) 
			throws SystemException {
		log.entering(CLASS_NAME, "fetchMailbagDetails");
		ResponseVO responseVO = new ResponseVO();
		responseVO.setStatus(MailOperationsModelConverter.RESPONSE_STATUS_NO_DATA);
	     
	    LogonAttributes logonAttributes = (LogonAttributes)ContextUtils.getSecurityContext().getLogonAttributesVO();
	    MailbagEnquiryFilter filterFromUI = mailbagEnquiryModel.getMailbagEnquiryFilter();
	    MailbagEnquiryFilterVO mailbagEnquiryFilterVO = new MailbagEnquiryFilterVO();
	    mailbagEnquiryFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
	    mailbagEnquiryFilterVO.setMailbagId(filterFromUI.getMailbagId());
		MailbagVO resultMailbagVo = new MailbagVO();
		try {
			resultMailbagVo = despatchRequest("findMailbagDetailsForMailbagEnquiryHHT", mailbagEnquiryFilterVO);
		} catch (WSBusinessException e) {
			
			log.log(Log.SEVERE, "Error Found - #findAwbEnquiryDetailsforHHT");
			responseVO.setStatus(MailOperationsModelConverter.RESPONSE_STATUS_ERROR);
		}
		if (resultMailbagVo == null) {
			log.exiting(CLASS_NAME, "fetchMailbagDetails");
			return responseVO;
		}
		if(null != resultMailbagVo)
		{
			mailbagEnquiryModel = MailOperationsModelConverter.populateMailbagDetailsToMailbagEnquiryModel(mailbagEnquiryModel, resultMailbagVo);
		}
		
		responseVO.setResults(Arrays.asList(new MailbagEnquiryModel[] { mailbagEnquiryModel }));
		responseVO.setStatus(MailOperationsModelConverter.RESPONSE_STATUS_SUCCESS);
		log.exiting(CLASS_NAME, "fetchMailbagDetails");
		return responseVO;
	}
	
	
	
	}