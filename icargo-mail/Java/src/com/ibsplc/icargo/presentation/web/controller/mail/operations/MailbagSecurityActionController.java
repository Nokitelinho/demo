package com.ibsplc.icargo.presentation.web.controller.mail.operations;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.controller.AbstractActionController;
import com.ibsplc.icargo.framework.web.spring.resource.ICargoResourceBundle;
import com.ibsplc.icargo.presentation.web.model.mail.operations.MailbagSecurityModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

@Controller
@RequestMapping("mail/operations/mailbagsecuritydetails")
public class MailbagSecurityActionController extends AbstractActionController<MailbagSecurityModel> {

	@Resource(name = "mailbagSecurityResourceBundle")
	ICargoResourceBundle mailbagSecurityResourceBundle;

	@Override
	public ICargoResourceBundle getResourceBundle() {
		return mailbagSecurityResourceBundle;
	}

	@RequestMapping("/list")
	public @ResponseBody ResponseVO listMailbagSecurityDetails(@RequestBody MailbagSecurityModel mailbagSecurityModel)
			throws BusinessDelegateException, SystemException {
		return performAction("mail.operations.ux.mailbagsecuritydetails.listMailbagSecurityDetails",
				mailbagSecurityModel); 
	}
	@RequestMapping("/savescreeningDetailsPopup")
	public @ResponseBody ResponseVO savescreeningDetailsPopup(@RequestBody MailbagSecurityModel mailbagSecurityModel)
			throws BusinessDelegateException, SystemException {
		return performAction("mail.operations.ux.mailbagsecuritydetails.savescreeningDetails",
				mailbagSecurityModel); 
	}
	
	@RequestMapping("/saveconsignorDetailsPopup")
	public @ResponseBody ResponseVO saveconsignorDetailsPopup(@RequestBody MailbagSecurityModel mailbagSecurityModel)
			throws BusinessDelegateException, SystemException {
		return performAction("mail.operations.ux.mailbagsecuritydetails.saveconsignorDetails",
				mailbagSecurityModel); 
	}

	@RequestMapping("/deleteDetails")
	public @ResponseBody ResponseVO deleteDetails(@RequestBody MailbagSecurityModel mailbagSecurityModel)
			throws BusinessDelegateException, SystemException {
		return performAction("mail.operations.ux.mailbagsecuritydetails.deleteDetails",
				mailbagSecurityModel); 
	}
	@RequestMapping("/printMailbagSecurityDetails")
	public @ResponseBody ResponseVO printMailbagSecurityDetails(@RequestBody MailbagSecurityModel mailbagSecurityModel)
			throws BusinessDelegateException, SystemException {
		return performAction("mail.operations.ux.mailbagsecuritydetails.printMailbagSecurityDetails",
				mailbagSecurityModel);
	}
	@RequestMapping("/saveSecurityStatusCode")
	public @ResponseBody ResponseVO saveSecurityStatusCode(@RequestBody MailbagSecurityModel mailbagSecurityModel)
			throws BusinessDelegateException, SystemException {
		return performAction("mail.operations.ux.mailbagsecuritydetails.saveSecurityStatusCode",
				mailbagSecurityModel); 
	}
}
