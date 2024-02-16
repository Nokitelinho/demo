/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.controller.mail.mra.receivablemanagement.advancepayment.AdvancePaymentModelActionController.java
 *
 *	Created by	:	A-4809
 *	Created on	:	18-Oct-2021
 *
 *  Copyright 2021 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.controller.mail.mra.receivablemanagement.advancepayment;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.controller.AbstractActionController;
import com.ibsplc.icargo.framework.web.spring.resource.ICargoResourceBundle;
import com.ibsplc.icargo.presentation.web.model.mail.mra.receivablemanagement.AdvancePaymentModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.controller.mail.mra.receivablemanagement.advancepayment.AdvancePaymentModelActionController.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	18-Oct-2021	:	Draft
 */
@Controller
@RequestMapping("mail/mra/receivablemanagement/advancepayment")
public class AdvancePaymentModelActionController extends AbstractActionController<AdvancePaymentModel>{
    @Resource(name="advancePaymentResourceBundle")
    ICargoResourceBundle advancePaymentResourceBundle;

	@RequestMapping("/screenload")
	public @ResponseBody ResponseVO fetchDetailsOnScreenload(@RequestBody AdvancePaymentModel advancePaymentModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.mra.receivablemanagement.ux.advancepayment.fetchdetailsonscreenload",advancePaymentModel);
	}
	@RequestMapping("/list")
	public @ResponseBody ResponseVO fetchDetailsOnList(@RequestBody AdvancePaymentModel advancePaymentModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.mra.receivablemanagement.ux.advancepayment.fetchdetailsonlist",advancePaymentModel);
	}
	@RequestMapping("/create")
	public @ResponseBody ResponseVO fetchDetailsOnCreate(@RequestBody AdvancePaymentModel advancePaymentModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.mra.receivablemanagement.ux.advancepayment.fetchdetailsoncreate",advancePaymentModel);
	}
	@RequestMapping("/deleteattachment")
	public @ResponseBody ResponseVO fetchDetailsOnDeleteAttachment(@RequestBody AdvancePaymentModel advancePaymentModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.mra.receivablemanagement.ux.advancepayment.fetchdetailsondeleteattachment",advancePaymentModel);
	}
    @RequestMapping("/deletebatch")
	public @ResponseBody ResponseVO fetchDetailsOnDeleteBatch(@RequestBody AdvancePaymentModel advancePaymentModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.mra.receivablemanagement.ux.advancepayment.fetchdetailsondelete",advancePaymentModel);
	}
    @RequestMapping("/editbatch")
	public @ResponseBody ResponseVO fetchDetailsOnEditBatch(@RequestBody AdvancePaymentModel advancePaymentModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.mra.receivablemanagement.ux.advancepayment.fetchdetailsonedit",advancePaymentModel);
	}
	@RequestMapping("/ok")
	public @ResponseBody ResponseVO fetchDetailsOnOK(@RequestBody AdvancePaymentModel advancePaymentModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.mra.receivablemanagement.ux.advancepayment.fetchdetailsonok",advancePaymentModel);
	}
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.web.spring.controller.AbstractActionController#getResourceBundle()
	 *	Added by 			: A-4809 on 18-Oct-2021
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 */
	@Override
	public ICargoResourceBundle getResourceBundle() {
		// TODO Auto-generated method stub
		return advancePaymentResourceBundle;
	}

}
