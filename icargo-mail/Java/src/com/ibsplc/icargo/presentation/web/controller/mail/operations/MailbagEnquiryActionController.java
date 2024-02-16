/*
 * MailbagEnquiryActionController Created on Jun 08, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.controller.mail.operations;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.controller.AbstractActionController;
import com.ibsplc.icargo.framework.web.spring.resource.ICargoResourceBundle;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.MailbagEnquiryModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.util.ContextUtils;

/**
 * Revision History
 * Revision 	 Date      	     Author			Description
 * 0.1		Jun 08, 2018	     A-2257			First draft
 */

@Controller
@RequestMapping("mail/operations/mailbagenquiry")
public class MailbagEnquiryActionController extends AbstractActionController<MailbagEnquiryModel>{
	/*
	 * Resource bundle for Mailbag Enquiry
	 */
	@Resource(name="mailbagEnquiryResourceBundle")
	private ICargoResourceBundle mailbagEnquiryResourceBundle;
	/**
	 * 
	 * 	Method		:	MailbagEnquiryActionController.fetchDetailsOnScreenload
	 *	Added by 	:	A-2257 on 08-Jun-2018
	 * 	Used for 	:   Used for loading details requird On Load of App
	 *	Parameters	:	@param mailbagEnquiryModel
	 *	Parameters	:	@return
	 *	Parameters	:	@throws BusinessDelegateException
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	ResponseVO
	 */
	@RequestMapping("/fetchDetailsOnScreenload")
	public @ResponseBody ResponseVO fetchDetailsOnScreenload(
			@RequestBody MailbagEnquiryModel mailbagEnquiryModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailbagenquiry.fetchdetailsonscreenload",mailbagEnquiryModel);
	}
	/**
	 * 
	 * 	Method		:	MailbagEnquiryActionController.fetchMailbagDetailsForEnquiry
	 *	Added by 	:	A-2257 on 08-Jun-2018
	 * 	Used for 	:   Used for loading details requird On Load of App
	 *	Parameters	:	@param mailbagEnquiryModel
	 *	Parameters	:	@return
	 *	Parameters	:	@throws BusinessDelegateException
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	ResponseVO
	 */
	@RequestMapping("/fetchMailbagDetailsForEnquiry")
	public @ResponseBody ResponseVO fetchMailbagDetailsForEnquiry(
			@RequestBody MailbagEnquiryModel mailbagEnquiryModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailbagenquiry.fetchmailbagdetailsforenquiry",mailbagEnquiryModel);
	}
	@RequestMapping("/validateMailbagDetailsForEnquiry")
	public @ResponseBody ResponseVO validateMailbagDetailsForEnquiry(
			@RequestBody MailbagEnquiryModel mailbagEnquiryModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailbagenquiry.validateMailbagDetailsForEnquiry",mailbagEnquiryModel);
	}
	@RequestMapping("/reassignMailbags")
	public @ResponseBody ResponseVO reassignMailbags(
			@RequestBody MailbagEnquiryModel mailbagEnquiryModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailbagenquiry.reassignMailbags",mailbagEnquiryModel);
	}
	@RequestMapping("/transferMailbags")
	public @ResponseBody ResponseVO transferMailbags(
			@RequestBody MailbagEnquiryModel mailbagEnquiryModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailbagenquiry.transferMailbags",mailbagEnquiryModel);
	}
	@RequestMapping("/offloadMailbags")
	public @ResponseBody ResponseVO offlaodMailbags(
			@RequestBody MailbagEnquiryModel mailbagEnquiryModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailbagenquiry.offloadMailbags",mailbagEnquiryModel);
	}
	
	@RequestMapping("/deliverMailbags")
	public @ResponseBody ResponseVO deliverMailbags(
			@RequestBody MailbagEnquiryModel mailbagEnquiryModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailbagenquiry.deliverMailbags",mailbagEnquiryModel);
	}
	@RequestMapping("/returnMailbags")
	public @ResponseBody ResponseVO returnMailbags(
			@RequestBody MailbagEnquiryModel mailbagEnquiryModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailbagenquiry.returnMailbags",mailbagEnquiryModel);
	}
	@RequestMapping("/viewMailbagDamage")
	public @ResponseBody ResponseVO viewMailbagDamage(
			@RequestBody MailbagEnquiryModel mailbagEnquiryModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailbagenquiry.viewMailbagDamage",mailbagEnquiryModel);
	}
	
	@RequestMapping("/validateFlightDetailsForEnquiry")
	public @ResponseBody ResponseVO validateFlightDetails(
			@RequestBody MailbagEnquiryModel mailbagEnquiryModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailbagenquiry.validateFlightDetailsForEnquiry",mailbagEnquiryModel);
	}
	
	@RequestMapping("/validateContainerDetailsForEnquiry")
	public @ResponseBody ResponseVO validateContainerDetailsForEnquiry(
			@RequestBody MailbagEnquiryModel mailbagEnquiryModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailbagenquiry.validateContainerDetailsForEnquiry",mailbagEnquiryModel);
	}
	
	@RequestMapping("/saveContainerDetailsForEnquiry")
	public @ResponseBody ResponseVO saveContainerDetailsForEnquiry(
			@RequestBody MailbagEnquiryModel mailbagEnquiryModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailbagenquiry.saveContainerDetailsForEnquiry",mailbagEnquiryModel);
	}
	
	@RequestMapping("/saveActualWeight")
	public @ResponseBody ResponseVO saveActualMailbagWeight(
			@RequestBody MailbagEnquiryModel mailbagEnquiryModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailbagenquiry.saveActualWeight",mailbagEnquiryModel);
	}
	
	@RequestMapping("/validateMailbags")
	public @ResponseBody ResponseVO validateMailbags(
			@RequestBody MailbagEnquiryModel mailbagEnquiryModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailbagenquiry.validateMailbags",mailbagEnquiryModel);
	}
	@RequestMapping("/updateOriginAndDest")
	public @ResponseBody ResponseVO updateOriginAndDest(
			@RequestBody MailbagEnquiryModel mailbagEnquiryModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailbagenquiry.updateOriginAndDest",mailbagEnquiryModel);
	}
	
	
	@RequestMapping("/getDamageImages")
	public @ResponseBody byte[] getDamageImages(
			@RequestParam String id )
		throws BusinessDelegateException, SystemException{
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		
		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = new MailTrackingDefaultsDelegate();
		
			String companyCode = logonAttributes.getCompanyCode();
			byte[] imageBytes = null;
			try {
				imageBytes = mailTrackingDefaultsDelegate.findMailbagDamageImages(companyCode,id);
			} catch( Exception e ) {
		    	throw new SystemException(SystemException.RESOURCE_NOT_FOUND,e);
		    }
			return imageBytes;
	}
	
	@Override
	public ICargoResourceBundle getResourceBundle() {
		return mailbagEnquiryResourceBundle;
	}
	
}