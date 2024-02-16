package com.ibsplc.icargo.presentation.web.controller.mail.operations;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.controller.AbstractActionController;
import com.ibsplc.icargo.framework.web.spring.resource.ICargoResourceBundle;
import com.ibsplc.icargo.presentation.web.model.mail.operations.OffloadModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.controller.mail.mra.gpareporting.invoicenquiry.InvoicEnquiryActionController.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7929	:	13-Feb-2019	:	Draft
 */

@Controller
@RequestMapping("mail/operations/offload")
public class OffloadActionController extends AbstractActionController<OffloadModel> {
	
	 @Resource(name="offloadResourceBundle")
	  ICargoResourceBundle offloadResourceBundle;
	 
	 
	 @Override
		public ICargoResourceBundle getResourceBundle() {
			return offloadResourceBundle;
		}

	 
	 
	 @RequestMapping("/screenload")
		public @ResponseBody ResponseVO fetchDetailsOnScreenload(@RequestBody OffloadModel offloadModel)
			throws BusinessDelegateException, SystemException{
			return performAction("mail.operations.ux.offload.fetchdetailsonscreenload",offloadModel);
		}
	 
	 
	 
	 @RequestMapping("/list")
		public @ResponseBody ResponseVO fetchDetailsOnList(@RequestBody OffloadModel offloadModel)
			throws BusinessDelegateException, SystemException{
			return performAction("mail.operations.ux.offload.fetchDetailsOnList",offloadModel);
		}
	 
	 @RequestMapping("/offloadAction")
		public @ResponseBody ResponseVO saveoffloaddetails(@RequestBody OffloadModel offloadModel)
			throws BusinessDelegateException, SystemException{
			return performAction("mail.operations.ux.offload.saveoffloaddetails",offloadModel);
		}
	 
	
}
