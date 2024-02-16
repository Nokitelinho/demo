/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.controller.mail.mra.gpabilling.gpabillingenquiry.GPABillingEnquiryModelActionController.java
 *
 *	Created by	:	A-4809
 *	Created on	:	Dec 17, 2018
 *
 *  Copyright 2018 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.controller.mail.mra.gpabilling.gpabillingenquiry;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.controller.AbstractActionController;
import com.ibsplc.icargo.framework.web.spring.resource.ICargoResourceBundle;
import com.ibsplc.icargo.presentation.web.model.mail.mra.gpabilling.GPABillingEnquiryModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;


/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.controller.mail.mra.gpabilling.gpabillingenquiry.GPABillingEnquiryModelActionController.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	Dec 17, 2018	:	Draft
 */
@Controller
@RequestMapping("mail/mra/gpabilling/gpabillingenquiry")
public class GPABillingEnquiryModelActionController extends AbstractActionController<GPABillingEnquiryModel>{
    @Resource(name="gpabillingEnquiryResourceBundle")
    ICargoResourceBundle gpabillingEnquiryResourceBundle;
    
	@RequestMapping("/screenload")
	public @ResponseBody ResponseVO fetchDetailsOnScreenload(@RequestBody GPABillingEnquiryModel billingEnquiryModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.mra.gpabilling.ux.gpabillingenquiry.fetchdetailsonscreenload",billingEnquiryModel);
	}
	@RequestMapping("/list")
	public @ResponseBody ResponseVO fetchDetailsOnList(@RequestBody GPABillingEnquiryModel billingEnquiryModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.mra.gpabilling.ux.gpabillingenquiry.fetchdetailsonlist",billingEnquiryModel);
	}
	@RequestMapping("/rerate")
	public @ResponseBody ResponseVO fetchDetailsOnReRate(@RequestBody GPABillingEnquiryModel billingEnquiryModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.mra.gpabilling.ux.gpabillingenquiry.fetchdetailsonrerate",billingEnquiryModel);
	}	
	@RequestMapping("/surchargedetails")
	public @ResponseBody ResponseVO fetchDetailsOnSurchargeDetails(@RequestBody GPABillingEnquiryModel billingEnquiryModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.mra.gpabilling.ux.gpabillingenquiry.fetchdetailsonsurchargedetails",billingEnquiryModel);
		}
	@RequestMapping("/changestatusdetails")
	public @ResponseBody ResponseVO fetchDetailsOnChangeStatus(@RequestBody GPABillingEnquiryModel billingEnquiryModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.mra.gpabilling.ux.gpabillingenquiry.fetchdetailsonchnagestatus",billingEnquiryModel);
		}		
	@RequestMapping("/automca")
	public @ResponseBody ResponseVO fetchDetailsOnAutoMCA(@RequestBody GPABillingEnquiryModel billingEnquiryModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.mra.gpabilling.ux.gpabillingenquiry.fetchdetailsonautomca",billingEnquiryModel);
		}
	@RequestMapping("/updatebilling")
	public @ResponseBody ResponseVO fetchDetailsOnUpdateBilling(@RequestBody GPABillingEnquiryModel billingEnquiryModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.mra.gpabilling.ux.gpabillingenquiry.fetchdetailsonupdatebilling",billingEnquiryModel);
		}
	@RequestMapping("/changestatussave")
	public @ResponseBody ResponseVO fetchDetailsOnChangeStatusSave(@RequestBody GPABillingEnquiryModel billingEnquiryModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.mra.gpabilling.ux.gpabillingenquiry.fetchdetailsonchangestatussave",billingEnquiryModel);
		}	
	@RequestMapping("/listconsignment")
	public @ResponseBody ResponseVO ListConsignmentDetails(@RequestBody GPABillingEnquiryModel billingEnquiryModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.mra.gpabilling.ux.gpabillingenquiry.listconsignmentdetails",billingEnquiryModel);
	}
	@Override
	public ICargoResourceBundle getResourceBundle() {
		return gpabillingEnquiryResourceBundle;
	}

}
