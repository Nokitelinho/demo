package com.ibsplc.icargo.presentation.web.controller.mail.operations;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.controller.AbstractActionController;
import com.ibsplc.icargo.framework.web.spring.resource.ICargoResourceBundle;
import com.ibsplc.icargo.presentation.web.model.mail.operations.MailRuleConfigModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

@Controller
@RequestMapping("mail/operations/mailruleconfig")
public class MailRuleConfigController extends AbstractActionController<MailRuleConfigModel> {

	@Resource(name="mailruleconfigResourceBundle")
	ICargoResourceBundle mailruleconfigResourceBundle;
	
	@Override
	public ICargoResourceBundle getResourceBundle() {
		return mailruleconfigResourceBundle;
	}

	@RequestMapping("/screenload")
	public @ResponseBody ResponseVO screenloadMailruleconfig(@RequestBody MailRuleConfigModel mailRuleConfigModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailruleconfig.screenload",mailRuleConfigModel);
	}
	
	@RequestMapping("/list")
	public @ResponseBody ResponseVO listMailruleconfig(@RequestBody MailRuleConfigModel mailRuleConfigModel)
			throws BusinessDelegateException, SystemException {
		return performAction("mail.operations.ux.mailruleconfig.list",
				mailRuleConfigModel);
	}
	
	@RequestMapping("/save")
	public @ResponseBody ResponseVO saveMailruleconfig(@RequestBody MailRuleConfigModel mailRuleConfigModel)
			throws BusinessDelegateException, SystemException {
		return performAction("mail.operations.ux.mailruleconfig.save",
				mailRuleConfigModel);
	}
	
	@RequestMapping("/validatemailrule")
	public @ResponseBody ResponseVO validateMailRule(@RequestBody MailRuleConfigModel mailRuleConfigModel)
			throws BusinessDelegateException, SystemException {
		return performAction("mail.operations.ux.mailruleconfig.validatemailrule",
				mailRuleConfigModel);
	}
}
